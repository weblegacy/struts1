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
import java.lang.reflect.Method;

import org.apache.struts.chain.contexts.ActionContext;

/**
 * This interface defines a pluggable strategy for resolving a method and
 * extracting its methods from an {@link ActionContext}. A {@link Dispatcher}
 * may use the resolver to separate out its method resolution based on
 * technology (such as servlets, portlets, etc.).
 *
 * @version $Rev$
 * @since Struts 1.4
 */
public interface MethodResolver extends Serializable{

    /**
     * Constructs the arguments that will be passed to the dispatched method.
     *
     * @param context the current action context
     * @param method the target method of this dispatch
     *
     * @return the arguments array for the method, or <code>null</code> if the
     *         method does not match any supported signatures
     * @see #resolveMethod(ActionContext, String)
     */
    Object[] buildArguments(ActionContext context, Method method);

    /**
     * Decides the appropriate method instance for the specified method name.
     * Implementations may introspect for any desired method signature.
     *
     * @param context the current action context
     * @param methodName the method name to use for introspection
     * @return the method to invoke
     * @throws NoSuchMethodException if an appropriate method cannot be found
     */
    Method resolveMethod(ActionContext context, String methodName) throws NoSuchMethodException;
}