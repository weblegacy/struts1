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
package org.apache.struts.extras.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>An abstract <strong>Action</strong> that dispatches to a public method
 * that is named by the request parameter whose name is specified by the
 * <code>parameter</code> property of the corresponding ActionMapping.  This
 * Action is useful for developers who prefer to combine many similar actions
 * into a single Action class, in order to simplify their application
 * design.</p>
 *
 * <p>To configure the use of this action in your <code>struts-config.xml</code>
 * file, create an entry like this:</p>
 *
 * {@code &lt;action path="/saveSubscription"
 * type="org.apache.struts.extras.actions.DispatchAction"
 * name="subscriptionForm" scope="request" input="/subscription.jsp"
 * parameter="method"/&gt;}
 *
 * <p>which will use the value of the request parameter named "method" to pick
 * the appropriate "execute" method, which must have the same signature (other
 * than method name) of the standard Action.execute method.  For example, you
 * might have the following three methods in the same action:</p>
 *
 * <ul>
 *
 * <li>public ActionForward delete(ActionMapping mapping, ActionForm form,
 * HttpServletRequest request, HttpServletResponse response) throws
 * Exception</li>
 *
 * <li>public ActionForward insert(ActionMapping mapping, ActionForm form,
 * HttpServletRequest request, HttpServletResponse response) throws
 * Exception</li>
 *
 * <li>public ActionForward update(ActionMapping mapping, ActionForm form,
 * HttpServletRequest request, HttpServletResponse response) throws
 * Exception</li>
 *
 * </ul>
 *
 * <p>and call one of the methods with a URL like this:</p>
 *
 * <p> <code> http://localhost:8080/myapp/saveSubscription.do?method=update
 * </code></p>
 *
 * <p><strong>NOTE</strong> - All of the other mapping characteristics of this
 * action must be shared by the various handlers.  This places some
 * constraints over what types of handlers may reasonably be packaged into the
 * same <code>DispatchAction</code> subclass.</p>
 *
 * <p><strong>NOTE</strong> - If the value of the request parameter is empty,
 * a method named <code>unspecified</code> is called. The default action is to
 * throw an exception. If the request was cancelled (a
 * <code>html:cancel</code> button was pressed), the custom handler
 * <code>cancelled</code> will be used instead. You can also override the
 * <code>getMethodName</code> method to override the action's default handler
 * selection.</p>
 *
 * @version $Rev$ $Date$
 */
public abstract class DispatchAction extends BaseAction {
    private static final long serialVersionUID = 4211198620691041849L;

    /**
     * The {@code Log} instance for this class.
     */
    private transient final Logger log =
        LoggerFactory.getLogger(DispatchAction.class);

    // ----------------------------------------------------- Instance Variables

    /**
     * The Class instance of this <code>DispatchAction</code> class.
     */
    protected Class<? extends DispatchAction> clazz = this.getClass();

    /**
     * The set of Method objects we have introspected for this class, keyed by
     * method name.  This collection is populated as different methods are
     * called, so that introspection needs to occur only once per method
     * name.
     */
    protected HashMap<String, Method> methods = new HashMap<>();

    /**
     * The set of argument type classes for the reflected method call.  These
     * are the same for all calls, so calculate them only once.
     */
    protected Class<?>[] types =
        {
            ActionMapping.class, ActionForm.class, HttpServletRequest.class,
            HttpServletResponse.class
        };

    // --------------------------------------------------------- Public Methods

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred, or
     *         <code>null</code> if the response has been completed.
     * @throws Exception if an exception occurs
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        if (isCancelled(request)) {
            ActionForward af = cancelled(mapping, form, request, response);

            if (af != null) {
                return af;
            }
        }

        // Get the parameter. This could be overridden in subclasses.
        String parameter = getParameter(mapping, form, request, response);

        // Get the method's name. This could be overridden in subclasses.
        String name =
            getMethodName(mapping, form, request, response, parameter);

        // Prevent recursive calls
        if ("execute".equals(name) || "perform".equals(name)) {
            String message =
                messages.getMessage("dispatch.recursive", mapping.getPath());

            log.error(message);
            throw new ServletException(message);
        }

        // Invoke the named method, and return the result
        return dispatchMethod(mapping, form, request, response, name);
    }

    /**
     * Method which is dispatched to when there is no value for specified
     * request parameter included in the request.  Subclasses of
     * <code>DispatchAction</code> should override this method if they wish to
     * provide default behavior different than throwing a ServletException.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The non-HTTP request we are processing
     * @param response The non-HTTP response we are creating
     * @return The forward to which control should be transferred, or
     *         <code>null</code> if the response has been completed.
     * @throws Exception if the application business logic throws an
     *                   exception.
     */
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        String message =
            messages.getMessage("dispatch.parameter", mapping.getPath(),
                mapping.getParameter());

        log.error(message);

        throw new ServletException(message);
    }

    /**
     * Method which is dispatched to when the request is a cancel button
     * submit. Subclasses of <code>DispatchAction</code> should override this
     * method if they wish to provide default behavior different than
     * returning null.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The non-HTTP request we are processing
     * @param response The non-HTTP response we are creating
     * @return The forward to which control should be transferred, or
     *         <code>null</code> if the response has been completed.
     * @throws Exception if the application business logic throws an
     *                   exception.
     * @since Struts 1.2.0
     */
    protected ActionForward cancelled(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        return null;
    }

    // ----------------------------------------------------- Protected Methods

    /**
     * Dispatch to the specified method.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The non-HTTP request we are processing
     * @param response The non-HTTP response we are creating
     * @param name     The name of the method to invoke
     * @return The forward to which control should be transferred, or
     *         <code>null</code> if the response has been completed.
     * @throws Exception if the application business logic throws an
     *                   exception.
     * @since Struts 1.1
     */
    protected ActionForward dispatchMethod(ActionMapping mapping,
        ActionForm form, HttpServletRequest request,
        HttpServletResponse response, String name)
        throws Exception {
        // Make sure we have a valid method name to call.
        // This may be null if the user hacks the query string.
        if (name == null) {
            return this.unspecified(mapping, form, request, response);
        }

        // Identify the method object to be dispatched to
        Method method = null;

        try {
            method = getMethod(name);
        } catch (NoSuchMethodException e) {
            log.atError()
                .setMessage(() -> messages.getMessage("dispatch.method", mapping.getPath(), name))
                .setCause(e).log();

            String userMsg =
                messages.getMessage("dispatch.method.user", mapping.getPath());
            NoSuchMethodException e2 = new NoSuchMethodException(userMsg);
            e2.initCause(e);
            throw e2;
        }

        ActionForward forward = null;

        try {
            Object[] args = { mapping, form, request, response };

            forward = (ActionForward) method.invoke(this, args);
        } catch (ClassCastException e) {
            log.atError()
                .setMessage(() -> messages.getMessage("dispatch.return", mapping.getPath(), name))
                .setCause(e).log();
            throw e;
        } catch (IllegalAccessException e) {
            log.atError()
                .setMessage(() -> messages.getMessage("dispatch.error", mapping.getPath(), name))
                .setCause(e).log();
            throw e;
        } catch (InvocationTargetException e) {
            // Rethrow the target exception if possible so that the
            // exception handling machinery can deal with it
            Throwable t = e.getTargetException();

            if (t instanceof Exception) {
                throw ((Exception) t);
            } else {
                log.atError()
                    .setMessage(() -> messages.getMessage("dispatch.error", mapping.getPath(),
                        name))
                    .setCause(e).log();
                throw new ServletException(t);
            }
        }

        // Return the returned ActionForward instance
        return (forward);
    }

    /**
     * <p>Returns the parameter value.</p>
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The <code>ActionMapping</code> parameter's value
     * @throws Exception if the parameter is missing.
     */
    protected String getParameter(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        // Identify the request parameter containing the method name
        String parameter = mapping.getParameter();

        if (parameter == null) {
            String message =
                messages.getMessage("dispatch.handler", mapping.getPath());

            log.error(message);

            throw new ServletException(message);
        }


        return parameter;
    }

    /**
     * Introspect the current class to identify a method of the specified name
     * that accepts the same parameter types as the <code>execute</code>
     * method does.
     *
     * @param name Name of the method to be introspected
     * @return The method with the specified name.
     * @throws NoSuchMethodException if no such method can be found
     */
    protected Method getMethod(String name)
        throws NoSuchMethodException {
        synchronized (methods) {
            Method method = methods.get(name);

            if (method == null) {
                method = clazz.getMethod(name, types);
                methods.put(name, method);
            }

            return (method);
        }
    }

    /**
     * Returns the method name, given a parameter's value.
     *
     * @param mapping   The ActionMapping used to select this instance
     * @param form      The optional ActionForm bean for this request (if
     *                  any)
     * @param request   The HTTP request we are processing
     * @param response  The HTTP response we are creating
     * @param parameter The <code>ActionMapping</code> parameter's name
     * @return The method's name.
     * @throws Exception if an error occurs.
     * @since Struts 1.2.0
     */
    protected String getMethodName(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response,
        String parameter) throws Exception {
        // Identify the method name to be dispatched to.
        // dispatchMethod() will call unspecified() if name is null
        return request.getParameter(parameter);
    }
}