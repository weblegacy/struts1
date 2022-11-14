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

package org.apache.struts.faces.component;


import jakarta.el.ValueExpression;
import jakarta.faces.component.UIOutput;
import jakarta.faces.context.FacesContext;


/**
 * <p>Custom component that replaces the Struts
 * <code>&lt;html:html&gt;</code> tag.</p>
 */

public class HtmlComponent extends UIOutput {


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Create a new {@link HtmlComponent} with default properties.</p>
     */
    public HtmlComponent() {

        super();
        setRendererType("org.apache.struts.faces.Html");

    }


    // ------------------------------------------------------ Instance Variables


    /**
     * <p>Flag indicating we should create a locale.</p>
     */
    private boolean locale = true;
    private boolean localeSet = false;


    /**
     * <p>Flag indicating we should render XHTML output.</p>
     */
    private boolean xhtml = false;
    private boolean xhtmlSet = false;


    // ---------------------------------------------------- Component Properties


    /**
     * <p>Return the component family to which this component belongs.</p>
     */
    public String getFamily() {

        return "org.apache.struts.faces.Html";

    }


    /**
     * <p>Return a flag indicating whether a locale should be created.</p>
     */
    public boolean isLocale() {

        if (localeSet) {
            return locale;
        }
        ValueExpression vb = getValueExpression("locale");
        if (vb != null) {
            Boolean value = (Boolean) vb.getValue(getFacesContext().getELContext());
            if (null == value) {
                return locale;
            }
            return value.booleanValue();
        } else {
            return locale;
        }

    }


    /**
     * <p>Set the flag indicating whether a locale should be created.</p>
     *
     * @param locale The new flag
     */
    public void setLocale(boolean locale) {

        this.locale = locale;
        this.localeSet = true;

    }


    /**
     * <p>Return a flag indicating whether xhtml should be created.</p>
     */
    public boolean isXhtml() {

        if (xhtmlSet) {
            return xhtml;
        }
        ValueExpression vb = getValueExpression("xhtml");
        if (vb != null) {
            Boolean value = (Boolean) vb.getValue(getFacesContext().getELContext());
            if (null == value) {
                return xhtml;
            }
            return value.booleanValue();
        } else {
            return xhtml;
        }

    }


    /**
     * <p>Set the flag indicating whether xhtml should be created.</p>
     *
     * @param xhtml The new flag
     */
    public void setXhtml(boolean xhtml) {

        this.xhtml = xhtml;
        this.xhtmlSet = true;

    }


    // ---------------------------------------------------- StateManager Methods


    /**
     * <p>Restore the state of this component.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param state State object from which to restore our state
     */
    public void restoreState(FacesContext context, Object state) {

        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        locale = ((Boolean) values[1]).booleanValue();
        localeSet = ((Boolean) values[2]).booleanValue();
        xhtml = ((Boolean) values[3]).booleanValue();
        xhtmlSet = ((Boolean) values[4]).booleanValue();

    }


    /**
     * <p>Save the state of this component.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    public Object saveState(FacesContext context) {

        Object values[] = new Object[5];
        values[0] = super.saveState(context);
        values[1] = locale ? Boolean.TRUE : Boolean.FALSE;
        values[2] = localeSet ? Boolean.TRUE : Boolean.FALSE;
        values[3] = xhtml ? Boolean.TRUE : Boolean.FALSE;
        values[4] = xhtmlSet ? Boolean.TRUE : Boolean.FALSE;
        return values;

    }


}
