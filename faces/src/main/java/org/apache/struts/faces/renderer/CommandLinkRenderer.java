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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIForm;
import jakarta.faces.component.UINamingContainer;
import jakarta.faces.component.UIParameter;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;
import jakarta.faces.event.ActionEvent;


/**
 * <p><code>Renderer</code> implementation for the <code>commandLink</code>
 * tag from the <em>Struts-Faces Integration Library</em>.</p>
 *
 * @version $Rev$ $Date$
 */

public class CommandLinkRenderer extends AbstractRenderer {


    // -------------------------------------------------------- Static Variables


    /**
     * <p>Token for private names.</p>
     */
    private final static String TOKEN =
        "org_apache_struts_faces_renderer_CommandLinkRenderer";


    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(CommandLinkRenderer.class);


    // ---------------------------------------------------------- Public Methods


    /**
     * <p>Perform setup processing that will be required for decoding the
     * incoming request.</p>
     *
     * @param context FacesContext for the request we are processing
     * @param component UIComponent to be processed
     *
     * @exception NullPointerException if <code>context</code>
     *  or <code>component</code> is null
     */
    public void decode(FacesContext context, UIComponent component) {

        // Implement spec requirements on NullPointerException
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }

        // Skip this component if it is not relevant
        if (!component.isRendered() || isDisabled(component) ||
            isReadOnly(component)) {
            return;
        }

        // Set up variables we will need
        UIForm form = null;
        UIComponent parent = component.getParent();
        while (parent != null) {
            if (parent instanceof UIForm) {
                form = (UIForm) parent;
                break;
            }
            parent = parent.getParent();
        }
        if (form == null) {
            log.warn("CommandLinkComponent not nested inside UIForm, ignored");
            return;
        }

        // Was this the component that submitted this form?
        String paramId = TOKEN;
        String value = context.getExternalContext().getRequestParameterMap().get(paramId);
        if ((value == null) || !value.equals(component.getClientId(context))) {
            log.trace("decode({}) --> not active", component.getId());
            return;
        }

        // Queue an ActionEvent from this component
        log.trace("decode({}) --> queueEvent()", component.getId());
        component.queueEvent(new ActionEvent(component));

    }


    private static String passThrough[] =
    { "accesskey", "charset", "dir", "hreflang", "lang", "onblur",
      /* "onclick", */ "ondblclick", "onfocus", "onkeydown",
      "onkeypress", "onkeyup", "onmousedown", "onmousemove",
      "onmouseout", "onmouseover", "onmouseup", "rel", "rev",
      "style", "tabindex", "target", "title", "type" };


    /**
     * <p>Render the beginning of a hyperlink to submit this form.</p>
     *
     * @param context FacesContext for the request we are processing
     * @param component UIComponent to be rendered
     * @param writer ResponseWriter we are rendering to
     *
     * @exception IOException if an input/output error occurs while rendering
     * @exception NullPointerException if <code>context</code>
     *  or <code>component</code> is null
     */
    public void renderStart(FacesContext context, UIComponent component,
                            ResponseWriter writer)
        throws IOException {

        // Skip this component if it is not relevant
        if (!component.isRendered() || isDisabled(component) ||
            isReadOnly(component)) {
            return;
        }

        // Set up variables we will need
        UIForm form = null;
        UIComponent parent = component.getParent();
        while (parent != null) {
            if (parent instanceof UIForm) {
                form = (UIForm) parent;
                break;
            }
            parent = parent.getParent();
        }
        if (form == null) {
            log.warn("CommandLinkComponent not nested inside UIForm, ignored");
            return;
        }
        String formClientId = form.getClientId(context);

        // If this is the first nested command link inside this form,
        // render a hidden variable to identify which link did the submit
        String key = formClientId + UINamingContainer.getSeparatorChar(context) + TOKEN;
        if (context.getExternalContext().getRequestMap().get(key) == null) {
            writer.startElement("input", null);
            writer.writeAttribute("name", TOKEN, null);
            writer.writeAttribute("type", "hidden", null);
            writer.writeAttribute("value", "", null);
            writer.endElement("input");
            context.getExternalContext().getRequestMap().put
                (key, Boolean.TRUE);
        }


        // Render the beginning of this hyperlink
        writer.startElement("a", component);

    }


    /**
     * <p>Render the attributes of a hyperlink to submit this form.</p>
     *
     * @param context FacesContext for the request we are processing
     * @param component UIComponent to be rendered
     * @param writer ResponseWriter we are rendering to
     *
     * @exception IOException if an input/output error occurs while rendering
     * @exception NullPointerException if <code>context</code>
     *  or <code>component</code> is null
     */
    public void renderAttributes(FacesContext context, UIComponent component,
                                 ResponseWriter writer)
        throws IOException {

        // Skip this component if it is not relevant
        if (!component.isRendered() || isDisabled(component) ||
            isReadOnly(component)) {
            return;
        }

        // Set up variables we will need
        UIComponent form = null;
        UIComponent parent = component.getParent();
        while (parent != null) {
            if (parent instanceof UIForm ||
                "org.apache.myfaces.trinidad.Form".equals(parent.getFamily()) ||
                "oracle.adf.Form".equals(parent.getFamily())) {
                form = parent;
                break;
            }
            parent = parent.getParent();
        }
        if (form == null) {
            log.warn("CommandLinkComponent not nested inside UIForm, ignored");
            return;
        }
        String formClientId = form.getClientId(context);

        // Render the attributes of this hyperlink
        if (component.getId() != null) {
            writer.writeAttribute("id", component.getClientId(context), "id");
        }
        writer.writeAttribute("href", "#", null);
        String styleClass = (String)
            component.getAttributes().get("styleClass");
        if (styleClass != null) {
            writer.writeAttribute("class", styleClass, "styleClass");
        }
        renderPassThrough(context, component, writer, passThrough);

        // Render the JavaScript content of the "onclick" element
        StringBuilder sb = new StringBuilder();
        sb.append("document.forms['");
        sb.append(formClientId);
        sb.append("']['");
        sb.append(TOKEN);
        sb.append("'].value='");
        sb.append(component.getClientId(context));
        sb.append("';");
        for (UIComponent kid : component.getChildren()) {
            if (!(kid instanceof UIParameter)) {
                continue;
            }
            sb.append("document.forms['");
            sb.append(formClientId);
            sb.append("']['");
            sb.append((String) kid.getAttributes().get("name"));
            sb.append("'].value='");
            sb.append((String) kid.getAttributes().get("value"));
            sb.append("';");
        }
        sb.append("document.forms['");
        sb.append(formClientId);
        sb.append("'].submit(); return false;");
        writer.writeAttribute("onclick", sb.toString(), null);

        // Render the component value as the hyperlink text
        Object value = component.getAttributes().get("value");
        if (value != null) {
            if (value instanceof String) {
                writer.write((String) value);
            } else {
                writer.write(value.toString());
            }
        }

    }


    /**
     * <p>Render the end of a hyperlink to submit this form.</p>
     *
     * @param context FacesContext for the request we are processing
     * @param component UIComponent to be rendered
     * @param writer ResponseWriter we are rendering to
     *
     * @exception IOException if an input/output error occurs while rendering
     * @exception NullPointerException if <code>context</code>
     *  or <code>component</code> is null
     */
    public void renderEnd(FacesContext context, UIComponent component,
                          ResponseWriter writer)
        throws IOException {

        // Skip this component if it is not relevant
        if (!component.isRendered() || isDisabled(component) ||
            isReadOnly(component)) {
            return;
        }

        // Render the beginning of this hyperlink
        writer.endElement("a");

    }
}