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


import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;


/**
 * Check for a valid User logged on in the current session.  If there is no
 * such user, forward control to the logon page.
 *
 * @author Craig R. McClanahan
 * @author Marius Barduta
 * @version $Rev$ $Date$
 */

public final class CheckLogonTag extends TagSupport {
    private static final long serialVersionUID = -6725525394050394706L;


    // --------------------------------------------------- Instance Variables


    /**
     * The key of the session-scope bean we look for.
     */
    private String name = Constants.USER_KEY;


    /**
     * The page to which we should forward for the user to log on.
     */
    private String page = "/logon.jsp";


    // ----------------------------------------------------------- Properties


    /**
     * Return the bean name.
     */
    public String getName() {

    return (this.name);

    }


    /**
     * Set the bean name.
     *
     * @param name The new bean name
     */
    public void setName(String name) {

    this.name = name;

    }


    /**
     * Return the forward page.
     */
    public String getPage() {

    return (this.page);

    }


    /**
     * Set the forward page.
     *
     * @param page The new forward page
     */
    public void setPage(String page) {

    this.page = page;

    }


    // ------------------------------------------------------- Public Methods


    /**
     * Defer our checking until the end of this tag is encountered.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

    return (SKIP_BODY);

    }


    /**
     * Perform our logged-in user check by looking for the existence of
     * a session scope bean under the specified name.  If this bean is not
     * present, control is forwarded to the specified logon page.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {

    // Is there a valid user logged on?
    boolean valid = false;
    HttpSession session = pageContext.getSession();
    if ((session != null) && (session.getAttribute(name) != null))
        valid = true;

    // Forward control based on the results
    if (valid)
        return (EVAL_PAGE);
    else {
        try {
        pageContext.forward(page);
        } catch (Exception e) {
        throw new JspException(e.toString());
        }
        return (SKIP_PAGE);
    }

    }


    /**
     * Release any acquired resources.
     */
    public void release() {

        super.release();
        this.name = Constants.USER_KEY;
        this.page = "/logon.jsp";

    }


}
