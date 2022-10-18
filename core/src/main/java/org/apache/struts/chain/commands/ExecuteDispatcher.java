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
package org.apache.struts.chain.commands;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.Constants;
import org.apache.struts.chain.commands.util.ClassUtils;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.dispatcher.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteDispatcher extends ActionCommandBase {

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(ExecuteDispatcher.class);

    private String defaultDispatcherType;

    /**
     * Creates the dispatcher of the specified type.
     *
     * @param type the dispatcher class name
     * @param context the current action context
     * @return the dispatcher
     * @throws Exception if creation fails
     * @see ClassUtils#getApplicationInstance(String)
     */
    protected Dispatcher createDispatcher(String type, ActionContext context) throws Exception {
        log.info("Initializing dispatcher of type: {}", type);
        return (Dispatcher) ClassUtils.getApplicationInstance(type);
    }

    public boolean execute(ActionContext context) throws Exception {
        // Skip processing if the current request is not valid
        Boolean valid = context.getFormValid();
        if ((valid == null) || !valid.booleanValue()) {
            return CONTINUE_PROCESSING;
        }

        // Skip processing if no action is specified
        if (context.getAction() == null) {
            return CONTINUE_PROCESSING;
        }

        // Skip processing if no dispatcher type specified
        String dispatcherType = getDispatcherType(context);
        if (dispatcherType == null) {
            dispatcherType = defaultDispatcherType;
            if (dispatcherType == null) {
                return CONTINUE_PROCESSING;
            }
        }

        // Obtain (or create) the dispatcher cache
        String cacheKey = Constants.DISPATCHERS_KEY + context.getModuleConfig().getPrefix();
        @SuppressWarnings("unchecked")
        Map<String, Dispatcher> dispatchers = (Map<String, Dispatcher>) context.getApplicationScope().get(cacheKey);
        if (dispatchers == null) {
            dispatchers = new HashMap<>();
            context.getApplicationScope().put(cacheKey, dispatchers);
        }

        // Lookup (or create) the dispatch instance
        Dispatcher dispatcher = null;
        synchronized (dispatchers) {
            ActionConfig actionConfig = context.getActionConfig();
            String actionType = actionConfig.getType();
            dispatcher = dispatchers.get(actionType);

            if (dispatcher == null) {
                dispatcher = createDispatcher(dispatcherType, context);
                dispatchers.put(actionType, dispatcher);
            }
        }

        // Dispatch
        Object result = dispatcher.dispatch(context);
        processDispatchResult(result, context);

        return CONTINUE_PROCESSING;
    }

    /**
     * Retrieves the fully-qualified dispatcher class name to instantiate when
     * an action mapping does not specify a dispatcher.
     *
     * @return the default dispatcher type (fully-qualified class name)
     * @see #getDispatcherType(ActionContext)
     * @see #setDefaultDispatcherType(String)
     */
    public final String getDefaultDispatcherType() {
        return defaultDispatcherType;
    }

    /**
     * Retrieves the fully-qualified class name of the dispatcher to instantiate
     * for the specified context.
     *
     * @param context the current action context
     * @return the class name or <code>null</code>
     * @see #getDefaultDispatcherType()
     */
    protected String getDispatcherType(ActionContext context) {
        String dispatcherType = null;
        if (context != null) {
            dispatcherType = context.getActionConfig().getDispatcher();
        }
        return dispatcherType;
    }

    /**
     * Interprets the specified dispatch result. Subclasses should override this
     * method to provide custom result type handling. Four handlings are
     * automatically provided:
     * <ol>
     * <li><code>null</code> type means the response was handled directly,
     * and therefore no extra processing is perform</li>
     * <li>{@link ForwardConfig} type is the classical response, and will be
     * stored in the context</li>
     * <li>{@link String} type represents the name of a required forward to
     * lookup, and will be stored in the context</li>
     * <li>{@link Void} type means the method had no return signature, and
     * select the {@link Action#SUCCESS} action forward and store in the context</li>
     * </ol>
     *
     * @param result the result value
     * @param context the current action context
     * @throws IllegalStateException if unknown result type or the forward
     *         cannot be found
     * @see ActionMapping#findRequiredForward(String)
     */
    protected void processDispatchResult(Object result, ActionContext context) {
        // Null means the response was handled directly
        if (result == null) {
            return;
        }

        // A forward is the classical response
        if (result instanceof ForwardConfig) {
            context.setForwardConfig((ForwardConfig) result);
            return;
        }

        // String represents the name of a forward
        ActionConfig actionConfig = context.getActionConfig();
        ActionMapping mapping = ((ActionMapping) actionConfig);
        if (result instanceof String) {
            context.setForwardConfig(mapping.findRequiredForward((String) result));
            return;
        }

        // Select success if no return signature
        if (result == void.class) {
            context.setForwardConfig(mapping.findRequiredForward(Action.SUCCESS));
            return;
        }

        // Unknown result type
        throw new IllegalStateException("Unknown dispatch return type: " + result.getClass().getName());
    }

    /**
     * Stores the optional default dispatcher type (fully-qualified class name).
     *
     * @param defaultDispatcherType the dispatcher type or <code>null</code>
     * @see #getDefaultDispatcherType()
     */
    public final void setDefaultDispatcherType(String defaultDispatcherType) {
        this.defaultDispatcherType = defaultDispatcherType;
    }
}