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
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URL;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;

import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ExceptionConfig;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.FormPropertyConfig;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.ModuleConfigFactory;
import org.apache.struts.util.MessageResources;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Suite of unit tests for the {@link ActionServlet} class.
 */
public class TestActionServlet {
    // ----------------------------------------------------- Instance Variables

    /**
     * The ModuleConfig we'll use.
     */
    protected ModuleConfig moduleConfig = null;

    /**
     * The common form bean we'll use.
     */
    protected FormBeanConfig baseFormBean = null;

    /**
     * The common exception config we'll use.
     */
    protected ExceptionConfig baseException = null;

    /**
     * The common action config we'll use.
     */
    protected ActionMapping baseAction = null;

    /**
     * The common action forward we'll use.
     */
    protected ActionForward baseForward = null;

    /**
     * The ActionServlet we'll test.
     */
    protected ActionServlet actionServlet = null;

    // ------------------------------------------------- setUp() and tearDown()

    /**
     * Set up instance variables required by this test case.
     *
     * @throws ServletException if we cannot initialize these resources
     */
    @BeforeEach
    public void setUp() throws ServletException {
        actionServlet = new ActionServlet();
        actionServlet.initInternal();

        ModuleConfigFactory factoryObject = ModuleConfigFactory.createFactory();

        moduleConfig = factoryObject.createModuleConfig("");

        // Setup the base form
        baseFormBean = new FormBeanConfig();
        baseFormBean.setName("baseForm");
        baseFormBean.setType("org.apache.struts.action.DynaActionForm");

        // Set up id, name, and score
        FormPropertyConfig property = new FormPropertyConfig();

        property.setName("id");
        property.setType("java.lang.String");
        baseFormBean.addFormPropertyConfig(property);

        property = new FormPropertyConfig();
        property.setName("name");
        property.setType("java.lang.String");
        baseFormBean.addFormPropertyConfig(property);

        property = new FormPropertyConfig();
        property.setName("score");
        property.setType("java.lang.String");
        baseFormBean.addFormPropertyConfig(property);

        // Setup the exception handler
        baseException = new ExceptionConfig();
        baseException.setType("java.lang.NullPointerException");
        baseException.setKey("msg.exception.npe");

        // Setup the forward config
        baseForward = new ActionForward("success", "/succes.jsp", false);

        // Setup the action config
        baseAction = new ActionMapping();
        baseAction.setPath("/index");
        baseAction.setType("org.apache.struts.extras.actions.DummyAction");
        baseAction.setName("someForm");
        baseAction.setInput("/input.jsp");
        baseAction.addForwardConfig(new ActionForward("next", "/next.jsp", false));
        baseAction.addForwardConfig(new ActionForward("prev", "/prev.jsp", false));

        ExceptionConfig exceptionConfig = new ExceptionConfig();

        exceptionConfig.setType("java.sql.SQLException");
        exceptionConfig.setKey("msg.exception.sql");
        baseAction.addExceptionConfig(exceptionConfig);

        // Nothing is registered to our module config until they are needed
    }

    /**
     * Tear down instance variables required by this test case.
     */
    @AfterEach
    public void tearDown() {
        moduleConfig = null;
    }

    // ----------------------------- initInternal() and destroyInternal() tests

    /**
     * Verify that we can initialize and destroy our internal message
     * resources object.
     */
    @Test
    public void testInitDestroyInternal() {
        ActionServlet servlet = new ActionServlet();

        try {
            servlet.initInternal();
        } catch (ServletException e) {
            fail("initInternal() threw exception: " + e);
        }

        assertNotNull(servlet.getInternal(), "internal was initialized");
        assertInstanceOf(MessageResources.class, servlet.getInternal(),
            "internal of correct type");
        servlet.destroyInternal();
        assertNull(servlet.getInternal(), "internal was destroyed");
    }

    /**
     * Test class loader resolution and splitting.
     *
     * @throws ServletException if a servlet exception is thrown
     */
    @Test
    @Disabled
    public void notestSplitAndResolvePaths()
        throws ServletException {
        ActionServlet servlet = new ActionServlet();
        List<URL> list =
            servlet.splitAndResolvePaths(
                "org/apache/struts/config/struts-config.xml");

        assertNotNull(list);
        assertEquals(1, list.size(), "List size should be 1");

        list =
            servlet.splitAndResolvePaths(
                "org/apache/struts/config/struts-config.xml, "
                + "org/apache/struts/config/struts-config-1.1.xml");
        assertNotNull(list);
        assertEquals(2, list.size(), "List size should be 2, was " + list.size());

        list = servlet.splitAndResolvePaths("META-INF/MANIFEST.MF");
        assertNotNull(list);
        assertTrue(list.size() > 5, "Number of manifests should be more than 5, was "
            + list.size());

        // test invalid path
        try {
            list =
                servlet.splitAndResolvePaths(
                    "org/apache/struts/config/struts-asdfasdfconfig.xml");
            fail("Should have thrown an exception on bad path");
        } catch (NullPointerException ex) {
            // correct behavior since internal error resources aren't loaded
        }
    }

    //----- Test initApplication() method --------------------------------------

    /**
     * Verify that nothing happens if no "application" property is defined in
     * the servlet configuration.
     *
     * @throws ServletException if initialization cannot be performed.
     */

    /*
    @Test
    @Disabled
    public void testInitApplicationNull()
        throws ServletException {
        ActionServlet servlet = new ActionServlet();
        servlet.init(config);

        // Test the initApplication() method
        servlet.initApplication();

        // As no "application" object is found in the servlet config, no
        // attribute should be set in the context
        assertNull(config.getServletContext().getAttribute(Action.MESSAGES_KEY));
    }
    */

    /**
     * Verify that eveything is fine when only a "application" parameter is
     * defined in the servlet configuration.
     *
     * @throws ServletException if initialization cannot be performed.
     */

    /*
    @Test
    @Disabled
    public void testInitApplicationOk1()
        throws ServletException {
        // initialize config
        config.setInitParameter("application", "org.apache.struts.webapp.example.ApplicationResources");

        ActionServlet servlet = new ActionServlet();
        servlet.init(config);

        // Test the initApplication() method
        servlet.initApplication();

        assertNotNull(servlet.application);
        assertTrue(servlet.application.getReturnNull());

        assertNotNull(config.getServletContext().getAttribute(Action.MESSAGES_KEY));
        assertEquals(servlet.application, config.getServletContext().getAttribute(Action.MESSAGES_KEY));

    }
    */

    // --------------------------------------------------- FormBeanConfig Tests

    /**
     * Test that nothing fails if there are no extensions.
     */
    @Test
    public void testInitModuleFormBeansNoExtends() {
        moduleConfig.addFormBeanConfig(baseFormBean);

        try {
            actionServlet.initModuleExceptionConfigs(moduleConfig);
        } catch (Exception e) {
            fail("Unexpected exception caught.");
        }
    }

    /**
     * Test that initModuleFormBeans throws an exception when a form with a
     * null type is present.
     */
    @Test
    public void testInitModuleFormBeansNullFormType() {
        FormBeanConfig formBean = new FormBeanConfig();

        formBean.setName("noTypeForm");
        moduleConfig.addFormBeanConfig(formBean);

        try {
            actionServlet.initModuleFormBeans(moduleConfig);
            fail("An exception should've been thrown here.");
        } catch (UnavailableException e) {
            // success
        } catch (Exception e) {
            fail("Unrecognized exception thrown: " + e);
        }
    }

    /**
     * Test that initModuleFormBeans throws an exception when a form whose
     * prop type is null is present.
     */
    @Test
    public void testInitModuleFormBeansNullPropType() {
        moduleConfig.addFormBeanConfig(baseFormBean);
        baseFormBean.findFormPropertyConfig("name").setType(null);

        try {
            actionServlet.initModuleFormBeans(moduleConfig);
            fail("An exception should've been thrown here.");
        } catch (UnavailableException e) {
            // success
        } catch (Exception e) {
            fail("Unrecognized exception thrown: " + e);
        }
    }

    /**
     * Test that processFormBeanExtension() calls processExtends()
     */
    @Test
    public void testProcessFormBeanExtension()
        throws ServletException {
        CustomFormBeanConfig form = new CustomFormBeanConfig();

        actionServlet.processFormBeanExtension(form, moduleConfig);

        assertTrue(form.processExtendsCalled, "processExtends() was not called");
    }

    /**
     * Make sure processFormBeanConfigClass() returns an instance of the
     * correct class if the base config is using a custom class.
     *
     * @throws ServletException if a servlet exception is thrown
     */
    @Test
    public void testProcessFormBeanConfigClass()
        throws ServletException {
        CustomFormBeanConfig customBase = new CustomFormBeanConfig();

        customBase.setName("customBase");
        moduleConfig.addFormBeanConfig(customBase);

        FormBeanConfig customSub = new FormBeanConfig();

        customSub.setName("customSub");
        customSub.setExtends("customBase");
        customSub.setType("org.apache.struts.action.DynaActionForm");
        moduleConfig.addFormBeanConfig(customSub);

        FormBeanConfig result =
            actionServlet.processFormBeanConfigClass(customSub, moduleConfig);

        assertInstanceOf(CustomFormBeanConfig.class, result,
            "Incorrect class of form bean config");
        assertEquals(customSub.getName(), result.getName(), "Incorrect name");
        assertEquals(customSub.getType(), result.getType(), "Incorrect type");
        assertEquals(customSub.getExtends(), result.getExtends(),
            "Incorrect extends");
        assertEquals(customSub.isRestricted(), result.isRestricted(),
            "Incorrect 'restricted' value");

        assertSame(result, moduleConfig.findFormBeanConfig("customSub"),
            "Result was not registered in the module config");
    }

    /**
     * Make sure processFormBeanConfigClass() returns what it was given if the
     * form passed to it doesn't extend anything.
     *
     * @throws ServletException if a servlet exception is thrown
     */
    @Test
    public void testProcessFormBeanConfigClassNoExtends()
        throws ServletException {
        moduleConfig.addFormBeanConfig(baseFormBean);

        FormBeanConfig result = null;

        try {
            result =
                actionServlet.processFormBeanConfigClass(baseFormBean,
                    moduleConfig);
        } catch (UnavailableException e) {
            fail("An exception should not be thrown when there's nothing to do");
        }

        assertSame(baseFormBean, result,
            "Result should be the same as the input.");
    }

    /**
     * Make sure processFormBeanConfigClass() returns the same class instance
     * if the base config isn't using a custom class.
     *
     * @throws ServletException if a servlet exception is thrown
     */
    @Test
    public void testProcessFormBeanConfigClassSubFormCustomClass()
        throws ServletException {
        moduleConfig.addFormBeanConfig(baseFormBean);

        FormBeanConfig customSub = new FormBeanConfig();

        customSub.setName("customSub");
        customSub.setExtends("baseForm");
        moduleConfig.addFormBeanConfig(customSub);

        FormBeanConfig result =
            actionServlet.processFormBeanConfigClass(customSub, moduleConfig);

        assertSame(customSub, result,
            "The instance returned should be the param given it.");
    }

    /**
     * Make sure the code throws the correct exception when it can't create an
     * instance of the base config's custom class.
     */
    @Test
    @Disabled
    public void notestProcessFormBeanConfigClassError() {
        CustomFormBeanConfigArg customBase =
            new CustomFormBeanConfigArg("customBase");

        moduleConfig.addFormBeanConfig(customBase);

        FormBeanConfig customSub = new FormBeanConfig();

        customSub.setName("customSub");
        customSub.setExtends("customBase");
        moduleConfig.addFormBeanConfig(customSub);

        try {
            actionServlet.processFormBeanConfigClass(customSub, moduleConfig);
            fail("Exception should be thrown");
        } catch (UnavailableException e) {
            // success
        } catch (Exception e) {
            fail("Unexpected exception thrown.");
        }
    }

    /**
     * Test the case where the subform has already specified its own form bean
     * config class.  If the code still attempts to create a new instance, an
     * error will be thrown.
     */
    @Test
    public void testProcessFormBeanConfigClassOverriddenSubFormClass() {
        CustomFormBeanConfigArg customBase =
            new CustomFormBeanConfigArg("customBase");

        moduleConfig.addFormBeanConfig(customBase);

        FormBeanConfig customSub = new CustomFormBeanConfigArg("customSub");

        customSub.setExtends("customBase");
        moduleConfig.addFormBeanConfig(customSub);

        try {
            actionServlet.processFormBeanConfigClass(customSub, moduleConfig);
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    // -------------------------------------------------- ExceptionConfig Tests

    /**
     * Test that nothing fails if there are no extensions.
     */
    @Test
    public void testInitModuleExceptionConfigsNoExtends() {
        moduleConfig.addExceptionConfig(baseException);

        try {
            actionServlet.initModuleExceptionConfigs(moduleConfig);
        } catch (Exception e) {
            fail("Unexpected exception caught: " + e);
        }
    }

    /**
     * Test that initModuleExceptionConfigs does not throw an exception
     * when a handler with a null key is present.
     */
    @Test
    public void testInitModuleExceptionConfigsNullFormType() {
        ExceptionConfig handler = new ExceptionConfig();

        handler.setType("java.lang.NullPointerException");
        moduleConfig.addExceptionConfig(handler);

        try {
            actionServlet.initModuleExceptionConfigs(moduleConfig);
            // success
        } catch (UnavailableException e) {
            fail("Exception shouldn't have been thrown here.");
        } catch (Exception e) {
            fail("Unrecognized exception thrown: " + e);
        }
    }

    /**
     * Test that processExceptionExtension() calls processExtends()
     *
     * @throws ServletException if initialization cannot be performed.
     */
    @Test
    public void testProcessExceptionExtension()
        throws ServletException {
        CustomExceptionConfig handler = new CustomExceptionConfig();

        handler.setType("java.lang.NullPointerException");
        moduleConfig.addExceptionConfig(handler);
        actionServlet.processExceptionExtension(handler, moduleConfig, null);

        assertTrue(handler.processExtendsCalled,
            "processExtends() was not called");
    }

    /**
     * Make sure processExceptionConfigClass() returns an instance of the
     * correct class if the base config is using a custom class.
     *
     * @throws ServletException if initialization cannot be performed.
     */
    @Test
    public void testProcessExceptionConfigClass()
        throws ServletException {
        CustomExceptionConfig customBase = new CustomExceptionConfig();

        customBase.setType("java.lang.NullPointerException");
        customBase.setKey("msg.exception.npe");
        moduleConfig.addExceptionConfig(customBase);

        ExceptionConfig customSub = new ExceptionConfig();

        customSub.setType("java.lang.IllegalStateException");
        customSub.setExtends("java.lang.NullPointerException");
        moduleConfig.addExceptionConfig(customSub);

        ExceptionConfig result =
            actionServlet.processExceptionConfigClass(customSub, moduleConfig,
                null);

        assertInstanceOf(CustomExceptionConfig.class, result,
            "Incorrect class of exception config");
        assertEquals(customSub.getType(), result.getType(), "Incorrect type");
        assertEquals(customSub.getKey(), result.getKey(), "Incorrect key");
        assertEquals(customSub.getExtends(), result.getExtends(),
            "Incorrect extends");

        assertSame(result, moduleConfig.findExceptionConfig("java.lang.IllegalStateException"),
            "Result was not registered in the module config");
    }

    /**
     * Make sure processExceptionConfigClass() returns what it was given if
     * the handler passed to it doesn't extend anything.
     *
     * @throws ServletException if initialization cannot be performed.
     */
    @Test
    public void testProcessExceptionConfigClassNoExtends()
        throws ServletException {
        moduleConfig.addExceptionConfig(baseException);

        ExceptionConfig result = null;

        try {
            result =
                actionServlet.processExceptionConfigClass(baseException,
                    moduleConfig, null);
        } catch (UnavailableException e) {
            fail("An exception should not be thrown when there's nothing to do");
        }

        assertSame(baseException, result,
            "Result should be the same as the input.");
    }

    /**
     * Make sure processExceptionConfigClass() returns the same class instance
     * if the base config isn't using a custom class.
     *
     * @throws ServletException if initialization cannot be performed.
     */
    @Test
    public void testProcessExceptionConfigClassSubConfigCustomClass()
        throws ServletException {
        moduleConfig.addExceptionConfig(baseException);

        ExceptionConfig customSub = new ExceptionConfig();

        customSub.setType("java.lang.IllegalStateException");
        customSub.setExtends("java.lang.NullPointerException");
        moduleConfig.addExceptionConfig(customSub);

        ExceptionConfig result =
            actionServlet.processExceptionConfigClass(customSub, moduleConfig,
                null);

        assertSame(customSub, result,
            "The instance returned should be the param given it.");
    }

    /**
     * Make sure the code throws the correct exception when it can't create an
     * instance of the base config's custom class.
     */
    @Test
    @Disabled
    public void notestProcessExceptionConfigClassError() {
        ExceptionConfig customBase =
            new CustomExceptionConfigArg("java.lang.NullPointerException");

        moduleConfig.addExceptionConfig(customBase);

        ExceptionConfig customSub = new ExceptionConfig();

        customSub.setType("java.lang.IllegalStateException");
        customSub.setExtends("java.lang.NullPointerException");
        moduleConfig.addExceptionConfig(customSub);

        try {
            actionServlet.processExceptionConfigClass(customSub, moduleConfig,
                null);
            fail("Exception should be thrown");
        } catch (UnavailableException e) {
            // success
        } catch (Exception e) {
            fail("Unexpected exception thrown.");
        }
    }

    /**
     * Test the case where the subconfig has already specified its own config
     * class.  If the code still attempts to create a new instance, an error
     * will be thrown.
     */
    @Test
    public void testProcessExceptionConfigClassOverriddenSubFormClass() {
        moduleConfig.addExceptionConfig(baseException);

        ExceptionConfig customSub =
            new CustomExceptionConfigArg("java.lang.IllegalStateException");

        customSub.setExtends("java.lang.NullPointerException");
        moduleConfig.addExceptionConfig(customSub);

        try {
            actionServlet.processExceptionConfigClass(customSub, moduleConfig,
                null);
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    // ---------------------------------------------------- ForwardConfig Tests

    /**
     * Test that nothing fails if there are no extensions.
     */
    @Test
    public void testInitModuleForwardConfigsNoExtends() {
        moduleConfig.addForwardConfig(baseForward);

        try {
            actionServlet.initModuleForwards(moduleConfig);
        } catch (Exception e) {
            fail("Unexpected exception caught: " + e);
        }
    }

    /**
     * Test that initModuleForwards throws an exception when a forward with a
     * null path is present.
     */
    @Test
    public void testInitModuleForwardsNullFormType() {
        ActionForward forward = new ActionForward("success", null, false);

        moduleConfig.addForwardConfig(forward);

        try {
            actionServlet.initModuleForwards(moduleConfig);
            fail("An exception should've been thrown here.");
        } catch (UnavailableException e) {
            // success
        } catch (Exception e) {
            fail("Unrecognized exception thrown: " + e);
        }
    }

    /**
     * Test that processForwardExtension() calls processExtends()
     *
     * @throws ServletException if initialization cannot be performed.
     */
    @Test
    public void testProcessForwardExtension()
        throws ServletException {
        CustomForwardConfig forward =
            new CustomForwardConfig("forward", "/forward.jsp");

        moduleConfig.addForwardConfig(forward);
        actionServlet.processForwardExtension(forward, moduleConfig, null);

        assertTrue(forward.processExtendsCalled,
            "processExtends() was not called");
    }

    /**
     * Make sure processForwardConfigClass() returns an instance of the
     * correct class if the base config is using a custom class.
     *
     * @throws ServletException if initialization cannot be performed.
     */
    @Test
    public void testProcessForwardConfigClass()
        throws ServletException {
        CustomForwardConfig customBase =
            new CustomForwardConfig("success", "/success.jsp");

        moduleConfig.addForwardConfig(customBase);

        ActionForward customSub = new ActionForward();

        customSub.setName("failure");
        customSub.setExtends("success");
        moduleConfig.addForwardConfig(customSub);

        ForwardConfig result =
            actionServlet.processForwardConfigClass(customSub, moduleConfig,
                null);

        assertInstanceOf(CustomForwardConfig.class, result,
            "Incorrect class of forward config");
        assertEquals(customSub.getName(), result.getName(), "Incorrect name");
        assertEquals(customSub.getPath(), result.getPath(), "Incorrect path");
        assertEquals(customSub.getExtends(), result.getExtends(),
            "Incorrect extends");

        assertSame(result, moduleConfig.findForwardConfig("failure"),
            "Result was not registered in the module config");
    }

    /**
     * Make sure processForwardConfigClass() returns what it was given if the
     * forward passed to it doesn't extend anything.
     *
     * @throws ServletException if initialization cannot be performed.
     */
    @Test
    public void testProcessForwardConfigClassNoExtends()
        throws ServletException {
        moduleConfig.addForwardConfig(baseForward);

        ForwardConfig result = null;

        try {
            result =
                actionServlet.processForwardConfigClass(baseForward,
                    moduleConfig, null);
        } catch (UnavailableException e) {
            fail("An exception should not be thrown when there's nothing to do");
        }

        assertSame(baseForward, result,
            "Result should be the same as the input.");
    }

    /**
     * Make sure processForwardConfigClass() returns the same class instance
     * if the base config isn't using a custom class.
     *
     * @throws ServletException if initialization cannot be performed.
     */
    @Test
    public void testProcessForwardConfigClassSubConfigCustomClass()
        throws ServletException {
        moduleConfig.addForwardConfig(baseForward);

        ForwardConfig customSub = new ActionForward();

        customSub.setName("failure");
        customSub.setExtends("success");
        moduleConfig.addForwardConfig(customSub);

        ForwardConfig result =
            actionServlet.processForwardConfigClass(customSub, moduleConfig,
                null);

        assertSame(customSub, result,
            "The instance returned should be the param given it.");
    }

    /**
     * Make sure the code throws the correct exception when it can't create an
     * instance of the base config's custom class.
     */
    @Test
    @Disabled
    public void notestProcessForwardConfigClassError() {
        ForwardConfig customBase =
            new CustomForwardConfigArg("success", "/success.jsp");

        moduleConfig.addForwardConfig(customBase);

        ForwardConfig customSub = new ActionForward();

        customSub.setName("failure");
        customSub.setExtends("success");
        moduleConfig.addForwardConfig(customSub);

        try {
            actionServlet.processForwardConfigClass(customSub, moduleConfig,
                null);
            fail("Exception should be thrown");
        } catch (UnavailableException e) {
            // success
        } catch (Exception e) {
            fail("Unexpected exception thrown.");
        }
    }

    /**
     * Test the case where the subconfig has already specified its own config
     * class.  If the code still attempts to create a new instance, an error
     * will be thrown.
     */
    @Test
    public void testProcessForwardConfigClassOverriddenSubConfigClass() {
        moduleConfig.addForwardConfig(baseForward);

        ForwardConfig customSub =
            new CustomForwardConfigArg("failure", "/failure.jsp");

        customSub.setExtends("success");
        moduleConfig.addForwardConfig(customSub);

        try {
            actionServlet.processForwardConfigClass(customSub, moduleConfig,
                null);
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    // --------------------------------------------------- ActionConfig Tests

    /**
     * Test that nothing fails if there are no extensions.
     */
    @Test
    public void testInitModuleActionConfigsNoExtends() {
        moduleConfig.addActionConfig(baseAction);

        try {
            actionServlet.initModuleActions(moduleConfig);
        } catch (Exception e) {
            fail("Unexpected exception caught: " + e);
        }
    }

    /**
     * Test that processActionConfigExtension() calls processExtends()
     *
     * @throws ServletException if initialization cannot be performed.
     */
    @Test
    public void testProcessActionExtension()
        throws ServletException {
        CustomActionConfig action = new CustomActionConfig("/action");

        moduleConfig.addActionConfig(action);
        actionServlet.processActionConfigExtension(action, moduleConfig);

        assertTrue(action.processExtendsCalled,
            "processExtends() was not called");
    }

    /**
     * Test that an ActionConfig's ForwardConfig can inherit from a
     * global ForwardConfig.
     *
     * @throws ServletException if initialization cannot be performed.
     */
    @Test
    public void testProcessActionExtensionWithForwardConfig()
        throws ServletException {
        ForwardConfig forwardConfig = new ForwardConfig();
        forwardConfig.setName("sub");
        forwardConfig.setExtends("success");
        baseAction.addForwardConfig(forwardConfig);

        moduleConfig.addActionConfig(baseAction);
        moduleConfig.addForwardConfig(baseForward);
        actionServlet.processActionConfigExtension(baseAction, moduleConfig);

        forwardConfig = baseAction.findForwardConfig("sub");

        assertEquals(baseForward.getPath(), forwardConfig.getPath(),
            "'sub' forward's inheritance was not processed.");
    }

    /**
     * Test that an ActionConfig's ExceptionConfig can inherit from a
     * global ExceptionConfig.
     *
     * @throws ServletException if initialization cannot be performed.
     */
    @Test
    public void testProcessActionExtensionWithExceptionConfig()
        throws ServletException {
        ExceptionConfig exceptionConfig = new ExceptionConfig();
        exceptionConfig.setType("SomeException");
        exceptionConfig.setExtends("java.lang.NullPointerException");
        baseAction.addExceptionConfig(exceptionConfig);

        moduleConfig.addActionConfig(baseAction);
        moduleConfig.addExceptionConfig(baseException);
        actionServlet.processActionConfigExtension(baseAction, moduleConfig);

        exceptionConfig = baseAction.findExceptionConfig("SomeException");

        assertEquals(baseException.getKey(), exceptionConfig.getKey(),
            "SomeException's inheritance was not processed.");
    }

    /**
     * Make sure processActionConfigClass() returns an instance of the correct
     * class if the base config is using a custom class.
     *
     * @throws ServletException if initialization cannot be performed.
     */
    @Test
    public void testProcessActionConfigClass()
        throws ServletException {
        CustomActionConfig customBase = new CustomActionConfig("/base");

        moduleConfig.addActionConfig(customBase);

        ActionMapping customSub = new ActionMapping();

        customSub.setPath("/sub");
        customSub.setExtends("/base");
        moduleConfig.addActionConfig(customSub);

        ActionConfig result =
            actionServlet.processActionConfigClass(customSub, moduleConfig);

        assertInstanceOf(CustomActionConfig.class, result,
            "Incorrect class of action config");
        assertEquals(customSub.getPath(), result.getPath(), "Incorrect path");
        assertEquals(customSub.getExtends(), result.getExtends(),
            "Incorrect extends");

        assertSame(result, moduleConfig.findActionConfig("/sub"),
            "Result was not registered in the module config");
    }

    /**
     * Make sure processActionConfigClass() returns what it was given if the
     * action passed to it doesn't extend anything.
     *
     * @throws ServletException if initialization cannot be performed.
     */
    @Test
    public void testProcessActionConfigClassNoExtends()
        throws ServletException {
        moduleConfig.addActionConfig(baseAction);

        ActionConfig result = null;

        try {
            result =
                actionServlet.processActionConfigClass(baseAction, moduleConfig);
        } catch (UnavailableException e) {
            fail("An exception should not be thrown here");
        }

        assertSame(baseAction, result, "Result should be the same as the input.");
    }

    /**
     * Make sure processActionConfigClass() returns the same class instance if
     * the base config isn't using a custom class.
     *
     * @throws ServletException if initialization cannot be performed.
     */
    @Test
    public void testProcessActionConfigClassSubConfigCustomClass() throws ServletException {
        moduleConfig.addActionConfig(baseAction);

        ActionConfig customSub = new ActionMapping();

        customSub.setPath("/sub");
        customSub.setExtends("/index");
        moduleConfig.addActionConfig(customSub);

        ActionConfig result =
            actionServlet.processActionConfigClass(customSub, moduleConfig);

        assertSame(customSub, result,
            "The instance returned should be the param given it.");
    }

    /**
     * Make sure the code throws the correct exception when it can't create an
     * instance of the base config's custom class.
     */
    @Test
    @Disabled
    public void notestProcessActionConfigClassError() {
        ActionConfig customBase = new CustomActionConfigArg("/index");

        moduleConfig.addActionConfig(customBase);

        ActionConfig customSub = new ActionMapping();

        customSub.setPath("/sub");
        customSub.setExtends("/index");
        moduleConfig.addActionConfig(customSub);

        try {
            actionServlet.processActionConfigClass(customSub, moduleConfig);
            fail("Exception should be thrown");
        } catch (UnavailableException e) {
            // success
        } catch (Exception e) {
            fail("Unexpected exception thrown.");
        }
    }

    /**
     * Test the case where the subconfig has already specified its own config
     * class.  If the code still attempts to create a new instance, an error
     * will be thrown.
     */
    @Test
    public void testProcessActionConfigClassOverriddenSubConfigClass() {
        moduleConfig.addActionConfig(baseAction);

        ActionConfig customSub = new CustomActionConfigArg("/sub");

        customSub.setExtends("/index");
        moduleConfig.addActionConfig(customSub);

        try {
            actionServlet.processActionConfigClass(customSub, moduleConfig);
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    /**
     * Used for testing custom FormBeanConfig classes.
     */
    public static class CustomFormBeanConfig extends FormBeanConfig {
        private static final long serialVersionUID = -573022017310455853L;

        public boolean processExtendsCalled = false;

        public CustomFormBeanConfig() {
            super();
        }

        /**
         * Set a flag so we know this method was called.
         */
        public void processExtends(ModuleConfig moduleConfig)
            throws ClassNotFoundException, IllegalAccessException,
                InstantiationException {
            processExtendsCalled = true;
        }
    }

    /**
     * Used to test cases where the subclass cannot be created with a no-arg
     * constructor.
     */
    private class CustomFormBeanConfigArg extends FormBeanConfig {
        private static final long serialVersionUID = -455258948780624575L;

        CustomFormBeanConfigArg(String name) {
            super();
            setName(name);
        }
    }

    /**
     * Used for testing custom ExceptionConfig classes.
     */
    public static class CustomExceptionConfig extends ExceptionConfig {
        private static final long serialVersionUID = 3648200258159769023L;

        public boolean processExtendsCalled = false;

        public CustomExceptionConfig() {
            super();
        }

        /**
         * Set a flag so we know this method was called.
         */
        public void processExtends(ModuleConfig moduleConfig,
            ActionConfig actionConfig)
            throws ClassNotFoundException, IllegalAccessException,
                InstantiationException {
            processExtendsCalled = true;
        }
    }

    /**
     * Used to test cases where the subclass cannot be created with a no-arg
     * constructor.
     */
    private class CustomExceptionConfigArg extends ExceptionConfig {
        private static final long serialVersionUID = 4453990369781006902L;

        CustomExceptionConfigArg(String type) {
            super();
            setType(type);
        }
    }

    /**
     * Used for testing custom ForwardConfig classes.
     */
    public static class CustomForwardConfig extends ForwardConfig {
        private static final long serialVersionUID = -993693513159522038L;

        public boolean processExtendsCalled = false;

        public CustomForwardConfig() {
            super();
        }

        public CustomForwardConfig(String name, String path) {
            super(name, path, false);
        }

        /**
         * Set a flag so we know this method was called.
         */
        public void processExtends(ModuleConfig moduleConfig,
            ActionConfig actionConfig)
            throws ClassNotFoundException, IllegalAccessException,
                InstantiationException {
            processExtendsCalled = true;
        }
    }

    /**
     * Used to test cases where the subclass cannot be created with a no-arg
     * constructor.
     */
    private class CustomForwardConfigArg extends ForwardConfig {
        private static final long serialVersionUID = -2880188065203315383L;

        CustomForwardConfigArg(String name, String path) {
            super();
            setName(name);
            setPath(path);
        }
    }

    /**
     * Used for testing custom ActionConfig classes.
     */
    public static class CustomActionConfig extends ActionConfig {
        private static final long serialVersionUID = -7093772452618953476L;

        public boolean processExtendsCalled = false;

        public CustomActionConfig() {
            super();
        }

        public CustomActionConfig(String path) {
            super();
            setPath(path);
        }

        /**
         * Set a flag so we know this method was called.
         */
        public void processExtends(ModuleConfig moduleConfig)
            throws ClassNotFoundException, IllegalAccessException,
                InstantiationException {
            processExtendsCalled = true;
        }
    }

    /**
     * Used to test cases where the subclass cannot be created with a no-arg
     * constructor.
     */
    private class CustomActionConfigArg extends ActionConfig {
        private static final long serialVersionUID = -2205204932709330848L;

        CustomActionConfigArg(String path) {
            super();
            setPath(path);
        }
    }

    // [...]
}
