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


import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import org.apache.struts.apps.mailreader.dao.User;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;


/**
 * Generate a URL-encoded hyperlink to the specified URI, with
 * associated query parameters selecting a specified User.
 *
 * @author Craig R. McClanahan
 * @version $Rev$ $Date$
 */

public class LinkUserTag extends TagSupport {
    private static final long serialVersionUID = -9117230747162979755L;


    // ------------------------------------------------------ Instance Variables


    /**
     * The hyperlink URI.
     */
    protected String page = null;


    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
    MessageResources.getMessageResources
    ("org.apache.struts.webapp.example.ApplicationResources");


    /**
     * The attribute name.
     */
    private String name = "user";


    // ------------------------------------------------------------- Properties


    /**
     * Return the hyperlink URI.
     */
    public String getPage() {

    return (this.page);

    }


    /**
     * Set the hyperlink URI.
     *
     * @param page Set the hyperlink URI
     */
    public void setPage(String page) {

    this.page = page;

    }


    /**
     * Return the attribute name.
     */
    public String getName() {

    return (this.name);

    }


    /**
     * Set the attribute name.
     *
     * @param name The new attribute name
     */
    public void setName(String name) {

    this.name = name;

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Render the beginning of the hyperlink.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

    // Generate the URL to be encoded
        HttpServletRequest request =
            (HttpServletRequest) pageContext.getRequest();
        StringBuilder url = new StringBuilder(request.getContextPath());
        url.append(page);
    User user = null;
    try {
        user = (User) pageContext.findAttribute(name);
        } catch (ClassCastException e) {
        user = null;
    }
    if (user == null)
        throw new JspException
            (messages.getMessage("linkUser.noUser", name));
    if (page.indexOf("?") < 0)
        url.append("?");
    else
        url.append("&");
    url.append("username=");
    url.append(ResponseUtils.filter(user.getUsername()));

    // Generate the hyperlink start element
    HttpServletResponse response =
      (HttpServletResponse) pageContext.getResponse();
    StringBuilder results = new StringBuilder("<a href=\"");
    results.append(response.encodeURL(url.toString()));
    results.append("\">");

    // Print this element to our output writer
    JspWriter writer = pageContext.getOut();
    try {
        writer.print(results.toString());
    } catch (IOException e) {
        throw new JspException
        (messages.getMessage("linkUser.io", e.toString()));
    }

    // Evaluate the body of this tag
    return (EVAL_BODY_INCLUDE);

    }



    /**
     * Render the end of the hyperlink.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {


    // Print the ending element to our output writer
    JspWriter writer = pageContext.getOut();
    try {
        writer.print("</a>");
    } catch (IOException e) {
        throw new JspException
            (messages.getMessage("link.io", e.toString()));
    }

    return (EVAL_PAGE);

    }


    /**
     * Release any acquired resources.
     */
    public void release() {

        super.release();
        this.page = null;
        this.name = "user";

    }


}
