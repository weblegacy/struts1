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
import java.util.Map;

import org.apache.struts.Globals;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.faces.component.FormComponent;
import org.apache.struts.faces.util.StrutsContext;
import org.apache.struts.faces.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UINamingContainer;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;
import jakarta.servlet.http.HttpSession;


/**
 * <p><code>Renderer</code> implementation for the <code>form</code> tag
 * from the <em>Struts-Faces Integration Library</em>.</p>
 *
 * @version $Rev$ $Date$
 */

public class FormRenderer extends AbstractRenderer {


    // -------------------------------------------------------- Static Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(FormRenderer.class);


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

        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
        String clientId = component.getClientId(context);
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        log.atDebug()
            .setMessage("decode({}) --> {}")
            .addArgument(clientId)
            .addArgument(() -> map.containsKey(clientId))
            .log();
        component.getAttributes().put
            ("submitted",
             map.containsKey(clientId) ? Boolean.TRUE : Boolean.FALSE);

    }


    private static String passThrough[] =
    { "enctype", "method", "onreset", "onsubmit", "style", "target", };


    /**
     * <p>Render the beginning of an HTML <code>&lt;form&gt;</code>
     * control.</p>
     *
     * @param context FacesContext for the request we are processing
     * @param component UIComponent to be rendered
     *
     * @exception IOException if an input/output error occurs while rendering
     * @exception NullPointerException if <code>context</code>
     *  or <code>component</code> is null
     */
    public void encodeBegin(FacesContext context, UIComponent component)
        throws IOException {

        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }

        // Calculate and cache the form name
        FormComponent form = (FormComponent) component;
        String action = form.getAction();
        ModuleConfig moduleConfig = StrutsContext.getModuleConfig(context);
        ActionConfig actionConfig = moduleConfig.findActionConfig(action);
        if (actionConfig == null) {
            throw new IllegalArgumentException("Cannot find action '" +
                                               action + "' configuration");
        }
        String beanName = actionConfig.getAttribute();
        if (beanName != null) {
            form.getAttributes().put("beanName", beanName);
        }

        // Look up attribute values we need
        String clientId = component.getClientId(context);
        log.debug("encodeBegin({})", clientId);
        String styleClass = Utils.getMapValue(String.class,
                component.getAttributes(), "styleClass");

        // Render the beginning of this form
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("form", form);
        writer.writeAttribute("id", clientId, "clientId");
        if (beanName != null) {
            writer.writeAttribute("name", beanName, null);
        }
        writer.writeAttribute("action", action(context, component), "action");
        if (styleClass != null) {
            writer.writeAttribute("class", styleClass, "styleClass");
        }
        if (component.getAttributes().get("method") == null) {
            writer.writeAttribute("method", "post", null);
        }
        renderPassThrough(context, component, writer, passThrough);
        writer.writeText("\n", null);

        // Add a marker used by our decode() method to note this form is submitted
        writer.startElement("input", form);
        writer.writeAttribute("type", "hidden", null);
        writer.writeAttribute("name", clientId, null);
        writer.writeAttribute("value", clientId, null);
        writer.endElement("input");
        writer.writeText("\n", null);

        // Add a transaction token if necessary
        HttpSession session = (HttpSession)
            context.getExternalContext().getSession(false);
        if (session != null) {
            String token = (String)
                session.getAttribute(Globals.TRANSACTION_TOKEN_KEY);
            if (token != null) {
                writer.startElement("input", form);
                writer.writeAttribute("type", "hidden", null);
                writer.writeAttribute
                    ("name", "org.apache.struts.taglib.html.TOKEN", null);
                writer.writeAttribute("value", token, null);
                writer.endElement("input");
                writer.writeText("\n", null);
            }
        }

        // Create an instance of the form bean if necessary
        if (component instanceof FormComponent) {
            ((FormComponent) component).createActionForm(context);
        }

    }


    /**
     * <p>Render the ending of an HTML <code>&lt;form&gt;</code>
     * control.</p>
     *
     * @param context FacesContext for the request we are processing
     * @param component UIComponent to be rendered
     *
     * @exception IOException if an input/output error occurs while rendering
     * @exception NullPointerException if <code>context</code>
     *  or <code>component</code> is null
     */
    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {

        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
        String clientId = component.getClientId(context);
        log.debug("encodeEnd({})", clientId);
        ResponseWriter writer = context.getResponseWriter();

        // Render the hidden variable our decode() method uses to detect submits
        writer.startElement("input", component);
        writer.writeAttribute("type", "hidden", null);
        writer.writeAttribute("name", component.getClientId(context), null);
        writer.writeAttribute("value", component.getClientId(context), null);
        writer.endElement("input");
        writer.write("\n");

        // Write our state information (if necessary)
        context.getApplication().getViewHandler().writeState(context);

        // Render the ending of this form
        writer.endElement("form");
        writer.writeText("\n", null);

        // Render focus JavaScript if requested
        if (!(component instanceof FormComponent)) {
            return;
        }
        String focus = (String) component.getAttributes().get("focus");
        if (focus == null) {
            return;
        }
        String focusIndex =
            (String) component.getAttributes().get("focusIndex");
        writer.writeText("\n", null);
        FormComponent form = (FormComponent) component;
        writer.startElement("script", form);
        writer.writeAttribute("type", "text/javascript", null);
        if (!isXhtml(component)) {
            writer.writeAttribute("language", "JavaScript", null);
        }
        writer.writeText("\n", null);
//        if (!isXhtml(component)) {
//            writer.write("<!--\n");
//        }

        StringBuilder sb = new StringBuilder("document.forms[\"");
        sb.append(clientId);
        sb.append("\"].elements[\"");
        sb.append(component.getClientId(context));
        sb.append(UINamingContainer.getSeparatorChar(context));
        sb.append(focus);
        sb.append("\"]");
        String focusControl = sb.toString();

        writer.write("  var focusControl = ");
        writer.write(focusControl);
        writer.write(";\n");
        writer.write("  if (focusControl.type != \"hidden\") {\n");
        writer.write("    ");
        writer.write(focusControl);
        if (focusIndex != null) {
            writer.write("[");
            writer.write(focusIndex);
            writer.write("]");
        }
        writer.write(".focus();\n");
        writer.write("  }\n");

//        if (!isXhtml(component)) {
//            writer.write("// -->\n");
//        }
        writer.endElement("script");
        writer.writeText("\n", null);

    }


    // ------------------------------------------------------- Protected Methods


    /**
     * <p>Calculate and return the value to be specified for the
     * <code>action</action> attribute on the <code>&lt;form&gt;</code>
     * element to be rendered.</p>
     *
     * @param context FacesContext for the current request
     * @param component Component being processed
     */
    protected String action(FacesContext context, UIComponent component) {

        String actionURL =
            context.getApplication().getViewHandler().
            getActionURL(context, context.getViewRoot().getViewId());
        log.trace("getActionURL({}) --> {}", context.getViewRoot().getViewId(), actionURL);
        return (context.getExternalContext().encodeActionURL(actionURL));

    }
}