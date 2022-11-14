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

package examples.multibox;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Perform any tasks and setup any data that
 * must be prepared before the form is displayed.
 *
 * @version $Rev$ $Date$
 */
public class PrepareMultiboxAction extends Action {
    private static final long serialVersionUID = 5055393276157457523L;

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for PrepareOptionsAction.
     */
    public PrepareMultiboxAction() {
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
     * @exception Exception if an exception occurs
     *
     * @return the ActionForward to forward control to
     */
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

        System.out.println("Prepare MultiboxActionForm ....");

        /*
         * Prepare a String array of color names used to generate
         * checkboxes using html:multibox tags in the JSP page.
         */
        String[] colors =
            { "Red", "Orange", "Yellow", "Green", "Blue", "Indigo", "Violet" };
        request.setAttribute("colors", colors);

        /*
         * Set default checkbox values.
         */
        String[] defaultFruits = { "Orange", "Banana", "Apple" };
        String[] defaultColors = { "Orange", "Yellow" };
        MultiboxActionForm multiboxForm = (MultiboxActionForm) form;
        multiboxForm.setFruits(defaultFruits);
        multiboxForm.setColors(defaultColors);

        // Return an ActionForward to the form
        return mapping.findForward("success");
    }
}