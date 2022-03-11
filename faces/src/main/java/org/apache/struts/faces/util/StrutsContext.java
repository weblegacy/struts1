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

package org.apache.struts.faces.util;


import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.sql.DataSource;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.faces.Constants;
import org.apache.struts.util.MessageResources;


/**
 * <p>Context bean providing accessors for the Struts related request,
 * session, and application scope objects reated to this request.  Note
 * that this bean's methods will trigger exceptions unless there is a
 * <code>FacesContext</code> instance for this request.</p>
 */

public class StrutsContext {


    // ------------------------------------------------------ Instance Variables


    /**
     * <p>The <code>FacesContext</code> for the current request.</p>
     */
    private FacesContext fcontext =
        FacesContext.getCurrentInstance();


    /**
     * <p>The <code>ExternalContext</code> for the current request.</p>
     */
    private ExternalContext econtext =
        fcontext.getExternalContext();


    // ---------------------------------------------------------- Public Methods


    /**
     * <p>Return the <code>ActionEvent</code> for the current request
     * (if any).</p>
     */
    public ActionEvent getActionEvent() {

        return ((ActionEvent) econtext.getRequestMap().
                get(Constants.ACTION_EVENT_KEY));

    }


    /**
     * <p>Return the <code>ActionMapping</code> for the current
     * request (if any).</p>
     */
    public ActionMapping getActionMapping() {

        return ((ActionMapping) econtext.getRequestMap().
                get(Globals.MAPPING_KEY));

    }


    /**
     * <p>Return the <code>ActionMessages</code> instance containing
     * application error messages for this request (if any).</p>
     */
    public ActionMessages getActionMessages() {

        return ((ActionMessages) econtext.getRequestMap().
                get(Globals.MESSAGE_KEY));

    }


    /**
     * <p>Return the <code>ActionServlet</code> instance for this
     * web application.</p>
     */
    public ActionServlet getActionServlet() {

        return ((ActionServlet) econtext.getApplicationMap().
                get(Globals.ACTION_SERVLET_KEY));

    }


    /**
     * <p>Return <code>true</code> if a Boolean true value has been stored
     * in the request attribute indicating that this request has been
     * cancelled.</p>
     */
    public boolean isCancelled() {

        Object value = econtext.getRequestMap().get(Globals.CANCEL_KEY);
        if (value instanceof Boolean) {
            return (((Boolean) value).booleanValue());
        } else {
            return (false);
        }

    }



    /**
     * <p>Return the exception that caused one of the Struts custom tags
     * to report a JspException (if any).</p>
     */
    public Throwable getException() {

        return ((Throwable) econtext.getRequestMap().
                get(Globals.EXCEPTION_KEY));

    }


    /**
     * <p>Return the <code>ExternalContext</code> for the current request.</p>
     */
    public ExternalContext getExternalContext() {

        return (econtext);

    }


    /**
     * <p>Return the <code>FacesContext</code> for the current request.</p>
     */
    public FacesContext getFacesContext() {

        return (fcontext);

    }


    /**
     * <p>Return the <code>Locale</code> stored in the current user's
     * session (if any) for Struts based localization.</p>
     */
    public Locale getLocale() {

        if (econtext.getSession(false) != null) {
            return ((Locale) econtext.getSessionMap().
                    get(Globals.LOCALE_KEY));
        } else {
            return (null);
        }

    }


    /**
     * <p>Return the <code>MessageResources</code> instance for the
     * application module that is processing this request (if any).</p>
     */
    public MessageResources getMessageResources() {

        return ((MessageResources) econtext.getRequestMap().
                get(Globals.MESSAGES_KEY));

    }


    /**
     * <p>Return the <code>ModuleConfig</code> for the application module
     * to which this request has been assigned (if any).</p>
     */
    public ModuleConfig getModuleConfig() {

        return ((ModuleConfig) econtext.getRequestMap().
                get(Globals.MODULE_KEY));

    }


}
