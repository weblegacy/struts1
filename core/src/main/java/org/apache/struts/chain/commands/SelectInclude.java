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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Select and cache the include for this <code>ActionConfig</code> if
 * specified.</p>
 *
 * @version $Rev$ $Date: 2005-06-04 10:58:46 -0400 (Sat, 04 Jun 2005)
 *          $
 */
public class SelectInclude extends ActionCommandBase {
    // ------------------------------------------------------ Instance Variables

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(SelectInclude.class);

    // ---------------------------------------------------------- Public Methods

    /**
     * <p>Select and cache the include uri for this <code>ActionConfig</code>
     * if specified.</p>
     *
     * @param actionCtx The <code>Context</code> for the current request
     * @return <code>false</code> so that processing continues
     * @throws Exception on any error
     */
    public boolean execute(ActionContext actionCtx)
        throws Exception {
        // Acquire configuration objects that we need
        ActionConfig actionConfig = actionCtx.getActionConfig();

        // Cache an include uri if found
        String include = actionConfig.getInclude();

        if (include != null) {
            log.debug("Including {}", include);

            actionCtx.setInclude(include);
        }

        return CONTINUE_PROCESSING;
    }
}