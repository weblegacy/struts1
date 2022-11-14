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


import org.apache.struts.Globals;
import org.apache.struts.faces.util.Utils;

import jakarta.el.ValueExpression;
import jakarta.faces.component.UIComponent;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;


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
    private ValueExpression _locale;

    public void setLocale(ValueExpression locale) {
        this._locale = locale;
    }


    /**
     * <p>Render Struts HTML tags as xhtml.</p>
     */
    private ValueExpression _xhtml;

    public void setXhtml(ValueExpression xhtml) {
        this._xhtml = xhtml;
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
     * context attribute if <code>xhtml</code> is <code>true</code>.</p>
     */
    public int doStartTag() throws JspException {

        int result = super.doStartTag();
        if (_xhtml != null && getBooleanValue(_xhtml)) {
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
        this._locale = null;
        this._xhtml = null;

    }


    // ------------------------------------------------------- Protected Methods


    /**
     * <p>Override attributes set on this tag instance.</p>
     *
     * @param component Component whose attributes should be overridden
     */
    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        Utils.setBooleanProperty(component, "locale", _locale);
        Utils.setBooleanProperty(component, "xhtml", _xhtml);

    }
}