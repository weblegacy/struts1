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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link ActionConfig} class.  Currently only
 * contains code to test the methods that support configuration inheritance.
 *
 * @version $Rev$ $Date$
 */
public class TestActionConfig {
    // ----------------------------------------------------- Instance Variables

    /**
     * The ModuleConfig we'll use.
     */
    protected ModuleConfig config = null;

    /**
     * The common base we'll use.
     */
    protected ActionConfig baseConfig = null;

    // --------------------------------------------------------- Public Methods

    /**
     * Set up instance variables required by this test case.
     */
    @BeforeEach
    public void setUp() {
        ModuleConfigFactory factoryObject = ModuleConfigFactory.createFactory();

        config = factoryObject.createModuleConfig("");

        // setup the base form
        baseConfig = new ActionConfig();
        baseConfig.setPath("/base");
        baseConfig.setType("org.apache.struts.extras.actions.DummyAction");

        // set up success and failure forward
        ForwardConfig forward =
            new ForwardConfig("success", "/success.jsp", false);

        baseConfig.addForwardConfig(forward);

        forward = new ForwardConfig("failure", "/failure.jsp", false);
        forward.setProperty("forwardCount", "10");
        baseConfig.addForwardConfig(forward);

        // setup an exception handler
        ExceptionConfig exceptionConfig = new ExceptionConfig();

        exceptionConfig.setType("java.sql.SQLException");
        exceptionConfig.setKey("msg.exception.sql");
        exceptionConfig.setProperty("exceptionCount", "10");
        baseConfig.addExceptionConfig(exceptionConfig);

        // set some arbitrary properties
        baseConfig.setProperty("label", "base");
        baseConfig.setProperty("version", "1a");

        // register it to our config
        config.addActionConfig(baseConfig);
    }

    /**
     * Tear down instance variables required by this test case.
     */
    @AfterEach
    public void tearDown() {
        config = null;
        baseConfig = null;
    }

    // ------------------------------------------------------- Individual Tests

    /**
     * Basic check that shouldn't detect circular inheritance.
     */
    @Test
    public void testCheckCircularInheritance() {
        ActionConfig child = new ActionConfig();

        child.setPath("/child");
        child.setExtends("/base");

        ActionConfig grandChild = new ActionConfig();

        grandChild.setPath("/grandChild");
        grandChild.setExtends("/child");

        config.addActionConfig(child);
        config.addActionConfig(grandChild);

        assertFalse(grandChild.checkCircularInheritance(config),
            "Circular inheritance shouldn't have been detected");
    }

    /**
     * Basic check that should detect circular inheritance.
     */
    @Test
    public void testCheckCircularInheritanceError() {
        ActionConfig child = new ActionConfig();

        child.setPath("/child");
        child.setExtends("/base");

        ActionConfig grandChild = new ActionConfig();

        grandChild.setPath("/grandChild");
        grandChild.setExtends("/child");

        // establish the circular relationship with base
        baseConfig.setExtends("/grandChild");

        config.addActionConfig(child);
        config.addActionConfig(grandChild);

        assertTrue(grandChild.checkCircularInheritance(config),
            "Circular inheritance should've been detected");
    }

    /**
     * Test that processExtends() makes sure that a base action's own
     * extension has been processed.
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    @Test
    public void testProcessExtendsActionExtends()
        throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        CustomActionConfig first = new CustomActionConfig();

        first.setPath("/first");

        CustomActionConfig second = new CustomActionConfig();

        second.setPath("/second");
        second.setExtends("/first");

        config.addActionConfig(first);
        config.addActionConfig(second);

        // set baseConfig to extend second
        baseConfig.setExtends("/second");

        baseConfig.processExtends(config);

        assertTrue(first.processExtendsCalled,
            "The first action's processExtends() wasn't called");
        assertTrue(second.processExtendsCalled,
            "The second action's processExtends() wasn't called");
    }

    /**
     * Make sure that correct exception is thrown if a base action can't be
     * found.
     */
    @Test
    public void testProcessExtendsMissingAction() {
        baseConfig.setExtends("/someMissingAction");

        assertThrows(NullPointerException.class,
            () -> baseConfig.processExtends(config),
            "An exception should be thrown if a super form can't be found.");
    }

    /**
     * Test a typical form bean configuration extension where various forwards
     * and exception handlers should be inherited from a base form. This
     * method checks all the subelements.
     */
    @Test
    public void testInheritFrom()
        throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        // create a basic subform
        ActionConfig subConfig = new ActionConfig();
        String subConfigPath = "subConfig";

        subConfig.setPath(subConfigPath);
        subConfig.setExtends("/base");

        // override success
        ForwardConfig forward = new ForwardConfig();

        forward.setName("success");
        forward.setPath("/newSuccess.jsp");
        forward.setRedirect(true);
        subConfig.addForwardConfig(forward);

        // add an exception handler
        ExceptionConfig handler = new ExceptionConfig();

        handler.setType("java.lang.NullPointerException");
        handler.setKey("msg.exception.npe");
        subConfig.addExceptionConfig(handler);

        // override arbitrary "label" property
        subConfig.setProperty("label", "sub");

        config.addActionConfig(subConfig);

        subConfig.inheritFrom(baseConfig);

        // check that our subConfig is still the one in the config
        assertSame(subConfig, config.findActionConfig("subConfig"),
            "subConfig no longer in ModuleConfig");

        // check our configured sub config
        assertNotNull(subConfig.getType(), "Action type was not inherited");
        assertEquals(subConfigPath, subConfig.getPath(), "Wrong config path");
        assertEquals(baseConfig.getType(), subConfig.getType(),
            "Wrong config type");

        // check our forwards
        ForwardConfig[] forwards = subConfig.findForwardConfigs();

        assertEquals(2, forwards.length, "Wrong forwards count");

        forward = subConfig.findForwardConfig("success");
        assertNotNull(forward, "'success' forward was not found");
        assertEquals("/newSuccess.jsp", forward.getPath(),
            "Wrong path for success");

        forward = subConfig.findForwardConfig("failure");

        ForwardConfig origForward = baseConfig.findForwardConfig("failure");

        assertNotNull(forward, "'failure' forward was not inherited");
        assertEquals(origForward.getPath(), forward.getPath(),
            "Wrong type for 'failure'");
        assertEquals(origForward.getProperty("forwardCount"),
            forward.getProperty("forwardCount"),
            "Arbitrary property not copied");

        // check our exceptions
        ExceptionConfig[] handlers = subConfig.findExceptionConfigs();

        assertEquals(2, handlers.length, "Wrong exception config count");

        handler = subConfig.findExceptionConfig("java.sql.SQLException");

        ExceptionConfig origHandler =
            baseConfig.findExceptionConfig("java.sql.SQLException");

        assertNotNull(handler, "'SQLException' handler was not found");
        assertEquals(origHandler.getKey(), handler.getKey(),
            "Wrong key for 'SQLException'");
        assertEquals(origHandler.getProperty("exceptionCount"),
            handler.getProperty("exceptionCount"),
            "Arbitrary property not copied");

        handler =
            subConfig.findExceptionConfig("java.lang.NullPointerException");
        assertNotNull(handler, "'NullPointerException' handler disappeared");

        // check the arbitrary properties
        String version = subConfig.getProperty("version");

        assertEquals("1a", version,
            "Arbitrary property 'version' wasn't inherited");

        String label = subConfig.getProperty("label");

        assertEquals("sub", label,
            "Arbitrary property 'label' shouldn't have changed");
    }

    /**
     * Make sure that correct exception is thrown if a base action can't be
     * found.
     */
    @Test
    public void testInheritBoolean()
        throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {

        ActionConfig parentConfig = new ActionConfig();
        parentConfig.setPath("/parent");
        ActionConfig childConfig  = null;

        // Test if boolean is NOT set it IS inherited
        parentConfig.setValidate(true);
        parentConfig.setCancellable(true);
        childConfig = new ActionConfig();
        childConfig.inheritFrom(parentConfig);
        assertTrue(childConfig.getValidate(), "default validate inherit true");
        assertTrue(childConfig.getCancellable(), "default cancellable inherit true");

        // Test if boolean is NOT set it IS inherited
        parentConfig.setValidate(false);
        parentConfig.setCancellable(false);
        childConfig = new ActionConfig();
        childConfig.inheritFrom(parentConfig);
        assertFalse(childConfig.getValidate(), "default validate inherit false");
        assertFalse(childConfig.getCancellable(), "default cancellable inherit false");

        // Test if boolean IS set it is NOT inherited
        parentConfig.setValidate(true);
        parentConfig.setCancellable(true);
        childConfig = new ActionConfig();
        childConfig.setValidate(false);
        childConfig.setCancellable(false);
        childConfig.inheritFrom(parentConfig);
        assertFalse(childConfig.getValidate(), "set validate (not inherit true)");
        assertFalse(childConfig.getCancellable(), "set cancellable (not inherit false)");

        // Test if boolean IS set it is NOT inherited
        parentConfig.setValidate(false);
        parentConfig.setCancellable(false);
        childConfig = new ActionConfig();
        childConfig.setValidate(true);
        childConfig.setCancellable(true);
        childConfig.inheritFrom(parentConfig);
        assertTrue(childConfig.getValidate(), "set validate (not inherit false)");
        assertTrue(childConfig.getCancellable(), "set cancellable (not inherit false)");

    }

    /**
     * Test getter of acceptPage property.
     */
    @Test
    public void testGetAcceptPage() {
        ActionConfig config = new ActionConfig();
        assertNull(config.getAcceptPage());
    }

    /**
     * Test setter of acceptPage property.
     */
    @Test
    public void testSetAcceptPage() {
        ActionConfig config = new ActionConfig();
        Integer acceptPage = 5;
        config.setAcceptPage(acceptPage);
        assertEquals(acceptPage, config.getAcceptPage());
    }

    /**
     * Test a String object representing the value of the ActionConfig object.
     */
    @Test
    public void testToStringAcceptPage() {
        ActionConfig config = new ActionConfig();
        Integer acceptPage = 7;
        config.setAcceptPage(acceptPage);
        String test = "acceptPage=" + acceptPage;
        assertTrue(config.toString().contains(test));
    }

    /**
     * Used to detect that ActionConfig is making the right calls.
     */
    public static class CustomActionConfig extends ActionConfig {
        private static final long serialVersionUID = 4345195201607298134L;

        boolean processExtendsCalled = false;

        public void processExtends(ModuleConfig moduleConfig)
            throws ClassNotFoundException, IllegalAccessException,
                InstantiationException, InvocationTargetException {
            super.processExtends(moduleConfig);
            processExtendsCalled = true;
        }
    }
}
