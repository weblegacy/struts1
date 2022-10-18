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

import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.FormBeanConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Create (if necessary) and cache a form bean for this request.</p>
 *
 * @version $Id$
 */
public class CreateActionForm extends ActionCommandBase {
    // ------------------------------------------------------ Instance Variables

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(CreateActionForm.class);

    // ---------------------------------------------------------- Public Methods

    /**
     * <p>Create (if necessary) and cache a form bean for this request.</p>
     *
     * @param actionCtx The <code>Context</code> for the current request
     * @return <code>false</code> so that processing continues
     * @throws Exception on any error
     */
    public boolean execute(ActionContext actionCtx)
        throws Exception {
        // Is there a form bean associated with this ActionConfig?
        ActionConfig actionConfig = actionCtx.getActionConfig();
        String name = actionConfig.getName();

        if (name == null) {
            actionCtx.setActionForm(null);
            return CONTINUE_PROCESSING;
        }

        log.trace("Look up form-bean {}", name);

        // Look up the corresponding FormBeanConfig (if any)
        FormBeanConfig formBeanConfig =
            actionConfig.getModuleConfig().findFormBeanConfig(name);

        if (formBeanConfig == null) {
            log.warn("No FormBeanConfig found in module {} under name {}",
                actionConfig.getModuleConfig().getPrefix(), name);
            actionCtx.setActionForm(null);
            return CONTINUE_PROCESSING;
        }

        Map<String, Object> scope = actionCtx.getScope(actionConfig.getScope());

        ActionForm instance;

        instance = (ActionForm) scope.get(actionConfig.getAttribute());

        // Can we recycle the existing instance (if any)?
        if (!formBeanConfig.canReuse(instance)) {
            instance = formBeanConfig.createActionForm(actionCtx);
        }

        // TODO: Remove ServletActionContext when ActionForm no longer
        //  directly depends on ActionServlet
        if (actionCtx instanceof ServletActionContext) {
            // The servlet property of ActionForm is transient, so
            // ActionForms which are restored from a serialized state
            // need to have their servlet restored.
            ServletActionContext sac = (ServletActionContext) actionCtx;

            instance.setServlet(sac.getActionServlet());
        }

        actionCtx.setActionForm(instance);

        scope.put(actionConfig.getAttribute(), instance);

        return CONTINUE_PROCESSING;
    }
}