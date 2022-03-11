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
package org.apache.strutsel.taglib.tiles;

import org.apache.struts.tiles.taglib.UseAttributeTag;
import org.apache.strutsel.taglib.utils.EvalHelper;

import javax.servlet.jsp.JspException;

/**
 * Custom tag exposing a component attribute to page. <p> This class is a
 * subclass of the class <code>org.apache.struts.taglib.tiles.UseAttributeTag</code>
 * which provides most of the described functionality.  This subclass allows
 * all attribute values to be specified as expressions utilizing the
 * JavaServer Pages Standard Library expression language.
 *
 * @version $Rev$
 */
public class ELUseAttributeTag extends UseAttributeTag {
    /**
     * Instance variable mapped to "id" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String idExpr;

    /**
     * Instance variable mapped to "classname" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String classnameExpr;

    /**
     * Instance variable mapped to "scope" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String scopeExpr;

    /**
     * Instance variable mapped to "name" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String nameExpr;

    /**
     * Instance variable mapped to "ignore" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String ignoreExpr;

    /**
     * Getter method for "id" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getIdExpr() {
        return (idExpr);
    }

    /**
     * Getter method for "classname" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getClassnameExpr() {
        return (classnameExpr);
    }

    /**
     * Getter method for "scope" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getScopeExpr() {
        return (scopeExpr);
    }

    /**
     * Getter method for "name" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getNameExpr() {
        return (nameExpr);
    }

    /**
     * Getter method for "ignore" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getIgnoreExpr() {
        return (ignoreExpr);
    }

    /**
     * Setter method for "id" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setIdExpr(String idExpr) {
        this.idExpr = idExpr;
    }

    /**
     * Setter method for "classname" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setClassnameExpr(String classnameExpr) {
        this.classnameExpr = classnameExpr;
    }

    /**
     * Setter method for "scope" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setScopeExpr(String scopeExpr) {
        this.scopeExpr = scopeExpr;
    }

    /**
     * Setter method for "name" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setNameExpr(String nameExpr) {
        this.nameExpr = nameExpr;
    }

    /**
     * Setter method for "ignore" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setIgnoreExpr(String ignoreExpr) {
        this.ignoreExpr = ignoreExpr;
    }

    /**
     * Resets attribute values for tag reuse.
     */
    public void release() {
        super.release();
        setIdExpr(null);
        setClassnameExpr(null);
        setScopeExpr(null);
        setNameExpr(null);
        setIgnoreExpr(null);
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
                EvalHelper.evalString("classname", getClassnameExpr(), this,
                    pageContext)) != null) {
            setClassname(string);
        }

        if ((string =
                EvalHelper.evalString("scope", getScopeExpr(), this, pageContext)) != null) {
            setScope(string);
        }

        if ((string =
                EvalHelper.evalString("name", getNameExpr(), this, pageContext)) != null) {
            setName(string);
        }

        if ((bool =
                EvalHelper.evalBoolean("ignore", getIgnoreExpr(), this,
                    pageContext)) != null) {
            setIgnore(bool.booleanValue());
        }
    }
}
