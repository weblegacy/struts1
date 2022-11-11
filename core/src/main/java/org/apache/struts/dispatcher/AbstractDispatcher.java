/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.struts.dispatcher;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.struts.action.Action;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.util.MessageResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This abstract class is the stock template for {@link Dispatcher}
 * implementations.
 *
 * @version $Rev$
 * @since Struts 1.4
 */
public abstract class AbstractDispatcher implements Dispatcher, Serializable {
    private static final long serialVersionUID = 8527912438873600103L;

    // Package message bundle keys
    static final String LOCAL_STRINGS = "org.apache.struts.dispatcher.LocalStrings";
    static final String MSG_KEY_DISPATCH_ERROR = "dispatcher.error";
    static final String MSG_KEY_MISSING_METHOD = "dispatcher.missingMethod";
    static final String MSG_KEY_MISSING_METHOD_LOG = "dispatcher.missingMethod.log";
    static final String MSG_KEY_MISSING_MAPPING_PARAMETER = "dispatcher.missingMappingParameter";
    static final String MSG_KEY_UNSPECIFIED = "dispatcher.unspecified";

    /**
     * The name of the <code>cancelled</code> method.
     *
     * @see ActionContext#getCancelled()
     */
    public static final String CANCELLED_METHOD_NAME = "cancelled";

    /**
     * The name of the <code>execute</code> method.
     */
    public static final String EXECUTE_METHOD_NAME = "execute";

    /**
     * The message resources for this package.
     */
    static MessageResources messages = MessageResources.getMessageResources(LOCAL_STRINGS);

    /**
     * The {@code Log} instance for this class.
     */
    private transient final Logger log =
        LoggerFactory.getLogger(AbstractDispatcher.class);

    /**
     * The dictionary of {@link Method} objects we have introspected for this
     * class, keyed by method name. This collection is populated as different
     * methods are called, so that introspection needs to occur only once per
     * method name.
     */
    private transient final HashMap<String, Method> methods;

    private final MethodResolver methodResolver;

    /**
     * Constructs a new dispatcher with the specified method resolver.
     *
     * @param methodResolver the method resolver
     */
    public AbstractDispatcher(MethodResolver methodResolver) {
        this.methodResolver = methodResolver;
        methods = new HashMap<>();
    }

    /**
     * Constructs the arguments that will be passed to the dispatched method.
     * The construction is delegated to the method resolver instance.
     *
     * @param context the current action context
     * @param method the target method of this dispatch
     * @return the arguments array
     * @throws IllegalStateException if the method does not have a supported
     *         signature
     * @see MethodResolver#buildArguments(ActionContext, Method)
     */
    Object[] buildMethodArguments(ActionContext context, Method method) {
        Object[] args = methodResolver.buildArguments(context, method);
        if (args == null) {
            throw new IllegalStateException("Unsupported method signature: " + method.toString());
        }
        return args;
    }

    public Object dispatch(ActionContext context) throws Exception {
        // Resolve the method name; fallback to default if necessary
        String methodName = resolveMethodName(context);
        if ((methodName == null) || "".equals(methodName)) {
            methodName = getDefaultMethodName();
        }

        // Ensure there is a specified method name to invoke.
        // This may be null if the user hacks the query string.
        if (methodName == null) {
            return unspecified(context);
        }

        // Identify the method object to dispatch
        Method method;
        try {
            method = getMethod(context, methodName);
        } catch (NoSuchMethodException e) {
            // The log message reveals the offending method name...
            String path = context.getActionConfig().getPath();
            if (log.isErrorEnabled()) {
                String message = messages.getMessage(MSG_KEY_MISSING_METHOD_LOG, path, methodName);
                log.error(message, e);
            }

            // ...but the exception thrown does not
            // See r383718 (XSS vulnerability)
            String userMsg = messages.getMessage(MSG_KEY_MISSING_METHOD, path);
            NoSuchMethodException e2 = new NoSuchMethodException(userMsg);
            e2.initCause(e);
            throw e2;
        }

        // Invoke the named method and return its result
        return dispatchMethod(context, method, methodName);
    }

    /**
     * Dispatches to the specified method.
     *
     * @param context the current action context
     * @param method The method to invoke
     * @param name The name of the method to invoke
     * @return the return value of the method
     * @throws Exception if the dispatch fails with an exception
     * @see #buildMethodArguments(ActionContext, Method)
     */
    protected final Object dispatchMethod(ActionContext context, Method method, String name) throws Exception {
        Action target = context.getAction();
        String path = context.getActionConfig().getPath();
        Object[] args = buildMethodArguments(context, method);
        return invoke(target, method, args, path);
    }

    /**
     * Empties the method cache.
     *
     * @see #getMethod(ActionContext, String)
     */
    final void flushMethodCache() {
        synchronized (methods) {
            methods.clear();
        }
    }

    /**
     * Retrieves the name of the method to fallback upon if no method name can
     * be resolved. The default implementation returns
     * {@link #EXECUTE_METHOD_NAME}.
     *
     * @return the fallback method name; can be <code>null</code>
     * @see #resolveMethodName(ActionContext)
     * @see #EXECUTE_METHOD_NAME
     */
    protected String getDefaultMethodName() {
        return EXECUTE_METHOD_NAME;
    }

    /**
     * Introspects the action to identify a method of the specified name that
     * will be the target of the dispatch. This implementation caches the method
     * instance for subsequent invocations.
     *
     * @param context the current action context
     * @param methodName the name of the method to be introspected
     * @return the method of the specified name
     * @throws NoSuchMethodException if no such method can be found
     * @see #resolveMethod(ActionContext, String)
     * @see #flushMethodCache()
     */
    protected final Method getMethod(ActionContext context, String methodName) throws NoSuchMethodException {
        synchronized (methods) {
            // Key the method based on the class-method combination
            StringBuilder keyBuf = new StringBuilder(100);
            keyBuf.append(context.getAction().getClass().getName());
            keyBuf.append(":");
            keyBuf.append(methodName);
            String key = keyBuf.toString();

            Method method = methods.get(key);

            if (method == null) {
                method = resolveMethod(context, methodName);
                methods.put(key, method);
            }

            return method;
        }
    }

    /**
     * Convenience method to help dispatch the specified method. The method is
     * invoked via reflection.
     *
     * @param target the target object
     * @param method the method of the target object
     * @param args the arguments for the method
     * @param path the mapping path
     * @return the return value of the method
     * @throws Exception if the dispatch fails with an exception
     */
    protected final Object invoke(Object target, Method method, Object[] args, String path) throws Exception {
        try {
            Object retval = method.invoke(target, args);
            if (method.getReturnType() == void.class) {
                retval = void.class;
            }
            return retval;
        } catch (IllegalAccessException e) {
            log.atError()
                .setMessage("{}:{}")
                .addArgument(() -> messages.getMessage(MSG_KEY_DISPATCH_ERROR, path))
                .addArgument(method.getName())
                .setCause(e).log();
            throw e;
        } catch (InvocationTargetException e) {
            // Rethrow the target exception if possible so that the
            // exception handling machinery can deal with it
            Throwable t = e.getTargetException();
            if (t instanceof Exception) {
                throw (Exception) t;
            } else {
                log.atError()
                    .setMessage("{}:{}")
                    .addArgument(() -> messages.getMessage(MSG_KEY_DISPATCH_ERROR, path))
                    .addArgument(method.getName())
                    .setCause(e).log();
                throw new Exception(t);
            }
        }
    }

    /**
     * Determines whether the current form's cancel button was pressed. The
     * default behavior method will check if the
     * {@link ActionContext#getCancelled()} context property is set , which
     * normally occurs if the cancel button generated by <strong>CancelTag</strong>
     * was pressed by the user in the current request.
     *
     * @param context the current action context
     * @return <code>true</code> if the request is cancelled; otherwise
     *         <code>false</code>
     */
    protected boolean isCancelled(ActionContext context) {
        Boolean cancelled = context.getCancelled();
        return (cancelled != null) && cancelled.booleanValue();
    }

    /**
     * Decides the appropriate method instance for the specified method name.
     * Implementations may introspect for any desired method signature. This
     * resolution is only invoked if {@link #getMethod(ActionContext, String)}
     * does not find a match in its method cache.
     *
     * @param context the current action context
     * @param methodName the method name to use for introspection
     * @return the method to invoke
     * @throws NoSuchMethodException if an appropriate method cannot be found
     * @see #getMethod(ActionContext, String)
     * @see #invoke(Object, Method, Object[], String)
     */
    Method resolveMethod(ActionContext context, String methodName) throws NoSuchMethodException {
        return methodResolver.resolveMethod(context, methodName);
    }

    /**
     * Decides the method name that can handle the request.
     *
     * @param context the current action context
     * @return the method name or <code>null</code> if no name can be resolved
     * @see #getDefaultMethodName()
     * @see #resolveMethod(ActionContext, String)
     */
    abstract String resolveMethodName(ActionContext context);

    /**
     * Services the case when the dispatch fails because the method name cannot
     * be resolved. The default behavior throws an {@link IllegalStateException}.
     * Subclasses should override this to provide custom handling such as
     * sending an HTTP 404 error or dispatching elsewhere.
     *
     * @param context the current action context
     * @return the return value of the dispatch
     * @throws Exception if an error occurs
     * @see #resolveMethodName(ActionContext)
     */
    protected Object unspecified(ActionContext context) throws Exception {
        ActionConfig config = context.getActionConfig();
        String msg = messages.getMessage(MSG_KEY_UNSPECIFIED, config.getPath());
        log.error(msg);
        throw new IllegalStateException(msg);
    }
}