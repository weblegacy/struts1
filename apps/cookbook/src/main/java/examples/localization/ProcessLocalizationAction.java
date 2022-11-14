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

package examples.localization;

import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Retrieve and process data from the submitted form
 *
 * @version $Rev$ $Date$
 */
public class ProcessLocalizationAction extends Action {
    private static final long serialVersionUID = 1840482879886795996L;

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for ProcessOptionsAction.
     */
    public ProcessLocalizationAction() {
        super();
    }

    // ---------------------------------------------------------- Action Methods

    /**
     * Process the request and return an <code>ActionForward</code> instance
     * describing where and how control should be forwarded, or
     * <code>null</code>if the response has already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception Exception if the application logic throws an exception
     *
     * @return the ActionForward for the next view
     */
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

        // Extract attributes we will need
        HttpSession session = request.getSession();

        // Get locale from request, if any
        Locale locale = request.getLocale();

        // If supplied, set Locale based on request parameters;
        // country and language
        String language = request.getParameter("language");
        String country = request.getParameter("country");

        if ((language != null && language.length() > 0)
            && (country != null && country.length() > 0)) {
            locale = new Locale(language, country);
        } else if (language != null && language.length() > 0) {
            locale = new Locale(language);
        }

        //Save locale
        session.setAttribute(Globals.LOCALE_KEY, locale);

        // Forward to result page
        return mapping.findForward("success");
    }
}