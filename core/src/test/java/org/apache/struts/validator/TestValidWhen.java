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

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.validator.validwhen.ValidWhenEvaluator;
import org.apache.struts.validator.validwhen.ValidWhenLexer;
import org.apache.struts.validator.validwhen.ValidWhenParser;
import org.apache.struts.validator.validwhen.ValidWhenParser.ExpressionContext;
import org.apache.struts.validator.validwhen.ValidWhenResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for the ValidWhen Parser/Lexer.
 */
public class TestValidWhen {

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(TestValidWhen.class);

    protected PojoBean testBean;

    @BeforeEach
    public void setUp() {
        testBean = new PojoBean(123, 789);
        testBean.setStringValue1("ABC");
        testBean.setStringValue2(null);
        testBean.setFloatValue1(0.0f);
        testBean.setFloatValue2(-3.14f);
        testBean.setDoubleValue1(0.0);
        testBean.setDoubleValue2(-3.14);
        testBean.setBeans(new PojoBean[] {
                new PojoBean(11, 12), new PojoBean(21, 22), new PojoBean(31, 42),
                new PojoBean(41, 52), new PojoBean(51, 62)
            });
        testBean.setMapped("testKey", "mappedValue");
        testBean.setStringArray(new String[] {"zero", "one", "two", "three"});
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
     * Test integer values.
     */
    @Test
    public void testInteger() {
        // Test Zero
        PojoBean numberBean = new PojoBean(0, -50);

        doParse("(intValue1 == 0)", numberBean, 0, "intValue1", true);
        doParse("(intValue2 != 0)", numberBean, 0, "intValue2", true);
        doParse("(integerValue1 == 0)", numberBean, 0, "integerValue1", true);
        doParse("(integerValue2 != 0)", numberBean, 0, "integerValue2", true);

        // int
        doParse("(intValue1 == 123)", testBean, 0, "intValue1", true);
        doParse("(intValue1 == 123.0)", testBean, 0, "intValue1", true);
        doParse("(intValue1 == 123.1)", testBean, 0, "intValue1", false);

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
     * Test float values
     */
    @Test
    public void testFloat() {
        // Test zero
        doParse("(floatValue1 == 0)", testBean, 0, "floatValue1", true);
        doParse("(floatValue2 != 0)", testBean, 0, "floatValue2", true);
        doParse("(floatValue1 == 0.0)", testBean, 0, "floatValue1", true);
        doParse("(floatValue2 != 0.0)", testBean, 0, "floatValue2", true);
        doParse("(floatValue1 == 00.00)", testBean, 0, "floatValue1", true);
        doParse("(floatValue2 != 00.00)", testBean, 0, "floatValue2", true);

        // Test precision
        doParse("(floatValue2 == -3.14)", testBean, 0, "floatValue2", true);
        doParse("(floatValue2 == -3.140)", testBean, 0, "floatValue2", true);
        doParse("(floatValue2 == -3.1)", testBean, 0, "floatValue2", false);

        // Test negative
        doParse("((floatValue2 > -3.15) and (floatValue2 < -3.13))", testBean, 0, "floatValue2", true);
    }

    /**
     * Test double values
     */
    @Test
    public void testDouble() {
        // Test zero
        doParse("(doubleValue1 == 0)", testBean, 0, "doubleValue1", true);
        doParse("(doubleValue2 != 0)", testBean, 0, "doubleValue2", true);
        doParse("(doubleValue1 == 0.0)", testBean, 0, "doubleValue1", true);
        doParse("(doubleValue2 != 0.0)", testBean, 0, "doubleValue2", true);
        doParse("(doubleValue1 == 00.00)", testBean, 0, "doubleValue1", true);
        doParse("(doubleValue2 != 00.00)", testBean, 0, "doubleValue2", true);

        // Test precision
        doParse("(doubleValue2 == -3.14)", testBean, 0, "doubleValue2", true);
        doParse("(doubleValue2 == -3.140)", testBean, 0, "doubleValue2", true);
        doParse("(doubleValue2 == -3.1)", testBean, 0, "doubleValue2", false);

        // Test negative
        doParse("((doubleValue2 > -3.15) and (doubleValue2 < -3.13))", testBean, 0, "doubleValue2", true);
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
     * Test Indexed Property.
     */
    @Test
    public void testIndexedValue() {
        // Test Case for Jira Issue STR-2802
        // see https://issues.apache.org/jira/browse/STR-2802
        doParse("(stringArray[1] == 'one')", testBean, 1, "stringArray[1]", true);
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
//            System.out.println("---------------------------");
//            System.out.println(test + " " + expected);
            result = doParse(test, bean, index, property);
        } catch (Exception ex) {
//            System.out.println("Exception: " + ex);
//            ex.printStackTrace();
            log.error("Parsing {} for property '{}'", test, property, ex);
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

        ValidWhenLexer lexer = new ValidWhenLexer(CharStreams.fromString(test));

        ValidWhenParser parser = new ValidWhenParser(new CommonTokenStream(lexer));

        ValidWhenEvaluator validWhenEvaluator = new ValidWhenEvaluator(bean, value, index);

        ExpressionContext expressionContext = parser.expression();
        ValidWhenResult<?> result = validWhenEvaluator.visitExpression(expressionContext);

        return result == null ? false : result.toBoolean();
    }
}