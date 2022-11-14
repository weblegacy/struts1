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
 * <code>&lt;html:message&gt;</code> tag.</p>
 */

public class MessageComponent extends UIOutput {


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Create a new {@link MessageComponent} with default properties.</p>
     */
    public MessageComponent() {

        super();
        setRendererType("org.apache.struts.faces.Message");

    }


    // ------------------------------------------------------ Instance Variables


    /**
     * <p>MessageResources attribute key to use for message lookup.</p>
     */
    private String bundle = null;


    /**
     * <p>Flag indicating whether output should be filtered.</p>
     */
    private boolean filter = true;
    private boolean filterSet = false;


    /**
     * <p>Message key to use for message lookup.</p>
     */
    private String key = null;


    /**
     * <p>CSS style(s) to be rendered for this component.</p>
     */
    private String style = null;


    /**
     * <p>CSS style class(es) to be rendered for this component.</p>
     */
    private String styleClass = null;


    // ---------------------------------------------------- Component Properties


    /**
     * <p>Return the MessageResources key.</p>
     */
    public String getBundle() {

        ValueExpression vb = getValueExpression("bundle");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return bundle;
        }

    }


    /**
     * <p>Set the MessageResources key.</p>
     *
     * @param bundle The new key
     */
    public void setBundle(String bundle) {

        this.bundle = bundle;

    }


    /**
     * <p>Return the component family to which this component belongs.</p>
     */
    public String getFamily() {

        return "org.apache.struts.faces.Message";

    }


    /**
     * <p>Return a flag indicating whether filtering should take place.</p>
     */
    public boolean isFilter() {

        if (filterSet) {
            return filter;
        }
        ValueExpression vb = getValueExpression("filter");
        if (vb != null) {
            Boolean value = (Boolean) vb.getValue(getFacesContext().getELContext());
            if (null == value) {
                return filter;
            }
            return value.booleanValue();
        } else {
            return filter;
        }

    }


    /**
     * <p>Set the flag indicating that the output value should be filtered.</p>
     *
     * @param filter The new filter flag
     */
    public void setFilter(boolean filter) {

        this.filter = filter;
        this.filterSet = true;

    }


    /**
     * <p>Return the message key.</p>
     */
    public String getKey() {

        ValueExpression vb = getValueExpression("key");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return key;
        }

    }


    /**
     * <p>Set the message key.</p>
     *
     * @param key The new key
     */
    public void setKey(String key) {

        this.key = key;

    }


    /**
     * <p>Return the CSS style(s) to be rendered for this component.</p>
     */
    public String getStyle() {

        ValueExpression vb = getValueExpression("style");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return style;
        }

    }


    /**
     * <p>Set the CSS style(s) to be rendered for this component.</p>
     *
     * @param style The new CSS style(s)
     */
    public void setStyle(String style) {

        this.style = style;

    }


    /**
     * <p>Return the CSS style class(es) to be rendered for this component.</p>
     */
    public String getStyleClass() {

        ValueExpression vb = getValueExpression("styleClass");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return styleClass;
        }

    }


    /**
     * <p>Set the CSS style class(es) to be rendered for this component.</p>
     *
     * @param styleClass The new CSS style class(es)
     */
    public void setStyleClass(String styleClass) {

        this.styleClass = styleClass;

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
        bundle = (String) values[1];
        filter = ((Boolean) values[2]).booleanValue();
        filterSet = ((Boolean) values[3]).booleanValue();
        key = (String) values[4];
        style = (String) values[5];
        styleClass = (String) values[6];

    }


    /**
     * <p>Save the state of this component.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    public Object saveState(FacesContext context) {

        Object values[] = new Object[7];
        values[0] = super.saveState(context);
        values[1] = bundle;
        values[2] = filter ? Boolean.TRUE : Boolean.FALSE;
        values[3] = filterSet ? Boolean.TRUE : Boolean.FALSE;
        values[4] = key;
        values[5] = style;
        values[6] = styleClass;
        return values;

    }


}
