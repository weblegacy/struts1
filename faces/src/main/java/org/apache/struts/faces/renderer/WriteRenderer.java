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

import org.apache.struts.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.component.ValueHolder;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;


/**
 * <p><code>Renderer</code> implementation for the <code>write</code> tag
 * from the <em>Struts-Faces Integration Library</em>.</p>
 *
 * @version $Rev$ $Date$
 */

public class WriteRenderer extends AbstractRenderer {


    // -------------------------------------------------------- Static Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(WriteRenderer.class);


    // ---------------------------------------------------------- Public Methods


    /**
     * <p>Encode the specified text to our response.</p>
     *
     * @param context FacesContext for the response we are creating
     * @param component Component to be rendered
     *
     * @exception IOException if an input/output error occurs
     * @exception NullPointerException if <code>context</code>
     *  or <code>component</code> is <code>null</code>
     */
    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {

        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }

        ResponseWriter writer = context.getResponseWriter();
        String id = component.getId();
        if ((id != null) && id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
            id = null;
        }
        String style =
            (String) component.getAttributes().get("style");
        String styleClass =
            (String) component.getAttributes().get("styleClass");
        log.trace("id='{}', style='{}', styleClass='{}'",
            id, style, styleClass);
        if ((id != null) || (style != null) || (styleClass != null)) {
            writer.startElement("span", component);
            if (id != null) {
                writer.writeAttribute("id", component.getClientId(context),
                                      "id");
            }
            if (style != null) {
                writer.writeAttribute("style", style, "style");
            }
            if (styleClass != null) {
                writer.writeAttribute("class", styleClass, "styleClass");
            }
            writer.writeText("", null);
        }
        String text = getText(context, component);
        log.atTrace()
            .setMessage("encodeEnd({},{})")
            .addArgument(() ->  component.getClientId(context))
            .addArgument(text)
            .log();
        writer.write(text);
        if ((id != null) || (style != null) || (styleClass != null)) {
            writer.endElement("span");
        }

    }


    // ------------------------------------------------------- Protected Methods


    /**
     * <p>Return the text to be rendered for this component, optionally
     * filtered if requested.</p>
     *
     * @param context FacesContext for the response we are creating
     * @param component Component to be rendered
     */
    protected String getText(FacesContext context, UIComponent component) {

        String text = getAsString(context, component,
                                  ((ValueHolder) component).getValue());
        Boolean filter = (Boolean) component.getAttributes().get("filter");
        if (filter == null) {
            filter = Boolean.FALSE;
        }
        if (filter.booleanValue()) {
            return (ResponseUtils.filter(text));
        } else {
            return (text);
        }

    }
}