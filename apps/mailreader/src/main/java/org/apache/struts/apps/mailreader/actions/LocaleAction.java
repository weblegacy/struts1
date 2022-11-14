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
package org.apache.struts.apps.mailreader.actions;

import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * Change user's Struts {@link java.util.Locale}.
 * </p>
 */
public final class LocaleAction extends BaseAction {
    private static final long serialVersionUID = 7038993006910281561L;

    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(LocaleAction.class);

    /**
     * <p>
     * Return true if parameter is null or trims to empty.
     * </p>
     *
     * @param string The string to text; may be  null
     * @return true if parameter is null or empty
     */
    private boolean isBlank(String string) {
        return ((string == null) || (string.trim().length() == 0));
    }

    /**
     * <p>
     * Parameter for {@link java.util.Locale} language property. ["language"]
     * </p>
     */
    private static final String LANGUAGE = "language";

    /**
     * <p>
     * Parameter for {@link java.util.Locale} country property. ["country"]
     * </p>
     */
    private static final String COUNTRY = "country";

    /**
     * <p>
     * Parameter for response page URI. ["page"]
     * </p>
     */
    private static final String PAGE = "page";

    /**
     * <p>
     * Parameter for response forward name. ["forward"]
     * </p>
     */
    private static final String FORWARD = "forward";

    /**
     * <p>
     * Logging message if LocaleAction is missing a target parameter.
     * </p>
     */
    private static final String LOCALE_LOG =
            "LocaleAction: Missing page or forward parameter";

    /**
     * <p>
     * Change the user's Struts {@link java.util.Locale} based on request
     * parameters for "language", "country".
     * After setting the Locale, control is forwarded to an URI path
     * indicated by a "page" parameter, or a forward indicated by a
     * "forward" parameter, or to a forward given as the mappings
     * "parameter" property.
     * The response location must be specified one of these ways.
     * </p>
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return An ActionForward indicate the resources that will render the
     *         response
     * @throws Exception if an input/output error or servlet exception occurs
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        String language = request.getParameter(LANGUAGE);
        String country = request.getParameter(COUNTRY);

        Locale locale = getLocale(request);

        if ((!isBlank(language)) && (!isBlank(country))) {
            locale = new Locale(language, country);
        } else if (!isBlank(language)) {
            locale = new Locale(language);
        }

        HttpSession session = request.getSession();
        session.setAttribute(Globals.LOCALE_KEY, locale);

        String target = request.getParameter(PAGE);
        if (!isBlank(target)) {
            return new ActionForward(target);
        }

        target = request.getParameter(FORWARD);
        if (isBlank(target)) {
            target = mapping.getParameter();
        }
        if (isBlank(target)) {
            LOG.warn(LOCALE_LOG);
            return null;
        }
        return mapping.findForward(target);
    }
}