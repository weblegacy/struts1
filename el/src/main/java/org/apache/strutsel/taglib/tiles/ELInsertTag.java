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

import org.apache.struts.tiles.taglib.InsertTag;
import org.apache.strutsel.taglib.utils.EvalHelper;

import javax.servlet.jsp.JspException;

/**
 * This is the tag handler for &lt;tiles:insert&gt;, which includes a
 * template. The tag's body content consists of &lt;tiles:put&gt; tags, which
 * are accessed by &lt;tiles:get&gt; in the template. <p> This class is a
 * subclass of the class <code>org.apache.struts.taglib.tiles.InsertTag</code>
 * which provides most of the described functionality.  This subclass allows
 * all attribute values to be specified as expressions utilizing the
 * JavaServer Pages Standard Library expression language.
 *
 * @version $Rev$
 */
public class ELInsertTag extends InsertTag {
    /**
     * Instance variable mapped to "template" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String templateExpr;

    /**
     * Instance variable mapped to "component" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String componentExpr;

    /**
     * Instance variable mapped to "page" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String pageExpr;

    /**
     * Instance variable mapped to "definition" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String definitionExpr;

    /**
     * Instance variable mapped to "attribute" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String attributeExpr;

    /**
     * Instance variable mapped to "name" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String nameExpr;

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
     * Instance variable mapped to "flush" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String flushExpr;

    /**
     * (Mapping set in associated BeanInfo class.) Instance variable mapped to
     * "ignore" tag attribute.
     */
    private String ignoreExpr;

    /**
     * Instance variable mapped to "role" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String roleExpr;

    /**
     * Instance variable mapped to "controllerUrl" tag attribute. (Mapping set
     * in associated BeanInfo class.)
     */
    private String controllerUrlExpr;

    /**
     * Instance variable mapped to "controllerClass" tag attribute. (Mapping
     * set in associated BeanInfo class.)
     */
    private String controllerClassExpr;

    /**
     * Getter method for "template" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getTemplateExpr() {
        return (templateExpr);
    }

    /**
     * Getter method for "component" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getComponentExpr() {
        return (componentExpr);
    }

    /**
     * Getter method for "page" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getPageExpr() {
        return (pageExpr);
    }

    /**
     * Getter method for "definition" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getDefinitionExpr() {
        return (definitionExpr);
    }

    /**
     * Getter method for "attribute" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getAttributeExpr() {
        return (attributeExpr);
    }

    /**
     * Getter method for "name" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getNameExpr() {
        return (nameExpr);
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
     * Getter method for "flush" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getFlushExpr() {
        return (flushExpr);
    }

    /**
     * Getter method for "ignore" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getIgnoreExpr() {
        return (ignoreExpr);
    }

    /**
     * Getter method for "role" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getRoleExpr() {
        return (roleExpr);
    }

    /**
     * Getter method for "controllerUrl" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getControllerUrlExpr() {
        return (controllerUrlExpr);
    }

    /**
     * Getter method for "controllerClass" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getControllerClassExpr() {
        return (controllerClassExpr);
    }

    /**
     * Setter method for "template" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setTemplateExpr(String templateExpr) {
        this.templateExpr = templateExpr;
    }

    /**
     * Setter method for "component" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setComponentExpr(String componentExpr) {
        this.componentExpr = componentExpr;
    }

    /**
     * Setter method for "page" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setPageExpr(String pageExpr) {
        this.pageExpr = pageExpr;
    }

    /**
     * Setter method for "definition" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setDefinitionExpr(String definitionExpr) {
        this.definitionExpr = definitionExpr;
    }

    /**
     * Setter method for "attribute" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setAttributeExpr(String attributeExpr) {
        this.attributeExpr = attributeExpr;
    }

    /**
     * Setter method for "name" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setNameExpr(String nameExpr) {
        this.nameExpr = nameExpr;
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
     * Setter method for "flush" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setFlushExpr(String flushExpr) {
        this.flushExpr = flushExpr;
    }

    /**
     * Setter method for "ignore" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setIgnoreExpr(String ignoreExpr) {
        this.ignoreExpr = ignoreExpr;
    }

    /**
     * Setter method for "role" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setRoleExpr(String roleExpr) {
        this.roleExpr = roleExpr;
    }

    /**
     * Setter method for "controllerUrl" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setControllerUrlExpr(String controllerUrlExpr) {
        this.controllerUrlExpr = controllerUrlExpr;
    }

    /**
     * Setter method for "controllerClass" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setControllerClassExpr(String controllerClassExpr) {
        this.controllerClassExpr = controllerClassExpr;
    }

    /**
     * Resets attribute values for tag reuse.
     */
    public void release() {
        super.release();
        setTemplateExpr(null);
        setComponentExpr(null);
        setPageExpr(null);
        setDefinitionExpr(null);
        setAttributeExpr(null);
        setNameExpr(null);
        setBeanNameExpr(null);
        setBeanPropertyExpr(null);
        setBeanScopeExpr(null);
        setFlushExpr(null);
        setIgnoreExpr(null);
        setRoleExpr(null);
        setControllerUrlExpr(null);
        setControllerClassExpr(null);
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
                EvalHelper.evalString("template", getTemplateExpr(), this,
                    pageContext)) != null) {
            setTemplate(string);
        }

        if ((string =
                EvalHelper.evalString("component", getComponentExpr(), this,
                    pageContext)) != null) {
            setComponent(string);
        }

        if ((string =
                EvalHelper.evalString("page", getPageExpr(), this, pageContext)) != null) {
            setPage(string);
        }

        if ((string =
                EvalHelper.evalString("definition", getDefinitionExpr(), this,
                    pageContext)) != null) {
            setDefinition(string);
        }

        if ((string =
                EvalHelper.evalString("attribute", getAttributeExpr(), this,
                    pageContext)) != null) {
            setAttribute(string);
        }

        if ((string =
                EvalHelper.evalString("name", getNameExpr(), this, pageContext)) != null) {
            setName(string);
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
                EvalHelper.evalString("flush", getFlushExpr(), this, pageContext)) != null) {
            setFlush(string);
        }

        if ((bool =
                EvalHelper.evalBoolean("ignore", getIgnoreExpr(), this,
                    pageContext)) != null) {
            setIgnore(bool.booleanValue());
        }

        if ((string =
                EvalHelper.evalString("role", getRoleExpr(), this, pageContext)) != null) {
            setRole(string);
        }

        if ((string =
                EvalHelper.evalString("controllerUrl", getControllerUrlExpr(),
                    this, pageContext)) != null) {
            setControllerUrl(string);
        }

        if ((string =
                EvalHelper.evalString("controllerClass",
                    getControllerClassExpr(), this, pageContext)) != null) {
            setControllerClass(string);
        }
    }
}
