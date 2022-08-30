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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Locale;

import org.apache.struts.util.MessageResources;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MessagesMap}.
 */
public class MessagesMapTestCase {


    // ------------------------------------------------------ Instance Variables


    /**
     * The {@code MessagesMap} instance to be tested.
     */
    protected MessagesMap map = null;


    /**
     * The {@code MessageResources} instance containing the messages
     * used for testing.
     */
    protected MessageResources resources = null;


    // ---------------------------------------------------- Overall Test Methods


    /**
     * Set up instance variables required by this test case.
     */
    @BeforeEach
    public void setUp() {

        resources =
            MessageResources.getMessageResources
            ("org.apache.struts.faces.util.Bundle");
        map = new MessagesMap(resources, Locale.getDefault());

    }


    /**
     * Tear down instance variables required by this test case.
     */
    @AfterEach
    public void tearDown() {

        map = null;
        resources = null;

    }


    // -------------------------------------------------- Individual Test Methods


    /**
     * Test the {@code containsKey()} method.
     */
    @Test
    public void testContainsKey() {

        // Positive tests
        assertTrue(map.containsKey("foo"));
        assertTrue(map.containsKey("bar"));
        assertTrue(map.containsKey("baz"));

        // Negative tests
        assertFalse(map.containsKey("bop"));

    }


    /**
     * Test the {@code get()} method.
     */
    @Test
    public void testGet() {

        // Positive tests
        assertEquals("This is foo", map.get("foo"));
        assertEquals("And this is bar", map.get("bar"));
        assertEquals("We also have baz", map.get("baz"));

        // Negative tests
        assertNull(map.get("bop"));

    }


    /**
     * Test a pristine instance, and all unsupported methods.
     */
    @Test
    public void testPristine() {

        // clear()
        assertThrows(UnsupportedOperationException.class,
            () -> map.clear(),
            "clear() should have thrown UnsupportedOperationException");

        // containsValue()
        assertThrows(UnsupportedOperationException.class,
            () -> map.containsValue("foo"),
            "containsValue() should have thrown UnsupportedOperationException");

        // entrySet()
        assertThrows(UnsupportedOperationException.class,
            () -> map.entrySet(),
            "entrySet() should have thrown UnsupportedOperationException");

        // keySet()
        assertThrows(UnsupportedOperationException.class,
            () -> map.keySet(),
            "keySet() should have thrown UnsupportedOperationException");

        // put()
        assertThrows(UnsupportedOperationException.class,
            () -> map.put("foo", "bar"),
            "put() should have thrown UnsupportedOperationException");

        // putAll()
        assertThrows(UnsupportedOperationException.class,
            () -> map.putAll(Collections.emptyMap()),
            "putAll() should have thrown UnsupportedOperationException");

        // remove()
        assertThrows(UnsupportedOperationException.class,
            () -> map.remove("foo"),
            "remove() should have thrown UnsupportedOperationException");

        // size()
        assertThrows(UnsupportedOperationException.class,
            () -> map.size(),
            "size() should have thrown UnsupportedOperationException");

        // values()
        assertThrows(UnsupportedOperationException.class,
            () -> map.values(),
            "values() should have thrown UnsupportedOperationException");

    }


}
