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


import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.faces.Constants;
import org.apache.struts.faces.component.FormComponent;
import org.apache.struts.util.ModuleUtils;
import org.apache.struts.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.faces.component.ActionSource;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIForm;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.ActionListener;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * <p>Concrete implementation of {@code ActionListener} that replaces
 * the default provided implementation.  It converts application-level events
 * into execution of the corresponding Struts request processing lifecycle.
 * </p>
 *
 * @version $Rev$ $Date$
 */

public final class ActionListenerImpl implements ActionListener {


    // -------------------------------------------------------- Static Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(ActionListenerImpl.class);


    // ------------------------------------------------------ Instance Variables


    /**
     * The previously configured {@ode ActionListener} instance.
     */
    private final ActionListener oldActionListener;


    // ------------------------------------------------------------ Constructors


    /**
     * Construct a new default {@code ActionListener} instance, passing
     * it the previously configured one.
     *
     * @param oldActionListener Original default {@code ActionListener}
     *
     * @exception NullPointerException if {@code original} is {@code null}
     */
    public ActionListenerImpl(ActionListener oldActionListener) {

        if (oldActionListener == null) {
            throw new NullPointerException();
        }

        this.oldActionListener = oldActionListener;

        log.info("Create ActionListener wrapping instance of type '{}'",
            oldActionListener.getClass().getName());

    }


    // ---------------------------------------------------------- Public Methods


    /**
     * Process the specified {@code ActionEvent}.
     *
     * @param event The {@code ActionEvent} to be processed
     *
     * @exception AbortProcessingException to signal that no further
     *     event processing should be performed
     */
    public void processAction(ActionEvent event) throws AbortProcessingException {

        // If this is an immediate action, or we are NOT nested in a
        // Struts form, perform the standard processing
        UIComponent component = event.getComponent();
        ActionSource source = (ActionSource) component;
        boolean standard = source.isImmediate();
        if (!standard) {
            UIComponent parent = component.getParent();
            while (parent != null) {
                if (parent instanceof UIForm) {
                    if (!(parent instanceof FormComponent)) {
                        standard = true;
                    }
                    break;
                }
                parent = parent.getParent();
            }
        }
        if (standard) {
            log.debug("Performing standard handling for event from source component '{}'",
                component.getId());
            oldActionListener.processAction(event);
            return;
        }

        // Acquire Servlet API Object References
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext)
            context.getExternalContext().getContext();
        HttpServletRequest request = (HttpServletRequest)
            context.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse)
            context.getExternalContext().getResponse();

        // Log this event if requested
        log.debug("Performing Struts form submit for event from source component '{}'",
            component.getId());

        // Invoke the appropriate request processor for this request
        try {
            request.setAttribute(Constants.ACTION_EVENT_KEY, event);
            ModuleUtils.getInstance().selectModule(request, servletContext);
            ModuleConfig moduleConfig = (ModuleConfig)
                request.getAttribute(Globals.MODULE_KEY);
            log.trace("Assigned to module with prefix '{}'",
                moduleConfig.getPrefix());
            RequestProcessor processor =
                getRequestProcessor(moduleConfig, servletContext);
            log.trace("Invoking request processor instance {}", processor);
            processor.process(request, response);
            context.responseComplete();
        } catch (Exception e) {
            log.error("Exception processing action event {}", event, e);
        } finally {
            request.removeAttribute(Constants.ACTION_EVENT_KEY);
        }

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * <p>Look up and return the {@code RequestProcessor} responsible for
     * the specified module, creating a new one if necessary.  This method is
     * based on the corresponding code in {@code ActionServlet}, which
     * cannot be used directly because it is a protected method.</p>
     *
     * @param config The module configuration for which to
     *  acquire and return a RequestProcessor
     * @param context The {@code ServletContext} instance
     *  for this web application
     *
     * @exception IllegalStateException if we cannot instantiate a
     *  RequestProcessor instance
     */
    protected RequestProcessor getRequestProcessor(ModuleConfig config,
                                                   ServletContext context) {

        String key = Globals.REQUEST_PROCESSOR_KEY + config.getPrefix();
        RequestProcessor processor =
            (RequestProcessor) context.getAttribute(key);

        if (processor == null) {
            try {
                log.debug("Instantiating RequestProcessor of class {}",
                    config.getControllerConfig().getProcessorClass());
                ActionServlet servlet = (ActionServlet)
                context.getAttribute(Globals.ACTION_SERVLET_KEY);
                processor =
                    (RequestProcessor) RequestUtils.applicationInstance(
                        config.getControllerConfig().getProcessorClass());
                processor.init(servlet, config);
                context.setAttribute(key, processor);
            } catch (Exception e) {
                log.error("Cannot instantiate RequestProcessor of class {}",
                    config.getControllerConfig().getProcessorClass(), e);
                IllegalStateException e2 = new IllegalStateException(
                    "Cannot initialize RequestProcessor of class "
                        + config.getControllerConfig().getProcessorClass());
                e2.initCause(e);
                throw e2;
            }

        }
        return (processor);

    }
}