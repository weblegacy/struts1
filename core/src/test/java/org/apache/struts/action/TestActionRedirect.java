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
package org.apache.struts.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link ActionRedirect} class.
 *
 * @version $Rev$ $Date$
 */
public class TestActionRedirect {

    // ----------------------------------------------------- Test Methods

    /**
     * Check that the redirect flag is set.
     */
    @Test
    public void testActionRedirectRedirectFlag() {
        ActionRedirect ar = new ActionRedirect("/path.do");

        assertTrue(ar.getRedirect(), "Redirect flag should be set to true.");
    }

    /**
     * Test all addParameter methods accepting different data types.
     */
    @Test
    public void testActionRedirectAddParameter() {
        ActionRedirect ar = new ActionRedirect("/path.do");

        ar.addParameter("st", "test");
        ar.addParameter("obj", new StringBuilder("someString"));

        assertEquals(0, ar.getPath().indexOf("/path.do"), "Incorrect path");
        assertHasParameter(ar.parameterValues, "st", "test");
        assertHasParameter(ar.parameterValues, "obj", "someString");
    }

    /**
     * Test redirect with anchor.
     */
    @Test
    public void testActionRedirectWithAnchor() {
        ActionRedirect ar = new ActionRedirect("/path.do");

        ar.addParameter("st", "test");
        ar.setAnchor("foo");

        assertEquals(ar.getPath(), "/path.do?st=test#foo", "Incorrect path");
    }

    /**
     * Test adding parameters with the same name.
     */
    @Test
    public void testActionRedirectAddSameNameParameter() {
        ActionRedirect ar = new ActionRedirect("/path.do");

        ar.addParameter("param", "param1");
        ar.addParameter("param", "param2");
        ar.addParameter("param", new StringBuilder("someString"));

        assertEquals(0, ar.getPath().indexOf("/path.do"), "Incorrect path");
        assertHasParameter(ar.parameterValues, "param", "param1");
        assertHasParameter(ar.parameterValues, "param", "param2");
        assertHasParameter(ar.parameterValues, "param", "someString");
        assertEquals(3, countParameters(ar.parameterValues, "param"),
            "Incorrect number of parameters");
    }

    /**
     * Test creating an ActionRedirect which copies its configuration from an
     * existing ActionForward (except for the "redirect" property).
     */
    @Test
    public void testActionRedirectFromExistingForward() {
        ActionForward forward = new ActionForward("/path.do?param=param1");
        forward.setRedirect(false);
        forward.setProperty("key","value");

        ActionRedirect ar = new ActionRedirect(forward);

        ar.addParameter("param", "param2");
        ar.addParameter("object1", new StringBuilder("someString"));

        assertEquals(0, ar.getPath().indexOf("/path.do"), "Incorrect path");
        assertHasParameter(ar.parameterValues, "param", "param2");
        assertHasParameter(ar.parameterValues, "object1", "someString");
        assertEquals(forward.getPath(), ar.getOriginalPath(),
            "Incorrect original path.");
        assertEquals("value", ar.getProperty("key"),
            "Incorrect or missing property");
        assertTrue(ar.getRedirect(), "Original had redirect to false");
    }

    /**
     * Assert that the given parameters contains an entry for
     * <code>paramValue</code> under the <code>paramName</code> key. <p/>
     *
     * @param parameters the map of parameters to check into
     * @param paramName  the key of the value to be checked
     * @param paramValue the value to check for
     */
    static void assertHasParameter(Map<String, ?> parameters, String paramName,
        String paramValue) {
        Object value = parameters.get(paramName);

        if (value == null) {
            fail("Parameter [" + paramName + "] not found");
        }

        if (value instanceof String) {
            assertEquals(paramValue, (String) value, "Incorrect value found");
        } else if (value instanceof String[]) {
            // see if our value is among those in the array
            String[] values = (String[]) value;

            for (int i = 0; i < values.length; i++) {
                if (paramValue.equals(values[i])) {
                    return;
                }
            }

            fail("Expected value not found for parameter [" + paramName + "]");
        } else {
            // can't recognize the value
            fail("Unexpected type found as parameter value for [" + paramName + "]");
        }
    }

    /**
     * Determine the number of values that are available for a specific
     * parameter. <p/>
     *
     * @param parameters the map of parameters to check into
     * @param paramName  the key of the value(s) to count
     * @return the number of values for the specified parameter
     */
    static int countParameters(Map<String, ?> parameters, String paramName) {
        Object value = parameters.get(paramName);

        if (value == null) {
            return 0;
        }

        if (value instanceof String) {
            return 1;
        } else if (value instanceof String[]) {
            String[] values = (String[]) value;

            return values.length;
        } else {
            // can't recognize the value
            fail("Unexpected type found as parameter value for [" + paramName + "]");

            // Dead code
            return -1;
        }
    }
}
