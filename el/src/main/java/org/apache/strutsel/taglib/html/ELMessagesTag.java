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

import org.apache.struts.taglib.html.MessagesTag;
import org.apache.strutsel.taglib.utils.EvalHelper;

import javax.servlet.jsp.JspException;

/**
 * Custom tag that iterates the elements of a message collection. It defaults
 * to retrieving the messages from <code>Action.ERROR_KEY</code>, but if the
 * message attribute is set to true then the messages will be retrieved from
 * <code>Action.MESSAGE_KEY</code>. This is an alternative to the default
 * <code>ErrorsTag</code>. <p> This class is a subclass of the class
 * <code>org.apache.struts.taglib.html.MessagesTag</code> which provides most
 * of the described functionality.  This subclass allows all attribute values
 * to be specified as expressions utilizing the JavaServer Pages Standard
 * Library expression language.
 *
 * @version $Rev$
 */
public class ELMessagesTag extends MessagesTag {
    /**
     * Instance variable mapped to "id" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String idExpr;

    /**
     * Instance variable mapped to "bundle" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String bundleExpr;

    /**
     * Instance variable mapped to "filterArgs" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String filterArgsExpr;

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
     * Instance variable mapped to "header" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String headerExpr;

    /**
     * Instance variable mapped to "footer" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String footerExpr;

    /**
     * Instance variable mapped to "message" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String messageExpr;

    /**
     * Getter method for "id" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getIdExpr() {
        return (idExpr);
    }

    /**
     * Getter method for "bundle" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getBundleExpr() {
        return (bundleExpr);
    }

    /**
     * Getter method for "filterArgs" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getFilterArgsExpr() {
        return (filterArgsExpr);
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
     * Getter method for "header" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getHeaderExpr() {
        return (headerExpr);
    }

    /**
     * Getter method for "footer" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getFooterExpr() {
        return (footerExpr);
    }

    /**
     * Getter method for "message" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getMessageExpr() {
        return (messageExpr);
    }

    /**
     * Setter method for "id" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setIdExpr(String idExpr) {
        this.idExpr = idExpr;
    }

    /**
     * Setter method for "bundle" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setBundleExpr(String bundleExpr) {
        this.bundleExpr = bundleExpr;
    }

    /**
     * Setter method for "filterArgs" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setFilterArgsExpr(String filterArgsExpr) {
        this.filterArgsExpr = filterArgsExpr;
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
     * Setter method for "header" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setHeaderExpr(String headerExpr) {
        this.headerExpr = headerExpr;
    }

    /**
     * Setter method for "footer" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setFooterExpr(String footerExpr) {
        this.footerExpr = footerExpr;
    }

    /**
     * Setter method for "message" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setMessageExpr(String messageExpr) {
        this.messageExpr = messageExpr;
    }

    /**
     * Resets attribute values for tag reuse.
     */
    public void release() {
        super.release();
        setIdExpr(null);
        setBundleExpr(null);
        setFilterArgsExpr(null);
        setLocaleExpr(null);
        setNameExpr(null);
        setPropertyExpr(null);
        setHeaderExpr(null);
        setFooterExpr(null);
        setMessageExpr(null);
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
        Boolean bool = null;

        if ((string =
                EvalHelper.evalString("id", getIdExpr(), this, pageContext)) != null) {
            setId(string);
        }

        if ((string =
                EvalHelper.evalString("bundle", getBundleExpr(), this,
                    pageContext)) != null) {
            setBundle(string);
        }

        if ((bool =
                EvalHelper.evalBoolean("filterArgs", getFilterArgsExpr(), this,
                    pageContext)) != null) {
            setFilterArgs(bool.booleanValue());
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
                EvalHelper.evalString("header", getHeaderExpr(), this,
                    pageContext)) != null) {
            setHeader(string);
        }

        if ((string =
                EvalHelper.evalString("footer", getFooterExpr(), this,
                    pageContext)) != null) {
            setFooter(string);
        }

        if ((string =
                EvalHelper.evalString("message", getMessageExpr(), this,
                    pageContext)) != null) {
            setMessage(string);
        }
    }
}
