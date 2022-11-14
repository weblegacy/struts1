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
import java.util.List;
import java.util.Map;

import org.apache.struts.faces.component.HtmlComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.el.ValueExpression;
import jakarta.faces.component.EditableValueHolder;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.ValueHolder;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.render.Renderer;


/**
 * Abstract base class for concrete implementations of
 * {@code jakarta.faces.render.Renderer} for the
 * <em>Struts-Faces Integration Library</em>.
 *
 * @version $Rev$ $Date$
 */
public abstract class AbstractRenderer extends Renderer {


    // -------------------------------------------------------- Static Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(AbstractRenderer.class);


    // -------------------------------------------------------- Renderer Methods


    /**
     * Decode any new state of the specified {@code UIComponent} from the
     * request contained in the specified {@code FacesContext}, and store
     * that state on the {@code UIComponent}.
     *
     * <p>The default implementation calls {@code setSubmittedValue()}
     * unless this component has a boolean {@code disabled} or
     * {@code readonly} attribute that is set to {@code true}.</p>
     *
     * @param context {@code FacesContext} for the current request
     * @param component {@code UIComponent} to be decoded
     *
     * @throws NullPointerException if {@code context} or
     *     {@code component} is {@code null}
     */
    public void decode(FacesContext context, UIComponent component) {

        // Enforce NPE requirements in the JavaDocs
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        // Disabled or read-only components are not decoded
        if (isDisabled(component) || isReadOnly(component)) {
            return;
        }

        // Save submitted value on EditableValueHolder components
        if (component instanceof EditableValueHolder) {
            setSubmittedValue(context, component);
        }

    }


    /**
     * Render the beginning of the specified {@code UIComponent} to the
     * output stream or writer associated with the response we are
     * creating.
     *
     * <p>The default implementation calls {@code renderStart()} and
     * {@code renderAttributes()}.</p>
     *
     * @param context {@code FacesContext} for the current request
     * @param component {@code UIComponent} to be decoded
     *
     * @throws NullPointerException if {@code context} or
     *     {@code component} is {@code null}
     *
     * @throws IOException if an input/output error occurs
     */
    public void encodeBegin(FacesContext context, UIComponent component)
        throws IOException {

        // Enforce NPE requirements in the JavaDocs
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        log.trace("encodeBegin(id={}, family={}, rendererType={})",
            component.getId(), component.getFamily(), component.getRendererType());

        // Render the element and attributes for this component
        ResponseWriter writer = context.getResponseWriter();
        renderStart(context, component, writer);
        renderAttributes(context, component, writer);

    }


    /**
     * Render the children of the specified {@code UIComponent} to the
     * output stream or writer associated with the response we are
     * creating.
     *
     * <p>The default implementation iterates through the children of
     * this component and renders them.</p>
     *
     * @param context {@code FacesContext} for the current request
     * @param component {@code UIComponent} to be decoded
     *
     * @throws NullPointerException if {@code context} or
     *     {@code component} is {@code null}
     *
     * @throws IOException if an input/output error occurs
     */
    public void encodeChildren(FacesContext context, UIComponent component)
        throws IOException {

        if (context == null || component == null) {
            throw new NullPointerException();
        }

        log.trace("encodeChildren(id={}, family={}, rendererType={})",
            component.getId(), component.getFamily(), component.getRendererType());
        List<UIComponent> kids = component.getChildren();
        for (UIComponent kid : kids) {
            kid.encodeBegin(context);
            if (kid.getRendersChildren()) {
                kid.encodeChildren(context);
            }
            kid.encodeEnd(context);
        }
        log.trace("encodeChildren(id={}) end", component.getId());

    }


    /**
     * Render the ending of the specified {@code UIComponent} to the
     * output stream or writer associated with the response we are
     * creating.
     *
     * <p>The default implementation calls {@code renderEnd()}.</p>
     *
     * @param context {@code FacesContext} for the current request
     * @param component {@code UIComponent} to be decoded
     *
     * @throws NullPointerException if {@code context} or
     *     {@code component} is {@code null}
     *
     * @throws IOException if an input/output error occurs
     */
    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {

        // Enforce NPE requirements in the Javadocs
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        log.trace("encodeEnd(id={}, family={}, rendererType={})",
            component.getId(), component.getFamily(), component.getRendererType());

        // Render the element closing for this component
        ResponseWriter writer = context.getResponseWriter();
        renderEnd(context, component, writer);

    }


    // --------------------------------------------------------- Package Methods


    // ------------------------------------------------------- Protected Methods


    /**
     * Render nested child components by invoking the encode methods
     * on those components, but only when the {@code rendered}
     * property is {@code true}.
     */
    protected void encodeRecursive(FacesContext context, UIComponent component)
        throws IOException {

        // suppress rendering if "rendered" property on the component is
        // false.
        if (!component.isRendered()) {
            return;
        }

        // Render this component and its children recursively
        log.trace("encodeRecursive(id={}, family={}, rendererType={}) encodeBegin",
            component.getId(), component.getFamily(), component.getRendererType());
        component.encodeBegin(context);
        if (component.getRendersChildren()) {
            log.trace("encodeRecursive(id={}) delegating",
                component.getId());
            component.encodeChildren(context);
        } else {
            log.trace("encodeRecursive(id={}) recursing",
                component.getId());
            List<UIComponent> kids = component.getChildren();
            for (UIComponent kid : kids) {
                encodeRecursive(context, kid);
            }
        }
        log.trace("encodeRecursive(id={}) encodeEnd", component.getId());
        component.encodeEnd(context);

    }


    /**
     * Return {@code true} if the specified component is disabled.
     *
     * @param component {@code UIComponent} to be checked
     */
    protected boolean isDisabled(UIComponent component) {

        Object disabled = component.getAttributes().get("disabled");
        if (disabled == null) {
            return false;
        }
        if (disabled instanceof String) {
            return Boolean.valueOf((String) disabled).booleanValue();
        } else {
            return disabled.equals(Boolean.TRUE);
        }

    }


    /**
     * Return {@code true} if the specified component is read only.
     *
     * @param component {@code UIComponent} to be checked
     */
    protected boolean isReadOnly(UIComponent component) {

        Object readonly = component.getAttributes().get("readonly");
        if (readonly == null) {
            return false;
        }
        if (readonly instanceof String) {
            return Boolean.valueOf((String) readonly).booleanValue();
        } else {
            return readonly.equals(Boolean.TRUE);
        }

    }


    /**
     * Render the element attributes for the generated markup related to this
     * component. Simple renderers that create a single markup element
     * for this component should override this method and include calls to
     * to {@code writeAttribute()} and {@code writeURIAttribute}
     * on the specified {@code ResponseWriter}.
     *
     * <p>The default implementation does nothing.</p>
     *
     * @param context {@code FacesContext} for the current request
     * @param component {@code EditableValueHolder} component whose
     *     submitted value is to be stored
     * @param writer {@code ResponseWriter} to which the element
     *     start should be rendered
     *
     * @throws IOException if an input/output error occurs
     */
    protected void renderAttributes(FacesContext context, UIComponent component,
                                    ResponseWriter writer) throws IOException {

    }


    /**
     * Render the element end for the generated markup related to this
     * component. Simple renderers that create a single markup element
     * for this component should override this method and include a call
     * to {@code endElement()} on the specified {@code ResponseWriter}.
     *
     * <p>The default implementation does nothing.</p>
     *
     * @param context {@code FacesContext} for the current request
     * @param component {@code EditableValueHolder} component whose
     *     submitted value is to be stored
     * @param writer {@code ResponseWriter} to which the element
     *     start should be rendered
     *
     * @throws IOException if an input/output error occurs
     */
    protected void renderEnd(FacesContext context, UIComponent component,
                             ResponseWriter writer) throws IOException {

    }


    /**
     * Render any boolean attributes on the specified list that have
     * {@code true} values on the corresponding attribute of the
     * specified {@code UIComponent}.
     *
     * @param context {@code FacesContext} for the current request
     * @param component {@code EditableValueHolder} component whose
     *     submitted value is to be stored
     * @param writer {@code ResponseWriter} to which the element
     *     start should be rendered
     * @param names List of attribute names to be passed through
     *
     * @throws IOException if an input/output error occurs
     */
    protected void renderBoolean(FacesContext context,
                                 UIComponent component,
                                 ResponseWriter writer,
                                 String names[]) throws IOException {

        if (names == null) {
            return;
        }
        Map<String, Object> attributes = component.getAttributes();
        boolean flag;
        Object value;
        for (String name : names) {
            value = attributes.get(name);
            if (value != null) {
                if (value instanceof String) {
                    flag = Boolean.valueOf((String) value).booleanValue();
                } else {
                    flag = Boolean.valueOf(value.toString()).booleanValue();
                }
                if (flag) {
                    writer.writeAttribute(name, name, name);
                    flag = false;
                }
            }
        }

    }


    /**
     * Render any attributes on the specified list directly to the
     * specified {@code ResponseWriter} for which the specified
     * {@code UIComponent} has a non-{@code null} attribute value.
     * This method may be used to "pass through" commonly used attribute
     * name/value pairs with a minimum of code.
     *
     * @param context {@code FacesContext} for the current request
     * @param component {@code EditableValueHolder} component whose
     *     submitted value is to be stored
     * @param writer {@code ResponseWriter} to which the element
     *     start should be rendered
     * @param names List of attribute names to be passed through
     *
     * @throws IOException if an input/output error occurs
     */
    protected void renderPassThrough(FacesContext context,
                                     UIComponent component,
                                     ResponseWriter writer,
                                     String names[]) throws IOException {

        if (names == null) {
            return;
        }
        Map<String, Object> attributes = component.getAttributes();
        Object value;
        for (String name : names) {
            value = attributes.get(name);
            if (value != null) {
                if (value instanceof String) {
                    writer.writeAttribute(name, value, name);
                } else {
                    writer.writeAttribute(name, value.toString(), name);
                }
            }
        }

    }


    /**
     * Render the element start for the generated markup related to this
     * component. Simple renderers that create a single markup element
     * for this component should override this method and include a call
     * to {@code startElement()} on the specified {@code ResponseWriter}.
     *
     * <p>The default implementation does nothing.</p>
     *
     * @param context {@code FacesContext} for the current request
     * @param component {@code EditableValueHolder} component whose
     *  submitted value is to be stored
     * @param writer {@code ResponseWriter} to which the element
     *  start should be rendered
     *
     * @throws IOException if an input/output error occurs
     */
    protected void renderStart(FacesContext context, UIComponent component,
                               ResponseWriter writer) throws IOException {

    }


    /**
     * If a submitted value was included on this request, store it in the
     * component as appropriate.
     *
     * <p>The default implementation determines whether this component
     * implements {@code EditableValueHolder}.  If so, it checks for a
     * request parameter with the same name as the {@code clientId}
     * of this {@code UIComponent}. If there is such a parameter, its
     * value is passed (as a String) to the {@code setSubmittedValue()}
     * method on the {@code EditableValueHolder} component.</p>
     *
     * @param context {@code FacesContext} for the current request
     * @param component {@code EditableValueHolder} component whose
     *     submitted value is to be stored
     */
    protected void setSubmittedValue
        (FacesContext context, UIComponent component) {

        if (!(component instanceof EditableValueHolder)) {
            return;
        }
        String clientId = component.getClientId(context);
        Map<String, String> parameters = context.getExternalContext().getRequestParameterMap();
        if (parameters.containsKey(clientId)) {
            log.atTrace()
                .setMessage("setSubmittedValue({},{})")
                .addArgument(clientId)
                .addArgument(() -> parameters.get(clientId))
                .log();
            component.getAttributes().put("submittedValue",
                                          parameters.get(clientId));
        }

    }


    // --------------------------------------------------------- Private Methods


    /**
     * Decode the current state of the specified UIComponent from the
     * request contained in the specified {@code FacesContext}, and
     * attempt to convert this state information into an object of the
     * type required for this component.
     *
     * @param context FacesContext for the request we are processing
     * @param component UIComponent to be decoded
     *
     * @throws NullPointerException if context or component is null
     */
    /*
    public void decode(FacesContext context, UIComponent component) {

        // Enforce NPE requirements in the JavaDocs
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
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
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
     * Add an error message denoting a conversion failure.
     *
     * @param context The {@code FacesContext} for this request
     * @param component The {@code UIComponent} that experienced
     *     the conversion failure
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
     * Convert the String representation of this component's value
     * to the corresponding Object representation. The default
     * implementation utilizes the {@code getAsObject()} method of any
     * associated {@code Converter}.<
     *
     * @param context The {@code FacesContext} for this request
     * @param component The {@code UIComponent} whose value is
     *     being converted
     * @param value The String representation to be converted
     *
     * @throws ConverterException if conversion fails
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
        if (converter == null && vb != null) {
            Class<?> type = vb.getType(context);
            if (type == null || type == String.class) {
                return value; // No conversion required for Strings
            }
            // Acquire implicit by-type Converter (if any)
            converter = context.getApplication().createConverter(type);
        }

        // Convert the result if we identified a Converter
        if (converter != null) {
            return converter.getAsObject(context, component, value);
        } else {
            return value;
        }

    }
    */


    /**
     * <p>Convert the Object representation of this component's value
     * to the corresponding String representation.  The default implementation
     * utilizes the {@code getAsString()} method of any associated
     * {@code Converter}.</p>
     *
     * @param context The {@code FacesContext} for this request
     * @param component The {@code UIComponent} whose value is
     *  being converted
     * @param value The Object representation to be converted
     *
     * @throws ConverterException if conversion fails
     */
    protected String getAsString(FacesContext context, UIComponent component,
                                 Object value) throws ConverterException {

        // Identify any Converter associated with this component value
        ValueExpression vb = component.getValueExpression("value");
        Converter<Object> converter = null;
        if (component instanceof ValueHolder) {
            // Acquire explicitly assigned Converter (if any)
            @SuppressWarnings("unchecked")
            Converter<Object> conv = ((ValueHolder) component).getConverter();
            converter = conv;
        }
        if (converter == null && vb != null) {
            // Acquire implicit by-type Converter (if any)
            Class<?> type = vb.getType(context.getELContext());
            if (type != null) {
                @SuppressWarnings("unchecked")
                Converter<Object> conv = context.getApplication().createConverter(type);
                converter = conv;
            }
        }

        // Convert the result if we identified a Converter
        if (converter != null) {
            return converter.getAsString(context, component, value);
        } else if (value == null) {
            return "";
        } else if (value instanceof String) {
            return (String) value;
        } else {
            return value.toString();
        }

    }


    /**
     * Return {@code true} if we should render as XHTML.
     *
     * @param component The component we are rendering
     */
    protected boolean isXhtml(UIComponent component) {
        final HtmlComponent htmlComponent = searchComponent(HtmlComponent.class, component);

        return htmlComponent == null ? false : htmlComponent.isXhtml();
    }


    /**
     * Search the give {@code UIComponent} in the component-tree.
     *
     * @param component The entry-point into component-tree.
     *
     * @return The {@code UIComponent} or {@code null} if the
     *     {@code component} is not found.
     */
    protected <T extends UIComponent> T searchComponent(Class<T> clazz, UIComponent component) {
        while (component != null) {
            if (clazz.isInstance(component)) {
                return clazz.cast(component);
            }
            component = component.getParent();
        }
        return null;
    }
}