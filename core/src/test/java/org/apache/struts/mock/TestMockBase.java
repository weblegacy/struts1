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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionFormBean;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ControllerConfig;
import org.apache.struts.config.FormPropertyConfig;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.ModuleConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Convenience base class for unit tests of the {@code org.apache.struts.mock}
 * package, and others that require a runtime environment similar to what the
 * Struts controller servlet sets up.  The {@link #setUp()} method
 * establishes a consistent basic environment for the various tests.  The only
 * tests included in this class are simple validations that the basic
 * environment was set up correctly.
 *
 * @version $Rev$ $Date$
 */
public class TestMockBase {
    // ----------------------------------------------------- Instance Variables
    protected ModuleConfig moduleConfig = null;
    protected ModuleConfig moduleConfig2 = null;
    protected ModuleConfig moduleConfig3 = null;
    protected MockServletConfig config = null;
    protected MockServletContext context = null;
    protected MockPageContext page = null;
    protected MockPrincipal principal = null;
    protected MockHttpServletRequest request = null;
    protected MockHttpServletResponse response = null;
    protected MockHttpSession session = null;

    // ----------------------------------------------------- Setup and Teardown
    @BeforeEach
    public void setUp() {
        // Set up the servlet API objects for a test scenario
        context = new MockServletContext();
        config = new MockServletConfig(context);
        session = new MockHttpSession(context);
        request = new MockHttpServletRequest(session);
        principal =
            new MockPrincipal("username", new String[] { "admin", "manager" });
        request.setUserPrincipal(principal);
        response = new MockHttpServletResponse();
        page = new MockPageContext(config, request, response);

        // Set up application configurations for our supported modules
        setUpDefaultApp();
        setUpSecondApp();
        setUpThirdApp();

        // NOTE - we do not initialize the request attribute
        // for the selected module so that fallbacks to the
        // default module can be tested.  To select a module,
        // tests should set the request attribute Globals.MODULE_KEY
        // to the ModuleConfig instance for the selected module
    }

    protected void setUpDefaultApp() {
        ActionFormBean formBean = null;
        ActionMapping mapping = null;

        ModuleConfigFactory factoryObject = ModuleConfigFactory.createFactory();

        moduleConfig = factoryObject.createModuleConfig("");

        context.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // Forward "external" to "http://jakarta.apache.org/"
        moduleConfig.addForwardConfig(new ActionForward("external",
                "http://jakarta.apache.org/", false));

        // Forward "foo" to "/bar.jsp"
        moduleConfig.addForwardConfig(new ActionForward("foo", "/bar.jsp", false));

        // Forward "relative1" to "relative.jsp" non-context-relative
        moduleConfig.addForwardConfig(new ActionForward("relative1",
                "relative.jsp", false));

        // Forward "relative2" to "relative.jsp" context-relative
        moduleConfig.addForwardConfig(new ActionForward("relative2",
                "relative.jsp", false));

        // Form Bean "static" is a standard ActionForm subclass
        formBean =
            new ActionFormBean("static", "org.apache.struts.mock.MockFormBean");
        moduleConfig.addFormBeanConfig(formBean);

        // Action "/static" uses the "static" form bean in request scope
        mapping = new ActionMapping();
        mapping.setInput("/static.jsp");
        mapping.setName("static");
        mapping.setPath("/static");
        mapping.setScope("request");
        mapping.setType("org.apache.struts.mock.MockAction");
        moduleConfig.addActionConfig(mapping);

        // Form Bean "dynamic" is a DynaActionForm with the same properties
        formBean =
            new ActionFormBean("dynamic",
                "org.apache.struts.action.DynaActionForm");
        formBean.addFormPropertyConfig(new FormPropertyConfig(
                "booleanProperty", "boolean", "false"));
        formBean.addFormPropertyConfig(new FormPropertyConfig(
                "stringProperty", "java.lang.String", null));
        moduleConfig.addFormBeanConfig(formBean);

        // Action "/dynamic" uses the "dynamic" form bean in session scope
        mapping = new ActionMapping();
        mapping.setInput("/dynamic.jsp");
        mapping.setName("dynamic");
        mapping.setPath("/dynamic");
        mapping.setScope("session");
        mapping.setType("org.apache.struts.mock.MockAction");
        moduleConfig.addActionConfig(mapping);

        // Form Bean "/dynamic0" is a DynaActionForm with initializers
        formBean =
            new ActionFormBean("dynamic0",
                "org.apache.struts.action.DynaActionForm");
        formBean.addFormPropertyConfig(new FormPropertyConfig(
                "booleanProperty", "boolean", "true"));
        formBean.addFormPropertyConfig(new FormPropertyConfig(
                "stringProperty", "java.lang.String", "String Property"));
        formBean.addFormPropertyConfig(new FormPropertyConfig("intArray1",
                "int[]", "{1,2,3}", 4)); // 4 should be ignored
        formBean.addFormPropertyConfig(new FormPropertyConfig("intArray2",
                "int[]", null, 5)); // 5 should be respected
        formBean.addFormPropertyConfig(new FormPropertyConfig("principal",
                "org.apache.struts.mock.MockPrincipal", null));
        formBean.addFormPropertyConfig(new FormPropertyConfig("stringArray1",
                "java.lang.String[]", "{aaa,bbb,ccc}", 2)); // 2 should be ignored
        formBean.addFormPropertyConfig(new FormPropertyConfig("stringArray2",
                "java.lang.String[]", null, 3)); // 3 should be respected
        moduleConfig.addFormBeanConfig(formBean);

        // Action "/dynamic0" uses the "dynamic0" form bean in request scope
        mapping = new ActionMapping();
        mapping.setName("dynamic0");
        mapping.setPath("/dynamic0");
        mapping.setScope("request");
        mapping.setType("org.apache.struts.mock.MockAction");
        moduleConfig.addActionConfig(mapping);

        // Action "/noform" has no form bean associated with it
        mapping = new ActionMapping();
        mapping.setPath("/noform");
        mapping.setType("org.apache.struts.mock.MockAction");
        moduleConfig.addActionConfig(mapping);

        // Configure global forward declarations
        moduleConfig.addForwardConfig(new ForwardConfig("moduleForward",
                "/module/forward", false)); // No redirect, same module

        moduleConfig.addForwardConfig(new ForwardConfig("moduleRedirect",
                "/module/redirect", true)); // Redirect, same module

        moduleConfig.addForwardConfig(new ForwardConfig("contextForward",
                "/forward", false, // No redirect
                "/context")); // Specify module

        moduleConfig.addForwardConfig(new ForwardConfig("contextRedirect",
                "/redirect", true, // Redirect
                "/context")); // Specify module

        moduleConfig.addForwardConfig(new ForwardConfig("moduleNoslash",
                "module/noslash", false)); // No redirect, same module

        moduleConfig.addForwardConfig(new ForwardConfig("contextNoslash",
                "noslash", false, // No redirect
                "/context")); // Specify module
    }

    protected void setUpSecondApp() {
        ActionFormBean formBean = null;
        ActionMapping mapping = null;

        ModuleConfigFactory factoryObject = ModuleConfigFactory.createFactory();

        moduleConfig2 = factoryObject.createModuleConfig("/2");

        context.setAttribute(Globals.MODULE_KEY + "/2", moduleConfig2);

        // Forward "external" to "http://jakarta.apache.org/"
        moduleConfig2.addForwardConfig(new ActionForward("external",
                "http://jakarta.apache.org/", false));

        // Forward "foo" to "/baz.jsp" (different from default)
        moduleConfig2.addForwardConfig(new ActionForward("foo", "/baz.jsp",
                false));

        // Forward "relative1" to "relative.jsp" non-context-relative
        moduleConfig2.addForwardConfig(new ActionForward("relative1",
                "relative.jsp", false));

        // Forward "relative2" to "relative.jsp" context-relative
        moduleConfig2.addForwardConfig(new ActionForward("relative2",
                "relative.jsp", false));

        // Form Bean "static" is a standard ActionForm subclass (same as default)
        formBean =
            new ActionFormBean("static", "org.apache.struts.mock.MockFormBean");
        moduleConfig2.addFormBeanConfig(formBean);

        // Action "/static" uses the "static" form bean in request scope (same as default)
        mapping = new ActionMapping();
        mapping.setInput("/static.jsp");
        mapping.setName("static");
        mapping.setPath("/static");
        mapping.setScope("request");
        mapping.setType("org.apache.struts.mock.MockAction");
        moduleConfig2.addActionConfig(mapping);

        // Form Bean "dynamic2" is a DynaActionForm with the same properties
        formBean =
            new ActionFormBean("dynamic2",
                "org.apache.struts.action.DynaActionForm");
        formBean.addFormPropertyConfig(new FormPropertyConfig(
                "booleanProperty", "boolean", "false"));
        formBean.addFormPropertyConfig(new FormPropertyConfig(
                "stringProperty", "java.lang.String", null));
        moduleConfig2.addFormBeanConfig(formBean);

        // Action "/dynamic2" uses the "dynamic2" form bean in session scope
        mapping = new ActionMapping();
        mapping.setInput("/dynamic2.jsp");
        mapping.setName("dynamic2");
        mapping.setPath("/dynamic2");
        mapping.setScope("session");
        mapping.setType("org.apache.struts.mock.MockAction");
        moduleConfig2.addActionConfig(mapping);

        // Action "/noform" has no form bean associated with it (same as default)
        mapping = new ActionMapping();
        mapping.setPath("/noform");
        mapping.setType("org.apache.struts.mock.MockAction");
        moduleConfig2.addActionConfig(mapping);

        // Configure global forward declarations
        moduleConfig2.addForwardConfig(new ForwardConfig("moduleForward",
                "/module/forward", false)); // No redirect, same module

        moduleConfig2.addForwardConfig(new ForwardConfig("moduleRedirect",
                "/module/redirect", true)); // Redirect, same module

        moduleConfig2.addForwardConfig(new ForwardConfig("contextForward",
                "/forward", false, // No redirect
                "/context")); // Specify module

        moduleConfig2.addForwardConfig(new ForwardConfig("contextRedirect",
                "/redirect", true, // Redirect
                "/context")); // Specify module

        moduleConfig2.addForwardConfig(new ForwardConfig("moduleNoslash",
                "module/noslash", false)); // No redirect, same module

        moduleConfig2.addForwardConfig(new ForwardConfig("contextNoslash",
                "noslash", false, // No redirect
                "/context")); // Specify module
    }

    // Set up third app for testing URL mapping
    protected void setUpThirdApp() {
        ModuleConfigFactory factoryObject = ModuleConfigFactory.createFactory();

        moduleConfig3 = factoryObject.createModuleConfig("/3");

        context.setAttribute(Globals.MODULE_KEY + "/3", moduleConfig3);

        // Instantiate the controller configuration for this app
        ControllerConfig controller = new ControllerConfig();

        moduleConfig3.setControllerConfig(controller);

        // Configure the properties we will be testing
        controller.setForwardPattern("/forwarding$M$P");
        controller.setInputForward(true);
        controller.setPagePattern("/paging$M$P");

        // Configure global forward declarations
        moduleConfig3.addForwardConfig(new ForwardConfig("moduleForward",
                "/module/forward", false)); // No redirect, same module

        moduleConfig3.addForwardConfig(new ForwardConfig("moduleRedirect",
                "/module/redirect", true)); // Redirect, same module

        moduleConfig3.addForwardConfig(new ForwardConfig("contextForward",
                "/forward", false, // No redirect
                "/context")); // Specify module

        moduleConfig3.addForwardConfig(new ForwardConfig("contextRedirect",
                "/redirect", true, // Redirect
                "/context")); // Specify module

        moduleConfig3.addForwardConfig(new ForwardConfig("moduleNoslash",
                "module/noslash", false)); // No redirect, same module

        moduleConfig3.addForwardConfig(new ForwardConfig("contextNoslash",
                "noslash", false, // No redirect
                "/context")); // specify module
    }

    @AfterEach
    public void tearDown() {
        moduleConfig3 = null;
        moduleConfig2 = null;
        moduleConfig = null;
        config = null;
        context = null;
        page = null;
        principal = null;
        request = null;
        response = null;
        session = null;
    }

    // ------------------------------------------------------- Individual Tests
    // Test the basic setup of the mock object environment
    @Test
    public void testUtilBaseEnvironment() {
        // Validate the servlet API objects and inter-relationships
        assertNotNull(config, "config is present");
        assertNotNull(context, "context is present");
        assertNotNull(page, "page is present");
        assertNotNull(principal, "principal is present");
        assertNotNull(request, "request is present");
        assertNotNull(response, "response is present");
        assertNotNull(session, "session is present");
        assertEquals(config, page.getServletConfig(), "page-->config");
        assertEquals(context, config.getServletContext(), "config-->context");
        assertEquals(context, page.getServletContext(), "page-->context");
        assertEquals(request, page.getRequest(), "page-->request");
        assertEquals(response, page.getResponse(), "page-->response");
        assertEquals(session, page.getSession(), "page-->session");
        assertEquals(principal, request.getUserPrincipal(),
            "request-->principal");
        assertEquals(session, request.getSession(), "request-->session");
        assertEquals(context, session.getServletContext(), "session-->context");

        // Validate the configuration for the default module
        assertNotNull(moduleConfig, "moduleConfig is present");
        assertEquals(moduleConfig, context.getAttribute(Globals.MODULE_KEY),
            "context-->moduleConfig");

        // Validate the configuration for the second module
        assertNotNull(moduleConfig2, "moduleConfig2 is present");
        assertEquals(moduleConfig2, context.getAttribute(Globals.MODULE_KEY + "/2"),
            "context-->moduleConfig2");
    }
}