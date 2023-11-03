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
package org.apache.struts.chain.contexts;

import org.apache.commons.chain.web.jakarta.servlet.ServletWebContext;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.chain.Constants;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.util.MessageResources;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Implement ActionContext interface while making Servlet API-specific values
 * available.
 */
public class ServletActionContext extends WebActionContext {

    /**
     * Instantiate this composite by wrapping a ServletWebContext.
     *
     * @param context The ServletWebContext to wrap
     */
    public ServletActionContext(ServletWebContext context) {
        super(context);
    }

    /**
     * Instantiate this Context for a given {@code ServletContext},
     * {@code HttpServletRequest}, and {@code HttpServletResponse}.
     *
     * @param context  The instant ServletContext
     * @param request  The instant HttpServletRequest
     * @param response The instant HttpServletResponse
     */
    public ServletActionContext(ServletContext context,
        HttpServletRequest request, HttpServletResponse response) {
        this(new ServletWebContext(context, request, response));
    }

    /**
     * Provide the {@code ServletWebContext} for this composite.
     *
     * @return Our ServletWebContext
     */
    protected ServletWebContext servletWebContext() {
        return (ServletWebContext) this.getBaseContext();
    }

    public void release() {
        this.servletWebContext().release();
        super.release();
    }

    // -------------------------------
    // Servlet specific properties
    // -------------------------------

    /**
     * Return the {@code ServletContext} for this context.
     *
     * @return Our ServletContext
     */
    public ServletContext getContext() {
        return servletWebContext().getContext();
    }

    /**
     * Return the {@code HttpServletRequest} for this context.
     *
     * @return Our HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        return servletWebContext().getRequest();
    }

    /**
     * Return the {@code HttpServletResponse} for this context.
     *
     * @return Our HttpServletResponse
     */
    public HttpServletResponse getResponse() {
        return servletWebContext().getResponse();
    }

    /**
     * Return the {@code ActionServlet} for this context.
     *
     * @return Our ActionServlet
     */
    public ActionServlet getActionServlet() {
        return (ActionServlet) this.get(Constants.ACTION_SERVLET_KEY);
    }

    /**
     * Set the {@code ActionServlet} instance for this context.
     *
     * @param servlet Our ActionServlet instance
     */
    public void setActionServlet(ActionServlet servlet) {
        this.put(Constants.ACTION_SERVLET_KEY, servlet);
    }

    // -------------------------------
    // Servlet specific modifications to base properties.
    // -------------------------------
    public void setActionConfig(ActionConfig actionConfig) {
        super.setActionConfig(actionConfig);
        this.getRequestScope().put(Globals.MAPPING_KEY, actionConfig);

        // ISSUE: Should we check this call to put?
    }

    public MessageResources getMessageResources() {
        return ((MessageResources) getRequest().getAttribute(Globals.MESSAGES_KEY));
    }

    // ISSUE: This method would probably be better handled by a "Struts"
    // object which encapsulated the servler (Application) scope.
    public MessageResources getMessageResources(String key) {
        // Identify the current module
        ServletContext context = getActionServlet().getServletContext();

        // Return the requested message resources instance
        return (MessageResources) context.getAttribute(key
            + getModuleConfig().getPrefix());
    }

    public void setMessageResources(MessageResources resources) {
        super.setMessageResources(resources);
        this.getRequest().setAttribute(Globals.MESSAGES_KEY, resources);
    }

    /**
     * Store the message resources for the current module under the given
     * request attribute key.
     *
     * @param key       Request attribute key
     * @param resources Message resources to store
     */
    public void setMessageResources(String key, MessageResources resources) {
        this.getRequest().setAttribute(key + getModuleConfig().getPrefix(),
            resources);
    }

    // -------------------------------
    // ActionMessage Processing
    // -------------------------------
    public void saveErrors(ActionMessages errors) {
        // Remove any error messages attribute if none are required
        if ((errors == null) || errors.isEmpty()) {
            getRequest().removeAttribute(Globals.ERROR_KEY);

            return;
        }

        // Save the error messages we need
        getRequest().setAttribute(Globals.ERROR_KEY, errors);
    }

    public void saveMessages(ActionMessages messages) {
        if ((messages == null) || messages.isEmpty()) {
            getRequest().removeAttribute(Globals.MESSAGE_KEY);

            return;
        }

        getRequest().setAttribute(Globals.MESSAGE_KEY, messages);
    }

    public void addMessages(ActionMessages messages) {
        if (messages == null) {
            return;
        }

        ActionMessages requestMessages = getMessages();

        if (requestMessages == null) {
            requestMessages = new ActionMessages();
        }

        requestMessages.add(messages);
        saveMessages(requestMessages);
    }

    public void addErrors(ActionMessages errors) {
        if (errors == null) {
            return;
        }

        ActionMessages requestErrors = getErrors();

        if (requestErrors == null) {
            requestErrors = new ActionMessages();
        }

        requestErrors.add(errors);
        saveErrors(requestErrors);
    }

    public ActionMessages getErrors() {
        return (ActionMessages) this.getRequest().getAttribute(Globals.ERROR_KEY);
    }

    public ActionMessages getMessages() {
        return (ActionMessages) this.getRequest().getAttribute(Globals.MESSAGE_KEY);
    }

    // -------------------------------
    // Token Processing
    // Implementing the servlet-aware versions by using the
    // TokenProcessor class
    // directly should ensure greater compatibility.
    // -------------------------------
    public void saveToken() {
        token.saveToken(getRequest());
    }

    public String generateToken() {
        return token.generateToken(getRequest());
    }

    public boolean isTokenValid(boolean reset) {
        return token.isTokenValid(getRequest(), reset);
    }

    public void resetToken() {
        token.resetToken(getRequest());
    }
}