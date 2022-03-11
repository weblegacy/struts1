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

import org.apache.struts.taglib.bean.SizeTag;
import org.apache.strutsel.taglib.utils.EvalHelper;

import javax.servlet.jsp.JspException;

/**
 * Define a scripting variable that will contain the number of elements found
 * in a specified array, Collection, or Map. <p> This class is a subclass of
 * the class <code>org.apache.struts.taglib.bean.SizeTag</code> which provides
 * most of the described functionality.  This subclass allows all attribute
 * values to be specified as expressions utilizing the JavaServer Pages
 * Standard Library expression language.
 *
 * @version $Rev$
 */
public class ELSizeTag extends SizeTag {
    /**
     * Instance variable mapped to "collection" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String collectionExpr;

    /**
     * Instance variable mapped to "id" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String idExpr;

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
     * Getter method for "collection" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getCollectionExpr() {
        return (collectionExpr);
    }

    /**
     * Getter method for "id" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getIdExpr() {
        return (idExpr);
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
     * Setter method for "collection" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setCollectionExpr(String collectionExpr) {
        this.collectionExpr = collectionExpr;
    }

    /**
     * Setter method for "id" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setIdExpr(String idExpr) {
        this.idExpr = idExpr;
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
     * Releases state of custom tag so this instance can be reused.
     */
    public void release() {
        super.release();
        setCollectionExpr(null);
        setIdExpr(null);
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
        Object object = null;

        if ((object =
                EvalHelper.eval("collection", getCollectionExpr(), this,
                    pageContext)) != null) {
            setCollection(object);
        }

        if ((string =
                EvalHelper.evalString("id", getIdExpr(), this, pageContext)) != null) {
            setId(string);
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
