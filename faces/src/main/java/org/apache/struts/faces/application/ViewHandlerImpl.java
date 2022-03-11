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
import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;


/**
 * <p>Custom <code>ViewHandler</code> implementation that adds features
 * specific to the Struts-Faces Integration Library.  It leverages the
 * "decorator pattern" customization strategy that JSF supports, by
 * delegating most processing to the <code>ViewHandler</code> instance
 * handed to our constructor.</p>
 */

public class ViewHandlerImpl extends ViewHandler {


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Construct a <code>ViewHandlerImpl</code> decorating the
     * specified <code>ViewHandler</code> instance.</p>
     *
     * @param handler <code>ViewHandler</code> to be decorated
     */
    public ViewHandlerImpl(ViewHandler handler) {
        if (log.isDebugEnabled()) {
            log.debug("Creating ViewHandler instance, wrapping handler " +
                      handler);
        }
        this.handler = handler;
    }


    // ------------------------------------------------------ Instance Variables


    /**
     * <p>The <code>ViewHandler</code> instance that we are decorating.</p>
     */
    private ViewHandler handler = null;


    // -------------------------------------------------------- Static Variables


    /**
     * <p>The <code>Log</code> instance for this class.</p>
     */
    private static final Log log =
        LogFactory.getLog(ViewHandlerImpl.class);



    // -------------------------------------------------------------- Properties


    /**
     * <p>Return the <code>ViewHandler</code> instance we are decorating.</p>
     */
    public ViewHandler getHandler() {
        return this.handler;
    }


    /**
     * <p>Set the <code>ViewHandler</code> instance we are decorating.</p>
     *
     * @param handler <code>ViewHandler</code> instance to decorate
     */
    public void setHandler(ViewHandler handler) {
        this.handler = handler;
    }


    // ----------------------------------------------------- Specialized Methods


    /**
     * <p>If the Struts application has set a <code>Locale</code>, pass it
     * on to JSF prior to delegating the actual rendering.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param view <code>UIViewRoot</code> to be rendered
     */
    public void renderView(FacesContext context, UIViewRoot view)
        throws IOException, FacesException {

        if (log.isDebugEnabled()) {
            log.debug("renderView(" + view.getViewId() + ")");
        }
        ExternalContext econtext = context.getExternalContext();
        if (econtext.getSession(false) != null) {
            Locale locale = (Locale)
                econtext.getSessionMap().get(Globals.LOCALE_KEY);
            if (locale != null) {
                if (log.isTraceEnabled()) {
                    log.trace("Setting view locale to " + locale);
                }
                view.setLocale(locale);
            }
        }
        handler.renderView(context, view);

    }


    // ------------------------------------------------------- Delegated Methods


    // See ViewHandler JavaDocs for method descriptions


    public Locale calculateLocale(FacesContext context) {
        return handler.calculateLocale(context);
    }


    public String calculateRenderKitId(FacesContext context) {
        return handler.calculateRenderKitId(context);
    }


    public UIViewRoot createView(FacesContext context, String viewId) {
        return handler.createView(context, viewId);
    }


    public String getActionURL(FacesContext context, String viewId) {
        return handler.getActionURL(context, viewId);
    }


    public String getResourceURL(FacesContext context, String viewId) {
        return handler.getResourceURL(context, viewId);
    }


    public UIViewRoot restoreView(FacesContext context, String viewId) {
        return handler.restoreView(context, viewId);
    }


    public void writeState(FacesContext context) throws IOException {
        handler.writeState(context);
    }


}
