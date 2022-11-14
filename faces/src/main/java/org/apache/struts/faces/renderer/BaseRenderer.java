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

import org.apache.struts.faces.util.StrutsContext;
import org.apache.struts.faces.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;


/**
 * {@code Renderer} implementation for the {@code base} tag
 * from the <em>Struts-Faces Integration Library</em>.
 *
 * @version $Rev$ $Date$
 */
public class BaseRenderer extends AbstractRenderer {


    // -------------------------------------------------------- Static Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(BaseRenderer.class);


    // ---------------------------------------------------------- Public Methods


    /**
     * Render an HTML {@code base} element.
     *
     * @param context {@code FacesContext} for the request we are processing
     * @param component {@code UIComponent} to be rendered
     *
     * @throws IOException if an input/output error occurs while rendering
     * @throws NullPointerException if {@code context} or {@code component}
     *     is null
     */
    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {

        if (context == null || component == null) {
            throw new NullPointerException();
        }

        final String uri = StrutsContext.uri(context);

        log.trace("viewId='{}' --> uri='{}'",
            context.getViewRoot().getViewId(), uri);

        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("base", component);
        writer.writeURIAttribute("href", uri, null);
        String target = Utils.getMapValue(String.class, component.getAttributes(), "target");
        if (target != null) {
            writer.writeAttribute("target", target, "target");
        }
        writer.endElement("base");
        writer.writeText("\n", null);

    }
}