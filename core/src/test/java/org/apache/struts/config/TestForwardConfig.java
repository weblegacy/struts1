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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ForwardConfig}.  Currently contains tests for
 * methods supporting configuration inheritance.
 *
 * @version $Rev$ $Date$
 */
public class TestForwardConfig {
    // ----------------------------------------------------- Instance Variables

    /**
     * The ModuleConfig we'll use.
     */
    protected ModuleConfig moduleConfig = null;

    /**
     * The common base we'll use.
     */
    protected ForwardConfig baseConfig = null;

    /**
     * The common subForward we'll use.
     */
    protected ForwardConfig subConfig = null;

    /**
     * A ForwardConfig we'll use to test cases where a ForwardConfig declared
     * for an action extends a ForwardConfig declared globally, with both
     * ForwardConfigs using the same name.
     */
    protected ForwardConfig actionBaseConfig = null;

    /**
     * An action mapping we'll use within tests.
     */
    protected ActionConfig actionConfig = null;

    // --------------------------------------------------------- Public Methods

    /**
     * Set up instance variables required by this test case.
     */
    @BeforeEach
    public void setUp() {
        ModuleConfigFactory factoryObject = ModuleConfigFactory.createFactory();

        moduleConfig = factoryObject.createModuleConfig("");

        // Setup the base and sub forwards, with sub extending base
        baseConfig = new ForwardConfig();
        baseConfig.setName("baseConfig");
        baseConfig.setPath("/somePage.jsp");

        subConfig = new ForwardConfig();
        subConfig.setName("subConfig");
        subConfig.setExtends("baseConfig");
        subConfig.setRedirect(true);

        actionBaseConfig = new ForwardConfig();
        actionBaseConfig.setName("baseConfig");
        actionBaseConfig.setExtends("baseConfig");
        actionBaseConfig.setModule("/other");

        // Setup the default action config
        actionConfig = new ActionConfig();
        actionConfig.setPath("/index");
        moduleConfig.addActionConfig(actionConfig);

        // No forward configs are registered to either module or action configs.
        // Each test will determine where it needs these configs, if at all.
    }

    /**
     * Tear down instance variables required by this test case.
     */
    @AfterEach
    public void tearDown() {
        moduleConfig = null;
        baseConfig = null;
    }

    // ------------------------------------------------------- Individual Tests

    /**
     * Make sure checkCircularInheritance() works as expected where there is
     * no inheritance set up.
     */
    @Test
    public void testCheckCircularInheritanceNoExtends() {
        moduleConfig.addForwardConfig(baseConfig);

        boolean result =
            baseConfig.checkCircularInheritance(moduleConfig, null);

        assertFalse(result, "Incorrect result");
    }

    /**
     * Test checkCircularInheritance() for when there is no circular
     * inheritance.
     */
    @Test
    public void testCheckCircularInheritanceNoConflicts() {
        moduleConfig.addForwardConfig(baseConfig);
        moduleConfig.addForwardConfig(subConfig);

        boolean result = subConfig.checkCircularInheritance(moduleConfig, null);

        assertFalse(result, "Incorrect result");
    }

    /**
     * Test checkCircularInheritance() for circular inheritance between global
     * forwards.
     */
    @Test
    public void testCheckCircularInheritanceBasicGlobal() {
        moduleConfig.addForwardConfig(subConfig);
        moduleConfig.addForwardConfig(baseConfig);

        // set the baseConfig to extend subConfig
        baseConfig.setExtends("subConfig");

        boolean result = subConfig.checkCircularInheritance(moduleConfig, null);

        assertTrue(result, "Circular inheritance not detected.");
    }

    /**
     * Test checkCircularInheritance() for circular inheritance between global
     * forwards.
     */
    @Test
    public void testCheckCircularInheritanceGlobal2Levels() {
        moduleConfig.addForwardConfig(baseConfig);
        moduleConfig.addForwardConfig(subConfig);

        ForwardConfig grand = new ForwardConfig();

        grand.setName("grandConfig");
        grand.setExtends("subConfig");
        moduleConfig.addForwardConfig(grand);

        // set the baseConfig to extend grandConfig
        baseConfig.setExtends("grandConfig");

        boolean result = grand.checkCircularInheritance(moduleConfig, null);

        assertTrue(result, "Circular inheritance not detected.");
    }

    /**
     * Test checkCircularInheritance() for circular inheritance between
     * forwards in an action.
     */
    @Test
    public void testCheckCircularInheritanceActionForwardsNoConflict() {
        actionConfig.addForwardConfig(baseConfig);
        actionConfig.addForwardConfig(subConfig);

        boolean result =
            subConfig.checkCircularInheritance(moduleConfig, actionConfig);

        assertFalse(result, "Incorrect result");
    }

    /**
     * Test checkCircularInheritance() for circular inheritance between
     * forwards in an action.
     */
    @Test
    public void testCheckCircularInheritanceActionForwardsBasic() {
        actionConfig.addForwardConfig(baseConfig);
        actionConfig.addForwardConfig(subConfig);

        // set base to extend sub
        baseConfig.setExtends("subConfig");

        boolean result =
            subConfig.checkCircularInheritance(moduleConfig, actionConfig);

        assertTrue(result, "Circular inheritance not detected.");
    }

    /**
     * Test checkCircularInheritance() for circular inheritance between a
     * forward declared in an action and a global forward.
     */
    @Test
    public void testCheckCircularInheritanceActionForwardExtendGlobal() {
        actionConfig.addForwardConfig(subConfig);
        moduleConfig.addForwardConfig(baseConfig);

        boolean result =
            subConfig.checkCircularInheritance(moduleConfig, actionConfig);

        assertFalse(result, "Incorrect result");
    }

    /**
     * Test checkCircularInheritance() for circular inheritance between a
     * forward declared in an action and a global forward and both forwards
     * have the same name.
     */
    @Test
    public void testCheckCircularInheritanceActionForwardExtendGlobalSameName() {
        moduleConfig.addForwardConfig(baseConfig);
        actionConfig.addForwardConfig(actionBaseConfig);

        boolean result =
            actionBaseConfig.checkCircularInheritance(moduleConfig, actionConfig);

        assertFalse(result, "Incorrect result");
    }

    /**
     * Make sure processExtends() throws an error when the config is already
     * configured.
     */
    @Test
    public void testProcessExtendsConfigured() {
        baseConfig.configured = true;
        moduleConfig.addForwardConfig(baseConfig);

        assertThrows(IllegalStateException.class,
            () -> baseConfig.processExtends(moduleConfig, null),
            "processExtends should not succeed when object is already configured");
    }

    /**
     * Test processExtends() when nothing is extended.
     */
    @Test
    public void testProcessExtendsNoExtension()
        throws Exception {
        String path = baseConfig.getPath();
        String module = baseConfig.getModule();
        String name = baseConfig.getName();
        String inherit = baseConfig.getExtends();
        boolean redirect = baseConfig.getRedirect();

        moduleConfig.addForwardConfig(baseConfig);
        baseConfig.processExtends(moduleConfig, null);

        assertEquals(path, baseConfig.getPath(), "Path shouldn't have changed");
        assertEquals(module, baseConfig.getModule(),
            "Module shouldn't have changed");
        assertEquals(name, baseConfig.getName(), "Name shouldn't have changed");
        assertEquals(inherit, baseConfig.getExtends(),
            "Extends shouldn't have changed");
        assertEquals(redirect, baseConfig.getRedirect(),
            "Redirect shouldn't have changed");
    }

    /**
     * Test processExtends() with a basic extension.
     */
    @Test
    public void testProcessExtendsBasicExtension()
        throws Exception {
        String baseCount = "10";

        baseConfig.setProperty("count", baseCount);

        String baseLabel = "label a";

        baseConfig.setProperty("label", baseLabel);

        String path = baseConfig.getPath();
        String module = baseConfig.getModule();

        String inherit = subConfig.getExtends();
        String name = subConfig.getName();
        boolean redirect = subConfig.getRedirect();

        String subLabel = "label b";

        subConfig.setProperty("label", subLabel);

        moduleConfig.addForwardConfig(baseConfig);
        moduleConfig.addForwardConfig(subConfig);
        subConfig.processExtends(moduleConfig, null);

        assertEquals(path, subConfig.getPath(), "Path wasn't inherited");
        assertEquals(module, subConfig.getModule(), "Module wasn't inherited");
        assertEquals(name, subConfig.getName(), "Name shouldn't have changed");
        assertEquals(inherit, subConfig.getExtends(),
            "Extends shouldn't have changed");
        assertEquals(redirect, subConfig.getRedirect(),
            "Redirect shouldn't have changed");
        assertEquals(baseCount, subConfig.getProperty("count"),
            "Arbitrary config property was not inherited");
        assertEquals(subLabel, subConfig.getProperty("label"),
            "Overridden config property was not retained");
    }

    /**
     * Test processExtends() with a basic extension between an action config
     * and a global config.
     */
    @Test
    public void testProcessExtendsBasicGlobalExtension()
        throws Exception {
        String path = baseConfig.getPath();
        String module = baseConfig.getModule();

        boolean redirect = subConfig.getRedirect();
        String inherit = subConfig.getExtends();
        String name = subConfig.getName();

        moduleConfig.addForwardConfig(baseConfig);
        actionConfig.addForwardConfig(subConfig);
        subConfig.processExtends(moduleConfig, actionConfig);

        assertEquals(path, subConfig.getPath(), "Path wasn't inherited");
        assertEquals(module, subConfig.getModule(), "Module wasn't inherited");
        assertEquals(name, subConfig.getName(), "Name shouldn't have changed");
        assertEquals(inherit, subConfig.getExtends(),
            "Extends shouldn't have changed");
        assertEquals(redirect, subConfig.getRedirect(),
            "Redirect shouldn't have changed");
    }

    /**
     * Test processExtends() with an incorrect setup where a global config
     * attempts to extend an action config.
     */
    @Test
    public void testProcessExtendsGlobalExtendingAction() {
        moduleConfig.addForwardConfig(subConfig);
        actionConfig.addForwardConfig(baseConfig);

        assertThrows(NullPointerException.class,
            () -> subConfig.processExtends(moduleConfig, actionConfig),
            "Should not find config from actionConfig when *this* is global");
    }

    /**
     * Test processExtends() with an action config that extends a global
     * config with the same name.
     */
    @Test
    public void testProcessExtendsSameNames()
        throws Exception {
        String path = baseConfig.getPath();
        boolean redirect = baseConfig.getRedirect();

        String module = actionBaseConfig.getModule();
        String inherit = actionBaseConfig.getExtends();
        String name = actionBaseConfig.getName();

        moduleConfig.addForwardConfig(baseConfig);
        actionConfig.addForwardConfig(actionBaseConfig);

        actionBaseConfig.processExtends(moduleConfig, actionConfig);

        assertEquals(path, actionBaseConfig.getPath(), "Path wasn't inherited");
        assertEquals(module, actionBaseConfig.getModule(),
            "Module shouldn't have changed");
        assertEquals(name, actionBaseConfig.getName(),
            "Name shouldn't have changed");
        assertEquals(inherit, actionBaseConfig.getExtends(),
            "Extends shouldn't have changed");
        assertEquals(redirect, actionBaseConfig.getRedirect(),
            "Redirect shouldn't have changed");
    }

    /**
     * Test processExtends() where an action ForwardConfig extends another
     * ForwardConfig, which in turn extends a global ForwardConfig with the
     * same name.
     */
    @Test
    public void testProcessExtendsActionExtendsActionExtendsGlobalWithSameName()
        throws Exception {
        String path = baseConfig.getPath();

        String module = actionBaseConfig.getModule();

        boolean redirect = subConfig.getRedirect();
        String inherit = subConfig.getExtends();
        String name = subConfig.getName();

        moduleConfig.addForwardConfig(baseConfig);
        actionConfig.addForwardConfig(actionBaseConfig);
        actionConfig.addForwardConfig(subConfig);

        subConfig.processExtends(moduleConfig, actionConfig);

        assertTrue(baseConfig.extensionProcessed,
            "baseConfig's processExtends() should've been called");
        assertTrue(actionBaseConfig.extensionProcessed,
            "actionBaseConfig's processExtends() should've been called");

        assertEquals(path, subConfig.getPath(), "Path wasn't inherited");
        assertEquals(module, subConfig.getModule(), "Module wasn't inherited");
        assertEquals(name, subConfig.getName(), "Name shouldn't have changed");
        assertEquals(inherit, subConfig.getExtends(),
            "Extends shouldn't have changed");
        assertEquals(redirect, subConfig.getRedirect(),
            "Redirect shouldn't have changed");
    }
}
