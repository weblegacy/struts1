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

package examples.token;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * Retrieve and process data from the submitted form
 *
 * @version $Rev$ $Date$
 */
public class ProcessTokenAction extends Action {
    private static final long serialVersionUID = -5017052903191285282L;

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for ProcessOptionsAction.
     */
    public ProcessTokenAction() {
        super();
    }

    // ---------------------------------------------------------- Action Methods

    /**
     * Process the request and return an <code>ActionForward</code> instance
     * describing where and how control should be forwarded, or
     * <code>null</code>if the response has already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception Exception if the application logic throws an exception
     *
     * @return the ActionForward for the next view
     */
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

        // If user pressed 'Cancel' button,
        // return to home page
        if (isCancelled(request)) {
            return mapping.findForward("home");
        }

        ActionErrors errors = new ActionErrors();

        // Prevent unintentional duplication submissions by checking
        // that we have not received this token previously
        if (!isTokenValid(request)) {
            errors.add(
                ActionMessages.GLOBAL_MESSAGE,
                new ActionMessage("errors.token"));
        }
        resetToken(request);

        // Report any errors we have discovered back to the original form
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            saveToken(request);
            return (mapping.getInputForward());
        }

        // Forward to result page
        return mapping.findForward("success");
    }
}