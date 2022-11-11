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

import org.apache.struts.action.Action;
import org.apache.struts.chain.contexts.ActionContext;

/**
 * This interface defines an intermediate handler that determines what method to
 * execute in an {@link Action}. Unlike the classical <code>execute</code>
 * signature, it is up to the dispatcher implementation to determine the
 * particular arguments and return type.
 *
 * @version $Rev$
 * @since Struts 1.4
 */
public interface Dispatcher extends Serializable {

    /**
     * Dispatches to the action referenced by the specified context.
     *
     * @param context the current action context
     * @return the result type, <code>null</code> if the response was handled
     *         directly, or {@link Void} if the executed method has no return
     *         signature
     * @throws Exception if an exception occurs
     */
    Object dispatch(ActionContext context) throws Exception;

}
