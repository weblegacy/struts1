/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.struts.apps.mailreader.actions;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.apps.mailreader.Constants;
import org.apache.struts.apps.mailreader.dao.Subscription;
import org.apache.struts.apps.mailreader.dao.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 * Provide an Edit method for retrieving an existing subscription,
 * and a Save method for updating or inserting a subscription.
 * </p>
 */
public final class SubscriptionAction extends BaseAction {

    // --- Public Constants --

    /**
     * <p>
     * Name of autoConnect field ["autoConnect"].
     * </p>
     */
    public final static String AUTO_CONNECT = "autoConnect";

    /**
     * <p>
     * Name of host field ["host"].
     * </p>
     */
    public final static String HOST = "host";

    /**
     * <p>
     * Name of type field ["type"].
     * </p>
     */
    public final static String TYPE = "type";

    // ---- Private Methods ----

    final String LOG_SUBSCRIPTION_POPULATE = "SubscriptionForm.populate";

    /**
     * <p>
     * Obtain subscription matching host for the given User,
     * or return null if not found.
     * </p>
     *
     * @param user Our User object
     * @param host The name of the mail server host
     * @return The matching Subscription or null
     */
    private Subscription doFindSubscription(User user, String host) {

        Subscription subscription;

        try {
            subscription = user.findSubscription(host);
        }
        catch (NullPointerException e) {
            subscription = null;
        }

        if ((subscription == null) && (log.isTraceEnabled())) {
            log.trace(
                    " No subscription for user "
                            + user.getUsername()
                            + " and host "
                            + host);
        }

        return subscription;
    }

    /**
     * <p>
     * Helper method to populate the Subscription object from the input form.
     * </p>
     *
     * @param subscription User object to populate
     * @param form         Form with incoming values
     * @throws ServletException On any error
     */
    private void doPopulate(Subscription subscription, ActionForm form)
            throws ServletException {

        if (log.isTraceEnabled()) {
            log.trace(Constants.LOG_POPULATE_SUBSCRIPTION + subscription);
        }

        try {
            PropertyUtils.copyProperties(subscription, form);
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if (t == null) {
                t = e;
            }
            log.error(LOG_SUBSCRIPTION_POPULATE, t);
            throw new ServletException(LOG_SUBSCRIPTION_POPULATE, t);
        } catch (Throwable t) {
            log.error(LOG_SUBSCRIPTION_POPULATE, t);
            throw new ServletException(LOG_SUBSCRIPTION_POPULATE, t);
        }
    }

    /**
     * <p>
     * Helper method to populate the input form from the Subscription object.
     * </p>
     *
     * @param subscription User object to populate
     * @param form         Form with incoming values
     * @throws ServletException On any error
     */
    private void doPopulate(ActionForm form, Subscription subscription)
            throws ServletException {

        final String title = Constants.EDIT;

        if (log.isTraceEnabled()) {
            log.trace(Constants.LOG_POPULATE_FORM + subscription.getHost());
        }

        try {
            PropertyUtils.copyProperties(form, subscription);
            doSet(form, TASK, title);
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if (t == null) {
                t = e;
            }
            log.error(LOG_SUBSCRIPTION_POPULATE, t);
            throw new ServletException(LOG_SUBSCRIPTION_POPULATE, t);
        } catch (Throwable t) {
            log.error(LOG_SUBSCRIPTION_POPULATE, t);
            throw new ServletException(LOG_SUBSCRIPTION_POPULATE, t);
        }
    }

    /**
     * <p>
     * Remove the given subscription for this user.
     * </p>
     *
     * @param mapping      Our ActionMapping
     * @param session      Our HttpSession
     * @param user         Our User
     * @param subscription Subscription to delete
     * @return "Success" if delete is nominal, "Logon" if attributes are
     *         missing
     * @throws ServletException if updates fails
     */
    private ActionForward doRemoveSubscription(
            ActionMapping mapping,
            HttpSession session,
            User user,
            Subscription subscription)
            throws ServletException {

        final String method = Constants.DELETE;
        doLogProcess(mapping, method);

        if (log.isTraceEnabled()) {
            log.trace(
                    " Deleting subscription to mail server '"
                            + subscription.getHost()
                            + "' for user '"
                            + user.getUsername()
                            + "'");
        }

        boolean missingAttributes = ((user == null) || (subscription == null));
        if (missingAttributes) {
            return doFindLogon(mapping);
        }

        user.removeSubscription(subscription);
        session.removeAttribute(Constants.SUBSCRIPTION_KEY);
        doSaveUser(user);

        return doFindSuccess(mapping);
    }

    // ----- Public Methods ----

    /**
     * <p>
     * Prepare for a Delete operation by populating the form
     * and seting the action to Delete.
     * </p>
     *
     * @param mapping  Our ActionMapping
     * @param form     Our ActionForm
     * @param request  Our HttpServletRequest
     * @param response Our HttpServletResponse
     * @return The "Success" result for this mapping
     * @throws Exception on any error
     */
    public ActionForward Delete(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        final String method = Constants.DELETE;
        doLogProcess(mapping, method);

        ActionForward result = Edit(mapping, form, request, response);

        doSet(form, TASK, method);
        return result;
    }

    /**
     * <p>
     * Retrieve the Subscription object to edit
     * or null if the Subscription does not exist.
     * </p><p>
     * The Subscription object is bound to the User,
     * and so if the User is not logged in,
     * control is forwarded to the Logon result.
     * </p>
     *
     * @param mapping  Our ActionMapping
     * @param form     Our ActionForm
     * @param request  Our HttpServletRequest
     * @param response Our HttpServletResponse
     * @return The "Success" result for this mapping
     * @throws Exception on any error
     */
    public ActionForward Edit(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        final String method = Constants.EDIT;
        doLogProcess(mapping, method);

        HttpSession session = request.getSession();
        User user = doGetUser(session);
        if (user == null) {
            return doFindLogon(mapping);
        }

        // Retrieve the subscription, if there is one
        Subscription subscription;
        String host = doGet(form, HOST);
        boolean updating = (host != null);
        if (updating) {
            subscription = doFindSubscription(user, host);
            if (subscription == null) {
                return doFindFailure(mapping);
            }
            session.setAttribute(Constants.SUBSCRIPTION_KEY, subscription);
            doPopulate(form, subscription);
            doSet(form, TASK, method);
        }

        return doFindSuccess(mapping);
    }

    /**
     * <p>
     * Insert or update a Subscription object to the persistent store.
     * </p>
     *
     * @param mapping  Our ActionMapping
     * @param form     Our ActionForm
     * @param request  Our HttpServletRequest
     * @param response Our HttpServletResponse
     * @return The "Success" result for this mapping
     * @throws Exception on any error
     */
    public ActionForward Save(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        final String method = Constants.SAVE;
        doLogProcess(mapping, method);

        User user = doGetUser(request);
        if (user == null) {
            return doFindLogon(mapping);
        }

        HttpSession session = request.getSession();
        if (isCancelled(request)) {
            doCancel(session, method, Constants.SUBSCRIPTION_KEY);
            return doFindSuccess(mapping);
        }

        String action = doGet(form, TASK);
        Subscription subscription = doGetSubscription(request);
        boolean isDelete = action.equals(Constants.DELETE);
        if (isDelete) {
            return doRemoveSubscription(mapping, session, user, subscription);
        }

        if (subscription == null) {
            subscription = user.createSubscription(doGet(form, HOST));
            session.setAttribute(Constants.SUBSCRIPTION_KEY, subscription);
        }

        doPopulate(subscription, form);
        doSaveUser(user);
        session.removeAttribute(Constants.SUBSCRIPTION_KEY);

        return doFindSuccess(mapping);
    }

}
