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

import org.apache.struts.taglib.logic.PresentTag;
import org.apache.strutsel.taglib.utils.EvalHelper;

import javax.servlet.jsp.JspException;

/**
 * Evaluates the nested body content of this tag if the specified value is
 * present for this request. <p> This class is a subclass of the class
 * <code>org.apache.struts.taglib.logic.PresentTag</code> which provides most
 * of the described functionality.  This subclass allows all attribute values
 * to be specified as expressions utilizing the JavaServer Pages Standard
 * Library expression language.
 *
 * @version $Rev$
 */
public class ELPresentTag extends PresentTag {
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
     * Instance variable mapped to "role" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String roleExpr;

    /**
     * Instance variable mapped to "scope" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String scopeExpr;

    /**
     * Instance variable mapped to "user" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String userExpr;

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
     * Getter method for "role" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getRoleExpr() {
        return (roleExpr);
    }

    /**
     * Getter method for "scope" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getScopeExpr() {
        return (scopeExpr);
    }

    /**
     * Getter method for "user" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getUserExpr() {
        return (userExpr);
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
     * Setter method for "role" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setRoleExpr(String roleExpr) {
        this.roleExpr = roleExpr;
    }

    /**
     * Setter method for "scope" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setScopeExpr(String scopeExpr) {
        this.scopeExpr = scopeExpr;
    }

    /**
     * Setter method for "user" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setUserExpr(String userExpr) {
        this.userExpr = userExpr;
    }

    /**
     * Resets attribute values for tag reuse.
     */
    public void release() {
        super.release();
        setCookieExpr(null);
        setHeaderExpr(null);
        setNameExpr(null);
        setParameterExpr(null);
        setPropertyExpr(null);
        setRoleExpr(null);
        setScopeExpr(null);
        setUserExpr(null);
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
                EvalHelper.evalString("header", getHeaderExpr(), this,
                    pageContext)) != null) {
            setHeader(string);
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
                EvalHelper.evalString("role", getRoleExpr(), this, pageContext)) != null) {
            setRole(string);
        }

        if ((string =
                EvalHelper.evalString("scope", getScopeExpr(), this, pageContext)) != null) {
            setScope(string);
        }

        if ((string =
                EvalHelper.evalString("user", getUserExpr(), this, pageContext)) != null) {
            setUser(string);
        }
    }
}
