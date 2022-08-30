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


import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.ActionSource2;
import javax.faces.component.UIComponent;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.webapp.UIComponentELTag;


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
        setStringProperty(component, "bundle", _bundle);
        setStringProperty(component, "style", _style);
        setStringProperty(component, "styleClass", _styleClass);
        setStringProperty(component, "value", _value);

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
     * If the specified attribute value is not {@code null} use it
     * to either store a value binding expression for the specified
     * attribute name, or store it as the literal value of the
     * attribute.
     *
     * @param component {@code UIComponent} whose attribute is to
     *                  be set
     * @param propName  Property name
     * @param value     Property value (or {@code null})
     *
     * @exception ELException if the expression has invalid syntax
     *
     * @since 1.4.1
     */
    protected static void setBooleanProperty(UIComponent component,
            String propName, ValueExpression value) {

        if (value == null) {
            return;
        }

        if (value.isLiteralText()) {
            component.getAttributes().put(propName, Boolean.valueOf(value.getExpressionString()));
        } else {
            component.setValueExpression(propName, value);
        }
    }

    /**
     * If the specified attribute value is not {@code null} use it
     * to either store a value binding expression for the specified
     * attribute name, or store it as the literal value of the
     * attribute.
     *
     * @param component {@code UIComponent} whose attribute is to
     *                  be set
     * @param propName  Property name
     * @param value     Property value (or {@code null})
     *
     * @exception ELException if the expression has invalid syntax
     *
     * @since 1.4.1
     */
    protected static void setStringProperty(UIComponent component,
            String propName, ValueExpression value) {

        if (value == null) {
            return;
        }

        if (value.isLiteralText()) {
            component.getAttributes().put(propName, value.getExpressionString());
        } else {
            component.setValueExpression(propName, value);
        }
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

    /**
     * If the specified attribute value is not {@code null} use it
     * to either store a value binding expression for the specified
     * attribute name, or store it as the literal value of the
     * attribute.
     *
     * @param component {@code UIComponent} whose attribute is to
     *                  be set
     * @param propName  Property name
     * @param value     Property value (or {@code null})
     *
     * @exception ELException if the expression has invalid syntax
     *
     * @since 1.4.1
     */
    protected static void setIntegerProperty(UIComponent component,
            String propName, ValueExpression value) {

        if (value == null) {
            return;
        }

        if (value.isLiteralText()) {
            component.getAttributes().put(propName, Integer.valueOf(value.getExpressionString()));
        } else {
            component.setValueExpression(propName, value);
        }
    }

    /**
     * If the specified action is not {@code null} use it to
     * set the action of the component.
     *
     * @param component {@code UIComponent} whose action is to
     *                  be set
     * @param action    the Action
     *
     * @throws IllegalArgumentException if the component is not an
     *         instance of {@code ActionSource2}
     *
     * @since 1.4.1
     */
    public void setActionProperty(UIComponent component, MethodExpression action) {
        if (action != null) {
            castActionSource2(component).setActionExpression(action);
        }
    }

    /**
     * If the specified action-listener is not {@code null} use
     * it to add the action-listener to the component.
     *
     * @param component {@code UIComponent} whose action-listener
     *                  is to be added
     * @param action    the Action-Listener
     *
     * @throws IllegalArgumentException if the component is not an
     *         instance of {@code ActionSource2}
     *
     * @since 1.4.1
     */
    public void setActionListenerProperty(UIComponent component, MethodExpression actionListener) {
        if (actionListener != null) {
            castActionSource2(component).addActionListener(new MethodExpressionActionListener(actionListener));
        }
    }

    /**
     * Test the component if it's an instance of {@code ActionSource2}
     * and returns it.
     *
     * @param component {@code UIComponent} to test
     *
     * @return the component as {@code ActionSource2}
     *
     * @throws IllegalArgumentException if the component is not an
     *         instance of {@code ActionSource2}
     *
     * @since 1.4.1
    */
    private ActionSource2 castActionSource2(UIComponent component) {
        if (component instanceof ActionSource2) {
            return (ActionSource2) component;
        }
        throw new IllegalArgumentException("Component "
                + component.getClientId(getFacesContext())
                + " is no ActionSource2");
    }
}