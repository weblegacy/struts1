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

package org.apache.struts.faces.taglib;


import java.util.Locale;

import javax.faces.context.FacesContext;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.faces.util.MessagesMap;
import org.apache.struts.util.MessageResources;


/**
 * <p>Tag that exposes a specified <code>MessageResources</code> instance
 * as <code>Map</code>, so that the embedded messages may be retrieved via
 * value binding expressions.</p>
 */

public class LoadMessagesTag extends TagSupport {


    // ---------------------------------------------------------- Tag Attributes


    /**
     * <p>The name of the <code>MessageResources</code> to expose, or
     * <code>null</code> for the default <code>MessageResources</code>
     * for this application module.</p>
     */
    private String messages = null;
    public void setMessages(String messages) {
        this.messages = messages;
    }


    /**
     * <p>The request attribute key under which a <code>Map</code>
     * will be exposed.</p>
     */
    private String var = null;
    public void setVar(String var) {
        this.var = var;
    }


    // ------------------------------------------------------------- Tag Methods


    /**
     * <p>Expose a <code>Map</code> wrapping the specified
     * <code>MessageResources</code> instance, for the <code>Locale</code>
     * specified in the view root component of the current view.</p>
     */
    public int doStartTag() {

        // Acquire the Locale to be wrapped
        Locale locale =
            FacesContext.getCurrentInstance().getViewRoot().getLocale();

        // Acquire the MessageResources to be wrapped
        MessageResources messages = null;
        if (this.messages == null) {
            messages = (MessageResources)
                pageContext.getAttribute(Globals.MESSAGES_KEY,
                                         PageContext.REQUEST_SCOPE);
            if (messages == null) {
                messages = (MessageResources)
                    pageContext.getAttribute(Globals.MESSAGES_KEY,
                                             PageContext.APPLICATION_SCOPE);
            }
        } else {
            messages = (MessageResources)
                pageContext.getAttribute(this.messages,
                                         PageContext.APPLICATION_SCOPE);
        }

        // Expose a Map instance under the specified request attribute key
        pageContext.setAttribute(var,
                                 new MessagesMap(messages, locale),
                                 PageContext.REQUEST_SCOPE);

        // Skip the body of this tag (if any)
        return (SKIP_BODY);

    }


    /**
     * <p>Release any resources allocated by this tag instance.</p>
     */
    public void release() {

        this.messages = null;
        this.var = null;

    }


}
