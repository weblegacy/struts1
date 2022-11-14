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
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.servlet.ServletException;

import org.apache.commons.chain.web.servlet.ServletWebContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.mock.MockActionServlet;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.apache.struts.mock.MockPrincipal;
import org.apache.struts.mock.MockServletConfig;
import org.apache.struts.mock.MockServletContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnitTest case for class: {@link PerformForward}
 */
public class TestPerformForward {
    MockHttpServletRequest request = null;
    MockPrincipal principal = null;
    ServletWebContext swContext = null;
    ServletActionContext saContext = null;
    PerformForward command = null;

    /* setUp method for test case */
    @BeforeEach
    protected void setUp() throws ServletException {
        this.request = new MockHttpServletRequest();
        this.principal =
            new MockPrincipal("Mr. Macri", new String[] { "administrator" });
        this.request.setUserPrincipal(principal);

        MockServletConfig servletConfig = new MockServletConfig();
        MockServletContext servletContext = new MockServletContext();
        MockActionServlet servlet =
            new MockActionServlet(servletContext, servletConfig);

        servlet.initInternal();

        this.saContext =
            new ServletActionContext(servletContext, request,
                new MockHttpServletResponse());

        this.saContext.setActionServlet(servlet);
        this.command = new PerformForward();
    }

    /* tearDown method for test case */
    @AfterEach
    protected void tearDown() {
    }

    @Test
    public void testNullForwardPath()
        throws Exception {
        ForwardConfig config = new ForwardConfig();

        config.setPath(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> command.perform(saContext, config),
            "Didn't throw an illegal argument exception on null forward path");

        System.out.println("exception: " + ex.getMessage());

        assertEquals("The path of an ForwardConfig cannot be null", ex.getMessage());
    }
}
