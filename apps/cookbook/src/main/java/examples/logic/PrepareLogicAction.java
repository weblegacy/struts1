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

package examples.logic;

import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import examples.TestBean;
import examples.options.BookBean;

/**
 * Perform any tasks and setup any data that
 * must be prepared before the form is displayed.
 *
 * @version $Rev$ $Date$
 */
public class PrepareLogicAction extends Action {
    private static final long serialVersionUID = 131485024310473177L;

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for PrepareOptionsAction.
     */
    public PrepareLogicAction() {
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
     * @exception Exception if an exception occurs
     *
     * @return the ActionForward to forward control to
     */
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

        TestBean bean = new TestBean();
        request.setAttribute("testBean", bean);

        ArrayList<String> items = new ArrayList<>();
        request.setAttribute("items", items);

        request.setAttribute("intValue", Integer.valueOf(7));
        request.setAttribute("stringValue", "Hello, world!");

        /* Collection of custom beans */
        ArrayList<BookBean> books = new ArrayList<>();
        books.add(new BookBean("0596003285", "Programming Jakarta Struts"));
        books.add(new BookBean("1930110502", "Struts in Action"));
        books.add(new BookBean("1861007817", "Professional Struts Applications"));
        books.add(new BookBean("0672324725", "Struts Kick Start"));
        books.add(new BookBean("0471213020", "Mastering Jakarta Struts"));
        books.add(new BookBean("1558608621", "The Struts Framework"));
        books.add(new BookBean("0971661901", "Struts Fast Track"));
        request.setAttribute("books", books);

        ActionErrors errors = new ActionErrors();

        errors.add(ActionMessages.GLOBAL_MESSAGE,
            new ActionMessage("errors.detail", "This is a global error #1"));
        errors.add(ActionMessages.GLOBAL_MESSAGE,
            new ActionMessage("errors.detail", "This is a global error #2"));
        errors.add("test",
            new ActionMessage("errors.detail", "This is a test error"));

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE,
            new ActionMessage("message.detail", "This is global message #1"));
        messages.add(ActionMessages.GLOBAL_MESSAGE,
            new ActionMessage("message.detail", "This is global message #2"));
        messages.add("test",
            new ActionMessage("message.example.simple"));


        saveMessages(request, messages);
        saveErrors(request, errors);

        // Forward to the form
        return mapping.findForward("success");
    }
}