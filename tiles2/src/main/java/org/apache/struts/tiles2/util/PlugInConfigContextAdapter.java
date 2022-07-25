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

package org.apache.struts.tiles2.util;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.iterators.IteratorEnumeration;
import org.apache.struts.config.PlugInConfig;

/**
 * Adapts a {@link PlugInConfig} object to become a ServletContext object,
 * exposing init parameters methods.
 */
public class PlugInConfigContextAdapter implements ServletContext {

    /**
     * The internal plugin config object.
     */
    private PlugInConfig plugInConfigObject;

    /**
     * The servlet context.
     */
    private ServletContext rootContext;

    /**
     * The set of all parameter names.
     */
    private Set parameterNames;

    /**
     * Constructor.
     *
     * @param plugInConfigObject The plugin config object to use.
     * @param servletContext The servlet context to use.
     */
    public PlugInConfigContextAdapter(PlugInConfig plugInConfigObject,
            ServletContext servletContext) {
        this.plugInConfigObject = plugInConfigObject;
        this.rootContext = servletContext;
        parameterNames = new LinkedHashSet();
        parameterNames.addAll(this.plugInConfigObject.getProperties().keySet());
        CollectionUtils.addAll(parameterNames, this.rootContext
                .getInitParameterNames());
    }

    /**
     * Returns an initialization parameter.
     *
     * @param parameterName The name of the parameter.
     * @return The value of the parameter.
     */
    public String getInitParameter(String parameterName) {
        String retValue;

        retValue = (String) plugInConfigObject.getProperties()
                .get(parameterName);
        if (retValue == null) {
            retValue = rootContext.getInitParameter(parameterName);
        }

        return retValue;
    }

    /**
     * Returns the names of all initialization parameters.
     *
     * @return The names of all initialization parameters.
     */
    public Enumeration getInitParameterNames() {
        return new IteratorEnumeration(parameterNames.iterator());
    }

    // The rest of the methods are wrapping implementations of the interface.

    /** {@inheritDoc} */
    public ServletContext getContext(String string) {
        return rootContext.getContext(string);
    }

    /** {@inheritDoc} */
    public int getMajorVersion() {
        return rootContext.getMajorVersion();
    }

    /** {@inheritDoc} */
    public int getMinorVersion() {
        return rootContext.getMinorVersion();
    }

    /** {@inheritDoc} */
    public String getMimeType(String string) {
        return rootContext.getMimeType(string);
    }

    /** {@inheritDoc} */
    public Set getResourcePaths(String string) {
        return rootContext.getResourcePaths(string);
    }

    /** {@inheritDoc} */
    public URL getResource(String string) throws MalformedURLException {
        return rootContext.getResource(string);
    }

    /** {@inheritDoc} */
    public InputStream getResourceAsStream(String string) {
        return rootContext.getResourceAsStream(string);
    }

    /** {@inheritDoc} */
    public RequestDispatcher getRequestDispatcher(String string) {
        return rootContext.getRequestDispatcher(string);
    }

    /** {@inheritDoc} */
    public RequestDispatcher getNamedDispatcher(String string) {
        return rootContext.getNamedDispatcher(string);
    }

    /** {@inheritDoc} */
    public Servlet getServlet(String string) throws ServletException {
        return rootContext.getServlet(string);
    }

    /** {@inheritDoc} */
    public Enumeration getServlets() {
        return rootContext.getServlets();
    }

    /** {@inheritDoc} */
    public Enumeration getServletNames() {
        return rootContext.getServletNames();
    }

    /** {@inheritDoc} */
    public void log(String string) {
        rootContext.log(string);
    }

    /** {@inheritDoc} */
    public void log(Exception exception, String string) {
        rootContext.log(exception, string);
    }

    /** {@inheritDoc} */
    public void log(String string, Throwable throwable) {
        rootContext.log(string, throwable);
    }

    /** {@inheritDoc} */
    public String getRealPath(String string) {
        return rootContext.getRealPath(string);
    }

    /** {@inheritDoc} */
    public String getServerInfo() {
        return rootContext.getServerInfo();
    }

    /** {@inheritDoc} */
    public Object getAttribute(String string) {
        return rootContext.getAttribute(string);
    }

    /** {@inheritDoc} */
    public Enumeration getAttributeNames() {
        return rootContext.getAttributeNames();
    }

    /** {@inheritDoc} */
    public void setAttribute(String string, Object object) {
        rootContext.setAttribute(string, object);
    }

    /** {@inheritDoc} */
    public void removeAttribute(String string) {
        rootContext.removeAttribute(string);
    }

    /** {@inheritDoc} */
    public String getServletContextName() {
        return rootContext.getServletContextName();
    }
}