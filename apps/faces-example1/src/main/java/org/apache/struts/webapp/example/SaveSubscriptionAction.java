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
import org.apache.struts.apps.mailreader.dao.Subscription;
import org.apache.struts.apps.mailreader.dao.User;
import org.apache.struts.apps.mailreader.dao.UserDatabase;
import org.apache.struts.util.MessageResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementation of <strong>Action</strong> that validates and creates or
 * updates the mail subscription entered by the user.
 *
 * @author Craig R. McClanahan
 * @version $Rev$ $Date$
 */

public final class SaveSubscriptionAction extends Action {
    private static final long serialVersionUID = 1776213610229977134L;


    // ----------------------------------------------------- Instance Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(SaveSubscriptionAction.class);


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
    MessageResources messages = getResources(request);
    HttpSession session = request.getSession();
    SubscriptionForm subform = (SubscriptionForm) form;
    String action = subform.getAction();
    if (action == null) {
        action = "?";
    }
    LOG.debug("SaveSubscriptionAction:  Processing {} action",
        action);

    // Is there a currently logged on user?
    User user = (User) session.getAttribute(Constants.USER_KEY);
    if (user == null) {
        LOG.trace(" User is not logged on in session {}",
            session.getId());
        return (mapping.findForward("logon"));
    }

    // Was this transaction cancelled?
    if (isCancelled(request)) {
        LOG.trace(" Transaction '{}' was cancelled",
            action);
        session.removeAttribute(Constants.SUBSCRIPTION_KEY);
        return (mapping.findForward("success"));
    }

    // Is there a related Subscription object?
    Subscription subscription =
      (Subscription) session.getAttribute(Constants.SUBSCRIPTION_KEY);
    if ("Create".equals(action)) {
        LOG.trace(" Creating subscription for mail server '{}'",
            subform.getHost());
        subscription =
            user.createSubscription(subform.getHost());
    }
    if (subscription == null) {
        LOG.trace(" Missing subscription for user '{}'",
            user.getUsername());
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                           messages.getMessage("error.noSubscription"));
        return (null);
    }

    // Was this transaction a Delete?
    if (action.equals("Delete")) {
        LOG.trace(" Deleting mail server '{}' for user '{}'",
            subscription.getHost(), user.getUsername());
        user.removeSubscription(subscription);
        session.removeAttribute(Constants.SUBSCRIPTION_KEY);
        try {
            UserDatabase database = (UserDatabase)
                servlet.getServletContext().
                getAttribute(Constants.DATABASE_KEY);
            database.save();
        } catch (Exception e) {
            LOG.error("Database save", e);
        }
        return (mapping.findForward("success"));
    }

    // All required validations were done by the form itself

    // Update the persistent subscription information
        LOG.trace(" Populating database from form bean");
        try {
            PropertyUtils.copyProperties(subscription, subform);
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if (t == null)
                t = e;
            LOG.error("Subscription.populate", t);
            throw new ServletException("Subscription.populate", t);
        } catch (Throwable t) {
            LOG.error("Subscription.populate", t);
            throw new ServletException("Subscription.populate", t);
        }

        try {
            UserDatabase database = (UserDatabase)
                servlet.getServletContext().
                getAttribute(Constants.DATABASE_KEY);
            database.save();
        } catch (Exception e) {
            LOG.error("Database save", e);
        }

    // Remove the obsolete form bean and current subscription
    if (mapping.getAttribute() != null) {
            if ("request".equals(mapping.getScope()))
                request.removeAttribute(mapping.getAttribute());
            else
                session.removeAttribute(mapping.getAttribute());
        }
    session.removeAttribute(Constants.SUBSCRIPTION_KEY);

    // Forward control to the specified success URI
    LOG.trace(" Forwarding to success page");
    return (mapping.findForward("success"));

    }
}