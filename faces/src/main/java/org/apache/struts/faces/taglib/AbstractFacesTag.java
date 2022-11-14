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
import jakarta.faces.webapp.UIComponentELTag;


/**
 * <p>Abstract base class for custom component tags for the
 * <em>Struts-Faces Integration Library</em>.</p>
 *
 *
 * @version $Rev$ $Date$
 */

public abstract class AbstractFacesTag extends UIComponentELTag {


    // ---------------------------------------------------------- Tag Attributes


    /**
     * <p>The servlet context attribute under which our
     * <code>MessageResources</code> bundle is stored.</p>
     */
    protected ValueExpression _bundle;

    public void setBundle(ValueExpression bundle) {
        this._bundle = bundle;
    }


    /**
     * <p>The CSS style(s) used to render this component.</p>
     */
    protected ValueExpression _style;

    public void setStyle(ValueExpression style) {
        this._style = style;
    }


    /**
     * <p>The CSS style class(es) used to render this component.</p>
     */
    protected ValueExpression _styleClass;

    public void setStyleClass(ValueExpression styleClass) {
        this._styleClass = styleClass;
    }


    /**
     * <p>The literal value to be rendered.</p>
     */
    protected ValueExpression _value;

    public void setValue(ValueExpression value) {
        this._value = value;
    }


    // ---------------------------------------------------------- Public Methods


    /**
     * <p>Return the component type of the component to be created for
     * this tag.</p>
     */
    public abstract String getComponentType();


    /**
     * <p>Return the <code>rendererType</code> to be used for rendering
     * our component.</p>
     */
    public abstract String getRendererType();


    /**
     * <p>Release any variables allocated during use of this tag instance.</p>
     */
    public void release() {

        super.release();
        this._bundle = null;
        this._style = null;
        this._styleClass = null;
        this._value = null;

    }


    // -------------------------------------------------- UIComponentTag Methods


    /**
     * <p>Override attributes set on this tag instance.</p>
     *
     * @param component Component whose attributes should be overridden
     */
    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        Utils.setStringProperty(component, "bundle", _bundle);
        Utils.setStringProperty(component, "style", _style);
        Utils.setStringProperty(component, "styleClass", _styleClass);
        Utils.setStringProperty(component, "value", _value);

    }


    // ------------------------------------------------------- Protected Methods


    /**
     * Gets the value of the specified value expression.
     *
     * @param valueExpression Value expression
     *
     * @return the value
     *
     * @since 1.4.1
     */
    protected Boolean getBooleanValue(ValueExpression valueExpression) {
        if (valueExpression.isLiteralText()){
            return Boolean.valueOf(valueExpression.getExpressionString());
        }

        return (Boolean) valueExpression.getValue(getELContext());
    }

    /**
     * Gets the value of the specified value expression.
     *
     * @param valueExpression Value expression
     *
     * @return the value
     *
     * @since 1.4.1
     */
    protected Integer getIntegerValue(ValueExpression valueExpression) {
        if (valueExpression.isLiteralText()){
            return Integer.valueOf(valueExpression.getExpressionString());
        }

        return (Integer) valueExpression.getValue(getELContext());
    }
}