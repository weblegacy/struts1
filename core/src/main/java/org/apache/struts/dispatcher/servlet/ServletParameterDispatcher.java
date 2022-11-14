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
import org.apache.struts.dispatcher.AbstractParameterDispatcher;

import jakarta.servlet.http.HttpServletResponse;

/**
 * This servlet-based dispatcher uses the value of the request parameter to pick
 * the appropriate method on the action.
 * <p>
 * To configure the use of this dispatcher in your configuration, create an
 * entry like below:
 * <p>
 * <code>
 * <pre>
 * &lt;action path=&quot;/saveSubscription&quot;
 *         type=&quot;org.example.SubscriptionAction&quot;
 *         dispatcher=&quot;org.apache.struts.dispatcher.servlet.ServletParameterDispatcher&quot;
 *         parameter=&quot;method&quot;/&gt;
 *         name=&quot;subscriptionForm&quot;
 *         scope=&quot;request&quot;
 *         input=&quot;/subscription.jsp&quot;
 * </pre>
 * </code>
 * <p>
 * This example will use the value of the request parameter named "method" to
 * pick the appropriate method. For example, you might have the following three
 * methods in the same action:
 *
 * <ul>
 * <li><code>public ActionForward delete(ActionMapping mapping, ActionForm form,
 * HttpServletRequest request, HttpServletResponse response) throws Exception</code></li>
 * <li><code>public void insert(ActionContext context)</code></li>
 * <li><code>public void update(ServletActionContext context)</code></li>
 * </ul>
 * and call one of the methods with a URL like this:
 * <p>
 * <code>http://localhost:8080/myapp/saveSubscription.do?method=update</code>
 *
 * @version $Rev$
 * @since Struts 1.4
 */
public class ServletParameterDispatcher extends AbstractParameterDispatcher {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new servlet parameter dispatcher.
     */
    public ServletParameterDispatcher() {
        super(new ServletMethodResolver());
    }

    /**
     * Extracts the value from the specified servlet parameter.
     *
     * @param context {@inheritDoc}
     * @param parameter the servlet parameter name
     * @return the servlet parameter value
     */
    protected String resolveParameterValue(ActionContext context, String parameter) {
        ServletActionContext servletContext = (ServletActionContext) context;
        return servletContext.getParam().get(parameter);
    }

    /**
     * Sends the 404 HTTP error response.
     *
     * @param context {@inheritDoc}
     * @return always <code>null</code> since the response is handled directly
     * @throws Exception if the error code fails to set
     */
    protected Object unspecified(ActionContext context) throws Exception {
        HttpServletResponse response = ((ServletActionContext) context).getResponse();
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }

}
