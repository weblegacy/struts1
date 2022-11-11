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

import java.lang.reflect.Method;

import org.apache.struts.action.Action;
import org.apache.struts.chain.contexts.ActionContext;

/**
 * This abstract class is the stock template for resolving methods for a
 * {@link Dispatcher}. The implementation handles methods that accept no
 * arguments or only an {@link ActionContext} instance:
 * <ul>
 * <li><code><i>return_type</i> execute()</code></li>
 * <li><code><i>return_type</i> execute(ActionContext context)</code></li>
 * </ul>
 *
 * @version $Rev$
 * @since Struts 1.4
 */
public abstract class AbstractMethodResolver implements MethodResolver {
    private static final long serialVersionUID = -9045373032747695495L;

    /**
     * The argument listing for a method without arguments.
     *
     * @see #EMPTY_ARGUMENT_TYPES
     */
    protected static final Object[] EMPTY_ARGUMENTS = {};

    /**
     * The type listing used for a method without arguments.
     *
     * @see #EMPTY_ARGUMENTS
     */
    protected static final Class<?>[] EMPTY_ARGUMENT_TYPES = {};

    /**
     * The argument listing for a method accepting the {@link ActionContext}.
     */
    private static final Class<?>[] ACTION_CONTEXT_ARGUMENT_TYPES = { ActionContext.class };

    public Object[] buildArguments(ActionContext context, Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        switch (parameterTypes.length) {
        case 0:
            return EMPTY_ARGUMENTS;

        case 1:
            if (parameterTypes[0].isInstance(context)) {
                return new Object[] { context };
            }
            return null;

        default:
            return null;
        }
    }

    public Method resolveMethod(ActionContext context, String methodName) throws NoSuchMethodException {
        Class<? extends Action> actionClass = context.getAction().getClass();

        // Does the method accept nothing?
        try {
            return actionClass.getMethod(methodName, EMPTY_ARGUMENT_TYPES);
        } catch (NoSuchMethodException e) {
            // continue
        }

        // Does the method accept the action context?
        return actionClass.getMethod(methodName, ACTION_CONTEXT_ARGUMENT_TYPES);
    }

}
