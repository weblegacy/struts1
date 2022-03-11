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


import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.faces.Constants;
import org.apache.struts.faces.component.FormComponent;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ModuleUtils;


/**
 * <p>Concrete implementation of <code>ActionListener</code> that replaces
 * the default provided implementation.  It converts application-level events
 * into execution of the corresponding Struts request processing lifecycle.
 * </p>
 *
 * @version $Rev$ $Date$
 */

public final class ActionListenerImpl implements ActionListener {


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Construct a new default <code>ActionListener</code> instance,
     * passing it the previously configured one.</p>
     *
     * @param original Original default <code>ActionListener</code>
     *
     * @exception NullPointerException if <code>original</code>
     *  is <code>null</code>
     */
    public ActionListenerImpl(ActionListener original) {

        if (original == null) {
            throw new NullPointerException();
        }
        this.original = original;
        if (log.isInfoEnabled()) {
            log.info("Create ActionListener wrapping instance of type '" +
                     original.getClass().getName() + "'");
        }

    }



    // ------------------------------------------------------ Instance Variables


    /**
     * <p>The logger for this instance.</p>
     */
    private static final Log log = LogFactory.getLog(ActionListenerImpl.class);


    /**
     * <p>The previously configured <code>ActionListener</code> instance.</p>
     */
    private ActionListener original;


    // ---------------------------------------------------------- Public Methods


    /**
     * <p>Process the specified <code>ActionEvent</code>.</p>
     *
     * @param event The <code>ActionEvent</code> to be processed
     *
     * @exception AbortProcessingException to signal that no further
     *  event processing should be performed
     */
    public void processAction(ActionEvent event)
        throws AbortProcessingException {

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
            if (log.isDebugEnabled()) {
                log.debug("Performing standard handling for event " +
                          "from source component '" + component.getId() + "'");
            }
            original.processAction(event);
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
        if (log.isDebugEnabled()) {
            log.debug("Performing Struts form submit for event " +
                      " from source component '" +
                      component.getId() + "'");
        }

        // Invoke the appropriate request processor for this request
        try {
            request.setAttribute(Constants.ACTION_EVENT_KEY, event);
            ModuleUtils.getInstance().selectModule(request, servletContext);
            ModuleConfig moduleConfig = (ModuleConfig)
                request.getAttribute(Globals.MODULE_KEY);
            if (log.isTraceEnabled()) {
                log.trace("Assigned to module with prefix '" +
                          moduleConfig.getPrefix() + "'");
            }
            RequestProcessor processor =
                getRequestProcessor(moduleConfig, servletContext);
            if (log.isTraceEnabled()) {
                log.trace("Invoking request processor instance " + processor);
            }
            processor.process(request, response);
            context.responseComplete();
        } catch (Exception e) {
            log.error("Exception processing action event " + event, e);
        } finally {
            request.removeAttribute(Constants.ACTION_EVENT_KEY);
        }

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * <p>Look up and return the <code>RequestProcessor</code> responsible for
     * the specified module, creating a new one if necessary.  This method is
     * based on the corresponding code in <code>ActionServlet</code>, which
     * cannot be used directly because it is a protected method.</p>
     *
     * @param config The module configuration for which to
     *  acquire and return a RequestProcessor
     * @param context The <code>ServletContext</code> instance
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
                if (log.isDebugEnabled()) {
                    log.debug("Instantiating RequestProcessor of class " +
                              config.getControllerConfig().getProcessorClass());
                }
                ActionServlet servlet = (ActionServlet)
                context.getAttribute(Globals.ACTION_SERVLET_KEY);
                processor =
                    (RequestProcessor) RequestUtils.applicationInstance(
                        config.getControllerConfig().getProcessorClass());
                processor.init(servlet, config);
                context.setAttribute(key, processor);
            } catch (Exception e) {
                log.error("Cannot instantiate RequestProcessor of class "
                          + config.getControllerConfig().getProcessorClass(),
                          e);
                throw new IllegalStateException(
                    "Cannot initialize RequestProcessor of class "
                        + config.getControllerConfig().getProcessorClass()
                        + ": "
                        + e);
            }

        }
        return (processor);

    }


}
