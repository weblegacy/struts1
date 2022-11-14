/*
 * $Id$
 *
 * Copyright 2007 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.struts.chain.commands.servlet;

import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.chain.commands.ActionCommandBase;
import org.apache.struts.Globals;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <p>Performs post-processing functions in command chain</p>
 *
 * @version $Rev$ $Date$
 * @since Struts 1.4
 */
public class ActionPostProcess extends ActionCommandBase {

    /**
     * <p>Performs additional functions after an Action or Command
     * has been called.</p>
     *
     * @param context The <code>Context</code> for the current request
     * @return <code>false</code> so that processing continues
     * @throws Exception on any error
     */
    public boolean execute(ActionContext context) throws Exception {

        ServletActionContext saContext = (ServletActionContext) context;
        HttpServletRequest request = saContext.getRequest();

        // Set flag in request object, notifying chained actions that
        // this request was already processed.
        request.setAttribute(Globals.CHAIN_KEY, Boolean.TRUE);

        // Continue chain processing
        return CONTINUE_PROCESSING;
    }
}
