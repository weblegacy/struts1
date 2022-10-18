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

import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.ModuleConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Select and cache the <code>ActionForward</code> for this
 * <code>ActionConfig</code> if specified.</p>
 *
 * @version $Rev$ $Date: 2005-06-04 10:58:46 -0400 (Sat, 04 Jun 2005)
 *          $
 */
public abstract class AbstractSelectForward extends ActionCommandBase {
    // ------------------------------------------------------ Instance Variables

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(AbstractSelectForward.class);

    // ---------------------------------------------------------- Public Methods

    /**
     * <p>Select and cache the <code>ActionForward</code> for this
     * <code>ActionConfig</code> if specified.</p>
     *
     * @param actionCtx The <code>Context</code> for the current request
     * @return <code>false</code> so that processing continues
     * @throws Exception if thrown by the Action class
     */
    public boolean execute(ActionContext actionCtx)
        throws Exception {
        // Skip processing if the current request is not valid
        Boolean valid = actionCtx.getFormValid();

        if ((valid == null) || !valid.booleanValue()) {
            return CONTINUE_PROCESSING;
        }

        // Acquire configuration objects that we need
        ActionConfig actionConfig = actionCtx.getActionConfig();
        ModuleConfig moduleConfig = actionConfig.getModuleConfig();

        ForwardConfig forwardConfig = null;
        String forward = actionConfig.getForward();

        if (forward != null) {
            forwardConfig = forward(actionCtx, moduleConfig, forward);

            log.debug("Forwarding to {}", forwardConfig);

            actionCtx.setForwardConfig(forwardConfig);
        }

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
     * @return ForwwardConfig representing the destination
     */
    protected abstract ForwardConfig forward(ActionContext context,
        ModuleConfig moduleConfig, String uri);
}