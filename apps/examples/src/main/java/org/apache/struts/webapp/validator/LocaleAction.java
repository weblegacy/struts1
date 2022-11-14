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


package org.apache.struts.webapp.validator;

import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementation of <strong>Action</strong> that changes the user's
 * {@link java.util.Locale} and forwards to a page, based on request level
 * parameters that are set  (language, country, &amp; page).
 *
*/
public final class LocaleAction extends Action {
    private static final long serialVersionUID = 8834021192169331239L;

    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(LocaleAction.class);

    /**
     * <p>
     * Change the user's {@link java.util.Locale} based on {@link ActionForm}
     * properties.
     * </p>
     * <p>
     * This <code>Action</code> looks for <code>language</code> and
     * <code>country</code> properties on the given form, constructs an
     * appropriate Locale object, and sets it as the Struts Locale for this
     * user's session.
     * Any <code>ActionForm</code>, including a
     * {@link org.apache.struts.action.DynaActionForm}, may be used.
     * </p>
     * <p>
     * If a <code>page</code> property is also provided, then after
     * setting the Locale, control is forwarded to that URI path.
     * Otherwise, control is forwarded to "success".
     * </p>
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @return Action to forward to
     * @exception java.lang.Exception if an input/output error or servlet exception occurs
     */
    public ActionForward execute(ActionMapping mapping,
                 ActionForm form,
                 HttpServletRequest request,
                 HttpServletResponse response)
    throws Exception {

    // Extract attributes we will need
    HttpSession session = request.getSession();
    Locale locale = getLocale(request);

    String language = null;
    String country = null;
    String page = null;

    try {
            language = (String)
              PropertyUtils.getSimpleProperty(form, "language");
            country = (String)
              PropertyUtils.getSimpleProperty(form, "country");
            page = (String)
              PropertyUtils.getSimpleProperty(form, "page");
        } catch (Exception e) {
           LOG.error(e.getMessage(), e);
        }

        if ((language != null && language.length() > 0) &&
            (country != null && country.length() > 0)) {
           locale = new Locale(language, country);
        } else if (language != null && language.length() > 0) {
           locale = new Locale(language);
    }

        session.setAttribute(Globals.LOCALE_KEY, locale);

        if (null==page || page.isEmpty()) return mapping.findForward("success");
        else return new ActionForward(page);

    }
}