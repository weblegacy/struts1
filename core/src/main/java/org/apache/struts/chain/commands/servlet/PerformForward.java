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
package org.apache.struts.chain.commands.servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.chain.commands.AbstractPerformForward;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ModuleUtils;
import org.apache.struts.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Perform forwarding or redirection based on the specified
 * <code>ForwardConfig</code> (if any).</p>
 *
 * @version $Rev$ $Date$
 */
public class PerformForward extends AbstractPerformForward {

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(PerformForward.class);

    // ------------------------------------------------------- Protected Methods

    /**
     * <p>Perform the appropriate processing on the specified
     * <code>ForwardConfig</code>.</p>
     *
     * @param context       The context for this request
     * @param forwardConfig The forward to be performed
     */
    protected void perform(ActionContext context, ForwardConfig forwardConfig)
        throws Exception {
        ServletActionContext sacontext = (ServletActionContext) context;
        String uri = forwardConfig.getPath();

        if (uri == null) {
            ActionServlet servlet = sacontext.getActionServlet();
            MessageResources resources = servlet.getInternal();

            throw new IllegalArgumentException(resources.getMessage("forwardPathNull"));
        }

        HttpServletRequest request = sacontext.getRequest();
        ServletContext servletContext = sacontext.getContext();
        HttpServletResponse response = sacontext.getResponse();

        // If the forward can be unaliased into an action, then use the path of the action
        String actionIdPath = RequestUtils.actionIdURL(forwardConfig, sacontext.getRequest(), sacontext.getActionServlet());
        if (actionIdPath != null) {
            uri = actionIdPath;
            ForwardConfig actionIdForwardConfig = new ForwardConfig(forwardConfig);
            actionIdForwardConfig.setPath(actionIdPath);
            forwardConfig = actionIdForwardConfig;
        }

        if (uri.startsWith("/")) {
            uri = resolveModuleRelativePath(forwardConfig, servletContext, request);
        }


        if (response.isCommitted() && !forwardConfig.getRedirect()) {
            handleAsInclude(uri, servletContext, request, response);
        } else if (forwardConfig.getRedirect()) {
            handleAsRedirect(uri, request, response);
        } else {
            handleAsForward(uri, servletContext, request, response);
        }
    }

    private String resolveModuleRelativePath(ForwardConfig forwardConfig, ServletContext servletContext, HttpServletRequest request) {
        String prefix = forwardConfig.getModule();
        ModuleConfig moduleConfig = ModuleUtils.getInstance().getModuleConfig(prefix, request, servletContext);
        return RequestUtils.forwardURL(request,forwardConfig, moduleConfig);
    }

    private void handleAsForward(String uri, ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = servletContext.getRequestDispatcher(uri);

        log.debug("Forwarding to {}", uri);

        rd.forward(request, response);
    }

    private void handleAsRedirect(String uri, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (uri.startsWith("/")) {
            uri = request.getContextPath() + uri;
        }

        log.debug("Redirecting to {}", uri);

        response.sendRedirect(response.encodeRedirectURL(uri));
    }

    private void handleAsInclude(String uri, ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher rd = servletContext.getRequestDispatcher(uri);

        if (rd == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Error getting RequestDispatcher for " + uri);
            return;
        }

        log.debug("Including {}", uri);

        rd.include(request, response);
    }
}