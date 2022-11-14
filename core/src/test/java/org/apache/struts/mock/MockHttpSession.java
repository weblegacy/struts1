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

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.HashMap;

/**
 * <p>Mock <strong>HttpSession</strong> object for low-level unit tests of
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
public class MockHttpSession implements HttpSession {
    // ----------------------------------------------------- Instance Variables

    /**
     * <p> The set of session attributes. </p>
     */
    protected HashMap<String, Object> attributes = new HashMap<>();

    /**
     * <p> The ServletContext with which we are associated. </p>
     */
    protected ServletContext servletContext = null;

    // ----------------------------------------------------------- Constructors
    public MockHttpSession() {
        super();
    }

    public MockHttpSession(ServletContext servletContext) {
        super();
        setServletContext(servletContext);
    }

    // --------------------------------------------------------- Public Methods
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    // ---------------------------------------------------- HttpSession Methods
    @Override
    public Object getAttribute(String name) {
        return (attributes.get(name));
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return (new MockEnumeration<>(attributes.keySet().iterator()));
    }

    @Override
    public long getCreationTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getLastAccessedTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxInactiveInterval() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServletContext getServletContext() {
        return (this.servletContext);
    }

    @Deprecated
    @Override
    public jakarta.servlet.http.HttpSessionContext getSessionContext() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public Object getValue(String name) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public String[] getValueNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void invalidate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isNew() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void putValue(String name, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Deprecated
    @Override
    public void removeValue(String name) {
        throw new UnsupportedOperationException();
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
    public void setMaxInactiveInterval(int interval) {
        throw new UnsupportedOperationException();
    }
}
