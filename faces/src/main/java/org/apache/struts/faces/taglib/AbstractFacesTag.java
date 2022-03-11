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


import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;


/**
 * <p>Abstract base class for custom component tags for the
 * <em>Struts-Faces Integration Library</em>.</p>
 *
 *
 * @version $Rev$ $Date$
 */

public abstract class AbstractFacesTag extends UIComponentTag {


    // ---------------------------------------------------------- Tag Attributes


    /**
     * <p>The servlet context attribute under which our
     * <code>MessageResources</code> bundle is stored.</p>
     */
    protected String bundle = null;

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }


    /**
     * <p>The CSS style(s) used to render this component.</p>
     */
    protected String style = null;

    public void setStyle(String style) {
        this.style = style;
    }


    /**
     * <p>The CSS style class(es) used to render this component.</p>
     */
    protected String styleClass = null;

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }


    /**
     * <p>The literal value to be rendered.</p>
     */
    protected String value = null;

    public void setValue(String value) {
        this.value = value;
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
        this.bundle = null;
        this.style = null;
        this.styleClass = null;
        this.value = null;

    }


    // -------------------------------------------------- UIComponentTag Methods


    /**
     * <p>Override attributes set on this tag instance.</p>
     *
     * @param component Component whose attributes should be overridden
     */
    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        setStringAttribute(component, "bundle", bundle);
        setStringAttribute(component, "style", style);
        setStringAttribute(component, "styleClass", styleClass);
        setStringAttribute(component, "value", value);

    }


    // ------------------------------------------------------- Protected Methods


    /**
     * <p>If the specified attribute value is not <code>null</code>
     * use it to either store a value binding expression for the
     * specified attribute name, or store it as the literal value
     * of the attribute.</p>
     *
     * @param component <code>UIComponent</code> whose attribute
     *  is to be set
     * @param name Attribute name
     * @param value Attribute value (or <code>null</code>)
     *
     * @exception NumberFormatException if the value does not
     *  contain a parsable integer
     * @exception ReferenceSyntaxException if the expression has
     *  invalid syntax
     */
    protected void setBooleanAttribute(UIComponent component,
                                       String name, String value) {

        if (value == null) {
            return;
        }
        if (isValueReference(value)) {
            ValueBinding vb =
                getFacesContext().getApplication().createValueBinding(value);
            component.setValueBinding(name, vb);
        } else {
            component.getAttributes().put(name, Boolean.valueOf(value));
        }

    }


    /**
     * <p>If the specified attribute value is not <code>null</code>
     * use it to either store a value binding expression for the
     * specified attribute name, or store it as the literal value
     * of the attribute.</p>
     *
     * @param component <code>UIComponent</code> whose attribute
     *  is to be set
     * @param name Attribute name
     * @param value Attribute value (or <code>null</code>)
     *
     * @exception NumberFormatException if the value does not
     *  contain a parsable integer
     * @exception ReferenceSyntaxException if the expression has
     *  invalid syntax
     */
    protected void setIntegerAttribute(UIComponent component,
                                       String name, String value) {

        if (value == null) {
            return;
        }
        if (isValueReference(value)) {
            ValueBinding vb =
                getFacesContext().getApplication().createValueBinding(value);
            component.setValueBinding(name, vb);
        } else {
            component.getAttributes().put(name, Integer.valueOf(value));
        }

    }


    /**
     * <p>If the specified attribute value is not <code>null</code>
     * use it to either store a value binding expression for the
     * specified attribute name, or store it as the literal value
     * of the attribute.</p>
     *
     * @param component <code>UIComponent</code> whose attribute
     *  is to be set
     * @param name Attribute name
     * @param value Attribute value (or <code>null</code>)
     *
     * @exception ReferenceSyntaxException if the expression has
     *  invalid syntax
     */
    protected void setStringAttribute(UIComponent component,
                                      String name, String value) {

        if (value == null) {
            return;
        }
        if (isValueReference(value)) {
            ValueBinding vb =
                getFacesContext().getApplication().createValueBinding(value);
            component.setValueBinding(name, vb);
        } else {
            component.getAttributes().put(name, value);
        }

    }
}
