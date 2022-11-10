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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;
import org.apache.struts.mock.MockHttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Suite of unit tests for the {@link DynaActionForm} class.
 */
public class TestDynaActionForm extends TestDynaActionFormClass {
    /**
     * The set of property names we expect to have returned when calling
     * <code>getDynaProperties()</code>.  You should update this list when new
     * properties are added to TestBean.
     */
    protected final static String[] properties =
        {
            "booleanProperty", "booleanSecond", "doubleProperty",
            "floatProperty", "intArray", "intIndexed", "intProperty",
            "listIndexed", "longProperty", "mappedProperty", "mappedIntProperty",


            //        "nullProperty",
            "shortProperty", "stringArray", "stringIndexed", "stringProperty",
        };

    // ----------------------------------------------------- Instance Variables

    /**
     * Dummy ModuleConfig for calls to reset() and validate().
     */
    protected ModuleConfig moduleConfig = null;

    /**
     * The basic <code>DynaActionForm</code> to use for testing.
     */
    protected DynaActionForm dynaForm = null;

    /**
     * Dummy ActionMapping for calls to reset() and validate().
     */
    protected ActionMapping mapping = null;

    // ----------------------------------------------------- Setup and Teardown
    @BeforeEach
    public void setUp() {
        super.setUp();

        try {
            dynaForm = (DynaActionForm) dynaClass.newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        } catch (InstantiationException e) {
            throw new RuntimeException(e.getMessage());
        }

        setupComplexProperties();
        moduleConfig = new DynaActionFormConfig(beanConfig);
        mapping = new DynaActionFormMapping(moduleConfig);
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
        moduleConfig = null;
        dynaForm = null;
        mapping = null;
    }

    // --------------------------------------------- Create New DynaActionForms
    // Test basic form bean properties on creation
    @Test
    public void testBeanCreate() {
        assertEquals(Boolean.TRUE, dynaForm.get("booleanProperty"),
            "booleanProperty");
        assertEquals(Boolean.TRUE, dynaForm.get("booleanSecond"),
            "booleanSecond");
        assertEquals(Double.valueOf(321.0), dynaForm.get("doubleProperty"),
            "doubleProperty");
        assertEquals(Float.valueOf((float) 123.0), dynaForm.get("floatProperty"),
            "floatProperty");
        assertEquals(Integer.valueOf(123), dynaForm.get("intProperty"),
            "intProperty");

        // FIXME - listIndexed
        assertEquals(Long.valueOf(321), dynaForm.get("longProperty"),
            "longProperty");

        // FIXME - mappedProperty
        // FIXME - mappedIntProperty
        //        assertNull(dynaForm.get("nullProperty"),
        //                     "nullProperty");
        assertEquals(Short.valueOf((short) 987), dynaForm.get("shortProperty"),
            "shortProperty");
        assertEquals("This is a string", dynaForm.get("stringProperty"),
            "stringProperty");
    }

    // Test initialize() method on indexed values to ensure that the
    // result returned by FormPropertyConfig().initial() is never clobbered
    @Test
    public void testIndexedInitialize() {
        // Update some values in the indexed properties
        dynaForm.set("intArray", 1, Integer.valueOf(111));
        assertEquals(Integer.valueOf(111), dynaForm.get("intArray", 1),
            "intArray[1]");
        dynaForm.set("intIndexed", 2, Integer.valueOf(222));
        assertEquals(Integer.valueOf(222), dynaForm.get("intIndexed", 2),
            "intIndexed[2]");
        dynaForm.set("stringArray", 3, "New String 3");
        assertEquals("New String 3", dynaForm.get("stringArray", 3),
            "stringArray[3]");
        dynaForm.set("stringIndexed", 4, "New String 4");
        assertEquals("New String 4", dynaForm.get("stringIndexed", 4),
            "stringIndexed[4]");

        // Perform initialize() and revalidate the original values
        // while ensuring our initial values did not get corrupted
        dynaForm.initialize(mapping);
        setupComplexProperties();
        testGetIndexedValues();
    }

    // Test initialize() method going back to initial values
    @Test
    public void testScalarInitialize() {
        // Update a bunch of scalar properties to new values
        dynaForm.set("booleanProperty", Boolean.FALSE);
        assertEquals(Boolean.FALSE, dynaForm.get("booleanProperty"),
            "booleanProperty");
        dynaForm.set("booleanSecond", Boolean.FALSE);
        dynaForm.set("doubleProperty", Double.valueOf(654.0));
        dynaForm.set("floatProperty", Float.valueOf((float) 543.0));
        dynaForm.set("intProperty", Integer.valueOf(555));
        dynaForm.set("longProperty", Long.valueOf(777));
        dynaForm.set("shortProperty", Short.valueOf((short) 222));
        dynaForm.set("stringProperty", "New String Value");
        assertEquals("New String Value", dynaForm.get("stringProperty"),
            "stringProperty");

        // Perform initialize() and revalidate the original values
        dynaForm.initialize(mapping);
        setupComplexProperties();
        testBeanCreate();
    }

    // --------------------------------------- Tests from BasicDynaBeanTestCase

    /**
     * Corner cases on getDynaProperty invalid arguments.
     */
    @Test
    public void testGetDescriptorArguments() {
        DynaProperty descriptor =
            dynaForm.getDynaClass().getDynaProperty("unknown");

        assertNull(descriptor, "Unknown property descriptor should be null");

        try {
            dynaForm.getDynaClass().getDynaProperty(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            ; // Expected response
        }
    }

    /**
     * Positive getDynaProperty on property <code>booleanProperty</code>.
     */
    @Test
    public void testGetDescriptorBoolean() {
        testGetDescriptorBase("booleanProperty", Boolean.TYPE);
    }

    /**
     * Positive getDynaProperty on property <code>doubleProperty</code>.
     */
    @Test
    public void testGetDescriptorDouble() {
        testGetDescriptorBase("doubleProperty", Double.TYPE);
    }

    /**
     * Positive getDynaProperty on property <code>floatProperty</code>.
     */
    @Test
    public void testGetDescriptorFloat() {
        testGetDescriptorBase("floatProperty", Float.TYPE);
    }

    /**
     * Positive getDynaProperty on property <code>intProperty</code>.
     */
    @Test
    public void testGetDescriptorInt() {
        testGetDescriptorBase("intProperty", Integer.TYPE);
    }

    /**
     * Positive getDynaProperty on property <code>longProperty</code>.
     */
    @Test
    public void testGetDescriptorLong() {
        testGetDescriptorBase("longProperty", Long.TYPE);
    }

    /**
     * Positive getDynaProperty on property <code>booleanSecond</code> that
     * uses an "is" method as the getter.
     */
    @Test
    public void testGetDescriptorSecond() {
        testGetDescriptorBase("booleanSecond", Boolean.TYPE);
    }

    /**
     * Positive getDynaProperty on property <code>shortProperty</code>.
     */
    @Test
    public void testGetDescriptorShort() {
        testGetDescriptorBase("shortProperty", Short.TYPE);
    }

    /**
     * Positive getDynaProperty on property <code>stringProperty</code>.
     */
    @Test
    public void testGetDescriptorString() {
        testGetDescriptorBase("stringProperty", String.class);
    }

    /**
     * Positive test for getDynaPropertys().  Each property name listed in
     * <code>properties</code> should be returned exactly once.
     */
    @Test
    public void testGetDescriptors() {
        DynaProperty[] pd = dynaForm.getDynaClass().getDynaProperties();

        assertNotNull(pd, "Got descriptors");

        int[] count = new int[properties.length];

        for (int i = 0; i < pd.length; i++) {
            String name = pd[i].getName();

            for (int j = 0; j < properties.length; j++) {
                if (name.equals(properties[j])) {
                    count[j]++;
                }
            }
        }

        for (int j = 0; j < properties.length; j++) {
            if (count[j] < 0) {
                fail("Missing property " + properties[j]);
            } else if (count[j] > 1) {
                fail("Duplicate property " + properties[j]);
            }
        }
    }

    /**
     * Corner cases on getIndexedProperty invalid arguments.
     */
    @Test
    public void testGetIndexedArguments() {
        try {
            dynaForm.get("intArray", -1);
            fail("Should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            ; // Expected response
        }
    }

    /**
     * Positive and negative tests on getIndexedProperty valid arguments.
     */
    @Test
    public void testGetIndexedValues() {
        Object value = null;

        for (int i = 0; i < 5; i++) {
            value = dynaForm.get("intArray", i);
            assertNotNull(value, "intArray returned value " + i);
            assertInstanceOf(Integer.class, value,
                "intArray returned Integer " + i);
            assertEquals(i * 10, (Integer) value,
                "intArray returned correct " + i);

            value = dynaForm.get("intIndexed", i);
            assertNotNull(value, "intIndexed returned value " + i);
            assertInstanceOf(Integer.class, value,
                "intIndexed returned Integer " + i);
            assertEquals(i * 100, (Integer) value,
                "intIndexed returned correct " + i);

            value = dynaForm.get("listIndexed", i);
            assertNotNull(value, "listIndexed returned value " + i);
            assertInstanceOf(String.class, value, "list returned String " + i);
            assertEquals("String " + i, value,
                "listIndexed returned correct " + i);

            value = dynaForm.get("stringArray", i);
            assertNotNull(value, "stringArray returned value " + i);
            assertInstanceOf(String.class, value,
                "stringArray returned String " + i);
            assertEquals("String " + i, value,
                "stringArray returned correct " + i);

            value = dynaForm.get("stringIndexed", i);
            assertNotNull(value, "stringIndexed returned value " + i);
            assertInstanceOf(String.class, value,
                "stringIndexed returned String " + i);
            assertEquals("String " + i, value,
                "stringIndexed returned correct " + i);
        }
    }

    /**
     * Corner cases on getMappedProperty invalid arguments.
     */
    @Test
    public void testGetMappedArguments() {
        Object value = dynaForm.get("mappedProperty", "unknown");

        assertNull(value, "Should not return a value");
    }

    /**
     * Positive and negative tests on getMappedProperty valid arguments.
     */
    @Test
    public void testGetMappedValues() {
        Object value = null;

        value = dynaForm.get("mappedProperty", "First Key");
        assertEquals("First Value", value, "Can find first value");

        value = dynaForm.get("mappedProperty", "Second Key");
        assertEquals("Second Value", value, "Can find second value");

        value = dynaForm.get("mappedProperty", "Third Key");
        assertNull(value, "Can not find third value");
    }

    /**
     * Corner cases on getSimpleProperty invalid arguments.
     */
    @Test
    public void testGetSimpleArguments() {
        try {
            dynaForm.get(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            ; // Expected response
        }
    }

    /**
     * Test getSimpleProperty on a boolean property.
     */
    @Test
    public void testGetSimpleBoolean() {
        Object value = dynaForm.get("booleanProperty");

        assertNotNull(value, "Got a value");
        assertInstanceOf(Boolean.class, value, "Got correct type");
        assertTrue((Boolean) value, "Got correct value");
    }

    /**
     * Test getSimpleProperty on a double property.
     */
    @Test
    public void testGetSimpleDouble() {
        Object value = dynaForm.get("doubleProperty");

        assertNotNull(value, "Got a value");
        assertInstanceOf(Double.class, value, "Got correct type");
        assertEquals(321.0,
            (Double) value, 0.005, "Got correct value");
    }

    /**
     * Test getSimpleProperty on a float property.
     */
    @Test
    public void testGetSimpleFloat() {
        Object value = dynaForm.get("floatProperty");

        assertNotNull(value, "Got a value");
        assertInstanceOf(Float.class, value, "Got correct type");
        assertEquals((float) 123.0,
            (Float) value,(float) 0.005, "Got correct value");
    }

    /**
     * Test getSimpleProperty on a int property.
     */
    @Test
    public void testGetSimpleInt() {
        Object value = dynaForm.get("intProperty");

        assertNotNull(value, "Got a value");
        assertInstanceOf(Integer.class, value, "Got correct type");
        assertEquals(123, (Integer) value, "Got correct value");
    }

    /**
     * Test getSimpleProperty on a long property.
     */
    @Test
    public void testGetSimpleLong() {
        Object value = dynaForm.get("longProperty");

        assertNotNull(value, "Got a value");
        assertInstanceOf(Long.class, value, "Got correct type");
        assertEquals((long) 321, (Long) value, "Got correct value");
    }

    /**
     * Test getSimpleProperty on a short property.
     */
    @Test
    public void testGetSimpleShort() {
        Object value = dynaForm.get("shortProperty");

        assertNotNull(value, "Got a value");
        assertInstanceOf(Short.class, value, "Got correct type");
        assertEquals((short) 987, (Short) value, "Got correct value");
    }

    /**
     * Test getSimpleProperty on a String property.
     */
    @Test
    public void testGetSimpleString() {
        Object value = dynaForm.get("stringProperty");

        assertNotNull(value, "Got a value");
        assertInstanceOf(String.class, "Got correct type");
        assertEquals("This is a string", value, "Got correct value");
    }

    /**
     * Test <code>contains()</code> method for mapped properties.
     */
    @Test
    public void testMappedContains() {
        assertTrue(dynaForm.contains("mappedProperty", "First Key"),
            "Can see first key");

        assertFalse(dynaForm.contains("mappedProperty", "Unknown Key"),
            "Can not see unknown key");
    }

    /**
     * Test <code>remove()</code> method for mapped properties.
     */
    @Test
    public void testMappedRemove() {
        assertTrue(dynaForm.contains("mappedProperty", "First Key"),
            "Can see first key");
        dynaForm.remove("mappedProperty", "First Key");
        assertFalse(dynaForm.contains("mappedProperty", "First Key"),
            "Can not see first key");

        assertFalse(dynaForm.contains("mappedProperty", "Unknown Key"),
            "Can not see unknown key");
        dynaForm.remove("mappedProperty", "Unknown Key");
        assertFalse(dynaForm.contains("mappedProperty", "Unknown Key"),
            "Can not see unknown key");
    }

    /**
     * Test the reset method when the request method is GET.
     */
    @Test
    public void testResetGet() {
        // set a choice set of props with non-initial values
        dynaForm.set("booleanProperty", Boolean.FALSE);
        dynaForm.set("booleanSecond", Boolean.FALSE);
        dynaForm.set("doubleProperty", Double.valueOf(456.0));
        dynaForm.set("floatProperty", Float.valueOf((float) 456.0));
        dynaForm.set("intProperty", Integer.valueOf(456));

        MockHttpServletRequest request = new MockHttpServletRequest();

        request.setMethod("GET");
        dynaForm.reset(mapping, request);

        assertEquals(Boolean.TRUE, dynaForm.get("booleanProperty"),
            "booleanProperty should be reset");
        assertEquals(Boolean.TRUE, dynaForm.get("booleanSecond"),
            "booleanSecond should be reset");
        assertEquals(Double.valueOf(321.0), dynaForm.get("doubleProperty"),
            "doubleProperty should be reset");
        assertEquals(Float.valueOf((float) 456.0), dynaForm.get("floatProperty"),
            "floatProperty should NOT be reset");
        assertEquals(Integer.valueOf(456), dynaForm.get("intProperty"),
            "intProperty should NOT be reset");
    }

    /**
     * Test the reset method when the request method is GET.
     */
    @Test
    public void testResetPost() {
        // set a choice set of props with non-initial values
        dynaForm.set("booleanProperty", Boolean.FALSE);
        dynaForm.set("booleanSecond", Boolean.FALSE);
        dynaForm.set("doubleProperty", Double.valueOf(456.0));
        dynaForm.set("floatProperty", Float.valueOf((float) 456.0));
        dynaForm.set("intProperty", Integer.valueOf(456));

        MockHttpServletRequest request = new MockHttpServletRequest();

        request.setMethod("POST");
        dynaForm.reset(mapping, request);

        assertEquals(Boolean.TRUE, dynaForm.get("booleanProperty"),
            "booleanProperty should be reset");
        assertEquals(Boolean.TRUE, dynaForm.get("booleanSecond"),
            "booleanSecond should be reset");
        assertEquals(Double.valueOf(456), dynaForm.get("doubleProperty"),
            "doubleProperty should NOT be reset");
        assertEquals(Float.valueOf((float) 123.0), dynaForm.get("floatProperty"),
            "floatProperty should be reset");
        assertEquals(Integer.valueOf(456), dynaForm.get("intProperty"),
            "intProperty should NOT be reset");
    }

    /**
     * Corner cases on setIndexedProperty invalid arguments.
     */
    @Test
    public void testSetIndexedArguments() {
        try {
            dynaForm.set("intArray", -1, Integer.valueOf(0));
            fail("Should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            ; // Expected response
        }
    }

    /**
     * Positive and negative tests on setIndexedProperty valid arguments.
     */
    @Test
    public void testSetIndexedValues() {
        Object value = null;

        dynaForm.set("intArray", 0, Integer.valueOf(1));
        value = dynaForm.get("intArray", 0);
        assertNotNull(value, "Returned new value 0");
        assertInstanceOf(Integer.class, value, "Returned Integer new value 0");
        assertEquals(1, (Integer) value,
            "Returned correct new value 0");

        dynaForm.set("intIndexed", 1, Integer.valueOf(11));
        value = dynaForm.get("intIndexed", 1);
        assertNotNull(value, "Returned new value 1");
        assertInstanceOf(Integer.class, value, "Returned Integer new value 1");
        assertEquals(11, (Integer) value, "Returned correct new value 1");
        dynaForm.set("listIndexed", 2, "New Value 2");
        value = dynaForm.get("listIndexed", 2);
        assertNotNull(value, "Returned new value 2");
        assertInstanceOf(String.class, value, "Returned String new value 2");
        assertEquals("New Value 2", value, "Returned correct new value 2");

        dynaForm.set("stringArray", 3, "New Value 3");
        value = dynaForm.get("stringArray", 3);
        assertNotNull(value, "Returned new value 3");
        assertInstanceOf(String.class, value, "Returned String new value 3");
        assertEquals("New Value 3", value, "Returned correct new value 3");

        dynaForm.set("stringIndexed", 4, "New Value 4");
        value = dynaForm.get("stringIndexed", 4);
        assertNotNull(value, "Returned new value 4");
        assertInstanceOf(String.class, value, "Returned String new value 4");
        assertEquals("New Value 4", value, "Returned correct new value 4");
    }

    /**
     * Positive and negative tests on setMappedProperty valid arguments.
     */
    @Test
    public void testSetMappedValues() {
        dynaForm.set("mappedProperty", "First Key", "New First Value");
        assertEquals("New First Value", dynaForm.get("mappedProperty", "First Key"),
            "Can replace old value");

        dynaForm.set("mappedProperty", "Fourth Key", "Fourth Value");
        assertEquals("Fourth Value", dynaForm.get("mappedProperty", "Fourth Key"),
            "Can set new value");
    }

    /**
     * Test setSimpleProperty on a boolean property.
     */
    @Test
    public void testSetSimpleBoolean() {
        boolean oldValue = (Boolean) dynaForm.get("booleanProperty");
        boolean newValue = !oldValue;

        dynaForm.set("booleanProperty", newValue);
        assertEquals(newValue, (Boolean) dynaForm.get("booleanProperty"),
            "Matched new value");
    }

    /**
     * Test setSimpleProperty on a double property.
     */
    @Test
    public void testSetSimpleDouble() {
        double oldValue = (Double) dynaForm.get("doubleProperty");
        double newValue = oldValue + 1.0;

        dynaForm.set("doubleProperty", newValue);
        assertEquals(newValue, (Double) dynaForm.get("doubleProperty"),
            0.005, "Matched new value");
    }

    /**
     * Test setSimpleProperty on a float property.
     */
    @Test
    public void testSetSimpleFloat() {
        float oldValue = (Float) dynaForm.get("floatProperty");
        float newValue = oldValue + (float) 1.0;

        dynaForm.set("floatProperty", newValue);
        assertEquals(newValue, (Float) dynaForm.get("floatProperty"),
            (float) 0.005, "Matched new value");
    }

    /**
     * Test setSimpleProperty on a int property.
     */
    @Test
    public void testSetSimpleInt() {
        int oldValue = (Integer) dynaForm.get("intProperty");
        int newValue = oldValue + 1;

        dynaForm.set("intProperty", newValue);
        assertEquals(newValue, (Integer) dynaForm.get("intProperty"),
            "Matched new value");
    }

    /**
     * Test setSimpleProperty on a long property.
     */
    @Test
    public void testSetSimpleLong() {
        long oldValue = (Long) dynaForm.get("longProperty");
        long newValue = oldValue + 1;

        dynaForm.set("longProperty", newValue);
        assertEquals(newValue, (Long) dynaForm.get("longProperty"),
            "Matched new value");
    }

    /**
     * Test setSimpleProperty on a short property.
     */
    @Test
    public void testSetSimpleShort() {
        short oldValue = (Short) dynaForm.get("shortProperty");
        short newValue = (short) (oldValue + 1);

        dynaForm.set("shortProperty", newValue);
        assertEquals(newValue, (Short) dynaForm.get("shortProperty"),
            "Matched new value");
    }

    /**
     * Test setSimpleProperty on a String property.
     */
    @Test
    public void testSetSimpleString() {
        String oldValue = (String) dynaForm.get("stringProperty");
        String newValue = oldValue + " Extra Value";

        dynaForm.set("stringProperty", newValue);
        assertEquals(newValue, dynaForm.get("stringProperty"),
            "Matched new value");
    }

    // ------------------------------------------------------ Protected Methods

    /**
     * Set up the complex properties that cannot be configured from the
     * initial value expression.
     */
    private void setupComplexProperties() {
        List<String> listIndexed = new ArrayList<>();

        listIndexed.add("String 0");
        listIndexed.add("String 1");
        listIndexed.add("String 2");
        listIndexed.add("String 3");
        listIndexed.add("String 4");
        dynaForm.set("listIndexed", listIndexed);

        Map<String, String> mappedProperty = new HashMap<>();

        mappedProperty.put("First Key", "First Value");
        mappedProperty.put("Second Key", "Second Value");
        dynaForm.set("mappedProperty", mappedProperty);

        Map<String, Integer> mappedIntProperty = new HashMap<>();

        mappedIntProperty.put("One", Integer.valueOf(1));
        mappedIntProperty.put("Two", Integer.valueOf(2));
        dynaForm.set("mappedIntProperty", mappedIntProperty);
    }

    /**
     * Base for testGetDescriptorXxxxx() series of tests.
     *
     * @param name Name of the property to be retrieved
     * @param type Expected class type of this property
     */
    public void testGetDescriptorBase(String name, Class<?> type) {
        DynaProperty descriptor = dynaForm.getDynaClass().getDynaProperty(name);

        assertNotNull(descriptor, "Got descriptor");
        assertEquals(type, descriptor.getType(), "Got correct type");
    }
}


class DynaActionFormMapping extends ActionMapping {
    private static final long serialVersionUID = 3732323850297042328L;

    private ModuleConfig appConfig = null;

    public DynaActionFormMapping(ModuleConfig appConfig) {
        this.appConfig = appConfig;
    }

    public ModuleConfig getModuleConfig() {
        return (this.appConfig);
    }

    public String getName() {
        return ("dynaForm");
    }
}


class DynaActionFormConfig extends ModuleConfigImpl {
    private static final long serialVersionUID = -900294438051733783L;

    private FormBeanConfig beanConfig = null;

    public DynaActionFormConfig(FormBeanConfig beanConfig) {
        super("");
        this.beanConfig = beanConfig;
    }

    public FormBeanConfig findFormBeanConfig(String name) {
        return (this.beanConfig);
    }
}
