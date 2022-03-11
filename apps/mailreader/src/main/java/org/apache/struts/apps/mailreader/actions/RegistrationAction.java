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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.apps.mailreader.Constants;
import org.apache.struts.apps.mailreader.dao.ExpiredPasswordException;
import org.apache.struts.apps.mailreader.dao.User;
import org.apache.struts.apps.mailreader.dao.UserDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 * Provide an Edit method for retrieving an existing user,
 * and a Save method for updating or inserting a user.
 * </p><p>
 * Both methods utilize a RegistrationForm to obtain or expose User details.
 * If Save is used to create a user,
 * additional validations ensure input is nominal.
 * When a user is created,
 * Save also handles the initial logon.
 * </p>
 */
public final class RegistrationAction extends BaseAction {

    // --- Public Constants --

    /**
     * <p>
     * Name of fromAddress field ["fromAddress"].
     * </p>
     */
    public final static String FROM_ADDRESS = "fromAddress";

    /**
     * <p>
     * Name of fullName field ["fullName"].
     * </p>
     */
    public final static String FULL_NAME = "fullName";

    /**
     * <p>
     * Name of password confirmation field ["password2"].
     * </p>
     */
    public final static String PASSWORD2 = "password2";

    /**
     * <p>
     * Name of replyToAddress field ["replyToAddress"].
     * </p>
     */
    public final static String REPLY_TO_ADDRESS = "replyToAddress";

    // ---- Private Methods ----

    /**
     * <p>
     * The message prefix to use when populating a Registration Form.
     * </p>
     */
    final String LOG_REGISTRATION_POPULATE = "RegistrationForm.populate";

    /**
     * <p>
     * Helper method to post error message when user already exists.
     * </p>
     *
     * @param username Existing username
     * @param errors   Our ActionMessages collection
     */
    private void errorUsernameUnique(String username,
                                     ActionMessages errors) {
        errors.add(
                USERNAME,
                new org.apache.struts.action.ActionMessage(
                        "error.username.unique", username));
    }

    /**
     * <p>
     * Verify input for creating a new user,
     * create the user, and process the login.
     * </p>
     *
     * @param form    The input form
     * @param request The HttpRequest being served
     * @param errors  The ActionMessages collection for any errors
     * @return A new User and empty Errors if create succeeds,
     *         or null and Errors if create fails
     */
    private User doCreateUser(
            ActionForm form,
            HttpServletRequest request,
            ActionMessages errors) {

        if (log.isTraceEnabled()) {
            log.trace(" Perform additional validations on Create");
        }

        UserDatabase database = doGetUserDatabase();
        String username = doGet(form, USERNAME);
        try {
            if (database.findUser(username) != null) {
                errorUsernameUnique(username, errors);
            }
        }
        catch (ExpiredPasswordException e) {
            errorUsernameUnique(username, errors);
            errors.add("errors.literal", new ActionMessage(e.getMessage()));
        }

        String password = doGet(form, PASSWORD);
        if ((password == null) || (password.length() < 1)) {
            errors.add(PASSWORD, new ActionMessage("error.password.required"));

            String password2 = doGet(form, PASSWORD2);
            if ((password2 == null) || (password2.length() < 1)) {
                errors.add(
                        PASSWORD2,
                        new ActionMessage("error.password2.required"));
            }
        }

        if (!errors.isEmpty()) {
            return null;
        }

        User user = database.createUser(username);

        // Log the user in
        HttpSession session = request.getSession();
        session.setAttribute(Constants.USER_KEY, user);
        if (log.isTraceEnabled()) {
            log.trace(
                    " User: '"
                            + user.getUsername()
                            + "' logged on in session: "
                            + session.getId());
        }

        return user;
    }

    /**
     * <p>
     * Helper method to populate the input form from the User object.
     * </p>
     *
     * @param form Form with incoming values
     * @param user User object to populate
     * @throws ServletException On any error
     */
    private void doPopulate(ActionForm form, User user)
            throws ServletException {

        final String title = Constants.EDIT;

        if (log.isTraceEnabled()) {
            log.trace(Constants.LOG_POPULATE_FORM + user);
        }

        try {
            PropertyUtils.copyProperties(form, user);
            DynaActionForm dyna = (DynaActionForm) form;
            dyna.set(TASK, title);
            dyna.set(PASSWORD, null);
            dyna.set(PASSWORD2, null);
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if (t == null) {
                t = e;
            }
            log.error(LOG_REGISTRATION_POPULATE, t);
            throw new ServletException(LOG_REGISTRATION_POPULATE, t);
        } catch (Throwable t) {
            log.error(LOG_REGISTRATION_POPULATE, t);
            throw new ServletException(LOG_REGISTRATION_POPULATE, t);
        }
    }

    /**
     * <p>
     * Helper method to populate the User object from the input form.
     * </p>
     *
     * @param user User object to populate
     * @param form Form with incoming values
     * @throws ServletException On any error
     */
    private void doPopulate(User user, ActionForm form)
            throws ServletException {

        if (log.isTraceEnabled()) {
            log.trace(Constants.LOG_POPULATE_USER + user);
        }

        try {
            String oldPassword = user.getPassword();
            PropertyUtils.copyProperties(user, form);
            String password = doGet(form, PASSWORD);
            if ((password == null)
                    || (password.length() < 1)) {

                user.setPassword(oldPassword);
            }

        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if (t == null) {
                t = e;
            }

            log.error(LOG_REGISTRATION_POPULATE, t);
            throw new ServletException(LOG_REGISTRATION_POPULATE, t);

        } catch (Throwable t) {
            log.error(LOG_REGISTRATION_POPULATE, t);
            throw new ServletException(LOG_REGISTRATION_POPULATE, t);
        }
    }

    /**
     * <p>
     * Validate and clear the transactional token,
     * creating logging statements as needed.
     * </p>
     *
     * @param request Our HttpServletRequest
     * @param errors  ActionErrors to transfer any messages
     */
    private void doValidateToken(HttpServletRequest request,
                                 ActionMessages errors) {

        if (log.isTraceEnabled()) {
            log.trace(Constants.LOG_TOKEN_CHECK);
        }

        if (!isTokenValid(request)) {
            errors.add(
                    ActionMessages.GLOBAL_MESSAGE,
                    new ActionMessage(Constants.MSG_TRANSACTION_TOKEN));
        }

        resetToken(request);
    }

    // ----- Public Methods ----

    /**
     * <p>
     * Retrieve the User object to edit or null if the User does not exist,
     * and set an transactional token to later detect multiple Save commands.
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
        boolean updating = (user != null);
        if (updating) {
            doPopulate(form, user);
        }

        doSaveToken(request);
        return doFindSuccess(mapping);
    }

    /**
     * <p>
     * Insert or update a User object to the persistent store.
     * </p><p>
     * If a User is not logged in,
     * then a new User is created and automatically logged in.
     * Otherwise, the existing User is updated.
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

        HttpSession session = request.getSession();
        if (isCancelled(request)) {
            doCancel(session, method, Constants.SUBSCRIPTION_KEY);
            return doFindSuccess(mapping);
        }

        ActionMessages errors = new ActionMessages();
        doValidateToken(request, errors);

        if (!errors.isEmpty()) {
            return doInputForward(mapping, request, errors);
        }

        User user = doGetUser(session);
        if (user == null) {
            user = doCreateUser(form, request, errors);
            if (!errors.isEmpty()) {
                return doInputForward(mapping, request, errors);
            }
        }

        doPopulate(user, form);
        doSaveUser(user);

        return doFindSuccess(mapping);
    }

}
