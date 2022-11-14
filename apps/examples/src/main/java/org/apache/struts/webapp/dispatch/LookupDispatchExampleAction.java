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

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.extras.actions.LookupDispatchAction;

/**
 * Example LookupDispatchAction.
 *
 * @version $Rev$ $Date$
 */
public class LookupDispatchExampleAction extends LookupDispatchAction {
    private static final long serialVersionUID = 6423904873036854367L;

    private HashMap<String, String> keyMethodMap = new HashMap<>();
    private int fooCount;
    private int barCount;

    /**
     * Constructor - populate the key method map.
     */
    public LookupDispatchExampleAction() {
        keyMethodMap.put("button.foo.label", "doFoo");
        keyMethodMap.put("button.bar.label", "doBar");
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

    /**
     * Provides the mapping from resource key to method name.
     *
     * @return Resource key / method name map.
     */
    protected Map<String, String> getKeyMethodMap() {
        return keyMethodMap;
    }
}