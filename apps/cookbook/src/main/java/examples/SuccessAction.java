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

package examples;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>An Action that forwards control via a "success" ActionFoward.</p>
 *
 * <p>A recommended strategy is that view pages should link only to Actions and
 * never directly to other views. In situations where the view does not require
 * any preparation you can use a SuccessAction, specifying the view as an
 * ActionForward named "success" in your action mapping.</p>
 *
 * <p>e.g. If you configure an ActionMapping in struts-config.xml like this:</p>
 *
 * <pre>
 *    &lt;action path="/prepareView"
 *            type="examples.SuccessAction"&gt;
 *        &lt;forward name="success" path="/jsp/View.jsp"/&gt;
 *    &lt;/action&gt;
 * </pre>
 *
 * <p>You could create a link to the view (via the Action) as follows:</p>
 *
 * <pre>
 *     &lt;html:link action="/prepareView"&gt;Display 'view' page&lt;/html:link&gt;
 * </pre>
 *
 * <p>If you later change your application such that the view page requires some
 * initialization you can change a single ActionMapping definition in
 * struts-config.xml rather than lots of link definitions in many JSPs.</p>
 *
 * @version $Rev$ $Date$
 */
public class SuccessAction extends Action {
    private static final long serialVersionUID = 8464922680951404772L;

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for SuccessAction.
     */
    public SuccessAction() {
        super();
    }

    // ---------------------------------------------------------- Action Methods

    /**
     * Returns the <code>ActionForward</code> named "success" if one is
     * configured or <code>null</code>if it cannot be found.
     *
     * Searches first for a local forward, then a global forward.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception Exception if mapping.findForward throws an Exception
     *
     * @return the "success" ActionForward, or null if it cannot be found
     */
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

        return mapping.findForward("success");
    }
}