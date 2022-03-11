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
package org.apache.strutsel.taglib.bean;

import org.apache.struts.taglib.bean.MessageTag;
import org.apache.strutsel.taglib.utils.EvalHelper;

import javax.servlet.jsp.JspException;

/**
 * Custom tag that retrieves an internationalized messages string (with
 * optional parametric replacement) from the <code>ActionResources</code>
 * object stored as a context attribute by our associated
 * <code>ActionServlet</code> implementation. <p> This class is a subclass of
 * the class <code>org.apache.struts.taglib.bean.MessageTag</code> which
 * provides most of the described functionality.  This subclass allows all
 * attribute values to be specified as expressions utilizing the JavaServer
 * Pages Standard Library expression language.
 *
 * @version $Rev$
 */
public class ELMessageTag extends MessageTag {
    /**
     * Instance variable mapped to "arg0" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String arg0Expr;

    /**
     * Instance variable mapped to "arg1" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String arg1Expr;

    /**
     * Instance variable mapped to "arg2" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String arg2Expr;

    /**
     * Instance variable mapped to "arg3" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String arg3Expr;

    /**
     * Instance variable mapped to "arg4" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String arg4Expr;

    /**
     * Instance variable mapped to "bundle" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String bundleExpr;

    /**
     * Instance variable mapped to "key" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String keyExpr;

    /**
     * Instance variable mapped to "locale" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String localeExpr;

    /**
     * Instance variable mapped to "name" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String nameExpr;

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
     * Getter method for "arg0" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getArg0Expr() {
        return (arg0Expr);
    }

    /**
     * Getter method for "arg1" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getArg1Expr() {
        return (arg1Expr);
    }

    /**
     * Getter method for "arg2" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getArg2Expr() {
        return (arg2Expr);
    }

    /**
     * Getter method for "arg3" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getArg3Expr() {
        return (arg3Expr);
    }

    /**
     * Getter method for "arg4" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getArg4Expr() {
        return (arg4Expr);
    }

    /**
     * Getter method for "bundle" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getBundleExpr() {
        return (bundleExpr);
    }

    /**
     * Getter method for "key" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getKeyExpr() {
        return (keyExpr);
    }

    /**
     * Getter method for "locale" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getLocaleExpr() {
        return (localeExpr);
    }

    /**
     * Getter method for "name" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getNameExpr() {
        return (nameExpr);
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
     * Setter method for "arg0" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setArg0Expr(String arg0Expr) {
        this.arg0Expr = arg0Expr;
    }

    /**
     * Setter method for "arg1" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setArg1Expr(String arg1Expr) {
        this.arg1Expr = arg1Expr;
    }

    /**
     * Setter method for "arg2" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setArg2Expr(String arg2Expr) {
        this.arg2Expr = arg2Expr;
    }

    /**
     * Setter method for "arg3" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setArg3Expr(String arg3Expr) {
        this.arg3Expr = arg3Expr;
    }

    /**
     * Setter method for "arg4" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setArg4Expr(String arg4Expr) {
        this.arg4Expr = arg4Expr;
    }

    /**
     * Setter method for "bundle" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setBundleExpr(String bundleExpr) {
        this.bundleExpr = bundleExpr;
    }

    /**
     * Setter method for "key" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setKeyExpr(String keyExpr) {
        this.keyExpr = keyExpr;
    }

    /**
     * Setter method for "locale" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setLocaleExpr(String localeExpr) {
        this.localeExpr = localeExpr;
    }

    /**
     * Setter method for "name" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setNameExpr(String nameExpr) {
        this.nameExpr = nameExpr;
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
     * Resets attribute values for tag reuse.
     */
    public void release() {
        super.release();
        setArg0Expr(null);
        setArg1Expr(null);
        setArg2Expr(null);
        setArg3Expr(null);
        setArg4Expr(null);
        setBundleExpr(null);
        setKeyExpr(null);
        setLocaleExpr(null);
        setNameExpr(null);
        setPropertyExpr(null);
        setScopeExpr(null);
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
                EvalHelper.evalString("arg0", getArg0Expr(), this, pageContext)) != null) {
            setArg0(string);
        }

        if ((string =
                EvalHelper.evalString("arg1", getArg1Expr(), this, pageContext)) != null) {
            setArg1(string);
        }

        if ((string =
                EvalHelper.evalString("arg2", getArg2Expr(), this, pageContext)) != null) {
            setArg2(string);
        }

        if ((string =
                EvalHelper.evalString("arg3", getArg3Expr(), this, pageContext)) != null) {
            setArg3(string);
        }

        if ((string =
                EvalHelper.evalString("arg4", getArg4Expr(), this, pageContext)) != null) {
            setArg4(string);
        }

        if ((string =
                EvalHelper.evalString("bundle", getBundleExpr(), this,
                    pageContext)) != null) {
            setBundle(string);
        }

        if ((string =
                EvalHelper.evalString("key", getKeyExpr(), this, pageContext)) != null) {
            setKey(string);
        }

        if ((string =
                EvalHelper.evalString("locale", getLocaleExpr(), this,
                    pageContext)) != null) {
            setLocale(string);
        }

        if ((string =
                EvalHelper.evalString("name", getNameExpr(), this, pageContext)) != null) {
            setName(string);
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
    }
}
