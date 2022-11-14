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
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;


/**
 * <p>Custom component that replaces the Struts
 * <code>&lt;html:write&gt;</code> tag.</p>
 */

public class WriteComponent extends UIOutput {


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Create a new {@link WriteComponent} with default properties.</p>
     */
    public WriteComponent() {

        super();
        setRendererType("org.apache.struts.faces.Write");

    }


    // ------------------------------------------------------ Instance Variables


    /**
     * <p>Flag indicating whether output should be filtered.</p>
     */
    private boolean filter = true;
    private boolean filterSet = false;


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
     * <p>Return the component family to which this component belongs.</p>
     */
    public String getFamily() {

        return "org.apache.struts.faces.Write";

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
        filter = ((Boolean) values[1]).booleanValue();
        filterSet = ((Boolean) values[2]).booleanValue();
        style = (String) values[3];
        styleClass = (String) values[4];

    }


    /**
     * <p>Save the state of this component.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    public Object saveState(FacesContext context) {

        Object values[] = new Object[5];
        values[0] = super.saveState(context);
        values[1] = filter ? Boolean.TRUE : Boolean.FALSE;
        values[2] = filterSet ? Boolean.TRUE : Boolean.FALSE;
        values[3] = style;
        values[4] = styleClass;
        return values;

    }


}
