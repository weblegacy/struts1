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
import org.apache.struts.apps.mailreader.dao.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementation of <strong>Action</strong> that populates an instance of
 * <code>RegistrationForm</code> from the profile of the currently logged on
 * User (if any).
 *
 * @author Craig R. McClanahan
 * @version $Rev$ $Date$
 */

public final class EditRegistrationAction extends Action {
    private static final long serialVersionUID = 999725197962195314L;


    // ----------------------------------------------------- Instance Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(EditRegistrationAction.class);


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

    // Extract attributes we will need
    HttpSession session = request.getSession();
    String action = request.getParameter("action");
    if (action == null)
        action = "Create";
    LOG.debug("EditRegistrationAction:  Processing {} action",
        action);

    // Is there a currently logged on user?
    User user = null;
    if (!"Create".equals(action)) {
        user = (User) session.getAttribute(Constants.USER_KEY);
        if (user == null) {
            LOG.debug(" User is not logged on in session {}",
                session.getId());
        return (mapping.findForward("logon"));
        }
    }

    // Populate the user registration form
    if (form == null) {
        LOG.trace(" Creating new RegistrationForm bean under key {}",
            mapping.getAttribute());
        form = new RegistrationForm();
            if ("request".equals(mapping.getScope()))
                request.setAttribute(mapping.getAttribute(), form);
            else
                session.setAttribute(mapping.getAttribute(), form);
    }
    RegistrationForm regform = (RegistrationForm) form;
    if (user != null) {
            LOG.trace(" Populating form from {}", user);
            try {
                PropertyUtils.copyProperties(regform, user);
                regform.setAction(action);
                regform.setPassword(null);
                regform.setPassword2(null);
            } catch (InvocationTargetException e) {
                Throwable t = e.getTargetException();
                if (t == null)
                    t = e;
                LOG.error("RegistrationForm.populate", t);
                throw new ServletException("RegistrationForm.populate", t);
            } catch (Throwable t) {
                LOG.error("RegistrationForm.populate", t);
                throw new ServletException("RegistrationForm.populate", t);
            }
    }

        // Set a transactional control token to prevent double posting
        LOG.trace(" Setting transactional control token");
        saveToken(request);

    // Forward control to the edit user registration page
        LOG.trace(" Forwarding to 'success' page");
    return (mapping.findForward("success"));

    }
}