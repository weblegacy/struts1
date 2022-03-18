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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.StringReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.validator.validwhen.ValidWhenLexer;
import org.apache.struts.validator.validwhen.ValidWhenParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ValidWhen Parser/Lexer.
 */
public class TestValidWhen {
    /**
     * All logging goes through this logger
     */
    private static Log log = LogFactory.getLog(TestValidWhen.class);
    protected PojoBean testBean;

    @BeforeEach
    public void setUp() {
        testBean = new PojoBean(123, 789);
        testBean.setStringValue1("ABC");
        testBean.setStringValue2(null);
        testBean.setBeans(new PojoBean[] {
                new PojoBean(11, 12), new PojoBean(21, 22), new PojoBean(31, 42),
                new PojoBean(41, 52), new PojoBean(51, 62)
            });
        testBean.setMapped("testKey", "mappedValue");
    }

    @AfterEach
    public void tearDown() {
        testBean = null;
    }

    /**
     * Test Operators.
     */
    @Test
    public void testProperty() {
        // *this*
        doParse("(*this* == 123)", testBean, 0, "intValue1", true);

        // Named property
        doParse("(intValue2 == 789)", testBean, 0, "intValue1", true);

        // Indexed Property
        doParse("(beans[].intValue1 == 21)", testBean, 1, "intValue1", true);
        doParse("(beans[1].intValue1 == 21)", testBean, 4, "intValue1", true);

        // Mapped Property - *** NOT SUPPORTED ***
        // doParse("(mapped(mappedValue) == 'testKey')", testBean , 0, "mappedValue", true);
    }

    /**
     * Test Operators.
     */
    @Test
    public void testOperators() {
        // Equal
        doParse("(*this* == 123)", testBean, 0, "intValue1", true);

        // Not Equal
        doParse("(*this* != 456)", testBean, 0, "intValue1", true);

        // Less Than
        doParse("(*this* < 456)", testBean, 0, "intValue1", true);

        // Greater Than
        doParse("(*this* > 100)", testBean, 0, "intValue1", true);

        // Less Than Equal
        doParse("(*this* <= 123)", testBean, 0, "intValue1", true);

        // Greater Than Equal
        doParse("(*this* >= 123)", testBean, 0, "intValue1", true);
    }

    /**
     * Test String values.
     */
    @Test
    public void testString() {
        doParse("(*this* != '--')", "XX", 0, "stringValue1", true);
        doParse("(*this* == '--')", "--", 0, "stringValue1", true);

        String testValue = "dsgUOLMdLsdL";

        // single quote
        doParse("(*this* == '" + testValue + "')", testValue, 0,
            "stringValue1", true);

        // double quote
        doParse("(*this* == \"" + testValue + "\")", testValue, 0,
            "stringValue1", true);

        // obscure characters
        doParse("(*this* == \":\")", ":", 0,
            "stringValue1", true);
        doParse("(*this* == \"foo:bar\")", "foo:bar", 0,
            "stringValue1", true);
    }

    /**
     * Test Numeric values.
     */
    @Test
    public void testNumeric() {
        // Test Zero
        PojoBean numberBean = new PojoBean(0, -50);

        doParse("(intValue1 == 0)", numberBean, 0, "intValue1", true);
        doParse("(intValue2 != 0)", numberBean, 0, "intValue2", true);
        doParse("(integerValue1 == 0)", numberBean, 0, "integerValue1", true);
        doParse("(integerValue2 != 0)", numberBean, 0, "integerValue2", true);

        // int
        doParse("(intValue1 == 123)", testBean, 0, "intValue1", true);

        // Integer
        doParse("(integerValue1 == 123)", testBean, 0, "integerValue1", true);

        // Negative Numbers
        doParse("((intValue2 < -10)     and (intValue2 > -60))", numberBean, 0,
            "intValue2", true);
        doParse("((integerValue2 < -10) and (integerValue2 > -60))",
            numberBean, 0, "integerValue2", true);

        // Hex
        doParse("(integerValue1 == 0x7B)", testBean, 0, "integerValue1", true);

        // Octal
        doParse("(integerValue1 == 0173)", testBean, 0, "integerValue1", true);

        // Test 'String' numbers
        PojoBean stringBean = new PojoBean("11", "2");

        doParse("(stringValue1 > stringValue2)", stringBean, 0, "stringValue1",
            true);
        doParse("(stringValue1 < stringValue2)", stringBean, 0, "stringValue1",
            false);
    }

    /**
     * Test Null.
     */
    @Test
    public void testNull() {
        // Not Null
        doParse("(*this* != null)", testBean, 0, "stringValue1", true);

        // Null
        doParse("(*this* == null)", testBean, 0, "stringValue2", true);

        // 0-length empty string
        testBean.setStringValue2("");
        doParse("(*this* == null)", testBean, 0, "stringValue2", true);

        // N-length empty string
        testBean.setStringValue2("  ");
        doParse("(*this* == null)", testBean, 0, "stringValue2", true);
    }

    /**
     * Test Joined expressions ('and' or 'or')
     */
    @Test
    public void testJoined() {
        // Join with 'or'
        doParse("((*this* == 'ABC') or (stringValue2 == null))", testBean, 0,
            "stringValue1", true);
        doParse("((*this* != 'ABC') or (stringValue2 == null))", testBean, 0,
            "stringValue1", true);
        doParse("((*this* == 'ABC') or (stringValue2 != null))", testBean, 0,
            "stringValue1", true);
        doParse("((*this* != 'ABC') or (stringValue2 != null))", testBean, 0,
            "stringValue1", false);

        // Join with 'and'
        doParse("((*this* == 'ABC') and (stringValue2 == null))", testBean, 0,
            "stringValue1", true);
        doParse("((*this* != 'ABC') and (stringValue2 == null))", testBean, 0,
            "stringValue1", false);
        doParse("((*this* == 'ABC') and (stringValue2 != null))", testBean, 0,
            "stringValue1", false);
        doParse("((*this* != 'ABC') and (stringValue2 != null))", testBean, 0,
            "stringValue1", false);
    }

    /**
     * Parse the expression and check that the expected result (either true or
     * false) occurs - fail if an exception is thrown opr the wrong result
     * occurs.
     *
     * @param test     Test expression
     * @param bean     Test Bean
     * @param index    index value
     * @param property Bean property
     * @param expected Expected Result
     */
    private void doParse(String test, Object bean, int index, String property,
        boolean expected) {
        boolean result = false;

        try {
            result = doParse(test, bean, index, property);
        } catch (Exception ex) {
            log.error("Parsing " + test + " for property '" + property + "'", ex);
            fail("Parsing " + test + " threw " + ex);
        }

        if (expected) {
            assertTrue(result, test + " didn't return TRUE for " + property);
        } else {
            assertFalse(result, test + " didn't return FALSE for " + property);
        }
    }

    /**
     * Parse the expression and check that an Exception is throw. Failes if no
     * expection is thrown.
     *
     * @param test     Test expression
     * @param bean     Test Bean
     * @param index    index value
     * @param property Bean property
     */
    /*
    private void doParseFail(String test, Object bean, int index,
        String property) {
        try {
            boolean result = doParse(test, bean, index, property);

            fail("Parsing " + test + " didn't throw exception as expected "
                + result);
        } catch (Exception expected) {
            // ignore exception - expected result
        }
    }
    */

    /**
     * Parse the expression returning the result
     *
     * @param test     Test expression
     * @param bean     Test Bean
     * @param index    index value
     * @param property Bean property
     */
    private boolean doParse(String test, Object bean, int index, String property)
        throws Exception {
        if (bean == null) {
            throw new NullPointerException("Bean is null for property '"
                + property + "'");
        }

        String value =
            String.class.isInstance(bean) ? (String) bean
                                          : ValidatorUtils.getValueAsString(bean,
                property);

        ValidWhenLexer lexer = new ValidWhenLexer(new StringReader(test));

        ValidWhenParser parser = new ValidWhenParser(lexer);

        parser.setForm(bean);
        parser.setIndex(index);
        parser.setValue(value);

        parser.expression();

        return parser.getResult();
    }
}
