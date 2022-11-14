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

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.InvalidCancelException;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.faces.Constants;
import org.apache.struts.faces.component.FormComponent;
import org.apache.struts.tiles.TilesRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.faces.FactoryFinder;
import jakarta.faces.application.ViewHandler;
import jakarta.faces.component.UICommand;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.FacesContextFactory;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.lifecycle.Lifecycle;
import jakarta.faces.lifecycle.LifecycleFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * <p>Concrete implementation of <code>RequestProcessor</code> that
 * implements the standard Struts request processing lifecycle on a
 * request that was received as an <code>ActionEvent</code> by our
 * associated <code>ActionListener</code>.  It replaces the request processor
 * instance normally configured by Struts+Tiles, so it must support non-Faces
 * requests as well.</p>
 *
 * @version $Rev$ $Date$
 */

public class FacesTilesRequestProcessor extends TilesRequestProcessor {
    private static final long serialVersionUID = -762245366421871701L;


    // ------------------------------------------------------ Instance Variables


    /**
     * The {@code Log} instance for this class.
     */
    private transient final Logger log =
        LoggerFactory.getLogger(FacesTilesRequestProcessor.class);

    /**
     * <p>The lifecycle id.</p>
     */
    public static final String LIFECYCLE_ID_ATTR = "jakarta.faces.LIFECYCLE_ID";


    // ------------------------------------------------------- Protected Methods


    /**
     * <p>Set up a Faces Request if we are not already processing one.  Next,
     * create a new view if the specified <code>uri</code> is different from
     * the current view identifier.  Finally, cause the new view to be
     * rendered, and call <code>FacesContext.responseComplete()</code> to
     * indicate that this has already been done.</p>
     *
     * @param uri Context-relative path to forward to
     * @param request Current page request
     * @param response Current page response
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    protected void doForward(String uri,
                             HttpServletRequest request,
                             HttpServletResponse response)
        throws IOException, ServletException {

        if (log.isDebugEnabled()) {
            log.debug("doForward(" + uri + ")");
        }

        // Remove the current ActionEvent (if any)
        request.removeAttribute(Constants.ACTION_EVENT_KEY);

        // Process a Struts controller request normally
        if (isStrutsRequest(uri)) {
            if (response.isCommitted()) {
                if (log.isTraceEnabled()) {
                    log.trace("  super.doInclude(" + uri + ")");
                }
                super.doInclude(uri, request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("  super.doForward(" + uri + ")");
                }
                super.doForward(uri, request, response);
            }
            return;
        }

        // Create a FacesContext for this request if necessary
        LifecycleFactory lf = (LifecycleFactory)
            FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        Lifecycle lifecycle =
            lf.getLifecycle(getLifecycleId());
        boolean created = false;
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null) {
            if (log.isTraceEnabled()) {
                log.trace("  Creating new FacesContext for '" + uri + "'");
            }
            created = true;
            FacesContextFactory fcf = (FacesContextFactory)
                FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
            context = fcf.getFacesContext(servlet.getServletContext(),
                                          request, response, lifecycle);
        }

        // Create a new view root
        ViewHandler vh = context.getApplication().getViewHandler();
        if (log.isTraceEnabled()) {
            log.trace("  Creating new view for '" + uri + "'");
        }
        context.setViewRoot(vh.createView(context, uri));

        // Cause the view to be rendered
        if (log.isTraceEnabled()) {
            log.trace("  Rendering view for '" + uri + "'");
        }
        try {
            lifecycle.render(context);
        } finally {
            if (created) {
                if (log.isTraceEnabled()) {
                    log.trace("  Releasing context for '" + uri + "'");
                }
                context.release();
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("  Rendering completed");
                }
            }
        }

    }


    // Override default processing to provide logging
    protected void internalModuleRelativeForward
        (String uri, HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        if (log.isTraceEnabled()) {
            log.trace("Performing internal module relative forward to '" +
                      uri + "'");
        }
        super.internalModuleRelativeForward(uri, request, response);

    }


    // Override default processing to provide logging
    protected Action processActionCreate(HttpServletRequest request,
                                         HttpServletResponse response,
                                         ActionMapping mapping)
        throws IOException {

        if (log.isTraceEnabled()) {
            log.trace("Performing standard action create");
        }
        Action result = super.processActionCreate(request, response, mapping);
        if (log.isDebugEnabled()) {
            log.debug("Standard action create returned " +
                      result.getClass().getName() + " instance");
        }
        return (result);

    }


    // Override default processing to provide logging
    protected ActionForm processActionForm(HttpServletRequest request,
                                           HttpServletResponse response,
                                           ActionMapping mapping) {
        if (log.isTraceEnabled()) {
            log.trace("Performing standard action form processing");
            String attribute = mapping.getAttribute();
            if (attribute != null) {
                String name = mapping.getName();
                FormBeanConfig fbc = moduleConfig.findFormBeanConfig(name);
                if (fbc != null) {
                    if ("request".equals(mapping.getScope())) {
                        log.trace("  Bean in request scope = " +
                                  request.getAttribute(attribute));
                    } else {
                        log.trace("  Bean in session scope = " +
                                  request.getSession().getAttribute(attribute));
                    }
                } else {
                    log.trace("  No FormBeanConfig for '" + name + "'");
                }
            } else {
                log.trace("  No form bean for this action");
            }
        }
        ActionForm result =
            super.processActionForm(request, response, mapping);
        if (log.isDebugEnabled()) {
            log.debug("Standard action form returned " +
                      result);
        }
        return (result);


    }


    // Override default processing to provide logging
    protected ActionForward processActionPerform(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 Action action,
                                                 ActionForm form,
                                                 ActionMapping mapping)
        throws IOException, ServletException {

        if (log.isTraceEnabled()) {
            log.trace("Performing standard action perform");
        }
        ActionForward result =
            super.processActionPerform(request, response, action,
                                       form, mapping);
        if (log.isDebugEnabled()) {
            log.debug("Standard action perform returned " +
                      (result == null ? "NULL" :
                      result.getPath()) + " forward path");
        }
        return (result);

    }


    // Override default processing to provide logging
    protected boolean processForward(HttpServletRequest request,
                                     HttpServletResponse response,
                                     ActionMapping mapping)
        throws IOException, ServletException {

        if (log.isTraceEnabled()) {
            log.trace("Performing standard forward handling");
        }
        boolean result = super.processForward
            (request, response, mapping);
        if (log.isDebugEnabled()) {
            log.debug("Standard forward handling returned " + result);
        }
        return (result);

    }


    // Override default processing to provide logging
    protected void processForwardConfig(HttpServletRequest request,
                                        HttpServletResponse response,
                                        ForwardConfig forward)
        throws IOException, ServletException {

        if (log.isTraceEnabled()) {
            log.trace("Performing standard forward config handling");
        }
        super.processForwardConfig(request, response, forward);
        if (log.isDebugEnabled()) {
            log.debug("Standard forward config handling completed");
        }

    }


    // Override default processing to provide logging
    protected boolean processInclude(HttpServletRequest request,
                                     HttpServletResponse response,
                                     ActionMapping mapping)
        throws IOException, ServletException {

        if (log.isTraceEnabled()) {
            log.trace("Performing standard include handling");
        }
        boolean result = super.processInclude
            (request, response, mapping);
        if (log.isDebugEnabled()) {
            log.debug("Standard include handling returned " + result);
        }
        return (result);

    }


    /**
     * <p>Identify and return the path component (from the request URI for a
     * non-Faces request, or from the form event for a Faces request)
     * that we will use to select an ActionMapping to dispatch with.
     * If no such path can be identified, create an error response and return
     * <code>null</code>.</p>
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     *
     * @exception IOException if an input/output error occurs
     */
    protected String processPath(HttpServletRequest request,
                                 HttpServletResponse response)
        throws IOException {

        // Are we processing a Faces request?
        ActionEvent event = (ActionEvent)
            request.getAttribute(Constants.ACTION_EVENT_KEY);

        // Handle non-Faces requests in the usual way
        if (event == null) {
            if (log.isTraceEnabled()) {
                log.trace("Performing standard processPath() processing");
            }
            return (super.processPath(request, response));
        }

        // Calculate the path from the form name
        UIComponent component = event.getComponent();
        if (log.isTraceEnabled()) {
            log.trace("Locating form parent for command component " +
                      event.getComponent());
        }
        while (!(component instanceof FormComponent)) {
            component = component.getParent();
            if (component == null) {
                log.warn("Command component was not nested in a Struts form!");
                return (null);
            }
        }
        if (log.isTraceEnabled()) {
            log.trace("Returning selected path of " +
                      ((FormComponent) component).getAction());
        }
        return (((FormComponent) component).getAction());

    }


    /**
     * <p>Populate the properties of the specified <code>ActionForm</code>
     * instance from the request parameters included with this request,
     * <strong>IF</strong> this is a non-Faces request.  For a Faces request,
     * this will have already been done by the <em>Update Model Values</em>
     * phase of the request processing lifecycle, so all we have to do is
     * recognize whether the request was cancelled or not.</p>
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param form The ActionForm instance we are populating
     * @param mapping The ActionMapping we are using
     *
     * @exception ServletException if thrown by RequestUtils.populate()
     */
    protected void processPopulate(HttpServletRequest request,
                                   HttpServletResponse response,
                                   ActionForm form,
                                   ActionMapping mapping)
        throws ServletException {

        // Are we processing a Faces request?
        ActionEvent event = (ActionEvent)
            request.getAttribute(Constants.ACTION_EVENT_KEY);

        // Handle non-Faces requests in the usual way
        if (event == null) {
            if (log.isTraceEnabled()) {
                log.trace("Performing standard processPopulate() processing");
            }
            super.processPopulate(request, response, form, mapping);
            return;
        }

        // Faces Requests require no processing for form bean population
        // so we need only check for the cancellation command name
        if (log.isTraceEnabled()) {
            log.trace("Faces request, so no processPopulate() processing");
        }
        UIComponent source = event.getComponent();
        if (source instanceof UICommand) {
            if ("cancel".equals(((UICommand) source).getId())) {
                if (log.isTraceEnabled()) {
                    log.trace("Faces request with cancel button pressed");
                }
                request.setAttribute(Globals.CANCEL_KEY, Boolean.TRUE);
            }
        }

    }


    // Override default processing to provide logging
    protected boolean processValidate(HttpServletRequest request,
                                      HttpServletResponse response,
                                      ActionForm form,
                                      ActionMapping mapping)
        throws IOException, ServletException, InvalidCancelException {

        if (log.isTraceEnabled()) {
            log.trace("Performing standard validation");
        }
        boolean result = super.processValidate
            (request, response, form, mapping);
        if (log.isDebugEnabled()) {
            log.debug("Standard validation processing returned " + result);
        }
        return (result);

    }


    // --------------------------------------------------------- Private Methods


    /**
     * <p>Return the used Lifecycle ID (default or custom).</p>
     */
    private String getLifecycleId()
    {
        String lifecycleId = this.servlet.getServletContext().getInitParameter(LIFECYCLE_ID_ATTR);
        return lifecycleId != null ? lifecycleId : LifecycleFactory.DEFAULT_LIFECYCLE;
    }

    /**
     * <p>Return <code>true</code> if the specified context-relative URI
     * specifies a request to be processed by the Struts controller servlet.</p>
     *
     * @param uri URI to be checked
     */
    private boolean isStrutsRequest(String uri) {

        int question = uri.indexOf("?");
        if (question >= 0) {
            uri = uri.substring(0, question);
        }
        String mapping = (String)
            servlet.getServletContext().getAttribute(Globals.SERVLET_KEY);
        if (mapping == null) {
            return (false);
        } else if (mapping.startsWith("*.")) {
            return (uri.endsWith(mapping.substring(1)));
        } else if (mapping.endsWith("/*")) {
            return (uri.startsWith(mapping.substring(0, mapping.length() - 2)));
        } else {
            return (false);
        }

    }
}