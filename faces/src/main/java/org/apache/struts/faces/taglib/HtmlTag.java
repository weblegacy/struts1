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

package org.apache.struts.faces.taglib;


import javax.faces.component.UIComponent;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.Globals;


/**
 * <p>Render an HTML <code>&lt;html&gt;</code> element for
 * the <em>Struts-Faces Integration Library</em>.</p>
 *
 * @version $Rev$ $Date$
 */

public class HtmlTag extends AbstractFacesTag {


    // ---------------------------------------------------------- Tag Attributes


    /**
     * <p>Set a locale if not set yet.</p>
     */
    private String locale = null;

    public void setLocale(String locale) {
        this.locale = locale;
    }


    /**
     * <p>Render Struts HTML tags as xhtml.</p>
     */
    private String xhtml = null;

    public void setXhtml(String xhtml) {
        this.xhtml = xhtml;
    }


    // ---------------------------------------------------------- Public Methods


    /**
     * <p>Return the type of component to be created for this tag.</p>
     */
    public String getComponentType() {

        return ("org.apache.struts.faces.Html");

    }


    /**
     * <p>Override <code>doStartTag()</code> method to also set a page
     * context attribute if <code>xhtml</code> is <code>>true</code>.</p>
     */
    public int doStartTag() throws JspException {

        int result = super.doStartTag();
        if (xhtml != null) {
            pageContext.setAttribute(Globals.XHTML_KEY, "true",
                                     PageContext.PAGE_SCOPE);
        }
        return (result);

    }


    /**
     * <p>Return the <code>rendererType</code> to be used for rendering
     * our component.</p>
     */
    public String getRendererType() {

        return ("org.apache.struts.faces.Html");

    }


    /**
     * <p>Release resources allocated to this tag instance.</p>
     */
    public void release() {

        super.release();
        this.locale = null;
        this.xhtml = null;

    }


    // ------------------------------------------------------- Protected Methods


    /**
     * <p>Override attributes set on this tag instance.</p>
     *
     * @param component Component whose attributes should be overridden
     */
    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        setBooleanAttribute(component, "locale", locale);
        setBooleanAttribute(component, "xhtml", xhtml);

    }


}
