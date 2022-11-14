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
package org.apache.struts.dispatcher.servlet;

import java.lang.reflect.Method;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.dispatcher.AbstractMethodResolver;

/**
 * This class helps resolve methods with servlet-based signatures. The following
 * examples illustrate the method signatures supported:
 * <ul>
 * <li><code><i>return_type</i> execute()</code></li>
 * <li><code><i>return_type</i> execute(ActionContext context)</code></li>
 * <li><code><i>return_type</i> execute(ServletActionContext context)</code></li>
 * <li><code><i>return_type</i> execute(ActionMapping mapping, ActionForm form,
 HttpServletRequest request, HttpServletResponse response)</code></li>
 * </ul>
 *
 * @version $Rev$
 * @since Struts 1.4
 */
public class ServletMethodResolver extends AbstractMethodResolver {
    private static final long serialVersionUID = -4372320855532463910L;

    /**
     * The set of argument type classes for the classic reflected method call.
     * These are the same for all calls, so calculate them only once.
     *
     * @see org.apache.struts.action.Action#execute(ActionMapping, ActionForm,
     *      jakarta.servlet.ServletRequest, jakarta.servlet.ServletResponse)
     */
    private static final Class<?>[] CLASSIC_EXECUTE_SIGNATURE = { ActionMapping.class, ActionForm.class,
            HttpServletRequest.class, HttpServletResponse.class };

    public Object[] buildArguments(ActionContext context, Method method) {
        Object[] args = super.buildArguments(context, method);
        if (args == null) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            switch (parameterTypes.length) {
            case 4:
                return buildClassicArguments((ServletActionContext) context);
            default:
                break;
            }
        }
        return args;
    }

    /**
     * Constructs the arguments to invoke the classic <code>execute</code>
     * servlet signature from the specified context.
     *
     * @param context the current context
     * @return the arguments array
     * @throws NullPointerException if context is <code>null</code>
     * @see #resolveClassicMethod(ActionContext, String)
     */
    protected final Object[] buildClassicArguments(ServletActionContext context) {
        ActionConfig mapping = context.getActionConfig();
        ActionForm form = context.getActionForm();
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();
        return new Object[] { mapping, form, request, response };
    }

    /**
     * Obtains the method instance with the classic <code>execute</code>
     * servlet signature.
     *
     * @param context the current context
     * @param methodName the method name to introspect
     * @return the found method in the action
     * @throws NullPointerException if context is <code>null</code>
     * @throws NoSuchMethodException if the method does not exist
     * @see #buildClassicArguments(ServletActionContext)
     * @see org.apache.struts.action.Action#execute(ActionMapping, ActionForm,
     *      jakarta.servlet.ServletRequest, jakarta.servlet.ServletResponse)
     */
    protected final Method resolveClassicMethod(ActionContext context, String methodName) throws NoSuchMethodException {
        Class<? extends Action> actionClass = context.getAction().getClass();
        return actionClass.getMethod(methodName, CLASSIC_EXECUTE_SIGNATURE);
    }

    public Method resolveMethod(ActionContext context, String methodName) throws NoSuchMethodException {
        // First try to resolve anything the superclass supports
        try {
            return super.resolveMethod(context, methodName);
        } catch (NoSuchMethodException e) {
            // continue
        }

        // Can the method accept the servlet action context?
        if (context instanceof ServletActionContext) {
            try {
                Class<? extends Action> actionClass = context.getAction().getClass();
                return actionClass.getMethod(methodName, ServletActionContext.class);
            } catch (NoSuchMethodException e) {
                // continue
            }
        }

        // Lastly, try the classical argument listing
        return resolveClassicMethod(context, methodName);
    }
}