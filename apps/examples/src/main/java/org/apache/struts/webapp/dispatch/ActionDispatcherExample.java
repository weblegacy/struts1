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
package org.apache.struts.webapp.dispatch;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.extras.actions.ActionDispatcher;

/**
 * Example DispatchAction.
 *
 * @version $Rev$ $Date$
 */
public class ActionDispatcherExample extends Action {
    private static final long serialVersionUID = -5974809892927967588L;

    private ActionDispatcher dispatcher
                    = new ActionDispatcher(this,
                                ActionDispatcher.DISPATCH_FLAVOR);

    private int fooCount;
    private int barCount;

    /**
     * Execute method.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     *
     * @exception Exception if business logic throws an exception
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception {

        return dispatcher.execute(mapping, form, request, response);

    }

    /**
     * Example "foo" method.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     *
     * @exception Exception if business logic throws an exception
     */
    public ActionForward doFoo(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
        throws Exception {

        fooCount++;

        ActionMessages messages = new ActionMessages();
        messages.add("foo", new ActionMessage("count.foo.message", fooCount+""));
        saveMessages(request, messages);

        return (mapping.findForward("success"));

    }

    /**
     * Example "bar" method.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     *
     * @exception Exception if business logic throws an exception
     */
    public ActionForward doBar(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
        throws Exception {
        barCount++;

        ActionMessages messages = new ActionMessages();
        messages.add("bar", new ActionMessage("count.bar.message", barCount+""));
        saveMessages(request, messages);

        return (mapping.findForward("success"));

    }

}
