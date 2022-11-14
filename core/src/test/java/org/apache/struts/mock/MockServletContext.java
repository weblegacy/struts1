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
package org.apache.struts.mock;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.SessionTrackingMode;
import jakarta.servlet.descriptor.JspConfigDescriptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Mock <strong>ServletContext</strong> object for low-level unit tests of
 * Struts controller components.  Coarser grained tests should be implemented
 * in terms of the Cactus framework, instead of the mock object classes.</p>
 *
 * <p><strong>WARNING</strong> - Only the minimal set of methods needed to
 * create unit tests is provided, plus additional methods to configure this
 * object as necessary.  Methods for unsupported operations will throw
 * <code>UnsupportedOperationException</code>.</p>
 *
 * <p><strong>WARNING</strong> - Because unit tests operate in a single
 * threaded environment, no synchronization is performed.</p>
 *
 * @version $Rev$ $Date: 2005-05-07 12:11:38 -0400 (Sat, 07 May 2005)
 *          $
 */
public class MockServletContext implements ServletContext {
    // ----------------------------------------------------- Instance Variables

    /**
     * The set of servlet context attributes.
     */
    protected HashMap<String, Object> attributes = new HashMap<>();

    /**
     * Store the Logger instance for this Context.
     */
    private Logger logger =
        LoggerFactory.getLogger(MockServletContext.class);

    /**
     * The set of context initialization parameters.
     */
    protected HashMap<String, String> parameters = new HashMap<>();

    // --------------------------------------------------------- Public Methods
    public void addInitParameter(String name, String value) {
        parameters.put(name, value);
    }

    public void setLog(Logger logger) {
        this.logger = logger;
    }

    // ------------------------------------------------- ServletContext Methods
    @Override
    public String getContextPath() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServletContext getContext(String uripath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMajorVersion() {
        return 3;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public int getEffectiveMajorVersion() {
        return getMajorVersion();
    }

    @Override
    public int getEffectiveMinorVersion() {
        return getMinorVersion();
    }

    @Override
    public String getMimeType(String file) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getResourcePaths(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public URL getResource(String path) throws MalformedURLException {
        return this.getClass().getResource(path);
    }

    @Override
    public InputStream getResourceAsStream(String path) {
        return this.getClass().getResourceAsStream(path);
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public Servlet getServlet(String name) throws ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public Enumeration<Servlet> getServlets() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public Enumeration<String> getServletNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void log(String msg) {
        logger.info(msg);
    }

    @Override
    @Deprecated
    public void log(Exception exception, String msg) {
       log(msg, exception);
    }

    @Override
    public void log(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    @Override
    public String getRealPath(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getServerInfo() {
        return "MockServletContext/$Version$";
    }

    @Override
    public String getInitParameter(String name) {
        return parameters.get(name);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return new MockEnumeration<>(parameters.keySet().iterator());
    }

    @Override
    public boolean setInitParameter(String name, String value) {
        return parameters.putIfAbsent(name, value) == null;
    }

    @Override
    public Object getAttribute(String name) {
        return (attributes.get(name));
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return new MockEnumeration<>(attributes.keySet().iterator());
    }

    @Override
    public void setAttribute(String name, Object value) {
        if (value == null) {
            attributes.remove(name);
        } else {
            attributes.put(name, value);
        }
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public String getServletContextName() {
        return getServerInfo();
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String servletName, String className) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServletRegistration.Dynamic addJspFile(String servletName, String jspFile) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServletRegistration getServletRegistration(String servletName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        throw new UnsupportedOperationException();
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, String className) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    public FilterRegistration getFilterRegistration(String filterName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SessionCookieConfig getSessionCookieConfig() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addListener(String className) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends EventListener> void addListener(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addListener(Class<? extends EventListener> listenerClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    public JspConfigDescriptor getJspConfigDescriptor() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClassLoader getClassLoader() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void declareRoles(String... roleNames) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getVirtualServerName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getSessionTimeout() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSessionTimeout(int sessionTimeout) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getRequestCharacterEncoding() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRequestCharacterEncoding(String encoding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getResponseCharacterEncoding() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setResponseCharacterEncoding(String encoding) {
        throw new UnsupportedOperationException();
    }
}