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

import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.dispatcher.AbstractEventMappingDispatcher;

import jakarta.servlet.http.HttpServletRequest;

/**
 * This servlet-based dispatcher chooses the target method based on the
 * <code>parameter</code> attribute of the {@link ActionMapping} that matches
 * a submission parameter. This is useful for developers who prefer to use many
 * submit buttons, images, or submit links on a single form and whose related
 * actions exist in a single action.
 * <p>
 * To configure the use of this action in your configuration, create an entry
 * like this:
 *
 * <pre><code>
 *   &lt;action path=&quot;/saveSubscription&quot;
 *           type=&quot;org.example.SubscriptionAction&quot;
 *           dispatcher=&quot;org.apache.struts.dispatcher.servlet.ServletEventMappingDispatcher&quot;/&gt;
 *           parameter=&quot;save,back,recalc=recalculate,default=save&quot;/&gt;
 *           name=&quot;subscriptionForm&quot;
 *           scope=&quot;request&quot;
 *           input=&quot;/subscription.jsp&quot;
 * </code></pre>
 *
 * where <code>parameter</code> contains three possible methods and one
 * default method if nothing matches (such as the user pressing the enter key).
 *
 * @version $Rev$
 * @since Struts 1.4
 */
public class ServletEventMappingDispatcher extends AbstractEventMappingDispatcher {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new servlet event mapping dispatcher.
     */
    public ServletEventMappingDispatcher() {
        super(new ServletMethodResolver());
    }

    /**
     * If the method key exists as a standalone parameter or with the image
     * suffixes (.x/.y), the method name has been found.
     */
    protected boolean isSubmissionParameter(ActionContext context, String methodKey) {
        HttpServletRequest request = ((ServletActionContext) context).getRequest();
        return (request.getParameter(methodKey) != null) || (request.getParameter(methodKey + ".x") != null);
    }

}
