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
 * <p>Render an input form that is submitted to a Struts <code>Action</code>,
 * for the <em>Struts-Faces Integration Library</em>.</p>
 *
 * @version $Rev$ $Date$
 */

public class FormTag extends AbstractFacesTag {


    // ---------------------------------------------------------- Tag Attributes


    /**
     * <p>The <code>path</code> of the Struts <code>Action</code> to which
     * this form should be submitted.  This property is analogous to the
     * <code>formName</code> property on the form tag in the standard
     * HTML RenderKit.</p>
     */
    protected ValueExpression _action;

    public void setAction(ValueExpression action) {
        this._action = action;
    }


    /**
     * <p>The content encoding type to use.</p>
     */
    protected ValueExpression _enctype;

    public void setEnctype(ValueExpression enctype) {
        this._enctype = enctype;
    }


    /**
     * <p>The name of the field to which focus should be set when this
     * form is displayed.</p>
     */
    protected ValueExpression _focus;

    public void setFocus(ValueExpression focus) {
        this._focus = focus;
    }


    /**
     * <p>The subscript of the focus field array to receive focus.</p>
     */
    protected ValueExpression _focusIndex;

    public void setFocusIndex(ValueExpression focusIndex) {
        this._focusIndex = focusIndex;
    }


    /**
     * <p>The JavaScript reset event handler.</p>
     */
    protected ValueExpression _onreset;

    public void setOnreset(ValueExpression onreset) {
        this._onreset = onreset;
    }


    /**
     * <p>The JavaScript submit event handler.</p>
     */
    protected ValueExpression _onsubmit;

    public void setOnsubmit(ValueExpression onsubmit) {
        this._onsubmit = onsubmit;
    }


    /**
     * <p>The window target for this submit.</p>
     */
    protected ValueExpression _target;

    public void setTarget(ValueExpression target) {
        this._target = target;
    }


    // ------------------------------------------------------------- Tag Methods


    /**
     * <p>Release any allocated resources.</p>
     */
    public void release() {

        super.release();
        _action = null;
        _enctype = null;
        _focus = null;
        _focusIndex = null;
        _onreset = null;
        _onsubmit = null;
        _target = null;

    }


    // ---------------------------------------------------------- Public Methods


    /**
     * Return the type of component to be created for this tag.
     */
    public String getComponentType() {

        return ("org.apache.struts.faces.Form");

    }


    /**
     * Return the {@code rendererType} to be used for rendering
     * our component.
     */
    public String getRendererType() {

        return ("org.apache.struts.faces.Form");

    }


    // ------------------------------------------------------- Protected Methods


    /**
     * <p>Override attributes set on this tag instance.</p>
     *
     * @param component Component whose attributes should be overridden
     */
    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        Utils.setStringProperty(component, "action", _action);
        Utils.setStringProperty(component, "enctype", _enctype);
        Utils.setStringProperty(component, "focus", _focus);
        Utils.setStringProperty(component, "focusIndex", _focusIndex);
        Utils.setStringProperty(component, "onreset", _onreset);
        Utils.setStringProperty(component, "onsubmit", _onsubmit);
        Utils.setStringProperty(component, "target", _target);

    }
}