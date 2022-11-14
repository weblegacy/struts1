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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.apps.mailreader.dao.ExpiredPasswordException;
import org.apache.struts.apps.mailreader.dao.User;
import org.apache.struts.apps.mailreader.dao.UserDatabase;
import org.apache.struts.util.ModuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementation of <strong>Action</strong> that validates a user logon.
 *
 * @author Craig R. McClanahan
 * @version $Rev$ $Date$
 */

public final class LogonAction extends Action {
    private static final long serialVersionUID = 4996537708066805369L;


    // ----------------------------------------------------- Instance Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(LogonAction.class);


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
     * @exception Exception if business logic throws an exception
     */
    public ActionForward execute(ActionMapping mapping,
                 ActionForm form,
                 HttpServletRequest request,
                 HttpServletResponse response)
    throws Exception {

    // Extract attributes we will need
    User user = null;

    // Validate the request parameters specified by the user
    ActionErrors errors = new ActionErrors();
    String username = (String)
            PropertyUtils.getSimpleProperty(form, "username");
        String password = (String)
            PropertyUtils.getSimpleProperty(form, "password");
    UserDatabase database = (UserDatabase)
      servlet.getServletContext().getAttribute(Constants.DATABASE_KEY);
    if (database == null)
            errors.add(ActionErrors.GLOBAL_MESSAGE,
                       new ActionMessage("error.database.missing"));
    else {
        user = getUser(database, username);
        if ((user != null) && !user.getPassword().equals(password))
        user = null;
        if (user == null)
                errors.add(ActionErrors.GLOBAL_MESSAGE,
                           new ActionMessage("error.password.mismatch"));
    }

    // Report any errors we have discovered back to the original form
    if (!errors.isEmpty()) {
        saveErrors(request, errors);
            return (mapping.getInputForward());
    }

    // Save our logged-in user in the session
    HttpSession session = request.getSession();
    session.setAttribute(Constants.USER_KEY, user);
    LOG.debug("LogonAction: User '{}' logged on in session {}",
        user.getUsername(), session.getId());

        // Remove the obsolete form bean
    if (mapping.getAttribute() != null) {
            if ("request".equals(mapping.getScope()))
                request.removeAttribute(mapping.getAttribute());
            else
                session.removeAttribute(mapping.getAttribute());
        }

    // Forward control to the specified success URI
    return (mapping.findForward("success"));

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Look up the user, throwing an exception to simulate business logic
     * rule exceptions.
     *
     * @param database Database in which to look up the user
     * @param username Username specified on the logon form
     *
     * @exception AppException if a business logic rule is violated
     */
    public User getUser(UserDatabase database, String username)
        throws ModuleException {

        // Force an ArithmeticException which can be handled explicitly
        if ("arithmetic".equals(username)) {
            throw new ArithmeticException();
        }

        // Force an application-specific exception which can be handled
        if ("expired".equals(username)) {
            throw new ExpiredPasswordException(username);
        }

        // Look up and return the specified user
        return (database.findUser(username));
    }
}