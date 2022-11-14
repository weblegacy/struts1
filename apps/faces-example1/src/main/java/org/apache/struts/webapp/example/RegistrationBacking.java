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


package org.apache.struts.webapp.example;


import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.struts.apps.mailreader.dao.Subscription;
import org.apache.struts.apps.mailreader.dao.User;
import org.apache.struts.apps.mailreader.dao.UserDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.faces.component.UIData;
import jakarta.faces.context.FacesContext;


/**
 * <p>Backing bean for the <code>registration.jsp</code> page.</p>
 */

@Named
@RequestScoped
public class RegistrationBacking extends AbstractBacking {


    // -------------------------------------------------------- Static Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(RegistrationBacking.class);


    // -------------------------------------------------------------- Properties


    private UIData table = null;


    /**
     * <p>Return the <code>UIData</code> instance we are bound to.</p>
     */
    public UIData getTable() {

        return (this.table);

    }


    /**
     * <p>Set the <code>UIData</code> instance we are bound to.</p>
     *
     * @param table The <code>UIData</code> instance
     */
    public void setTable(UIData table) {

        this.table = table;

    }



    // ----------------------------------------------------------------- Actions


    /**
     * <p>Create a new subscription.</p>
     */
    public String create() {

        LOG.debug("create()");
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder url = subscription(context);
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

        LOG.debug("delete()");
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder url = subscription(context);
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

        LOG.debug("edit()");
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder url = subscription(context);
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


    /**
     * <p>Update the subscriptions to reflect any revisions to the
     * <code>type</code> and <code>autoConnect</code> properties.</p>
     */
    public String update() {

        LOG.debug("update()");

        FacesContext context = FacesContext.getCurrentInstance();

        // Updates went directly to the underlying rows, so all we need to do
        // is save the database
        try {
            UserDatabase database = (UserDatabase)
                context.getExternalContext().getApplicationMap().
                get(Constants.DATABASE_KEY);
            database.save();
        } catch (Exception e) {
            LOG.error("Database save", e);
        }

        // Forward back to the edit registration page
        StringBuilder sb = registration(context);
        sb.append("?action=Edit");
        forward(context, sb.toString());
        return (null);

    }
}