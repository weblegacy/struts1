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


import java.lang.reflect.InvocationTargetException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.apps.mailreader.dao.User;
import org.apache.struts.apps.mailreader.dao.UserDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementation of <strong>Action</strong> that validates and creates or
 * updates the user registration information entered by the user.  If a new
 * registration is created, the user is also implicitly logged on.
 *
 * @author Craig R. McClanahan
 * @version $Rev$ $Date$
 */

public final class SaveRegistrationAction extends Action {
    private static final long serialVersionUID = -8108970888606534899L;


    // ----------------------------------------------------- Instance Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(SaveRegistrationAction.class);


    // --------------------------------------------------------- Public Methods


    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception Exception if the application business logic throws
     *  an exception
     */
    public ActionForward execute(ActionMapping mapping,
                 ActionForm form,
                 HttpServletRequest request,
                 HttpServletResponse response)
    throws Exception {

    // Extract attributes and parameters we will need
    HttpSession session = request.getSession();
    RegistrationForm regform = (RegistrationForm) form;
    String action = regform.getAction();
    if (action == null) {
        action = "Create";
    }
    UserDatabase database = (UserDatabase)
    servlet.getServletContext().getAttribute(Constants.DATABASE_KEY);
    LOG.debug("SaveRegistrationAction:  Processing {} action",
        action);

    // Is there a currently logged on user (unless creating)?
    User user = (User) session.getAttribute(Constants.USER_KEY);
    if (!"Create".equals(action) && (user == null)) {
        LOG.trace(" User is not logged on in session {}",
            session.getId());
        return (mapping.findForward("logon"));
    }

    // Was this transaction cancelled?
    if (isCancelled(request)) {
        LOG.trace(" Transaction '{}' was cancelled", action);
        session.removeAttribute(Constants.SUBSCRIPTION_KEY);
        return (mapping.findForward("failure"));
    }

        // Validate the transactional control token
    ActionMessages errors = new ActionMessages();
    LOG.trace(" Checking transactional control token");
    if (!isTokenValid(request)) {
        errors.add(ActionMessages.GLOBAL_MESSAGE,
            new ActionMessage("error.transaction.token"));
    }
    resetToken(request);

    // Validate the request parameters specified by the user
    LOG.trace(" Performing extra validations");
    String value = null;
    value = regform.getUsername();
    if (("Create".equals(action)) &&
            (database.findUser(value) != null)) {
            errors.add("username",
                       new ActionMessage("error.username.unique",
                                       regform.getUsername()));
        }
    if ("Create".equals(action)) {
        value = regform.getPassword();
        if ((value == null) || (value.length() <1)) {
                errors.add("password",
                           new ActionMessage("error.password.required"));
            }
        value = regform.getPassword2();
        if ((value == null) || (value.length() < 1)) {
                errors.add("password2",
                           new ActionMessage("error.password2.required"));
            }
    }

    // Report any errors we have discovered back to the original form
    if (!errors.isEmpty()) {
        saveErrors(request, errors);
            saveToken(request);
            return (mapping.getInputForward());
    }

    // Update the user's persistent profile information
        try {
            if ("Create".equals(action)) {
                user = database.createUser(regform.getUsername());
            }
            String oldPassword = user.getPassword();
            PropertyUtils.copyProperties(user, regform);
            if ((regform.getPassword() == null) ||
                (regform.getPassword().length() < 1)) {
                user.setPassword(oldPassword);
            }
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if (t == null) {
                t = e;
            }
            LOG.error("Registration.populate", t);
            throw new ServletException("Registration.populate", t);
        } catch (Throwable t) {
            LOG.error("Registration.populate", t);
            throw new ServletException("Subscription.populate", t);
        }

        try {
            database.save();
        } catch (Exception e) {
            LOG.error("Database save", e);
        }

    // Log the user in if appropriate
    if ("Create".equals(action)) {
        session.setAttribute(Constants.USER_KEY, user);
        LOG.trace(" User '{}' logged on in session {}",
            user.getUsername(), session.getId());
    }

    // Remove the obsolete form bean
    if (mapping.getAttribute() != null) {
            if ("request".equals(mapping.getScope()))
                request.removeAttribute(mapping.getAttribute());
            else
                session.removeAttribute(mapping.getAttribute());
        }

    // Forward control to the specified success URI
    LOG.trace(" Forwarding to success page");
    return (mapping.findForward("success"));

    }
}