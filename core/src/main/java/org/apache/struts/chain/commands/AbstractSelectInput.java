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

import org.apache.struts.action.Action;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.ModuleConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Select and cache a <code>ForwardConfig</code> that returns us to the
 * input page for the current action, if any.</p>
 *
 * @version $Rev$ $Date: 2005-06-04 10:58:46 -0400 (Sat, 04 Jun 2005)
 *          $
 */
public abstract class AbstractSelectInput extends ActionCommandBase {
    // ------------------------------------------------------ Instance Variables

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(AbstractSelectInput.class);

    // ---------------------------------------------------------- Public Methods

    /**
     * <p>Select and cache a <code>ForwardConfig</code> for the input page for
     * the current request.</p>
     *
     * @param actionCtx The <code>Context</code> for the current request
     * @return <code>false</code> so that processing continues
     * @throws Exception if thrown by the Action class
     */
    public boolean execute(ActionContext actionCtx)
        throws Exception {
        // Skip processing if the current request is valid
        Boolean valid = actionCtx.getFormValid();

        if ((valid != null) && valid.booleanValue()) {
            return CONTINUE_PROCESSING;
        }

        // Acquire configuration objects that we need
        ActionConfig actionConfig = actionCtx.getActionConfig();
        ModuleConfig moduleConfig = actionConfig.getModuleConfig();

        // Cache an ForwardConfig back to our input page
        ForwardConfig forwardConfig = null;
        String input = actionConfig.getInput();

        if (moduleConfig.getControllerConfig().getInputForward()) {
            log.trace("Finding ForwardConfig for '{}'", input);
            forwardConfig = inputForward(actionConfig, moduleConfig, input);
            if (forwardConfig == null) {
                log.atError().log(() -> getErrorMessage(actionCtx, actionConfig));
            }
        } else {
            log.trace("Delegating to forward() for '{}'", input);
            forwardConfig = forward(actionCtx, moduleConfig, input);
        }

        log.debug("Forwarding back to {}", forwardConfig);

        actionCtx.setForwardConfig(forwardConfig);

        return CONTINUE_PROCESSING;
    }

    // ------------------------------------------------------- Protected Methods

    /**
     * <p>Create and return a <code>ForwardConfig</code> representing the
     * specified module-relative destination.</p>
     *
     * @param context      The context for this request
     * @param moduleConfig The <code>ModuleConfig</code> for this request
     * @param uri          The module-relative URI to be the destination
     * @return ForwardConfig representing destination
     */
    protected abstract ForwardConfig forward(ActionContext context,
        ModuleConfig moduleConfig, String uri);

    /**
     * <p> Retrieve error message from context. </p>
     *
     * @param context      The <code>Context</code> for the current request
     * @param actionConfig The current action mapping
     * @return error message
     */
    protected abstract String getErrorMessage(ActionContext context,
        ActionConfig actionConfig);

    /**
     * Attempts to resolve the input as a {@link ForwardConfig} attribute.
     * This method should only invoked if the Controller has its
     * <code>inputForward</code> property set to <code>true</code>.
     * If the input parameter is specified, use that, otherwise try
     * to find one in the mapping or the module under the standard
     * conventional <code>input</code> name.
     *
     * @param actionConfig the config for the target action
     * @param moduleConfig the config for the module of the action
     * @param input the name of the input
     * @return ForwardConfig representing destination
     * @see Action#INPUT
     */
    protected ForwardConfig inputForward(ActionConfig actionConfig,
            ModuleConfig moduleConfig, String input) {
        ForwardConfig forwardConfig;

        if (input != null) {
            forwardConfig = actionConfig.findForwardConfig(input);
            if (forwardConfig == null) {
                forwardConfig = moduleConfig.findForwardConfig(input);
            }
        } else {
            forwardConfig = actionConfig.findForwardConfig(Action.INPUT);
            if (forwardConfig == null) {
                forwardConfig = moduleConfig.findForwardConfig(Action.INPUT);
            }
        }

        return forwardConfig;
    }
}