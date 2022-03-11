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

import org.apache.struts.tiles.taglib.AddTag;
import org.apache.strutsel.taglib.utils.EvalHelper;

import javax.servlet.jsp.JspException;

/**
 * Adds an element to the surrounding list tag.  Same syntax as
 * <code>&lt;put&gt;</code>. <p> This class is a subclass of the class
 * <code>org.apache.struts.taglib.tiles.AddTag</code> which provides most of
 * the described functionality.  This subclass allows all attribute values to
 * be specified as expressions utilizing the JavaServer Pages Standard Library
 * expression language.
 *
 * @version $Rev$
 */
public class ELAddTag extends AddTag {
    /**
     * Instance variable mapped to "value" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String valueExpr;

    /**
     * Instance variable mapped to "content" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String contentExpr;

    /**
     * Instance variable mapped to "direct" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String directExpr;

    /**
     * Instance variable mapped to "type" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String typeExpr;

    /**
     * Instance variable mapped to "beanName" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String beanNameExpr;

    /**
     * Instance variable mapped to "beanProperty" tag attribute. (Mapping set
     * in associated BeanInfo class.)
     */
    private String beanPropertyExpr;

    /**
     * Instance variable mapped to "beanScope" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String beanScopeExpr;

    /**
     * Instance variable mapped to "role" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String roleExpr;

    /**
     * Getter method for "value" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getValueExpr() {
        return (valueExpr);
    }

    /**
     * Getter method for "content" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getContentExpr() {
        return (contentExpr);
    }

    /**
     * Getter method for "direct" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getDirectExpr() {
        return (directExpr);
    }

    /**
     * Getter method for "type" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getTypeExpr() {
        return (typeExpr);
    }

    /**
     * Getter method for "beanName" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getBeanNameExpr() {
        return (beanNameExpr);
    }

    /**
     * Getter method for "beanProperty" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getBeanPropertyExpr() {
        return (beanPropertyExpr);
    }

    /**
     * Getter method for "beanScope" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getBeanScopeExpr() {
        return (beanScopeExpr);
    }

    /**
     * Getter method for "role" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getRoleExpr() {
        return (roleExpr);
    }

    /**
     * Setter method for "value" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setValueExpr(String valueExpr) {
        this.valueExpr = valueExpr;
    }

    /**
     * Setter method for "content" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setContentExpr(String contentExpr) {
        this.contentExpr = contentExpr;
    }

    /**
     * Setter method for "direct" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setDirectExpr(String directExpr) {
        this.directExpr = directExpr;
    }

    /**
     * Setter method for "type" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setTypeExpr(String typeExpr) {
        this.typeExpr = typeExpr;
    }

    /**
     * Setter method for "beanName" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setBeanNameExpr(String beanNameExpr) {
        this.beanNameExpr = beanNameExpr;
    }

    /**
     * Setter method for "beanProperty" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setBeanPropertyExpr(String beanPropertyExpr) {
        this.beanPropertyExpr = beanPropertyExpr;
    }

    /**
     * Setter method for "beanScope" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setBeanScopeExpr(String beanScopeExpr) {
        this.beanScopeExpr = beanScopeExpr;
    }

    /**
     * Setter method for "role" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setRoleExpr(String roleExpr) {
        this.roleExpr = roleExpr;
    }

    /**
     * Resets attribute values for tag reuse.
     */
    public void release() {
        super.release();
        setValueExpr(null);
        setContentExpr(null);
        setDirectExpr(null);
        setTypeExpr(null);
        setBeanNameExpr(null);
        setBeanPropertyExpr(null);
        setBeanScopeExpr(null);
        setRoleExpr(null);
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
                EvalHelper.evalString("value", getValueExpr(), this, pageContext)) != null) {
            setValue(string);
        }

        if ((string =
                EvalHelper.evalString("content", getContentExpr(), this,
                    pageContext)) != null) {
            setContent(string);
        }

        if ((string =
                EvalHelper.evalString("direct", getDirectExpr(), this,
                    pageContext)) != null) {
            setDirect(string);
        }

        if ((string =
                EvalHelper.evalString("type", getTypeExpr(), this, pageContext)) != null) {
            setType(string);
        }

        if ((string =
                EvalHelper.evalString("beanName", getBeanNameExpr(), this,
                    pageContext)) != null) {
            setBeanName(string);
        }

        if ((string =
                EvalHelper.evalString("beanProperty", getBeanPropertyExpr(),
                    this, pageContext)) != null) {
            setBeanProperty(string);
        }

        if ((string =
                EvalHelper.evalString("beanScope", getBeanScopeExpr(), this,
                    pageContext)) != null) {
            setBeanScope(string);
        }

        if ((string =
                EvalHelper.evalString("role", getRoleExpr(), this, pageContext)) != null) {
            setRole(string);
        }
    }
}
