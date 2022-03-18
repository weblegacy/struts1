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
package org.apache.struts.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.MalformedURLException;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.mock.MockFormBean;
import org.apache.struts.mock.MockPrincipal;
import org.apache.struts.mock.TestMockBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link RequestUtils}.
 *
 * @version $Rev$ $Date$
 */
public class TestRequestUtils extends TestMockBase {
    // ----------------------------------------------------- Instance Variables
    // ----------------------------------------------------- Setup and Teardown
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

    // ------------------------------------------------------- Individual Tests
    // ---------------------------------------------------------- absoluteURL()
    @Test
    public void testAbsoluteURL() {
        request.setPathElements("/myapp", "/action.do", null, null);

        String url = null;

        try {
            url = RequestUtils.absoluteURL(request, "/foo/bar.jsp").toString();
            assertEquals("http://localhost:8080/myapp/foo/bar.jsp",
                url, "absoluteURL is correct");
        } catch (MalformedURLException e) {
            fail("Threw MalformedURLException: " + e);
        }
    }

    // ------------------------------------------------------------ actionURL()
    // Default application -- extension mapping
    @Test
    public void testActionURL1() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        request.setPathElements("/myapp", "/foo.do", null, null);

        String url =
            RequestUtils.actionURL(request,
                moduleConfig.findActionConfig("/dynamic"), "*.do");

        assertNotNull(url, "URL was returned");
        assertEquals("/dynamic.do", url, "URL value");
    }

    // Second application -- extension mapping
    @Test
    public void testActionURL2() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig2);
        request.setPathElements("/myapp", "/2/foo.do", null, null);

        String url =
            RequestUtils.actionURL(request,
                moduleConfig2.findActionConfig("/dynamic2"), "*.do");

        assertNotNull(url, "URL was returned");
        assertEquals("/2/dynamic2.do", url, "URL value");
    }

    // Default application -- path mapping
    @Test
    public void testActionURL3() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        request.setPathElements("/myapp", "/do/foo", null, null);

        String url =
            RequestUtils.actionURL(request,
                moduleConfig.findActionConfig("/dynamic"), "/do/*");

        assertNotNull(url, "URL was returned");
        assertEquals("/do/dynamic", url, "URL value");
    }

    // ----------------------------------------------------- createActionForm()
    // Default module -- No ActionForm should be created
    @Test
    public void testCreateActionForm1a() {
        request.setPathElements("/myapp", "/noform.do", null, null);

        ActionMapping mapping =
            (ActionMapping) moduleConfig.findActionConfig("/noform");

        assertNotNull(mapping, "Found /noform mapping");

        ActionForm form =
            RequestUtils.createActionForm(request, mapping, moduleConfig, null);

        assertNull(form, "No ActionForm returned");
    }

    // Second module -- No ActionForm should be created
    @Test
    public void testCreateActionForm1b() {
        request.setPathElements("/myapp", "/2/noform.do", null, null);

        ActionMapping mapping =
            (ActionMapping) moduleConfig2.findActionConfig("/noform");

        assertNotNull(mapping, "Found /noform mapping");

        ActionForm form =
            RequestUtils.createActionForm(request, mapping, moduleConfig2, null);

        assertNull(form, "No ActionForm returned");
    }

    // Default module -- Standard ActionForm should be created
    @Test
    public void testCreateActionForm2a() {
        request.setPathElements("/myapp", "/static.do", null, null);

        ActionMapping mapping =
            (ActionMapping) moduleConfig.findActionConfig("/static");

        assertNotNull(mapping, "Found /static mapping");
        assertNotNull(mapping.getName(), "Mapping has non-null name");
        assertEquals("static", mapping.getName(), "Mapping has correct name");
        assertNotNull(moduleConfig.findFormBeanConfig(mapping.getName()),
            "AppConfig has form bean " + mapping.getName());

        ActionForm form =
            RequestUtils.createActionForm(request, mapping, moduleConfig, null);

        assertNotNull(form, "ActionForm returned");
        assertInstanceOf(MockFormBean.class, form, "ActionForm of correct type");
    }

    // Second module -- Standard ActionForm should be created
    @Test
    public void testCreateActionForm2b() {
        request.setPathElements("/myapp", "/2/static.do", null, null);

        ActionMapping mapping =
            (ActionMapping) moduleConfig2.findActionConfig("/static");

        assertNotNull(mapping, "Found /static mapping");
        assertNotNull(mapping.getName(), "Mapping has non-null name");
        assertEquals("static", mapping.getName(), "Mapping has correct name");
        assertNotNull(moduleConfig.findFormBeanConfig(mapping.getName()),
            "AppConfig has form bean " + mapping.getName());

        ActionForm form =
            RequestUtils.createActionForm(request, mapping, moduleConfig2, null);

        assertNotNull(form, "ActionForm returned");
        assertInstanceOf(MockFormBean.class, form, "ActionForm of correct type");
    }

    // Default module -- Dynamic ActionForm should be created
    @Test
    public void testCreateActionForm3a() {
        request.setPathElements("/myapp", "/dynamic.do", null, null);

        ActionMapping mapping =
            (ActionMapping) moduleConfig.findActionConfig("/dynamic");

        assertNotNull(mapping, "Found /dynamic mapping");
        assertNotNull(mapping.getName(), "Mapping has non-null name");
        assertEquals("dynamic", mapping.getName(), "Mapping has correct name");
        assertNotNull(moduleConfig.findFormBeanConfig(mapping.getName()),
            "AppConfig has form bean " + mapping.getName());

        ActionForm form =
            RequestUtils.createActionForm(request, mapping, moduleConfig, null);

        assertNotNull(form, "ActionForm returned");
        assertInstanceOf(DynaActionForm.class, form, "ActionForm of correct type");
    }

    // Second module -- Dynamic ActionForm should be created
    @Test
    public void testCreateActionForm3b() {
        request.setPathElements("/myapp", "/2/dynamic2.do", null, null);

        ActionMapping mapping =
            (ActionMapping) moduleConfig2.findActionConfig("/dynamic2");

        assertNotNull(mapping, "Found /dynamic2 mapping");
        assertNotNull(mapping.getName(), "Mapping has non-null name");
        assertEquals("dynamic2", mapping.getName(), "Mapping has correct name");
        assertNotNull(moduleConfig2.findFormBeanConfig(mapping.getName()),
            "AppConfig has form bean " + mapping.getName());

        ActionForm form =
            RequestUtils.createActionForm(request, mapping, moduleConfig2, null);

        assertNotNull(form, "ActionForm returned");
        assertInstanceOf(DynaActionForm.class, form, "ActionForm of correct type");
    }

    // Default module -- Dynamic ActionForm with initializers
    @Test
    public void testCreateActionForm4a() {
        // Retrieve an appropriately configured DynaActionForm instance
        request.setPathElements("/myapp", "/dynamic0.do", null, null);

        ActionMapping mapping =
            (ActionMapping) moduleConfig.findActionConfig("/dynamic0");

        assertNotNull(mapping, "Found /dynamic0 mapping");
        assertNotNull(mapping.getName(), "Mapping has non-null name");
        assertEquals("dynamic0", mapping.getName(), "Mapping has correct name");
        assertNotNull(moduleConfig.findFormBeanConfig(mapping.getName()),
            "AppConfig has form bean " + mapping.getName());

        ActionForm form =
            RequestUtils.createActionForm(request, mapping, moduleConfig, null);

        assertNotNull(form, "ActionForm returned");
        assertInstanceOf(DynaActionForm.class, form, "ActionForm of correct type");

        // Validate the property values
        DynaActionForm dform = (DynaActionForm) form;
        Boolean booleanProperty = (Boolean) dform.get("booleanProperty");

        assertTrue(booleanProperty.booleanValue(), "booleanProperty is true");

        String stringProperty = (String) dform.get("stringProperty");

        assertEquals("String Property", stringProperty,
            "stringProperty is correct");

        Object value = null;

        value = dform.get("intArray1");
        assertNotNull(value, "intArray1 exists");
        assertInstanceOf(int[].class, value, "intArray1 is int[]");

        int[] intArray1 = (int[]) value;

        assertEquals(3, intArray1.length, "intArray1 length is correct");
        assertEquals(1, intArray1[0], "intArray1[0] value is correct");
        assertEquals(2, intArray1[1], "intArray1[1] value is correct");
        assertEquals(3, intArray1[2], "intArray1[2] value is correct");

        value = dform.get("intArray2");
        assertNotNull(value, "intArray2 exists");
        assertInstanceOf(int[].class, value, "intArray2 is int[]");

        int[] intArray2 = (int[]) value;

        assertEquals(5, intArray2.length, "intArray2 length is correct");
        assertEquals(0, intArray2[0], "intArray2[0] value is correct");
        assertEquals(0, intArray2[1], "intArray2[1] value is correct");
        assertEquals(0, intArray2[2], "intArray2[2] value is correct");
        assertEquals(0, intArray2[3], "intArray2[3] value is correct");
        assertEquals(0, intArray2[4], "intArray2[4] value is correct");

        value = dform.get("principal");
        assertNotNull(value, "principal exists");
        assertInstanceOf(MockPrincipal.class, value, "principal is MockPrincipal");

        value = dform.get("stringArray1");
        assertNotNull(value, "stringArray1 exists");
        assertInstanceOf(String[].class, value, "stringArray1 is int[]");

        String[] stringArray1 = (String[]) value;

        assertEquals(3, stringArray1.length, "stringArray1 length is correct");
        assertEquals("aaa", stringArray1[0], "stringArray1[0] value is correct");
        assertEquals("bbb", stringArray1[1], "stringArray1[1] value is correct");
        assertEquals("ccc", stringArray1[2], "stringArray1[2] value is correct");

        value = dform.get("stringArray2");
        assertNotNull(value, "stringArray2 exists");
        assertInstanceOf(String[].class, value, "stringArray2 is int[]");

        String[] stringArray2 = (String[]) value;

        assertEquals(3, stringArray2.length, "stringArray2 length is correct");
        assertEquals(new String(), stringArray2[0],
            "stringArray2[0] value is correct");
        assertEquals(new String(), stringArray2[1],
            "stringArray2[1] value is correct");
        assertEquals(new String(), stringArray2[2],
            "stringArray2[2] value is correct");

        // Different form beans should get different property value instances
        Object value1 = null;
        DynaActionForm dform1 =
            (DynaActionForm) RequestUtils.createActionForm(request, mapping,
                moduleConfig, null);

        value = dform.get("principal");
        value1 = dform1.get("principal");
        assertEquals(value, value1,
            "Different form beans get equal instance values");
        assertNotSame(value, value1,
            "Different form beans get different instances 1");

        value = dform.get("stringArray1");
        value1 = dform1.get("stringArray1");
        assertNotSame(value, value1,
            "Different form beans get different instances 2");

        dform1.set("stringProperty", "Different stringProperty value");
        value = dform.get("stringProperty");
        value1 = dform1.get("stringProperty");
        assertNotSame(value, value1,
            "Different form beans get different instances 3");
    }

    // ----------------------------------------------------------- forwardURL()
    // Default module (default forwardPattern)
    @Test
    public void testForwardURL1() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        request.setPathElements("/myapp", "/action.do", null, null);

        ForwardConfig forward = null;
        String result = null;

        // redirect=false, module=null
        forward = moduleConfig.findForwardConfig("moduleForward");
        assertNotNull(forward, "moduleForward found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "moduleForward computed");
        assertEquals("/module/forward", result, "moduleForward value");

        // redirect=true, module=null
        forward = moduleConfig.findForwardConfig("moduleRedirect");
        assertNotNull(forward, "moduleRedirect found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "moduleRedirect computed");
        assertEquals("/module/redirect", result, "moduleRedirect value");

        // redirect=false, module=/context
        forward = moduleConfig.findForwardConfig("contextForward");
        assertNotNull(forward, "contextForward found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "contextForward computed");
        assertEquals("/context/forward", result, "contextForward value");

        // redirect=true, module=/context
        forward = moduleConfig.findForwardConfig("contextRedirect");
        assertNotNull(forward, "contextRedirect found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "contextRedirect computed");
        assertEquals("/context/redirect", result, "contextRedirct value");

        // noslash, module=null
        forward = moduleConfig.findForwardConfig("moduleNoslash");
        assertNotNull(forward, "moduleNoslash found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "moduleNoslash computed");
        assertEquals("/module/noslash", result, "moduleNoslash value");

        // noslash, module=/
        forward = moduleConfig.findForwardConfig("contextNoslash");
        assertNotNull(forward, "contextNoslash found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "contextNoslash computed");
        assertEquals("/context/noslash", result, "contextNoslash value");
    }

    // Second module (default forwardPattern)
    @Test
    public void testForwardURL2() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig2);
        request.setPathElements("/myapp", "/2/action.do", null, null);

        ForwardConfig forward = null;
        String result = null;

        // redirect=false, module=null
        forward = moduleConfig2.findForwardConfig("moduleForward");
        assertNotNull(forward, "moduleForward found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "moduleForward computed");
        assertEquals("/2/module/forward", result, "moduleForward value");

        // redirect=true, module=null
        forward = moduleConfig2.findForwardConfig("moduleRedirect");
        assertNotNull(forward, "moduleRedirect found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "moduleRedirect computed");
        assertEquals("/2/module/redirect", result, "moduleRedirect value");

        // redirect=false, module=/context
        forward = moduleConfig2.findForwardConfig("contextForward");
        assertNotNull(forward, "contextForward found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "contextForward computed");
        assertEquals("/context/forward", result, "contextForward value");

        // redirect=true, module=/context
        forward = moduleConfig2.findForwardConfig("contextRedirect");
        assertNotNull(forward, "contextRedirect found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "contextRedirect computed");
        assertEquals("/context/redirect", result, "contextRedirct value");

        // noslash, module=null
        forward = moduleConfig2.findForwardConfig("moduleNoslash");
        assertNotNull(forward, "moduleNoslash found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "moduleNoslash computed");
        assertEquals("/2/module/noslash", result, "moduleNoslash value");

        // noslash, module=/
        forward = moduleConfig2.findForwardConfig("contextNoslash");
        assertNotNull(forward, "contextNoslash found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "contextNoslash computed");
        assertEquals("/context/noslash", result, "contextNoslash value");
    }

    // Third module (custom forwardPattern)
    @Test
    public void testForwardURL3() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig3);
        request.setPathElements("/myapp", "/3/action.do", null, null);

        ForwardConfig forward = null;
        String result = null;

        // redirect=false, module=null
        forward = moduleConfig3.findForwardConfig("moduleForward");
        assertNotNull(forward, "moduleForward found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "moduleForward computed");
        assertEquals("/forwarding/3/module/forward", result,
            "moduleForward value");

        // redirect=true, module=null
        forward = moduleConfig3.findForwardConfig("moduleRedirect");
        assertNotNull(forward, "moduleRedirect found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "moduleRedirect computed");
        assertEquals("/forwarding/3/module/redirect", result,
            "moduleRedirect value");

        // redirect=false, module=/context
        forward = moduleConfig3.findForwardConfig("contextForward");
        assertNotNull(forward, "contextForward found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "contextForward computed");
        assertEquals("/forwarding/context/forward", result,
            "contextForward value");

        // redirect=true, module=/context
        forward = moduleConfig3.findForwardConfig("contextRedirect");
        assertNotNull(forward, "contextRedirect found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "contextRedirect computed");
        assertEquals("/forwarding/context/redirect", result,
            "contextRedirct value");

        // noslash, module=null
        forward = moduleConfig3.findForwardConfig("moduleNoslash");
        assertNotNull(forward, "moduleNoslash found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "moduleNoslash computed");
        assertEquals("/forwarding/3/module/noslash", result,
            "moduleNoslash value");

        // noslash, module=/
        forward = moduleConfig3.findForwardConfig("contextNoslash");
        assertNotNull(forward, "contextNoslash found");
        result = RequestUtils.forwardURL(request, forward, null);
        assertNotNull(result, "contextNoslash computed");
        assertEquals("/forwarding/context/noslash", result,
            "contextNoslash value");
    }

    // Cross module forwards
    @Test
    public void testForwardURLa() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        request.setPathElements("/myapp", "/action.do", null, null);

        ForwardConfig forward = null;
        String result = null;

        // redirect=false, contextRelative=false, link to module 3
        forward = moduleConfig3.findForwardConfig("moduleForward");
        assertNotNull(forward, "moduleForward found");
        result = RequestUtils.forwardURL(request, forward, moduleConfig3);
        assertNotNull(result, "moduleForward computed");
        assertEquals("/forwarding/3/module/forward", result,
            "moduleForward value");

        // redirect=true, contextRelative=false, link to module 3
        forward = moduleConfig3.findForwardConfig("moduleRedirect");
        assertNotNull(forward, "moduleRedirect found");
        result = RequestUtils.forwardURL(request, forward, moduleConfig3);
        assertNotNull(result, "moduleRedirect computed");
        assertEquals("/forwarding/3/module/redirect", result,
            "moduleRedirect value");

        // redirect=false, module=/context
        forward = moduleConfig3.findForwardConfig("contextForward");
        assertNotNull(forward, "contextForward found");
        result = RequestUtils.forwardURL(request, forward, moduleConfig3);
        assertNotNull(result, "contextForward computed");
        assertEquals("/forwarding/context/forward", result,
            "contextForward value");

        // redirect=true, module=/context
        forward = moduleConfig3.findForwardConfig("contextRedirect");
        assertNotNull(forward, "contextRedirect found");
        result = RequestUtils.forwardURL(request, forward, moduleConfig3);
        assertNotNull(result, "contextRedirect computed");
        assertEquals("/forwarding/context/redirect", result,
            "contextRedirct value");

        // noslash, contextRelative=false, link to module 3
        forward = moduleConfig3.findForwardConfig("moduleNoslash");
        assertNotNull(forward, "moduleNoslash found");
        result = RequestUtils.forwardURL(request, forward, moduleConfig3);
        assertNotNull(result, "moduleNoslash computed");
        assertEquals("/forwarding/3/module/noslash", result,
            "moduleNoslash value");

        // noslash, module=/
        forward = moduleConfig3.findForwardConfig("contextNoslash");
        assertNotNull(forward, "contextNoslash found");
        result = RequestUtils.forwardURL(request, forward, moduleConfig3);
        assertNotNull(result, "contextNoslash computed");
        assertEquals("/forwarding/context/noslash", result,
            "contextNoslash value");
    }

    // ----------------------------------------------------------- requestURL()
    @Test
    public void testRequestURL() {
        request.setPathElements("/myapp", "/foo.do", null, null);

        String url = null;

        try {
            url = RequestUtils.requestURL(request).toString();
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "URL was returned");
        assertEquals("http://localhost:8080/myapp/foo.do", url, "URL value");
    }

    // ---------------------------------------------------- selectApplication()
    // Map to the default module -- direct
    @Test
    public void testSelectApplication1a() {
        request.setPathElements("/myapp", "/noform.do", null, null);
        ModuleUtils.getInstance().selectModule(request, context);

        ModuleConfig moduleConfig =
            (ModuleConfig) request.getAttribute(Globals.MODULE_KEY);

        assertNotNull(moduleConfig, "Selected a module");
        assertEquals("", moduleConfig.getPrefix(), "Selected correct module");

        // FIXME - check module resources?
    }

    // Map to the second module -- direct
    @Test
    public void testSelectApplication1b() {
        String[] prefixes = { "/1", "/2" };

        context.setAttribute(Globals.MODULE_PREFIXES_KEY, prefixes);
        request.setPathElements("/myapp", "/2/noform.do", null, null);

        ModuleUtils.getInstance().selectModule(request, context);

        ModuleConfig moduleConfig =
            (ModuleConfig) request.getAttribute(Globals.MODULE_KEY);

        assertNotNull(moduleConfig, "Selected a module");
        assertEquals("/2", moduleConfig.getPrefix(), "Selected correct module");

        // FIXME - check module resources?
    }

    // Map to the default module -- include
    @Test
    public void testSelectApplication2a() {
        request.setPathElements("/myapp", "/2/noform.do", null, null);
        request.setAttribute(RequestProcessor.INCLUDE_SERVLET_PATH, "/noform.do");
        ModuleUtils.getInstance().selectModule(request, context);

        ModuleConfig moduleConfig =
            (ModuleConfig) request.getAttribute(Globals.MODULE_KEY);

        assertNotNull(moduleConfig, "Selected an application");
        assertEquals("", moduleConfig.getPrefix(),
            "Selected correct application");

        // FIXME - check application resources?
    }

    // Map to the second module -- include
    @Test
    public void testSelectApplication2b() {
        String[] prefixes = { "/1", "/2" };

        context.setAttribute(Globals.MODULE_PREFIXES_KEY, prefixes);
        request.setPathElements("/myapp", "/noform.do", null, null);
        request.setAttribute(RequestProcessor.INCLUDE_SERVLET_PATH,
            "/2/noform.do");
        ModuleUtils.getInstance().selectModule(request, context);

        ModuleConfig moduleConfig =
            (ModuleConfig) request.getAttribute(Globals.MODULE_KEY);

        assertNotNull(moduleConfig, "Selected a module");
        assertEquals("/2", moduleConfig.getPrefix(), "Selected correct module");

        // FIXME - check application resources?
    }

    // ------------------------------------------------------------ serverURL()
    // Basic test on values in mock objects
    @Test
    public void testServerURL() {
        String url = null;

        try {
            url = RequestUtils.serverURL(request).toString();
        } catch (MalformedURLException e) {
            fail("Threw MalformedURLException: " + e);
        }

        assertNotNull(url, "serverURL is present");
        assertEquals("http://localhost:8080", url, "serverURL value");
    }
}
