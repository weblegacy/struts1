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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.TestMockBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ActionConfigMatcher}.
 *
 * @version $Rev$ $Date$
 */
public class TestActionConfigMatcher extends TestMockBase {

    // ----------------------------------------------------- Instance Variables
    // ----------------------------------------------------- Setup and Teardown
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

    // ------------------------------------------------------- Individual Tests
    // ---------------------------------------------------------- match()
    @Test
    public void testNoMatch() {
        ActionConfig[] configs = new ActionConfig[1];
        ActionConfig mapping = buildActionConfig("/foo");

        configs[0] = mapping;

        ActionConfigMatcher matcher = new ActionConfigMatcher(configs);

        assertNull(matcher.match("/test"), "ActionConfig shouldn't be matched");
    }

    /**
     * Verifies that a match succeeds when the substituted value contains a
     * placeholder key.
     * <p>
     * See STR-3169.
     *
     * @since Struts 1.3.11
     */
    @Test
    public void testMatchWithKeyInPath() {
        ActionMapping[] mapping = new ActionMapping[1];
        mapping[0] = new ActionMapping();
        mapping[0].setPath("/page-*");

        ActionConfigMatcher matcher = new ActionConfigMatcher(mapping);
        ActionConfig matched = matcher.match("/page-{0}");

        assertNotNull(matched, "ActionConfig should be matched");
        assertEquals("/page-{0}", matched.getPath(), "Path hasn't been replaced");
    }

    /**
     * Verifies that an infinite loop is prevented and substitution is skipped when
     * the substituted value equals the placeholder key.
     * <p>
     * See STR-3169.
     *
     * @since Struts 1.3.11
     * @see #testMatchWithSubstitutionEqualPlaceholderKey1()
     */
    @Test
    public void testMatchWithSubstitutionEqualPlaceholderKey0() {
        ActionMapping[] mapping = new ActionMapping[1];
        mapping[0] = new ActionMapping();
        mapping[0].setPath("/page-*");
        mapping[0].setParameter("{0}");

        ActionConfigMatcher matcher = new ActionConfigMatcher(mapping);
        ActionConfig matched = matcher.match("/page-{1}");

        assertNull(matched, "ActionConfig should not be matched");
    }

    /**
     * Verifies that an infinite loop is prevented and substitution is skipped when
     * the substituted value equals the placeholder key.
     * <p>
     * See STR-3169.
     *
     * @since Struts 1.3.11
     * @see #testMatchWithSubstitutionEqualPlaceholderKey0()
     */
    @Test
    public void testMatchWithSubstitutionEqualPlaceholderKey1() {
        ActionMapping[] mapping = new ActionMapping[1];
        mapping[0] = new ActionMapping();
        mapping[0].setPath("/page-*");
        mapping[0].setParameter("{1}");

        ActionConfigMatcher matcher = new ActionConfigMatcher(mapping);
        ActionConfig matched = matcher.match("/page-{1}");

        assertNull(matched, "ActionConfig should not be matched");
    }

    /**
     * Verifies that an infinite loop is prevented and substitution is skipped when
     * the the placeholder key is contained within the substituted value.
     * <p>
     * See STR-3169.
     *
     * @since Struts 1.3.11
     */
    @Test
    public void testMatchWhenSubstitutionContainsPlaceholderKey() {
        ActionMapping[] mapping = new ActionMapping[1];
        mapping[0] = new ActionMapping();
        mapping[0].setPath("/page-*");
        mapping[0].setParameter("{1}");

        ActionConfigMatcher matcher = new ActionConfigMatcher(mapping);
        ActionConfig matched = matcher.match("/page-{0}");

        assertNull(matched, "ActionConfig should not be matched");
    }

    @Test
    public void testNoWildcardMatch() {
        ActionConfig[] configs = new ActionConfig[1];
        ActionConfig mapping = buildActionConfig("/fooBar");

        configs[0] = mapping;

        ActionConfigMatcher matcher = new ActionConfigMatcher(configs);

        assertNull(matcher.match("/fooBar"), "ActionConfig shouldn't be matched");
    }

    @Test
    public void testShouldMatch() {
        ActionConfig[] configs = new ActionConfig[1];
        ActionConfig mapping = buildActionConfig("/foo*");

        configs[0] = mapping;

        ActionConfigMatcher matcher = new ActionConfigMatcher(configs);

        ActionConfig matched = matcher.match("/fooBar");

        assertNotNull(matched, "ActionConfig should be matched");
        assertEquals(2, matched.findForwardConfigs().length, "ActionConfig should have two action forward");
        assertEquals(2, matched.findExceptionConfigs().length, "ActionConfig should have two exception forward");
        assertEquals(2, matched.getProperties().size(), "ActionConfig should have properties");
    }

    @Test
    public void testCheckSubstitutionsMatch() {
        ActionConfig[] configs = new ActionConfig[1];
        ActionConfig mapping = buildActionConfig("/foo*");

        configs[0] = mapping;

        ActionConfigMatcher matcher = new ActionConfigMatcher(configs);
        ActionConfig m = matcher.match("/fooBar");

        assertEquals("name,Bar", m.getName(), "Name hasn't been replaced");
        assertEquals("/fooBar", m.getPath(), "Path hasn't been replaced");
        assertEquals("request", m.getScope(), "Scope isn't correct");
        assertFalse(m.getUnknown(), "Unknown isn't correct");
        assertTrue(m.getValidate(), "Validate isn't correct");

        assertEquals("foo,Bar", m.getPrefix(), "Prefix hasn't been replaced");
        assertEquals("bar,Bar", m.getSuffix(), "Suffix hasn't been replaced");
        assertEquals("foo.bar.BarAction", m.getType(), "Type hasn't been replaced");
        assertEquals("public,Bar", m.getRoles(), "Roles hasn't been replaced");
        assertEquals("param,Bar", m.getParameter(), "Parameter hasn't been replaced");
        assertEquals("attrib,Bar", m.getAttribute(), "Attribute hasn't been replaced");
        assertEquals("fwd,Bar", m.getForward(), "Forward hasn't been replaced");
        assertEquals("include,Bar", m.getInclude(), "Include hasn't been replaced");
        assertEquals("input,Bar", m.getInput(), "Input hasn't been replaced");

        assertEquals("testBar", m.getProperty("testprop2"), "ActionConfig property not replaced");

        ForwardConfig[] fConfigs = m.findForwardConfigs();
        boolean found = false;

        for (int x = 0; x < fConfigs.length; x++) {
            ForwardConfig cfg = fConfigs[x];

            if ("name".equals(cfg.getName())) {
                found = true;
                assertEquals("path,Bar", cfg.getPath(), "Path hasn't been replaced");
                assertEquals("bar,Bar", cfg.getProperty("foo"), "Property foo hasn't been replaced");
                assertEquals("modBar", cfg.getModule(), "Module hasn't been replaced");
            }
        }

        assertTrue(found, "The forward config 'name' cannot be found");
    }

    @Test
    public void testCheckMultipleSubstitutions() {
        ActionMapping[] mapping = new ActionMapping[1];

        mapping[0] = new ActionMapping();
        mapping[0].setPath("/foo*");
        mapping[0].setName("name,{1}-{1}");

        ActionConfigMatcher matcher = new ActionConfigMatcher(mapping);
        ActionConfig m = matcher.match("/fooBar");

        assertEquals("name,Bar-Bar", m.getName(), "Name hasn't been replaced correctly: " + m.getName());
    }

    private ActionConfig buildActionConfig(String path) {
        ActionMapping mapping = new ActionMapping();

        mapping.setName("name,{1}");
        mapping.setPath(path);
        mapping.setScope("request");
        mapping.setUnknown(false);
        mapping.setValidate(true);

        mapping.setPrefix("foo,{1}");
        mapping.setSuffix("bar,{1}");

        mapping.setType("foo.bar.{1}Action");
        mapping.setRoles("public,{1}");
        mapping.setParameter("param,{1}");
        mapping.setAttribute("attrib,{1}");
        mapping.setForward("fwd,{1}");
        mapping.setInclude("include,{1}");
        mapping.setInput("input,{1}");

        ForwardConfig cfg = new ActionForward();

        cfg.setName("name");
        cfg.setPath("path,{1}");
        cfg.setModule("mod{1}");
        cfg.setProperty("foo", "bar,{1}");
        mapping.addForwardConfig(cfg);

        cfg = new ActionForward();
        cfg.setName("name2");
        cfg.setPath("path2");
        cfg.setModule("mod{1}");
        mapping.addForwardConfig(cfg);

        ExceptionConfig excfg = new ExceptionConfig();

        excfg.setKey("foo");
        excfg.setType("foo.Bar");
        excfg.setPath("path");
        mapping.addExceptionConfig(excfg);

        excfg = new ExceptionConfig();
        excfg.setKey("foo2");
        excfg.setType("foo.Bar2");
        excfg.setPath("path2");
        mapping.addExceptionConfig(excfg);

        mapping.setProperty("testprop", "testval");
        mapping.setProperty("testprop2", "test{1}");

        mapping.freeze();

        return mapping;
    }
}
