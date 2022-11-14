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


import jakarta.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * Form bean for the user profile page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>action</b> - The maintenance action we are performing (Create, Delete,
 *     or Edit).
 * <li><b>host</b> - The mail host for this subscription.  [REQUIRED]
 * <li><b>password</b> - The password for this subscription.  [REQUIRED]
 * <li><b>type</b> - The subscription type (imap,pop3)
       for this subscription.  [REQUIRED]
 * <li><b>username</b> - The username of this subscription.  [REQUIRED]
 * </ul>
 *
 * @author Craig R. McClanahan
 * @version $Rev$ $Date$
 */

public final class SubscriptionForm extends ActionForm  {
    private static final long serialVersionUID = 626639455490589636L;


    // --------------------------------------------------- Instance Variables


    /**
     * The maintenance action we are performing (Create or Edit).
     */
    private String action = "Create";


    /**
     * Should we auto-connect at startup time?
     */
    private boolean autoConnect = false;


    /**
     * The host name.
     */
    private String host = null;


    /**
     * The password.
     */
    private String password = null;


    /**
     * The subscription type.
     */
    private String type = null;


    /**
     * The username.
     */
    private String username = null;


    // ----------------------------------------------------------- Properties


    /**
     * Return the maintenance action.
     */
    public String getAction() {

    return (this.action);

    }


    /**
     * Set the maintenance action.
     *
     * @param action The new maintenance action.
     */
    public void setAction(String action) {

        this.action = action;

    }


    /**
     * Return the auto-connect flag.
     */
    public boolean getAutoConnect() {

        return (this.autoConnect);

    }


    /**
     * Set the auto-connect flag.
     *
     * @param autoConnect The new auto-connect flag
     */
    public void setAutoConnect(boolean autoConnect) {

        this.autoConnect = autoConnect;
    }


    /**
     * Return the host name.
     */
    public String getHost() {

    return (this.host);

    }


    /**
     * Set the host name.
     *
     * @param host The host name
     */
    public void setHost(String host) {

        this.host = host;

    }


    /**
     * Return the password.
     */
    public String getPassword() {

    return (this.password);

    }


    /**
     * Set the password.
     *
     * @param password The new password
     */
    public void setPassword(String password) {

        this.password = password;

    }


    /**
     * Return the subscription type.
     */
    public String getType() {

    return (this.type);

    }


    /**
     * Set the subscription type.
     *
     * @param type The subscription type
     */
    public void setType(String type) {

        this.type = type;

    }


    /**
     * Return the username.
     */
    public String getUsername() {

    return (this.username);

    }


    /**
     * Set the username.
     *
     * @param username The new username
     */
    public void setUsername(String username) {

        this.username = username;

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {

        this.action = "Create";
        this.autoConnect = false;
        this.host = null;
        this.password = null;
        this.type = null;
        this.username = null;

    }


    /**
     * Validate the properties that have been set from this HTTP request,
     * and return an <code>ActionMessages</code> object that encapsulates any
     * validation errors that have been found.  If no errors are found, return
     * <code>null</code> or an <code>ActionMessages</code> object with no
     * recorded error messages.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();

    if ((host == null) || (host.length() < 1))
            errors.add("host",
                       new ActionMessage("error.host.required"));
    if ((username == null) || (username.length() < 1))
            errors.add("username",
                       new ActionMessage("error.username.required"));
    if ((password == null) || (password.length() < 1))
            errors.add("password",
                       new ActionMessage("error.password.required"));
    if ((type == null) || (type.length() < 1))
            errors.add("type",
                       new ActionMessage("error.type.required"));
    else if (!"imap".equals(type) && !"pop3".equals(type))
            errors.add("type",
                       new ActionMessage("error.type.invalid", type));

    return (errors);

    }


    /**
     * <p>Return a string representation of this form bean.</p>
     */
    public String toString() {

        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(",action=");
        sb.append(action);
        sb.append(",autoConnect=");
        sb.append(autoConnect);
        sb.append(",host=");
        sb.append(host);
        sb.append(",password=");
        sb.append(password);
        sb.append(",type=");
        sb.append(type);
        sb.append(",username=");
        sb.append(username);
        return (sb.toString());

    }


}

