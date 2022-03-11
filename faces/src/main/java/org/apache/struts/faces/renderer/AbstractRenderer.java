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

package org.apache.struts.faces.renderer;


import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>Abstract base class for concrete implementations of
 * <code>javax.faces.render.Renderer</code> for the
 * <em>Struts-Faces Integration Library</em>.</p>
 *
 * @version $Rev$ $Date$
 */

public abstract class AbstractRenderer extends Renderer {


    // -------------------------------------------------------- Static Variables


    private static final Log log =
  LogFactory.getLog(AbstractRenderer.class);


    // -------------------------------------------------------- Renderer Methods


    /**
     * <p>Decode any new state of the specified <code>UIComponent</code>
     * from the request contained in the specified <code>FacesContext</code>,
     * and store that state on the <code>UIComponent</code>.</p>
     *
     * <p>The default implementation calls <code>setSubmittedValue()</code>
     * unless this component has a boolean <code>disabled</code> or
     * <code>readonly</code> attribute that is set to <code>true</code>.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be decoded
     *
     * @exception NullPointerException if <code>context</code> or
     *  <code>component</code> is <code>null</code>
     */
    public void decode(FacesContext context, UIComponent component) {

        // Enforce NPE requirements in the Javadocs
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }

        // Disabled or readonly components are not decoded
        if (isDisabled(component) || isReadOnly(component)) {
            return;
        }

        // Save submitted value on EditableValueHolder components
        if (component instanceof EditableValueHolder) {
            setSubmittedValue(context, component);
        }

    }


    /**
     * <p>Render the beginning of the specified <code>UIComponent</code>
     * to the output stream or writer associated with the response we are
     * creating.</p>
     *
     * <p>The default implementation calls <code>renderStart()</code> and
     * <code>renderAttributes()</code>.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be decoded
     *
     * @exception NullPointerException if <code>context</code> or
     *  <code>component</code> is <code>null</code>
     *
     * @exception IOException if an input/output error occurs
     */
    public void encodeBegin(FacesContext context, UIComponent component)
        throws IOException {

        // Enforce NPE requirements in the Javadocs
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }

        if (log.isTraceEnabled()) {
            log.trace("encodeBegin(id=" + component.getId() +
                ", family=" + component.getFamily() +
                ", rendererType=" + component.getRendererType() + ")");
        }

        // Render the element and attributes for this component
        ResponseWriter writer = context.getResponseWriter();
        renderStart(context, component, writer);
        renderAttributes(context, component, writer);

    }


    /**
     * <p>Render the children of the specified <code>UIComponent</code>
     * to the output stream or writer associated with the response we are
     * creating.</p>
     *
     * <p>The default implementation iterates through the children of
     * this component and renders them.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be decoded
     *
     * @exception NullPointerException if <code>context</code> or
     *  <code>component</code> is <code>null</code>
     *
     * @exception IOException if an input/output error occurs
     */
    public void encodeChildren(FacesContext context, UIComponent component)
        throws IOException {

        if (context == null || component == null) {
            throw new NullPointerException();
        }

        if (log.isTraceEnabled()) {
            log.trace("encodeChildren(id=" + component.getId() +
                    ", family=" + component.getFamily() +
                    ", rendererType=" + component.getRendererType() + ")");
        }
        Iterator kids = component.getChildren().iterator();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
            kid.encodeBegin(context);
            if (kid.getRendersChildren()) {
                kid.encodeChildren(context);
            }
            kid.encodeEnd(context);
        }
        if (log.isTraceEnabled()) {
            log.trace("encodeChildren(id=" + component.getId() + ") end");
        }

    }


    /**
     * <p>Render the ending of the specified <code>UIComponent</code>
     * to the output stream or writer associated with the response we are
     * creating.</p>
     *
     * <p>The default implementation calls <code>renderEnd()</code>.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be decoded
     *
     * @exception NullPointerException if <code>context</code> or
     *  <code>component</code> is <code>null</code>
     *
     * @exception IOException if an input/output error occurs
     */
    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {

        // Enforce NPE requirements in the Javadocs
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }

        if (log.isTraceEnabled()) {
            log.trace("encodeEnd(id=" + component.getId() +
                    ", family=" + component.getFamily() +
                    ", rendererType=" + component.getRendererType() + ")");
        }

        // Render the element closing for this component
        ResponseWriter writer = context.getResponseWriter();
        renderEnd(context, component, writer);

    }


    // --------------------------------------------------------- Package Methods


    // ------------------------------------------------------- Protected Methods


    /**
     * <p>Render nested child components by invoking the encode methods
     * on those components, but only when the <code>rendered</code>
     * property is <code>true</code>.</p>
     */
    protected void encodeRecursive(FacesContext context, UIComponent component)
        throws IOException {

        // suppress rendering if "rendered" property on the component is
        // false.
        if (!component.isRendered()) {
            return;
        }

        // Render this component and its children recursively
        if (log.isTraceEnabled()) {
            log.trace("encodeRecursive(id=" + component.getId() +
                    ", family=" + component.getFamily() +
                    ", rendererType=" + component.getRendererType() +
                    ") encodeBegin");
        }
        component.encodeBegin(context);
        if (component.getRendersChildren()) {
            if (log.isTraceEnabled()) {
                log.trace("encodeRecursive(id=" + component.getId() +
                        ") delegating");
            }
            component.encodeChildren(context);
        } else {
            if (log.isTraceEnabled()) {
                log.trace("encodeRecursive(id=" + component.getId() +
                        ") recursing");
            }
            Iterator kids = component.getChildren().iterator();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();
                encodeRecursive(context, kid);
            }
        }
        if (log.isTraceEnabled()) {
            log.trace("encodeRecursive(id=" + component.getId() + ") encodeEnd");
        }
        component.encodeEnd(context);

    }


    /**
     * <p>Return <code>true</code> if the specified component is disabled.</p>
     *
     * @param component <code>UIComponent</code> to be checked
     */
    protected boolean isDisabled(UIComponent component) {

        Object disabled = component.getAttributes().get("disabled");
        if (disabled == null) {
            return (false);
        }
        if (disabled instanceof String) {
            return (Boolean.valueOf((String) disabled).booleanValue());
        } else {
            return (disabled.equals(Boolean.TRUE));
        }

    }


    /**
     * <p>Return <code>true</code> if the specified component is read only.</p>
     *
     * @param component <code>UIComponent</code> to be checked
     */
    protected boolean isReadOnly(UIComponent component) {

        Object readonly = component.getAttributes().get("readonly");
        if (readonly == null) {
            return (false);
        }
        if (readonly instanceof String) {
            return (Boolean.valueOf((String) readonly).booleanValue());
        } else {
            return (readonly.equals(Boolean.TRUE));
        }

    }


    /**
     * <p>Render the element attributes for the generated markup related to this
     * component.  Simple renderers that create a single markup element
     * for this component should override this method and include calls to
     * to <code>writeAttribute()</code> and <code>writeURIAttribute</code>
     * on the specified <code>ResponseWriter</code>.</p>
     *
     * <p>The default implementation does nothing.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderAttributes(FacesContext context, UIComponent component,
                                    ResponseWriter writer) throws IOException {

    }


    /**
     * <p>Render the element end for the generated markup related to this
     * component.  Simple renderers that create a single markup element
     * for this component should override this method and include a call
     * to <code>endElement()</code> on the specified
     * <code>ResponseWriter</code>.</p>
     *
     * <p>The default implementation does nothing.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderEnd(FacesContext context, UIComponent component,
                             ResponseWriter writer) throws IOException {

    }


    /**
     * <p>Render any boolean attributes on the specified list that have
     * <code>true</code> values on the corresponding attribute of the
     * specified <code>UIComponent</code>.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     * @param names List of attribute names to be passed through
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderBoolean(FacesContext context,
                                 UIComponent component,
                                 ResponseWriter writer,
                                 String names[]) throws IOException {

        if (names == null) {
            return;
        }
        Map attributes = component.getAttributes();
        boolean flag;
        Object value;
        for (int i = 0; i < names.length; i++) {
            value = attributes.get(names[i]);
            if (value != null) {
                if (value instanceof String) {
                    flag = Boolean.valueOf((String) value).booleanValue();
                } else {
                    flag = Boolean.valueOf(value.toString()).booleanValue();
                }
                if (flag) {
                    writer.writeAttribute(names[i], names[i], names[i]);
                    flag = false;
                }
            }
        }

    }


    /**
     * <p>Render any attributes on the specified list directly to the
     * specified <code>ResponseWriter</code> for which the specified
     * <code>UIComponent</code> has a non-<code>null</code> attribute value.
     * This method may be used to "pass through" commonly used attribute
     * name/value pairs with a minimum of code.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     * @param names List of attribute names to be passed through
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderPassThrough(FacesContext context,
                                     UIComponent component,
                                     ResponseWriter writer,
                                     String names[]) throws IOException {

        if (names == null) {
            return;
        }
        Map attributes = component.getAttributes();
        Object value;
        for (int i = 0; i < names.length; i++) {
            value = attributes.get(names[i]);
            if (value != null) {
                if (value instanceof String) {
                    writer.writeAttribute(names[i], value, names[i]);
                } else {
                    writer.writeAttribute(names[i], value.toString(), names[i]);
                }
            }
        }

    }


    /**
     * <p>Render the element start for the generated markup related to this
     * component.  Simple renderers that create a single markup element
     * for this component should override this method and include a call
     * to <code>startElement()</code> on the specified
     * <code>ResponseWriter</code>.</p>
     *
     * <p>The default implementation does nothing.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderStart(FacesContext context, UIComponent component,
                               ResponseWriter writer) throws IOException {

    }


    /**
     * <p>If a submitted value was included on this request, store it in the
     * component as appropriate.</p>
     *
     * <p>The default implementation determines whether this component
     * implements <code>EditableValueHolder</code>.  If so, it checks for a
     * request parameter with the same name as the <code>clientId</code>
     * of this <code>UIComponent</code>.  If there is such a parameter, its
     * value is passed (as a String) to the <code>setSubmittedValue()</code>
     * method on the <code>EditableValueHolder</code> component.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     */
    protected void setSubmittedValue
        (FacesContext context, UIComponent component) {

        if (!(component instanceof EditableValueHolder)) {
            return;
        }
        String clientId = component.getClientId(context);
        Map parameters = context.getExternalContext().getRequestParameterMap();
        if (parameters.containsKey(clientId)) {
            if (log.isTraceEnabled()) {
                log.trace("setSubmittedValue(" + clientId + "," +
                          (String) parameters.get(clientId));
            }
            component.getAttributes().put("submittedValue",
                                          parameters.get(clientId));
        }

    }


    // --------------------------------------------------------- Private Methods


    /**
     * <p>Decode the current state of the specified UIComponent from the
     * request contained in the specified <code>FacesContext</code>, and
     * attempt to convert this state information into an object of the
     * type equired for this component.</p>
     *
     * @param context FacesContext for the request we are processing
     * @param component UIComponent to be decoded
     *
     * @exception NullPointerException if context or component is null
     */
    /*
    public void decode(FacesContext context, UIComponent component) {

        // Enforce NPE requirements in the Javadocs
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }

        // Only input components need to be decoded
        if (!(component instanceof UIInput)) {
            return;
        }
        UIInput input = (UIInput) component;

        // Save the old value for use in generating ValueChangedEvents
        Object oldValue = input.getValue();
        if (oldValue instanceof String) {
            try {
                oldValue = getAsObject(context, component, (String) oldValue);
            } catch (ConverterException e) {
                ;
            }
        }
        input.setPrevious(oldValue);

        // Decode and convert (if needed) the new value
        String clientId = component.getClientId(context);
        Map map = context.getExternalContext().getRequestParameterMap();
        String newString = (String) map.get(clientId);
        Object newValue = null;
        try {
            newValue = getAsObject(context, component, newString);
            input.setValue(newValue);
            input.setValid(true);
        } catch (ConverterException e) {
            input.setValue(newValue);
            input.setValid(false);
            addConverterMessage(context, component, e.getMessage());
        }

    }
    */


    // --------------------------------------------------------- Package Methods


    // ------------------------------------------------------- Protected Methods


    /**
     * <p>Add an error message denoting a conversion failure.</p>
     *
     * @param context The <code>FacesContext</code> for this request
     * @param component The <code>UIComponent</code> that experienced
     *  the conversion failure
     * @param text The text of the error message
     */
    /*
    protected void addConverterMessage(FacesContext context,
                                       UIComponent component,
                                       String text) {

        String clientId = component.getClientId(context);
        FacesMessage message = new FacesMessage
            (text,
             "Conversion error on component '" + clientId + "'");
        context.addMessage(clientId, message);

    }
    */


    /**
     * <p>Convert the String representation of this component's value
     * to the corresponding Object representation.  The default
     * implementation utilizes the <code>getAsObject()</code> method of any
     * associated <code>Converter</code>.</p>
     *
     * @param context The <code>FacesContext</code> for this request
     * @param component The <code>UIComponent</code> whose value is
     *  being converted
     * @param value The String representation to be converted
     *
     * @exception ConverterException if conversion fails
     */
    /*
    protected Object getAsObject(FacesContext context, UIComponent component,
                                 String value) throws ConverterException {

        // Identify any Converter associated with this component value
        ValueBinding vb = component.getValueBinding("value");
        Converter converter = null;
        if (component instanceof ValueHolder) {
            // Acquire explicitly assigned Converter (if any)
            converter = ((ValueHolder) component).getConverter();
        }
        if ((converter == null) && (vb != null)) {
            Class type = vb.getType(context);
            if ((type == null) || (type == String.class)) {
                return (value); // No conversion required for Strings
            }
            // Acquire implicit by-type Converter (if any)
            converter = context.getApplication().createConverter(type);
        }

        // Convert the result if we identified a Converter
        if (converter != null) {
            return (converter.getAsObject(context, component, value));
        } else {
            return (value);
        }

    }
    */


    /**
     * <p>Convert the Object representation of this component's value
     * to the corresponding String representation.  The default implementation
     * utilizes the <code>getAsString()</code> method of any associated
     * <code>Converter</code>.</p>
     *
     * @param context The <code>FacesContext</code> for this request
     * @param component The <code>UIComponent</code> whose value is
     *  being converted
     * @param value The Object representation to be converted
     *
     * @exception ConverterException if conversion fails
     */
    protected String getAsString(FacesContext context, UIComponent component,
                                 Object value) throws ConverterException {

        // Identify any Converter associated with this component value
        ValueBinding vb = component.getValueBinding("value");
        Converter converter = null;
        if (component instanceof ValueHolder) {
            // Acquire explicitly assigned Converter (if any)
            converter = ((ValueHolder) component).getConverter();
        }
        if ((converter == null) && (vb != null)) {
            // Acquire implicit by-type Converter (if any)
            Class type = vb.getType(context);
            if (type != null) {
                converter = context.getApplication().createConverter(type);
            }
        }

        // Convert the result if we identified a Converter
        if (converter != null) {
            return (converter.getAsString(context, component, value));
        } else if (value == null) {
            return ("");
        } else if (value instanceof String) {
            return ((String) value);
        } else {
            return (value.toString());
        }

    }


}
