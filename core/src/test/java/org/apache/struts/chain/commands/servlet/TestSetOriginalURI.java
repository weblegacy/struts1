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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.struts.Globals;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.mock.MockActionServlet;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.apache.struts.mock.MockServletConfig;
import org.apache.struts.mock.MockServletContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnitTest case for class: {@link SetOriginalURI}
 */
public class TestSetOriginalURI {
    SetOriginalURI command = null;

    /* setUp method for test case */
    @BeforeEach
    protected void setUp() {
        this.command = new SetOriginalURI();
    }

    /* tearDown method for test case */
    @AfterEach
    protected void tearDown() {
    }

    @Test
    public void testSetOriginalURI()
        throws Exception {
        MockHttpServletRequest request =
            new MockHttpServletRequest("foo/", "bar.do", null, null);
        MockServletConfig servletConfig = new MockServletConfig();
        MockServletContext servletContext = new MockServletContext();
        MockActionServlet servlet =
            new MockActionServlet(servletContext, servletConfig);

        servlet.initInternal();

        ServletActionContext saContext =
            new ServletActionContext(servletContext, request,
                new MockHttpServletResponse());

        saContext.setActionServlet(servlet);

        boolean result = command.execute(saContext);

        assertFalse(result);

        String uri = (String) request.getAttribute(Globals.ORIGINAL_URI_KEY);

        assertEquals("bar.do", uri, "Original uri not correct: " + uri);

        request.setPathElements("foo/", "bar2.do", null, null);
        uri = (String) request.getAttribute(Globals.ORIGINAL_URI_KEY);
        assertEquals("bar.do", uri, "Original uri not correct: " + uri);
    }
}
