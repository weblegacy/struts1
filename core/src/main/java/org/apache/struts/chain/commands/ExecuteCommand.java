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

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.CatalogFactory;
import org.apache.commons.chain.Command;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.config.ActionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Invoke the appropriate {@code Command} for this request. If the context's
 * {@code ActionConfig} has no {@code command} property defined, no action will
 * be taken. If the specified command cannot be found, a warning will be
 * logged, but processing will continue. Depending on how the chain is
 * configured, this can be used in place of an {@code Action} or as a method of
 * performing pre-processing.
 *
 * <p>If used instead of an action, the command which is looked up should put
 * an ActionForward into the context, unless it has already dealt with the
 * response.</p>
 */
public class ExecuteCommand extends ActionCommandBase {
    // ------------------------------------------------------ Instance Variables

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(ExecuteCommand.class);

    // ---------------------------------------------------------- Public Methods

    /**
     * If the {@code context} is "valid", lookup a command and execute it.
     *
     * @param actionCtx The {@code ActionContext} for the current request
     *
     * @return the result of the lookup command's {@code execute} method,
     *         if executed, or {@code false} if it was not executed.
     * @throws Exception on any error
     */
    public boolean execute(ActionContext actionCtx)
        throws Exception {
        if (shouldProcess(actionCtx)) {
            Command<ActionContext> command = getCommand(actionCtx);

            if (command != null) {
                return command.execute(actionCtx);
            }
        }

        return CONTINUE_PROCESSING;
    }

    /**
     * Evaluate the current context to see if a command should even be
     * executed.
     *
     * @param context A valid ActionContext
     *
     * @return TRUE if the pending Command should be executed
     */
    protected boolean shouldProcess(ActionContext context) {
        // Skip processing if the current request is not valid
        Boolean valid = context.getFormValid();

        return valid != null && valid.booleanValue();
    }

    /**
     * Find the {@code ActionConfig} in the current context and, if it is
     * properly configured, lookup the appropriate {@code commons-chain}
     * command.
     *
     * @param context A valid ActionContext
     *
     * @return a {@code Command} to execute, or null if none is specified
     *         or if the specified command cannot be found.
     */
    protected Command<ActionContext> getCommand(ActionContext context) {
        ActionConfig actionConfig = context.getActionConfig();

        String commandName = actionConfig.getCommand();

        if (commandName == null) {
            return null;
        }

        String catalogName = actionConfig.getCatalog();

        return getCommand(commandName, catalogName);
    }

    /**
     * Retrieve the specified Command from the specified Catalog.
     *
     * @param commandName The Command to retrieve.
     * @param catalogName The Catalog to search.
     *
     * @return Instantiated Command, or null
     */
    protected Command<ActionContext> getCommand(String commandName,
            String catalogName) {

        if (commandName == null) {
            return null;
        }

        final CatalogFactory<ActionContext> catalogFactory =
                CatalogFactory.getInstance();

        final Catalog<ActionContext> catalog;

        if (catalogName != null) {
            catalog = catalogFactory.getCatalog(catalogName);

            if (catalog == null) {
                log.warn("When looking up {}, no catalog found under {}",
                    commandName, catalogName);

                return null;
            }
        } else {
            catalogName = "the default catalog";
            catalog = catalogFactory.getCatalog();

            if (catalog == null) {
                log.warn("When looking up {}, no default catalog found.",
                    commandName);

                return null;
            }
        }

        log.debug("looking up command {} in {}",
            commandName, catalogName);

        return catalog.getCommand(commandName);
    }
}