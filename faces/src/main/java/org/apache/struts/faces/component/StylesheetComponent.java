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
 * <code>&lt;html:stylesheet&gt;</code> tag.</p>
 */

public class StylesheetComponent extends UIOutput {


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Create a new {@link StylesheetComponent} with default properties.</p>
     */
    public StylesheetComponent() {

        super();
        setRendererType("org.apache.struts.faces.Stylesheet");

    }


    // ------------------------------------------------------ Instance Variables


    /**
     * <p>Context-relative path of the stylesheet resource.</p>
     */
    private String path = null;


    // ---------------------------------------------------- Component Properties


    /**
     * <p>Return the component family to which this component belongs.</p>
     */
    public String getFamily() {

        return "org.apache.struts.faces.Stylesheet";

    }


    /**
     * <p>Return the context-relative stylesheet path.</p>
     */
    public String getPath() {

        ValueExpression vb = getValueExpression("path");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return path;
        }

    }


    /**
     * <p>Set the context-relative stylesheet path.</p>
     *
     * @param path The new path
     */
    public void setPath(String path) {

        this.path = path;

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
        path = (String) values[1];

    }


    /**
     * <p>Save the state of this component.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    public Object saveState(FacesContext context) {

        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = path;
        return values;

    }


}
