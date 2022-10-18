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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p> Create (if necessary) and cache an <code>Action</code> for this
 * request. </p>
 *
 * @version $Rev$ $Date: 2005-11-12 13:01:44 -0500 (Sat, 12 Nov 2005)
 *          $
 */
public abstract class AbstractCreateAction extends ActionCommandBase {
    // ------------------------------------------------------ Instance Variables

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(AbstractCreateAction.class);

    // ---------------------------------------------------------- Public Methods

    /**
     * <p>Create (if necessary) and cache an <code>Action</code> for this
     * request.</p>
     *
     * @param actionCtx The <code>Context</code> for the current request
     * @return <code>false</code> so that processing continues
     * @throws Exception if there are any problems instantiating the Action
     *                   class.
     */
    public boolean execute(ActionContext actionCtx)
        throws Exception {
        // Skip processing if the current request is not valid
        Boolean valid = actionCtx.getFormValid();

        if ((valid == null) || !valid.booleanValue()) {
            log.trace("Invalid form; not going to execute.");

            return CONTINUE_PROCESSING;
        }

        // Check to see if an action has already been created
        if (actionCtx.getAction() != null) {
            log.trace("already have an action [{}]", actionCtx.getAction());

            return CONTINUE_PROCESSING;
        }

        // Look up the class name for the desired Action
        ActionConfig actionConfig = actionCtx.getActionConfig();
        String type = actionConfig.getType();

        if (type == null) {
            String command = actionConfig.getCommand();
            if ((command == null) && (actionConfig.getForward() == null)
                && (actionConfig.getInclude() == null)) {
                log.error("no type or command for {}", actionConfig.getPath());
            } else {
                log.trace("no type for {}", actionConfig.getPath());
            }

            return CONTINUE_PROCESSING;
        }

        // Create (if necessary) and cache an Action instance
        Action action = getAction(actionCtx, type, actionConfig);

        log.trace("setting action to {}", action);

        actionCtx.setAction(action);

        return CONTINUE_PROCESSING;
    }

    // ------------------------------------------------------- Protected Methods

    /**
     * <p> Create and return the appropriate <code>Action</code> class for the
     * given <code>type</code> and <code>actionConfig</code>. </p> <p> NOTE:
     * The dependence on ActionServlet suggests that this should be broken up
     * along the lines of the other Abstract/concrete pairs in the
     * org.apache.struts.chain.commands package. </p>
     *
     * @param context      The <code>Context</code> for this request
     * @param type         Name of class to instantiate
     * @param actionConfig The {@link ActionConfig} for this request
     * @return Instantiated Action class
     * @throws Exception if there are any problems instantiating the Action
     *                   class.
     */
    protected abstract Action getAction(ActionContext context, String type,
        ActionConfig actionConfig)
        throws Exception;
}