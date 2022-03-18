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
package org.apache.struts.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link FormBeanConfig} class.  Currently only
 * contains code to test the methods that support configuration inheritance.
 *
 * @version $Rev$ $Date$
 */
public class TestFormBeanConfig {
    // ----------------------------------------------------- Instance Variables

    /**
     * The ModuleConfig we'll use.
     */
    protected ModuleConfig config = null;

    /**
     * The common base we'll use.
     */
    protected FormBeanConfig baseForm = null;

    // --------------------------------------------------------- Public Methods

    /**
     * Set up instance variables required by this test case.
     */
    @BeforeEach
    public void setUp() {
        ModuleConfigFactory factoryObject = ModuleConfigFactory.createFactory();

        config = factoryObject.createModuleConfig("");

        // setup the base form
        baseForm = new FormBeanConfig();
        baseForm.setName("baseForm");
        baseForm.setType("org.apache.struts.action.DynaActionForm");

        // set up id, name, and score
        FormPropertyConfig property = new FormPropertyConfig();

        property.setName("id");
        property.setType("java.lang.String");
        baseForm.addFormPropertyConfig(property);

        property = new FormPropertyConfig();
        property.setName("name");
        property.setType("java.lang.String");
        property.setProperty("count", "10");
        baseForm.addFormPropertyConfig(property);

        property = new FormPropertyConfig();
        property.setName("score");
        property.setType("java.lang.String");
        baseForm.addFormPropertyConfig(property);

        property = new FormPropertyConfig();
        property.setName("message");
        property.setType("java.lang.String");
        baseForm.addFormPropertyConfig(property);

        // register it to our config
        config.addFormBeanConfig(baseForm);
    }

    /**
     * Tear down instance variables required by this test case.
     */
    @AfterEach
    public void tearDown() {
        config = null;
        baseForm = null;
    }

    // ------------------------------------------------------- Individual Tests

    /**
     * Basic check that shouldn't detect an error.
     */
    @Test
    public void testCheckCircularInheritance() {
        FormBeanConfig child = new FormBeanConfig();

        child.setName("child");
        child.setExtends("baseForm");

        FormBeanConfig grandChild = new FormBeanConfig();

        grandChild.setName("grandChild");
        grandChild.setExtends("child");

        config.addFormBeanConfig(child);
        config.addFormBeanConfig(grandChild);

        assertFalse(grandChild.checkCircularInheritance(config),
            "Circular inheritance shouldn't have been detected");
    }

    /**
     * Basic check that SHOULD detect an error.
     */
    @Test
    public void testCheckCircularInheritanceError() {
        FormBeanConfig child = new FormBeanConfig();

        child.setName("child");
        child.setExtends("baseForm");

        FormBeanConfig grandChild = new FormBeanConfig();

        grandChild.setName("grandChild");
        grandChild.setExtends("child");

        // establish the circular relationship with base
        baseForm.setExtends("grandChild");

        config.addFormBeanConfig(child);
        config.addFormBeanConfig(grandChild);

        assertTrue(grandChild.checkCircularInheritance(config),
            "Circular inheritance should've been detected");
    }

    /**
     * Test that processExtends() makes sure that a base form's own extension
     * has been processed.
     */
    @Test
    public void testProcessExtendsBaseFormExtends()
        throws Exception {
        CustomFormBeanConfig first = new CustomFormBeanConfig();

        first.setName("first");

        CustomFormBeanConfig second = new CustomFormBeanConfig();

        second.setName("second");
        second.setExtends("first");

        config.addFormBeanConfig(first);
        config.addFormBeanConfig(second);

        // set baseForm to extend second
        baseForm.setExtends("second");

        baseForm.processExtends(config);

        assertTrue(first.processExtendsCalled,
            "The first form's processExtends() wasn't called");
        assertTrue(second.processExtendsCalled,
            "The second form's processExtends() wasn't called");
    }

    /**
     * Make sure that correct exception is thrown if a base form can't be
     * found.
     */
    @Test
    public void testProcessExtendsMissingBaseForm() {
        baseForm.setExtends("someMissingForm");

        assertThrows(NullPointerException.class,
            () -> baseForm.processExtends(config),
            "An exception should be thrown if a super form can't be found.");
    }

    /**
     * Test a typical form bean configuration extension where various
     * properties should be inherited from a base form.  This method checks
     * all the properties.
     */
    @Test
    public void testInheritFrom()
        throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        // give baseForm some arbitrary parameters
        String baseFormCount = "1";

        baseForm.setProperty("count", baseFormCount);

        // create a basic subform
        FormBeanConfig subForm = new FormBeanConfig();
        String subFormName = "subForm";

        subForm.setName(subFormName);
        subForm.setExtends("baseForm");

        // override score
        FormPropertyConfig property = new FormPropertyConfig();

        property.setName("score");
        property.setType("java.lang.Integer");
        subForm.addFormPropertyConfig(property);

        // ... and id
        property = new FormPropertyConfig();
        property.setName("id");
        property.setType("java.lang.String");
        property.setInitial("unknown");
        subForm.addFormPropertyConfig(property);

        // ... and message
        property = new FormPropertyConfig();
        property.setName("message");
        property.setType("java.lang.String");
        property.setSize(10);
        subForm.addFormPropertyConfig(property);

        config.addFormBeanConfig(subForm);

        subForm.inheritFrom(baseForm);

        // check that our subForm is still the one in the config
        assertSame(subForm, config.findFormBeanConfig("subForm"),
            "subForm no longer in ModuleConfig");

        // check our configured sub form
        assertNotNull(subForm.getType(), "Form bean type was not inherited");
        assertEquals(subFormName, subForm.getName(), "Wrong form bean name");
        assertEquals(baseForm.getType(), subForm.getType(),
            "Wrong form bean type");
        assertEquals(baseForm.isRestricted(), subForm.isRestricted(),
            "Wrong restricted value");

        FormPropertyConfig[] formPropertyConfigs =
            subForm.findFormPropertyConfigs();

        assertEquals(4, formPropertyConfigs.length, "Wrong prop count");

        // we want to check that the form is EXACTLY as we want it, so
        //  here are some fine grain checks
        property = subForm.findFormPropertyConfig("name");

        FormPropertyConfig original = baseForm.findFormPropertyConfig("name");

        assertNotNull(property, "'name' property was not inherited");
        assertEquals(original.getType(), property.getType(),
            "Wrong type for name");
        assertEquals(original.getInitial(), property.getInitial(),
            "Wrong initial value for name");
        assertEquals(original.getSize(), property.getSize(),
            "Wrong size value for name");

        property = subForm.findFormPropertyConfig("id");
        original = baseForm.findFormPropertyConfig("id");
        assertNotNull(property, "'id' property was not found");
        assertEquals(original.getType(), property.getType(), "Wrong type for id");
        assertEquals("unknown", property.getInitial(),
            "Wrong initial value for id");
        assertEquals(original.getSize(), property.getSize(),
            "Wrong size value for id");

        property = subForm.findFormPropertyConfig("score");
        original = baseForm.findFormPropertyConfig("score");
        assertNotNull(property, "'score' property was not found");
        assertEquals(property.getType(), "java.lang.Integer",
            "Wrong type for score");
        assertEquals(original.getInitial(), property.getInitial(),
            "Wrong initial value for score");
        assertEquals(original.getSize(), property.getSize(),
            "Wrong size value for score");

        property = subForm.findFormPropertyConfig("message");
        original = baseForm.findFormPropertyConfig("message");
        assertNotNull(property, "'message' property was not found");
        assertEquals(original.getType(), property.getType(),
            "Wrong type for message");
        assertEquals(original.getInitial(), property.getInitial(),
            "Wrong initial value for message");
        assertEquals(10, property.getSize(),
            "Wrong size value for message");

        property = subForm.findFormPropertyConfig("name");
        original = baseForm.findFormPropertyConfig("name");
        assertEquals(original.getProperty("count"), property.getProperty("count"),
            "Arbitrary property not found");

        String count = subForm.getProperty("count");

        assertEquals(baseFormCount, count,
            "Arbitrary property was not inherited");
    }

    /**
     * Used to detect that FormBeanConfig is making the right calls.
     */
    public static class CustomFormBeanConfig extends FormBeanConfig {
        private static final long serialVersionUID = -1148163983085104532L;

        boolean processExtendsCalled = false;

        public void processExtends(ModuleConfig moduleConfig)
            throws ClassNotFoundException, IllegalAccessException,
                InstantiationException, InvocationTargetException {
            super.processExtends(moduleConfig);
            processExtendsCalled = true;
        }
    }
}
