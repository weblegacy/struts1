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
package org.apache.struts.dispatcher.servlet;

import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.dispatcher.AbstractMappingDispatcher;

import jakarta.servlet.http.HttpServletResponse;

/**
 * This servlet-based dispatcher uses the configuration value of the
 * <code>parameter</code> attribute from the corresponding
 * {@link org.apache.struts.action.ActionMapping} to pick the appropriate method
 * on the action. Because mapping characteristics may differ between the various
 * handlers, actions can be combined in the same class that, differ in their use
 * of method signatures, forms, and/or validation.
 * <p>
 * For example, a single action may manage a subscription process by defining
 * the following methods:
 * <ul>
 * <li><code>public void create(ActionContext context)</code></li>
 * <li><code>public void delete(ActionContext context)</code></li>
 * <li><code>public String edit(ActionContext context)</code></li>
 * <li><code>public ActionForward list(ActionContext context)</code></li>
 * <li><code>public String save(ActionContext context)</code></li>
 * </ul>
 * for which a corresponding configuration would exist:
 *
 * <pre><code>
 *  &lt;action path=&quot;/createSubscription&quot;
 *          type=&quot;org.example.SubscriptionAction&quot;
 *          dispatcher=&quot;org.apache.struts.dispatcher.servlet.ServletMappingDispatcher&quot;
 *          parameter=&quot;create&quot;&gt;
 *      &lt;forward path=&quot;/editSubscription.jsp&quot;/&gt;
 *  &lt;/action&gt;
 *
 *  &lt;action path=&quot;/deleteSubscription&quot;
 *          type=&quot;org.example.SubscriptionAction&quot;
 *          dispatcher=&quot;org.apache.struts.dispatcher.servlet.ServletMappingDispatcher&quot;
 *          parameter=&quot;delete&quot;
 *          name=&quot;subscriptionForm&quot;
 *          scope=&quot;request&quot;&gt;
 *      &lt;forward path=&quot;/deletedSubscription.jsp&quot;/&gt;
 *      &lt;forward name=&quot;input&quot; path=&quot;/subscription.jsp&quot;
 *  &lt;/action&gt;
 *
 *  &lt;action path=&quot;/editSubscription&quot;
 *          type=&quot;org.example.SubscriptionAction&quot;
 *          dispatcher=&quot;org.apache.struts.dispatcher.servlet.ServletMappingDispatcher&quot;
 *          parameter=&quot;edit&quot;&gt;
 *      &lt;forward path=&quot;/editSubscription.jsp&quot;/&gt;
 *  &lt;/action&gt;
 *
 *  &lt;action path=&quot;/listSubscriptions&quot;
 *          type=&quot;org.example.SubscriptionAction&quot;
 *          dispatcher=&quot;org.apache.struts.dispatcher.servlet.ServletMappingDispatcher&quot;
 *          parameter=&quot;list&quot;&gt;
 *      &lt;forward path=&quot;/subscriptionList.jsp&quot;/&gt;
 *  &lt;/action&gt;
 *
 *  &lt;action path=&quot;/saveSubscription&quot;
 *          type=&quot;org.example.SubscriptionAction&quot;
 *          dispatcher=&quot;org.apache.struts.dispatcher.servlet.ServletMappingDispatcher&quot;
 *          parameter=&quot;save&quot;
 *          name=&quot;subscriptionForm&quot;
 *          scope=&quot;request&quot;
 *          validate=&quot;true&quot;&gt;
 *      &lt;forward path=&quot;/savedSubscription.jsp&quot;/&gt;
 *      &lt;forward name=&quot;input&quot; path=&quot;/editSubscription.jsp&quot;
 *  &lt;/action&gt;
 * </code></pre>
 *
 * @version $Rev$
 * @since Struts 1.4
 */
public class ServletMappingDispatcher extends AbstractMappingDispatcher {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new servlet mapping dispatcher.
     */
    public ServletMappingDispatcher() {
        super(new ServletMethodResolver());
    }

    /**
     * Sends the 404 HTTP error response.
     *
     * @return always <code>null</code> since the response is handled directly
     */
    protected Object unspecified(ActionContext context) throws Exception {
        HttpServletResponse response = ((ServletActionContext) context).getResponse();
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }

}
