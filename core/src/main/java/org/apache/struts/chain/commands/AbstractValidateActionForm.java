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

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.InvalidCancelException;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.config.ActionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Validate the properties of the form bean for this request.  If there are
 * any validation errors, execute the specified command; otherwise, proceed
 * normally.</p>
 *
 * @version $Rev$ $Date: 2005-06-04 10:58:46 -0400 (Sat, 04 Jun 2005)
 *          $
 */
public abstract class AbstractValidateActionForm extends ActionCommandBase {
    // ------------------------------------------------------ Instance Variables

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(AbstractSelectForward.class);

    // ------------------------------------------------------ Protected Methods

    /**
     * <p>Helper method to verify the Cancel state.</p>
     *
     * <p>If the state is invalid, Cancel is unset and an
     * InvalidCancelException is thrown.</p>
     *
     * @param actionCtx    Our ActionContext
     * @param actionConfig Our ActionConfig
     * @return true if cancel is set, false otherwise.
     * @throws InvalidCancelException
     */
    private boolean isCancelled(ActionContext actionCtx,
        ActionConfig actionConfig)
        throws InvalidCancelException {
        Boolean cancel = actionCtx.getCancelled();
        boolean cancelled = ((cancel != null) && cancel.booleanValue());
        boolean cancellable = actionConfig.getCancellable();

        boolean invalidState = (cancelled && !cancellable);

        if (invalidState) {
            actionCtx.setCancelled(Boolean.FALSE);
            actionCtx.setFormValid(Boolean.FALSE);
            throw new InvalidCancelException();
        }

        return cancelled;
    }

    // ---------------------------------------------------------- Public Methods

    /**
     * <p>Validate the properties of the form bean for this request.  If there
     * are any validation errors, execute the child commands in our chain;
     * otherwise, proceed normally.</p>
     *
     * @param actionCtx The <code>Context</code> for the current request
     * @return <code>false</code> so that processing continues, if there are
     *         no validation errors; otherwise <code>true</code>
     * @throws Exception if thrown by the Action class
     */
    public boolean execute(ActionContext actionCtx)
        throws Exception {
        // Set form valid until found otherwise
        actionCtx.setFormValid(Boolean.TRUE);

        // Is there a form bean for this request?
        ActionForm actionForm = actionCtx.getActionForm();

        if (actionForm == null) {
            return CONTINUE_PROCESSING;
        }

        // Is validation disabled on this request?
        ActionConfig actionConfig = actionCtx.getActionConfig();

        if (!actionConfig.getValidate()) {
            return CONTINUE_PROCESSING;
        }

        // Was this request cancelled?
        if (isCancelled(actionCtx, actionConfig)) {
            log.debug("Cancelled transaction, skipping validation");
            return CONTINUE_PROCESSING;
        }

        // Call the validate() method of this form bean
        ActionErrors errors = validate(actionCtx, actionConfig, actionForm);

        // If there were no errors, proceed normally
        if ((errors == null) || (errors.isEmpty())) {
            return CONTINUE_PROCESSING;
        }

        // Flag the validation failure and proceed
        /* NOTE: Is there any concern that there might have already
         * been errors, or that other errors might be coming?
         */
        actionCtx.saveErrors(errors);
        actionCtx.setFormValid(Boolean.FALSE);

        return CONTINUE_PROCESSING;
    }

    // ------------------------------------------------------- Protected Methods

    /**
     * <p>Call the <code>validate()</code> method of the specified form bean,
     * and return the resulting <code>ActionErrors</code> object.</p>
     *
     * @param context      The context for this request
     * @param actionConfig The <code>ActionConfig</code> for this request
     * @param actionForm   The form bean for this request
     * @return ActionErrors object, if any
     */
    protected abstract ActionErrors validate(ActionContext context,
        ActionConfig actionConfig, ActionForm actionForm);
}