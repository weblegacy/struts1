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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@code org.apache.struts.config} package.
 *
 * @version $Rev$ $Date$
 */
public class TestModuleConfig {
    // ----------------------------------------------------- Instance Variables

    /**
     * The ModuleConfig we are testing.
     */
    protected ModuleConfig config = null;

    // --------------------------------------------------------- Public Methods

    /**
     * Set up instance variables required by this test case.
     */
    @BeforeEach
    public void setUp() {
        ModuleConfigFactory factoryObject = ModuleConfigFactory.createFactory();

        config = factoryObject.createModuleConfig("");
    }

    /**
     * Tear down instance variables required by this test case.
     */
    @AfterEach
    public void tearDown() {
        config = null;
    }

    // ------------------------------------------------ Individual Test Methods
    private void parseConfig(String publicId, String entityURL,
        String strutsConfig) {
        // Prepare a Digester for parsing a struts-config.xml file
        Digester digester = new Digester();

        digester.push(config);
        digester.setNamespaceAware(true);
        digester.setValidating(true);
        digester.addRuleSet(new ConfigRuleSet());
        digester.register(publicId,
            this.getClass().getResource(entityURL).toString());

        // Parse the test struts-config.xml file
        try (InputStream input = this.getClass().getResourceAsStream(strutsConfig)) {
            assertNotNull(input, "Got an input stream for " + strutsConfig);
            digester.parse(input);
        } catch (Throwable t) {
            t.printStackTrace(System.out);
            fail("Parsing threw exception:  " + t);
        }
    }

    /**
     * Test parsing of a struts-config.xml file.
     */
    @Test
    public void testParse() {
        testParseBase("-//Apache Software Foundation//DTD Struts Configuration 1.2//EN",
            "/org/apache/struts/resources/struts-config_1_2.dtd",
            "/org/apache/struts/config/struts-config.xml");
    }

    @Test
    public void testParse1_1() {
        testParseBase("-//Apache Software Foundation//DTD Struts Configuration 1.1//EN",
            "/org/apache/struts/resources/struts-config_1_1.dtd",
            "/org/apache/struts/config/struts-config-1.1.xml");
    }

    public void testParseBase(String publicId, String entityURL,
        String strutsConfig) {
        parseConfig(publicId, entityURL, strutsConfig);

        // Perform assertion tests on the parsed information
        FormBeanConfig[] fbcs = config.findFormBeanConfigs();

        assertNotNull(fbcs, "Found our form bean configurations");
        assertEquals(3, fbcs.length, "Found three form bean configurations");

        ForwardConfig[] fcs = config.findForwardConfigs();

        assertNotNull(fcs, "Found our forward configurations");
        assertEquals(3, fcs.length, "Found three forward configurations");

        ActionConfig logon = config.findActionConfig("/logon");

        assertNotNull(logon, "Found logon action configuration");
        assertEquals("logonForm", logon.getName(),
            "Found correct logon configuration");
    }

    /**
     * Tests a struts-config.xml that contains a custom mapping and property.
     */
    @Test
    public void testCustomMappingParse() {
        // Prepare a Digester for parsing a struts-config.xml file
        testCustomMappingParseBase("-//Apache Software Foundation//DTD Struts Configuration 1.2//EN",
            "/org/apache/struts/resources/struts-config_1_2.dtd",
            "/org/apache/struts/config/struts-config-custom-mapping.xml");
    }

    /**
     * Tests a struts-config.xml that contains a custom mapping and property.
     */
    @Test
    public void testCustomMappingParse1_1() {
        // Prepare a Digester for parsing a struts-config.xml file
        testCustomMappingParseBase("-//Apache Software Foundation//DTD Struts Configuration 1.1//EN",
            "/org/apache/struts/resources/struts-config_1_1.dtd",
            "/org/apache/struts/config/struts-config-custom-mapping-1.1.xml");
    }

    /**
     * Tests a struts-config.xml that contains a custom mapping and property.
     */
    private void testCustomMappingParseBase(String publicId, String entityURL,
        String strutsConfig) {
        parseConfig(publicId, entityURL, strutsConfig);

        // Perform assertion tests on the parsed information
        CustomMappingTest map =
            (CustomMappingTest) config.findActionConfig("/editRegistration");

        assertNotNull(map, "Cannot find editRegistration mapping");
        assertTrue(map.getPublic(),
            "The custom mapping attribute has not been set");
    }

    /**
     * Test order of action mappings defined perserved.
     */
    @Test
    public void testPreserveActionMappingsOrder() {
        parseConfig("-//Apache Software Foundation//DTD Struts Configuration 1.2//EN",
            "/org/apache/struts/resources/struts-config_1_2.dtd",
            "/org/apache/struts/config/struts-config.xml");

        String[] paths =
            new String[] {
                "/editRegistration", "/editSubscription", "/logoff", "/logon",
                "/saveRegistration", "/saveSubscription", "/tour"
            };

        ActionConfig[] actions = config.findActionConfigs();

        for (int x = 0; x < paths.length; x++) {
            assertEquals(paths[x], actions[x].getPath(),
                "Action config out of order:" + actions[x].getPath());
        }
    }
}
