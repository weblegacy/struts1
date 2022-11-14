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

package examples.links;

import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import examples.TestBean;

/**
 * Perform any tasks and setup any data that
 * must be prepared before the form is displayed.
 *
 * @version $Rev$ $Date$
 */
public class PrepareLinksAction extends Action {
    private static final long serialVersionUID = 5540649738913671167L;

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for PrepareOptionsAction.
     */
    public PrepareLinksAction() {
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

        HashMap<String, String> parms = new HashMap<>();
        parms.put("color", "Red");
        parms.put("fruit", "Apple");
        parms.put("animal", "Rabbit");
        request.setAttribute("parms", parms);

        TestBean bean = new TestBean();
        request.setAttribute("testBean", bean);

        // Just forward to the form - no preparation required
        return mapping.findForward("success");
    }
}