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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.servlet.ServletException;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockFormBean;
import org.apache.struts.mock.MockMultipartRequestHandler;
import org.apache.struts.mock.TestMockBase;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the RequestUtil's {@code populate} method.
 *
 * @version $Rev$
 */
public class TestRequestUtilsPopulate extends TestMockBase {

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

}
