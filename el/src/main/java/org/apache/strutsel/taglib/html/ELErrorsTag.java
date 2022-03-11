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

import org.apache.struts.taglib.html.ErrorsTag;
import org.apache.strutsel.taglib.utils.EvalHelper;

import javax.servlet.jsp.JspException;

/**
 * <p>Custom tag that renders error messages if an appropriate request
 * attribute has been created.  The tag looks for a request attribute with a
 * reserved key, and assumes that it is either a String, a String array,
 * containing message keys to be looked up in the module's MessageResources,
 * or an object of type <code>org.apache.struts.action.ActionErrors</code>.
 * <p> The following optional message keys will be utilized if corresponding
 * messages exist for them in the application resources:</p>
 *
 * <ul>
 *
 * <li><b>errors.header</b> - If present, the corresponding message will be
 * rendered prior to the individual list of error messages.</li>
 *
 * <li><b>errors.footer</b> - If present, the corresponding message will be
 * rendered following the individual list of error messages.</li>
 *
 * <li><b>errors.prefix</b> - If present, the corresponding message will be
 * rendered before each individual error message.</li>
 *
 * <li><b>errors.suffix</b> - If present, the corresponding message will be
 * rendered after each individual error message.</li>
 *
 * </ul>
 *
 * <p> This class is a subclass of the class <code>org.apache.struts.taglib.html.ErrorsTag</code>
 * which provides most of the described functionality.  This subclass allows
 * all attribute values to be specified as expressions utilizing the
 * JavaServer Pages Standard Library expression language.
 *
 * @version $Rev$
 */
public class ELErrorsTag extends ErrorsTag {
    /**
     * Instance variable mapped to "bundle" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String bundleExpr;

    /**
     * Instance variable mapped to "footer" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String footerExpr;

    /**
     * Instance variable mapped to "header" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String headerExpr;

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
     * Instance variable mapped to "prefix" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String prefixExpr;

    /**
     * Instance variable mapped to "property" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String propertyExpr;

    /**
     * Instance variable mapped to "suffix" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String suffixExpr;

    /**
     * Getter method for "bundle" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getBundleExpr() {
        return (bundleExpr);
    }

    /**
     * Getter method for "footer" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getFooterExpr() {
        return (footerExpr);
    }

    /**
     * Getter method for "header" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getHeaderExpr() {
        return (headerExpr);
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
     * Getter method for "prefix" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getPrefixExpr() {
        return (prefixExpr);
    }

    /**
     * Getter method for "property" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getPropertyExpr() {
        return (propertyExpr);
    }

    /**
     * Getter method for "suffix" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getSuffixExpr() {
        return (suffixExpr);
    }

    /**
     * Setter method for "bundle" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setBundleExpr(String bundleExpr) {
        this.bundleExpr = bundleExpr;
    }

    /**
     * Setter method for "footer" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setFooterExpr(String footerExpr) {
        this.footerExpr = footerExpr;
    }

    /**
     * Setter method for "header" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setHeaderExpr(String headerExpr) {
        this.headerExpr = headerExpr;
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
     * Setter method for "prefix" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setPrefixExpr(String prefixExpr) {
        this.prefixExpr = prefixExpr;
    }

    /**
     * Setter method for "property" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setPropertyExpr(String propertyExpr) {
        this.propertyExpr = propertyExpr;
    }

    /**
     * Setter method for "suffix" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setSuffixExpr(String suffixExpr) {
        this.suffixExpr = suffixExpr;
    }

    /**
     * Resets attribute values for tag reuse.
     */
    public void release() {
        super.release();
        setBundleExpr(null);
        setFooterExpr(null);
        setHeaderExpr(null);
        setLocaleExpr(null);
        setNameExpr(null);
        setPrefixExpr(null);
        setPropertyExpr(null);
        setSuffixExpr(null);
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
                EvalHelper.evalString("bundle", getBundleExpr(), this,
                    pageContext)) != null) {
            setBundle(string);
        }

        if ((string =
                EvalHelper.evalString("footer", getFooterExpr(), this,
                    pageContext)) != null) {
            setFooter(string);
        }

        if ((string =
                EvalHelper.evalString("header", getHeaderExpr(), this,
                    pageContext)) != null) {
            setHeader(string);
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
                EvalHelper.evalString("prefix", getPrefixExpr(), this,
                    pageContext)) != null) {
            setPrefix(string);
        }

        if ((string =
                EvalHelper.evalString("property", getPropertyExpr(), this,
                    pageContext)) != null) {
            setProperty(string);
        }

        if ((string =
                EvalHelper.evalString("suffix", getSuffixExpr(), this,
                    pageContext)) != null) {
            setSuffix(string);
        }
    }
}
