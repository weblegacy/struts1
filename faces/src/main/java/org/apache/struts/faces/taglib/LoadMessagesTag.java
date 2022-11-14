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

import org.apache.struts.faces.util.Utils;

import jakarta.el.ValueExpression;
import jakarta.faces.component.UIComponent;

/**
 * Tag that exposes a specified {@code MessageResources} instance
 * as {@code MessagesMap}, so that the embedded messages may be
 * retrieved via value binding expressions.
 */
public class LoadMessagesTag extends AbstractFacesTag {


    // ---------------------------------------------------------- Tag Attributes


    /**
     * The name of the {@code MessageResources} to expose, or
     * {@code null} for the default {@code MessageResources}
     * for this application module.
     */
    private ValueExpression _messages;

    public void setMessages(ValueExpression messages) {
        this._messages = messages;
    }


    /**
     * The request attribute key under which a
     * {@code MessagesMap} will be exposed.
     */
    private ValueExpression _var;

    public void setVar(ValueExpression var) {
        this._var = var;
    }


    // ------------------------------------------------------------- Tag Methods


    /**
     * Release any allocated resources.
     */
    public void release() {
        super.release();
        _messages = null;
        _var = null;
    }


    // ---------------------------------------------------------- Public Methods


    /**
     * Return the type of component to be created for this tag.
     */
    public String getComponentType() {
        return "org.apache.struts.faces.LoadMessages";
    }


    /**
     * Return the {@code rendererType} to be used for rendering
     * our component.
     */
    public String getRendererType() {
        return "org.apache.struts.faces.LoadMessages";
    }


    // ------------------------------------------------------- Protected Methods


    /**
     * Override attributes set on this tag instance.
     *
     * @param component Component whose attributes should be overridden
     */
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        Utils.setStringProperty(component, "messages", _messages);
        Utils.setStringProperty(component, "var", _var);
    }
}