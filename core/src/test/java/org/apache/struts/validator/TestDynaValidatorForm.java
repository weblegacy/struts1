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
package org.apache.struts.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.TestMockBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link DynaValidatorForm} class.
 *
 * @version $Rev$ $Date$
 */
public class TestDynaValidatorForm extends TestMockBase {

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

    /**
     * Test value of determine page.
     */
    @Test
    public void testDeterminePage01() {
        DynaValidatorForm validatorForm = new DynaValidatorForm();
        ActionMapping mapping = new ActionMapping();

        mapping.setAcceptPage(null);
        validatorForm.setPage(-1);
        int page = validatorForm.determinePage(mapping, null);

        assertEquals(Integer.MAX_VALUE, page);
    }

    /**
     * Test value of determine page.
     */
    @Test
    public void testDeterminePage02() {
        DynaValidatorForm validatorForm = new DynaValidatorForm();
        ActionMapping mapping = new ActionMapping();

        mapping.setAcceptPage(-1);
        validatorForm.setPage(-1);
        int page = validatorForm.determinePage(mapping, null);

        assertEquals(-1, page);
    }

    /**
     * Test value of determine page.
     */
    @Test
    public void testDeterminePage03() {
        DynaValidatorForm validatorForm = new DynaValidatorForm();
        ActionMapping mapping = new ActionMapping();

        mapping.setAcceptPage(-1);
        validatorForm.setPage(0);
        int page = validatorForm.determinePage(mapping, null);

        assertEquals(0, page);
    }

    /**
     * Test value of determine page.
     */
    @Test
    public void testDeterminePage04() {
        DynaValidatorForm validatorForm = new DynaValidatorForm();
        ActionMapping mapping = new ActionMapping();

        mapping.setAcceptPage(-1);
        validatorForm.setPage(1);
        int page = validatorForm.determinePage(mapping, null);

        assertEquals(1, page);
    }

}