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
package org.apache.strutsel.taglib.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

/**
 * This class is used in the {@code evaluateExpressions} method of each
 * Tag class. It is used to process the original attribute value through the
 * JSTL EL engine to produce an evaluated value. It provides functions to
 * evaluate the expression assuming it is an Object, String, Integer, or
 * Boolean result.
 */
public final class EvalHelper {

    private final static String ERROR_MSG =
            ResourceBundle
                .getBundle("org.apache.strutsel.taglib.utils.Resources")
                .getString("ATTRIBUTE_EVALUATION_EXCEPTION");

    private EvalHelper() {
    }

    /**
     * Evaluates the attribute value in the JSTL EL engine, returning the raw
     * Object value of the evaluated expression. If the original expression
     * is null, or the resulting value is null, it will return null.
     *
     * @param attrName the name of the attribute
     * @param attrValue the value/expression of the attribute
     * @param pageContext the context for the JSP
     *
     * @return the result of evaluating the expression
     *
     * @throws JspException Thrown if there are syntactical errors in the
     *     provided expression.
     */
    public static Object eval(String attrName, String attrValue, PageContext pageContext)
        throws JspException {

        return evaluate(attrName, attrValue, pageContext, Object.class);
    }

    /**
     * Evaluates the attribute value in the JSTL EL engine, assuming the
     * resulting value is a String object. If the original expression is
     * null, or the resulting value is null, it will return null.
     *
     * @param attrName the name of the attribute
     * @param attrValue the value/expression of the attribute
     * @param pageContext the context for the JSP
     *
     * @return the result of evaluating the expression
     *
     * @throws JspException Thrown if there are syntactical errors in the
     *     provided expression.
     */
    public static String evalString(String attrName, String attrValue, PageContext pageContext)
        throws JspException {

        return evaluate(attrName, attrValue, pageContext, String.class);
    }

    /**
     * Evaluates the attribute value in the JSTL EL engine, assuming the
     * resulting value is an Integer object. If the original expression is
     * null, or the resulting value is null, it will return null.
     *
     * @param attrName the name of the attribute
     * @param attrValue the value/expression of the attribute
     * @param pageContext the context for the JSP
     *
     * @return the result of evaluating the expression
     *
     * @throws JspException Thrown if there are syntactical errors in the
     *     provided expression.
     */
    public static Integer evalInteger(String attrName, String attrValue, PageContext pageContext)
        throws JspException {

        return evaluate(attrName, attrValue, pageContext, Integer.class);
    }

    /**
     * Evaluates the attribute value in the JSTL EL engine, assuming the
     * resulting value is an Boolean object. If the original expression is
     * null, or the resulting value is null, it will return null.
     *
     * @param attrName the name of the attribute
     * @param attrValue the value/expression of the attribute
     * @param pageContext the context for the JSP
     *
     * @return the result of evaluating the expression
     *
     * @throws JspException Thrown if there are syntactical errors in the
     *     provided expression.
     */
    public static Boolean evalBoolean(String attrName, String attrValue, PageContext pageContext)
        throws JspException {

        return evaluate(attrName, attrValue, pageContext, Boolean.class);
    }

    /**
     * Evaluate a value expression. To support optional attributes, if the expression is null then
     * null will be returned.
     *
     * @param <T> the expected type of the expression
     * @param attrName the name of the attribute
     * @param attrValue the value/expression of the attribute
     * @param pageContext the context for the JSP
     * @param expectedType the expected class of the expression
     *
     * @return the result of evaluating the expression
     *
     * @throws JspException Thrown if there are syntactical errors in the
     *     provided expression.
     */
    private static <T> T evaluate(String attrName, String attrValue,
            PageContext pageContext, Class<T> expectedType)
            throws JspException {

        if (attrValue == null) {
            return null;
        }

        try {
            final JspApplicationContext appContext = JspFactory.getDefaultFactory()
                    .getJspApplicationContext(pageContext.getServletContext());

            final ExpressionFactory factory = appContext.getExpressionFactory();

            final ValueExpression expression = factory
                    .createValueExpression(pageContext.getELContext(), attrValue, expectedType);

            final Object ret = expression.getValue(pageContext.getELContext());
            return expectedType.cast(ret);
        } catch (ELException exc) {
            throw new JspException(MessageFormat.format(ERROR_MSG,
                    attrName,
                    attrValue,
                    exc.getMessage(),
                    exc.getCause()), exc.getCause());
        }
    }
}