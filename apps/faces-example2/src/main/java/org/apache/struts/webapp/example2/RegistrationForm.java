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


import jakarta.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;


/**
 * Form bean for the user registration page.  This form has the following
 * fields, with default values in square brackets:
 * <ul>
 * <li><b>action</b> - The maintenance action we are performing (Create,
 *     Delete, or Edit).
 * <li><b>fromAddress</b> - The EMAIL address of the sender, to be included
 *     on sent messages.  [REQUIRED]
 * <li><b>fullName</b> - The full name of the sender, to be included on
 *     sent messages.  [REQUIRED]
 * <li><b>password</b> - The password used by this user to log on.
 * <li><b>password2</b> - The confirmation password, which must match
 *     the password when changing or setting.
 * <li><b>replyToAddress</b> - The "Reply-To" address to be included on
 *     sent messages.  [Same as from address]
 * <li><b>username</b> - The registered username, which must be unique.
 *     [REQUIRED]
 * </ul>
 *
 * @author Craig R. McClanahan
 * @version $Rev$ $Date$
 */

public final class RegistrationForm extends ValidatorForm  {
    private static final long serialVersionUID = -5478229369238095131L;


    // ----------------------------------------------------- Instance Variables


    /**
     * The maintenance action we are performing (Create or Edit).
     */
    private String action = "Create";


    /**
     * The from address.
     */
    private String fromAddress = null;


    /**
     * The full name.
     */
    private String fullName = null;


    /**
     * The password.
     */
    private String password = null;


    /**
     * The confirmation password.
     */
    private String password2 = null;


    /**
     * The reply to address.
     */
    private String replyToAddress = null;



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
     * Return the from address.
     */
    public String getFromAddress() {

    return (this.fromAddress);

    }


    /**
     * Set the from address.
     *
     * @param fromAddress The new from address
     */
    public void setFromAddress(String fromAddress) {

        this.fromAddress = fromAddress;

    }


    /**
     * Return the full name.
     */
    public String getFullName() {

    return (this.fullName);

    }


    /**
     * Set the full name.
     *
     * @param fullName The new full name
     */
    public void setFullName(String fullName) {

        this.fullName = fullName;

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
     * Return the confirmation password.
     */
    public String getPassword2() {

    return (this.password2);

    }


    /**
     * Set the confirmation password.
     *
     * @param password2 The new confirmation password
     */
    public void setPassword2(String password2) {

        this.password2 = password2;

    }


    /**
     * Return the reply to address.
     */
    public String getReplyToAddress() {

    return (this.replyToAddress);

    }


    /**
     * Set the reply to address.
     *
     * @param replyToAddress The new reply to address
     */
    public void setReplyToAddress(String replyToAddress) {

        this.replyToAddress = replyToAddress;

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
        this.fromAddress = null;
        this.fullName = null;
        this.password = null;
        this.password2 = null;
        this.replyToAddress = null;
        this.username = null;

    }


    /**
     * Validate the properties that have been set from this HTTP request,
     * and return an <code>ActionErrors</code> object that encapsulates any
     * validation errors that have been found.  If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no
     * recorded error messages.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {

        // Perform validator framework validations
        ActionErrors errors = super.validate(mapping, request);

        // Only need crossfield validations here
        if (((password == null) && (password2 != null)) ||
            ((password != null) && (password2 == null)) ||
            ((password != null) && (password2 != null) &&
             !password.equals(password2))) {
            errors.add("password2",
                       new ActionMessage("error.password.match"));
        }
        return errors;

    }


}
