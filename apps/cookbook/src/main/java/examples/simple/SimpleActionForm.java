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

package examples.simple;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * A simple ActionForm
 *
 * @version $Rev$ $Date$
 */
public class SimpleActionForm extends ActionForm {
    private static final long serialVersionUID = 4189849944389346127L;

    // ------------------------------------------------------ Instance Variables

    /** Name */
    private String name = null;

    /** Secret */
    private String secret = null;

    /** Color */
    private String color = null;

    /** Confirm */
    private boolean confirm = false;

    /** Rating */
    private String rating = null;

    /** Message */
    private String message = null;

    /** Hidden */
    private String hidden = null;

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for MultiboxActionForm.
     */
    public SimpleActionForm() {
        super();
    }

    // ---------------------------------------------------------- Public Methods

    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {

        this.name = null;
        this.secret = null;
        this.color = null;
        this.confirm = false;
        this.rating = null;
        this.message = null;
        this.hidden = null;

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
     *
     * @return ActionMessages if any validation errors occurred
     */
    public ActionErrors validate(
        ActionMapping mapping,
        HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();

        // Name must be entered
        if ((name == null) || (name.length() < 1)) {
            errors.add("name", new ActionMessage("errors.name.required"));
        }

        // Secret Phrase must be entered
        if ((secret == null) || (secret.length() < 1)) {
            errors.add("secret", new ActionMessage("errors.secret.required"));
        }

        return (errors);

    }

    // -------------------------------------------------------------- Properties

    /**
     * Returns the color.
     * @return String
     */
    public String getColor() {
        return color;
    }

    /**
     * Returns the confirm.
     * @return boolean
     */
    public boolean getConfirm() {
        return confirm;
    }

    /**
     * Returns the hidden.
     * @return String
     */
    public String getHidden() {
        return hidden;
    }

    /**
     * Returns the message.
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the name.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the rating.
     * @return String
     */
    public String getRating() {
        return rating;
    }

    /**
     * Returns the secret.
     * @return String
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Sets the color.
     * @param color The color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Sets the confirm.
     * @param confirm The confirm to set
     */
    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    /**
     * Sets the hidden.
     * @param hidden The hidden to set
     */
    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    /**
     * Sets the message.
     * @param message The message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the rating.
     * @param rating The rating to set
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * Sets the secret.
     * @param secret The secret to set
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

}
