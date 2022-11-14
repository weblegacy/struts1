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
package org.apache.struts.taglib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;
import org.apache.struts.mock.MockFormBean;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.apache.struts.mock.MockPageContext;
import org.apache.struts.mock.MockServletConfig;
import org.apache.struts.mock.MockServletContext;
import org.apache.struts.taglib.html.Constants;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for the {@link TagUtils}.
 */
public class TestTagUtils extends TagTestBase {

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(TestTagUtils.class);

    /**
     * Test Operators.
     */
    @Test
    public void testFilter() {
        assertNull(null, "Null");

        // Test Null
        assertNull(tagutils.filter(null), "Filter Test 1");

        // Test Empty String
        assertEquals("", tagutils.filter(""), "Filter Test 2");

        // Test Single Character
        assertEquals("a", tagutils.filter("a"), "Filter Test 3");

        // Test Multiple Characters
        assertEquals("adhdfhdfhadhf",
            tagutils.filter("adhdfhdfhadhf"), "Filter Test 4");

        // Test Each filtered Character
        assertEquals("&lt;", tagutils.filter("<"), "Filter Test 5");
        assertEquals("&gt;", tagutils.filter(">"), "Filter Test 6");
        assertEquals("&amp;", tagutils.filter("&"), "Filter Test 7");
        assertEquals("&quot;", tagutils.filter("\""), "Filter Test 8");
        assertEquals("&#39;", tagutils.filter("'"), "Filter Test 9");

        // Test filtering beginning, middle, end
        assertEquals("a&lt;", tagutils.filter("a<"), "Filter Test 10");
        assertEquals("&lt;a", tagutils.filter("<a"), "Filter Test 11");
        assertEquals("a&lt;a", tagutils.filter("a<a"), "Filter Test 12");

        // Test filtering beginning, middle, end
        assertEquals("abc&lt;", tagutils.filter("abc<"), "Filter Test 13");
        assertEquals("&lt;abc", tagutils.filter("<abc"), "Filter Test 14");
        assertEquals("abc&lt;abc", tagutils.filter("abc<abc"), "Filter Test 15");

        // Test Multiple Characters
        assertEquals(
            "&lt;input type=&quot;text&quot; value=&#39;Me &amp; You&#39;&gt;",
            tagutils.filter("<input type=\"text\" value='Me & You'>"),
            "Filter Test 16");
    }

    // ---------------------------------------------------- computeParameters()
    // No parameters and no transaction token
    @Test
    public void testComputeParameters0a() {
        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, null, null, null,
                    null, null, null, null, false);
        } catch (JspException e) {
            fail("JspException: " + e);
        }

        assertNull(map, "Map is null");
    }

    // No parameters but add transaction token
    @Test
    public void testComputeParameters0b() {
        request.getSession().setAttribute(Globals.TRANSACTION_TOKEN_KEY, "token");

        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, null, null, null,
                    null, null, null, null, true);
        } catch (JspException e) {
            fail("JspException: " + e);
        }

        assertNotNull(map, "Map is not null");
        assertEquals(1, map.size(), "One parameter in the returned map");
        assertTrue(map.containsKey(Constants.TOKEN_KEY),
            "Transaction token parameter present");
        assertEquals("token", map.get(Constants.TOKEN_KEY),
            "Transaction token parameter value");
    }

    // invalid scope name is requested
    @Test
    public void testComputeParametersInvalidScope() {
        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, null, null, null,
                    "session", "i-do-not-exist", null, null, false);

            fail("JspException not thrown");
        } catch (JspException e) {
            assertNull(map, "map is null");
        }
    }

    // specified bean is not found
    @Test
    public void testComputeParametersBeanNotFound() {
        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, null, null, null,
                    null, "i-do-not-exist", null, null, false);

            fail("JspException not thrown");
        } catch (JspException e) {
            assertNull(map, "map is null");
        }
    }

    // accessing this property causes an exception
    @Test
    public void testComputeParametersPropertyThrowsException() {
        request.getSession().setAttribute("SomeBean", new MockFormBean(true));

        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, null, null, null,
                    null, "SomeBean", "justThrowAnException", null, false);
            fail("JspException not thrown");
        } catch (JspException e) {
            assertNull(map, "map is null");
        }
    }

    @Test
    public void testComputeParametersParamIdParamPropThrowException() {
        request.getSession().setAttribute("SomeBean", new MockFormBean(true));

        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, "paramId",
                    "SomeBean", "justThrowAnException", null, null, null, null,
                    false);

            fail("JspException not thrown");
        } catch (JspException e) {
            assertNull(map, "map is null");
        }
    }

    @Test
    public void testComputeParametersParamValueToString() {
        request.getSession().setAttribute("SomeBean",
            new MockFormBean(false, false, 1.0));

        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, "paramId",
                    "SomeBean", "doubleValue", null, null, null, null, false);

            assertNotNull(map, "map is null");

            String val = (String) map.get("paramId");

            assertEquals("1.0", val, "paramId should be 1.0");
        } catch (JspException e) {
            fail("JspException not thrown");
        }
    }

    @Test
    @Disabled
    public void skiptestComputeParametersParamIdAsStringArray() {
        Map<String, String> params = new HashMap<>();

        //        String[] vals = new String[]{"test0, test1"};
        params.put("fooParamId", "fooParamValue");

        request.getSession().setAttribute("SomeBean", params);

        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, "fooParamId",
                    "SomeBean", null, null, "SomeBean", null, null, false);

            //            map = tagutils.computeParameters(
            //                    page, "paramId", "SomeBean", "stringArray", null,
            //                    null, null, null, false);
            assertNotNull(map, "map is null");

            String val = (String) map.get("key0");

            assertEquals("1.0", val, "paramId should be \"test\"");
        } catch (JspException e) {
            fail("JspException not thrown");
        }
    }

    // Single parameter -- name
    @Test
    public void testComputeParameters1a() {
        request.getSession().setAttribute("attr", "bar");

        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, "foo", "attr", null,
                    null, null, null, null, false);
        } catch (JspException e) {
            fail("JspException: " + e);
        }

        assertNotNull(map, "Map is not null");
        assertEquals(1, map.size(), "One parameter in the returned map");
        assertTrue(map.containsKey("foo"), "Parameter present");
        assertEquals("bar", map.get("foo"), "Parameter value");
    }

    // Single parameter -- scope + name
    @Test
    public void testComputeParameters1b() {
        request.setAttribute("attr", "bar");

        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, "foo", "attr", null,
                    "request", null, null, null, false);
        } catch (JspException e) {
            fail("JspException: " + e);
        }

        assertNotNull(map, "Map is not null");
        assertEquals(1, map.size(), "One parameter in the returned map");
        assertTrue(map.containsKey("foo"), "Parameter present");
        assertEquals("bar", map.get("foo"), "Parameter value");
    }

    // Single parameter -- scope + name + property
    @Test
    public void testComputeParameters1c() {
        request.setAttribute("attr", new MockFormBean("bar"));

        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, "foo", "attr",
                    "stringProperty", "request", null, null, null, false);
        } catch (JspException e) {
            fail("JspException: " + e);
        }

        assertNotNull(map, "Map is not null");
        assertEquals(1, map.size(), "One parameter in the returned map");
        assertTrue(map.containsKey("foo"), "Parameter present");
        assertEquals("bar", map.get("foo"), "Parameter value");
    }

    // Provided map -- name
    @Test
    public void testComputeParameters2a() {
        Map<String, String> params = new HashMap<>();

        params.put("foo1", "bar1");
        params.put("foo2", "bar2");
        request.getSession().setAttribute("attr", params);

        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, null, null, null,
                    null, "attr", null, null, false);
        } catch (JspException e) {
            fail("JspException: " + e);
        }

        assertNotNull(map, "Map is not null");
        assertEquals(2, map.size(), "Two parameter in the returned map");
        assertTrue(map.containsKey("foo1"), "Parameter foo1 present");
        assertEquals("bar1", (String) map.get("foo1"), "Parameter foo1 value");
        assertTrue(map.containsKey("foo2"), "Parameter foo2 present");
        assertEquals("bar2", (String) map.get("foo2"), "Parameter foo2 value");
    }

    // Provided map -- scope + name
    @Test
    public void testComputeParameters2b() {
        Map<String, String> params = new HashMap<>();

        params.put("foo1", "bar1");
        params.put("foo2", "bar2");
        request.setAttribute("attr", params);

        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, null, null, null,
                    null, "attr", null, "request", false);
        } catch (JspException e) {
            fail("JspException: " + e);
        }

        assertNotNull(map, "Map is not null");
        assertEquals(2, map.size(), "Two parameter in the returned map");
        assertTrue(map.containsKey("foo1"), "Parameter foo1 present");
        assertEquals("bar1", map.get("foo1"), "Parameter foo1 value");
        assertTrue(map.containsKey("foo2"), "Parameter foo2 present");
        assertEquals("bar2", map.get("foo2"), "Parameter foo2 value");
    }

    // Provided map -- scope + name + property
    @Test
    public void testComputeParameters2c() {
        request.setAttribute("attr", new MockFormBean());

        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, null, null, null,
                    null, "attr", "mapProperty", "request", false);
        } catch (JspException e) {
            fail("JspException: " + e);
        }

        assertNotNull(map, "Map is not null");
        assertEquals(2, map.size(), "Two parameter in the returned map");
        assertTrue(map.containsKey("foo1"), "Parameter foo1 present");
        assertEquals("bar1", map.get("foo1"), "Parameter foo1 value");
        assertTrue(map.containsKey("foo2"), "Parameter foo2 present");
        assertEquals("bar2", map.get("foo2"), "Parameter foo2 value");
    }

    // Provided map -- name with one key and two values
    @Test
    public void testComputeParameters2d() {
        Map<String, String[]> params = new HashMap<>();

        params.put("foo", new String[] { "bar1", "bar2" });
        request.getSession().setAttribute("attr", params);

        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, null, null, null,
                    null, "attr", null, null, false);
        } catch (JspException e) {
            fail("JspException: " + e);
        }

        assertNotNull(map, "Map is not null");
        assertEquals(1, map.size(), "One parameter in the returned map");
        assertTrue(map.containsKey("foo"), "Parameter foo present");
        assertInstanceOf(String[].class, map.get("foo"),
            "Parameter foo value type");

        String[] values = (String[]) map.get("foo");

        assertEquals(2, values.length, "Values count");
    }

    // Kitchen sink combination of parameters with a merge
    @Test
    public void testComputeParameters3a() {
        request.setAttribute("attr", new MockFormBean("bar3"));
        request.getSession().setAttribute(Globals.TRANSACTION_TOKEN_KEY, "token");

        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, "foo1", "attr",
                    "stringProperty", "request", "attr", "mapProperty",
                    "request", true);
        } catch (JspException e) {
            fail("JspException: " + e);
        }

        assertNotNull(map, "Map is not null");
        assertEquals(3, map.size(), "Three parameter in the returned map");

        assertTrue(map.containsKey("foo1"), "Parameter foo1 present");
        assertInstanceOf(String[].class, map.get("foo1"),
            "Parameter foo1 value type");

        String[] values = (String[]) map.get("foo1");

        assertEquals(2, values.length, "Values count");

        assertTrue(map.containsKey("foo2"), "Parameter foo2 present");
        assertEquals("bar2", map.get("foo2"), "Parameter foo2 value");

        assertTrue(map.containsKey(Constants.TOKEN_KEY),
            "Transaction token parameter present");
        assertEquals("token", map.get(Constants.TOKEN_KEY),
            "Transaction token parameter value");
    }

    // Kitchen sink combination of parameters with a merge
    // with array values in map
    @Test
    public void testComputeParameters3aa() {
        request.setAttribute("attr", new MockFormBean("bar3"));
        request.getSession().setAttribute(Globals.TRANSACTION_TOKEN_KEY, "token");

        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, "foo1", "attr",
                    "stringProperty", "request", "attr",
                    "mapPropertyArrayValues", "request", true);
        } catch (JspException e) {
            fail("JspException: " + e);
        }

        assertNotNull(map, "Map is not null");
        assertEquals(3, map.size(), "Three parameter in the returned map");

        assertTrue(map.containsKey("foo1"), "Parameter foo1 present");
        assertInstanceOf(String[].class, map.get("foo1"),
            "Parameter foo1 value type");

        String[] values = (String[]) map.get("foo1");

        assertEquals(3, values.length, "Values count");

        assertTrue(map.containsKey("foo2"), "Parameter foo2 present");

        String[] arrayValues = (String[]) map.get("foo2");
        String val = arrayValues[0];

        assertEquals("bar2", val, "Parameter foo2 value");

        assertTrue(map.containsKey(Constants.TOKEN_KEY),
            "Transaction token parameter present");
        assertEquals("token", map.get(Constants.TOKEN_KEY),
            "Transaction token parameter value");
    }

    // Kitchen sink combination of parameters with a merge
    @Test
    public void testComputeParameters3b() {
        request.setAttribute("attr", new MockFormBean("bar3"));
        request.getSession().setAttribute(Globals.TRANSACTION_TOKEN_KEY, "token");

        Map<String, Object> map = null;

        try {
            map = tagutils.computeParameters(pageContext, "foo1", "attr",
                    "stringProperty", "request", "attr", "mapProperty",
                    "request", true);
        } catch (JspException e) {
            fail("JspException: " + e);
        }

        assertNotNull(map, "Map is not null");
        assertEquals(3, map.size(), "Three parameter in the returned map");

        assertTrue(map.containsKey("foo1"), "Parameter foo1 present");
        assertInstanceOf(String[].class, map.get("foo1"),
            "Parameter foo1 value type");

        String[] values = (String[]) map.get("foo1");

        assertEquals(2, values.length, "Values count");

        assertTrue(map.containsKey("foo2"), "Parameter foo2 present");
        assertEquals("bar2", map.get("foo2"), "Parameter foo2 value");

        assertTrue(map.containsKey(Constants.TOKEN_KEY),
            "Transaction token parameter present");
        assertEquals("token", map.get(Constants.TOKEN_KEY),
            "Transaction token parameter value");
    }

    // ----------------------------------------------------------- computeURL()
    // Default module -- Forward only
    @Test
    public void testComputeURL1a() {
        request.setPathElements("/myapp", "/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, "foo", null, null, null,
                    null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals("/myapp/bar.jsp", url, "url value");
    }

    // Default module -- Href only
    @Test
    public void testComputeURL1b() {
        request.setPathElements("/myapp", "/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, "http://foo.com/bar",
                    null, null, null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals("http://foo.com/bar", url, "url value");
    }

    // Default module -- Page only
    @Test
    public void testComputeURL1c() {
        request.setPathElements("/myapp", "/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, null, "/bar", null,
                    null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals("/myapp/bar", url, "url value");
    }

    // Default module -- Forward with pattern
    @Test
    public void testComputeURL1d() {
        moduleConfig.getControllerConfig().setForwardPattern("$C/WEB-INF/pages$M$P");
        request.setPathElements("/myapp", "/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, "foo", null, null, null,
                    null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals("/myapp/WEB-INF/pages/bar.jsp", url, "url value");
    }

    // Default module -- Page with pattern
    @Test
    public void testComputeURL1e() {
        moduleConfig.getControllerConfig().setPagePattern("$C/WEB-INF/pages$M$P");
        request.setPathElements("/myapp", "/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, null, "/bar", null,
                    null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals("/myapp/WEB-INF/pages/bar", url, "url value");
    }

    // Default module -- Forward with relative path (non-context-relative)
    @Test
    public void testComputeURL1f() {
        request.setPathElements("/myapp", "/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, "relative1", null, null,
                    null, null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals(
        //                     "/myapp/relative.jsp",
        "relative.jsp", url,
        "url value");
    }

    // Default module -- Forward with relative path (context-relative)
    @Test
    public void testComputeURL1g() {
        request.setPathElements("/myapp", "/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, "relative2", null, null,
                    null, null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals(
        //                     "/myapp/relative.jsp",
        "relative.jsp", url,
        "url value");
    }

    // Default module -- Forward with external path
    @Test
    public void testComputeURL1h() {
        request.setPathElements("/myapp", "/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, "external", null, null,
                    null, null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals("http://struts.apache.org/", url, "url value");
    }

    // Second module -- Forward only
    @Test
    public void testComputeURL2a() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig2);
        request.setPathElements("/myapp", "/2/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, "foo", null, null, null,
                    null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals("/myapp/2/baz.jsp", url, "url value");
    }

    // Second module -- Href only
    @Test
    public void testComputeURL2b() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig2);
        request.setPathElements("/myapp", "/2/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, "http://foo.com/bar",
                    null, null, null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals("http://foo.com/bar", url, "url value");
    }

    // Second module -- Page only
    @Test
    public void testComputeURL2c() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig2);
        request.setPathElements("/myapp", "/2/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, null, "/bar", null,
                    null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals("/myapp/2/bar", url, "url value");
    }

    // Default module -- Forward with pattern
    @Test
    public void testComputeURL2d() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig2);
        moduleConfig2.getControllerConfig().setForwardPattern("$C/WEB-INF/pages$M$P");
        request.setPathElements("/myapp", "/2/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, "foo", null, null, null,
                    null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals("/myapp/WEB-INF/pages/2/baz.jsp", url, "url value");
    }

    // Second module -- Page with pattern
    @Test
    public void testComputeURL2e() {
        moduleConfig2.getControllerConfig().setPagePattern("$C/WEB-INF/pages$M$P");
        request.setAttribute(Globals.MODULE_KEY, moduleConfig2);
        request.setPathElements("/myapp", "/2/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, null, "/bar", null,
                    null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals("/myapp/WEB-INF/pages/2/bar", url, "url value");
    }

    // Second module -- Forward with relative path (non-context-relative)
    @Test
    public void testComputeURL2f() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig2);
        request.setPathElements("/myapp", "/2/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, "relative1", null, null,
                    null, null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals(
        //                     "/myapp/2/relative.jsp",
        "relative.jsp", url,
        "url value");
    }

    // Second module -- Forward with relative path (context-relative)
    @Test
    public void testComputeURL2g() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig2);
        request.setPathElements("/myapp", "/2/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, "relative2", null, null,
                    null, null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals(
        //                     "/myapp/relative.jsp",
        "relative.jsp", url,
        "url value");
    }

    // Second module -- Forward with external path
    @Test
    public void testComputeURL2h() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig2);
        request.setPathElements("/myapp", "/2/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, "external", null, null,
                    null, null, null, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals("http://struts.apache.org/", url, "url value");
    }

    // Add parameters only -- forward URL
    @Test
    public void testComputeURL3a() {
        request.setPathElements("/myapp", "/action.do", null, null);

        Map<String, String> map = new HashMap<>();

        map.put("foo1", "bar1");
        map.put("foo2", "bar2");

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, null, "/bar", null,
                    null, map, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertTrue(
            url.equals("/myapp/bar?foo1=bar1&amp;foo2=bar2")
            || url.equals("/myapp/bar?foo2=bar2&amp;foo1=bar1"),
            "url value");
    }

    // Add anchor only -- forward URL
    @Test
    public void testComputeURL3b() {
        request.setPathElements("/myapp", "/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, null, "/bar", null,
                    null, null, "anchor", false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals("/myapp/bar#anchor", url, "url value");
    }

    // Add parameters + anchor -- forward URL
    @Test
    public void testComputeURL3c() {
        request.setPathElements("/myapp", "/action.do", null, null);

        Map<String, String> map = new HashMap<>();

        map.put("foo1", "bar1");
        map.put("foo2", "bar2");

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, null, "/bar", null,
                    null, map, "anchor", false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertTrue(
            url.equals("/myapp/bar?foo1=bar1&amp;foo2=bar2#anchor")
            || url.equals("/myapp/bar?foo2=bar2&amp;foo1=bar1#anchor"),
            "url value");
    }

    // Add parameters only -- redirect URL
    @Test
    public void testComputeURL3d() {
        request.setPathElements("/myapp", "/action.do", null, null);

        Map<String, String> map = new HashMap<>();

        map.put("foo1", "bar1");
        map.put("foo2", "bar2");

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, null, "/bar", null,
                    null, map, null, true);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertTrue(
            url.equals("/myapp/bar?foo1=bar1&foo2=bar2")
            || url.equals("/myapp/bar?foo2=bar2&foo1=bar1"),
            "url value");
    }

    // Add anchor only -- redirect URL
    @Test
    public void testComputeURL3e() {
        request.setPathElements("/myapp", "/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, null, "/bar", null,
                    null, null, "anchor", true);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals("/myapp/bar#anchor", url, "url value");
    }

    // Add parameters + anchor -- redirect URL
    @Test
    public void testComputeURL3f() {
        request.setPathElements("/myapp", "/action.do", null, null);

        Map<String, String> map = new HashMap<>();

        map.put("foo1", "bar1");
        map.put("foo2", "bar2");

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, null, "/bar", null,
                    null, map, "anchor", false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertTrue(
            url.equals("/myapp/bar?foo1=bar1&amp;foo2=bar2#anchor")
            || url.equals("/myapp/bar?foo2=bar2&amp;foo1=bar1#anchor"),
            "url value");
    }

    // Add parameters only -- forward URL -- do not encode seperator
    @Test
    public void testComputeURL3g() {
        request.setPathElements("/myapp", "/action.do", null, null);

        Map<String, String> map = new HashMap<>();

        map.put("foo1", "bar1");
        map.put("foo2", "bar2");

        String url = null;

        try {
            url = tagutils.computeURLWithCharEncoding(pageContext, null, null,
                    "/bar", null, null, map, null, false, false, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertTrue(
            url.equals("/myapp/bar?foo1=bar1&foo2=bar2")
            || url.equals("/myapp/bar?foo2=bar2&foo1=bar1"),
            "url value");
    }

    // Add parameters only
    //  -- forward URL
    //  -- do not encode seperator
    //  -- send param with null value
    @Test
    public void testComputeURL3h() {
        request.setPathElements("/myapp", "/action.do", null, null);

        Map<String, String> map = new HashMap<>();

        map.put("foo1", null);

        String url = null;

        try {
            url = tagutils.computeURLWithCharEncoding(pageContext, null, null,
                    "/bar", null, null, map, null, false, false, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals(url, "/myapp/bar?foo1=", "url value");
    }

    // Add parameters only
    //  -- forward URL
    //  -- do not encode seperator
    //  -- send param with null value
    //  -- add ? to page
    @Test
    public void testComputeURL3i() {
        request.setPathElements("/myapp", "/action.do", null, null);

        Map<String, String> map = new HashMap<>();

        map.put("foo1", null);

        String url = null;

        try {
            url = tagutils.computeURLWithCharEncoding(pageContext, null, null,
                    "/bar?", null, null, map, null, false, false, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals(url, "/myapp/bar?&foo1=", "url value");
    }

    // Add parameters only
    //  -- forward URL
    //  -- do not encode seperator
    //  -- send param with null value
    //  -- add ? and param to page
    @Test
    public void testComputeURL3j() {
        request.setPathElements("/myapp", "/action.do", null, null);

        Map<String, String> map = new HashMap<>();

        map.put("foo1", null);
        map.put("foo2", "bar2");

        String url = null;

        try {
            url = tagutils.computeURLWithCharEncoding(pageContext, null, null,
                    "/bar?a=b", null, null, map, null, false, false, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertTrue(
            url.equals("/myapp/bar?a=b&foo1=&foo2=bar2")
            || url.equals("/myapp/bar?a=b&foo2=bar2&foo1="),
            "url value");
    }

    // -- Add Parameters
    // -- Parameter as String Array
    @Test
    public void testComputeURL3k() {
        request.setPathElements("/myapp", "/action.do", null, null);

        Map<String, String[]> map = new HashMap<>();

        map.put("foo1", new String[] { "bar1", "baz1" });

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, null, "/bar", null,
                    null, map, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertTrue(
            url.equals("/myapp/bar?foo1=bar1&amp;foo1=baz1")
            || url.equals("/myapp/bar?foo1=baz1&amp;foo1=bar1"),
            "url value");
    }

    // -- Add Parameters
    // -- Parameter as non String or String Array
    @Test
    public void testComputeURL3l() {
        request.setPathElements("/myapp", "/action.do", null, null);

        Map<String, Double> map = new HashMap<>();

        map.put("foo1", 0.0);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, null, "/bar", null,
                    null, map, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals(url, "/myapp/bar?foo1=0.0", "url value");
    }

    // -- Add Parameters
    // -- Parameter as non String or String Array
    // -- with ? on path
    @Test
    public void testComputeURL3m() {
        request.setPathElements("/myapp", "/action.do", null, null);

        Map<String, Double> map = new HashMap<>();

        map.put("foo1", 0.0);

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, null, "/bar?", null,
                    null, map, null, false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertEquals(url, "/myapp/bar?&amp;foo1=0.0", "url value");
    }

    @Test
    public void testComputeURLCharacterEncoding() {
        request.setPathElements("/myapp", "/action.do", null, null);

        String url = null;

        try {
            url = tagutils.computeURLWithCharEncoding(pageContext, "foo", null,
                    null, null, null, null, null, false, true, true);
            fail("Exception not thrown");
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        } catch (UnsupportedOperationException e) {
            assertNull(url, "url should be null");
        }
    }

    @Test
    public void testComputeURLCharacterEncodingMultipleSpecifier() {
        verifyBadSetOfSpecifiers("foo", "foo", null, null);
        verifyBadSetOfSpecifiers("foo", null, "foo", null);
        verifyBadSetOfSpecifiers("foo", null, null, "foo");

        verifyBadSetOfSpecifiers(null, "foo", "foo", null);
        verifyBadSetOfSpecifiers(null, "foo", null, "foo");

        verifyBadSetOfSpecifiers(null, null, "foo", "foo");
    }

    @Test
    public void testComputeURLCharacterEncodingAction() {
        ActionConfig actionConfig = new ActionConfig();

        actionConfig.setName("baz");
        actionConfig.setPath("/baz");

        moduleConfig.addActionConfig(actionConfig);

        request.setPathElements("/myapp", "/foo.do", null, null);

        Map<String, String> map = new HashMap<>();

        map.put("foo1", "bar1");
        map.put("foo2", "bar2");

        String url = null;

        try {
            url = tagutils.computeURL(pageContext, null, null, null, "baz",
                    null, map, "anchor", false);
        } catch (MalformedURLException e) {
            fail("MalformedURLException: " + e);
        }

        assertNotNull(url, "url present");
        assertTrue(
            url.equals("/myapp/baz?foo1=bar1&amp;foo2=bar2#anchor")
            || url.equals("/myapp/baz?foo2=bar2&amp;foo1=bar1#anchor"),
            "url value");
    }

    // -------------------------------------------------------------- pageURL()
    // Default module (default pagePattern)
    @Test
    public void testPageURL1() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        request.setPathElements("/myapp", "/action.do", null, null);

        String page = null;
        String result = null;

        // Straight substitution
        page = "/mypages/index.jsp";
        result = tagutils.pageURL(request, page, moduleConfig);
        assertNotNull(result, "straight sub found");
        assertEquals("/mypages/index.jsp", result, "straight sub value");
    }

    // Second module (default pagePattern)
    @Test
    public void testPageURL2() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig2);
        request.setPathElements("/myapp", "/2/action.do", null, null);

        String page = null;
        String result = null;

        // Straight substitution
        page = "/mypages/index.jsp";
        result = tagutils.pageURL(request, page, moduleConfig2);
        assertNotNull(result, "straight sub found");
        assertEquals("/2/mypages/index.jsp", result, "straight sub value");
    }

    // Third module (custom pagePattern)
    // TODO finish me
    @Test
    public void testPageURL3a() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig3);
        request.setPathElements("/myapp", "/3/action.do", null, null);

        //        String page = null;
        //        String result = null;
    }

    // Third module (custom pagePattern)
    @Test
    public void testPageURL3b() {
        request.setAttribute(Globals.MODULE_KEY, moduleConfig3);
        request.setPathElements("/myapp", "/3/action.do", null, null);

        String page = null;
        String result = null;

        // Straight substitution
        page = "/mypages/index.jsp";
        result = tagutils.pageURL(request, page, moduleConfig3);
        assertNotNull(result, "straight sub found");
        assertEquals("/3/mypages/index.jsp", result, "straight sub value");
    }

    /**
     * Helper method that verifies the supplied specifiers.
     *
     * @param forward    The forward specified
     * @param href       The href specified
     * @param pageString The pageString specified
     * @param action     The action specified
     */
    private void verifyBadSetOfSpecifiers(String forward, String href,
        String pageString, String action) {
        String url = null;

        try {
            url = tagutils.computeURLWithCharEncoding(pageContext, forward,
                    href, pageString, action, null, null, null, false, true,
                    false);
        } catch (MalformedURLException e) {
            assertNull(url, "url should be null");
        } catch (UnsupportedOperationException e) {
            fail("MalformedURLException not thrown");
        }
    }

    // -------------------------------------------------------------- encodeURL()
    // Default module (default pagePattern)
    @Test
    public void testencodeURL1() {
        String encodedURL = null;

        encodedURL = tagutils.encodeURL("foo-bar.baz");
        assertEquals("foo-bar.baz", encodedURL, "encode url");
    }

    // ------------------------------------------ getActionErrors()
    // ActionErrors
    @Test
    public void testGetActionErrors1a() {
        ActionMessages actionErrors = new ActionMessages();

        actionErrors.add("prop", new ActionMessage("key.key"));
        request.setAttribute("errors", actionErrors);

        try {
            ActionMessages errors =
                tagutils.getActionMessages(pageContext, "errors");

            assertNotNull(errors, "errors should not be null");
            assertNotNull(errors.get("prop"), "errors prop should not be null");

            String val = null;
            int i = 0;

            for (Iterator<ActionMessage> iter = errors.get("prop"); iter.hasNext();) {
                ActionMessage error = iter.next();

                val = error.getKey();
                i++;
            }

            assertEquals(1, i, "only 1 error");
            assertEquals("key.key", val, "errors prop should match");
        } catch (JspException e) {
            fail(e.getMessage());
        }
    }

    // String
    @Test
    public void testGetActionErrors1b() {
        request.setAttribute("foo", "bar");

        try {
            ActionMessages errors =
                tagutils.getActionMessages(pageContext, "foo");

            assertNotNull(errors, "errors should not be null");
            assertNotNull(errors.get("prop"), "errors prop should not be null");

            String key = null;
            int i = 0;

            for (Iterator<ActionMessage> iter = errors.get(ActionMessages.GLOBAL_MESSAGE);
                iter.hasNext();) {
                ActionMessage error = iter.next();

                key = error.getKey();

                Object[] values = error.getValues();

                assertNull(values);
                i++;
            }

            assertEquals(1, i, "only 1 error");
            assertEquals("bar", key, "key should match");
        } catch (JspException e) {
            fail(e.getMessage());
        }
    }

    // String Array
    @Test
    public void testGetActionErrors1c() {
        String[] vals = new String[] { "bar", "baz" };

        request.setAttribute("foo", vals);

        try {
            ActionMessages errors =
                tagutils.getActionMessages(pageContext, "foo");

            assertNotNull(errors, "errors should not be null");
            assertNotNull(errors.get("prop"), "errors prop should not be null");

            String key = null;
            int i = 0;

            for (Iterator<ActionMessage> iter = errors.get(ActionMessages.GLOBAL_MESSAGE);
                iter.hasNext();) {
                ActionMessage error = iter.next();

                key = error.getKey();

                Object[] values = error.getValues();

                assertNull((values));
                assertEquals(vals[i], key, "1st key should match");
                i++;
            }

            assertEquals(2, i, "only 2 errors");
        } catch (JspException e) {
            fail(e.getMessage());
        }
    }

    // String Array (thrown JspException)
    @Test
    public void testGetActionErrors1d() {
        request.setAttribute("foo", new MockFormBean());

        ActionMessages errors = null;

        try {
            errors = tagutils.getActionMessages(pageContext, "foo");
            fail("should have thrown JspException");
        } catch (JspException e) {
            assertNull(errors, "errors should be null");
        }
    }

    // ActionErrors (thrown Exception)
    // TODO -- currently this does not hit the line for caught Exception
    @Test
    public void testGetActionErrors1e() {
        ActionMessages actionErrors = new ActionMessages();

        actionErrors.add("prop", new ActionMessage("key.key"));
        request.setAttribute("errors", actionErrors);

        try {
            ActionMessages errors =
                tagutils.getActionMessages(pageContext, "does-not-exist");

            assertNotNull(errors, "errors should not be null");
            assertNotNull(errors.get("prop"), "errors prop should not be null");

            for (Iterator<ActionMessage> iter = errors.get("prop"); iter.hasNext();) {
                fail("Should not have any errors for does-not-exist");
            }
        } catch (JspException e) {
            fail(e.getMessage());
        }
    }

    // ------------------------------------------ getActionMappingName()
    @Test
    public void testGetActionMappingName1() {
        String[] paths =
            {
                "foo", "foo.do", "foo?foo=bar", "foo?foo=bar&bar=baz",
                "foo?foo=bar&amp;bar=baz"
            };

        String[][] prepends =
            {
                { "", "/foo" },
                { "/", "/foo" },
                { "bar/", "/bar/foo" },
                { "/bar/", "/bar/foo" }
            };

        String[] appends =
            {
                "", "#anchor", "?", "?#", "?foo=bar", "?foo1=bar1&foo2=bar2",
                "?foo1=bar1&amp;foo2=bar2"
            };

        String finalResult = null;

        String path = null;
        String results = null;
        int ct = 0;

        for (int i = 0; i < appends.length; i++) {
            for (int j = 0; j < prepends.length; j++) {
                finalResult = prepends[j][1];

                for (int k = 0; k < paths.length; k++) {
                    path = prepends[j][0] + paths[k] + appends[i];
                    results = tagutils.getActionMappingName(path);

                    assertEquals(finalResult, results, "Path should translate to result");
                    ct++;
                }
            }
        }

        log.debug("{} assertions run in this test", ct);
    }

    @Test
    public void testString_getActionMappingURL_String_PageContext() {
        ActionConfig actionConfig = new ActionConfig();

        actionConfig.setParameter("/foo");
        moduleConfig.addActionConfig(actionConfig);

        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        request.setPathElements("/myapp", "/foo.do", null, null);

        assertEquals("/myapp/foo",
            tagutils.getActionMappingURL("/foo", pageContext), "Check path /foo");
    }

    // use servlet mapping (extension mapping)
    @Test
    public void testString_getActionMappingURL_String_String_PageContext_boolean1() {
        pageContext.getServletContext().setAttribute(Globals.SERVLET_KEY, "*.do");

        ActionConfig actionConfig = new ActionConfig();

        actionConfig.setParameter("/foo");
        moduleConfig.addActionConfig(actionConfig);

        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        request.setPathElements("/myapp", "/baz.do", null, null);

        assertEquals("/myapp/foo.do",
            tagutils.getActionMappingURL("/foo", pageContext), "Check path /foo");
    }

    // use servlet mapping (extension mapping)
    //  -- with params
    @Test
    public void testString_getActionMappingURL_String_String_PageContext_boolean2() {
        pageContext.getServletContext().setAttribute(Globals.SERVLET_KEY, "*.do");

        ActionConfig actionConfig = new ActionConfig();

        actionConfig.setParameter("/foo");
        moduleConfig.addActionConfig(actionConfig);

        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        request.setPathElements("/myapp", "/baz.do?foo=bar", null, null);

        assertEquals("/myapp/foo.do?foo=bar",
            tagutils.getActionMappingURL("/foo?foo=bar", pageContext),
            "Check path /foo");
    }

    // use servlet mapping (extension mapping)
    //  -- path as "/"
    // (this is probably not a realistic use case)
    @Test
    public void testString_getActionMappingURL_String_String_PageContext_boolean3() {
        pageContext.getServletContext().setAttribute(Globals.SERVLET_KEY, "*.do");

        ActionConfig actionConfig = new ActionConfig();

        actionConfig.setParameter("/foo");
        moduleConfig.addActionConfig(actionConfig);

        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        request.setPathElements("/mycontext", "/baz", null, null);

        assertEquals("/mycontext/.do",
            tagutils.getActionMappingURL("/", pageContext), "Check path /foo");
    }

    // use servlet mapping (path mapping)
    @Test
    public void testString_getActionMappingURL_String_String_PageContext_boolean4() {
        pageContext.getServletContext().setAttribute(Globals.SERVLET_KEY,
            "/myapp/*");

        ActionConfig actionConfig = new ActionConfig();

        actionConfig.setParameter("/foo");
        moduleConfig.addActionConfig(actionConfig);

        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        request.setPathElements("/mycontext", "/baz", null, null);

        assertEquals("/mycontext/myapp/foo",
            tagutils.getActionMappingURL("/foo", pageContext),
            "Check path /foo");
    }

    // use servlet mapping (path mapping)
    //  -- with params
    @Test
    public void testString_getActionMappingURL_String_String_PageContext_boolean5() {
        pageContext.getServletContext().setAttribute(Globals.SERVLET_KEY,
            "/myapp/*");

        ActionConfig actionConfig = new ActionConfig();

        actionConfig.setParameter("/foo");
        moduleConfig.addActionConfig(actionConfig);

        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        request.setPathElements("/mycontext", "/baz?foo=bar", null, null);

        assertEquals("/mycontext/myapp/foo?foo=bar",
            tagutils.getActionMappingURL("/foo?foo=bar", pageContext),
            "Check path /foo");
    }

    // use servlet mapping (path mapping)
    //  -- using "/" as mapping
    @Test
    public void testString_getActionMappingURL_String_String_PageContext_boolean6() {
        pageContext.getServletContext().setAttribute(Globals.SERVLET_KEY, "/");

        ActionConfig actionConfig = new ActionConfig();

        actionConfig.setParameter("/foo");
        moduleConfig.addActionConfig(actionConfig);

        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        request.setPathElements("/mycontext", "/baz", null, null);

        assertEquals("/mycontext/",
            tagutils.getActionMappingURL("/", pageContext), "Check path /foo");
    }

    // ------------------------------------------ getActionMessages()
    // -- using ActionMessages
    @Test
    public void testActionMessages_getActionMessages_PageContext_String1() {
        ActionMessages actionMessages = new ActionMessages();

        actionMessages.add("prop", new ActionMessage("key.key"));
        request.setAttribute("messages", actionMessages);

        try {
            ActionMessages messages =
                tagutils.getActionMessages(pageContext, "messages");

            assertNotNull(messages, "messages should not be null");
            assertNotNull(messages.get("prop"),
                "messages prop should not be null");

            String val = null;
            int i = 0;

            for (Iterator<ActionMessage> iter = messages.get("prop"); iter.hasNext();) {
                ActionMessage message = iter.next();

                val = message.getKey();
                i++;
            }

            assertEquals(1, i, "only 1 message");
            assertEquals("key.key", val, "messages prop should match");
        } catch (JspException e) {
            fail(e.getMessage());
        }
    }

    // -- using ActionErrors
    @Test
    public void testActionMessages_getActionMessages_PageContext_String2() {
        ActionMessages actionMessages = new ActionMessages();

        actionMessages.add("prop", new ActionMessage("key.key"));
        request.setAttribute("messages", actionMessages);

        try {
            ActionMessages messages =
                tagutils.getActionMessages(pageContext, "messages");

            assertNotNull(messages, "messages should not be null");
            assertNotNull(messages.get("prop"),
                "messages prop should not be null");

            String val = null;
            int i = 0;

            for (Iterator<ActionMessage> iter = messages.get("prop"); iter.hasNext();) {
                ActionMessage message = iter.next();

                val = message.getKey();
                i++;
            }

            assertEquals(1, i, "only 1 message");
            assertEquals("key.key", val, "messages prop should match");
        } catch (JspException e) {
            fail(e.getMessage());
        }
    }

    // -- using String
    @Test
    public void testActionMessages_getActionMessages_PageContext_String3() {
        request.setAttribute("foo", "bar");

        try {
            ActionMessages messages =
                tagutils.getActionMessages(pageContext, "foo");

            assertNotNull(messages, "messages should not be null");
            assertNotNull(messages.get("prop"),
                "messages prop should not be null");

            String key = null;
            int i = 0;

            for (Iterator<ActionMessage> iter = messages.get(ActionMessages.GLOBAL_MESSAGE);
                iter.hasNext();) {
                ActionMessage message = iter.next();

                key = message.getKey();

                Object[] values = message.getValues();

                assertNull(values);
                i++;
            }

            assertEquals(1, i, "only 1 message");
            assertEquals("bar", key, "key should match");
        } catch (JspException e) {
            fail(e.getMessage());
        }
    }

    // -- using String Array
    @Test
    public void testActionMessages_getActionMessages_PageContext_String4() {
        String[] vals = new String[] { "bar", "baz" };

        request.setAttribute("foo", vals);

        try {
            ActionMessages messages =
                tagutils.getActionMessages(pageContext, "foo");

            assertNotNull(messages, "messages should not be null");
            assertNotNull(messages.get("prop"),
                "messages prop should not be null");

            String key = null;
            int i = 0;

            for (Iterator<ActionMessage> iter = messages.get(ActionMessages.GLOBAL_MESSAGE);
                iter.hasNext();) {
                ActionMessage message = iter.next();

                key = message.getKey();

                Object[] values = message.getValues();

                assertNull((values));
                assertEquals(vals[i], key, "1st key should match");
                i++;
            }

            assertEquals(2, i, "only 2 messages");
        } catch (JspException e) {
            fail(e.getMessage());
        }
    }

    // String Array (thrown JspException)
    @Test
    public void testActionMessages_getActionMessages_PageContext_String5() {
        request.setAttribute("foo", new MockFormBean());

        ActionMessages messages = null;

        try {
            messages = tagutils.getActionMessages(pageContext, "foo");
            fail("should have thrown JspException");
        } catch (JspException e) {
            assertNull(messages, "messages should be null");
        }
    }

    // ActionMessages (thrown Exception)
    // TODO -- currently this does not hit the line for caught Exception
    @Test
    public void testActionMessages_getActionMessages_PageContext_String6() {
        ActionMessages actionMessages = new ActionMessages();

        actionMessages.add("prop", new ActionMessage("key.key"));
        request.setAttribute("messages", actionMessages);

        try {
            ActionMessages messages =
                tagutils.getActionMessages(pageContext, "does-not-exist");

            assertNotNull(messages, "messages should not be null");
            assertNotNull(messages.get("prop"),
                "messages prop should not be null");

            for (Iterator<ActionMessage> iter = messages.get("prop"); iter.hasNext();) {
                fail("Should not have any messages for does-not-exist");
            }
        } catch (JspException e) {
            fail(e.getMessage());
        }
    }

    // ----public ModuleConfig getModuleConfig(PageContext pageContext)
    @Test
    public void testModuleConfig_getModuleConfig_PageContext() {
        MockServletConfig mockServletConfig = new MockServletConfig();
        ModuleConfig moduleConfig = new ModuleConfigImpl("");
        MockServletContext mockServletContext = new MockServletContext();
        MockHttpServletRequest mockHttpServletRequest =
            new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse =
            new MockHttpServletResponse();

        mockServletConfig.setServletContext(mockServletContext);

        MockPageContext mockPageContext =
            new MockPageContext(mockServletConfig, mockHttpServletRequest,
                mockHttpServletResponse);

        ModuleConfig foundModuleConfig = null;

        try {
            foundModuleConfig = tagutils.getModuleConfig(mockPageContext);
            fail("Expected ModuleConfig to not be found");
        } catch (NullPointerException ignore) {
            // expected result
        }

        mockHttpServletRequest.setAttribute(Globals.MODULE_KEY, moduleConfig);

        mockPageContext.getServletContext().setAttribute(Globals.MODULE_KEY,
            mockPageContext);

        foundModuleConfig = tagutils.getModuleConfig(mockPageContext);
        assertNotNull(foundModuleConfig);
    }

    // -- public Locale getUserLocale(PageContext pageContext, String locale)
    @Test
    public void testLocale_getUserLocale_PageContext_String() {
        request.setLocale(Locale.ENGLISH);
        assertEquals(Locale.ENGLISH, tagutils.getUserLocale(pageContext, ""));

        request.setLocale(Locale.CANADA);
        assertEquals(Locale.CANADA, tagutils.getUserLocale(pageContext, ""));
    }

    // -- public boolean isXhtml(PageContext pageContext)
    @Test
    public void test_boolean_isXhtml_PageContext() {
        assertFalse(tagutils.isXhtml(pageContext));
        pageContext.setAttribute(Globals.XHTML_KEY, "true");

        assertTrue(tagutils.isXhtml(pageContext));
    }

    // -- public Object lookup(PageContext pageContext, String name, String scopeName)
    // lookup with null scope
    @Test
    public void test_Object_lookup_PageContext_String__String1() {
        pageContext.setAttribute("bean", new MockFormBean());

        try {
            Object val = tagutils.lookup(pageContext, "bean", null);

            assertNotNull((val));
        } catch (JspException e) {
            fail("bean not found:" + e.getMessage());
        }
    }

    // lookup with page scope
    @Test
    public void test_Object_lookup_PageContext_String__String2() {
        pageContext.setAttribute("bean", new MockFormBean());

        try {
            Object val = tagutils.lookup(pageContext, "bean", "page");

            assertNotNull((val));
        } catch (JspException e) {
            fail("bean not found:" + e.getMessage());
        }
    }

    // lookup with invalid scope
    // -- (where an exception is thrown)
    @Test
    public void test_Object_lookup_PageContext_String__String3() {
        pageContext.setAttribute("bean", new MockFormBean());

        Object val = null;

        try {
            val = tagutils.lookup(pageContext, "bean", "invalid");
            fail("invalid scope :");
        } catch (JspException e) {
            assertNull((val));
        }
    }

    // try to get the call to throw an IllegalAccessException
    @Test
    public void test_Object_lookup_PageContext_String_String_String1() {
        //        page.setAttribute("bean", new MockFormBean());
        //        Object val = null;
        //        try {
        //            val = tagutils.lookup(page, "bean", "throwIllegalAccessException");
        //            fail("should have thrown exception");
        //        } catch (JspException e) {
        //            assertNull(val);
        //        }
    }

    // try to get the call to throw an IllegalArgumentException
    @Test
    public void test_Object_lookup_PageContext_String_String_String2() {
        pageContext.setAttribute("bean", new MockFormBean());

        Object val = null;

        try {
            val = tagutils.lookup(pageContext, "bean", "doesNotExistMethod",
                    "page");
            fail("should have thrown exception");
        } catch (JspException e) {
            assertNull(val);
        }
    }

    // try to get the call to throw an NoSuchMethodException
    @Test
    public void test_Object_lookup_PageContext_String_String_String3() {
        pageContext.setAttribute("bean", new MockFormBean());

        Object val = null;

        try {
            val = tagutils.lookup(pageContext, "bean", "doesNotExistMethod");
            fail("should have thrown exception");
        } catch (JspException e) {
            assertNull(val);
        }
    }

    /**
     * Testing message()
     *
     * public String message( PageContext pageContext, String bundle, String
     * locale, String key) throws JspException
     */
    @Test
    public void testMessageBadParams() {
        String val = null;

        try {
            val = tagutils.message(pageContext, "bundle", "locale", "key");
            fail("val should be null");
        } catch (JspException e) {
            assertNull(val);
        }
    }

    // set bundle in page scope
    // message() assumes the bundle will never be in page scope
    // -- bad key
    @Test
    @Disabled
    public void donttestMessagePageBadKey() {
        putBundleInScope(PageContext.PAGE_SCOPE, true);

        String val = null;

        try {
            val = tagutils.message(pageContext, null, null,
                    "foo.bar.does.not.exist");
            fail("val should be null");
        } catch (JspException e) {
            assertNull(val);
        }
    }

    // set bundle in request scope
    // -- bad key
    @Test
    public void testMessageRequestBadKey() {
        putBundleInScope(PageContext.REQUEST_SCOPE, true);

        String val = null;

        try {
            val = tagutils.message(pageContext, null, null,
                    "foo.bar.does.not.exist");
            assertNull(val);
        } catch (JspException e) {
            fail("val should be null, no exception");
        }
    }

    // set bundle in session scope
    // -- bad key
    //
    // This represents a use case where the user specifically set the bundle
    //  in session under Globals.MESSAGES_KEY.
    // Perhaps we should check session, and throw/log a rather explicit message
    // for why this is a bad idea, instead of ignoring or returning null.
    @Test
    public void testMessageSessionBadKey() {
        putBundleInScope(PageContext.SESSION_SCOPE, true);

        String val = null;

        try {
            val = tagutils.message(pageContext, null, null,
                    "foo.bar.does.not.exist");
            fail("MessageResources should never be put in session scope.");
        } catch (JspException e) {
            assertNull(val);
        }
    }

    // set bundle in application scope
    // -- bad key
    @Test
    public void testMessageApplicationBadKey() {
        putBundleInScope(PageContext.APPLICATION_SCOPE, true);

        String val = null;

        try {
            val = tagutils.message(pageContext, null, null,
                    "foo.bar.does.not.exist");
            assertNull(val);
        } catch (JspException e) {
            fail("val should be null, no exception");
        }
    }

    // set bundle in request scope
    // -- good key
    @Test
    public void testMessageRequestGoodKey() {
        putBundleInScope(PageContext.REQUEST_SCOPE, true);

        String val = null;

        try {
            val = tagutils.message(pageContext, null, null, "foo");
            assertEquals("bar", val, "Validate message value");
        } catch (JspException e) {
            fail("val should be \"bar\"");
        }
    }

    // set bundle in application scope
    // -- good key
    @Test
    public void testMessageApplicationGoodKey() {
        putBundleInScope(PageContext.APPLICATION_SCOPE, true);

        String val = null;

        try {
            val = tagutils.message(pageContext, null, null, "foo");
            assertEquals("bar", val, "Validate message value");
        } catch (JspException e) {
            fail("val should be \"bar\"");
        }
    }

    /**
     * Tests for:
     *
     * public String message( PageContext pageContext, String bundle, String
     * locale, String key, Object args[]) throws JspException
     */
    @Test
    public void testMessageRequestGoodKeyWithNullParams() {
        putBundleInScope(PageContext.REQUEST_SCOPE, true);

        String[] args = null;

        String val = null;

        try {
            val = tagutils.message(pageContext, null, null, "foo", args);
            assertEquals("bar", val, "Validate message value");
        } catch (JspException e) {
            fail("val should be \"bar\"");
        }
    }

    @Test
    public void testMessageApplicationGoodKeyWithNullParams() {
        putBundleInScope(PageContext.REQUEST_SCOPE, true);

        String[] args = null;

        String val = null;

        try {
            val = tagutils.message(pageContext, null, null, "foo", args);
            assertEquals("bar", val, "Validate message value");
        } catch (JspException e) {
            fail("val should be \"bar\"");
        }
    }

    @Test
    public void testMessageRequestGoodKeyWithParams() {
        putBundleInScope(PageContext.REQUEST_SCOPE, true);

        String[] args = { "I love this" };

        String val = null;

        try {
            val = tagutils.message(pageContext, null, null, "foo.bar", args);
            assertEquals("I love this bar", val, "Validate message value");
        } catch (JspException e) {
            fail("val should be \"bar\"");
        }
    }

    @Test
    public void testMessageApplicationGoodKeyWithParams() {
        putBundleInScope(PageContext.REQUEST_SCOPE, true);

        String[] args = { "I love this" };

        String val = null;

        try {
            val = tagutils.message(pageContext, null, null, "foo.bar", args);
            assertEquals("I love this bar", val, "Validate message value");
        } catch (JspException e) {
            fail("val should be \"bar\"");
        }
    }

    /**
     * Tests for: public boolean present( PageContext pageContext, String
     * bundle, String locale, String key) throws JspException {
     */
    @Test
    public void testPresentNulls() {
        boolean result = false;

        try {
            result = tagutils.present(null, null, null, null);
            fail("An exception should have been thrown");
        } catch (JspException e) {
            fail("An npe should have been thrown");
        } catch (NullPointerException e) {
            assertFalse(result, "Correct behaviour");
        }
    }

    @Test
    public void testPresentBadKey() {
        putBundleInScope(PageContext.REQUEST_SCOPE, true);

        boolean result = false;

        try {
            result =
                tagutils.present(pageContext, null, null, "foo.bar.not.exist");
            assertFalse(result, "Value should be false");
        } catch (JspException e) {
            fail("An npe should have been thrown");
        } catch (NullPointerException e) {
            assertFalse(result, "Correct behaviour");
        }
    }

    @Test
    public void testPresentGoodKey() {
        putBundleInScope(PageContext.REQUEST_SCOPE, true);

        boolean result = false;

        try {
            result = tagutils.present(pageContext, null, null, "foo");
            assertTrue(result, "Key should have been found");
        } catch (JspException e) {
            fail("An exception should have been thrown");
        }
    }

    /**
     * public void write(PageContext pageContext, String text) throws
     * JspException {
     */
    @Test
    public void testWriteNullParams() {
        try {
            tagutils.write(null, null);
            fail("NullPointerException should have been thrown");
        } catch (JspException e) {
            fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // pass
        }
    }

    @Test
    public void testWrite() {
        MockPageContext pg = new MockPageContext(false, false);

        try {
            tagutils.write(pg, null);
        } catch (JspException e) {
            fail("JspException should not have been thrown");
        }
    }

    @Test
    public void testWriteThrowException() {
        MockPageContext pg = new MockPageContext(true, false);

        try {
            tagutils.write(pg, null);
            fail("JspException should have been thrown");
        } catch (JspException e) {
            // success
        }
    }

    @Test
    public void testWritePrevious() {
        MockPageContext pg = new MockPageContext(false, false);

        try {
            tagutils.writePrevious(pg, null);
        } catch (JspException e) {
            fail("JspException should not have been thrown");
        }
    }

    @Test
    public void testWritePreviousThrowException() {
        MockPageContext pg = new MockPageContext(true, false);

        try {
            tagutils.writePrevious(pg, null);
            fail("JspException should have been thrown");
        } catch (JspException e) {
            // success
        }
    }

    @Test
    public void testWritePreviousBody() {
        MockPageContext pg = new MockPageContext(false, true);

        try {
            tagutils.writePrevious(pg, null);
        } catch (JspException e) {
            fail("JspException should not have been thrown");
        }
    }

    @Test
    public void testOverrideInstance(){

        class CustomTagUtils extends TagUtils{
            public String filter(String value) {
                return "I HAVE BEEN OVERRIDDEN!";
            }
        }
        // verify original logic
        assertNull(TagUtils.getInstance().filter(null), "Filter Test");

        // set the custom instance
        TagUtils.setInstance(new CustomTagUtils());
        assertEquals("I HAVE BEEN OVERRIDDEN!", TagUtils.getInstance().filter(null), "Custom Instance Test");

        // reset back to the cached instance
        TagUtils.setInstance(tagutils);
        assertNull(TagUtils.getInstance().filter(null), "Filter Test");

    }
}