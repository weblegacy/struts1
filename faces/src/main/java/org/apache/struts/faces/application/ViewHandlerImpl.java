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

package org.apache.struts.faces.application;


import java.io.IOException;
import java.util.Locale;

import org.apache.struts.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.faces.FacesException;
import jakarta.faces.application.ViewHandler;
import jakarta.faces.application.ViewHandlerWrapper;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;


/**
 * Custom {@code ViewHandler} implementation that adds features
 * specific to the Struts-Faces Integration Library. It leverages the
 * "decorator pattern" customization strategy that JSF supports, by
 * delegating most processing to the {@code ViewHandler} instance
 * handed to our constructor.
 */
public class ViewHandlerImpl extends ViewHandlerWrapper {


    // -------------------------------------------------------- Static Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(ViewHandlerImpl.class);


    // ------------------------------------------------------------ Constructors


    /**
     * Construct a {@code ViewHandlerImpl} decorating the
     * specified {@code ViewHandler} instance.
     *
     * @param oldViewHandler {@code ViewHandler} to be decorated
     */
    public ViewHandlerImpl(ViewHandler oldViewHandler) {
        super(oldViewHandler);
        log.debug("Creating ViewHandler instance, wrapping handler {}",
            oldViewHandler);
    }


    // ----------------------------------------------------- Specialized Methods


    /**
     * If the Struts application has set a {@code Locale}, pass it
     * on to JSF prior to delegating the actual rendering.
     *
     * @param context {@code FacesContext} for the current request
     * @param viewToRender {@code UIViewRoot} to be rendered
     */
    @Override
    public void renderView(FacesContext context, UIViewRoot viewToRender)
            throws IOException, FacesException {

        log.debug("renderView({})", viewToRender.getViewId());
        ExternalContext econtext = context.getExternalContext();
        if (econtext.getSession(false) != null) {
            Locale locale = (Locale)
                econtext.getSessionMap().get(Globals.LOCALE_KEY);
            if (locale != null) {
                log.trace("Setting view locale to {}", locale);
                viewToRender.setLocale(locale);
            }
        }
        super.renderView(context, viewToRender);
    }
}