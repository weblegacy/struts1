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

import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Initializes ActionForm.
 *
 */
public final class EditTypeAction extends Action {
    private static final long serialVersionUID = 427874158279308873L;

    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(EditTypeAction.class);

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
     * @return Action to forward to
     * @exception Exception if an input/output error or servlet exception occurs
     */
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        // Was this transaction cancelled?

        initFormBeans(mapping, form, request);

        return mapping.findForward("success");
    }

    /**
     * Convenience method for initializing form bean.
     * @param mapping The ActionMapping used to select this instance
     * @param request The HTTP request we are processing
     */
    protected void initFormBeans(
            ActionMapping mapping, ActionForm form,
            HttpServletRequest request) {

        LOG.debug("initFromBeans");

        // Initialize
        ArrayList<LabelValueBean> satisfactionList = new ArrayList<>();
        satisfactionList.add(new LabelValueBean("Very Satisfied", "4"));
        satisfactionList.add(new LabelValueBean("Satisfied", "3"));
        satisfactionList.add(new LabelValueBean("Not Very Satisfied", "2"));
        satisfactionList.add(new LabelValueBean("Not Satisfied", "1"));
        request.setAttribute("satisfactionList", satisfactionList);

        ArrayList<LabelValueBean> osTypes = new ArrayList<>();
        osTypes.add(new LabelValueBean("Mac OsX", "OsX"));
        osTypes.add(new LabelValueBean("Windows 95/98/Me", "Win32"));
        osTypes.add(new LabelValueBean("Windows NT/2000/XP/2003", "WinNT"));
        osTypes.add(new LabelValueBean("Linux", "Linux"));
        osTypes.add(new LabelValueBean("BSD NetBSD/FreeBSD/OpenBSD", "BSD"));
        request.setAttribute("osTypes", osTypes);

        ArrayList<LabelValueBean> languageTypes = new ArrayList<>();
        languageTypes.add(new LabelValueBean("C++", "C++"));
        languageTypes.add(new LabelValueBean("C#", "C#"));
        languageTypes.add(new LabelValueBean("Java", "java"));
        languageTypes.add(new LabelValueBean("Smalltalk", "Smalltalk"));
        request.setAttribute("languageTypes", languageTypes);
    }
}