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
 * Custom component to load a {@code MessagesMap}.
 */
public class LoadMessagesComponent extends UIOutput {


    // ------------------------------------------------------------ Constructors


    /**
     * Create a new {@link LoadMessagesComponent} with default properties.
     */
    public LoadMessagesComponent() {
        super();
        setRendererType("org.apache.struts.faces.LoadMessages");
    }


    // ------------------------------------------------------ Instance Variables


    /**
     * The name of the {@code MessageResources} to expose, or
     * {@code null} for the default {@code MessageResources}
     * for this application module.
     */
    private String messages = null;

    /**
     * The request attribute key under which the
     * {@code MessagesMap} will be exposed.
     */
    private String var = null;


    // ---------------------------------------------------- Component Properties


    /**
     * Return the component family to which this component belongs.
     */
    public String getFamily() {
        return "org.apache.struts.faces.LoadMessages";
    }

    /**
     * Gets the name of the {@code MessageResources} to expose,
     * or {@code null} for the default {@code MessageResources}
     * for this application module.
     *
     * @return the name of the {@code MessageResources}
     */
    public String getMessages() {
        if (messages != null) {
            return messages;
        }
        ValueExpression vb = getValueExpression("messages");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return null;
        }
    }

    /**
     * Sets the name of the {@code MessageResources} to expose,
     * or {@code null} for the default {@code MessageResources}
     * for this application module.
     *
     * @param messages the name of the {@code MessageResources}
     */
    public void setMessages(String messages) {
        this.messages = messages;
    }

    /**
     * Gets the request attribute key under which the
     * {@code MessagesMap} will be exposed.
     *
     * @return the request attribute key
     */
    public String getVar() {
        if (var != null) {
            return var;
        }
        ValueExpression vb = getValueExpression("var");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return null;
        }
    }

    /**
     * Sets the request attribute key under which the
     * {@code MessagesMap} will be exposed.
     *
     * @param var the request attribute key
     */
    public void setVar(String var) {
        this.var = var;
    }


    // ---------------------------------------------------- StateManager Methods


    /**
     * Restore the state of this component.
     *
     * @param context {@code FacesContext} for the current request
     * @param state State object from which to restore our state
     */
    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        messages = (String) values[1];
        var = (String) values[2];
    }

    /**
     * Save the state of this component.
     *
     * @param context {@code FacesContext} for the current request
     */
    public Object saveState(FacesContext context) {
        Object values[] = new Object[3];
        values[0] = super.saveState(context);
        values[1] = messages;
        values[2] = var;
        return values;
    }
}