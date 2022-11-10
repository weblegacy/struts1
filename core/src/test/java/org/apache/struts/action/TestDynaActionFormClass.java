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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.FormPropertyConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Suite of unit tests for the {@link DynaActionFormClass} class.
 */
public class TestDynaActionFormClass {
    /**
     * The set of {@code FormPropertyConfig} objects to use when creating
     * our {@code FormBeanConfig}.
     */
    protected static final FormPropertyConfig[] dynaProperties =
        {
            new FormPropertyConfig("booleanProperty", "boolean", "true", "true"),
            new FormPropertyConfig("booleanSecond", "boolean", "true", "true"),
            new FormPropertyConfig("doubleProperty", "double", "321.0", "GET"),
            new FormPropertyConfig("floatProperty", "float", "123.0",
                "POST, HEAD"),
            new FormPropertyConfig("intArray", "int[]",
                "{ 0, 10,20, \"30\" '40' }"),
            new FormPropertyConfig("intIndexed", "int[]",
                " 0 100, 200, 300, 400 "),
            new FormPropertyConfig("intProperty", "int", "123"),
            new FormPropertyConfig("listIndexed", "java.util.List", null),
            new FormPropertyConfig("longProperty", "long", "321"),
            new FormPropertyConfig("mappedProperty", "java.util.Map", null),
            new FormPropertyConfig("mappedIntProperty", "java.util.Map", null),


            //        new FormPropertyConfig("nullProperty", "java.lang.String", null),
            new FormPropertyConfig("shortProperty", "short", "987"),
            new FormPropertyConfig("stringArray", "java.lang.String[]",
                "{ 'String 0', 'String 1', 'String 2', 'String 3', 'String 4'}"),
            new FormPropertyConfig("stringIndexed", "java.lang.String[]",
                "{ 'String 0', 'String 1', 'String 2', 'String 3', 'String 4'}"),
            new FormPropertyConfig("stringProperty", "java.lang.String",
                "This is a string"),
        };

    // ----------------------------------------------------- Instance Variables

    /**
     * The {@code FormBeanConfig} structure for the form bean we will be
     * creating.
     */
    protected FormBeanConfig beanConfig = null;

    /**
     * The [@code DynaActionFormClass} to use for testing.
     */
    protected DynaActionFormClass dynaClass = null;

    // ----------------------------------------------------- Setup and Teardown
    @BeforeEach
    public void setUp() {
        // Construct a FormBeanConfig to be used
        beanConfig = new FormBeanConfig();
        beanConfig.setName("dynaForm");
        beanConfig.setType("org.apache.struts.action.DynaActionForm");

        // Add relevant property definitions
        for (int i = 0; i < dynaProperties.length; i++) {
            beanConfig.addFormPropertyConfig(dynaProperties[i]);
        }

        // Construct a corresponding DynaActionFormClass
        dynaClass = new DynaActionFormClass(beanConfig);
    }

    @AfterEach
    public void tearDown() {
        dynaClass = null;
        beanConfig = null;
    }

    // -------------------------------------------------- Verify FormBeanConfig
    // Check for ability to add a property before and after freezing
    @Test
    public void testConfigAdd() {
        FormPropertyConfig prop = null;

        // Before freezing
        prop = beanConfig.findFormPropertyConfig("fooProperty");
        assertNull(prop, "fooProperty not found");
        beanConfig.addFormPropertyConfig(new FormPropertyConfig("fooProperty",
                "java.lang.String", ""));
        prop = beanConfig.findFormPropertyConfig("fooProperty");
        assertNotNull(prop, "fooProperty found");

        // after freezing
        beanConfig.freeze();
        prop = beanConfig.findFormPropertyConfig("barProperty");
        assertNull(prop, "barProperty not found");

        try {
            beanConfig.addFormPropertyConfig(new FormPropertyConfig(
                    "barProperty", "java.lang.String", ""));
            fail("barProperty add not prevented");
        } catch (IllegalStateException e) {
            ; // Expected result
        }
    }

    // Check basic FormBeanConfig properties
    @Test
    public void testConfigCreate() {
        assertTrue(beanConfig.getDynamic(), "dynamic is correct");
        assertEquals("dynaForm", beanConfig.getName(), "name is correct");
        assertEquals("org.apache.struts.action.DynaActionForm",
            beanConfig.getType(), "type is correct");
    }

    // Check attempts to add a duplicate property name
    @Test
    public void testConfigDuplicate() {
        FormPropertyConfig prop = null;

        assertNull(prop, "booleanProperty is found");

        try {
            beanConfig.addFormPropertyConfig(new FormPropertyConfig(
                    "booleanProperty", "java.lang.String", ""));
            fail("Adding duplicate property not prevented");
        } catch (IllegalArgumentException e) {
            ; // Expected result
        }
    }

    // Check the configured FormPropertyConfig element initial values
    @Test
    public void testConfigInitialValues() {
        assertEquals(Boolean.TRUE,
            beanConfig.findFormPropertyConfig("booleanProperty").initial(),
            "booleanProperty value");
        assertEquals(Boolean.TRUE,
            beanConfig.findFormPropertyConfig("booleanSecond").initial(),
            "booleanSecond value");
        assertEquals(Double.valueOf(321.0),
            beanConfig.findFormPropertyConfig("doubleProperty").initial(),
            "doubleProperty value");
        assertEquals(Float.valueOf((float) 123.0),
            beanConfig.findFormPropertyConfig("floatProperty").initial(),
            "floatProperty value");
        assertEquals(Integer.valueOf(123),
            beanConfig.findFormPropertyConfig("intProperty").initial(),
            "intProperty value");

        // FIXME - listIndexed
        assertEquals(Long.valueOf(321),
            beanConfig.findFormPropertyConfig("longProperty").initial(),
            "longProperty value");

        // FIXME - mappedProperty
        // FIXME - mappedIntProperty
        //        assertNull(beanConfig.findFormPropertyConfig("nullProperty").initial(),
        //                   "nullProperty value");
        assertEquals(Short.valueOf((short) 987),
            beanConfig.findFormPropertyConfig("shortProperty").initial(),
            "shortProperty value");

        // FIXME - stringArray
        // FIXME - stringIndexed
        assertEquals("This is a string",
            beanConfig.findFormPropertyConfig("stringProperty").initial(),
            "stringProperty value");
    }

    // Check the configured FormPropertyConfig element properties
    @Test
    public void testConfigProperties() {
        for (int i = 0; i < dynaProperties.length; i++) {
            FormPropertyConfig dynaProperty =
                beanConfig.findFormPropertyConfig(dynaProperties[i].getName());

            assertNotNull(dynaProperty,
                "Found dynaProperty " + dynaProperties[i].getName());
            assertEquals(dynaProperties[i].getName(), dynaProperty.getName(),
                "dynaProperty name for " + dynaProperties[i].getName());
            assertEquals(dynaProperties[i].getType(), dynaProperty.getType(),
                "dynaProperty type for " + dynaProperties[i].getName());
            assertEquals(dynaProperties[i].getInitial(), dynaProperty.getInitial(),
                "dynaProperty initial for " + dynaProperties[i].getName());
        }
    }

    // Check for ability to remove a property before and after freezing
    @Test
    public void testConfigRemove() {
        FormPropertyConfig prop = null;

        // Before freezing
        prop = beanConfig.findFormPropertyConfig("booleanProperty");
        assertNotNull(prop, "booleanProperty found");
        beanConfig.removeFormPropertyConfig(prop);
        prop = beanConfig.findFormPropertyConfig("booleanProperty");
        assertNull(prop, "booleanProperty not deleted");

        // after freezing
        beanConfig.freeze();
        prop = beanConfig.findFormPropertyConfig("booleanSecond");
        assertNotNull(prop, "booleanSecond found");

        try {
            beanConfig.removeFormPropertyConfig(prop);
            fail("booleanSecond remove not prevented");
        } catch (IllegalStateException e) {
            ; // Expected result
        }
    }

    // --------------------------------------------- Create DynaActionFormClass
    // Test basic DynaActionFormClass name and properties
    @Test
    public void testClassCreate() {
        assertEquals("dynaForm", dynaClass.getName(), "name");

        for (int i = 0; i < dynaProperties.length; i++) {
            DynaProperty prop =
                dynaClass.getDynaProperty(dynaProperties[i].getName());

            assertNotNull(dynaProperties[i].getName(),
                "Found property " + dynaProperties[i].getName());
            assertEquals(dynaProperties[i].getTypeClass(), prop.getType(),
                "Class for property " + dynaProperties[i].getName());
        }
    }
}
