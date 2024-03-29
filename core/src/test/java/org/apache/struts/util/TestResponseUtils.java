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

import org.apache.struts.mock.TestMockBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ResponseUtils}.
 *
 * @version $Rev$ $Date$
 */
public class TestResponseUtils extends TestMockBase {
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
    // ---------------------------------------------------------- filter()
    @Test
    public void testFilter() {
        assertEquals("123&amp;456", ResponseUtils.filter("123&456"));
        assertEquals("123&amp;456;", ResponseUtils.filter("123&456;"));
        assertEquals("123&amp;456", ResponseUtils.filter("123&amp;456"));
        assertEquals("123&#123;456", ResponseUtils.filter("123&#123;456"));
        assertEquals("123&amp;#12a;456", ResponseUtils.filter("123&#12a;456"));
        assertEquals("123&#x12a;456", ResponseUtils.filter("123&#x12a;456"));
        assertEquals("123&amp;#x12ah;456", ResponseUtils.filter("123&#x12ah;456"));
        assertEquals("123&lt;&gt;&quot;&#39;456", ResponseUtils.filter("123<>\"'456"));
        assertEquals("123&Uuml;456;", ResponseUtils.filter("123&Uuml;456;"));
        assertEquals("123&x;456", ResponseUtils.filter("123&x;456"));
        assertEquals("123&amp;1;456", ResponseUtils.filter("123&1;456"));
        assertEquals("&amp;456&Uuml;4", ResponseUtils.filter("&amp;456&Uuml;4"));
        assertEquals("&amp;45&amp;6&Uuml;4&#39;", ResponseUtils.filter("&amp;45&6&Uuml;4'"));
   }
}
