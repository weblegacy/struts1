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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link ActionMessage} class.
 *
 * @version $Rev$ $Date$
 */
public class TestActionMessage {
    protected ActionMessage amWithNoValue = null;
    protected ActionMessage amWithOneValue = null;
    protected ActionMessage amWithTwoValues = null;
    protected ActionMessage amWithThreeValues = null;
    protected ActionMessage amWithFourValues = null;
    protected ActionMessage amWithArrayValues = null;
    protected ActionMessage amWithTwoIntegerValues = null;
    protected ActionMessage amNoResource = null;
    protected Object[] test_values =
        new Object[] {
            "stringValue1", "stringValue2", "stringValue3", "stringValue4"
        };

    @BeforeEach
    public void setUp() {
        amWithNoValue = new ActionMessage("amWithNoValue");
        amWithOneValue =
            new ActionMessage("amWithOneValue", new String("stringValue"));
        amWithTwoValues =
            new ActionMessage("amWithTwoValues", new String("stringValue1"),
                new String("stringValue2"));
        amWithThreeValues =
            new ActionMessage("amWithThreeValues", new String("stringValue1"),
                new String("stringValue2"), new String("stringValue3"));
        amWithFourValues =
            new ActionMessage("amWithFourValues", new String("stringValue1"),
                new String("stringValue2"), new String("stringValue3"),
                new String("stringValue4"));
        amWithArrayValues = new ActionMessage("amWithArrayValues", test_values);
        amWithTwoIntegerValues =
            new ActionMessage("amWithTwoIntegerValues", Integer.valueOf(5),
                Integer.valueOf(10));
        amNoResource = new ActionMessage("amNoResource", false);
    }

    @AfterEach
    public void tearDown() {
        amWithNoValue = null;
        amWithOneValue = null;
        amWithTwoValues = null;
        amWithThreeValues = null;
        amWithFourValues = null;
        amWithArrayValues = null;
        amWithTwoIntegerValues = null;
        amNoResource = null;
    }

    @Test
    public void testActionMessageWithNoValue() {
        assertNull(amWithNoValue.getValues());
        assertTrue(amWithNoValue.isResource());
        assertEquals("amWithNoValue", amWithNoValue.getKey());
        assertEquals("amWithNoValue[]", amWithNoValue.toString());
    }

    @Test
    public void testActionMessageWithAStringValue() {
        Object[] values = amWithOneValue.getValues();

        assertNotNull(values);
        assertEquals(1, values.length);
        assertEquals("stringValue", values[0]);
        assertTrue(amWithOneValue.isResource());
        assertEquals("amWithOneValue", amWithOneValue.getKey());
        assertEquals("amWithOneValue[stringValue]", amWithOneValue.toString());
    }

    @Test
    public void testActionMessageWithTwoValues() {
        Object[] values = amWithTwoValues.getValues();

        assertNotNull(values != null);
        assertEquals(2, values.length);
        assertEquals("stringValue1", values[0]);
        assertEquals("stringValue2", values[1]);
        assertTrue(amWithTwoValues.isResource());
        assertEquals("amWithTwoValues", amWithTwoValues.getKey());
        assertEquals("amWithTwoValues[stringValue1, stringValue2]", amWithTwoValues.toString());
    }

    @Test
    public void testActionMessageWithThreeValues() {
        Object[] values = amWithThreeValues.getValues();

        assertNotNull(values);
        assertEquals(3, values.length);
        assertEquals("stringValue1", values[0]);
        assertEquals("stringValue2", values[1]);
        assertEquals("stringValue3", values[2]);
        assertEquals("amWithThreeValues", amWithThreeValues.getKey());
        assertTrue(amWithThreeValues.isResource());
        assertEquals("amWithThreeValues[stringValue1, stringValue2, stringValue3]", amWithThreeValues.toString());
    }

    @Test
    public void testActionMessageWithFourValues() {
        Object[] values = amWithFourValues.getValues();

        assertNotNull(values);
        assertEquals(4, values.length);
        assertEquals("stringValue1", values[0]);
        assertEquals("stringValue2", values[1]);
        assertEquals("stringValue3", values[2]);
        assertEquals("stringValue4", values[3]);
        assertTrue(amWithFourValues.isResource());
        assertEquals("amWithFourValues", amWithFourValues.getKey());
        assertEquals("amWithFourValues[stringValue1, stringValue2, stringValue3, stringValue4]", amWithFourValues.toString());
    }

    @Test
    public void testActionMessageWithArrayValues() {
        Object[] values = amWithArrayValues.getValues();

        assertNotNull(values);
        assertEquals(test_values.length, values.length);

        assertArrayEquals(test_values, values);

        assertTrue(amWithArrayValues.isResource());
        assertEquals("amWithArrayValues", amWithArrayValues.getKey());
        assertEquals("amWithArrayValues[stringValue1, stringValue2, stringValue3, stringValue4]", amWithArrayValues.toString());
    }

    @Test
    public void testActionWithTwoIntegers() {
        Object[] values = amWithTwoIntegerValues.getValues();

        assertNotNull(values);
        assertEquals(2, values.length);
        assertInstanceOf(Integer.class, values[0]);
        assertEquals(5, values[0]);
        assertInstanceOf(Integer.class, values[1]);
        assertEquals(10, values[1]);
        assertTrue(amWithTwoIntegerValues.isResource());
        assertEquals("amWithTwoIntegerValues", amWithTwoIntegerValues.getKey());
        assertEquals("amWithTwoIntegerValues[5, 10]", amWithTwoIntegerValues.toString());
    }

    @Test
    public void testActionNoResource() {
        assertNull(amNoResource.getValues());
        assertFalse(amNoResource.isResource());
        assertEquals("amNoResource", amNoResource.getKey());
        assertEquals("amNoResource[]", amNoResource.toString());
    }
}
