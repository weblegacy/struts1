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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpUpgradeHandler;
import jakarta.servlet.http.Part;

/**
 * <p>Mock <strong>HttpServletRequest</strong> object for low-level unit tests
 * of Struts controller components.  Coarser grained tests should be
 * implemented in terms of the Cactus framework, instead of the mock object
 * classes.</p>
 *
 * <p><strong>WARNING</strong> - Only the minimal set of methods needed to
 * create unit tests is provided, plus additional methods to configure this
 * object as necessary.  Methods for unsupported operations will throw
 * <code>UnsupportedOperationException</code>.</p>
 *
 * <p><strong>WARNING</strong> - Because unit tests operate in a single
 * threaded environment, no synchronization is performed.</p>
 *
 * @version $Rev$ $Date$
 */
public class MockHttpServletRequest implements HttpServletRequest {
    // ----------------------------------------------------- Instance Variables

    /**
     * <p> The set of request attributes. </p>
     */
    protected HashMap<String, Object> attributes = new HashMap<>();

    /**
     * <p> The context path for this request. </p>
     */
    protected String contextPath = null;

    /**
     * <p> The preferred locale for this request. </p>
     */
    protected Locale locale = null;

    /**
     * <p> The set of arrays of parameter values, keyed by parameter name.
     * </p>
     */
    protected HashMap<String, String[]> parameters = new HashMap<>();

    /**
     * <p> The extra path information for this request. v     * </p>
     */
    protected String pathInfo = null;

    /**
     * <p> The authenticated user for this request. </p>
     */
    protected Principal principal = null;

    /**
     * <p> The query string for this request. </p>
     */
    protected String queryString = null;

    /**
     * <p> The servlet path for this request. </p>
     */
    protected String servletPath = null;

    /**
     * <p> The HttpSession with which we are associated. </p>
     */
    protected HttpSession session = null;

    /**
     * <p> The HTTP request method. </p>
     */
    protected String method = null;

    /**
     * <p> The Content Type for this request. </p>
     */
    protected String contentType = null;

    // ----------------------------------------------------------- Constructors
    public MockHttpServletRequest() {
        super();
    }

    public MockHttpServletRequest(HttpSession session) {
        super();
        setHttpSession(session);
    }

    public MockHttpServletRequest(String contextPath, String servletPath,
        String pathInfo, String queryString) {
        super();
        setPathElements(contextPath, servletPath, pathInfo, queryString);
    }

    public MockHttpServletRequest(String contextPath, String servletPath,
        String pathInfo, String queryString, HttpSession session) {
        super();
        setPathElements(contextPath, servletPath, pathInfo, queryString);
        setHttpSession(session);
    }

    // --------------------------------------------------------- Public Methods
    public void addParameter(String name, String value) {
        String[] values = parameters.get(name);

        if (values == null) {
            String[] results = new String[] { value };

            parameters.put(name, results);

            return;
        }

        String[] results = new String[values.length + 1];

        System.arraycopy(values, 0, results, 0, values.length);
        results[values.length] = value;
        parameters.put(name, results);
    }

    public void setHttpSession(HttpSession session) {
        this.session = session;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setPathElements(String contextPath, String servletPath,
        String pathInfo, String queryString) {
        this.contextPath = contextPath;
        this.servletPath = servletPath;
        this.pathInfo = pathInfo;
        this.queryString = queryString;
    }

    public void setUserPrincipal(Principal principal) {
        this.principal = principal;
    }

    // --------------------------------------------- HttpServletRequest Methods
    @Override
    public String getAuthType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cookie[] getCookies() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getDateHeader(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getHeader(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getIntHeader(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getPathInfo() {
        return pathInfo;
    }

    @Override
    public String getPathTranslated() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getContextPath() {
        return contextPath;
    }

    @Override
    public String getQueryString() {
        return queryString;
    }

    @Override
    public String getRemoteUser() {
        if (principal != null) {
            return principal.getName();
        } else {
            return null;
        }
    }

    @Override
    public boolean isUserInRole(String role) {
        if (principal != null && principal instanceof MockPrincipal) {
            return ((MockPrincipal) principal).isUserInRole(role);
        } else {
            return false;
        }
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public String getRequestedSessionId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getRequestURI() {
        StringBuilder sb = new StringBuilder();

        if (contextPath != null) {
            sb.append(contextPath);
        }

        if (servletPath != null) {
            sb.append(servletPath);
        }

        if (pathInfo != null) {
            sb.append(pathInfo);
        }

        if (sb.length() > 0) {
            return sb.toString();
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public StringBuffer getRequestURL() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getServletPath() {
        return servletPath;
    }

    @Override
    public HttpSession getSession(boolean create) {
        if (create && session == null) {
            session = new MockHttpSession();

            // modified to act like the real deal,
            // call with (false) if you want null
            // throw new UnsupportedOperationException();
        }

        return session;
    }

    @Override
    public HttpSession getSession() {
        return getSession(true);
    }

    @Override
    public String changeSessionId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return isRequestedSessionIdFromURL();
    }

    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void login(String username, String password) throws ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void logout() throws ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        throw new UnsupportedOperationException();
    }

    // ------------------------------------------------- ServletRequest Methods
    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return new MockEnumeration<>(attributes.keySet().iterator());
    }

    @Override
    public String getCharacterEncoding() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getContentLength() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getContentLengthLong() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getParameter(String name) {
        String[] values = parameters.get(name);

        if (values != null) {
            return values[0];
        } else {
            return null;
        }
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return new MockEnumeration<>(parameters.keySet().iterator());
    }

    @Override
    public String[] getParameterValues(String name) {
        return parameters.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameters;
    }

    @Override
    public String getProtocol() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getScheme() {
        return "http";
    }

    @Override
    public String getServerName() {
        return "localhost";
    }

    @Override
    public int getServerPort() {
        return 8080;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getRemoteAddr() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getRemoteHost() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAttribute(String name, Object o) {
        if (o == null) {
            attributes.remove(name);
        } else {
            attributes.put(name, o);
        }
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSecure() {
        throw new UnsupportedOperationException();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public String getRealPath(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getRemotePort() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getLocalName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getLocalAddr() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLocalPort() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServletContext getServletContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAsyncStarted() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAsyncSupported() {
        throw new UnsupportedOperationException();
    }

    @Override
    public AsyncContext getAsyncContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DispatcherType getDispatcherType() {
        throw new UnsupportedOperationException();
    }
}