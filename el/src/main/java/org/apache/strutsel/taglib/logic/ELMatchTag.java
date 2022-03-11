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
package org.apache.strutsel.taglib.logic;

import org.apache.struts.taglib.logic.MatchTag;
import org.apache.strutsel.taglib.utils.EvalHelper;

import javax.servlet.jsp.JspException;

/**
 * Evalute the nested body content of this tag if the specified value is a
 * substring of the specified variable. <p> This class is a subclass of the
 * class <code>org.apache.struts.taglib.logic.MatchTag</code> which provides
 * most of the described functionality.  This subclass allows all attribute
 * values to be specified as expressions utilizing the JavaServer Pages
 * Standard Library expression language.
 *
 * @version $Rev$
 */
public class ELMatchTag extends MatchTag {
    /**
     * Instance variable mapped to "cookie" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String cookieExpr;

    /**
     * Instance variable mapped to "header" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String headerExpr;

    /**
     * Instance variable mapped to "location" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String locationExpr;

    /**
     * Instance variable mapped to "name" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String nameExpr;

    /**
     * Instance variable mapped to "parameter" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String parameterExpr;

    /**
     * Instance variable mapped to "property" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String propertyExpr;

    /**
     * Instance variable mapped to "scope" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String scopeExpr;

    /**
     * Instance variable mapped to "value" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String valueExpr;

    /**
     * String value of expression to be evaluated.
     */
    private String expr;

    /**
     * Evaluated value of expression.
     */
    private String exprValue;

    /**
     * Getter method for "cookie" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getCookieExpr() {
        return (cookieExpr);
    }

    /**
     * Getter method for "header" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getHeaderExpr() {
        return (headerExpr);
    }

    /**
     * Getter method for "location" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getLocationExpr() {
        return (locationExpr);
    }

    /**
     * Getter method for "name" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getNameExpr() {
        return (nameExpr);
    }

    /**
     * Getter method for "parameter" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getParameterExpr() {
        return (parameterExpr);
    }

    /**
     * Getter method for "property" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getPropertyExpr() {
        return (propertyExpr);
    }

    /**
     * Getter method for "scope" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getScopeExpr() {
        return (scopeExpr);
    }

    /**
     * Getter method for "value" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getValueExpr() {
        return (valueExpr);
    }

    /**
     * Setter method for "cookie" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setCookieExpr(String cookieExpr) {
        this.cookieExpr = cookieExpr;
    }

    /**
     * Setter method for "header" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setHeaderExpr(String headerExpr) {
        this.headerExpr = headerExpr;
    }

    /**
     * Setter method for "location" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setLocationExpr(String locationExpr) {
        this.locationExpr = locationExpr;
    }

    /**
     * Setter method for "name" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setNameExpr(String nameExpr) {
        this.nameExpr = nameExpr;
    }

    /**
     * Setter method for "parameter" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setParameterExpr(String parameterExpr) {
        this.parameterExpr = parameterExpr;
    }

    /**
     * Setter method for "property" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setPropertyExpr(String propertyExpr) {
        this.propertyExpr = propertyExpr;
    }

    /**
     * Setter method for "scope" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setScopeExpr(String scopeExpr) {
        this.scopeExpr = scopeExpr;
    }

    /**
     * Setter method for "value" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setValueExpr(String valueExpr) {
        this.valueExpr = valueExpr;
    }

    /**
     * Returns the string value of the expression.  This value will be
     * evaluated by the JSTL EL engine.
     */
    public String getExpr() {
        return (expr);
    }

    /**
     * Sets the string value of the expression.  This expression will be
     * evaluated by the JSTL EL engine.
     */
    public void setExpr(String expr) {
        this.expr = expr;
    }

    /**
     * Returns the evaluated expression.
     */
    public String getExprValue() {
        return (exprValue);
    }

    /**
     * Sets the evaluated expression.
     */
    public void setExprValue(String exprValue) {
        this.exprValue = exprValue;
    }

    /**
     * Releases state of custom tag so this instance can be reused.
     */
    public void release() {
        super.release();
        setCookieExpr(null);
        setHeaderExpr(null);
        setLocationExpr(null);
        setNameExpr(null);
        setParameterExpr(null);
        setPropertyExpr(null);
        setScopeExpr(null);
        setValueExpr(null);
        setExpr(null);
        setExprValue(null);
    }

    /**
     * Process the start tag.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        evaluateExpressions();

        return (super.doStartTag());
    }

    /**
     * Evaluates the condition that is being tested by this particular tag,
     * and returns <code>true</code> if the nested body content of this tag
     * should be evaluated, or <code>false</code> if it should be skipped.
     *
     * @param desired Desired value for a true result
     * @throws JspException if a JSP exception occurs
     */
    protected boolean condition(boolean desired)
        throws JspException {
        boolean result = false;

        if (getExprValue() != null) {
            result =
                ELMatchSupport.condition(desired, getExprValue(), value,
                    location, messages, pageContext);
        } else {
            result = super.condition(desired);
        }

        return (result);
    }

    /**
     * Processes all attribute values which use the JSTL expression evaluation
     * engine to determine their values.
     *
     * @throws JspException if a JSP exception has occurred
     */
    private void evaluateExpressions()
        throws JspException {
        String string = null;

        if ((string =
                EvalHelper.evalString("cookie", getCookieExpr(), this,
                    pageContext)) != null) {
            setCookie(string);
        }

        if ((string =
                EvalHelper.evalString("expr", getExpr(), this, pageContext)) != null) {
            setExprValue(string);
        }

        if ((string =
                EvalHelper.evalString("header", getHeaderExpr(), this,
                    pageContext)) != null) {
            setHeader(string);
        }

        if ((string =
                EvalHelper.evalString("location", getLocationExpr(), this,
                    pageContext)) != null) {
            setLocation(string);
        }

        if ((string =
                EvalHelper.evalString("name", getNameExpr(), this, pageContext)) != null) {
            setName(string);
        }

        if ((string =
                EvalHelper.evalString("parameter", getParameterExpr(), this,
                    pageContext)) != null) {
            setParameter(string);
        }

        if ((string =
                EvalHelper.evalString("property", getPropertyExpr(), this,
                    pageContext)) != null) {
            setProperty(string);
        }

        if ((string =
                EvalHelper.evalString("scope", getScopeExpr(), this, pageContext)) != null) {
            setScope(string);
        }

        if ((string =
                EvalHelper.evalString("value", getValueExpr(), this, pageContext)) != null) {
            setValue(string);
        }
    }
}
