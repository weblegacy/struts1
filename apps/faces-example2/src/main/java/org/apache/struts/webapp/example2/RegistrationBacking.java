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


package org.apache.struts.webapp.example2;


import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.struts.apps.mailreader.dao.Subscription;
import org.apache.struts.apps.mailreader.dao.User;

import jakarta.faces.FacesException;
import jakarta.faces.context.FacesContext;


/**
 * <p>Backing bean for the <code>registration.jsp</code> page.</p>
 */

@Named
@RequestScoped
public class RegistrationBacking {


    // -------------------------------------------------------------- Properties


    // These methods exist to work around a bug in the PFD version of the
    // rendering for <h:data_table> that disallows constant values on
    // per-row command and output components
    public String getDeleteLabel() { return ("Delete"); }
    public String getEditLabel() { return ("Edit"); }


    // ----------------------------------------------------------------- Actions


    /**
     * <p>Create a new subscription.</p>
     */
    public String create() {

        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder url = base(context);
        url.append("?action=Create");
        url.append("&username=");
        User user = (User)
            context.getExternalContext().getSessionMap().get("user");
        url.append(user.getUsername());
        forward(context, url.toString());
        return (null);

    }


    /**
     * <p>Delete an existing subscription.</p>
     */
    public String delete() {

        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder url = base(context);
        url.append("?action=Delete");
        url.append("&username=");
        User user = (User)
            context.getExternalContext().getSessionMap().get("user");
        url.append(user.getUsername());
        url.append("&host=");
        Subscription subscription = (Subscription)
            context.getExternalContext().getRequestMap().get("subscription");
        url.append(subscription.getHost());
        forward(context, url.toString());
        return (null);

    }


    /**
     * <p>Edit an existing subscription.</p>
     */
    public String edit() {

        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder url = base(context);
        url.append("?action=Edit");
        url.append("&username=");
        User user = (User)
            context.getExternalContext().getSessionMap().get("user");
        url.append(user.getUsername());
        url.append("&host=");
        Subscription subscription = (Subscription)
            context.getExternalContext().getRequestMap().get("subscription");
        url.append(subscription.getHost());
        forward(context, url.toString());
        return (null);

    }


    // --------------------------------------------------------- Private Methods


    /**
     * <p>Return the context relative base URL for the "edit subscriptions"
     * action.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    private StringBuilder base(FacesContext context) {

        // FIXME - assumes extension mapping for Struts
        return (new StringBuilder("/editSubscription.do"));

    }


    /**
     * <p>Forward to the specified URL and mark this response as having
     * been completed.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param url Context-relative URL to forward to
     *
     * @exception FacesException if any error occurs
     */
    private void forward(FacesContext context, String url) {

        try {
            context.getExternalContext().dispatch(url);
        } catch (IOException e) {
            throw new FacesException(e);
        } finally {
            context.responseComplete();
        }

    }


}
