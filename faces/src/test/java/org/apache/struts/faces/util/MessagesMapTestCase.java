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

package org.apache.struts.faces.util;


import java.util.Collections;
import java.util.Locale;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.struts.util.MessageResources;


/**
 * <p>Unit tests for <code>MessagesMap</code>.</p>
 */

public class MessagesMapTestCase extends TestCase {


    // ------------------------------------------------------ Instance Variables


    /**
     * <p>The <code>MessagesMap</code> instance to be tested.</p>
     */
    protected MessagesMap map = null;


    /**
     * <p>The <code>MessageResources</code> instance containing the messages
     * used for testing.</p>
     */
    protected MessageResources resources = null;


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Construct a new instance of this test case.</p>
     *
     * @param name Name of the test case
     */
    public MessagesMapTestCase(String name) {

        super(name);

    }


    // ---------------------------------------------------- Overall Test Methods


    /**
     * <p>Set up instance variables required by this test case.</p>
     */
    public void setUp() throws Exception {

        resources =
            MessageResources.getMessageResources
            ("org.apache.struts.faces.util.Bundle");
        map = new MessagesMap(resources, Locale.getDefault());

    }


    /**
     * <p>Return the tests included in this test suite.</p>
     */
    public static Test suite() {

        return new TestSuite(MessagesMapTestCase.class);

    }


    /**
     * <p>Tear down instance variables required by this test case.</p>
     */
    public void teaDown() throws Exception {

        map = null;
        resources = null;

    }


    // -------------------------------------------------- Individal Test Methods


    /**
     * <p>Test the <code>containsKey()</code> method.</p>
     */
    public void testContainsKey() throws Exception {

        // Positive tests
        assertTrue(map.containsKey("foo"));
        assertTrue(map.containsKey("bar"));
        assertTrue(map.containsKey("baz"));

        // Negative tests
        assertTrue(!map.containsKey("bop"));

    }


    /**
     * <p>Test the <code>get()</code> method.</p>
     */
    public void testGet() throws Exception {

        // Positive tests
        assertEquals("This is foo", (String) map.get("foo"));
        assertEquals("And this is bar", (String) map.get("bar"));
        assertEquals("We also have baz", (String) map.get("baz"));

        // Negative tests
        assertNull(map.get("bop"));

    }


    /**
     * <p>Test a pristine instance, and all unsupported methods.</p>
     */
    public void testPristine() throws Exception {

        // clear()
        try {
            map.clear();
            fail("clear() should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            ; // Expected result
        }

        // containsValue()
        try {
            map.containsValue("foo");
            fail("containsValue() should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            ; // Expected result
        }

        // entrySet()
        try {
            map.entrySet();
            fail("entrySet() should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            ; // Expected result
        }

        // keySet()
        try {
            map.keySet();
            fail("keySet() should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            ; // Expected result
        }

        // put()
        try {
            map.put("foo", "bar");
            fail("put() should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            ; // Expected result
        }

        // putAll()
        try {
            map.putAll(Collections.EMPTY_MAP);
            fail("putAll() should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            ; // Expected result
        }

        // remove()
        try {
            map.remove("foo");
            fail("remove() should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            ; // Expected result
        }

        // size()
        try {
            map.size();
            fail("size() should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            ; // Expected result
        }

        // size()
        try {
            map.values();
            fail("values() should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            ; // Expected result
        }

    }


}
