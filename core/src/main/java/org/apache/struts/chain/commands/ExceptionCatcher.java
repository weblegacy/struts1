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
import org.apache.commons.chain.Filter;
import org.apache.struts.chain.contexts.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Intercept any exception thrown by a subsequent {@code Command} in this
 * processing chain, and fire the configured exception handler chain after
 * storing the exception that has occurred into the {@code Context}.
 */
public class ExceptionCatcher extends ActionCommandBase implements Filter<ActionContext> {

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(ExceptionCatcher.class);

    // ------------------------------------------------------ Instance Variables

    /**
     * Field for CatalogName property.
     */
    private String catalogName = null;

    /**
     * Field for ExceptionCommand property.
     */
    private String exceptionCommand = null;

    // -------------------------------------------------------------- Properties

    /**
     * Return the name of the {@code Catalog} in which to perform lookups, or
     * {@code null} for the default {@code Catalog}.
     *
     * @return Name of catalog to use, or null
     */
    public String getCatalogName() {
        return (this.catalogName);
    }

    /**
     * Set the name of the {@code Catalog} in which to perform lookups, or
     * {@code null} for the default {@code Catalog}.
     *
     * @param catalogName The new catalog name or {@code null}
     */
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    /**
     * Return the name of the command to be executed if an exception occurs.
     *
     * @return The name of the command to be executed on an exception
     */
    public String getExceptionCommand() {
        return (this.exceptionCommand);
    }

    /**
     * Set the name of the command to be executed if an exception occurs.
     *
     * @param exceptionCommand The name of the chain to be executed
     */
    public void setExceptionCommand(String exceptionCommand) {
        this.exceptionCommand = exceptionCommand;
    }

    // ---------------------------------------------------------- Public Methods

    /**
     * Clear any existing stored exception and pass the {@code context} on to
     * the remainder of the current chain.
     *
     * @param actionCtx The {@code Context} for the current request
     *
     * @return {@code false} so that processing continues
     *
     * @throws Exception On any error
     */
    public boolean execute(ActionContext actionCtx)
        throws Exception {
        actionCtx.setException(null);

        return CONTINUE_PROCESSING;
    }

    /**
     * If an exception was thrown by a subsequent {@code Command}, pass it on
     * to the specified exception handling chain. Otherwise, do nothing.
     *
     * @param actionCtx The {@link ActionContext} to be processed by this
     *                  {@link Filter}
     * @param exception The {@code Exception} (if any) that was thrown by the
     *                  last {@link Command} that was executed; otherwise
     *                  {@code null}
     *
     * @return TRUE if post processing an exception occurred and the exception
     *         processing chain invoked
     *
     * @throws IllegalStateException If exception throws exception
     */
    public boolean postprocess(ActionContext actionCtx, Exception exception) {
        // Do nothing if there was no exception thrown
        if (exception == null) {
            return (false);
        }

        // Stash the exception in the specified context attribute
        log.debug("Attempting to handle a thrown exception");

        actionCtx.setException(exception);

        // Execute the specified command
        try {
            Command<ActionContext> command = lookupExceptionCommand();

            if (command == null) {
                log.error("Cannot find exceptionCommand '{}'",
                    exceptionCommand);
                throw new IllegalStateException(
                    "Cannot find exceptionCommand '" + exceptionCommand + "'");
            }

            log.trace("Calling exceptionCommand '{}'", exceptionCommand);

            command.execute(actionCtx);
        } catch (Exception e) {
            log.warn("Exception from exceptionCommand '{}'",
                exceptionCommand, e);
            IllegalStateException e2 = new IllegalStateException("Exception chain threw exception");
            e2.initCause(e);
            throw e2;
        }

        return true;
    }

    /**
     * Return the command to be executed if an exception occurs.
     *
     * @return The command to be executed if an exception occurs
     *
     * @throws IllegalArgumentException If catalog cannot be found
     * @throws IllegalStateException    If command property is not specified
     */
    protected Command<ActionContext> lookupExceptionCommand() {
        final String catalogName = getCatalogName();

        final CatalogFactory<ActionContext> catalogFactory =
                CatalogFactory.getInstance();

        final Catalog<ActionContext> catalog;

        if (catalogName == null) {
            catalog = catalogFactory.getCatalog();

            if (catalog == null) {
                log.error("Cannot find default catalog");
                throw new IllegalArgumentException(
                    "Cannot find default catalog");
            }
        } else {
            catalog = catalogFactory.getCatalog(catalogName);

            if (catalog == null) {
                log.error("Cannot find catalog '{}'", catalogName);
                throw new IllegalArgumentException("Cannot find catalog '"
                    + catalogName + "'");
            }
        }

        String exceptionCommand = getExceptionCommand();

        if (exceptionCommand == null) {
            log.error("No exceptionCommand property specified");
            throw new IllegalStateException(
                "No exceptionCommand property specfied");
        }

        return catalog.getCommand(exceptionCommand);
    }
}