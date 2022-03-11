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
package org.apache.strutsel.taglib.html;

import org.apache.struts.taglib.html.JavascriptValidatorTag;
import org.apache.strutsel.taglib.utils.EvalHelper;

import javax.servlet.jsp.JspException;

/**
 * Custom tag that generates JavaScript for client side validation based on
 * the validation rules loaded by the <code>ValidatorPlugIn</code> defined in
 * the struts-config.xml file. <p> This class is a subclass of the class
 * <code>org.apache.struts.taglib.html.JavascriptValidatorTag</code> which
 * provides most of the described functionality.  This subclass allows all
 * attribute values to be specified as expressions utilizing the JavaServer
 * Pages Standard Library expression language.
 *
 * @version $Rev$
 */
public class ELJavascriptValidatorTag extends JavascriptValidatorTag {
    /**
     * Instance variable mapped to "cdata" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String cdataExpr;

    /**
     * Instance variable mapped to "dynamicJavascript" tag attribute. (Mapping
     * set in associated BeanInfo class.)
     */
    private String dynamicJavascriptExpr;

    /**
     * Instance variable mapped to "formName" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String formNameExpr;

    /**
     * Instance variable mapped to "method" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String methodExpr;

    /**
     * Instance variable mapped to "page" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String pageExpr;

    /**
     * Instance variable mapped to "scriptLanguage" tag attribute. (Mapping
     * set in associated BeanInfo class.)
     */
    private String scriptLanguageExpr;

    /**
     * Instance variable mapped to "src" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String srcExpr;

    /**
     * Instance variable mapped to "staticJavascript" tag attribute. (Mapping
     * set in associated BeanInfo class.)
     */
    private String staticJavascriptExpr;

    /**
     * Instance variable mapped to "htmlComment" tag attribute. (Mapping set
     * in associated BeanInfo class.)
     */
    private String htmlCommentExpr;

    /**
     * Instance variable mapped to "bundle" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String bundleExpr;

    /**
     * Getter method for "cdata" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getCdataExpr() {
        return (cdataExpr);
    }

    /**
     * Getter method for "dynamicJavascript" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getDynamicJavascriptExpr() {
        return (dynamicJavascriptExpr);
    }

    /**
     * Getter method for "formName" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getFormNameExpr() {
        return (formNameExpr);
    }

    /**
     * Getter method for "method" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getMethodExpr() {
        return (methodExpr);
    }

    /**
     * Getter method for "page" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getPageExpr() {
        return (pageExpr);
    }

    /**
     * Getter method for "scriptLanguage" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getScriptLanguageExpr() {
        return (scriptLanguageExpr);
    }

    /**
     * Getter method for "src" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getSrcExpr() {
        return (srcExpr);
    }

    /**
     * Getter method for "staticJavascript" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getStaticJavascriptExpr() {
        return (staticJavascriptExpr);
    }

    /**
     * Getter method for "htmlComment" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getHtmlCommentExpr() {
        return (htmlCommentExpr);
    }

    /**
     * Getter method for "bundle" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getBundleExpr() {
        return (bundleExpr);
    }

    /**
     * Setter method for "cdata" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setCdataExpr(String cdataExpr) {
        this.cdataExpr = cdataExpr;
    }

    /**
     * Setter method for "dynamicJavascript" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setDynamicJavascriptExpr(String dynamicJavascriptExpr) {
        this.dynamicJavascriptExpr = dynamicJavascriptExpr;
    }

    /**
     * Setter method for "formName" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setFormNameExpr(String formNameExpr) {
        this.formNameExpr = formNameExpr;
    }

    /**
     * Setter method for "method" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setMethodExpr(String methodExpr) {
        this.methodExpr = methodExpr;
    }

    /**
     * Setter method for "page" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setPageExpr(String pageExpr) {
        this.pageExpr = pageExpr;
    }

    /**
     * Setter method for "scriptLanguage" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setScriptLanguageExpr(String scriptLanguageExpr) {
        this.scriptLanguageExpr = scriptLanguageExpr;
    }

    /**
     * Setter method for "src" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setSrcExpr(String srcExpr) {
        this.srcExpr = srcExpr;
    }

    /**
     * Setter method for "staticJavascript" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setStaticJavascriptExpr(String staticJavascriptExpr) {
        this.staticJavascriptExpr = staticJavascriptExpr;
    }

    /**
     * Setter method for "htmlComment" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setHtmlCommentExpr(String htmlCommentExpr) {
        this.htmlCommentExpr = htmlCommentExpr;
    }

    /**
     * Setter method for "bundle" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setBundleExpr(String bundleExpr) {
        this.bundleExpr = bundleExpr;
    }

    /**
     * Resets attribute values for tag reuse.
     */
    public void release() {
        super.release();
        setCdataExpr(null);
        setDynamicJavascriptExpr(null);
        setFormNameExpr(null);
        setMethodExpr(null);
        setPageExpr(null);
        setScriptLanguageExpr(null);
        setSrcExpr(null);
        setStaticJavascriptExpr(null);
        setHtmlCommentExpr(null);
        setBundleExpr(null);
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
        Integer integer = null;
        Boolean bool = null;

        if ((string =
                EvalHelper.evalString("cdata", getCdataExpr(), this, pageContext)) != null) {
            setCdata(string);
        }

        if ((string =
                EvalHelper.evalString("dynamicJavascript",
                    getDynamicJavascriptExpr(), this, pageContext)) != null) {
            setDynamicJavascript(string);
        }

        if ((string =
                EvalHelper.evalString("formName", getFormNameExpr(), this,
                    pageContext)) != null) {
            setFormName(string);
        }

        if ((string =
                EvalHelper.evalString("method", getMethodExpr(), this,
                    pageContext)) != null) {
            setMethod(string);
        }

        if ((integer =
                EvalHelper.evalInteger("page", getPageExpr(), this, pageContext)) != null) {
            setPage(integer.intValue());
        }

        if ((bool =
                EvalHelper.evalBoolean("scriptLanguage",
                    getScriptLanguageExpr(), this, pageContext)) != null) {
            setScriptLanguage(bool.booleanValue());
        }

        if ((string =
                EvalHelper.evalString("src", getSrcExpr(), this, pageContext)) != null) {
            setSrc(string);
        }

        if ((string =
                EvalHelper.evalString("staticJavascript",
                    getStaticJavascriptExpr(), this, pageContext)) != null) {
            setStaticJavascript(string);
        }

        if ((string =
                EvalHelper.evalString("htmlComment", getHtmlCommentExpr(),
                    this, pageContext)) != null) {
            setHtmlComment(string);
        }

        if ((string =
                EvalHelper.evalString("bundle", getBundleExpr(), this,
                    pageContext)) != null) {
            setBundle(string);
        }
    }
}
