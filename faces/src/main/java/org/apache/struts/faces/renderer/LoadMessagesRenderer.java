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

import java.util.Locale;

import org.apache.struts.Globals;
import org.apache.struts.faces.component.LoadMessagesComponent;
import org.apache.struts.faces.util.MessagesMap;
import org.apache.struts.faces.util.Utils;
import org.apache.struts.util.MessageResources;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

/**
 * {@code Renderer} implementation for the {@code LoadMessages} tag
 * from the <em>Struts-Faces Integration Library</em>.
 *
 * @version $Rev$ $Date$
 */
public class LoadMessagesRenderer  extends AbstractRenderer {


    /**
     * Expose a {@code MessagesMap} wrapping the specified
     * {@code MessageResources} instance, for the {@code Locale}
     * specified in the view root component of the current view.
     */
    public void encodeBegin(FacesContext context, UIComponent component) {

        final LoadMessagesComponent comp = (LoadMessagesComponent)
                component;

        final ExternalContext econtext = context.getExternalContext();

        // Acquire the Locale to be wrapped
        final Locale locale =
                FacesContext.getCurrentInstance().getViewRoot().getLocale();

        // Acquire the MessageResources to be wrapped
        MessageResources messages = null;
        if (comp.getMessages() == null) {
            messages = Utils.getMapValue(MessageResources.class,
                    econtext.getRequestMap(), Globals.MESSAGES_KEY);
            if (messages == null) {
                messages = Utils.getMapValue(MessageResources.class,
                        econtext.getApplicationMap(), Globals.MESSAGES_KEY);
            }
        } else {
            messages = Utils.getMapValue(MessageResources.class,
                    econtext.getApplicationMap(), comp.getMessages());
        }

        // Expose a Map instance under the specified request attribute key
        econtext.getRequestMap()
                .put(comp.getVar(), new MessagesMap(messages, locale));
    }
}