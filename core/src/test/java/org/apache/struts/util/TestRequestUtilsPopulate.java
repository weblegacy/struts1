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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashSet;

import jakarta.servlet.ServletException;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockFormBean;
import org.apache.struts.mock.MockMultipartRequestHandler;
import org.apache.struts.mock.TestMockBase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for the RequestUtil's {@code populate} method.
 *
 * @version $Rev$
 */
public class TestRequestUtilsPopulate extends TestMockBase {

    private final static String STRING_VALUE = "Test";

    protected static TestLoggerFactory logFactory;

    // ----------------------------------------------------- Setup and Teardown
    @BeforeAll
    public static void setUpAll() {
        logFactory = (TestLoggerFactory) LoggerFactory.getILoggerFactory();
    }

    @AfterAll
    public static void tearDownAll() {
        logFactory.release();
    }

    // ------------------------------------------------------- Individual Tests

    /**
     * Ensure that the getMultipartRequestHandler cannot be seen in
     * a subclass of ActionForm.
     *
     * The purpose of this test is to ensure that Bug #38534 is fixed.
     *
     */
    @Test
    public void testMultipartVisibility() {

        String mockMappingName = "mockMapping";
        String stringValue     = "Test";

        MockFormBean  mockForm = new MockFormBean();
        ActionMapping mapping  = new ActionMapping();
        mapping.setName(mockMappingName);

        // Set up the mock HttpServletRequest
        request.setMethod("POST");
        request.setContentType("multipart/form-data");
        request.setAttribute(Globals.MULTIPART_KEY, MockMultipartRequestHandler.class.getName());
        request.setAttribute(Globals.MAPPING_KEY, mapping);

        request.addParameter("stringProperty", stringValue);
        request.addParameter("multipartRequestHandler.mapping.name", "Bad");

        // Check the Mapping/ActionForm before
        assertNull(mockForm.getMultipartRequestHandler(), "Multipart Handler already set");
        assertEquals(mockMappingName, mapping.getName(),  "Mapping name not set correctly");

        // Try to populate
        try {
            RequestUtils.populate(mockForm, request);
        } catch(ServletException se) {
            // Expected BeanUtils.populate() to throw a NestedNullException
            // which gets wrapped in RequestUtils in a ServletException
            assertEquals("BeanUtils.populate", se.getMessage(), "Unexpected type of Exception thrown");
        }

        // Check the Mapping/ActionForm after
        assertNotNull(mockForm.getMultipartRequestHandler(), "Multipart Handler Missing");
        assertEquals(mockMappingName, mapping.getName(), "Mapping name has been modified");

    }

    /**
     * Ensure that the parameter of HTTP request
     * which causes ClassLoader manipulation is ignored.
     *
     * The purpose of this test is to ensure that security problem
     * CVE-2014-0114 is fixed.
     *
     */
    @Test
    public void testRequestParameterIgnore1() throws Exception {

        MockFormBean  mockForm = new MockFormBean();

        HashSet<String> ignoreSet = runRequestParameter(mockForm, "class.xxx.case1");

        // Check
        assertEquals(1, ignoreSet.size(), "ignore num no match");
        assertTrue(ignoreSet.contains("class.xxx.case1"), "not exists ignore parameter class.xxx.case1");
        assertNull(mockForm.getStringProperty(), "ActionForm property set");

    }

    /**
     * Ensure that the parameter of HTTP request
     * which causes ClassLoader manipulation is ignored.
     *
     * The purpose of this test is to ensure that security problem
     * CVE-2014-0114 is fixed.
     *
     */
    @Test
    public void testRequestParameterIgnore2() throws Exception {

        MockFormBean  mockForm = new MockFormBean();

        HashSet<String> ignoreSet = runRequestParameter(mockForm, "xxx.class.case2");

        // Check
        assertEquals(1, ignoreSet.size(), "ignore num no match");
        assertTrue(ignoreSet.contains("xxx.class.case2"), "not exists ignore parameter xxx.class.case2");
        assertNull(mockForm.getStringProperty(), "ActionForm property set");

    }

    /**
     * Ensure that the parameter of HTTP request
     * which causes ClassLoader manipulation is ignored.
     *
     * The purpose of this test is to ensure that security problem
     * CVE-2014-0114 is fixed.
     *
     */
    @Test
    public void testRequestParameterIgnore3() throws Exception {

        MockFormBean  mockForm = new MockFormBean();

        HashSet<String> ignoreSet = runRequestParameter(mockForm, "stringProperty");

        // Check
        assertEquals(0, ignoreSet.size(), "ignore num no match");
        assertFalse(ignoreSet.contains("stringProperty"), "exists ignore parameter stringProperty");
        assertEquals("Test", mockForm.getStringProperty(), "ActionForm property not equal");

    }

    /**
     * Ensure that the parameter of HTTP request
     * which causes ClassLoader manipulation is ignored.
     *
     * The purpose of this test is to ensure that security problem
     * CVE-2014-0114 is fixed.
     *
     */
    @Test
    public void testRequestParameterIgnore4() throws Exception {

        MockFormBean  mockForm = new MockFormBean();

        HashSet<String> ignoreSet = runRequestParameter(mockForm, "class.xxx.case4", "xxx.class.case4", "stringProperty");

        // Check
        assertEquals(2, ignoreSet.size(), "ignore num no match");
        assertTrue(ignoreSet.contains("class.xxx.case4"), "not exists ignore parameter class.xxx.case4");
        assertTrue(ignoreSet.contains("xxx.class.case4"), "not exists ignore parameter xxx.class.case4");
        assertEquals(STRING_VALUE, mockForm.getStringProperty(), "ActionForm property not equal");

    }

    private HashSet<String> runRequestParameter(MockFormBean mockForm, String... parameters) throws Exception {

       // Set up the mock HttpServletRequest
        request.setMethod("GET");
        request.setContentType("");

        for (String parameter : parameters) {
            request.addParameter(parameter, STRING_VALUE);
        }

        // Try to populate
        HashSet<String> ignoreSet = new HashSet<>();
        try {
            String loggerName = RequestUtils.class.getName();
            String keyword = "[TRACE] " + loggerName + " - ignore parameter: paramName=";

            // clear logger
            logFactory.clearOutput(loggerName);

            RequestUtils.populate(mockForm, request);

            String logString = logFactory.getOutput(loggerName);
            StringReader reader = new StringReader(logString);
            BufferedReader bufReader = new BufferedReader(reader);

            String line;
            while ((line = bufReader.readLine()) != null) {
                int pos = line.indexOf(keyword);
                if (pos >= 0) {
                    ignoreSet.add(line.substring(pos + keyword.length()));
                }
            }
        } catch(ServletException se) {
            fail("Exception occurs: ", se);
        }

        return ignoreSet;
    }
}