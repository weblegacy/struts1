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

import org.apache.struts.taglib.bean.StrutsTag;
import org.apache.strutsel.taglib.utils.EvalHelper;

import javax.servlet.jsp.JspException;

/**
 * Define a scripting variable that exposes the requested Struts internal
 * configuraton object. <p> This class is a subclass of the class
 * <code>org.apache.struts.taglib.bean.StrutsTag</code> which provides most of
 * the described functionality.  This subclass allows all attribute values to
 * be specified as expressions utilizing the JavaServer Pages Standard Library
 * expression language.
 *
 * @version $Rev$
 */
public class ELStrutsTag extends StrutsTag {
    /**
     * Instance variable mapped to "id" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String idExpr;

    /**
     * Instance variable mapped to "formBean" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String formBeanExpr;

    /**
     * Instance variable mapped to "forward" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String forwardExpr;

    /**
     * Instance variable mapped to "mapping" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String mappingExpr;

    /**
     * Getter method for "id" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getIdExpr() {
        return (idExpr);
    }

    /**
     * Getter method for "formBean" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getFormBeanExpr() {
        return (formBeanExpr);
    }

    /**
     * Getter method for "forward" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getForwardExpr() {
        return (forwardExpr);
    }

    /**
     * Getter method for "mapping" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getMappingExpr() {
        return (mappingExpr);
    }

    /**
     * Setter method for "id" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setIdExpr(String idExpr) {
        this.idExpr = idExpr;
    }

    /**
     * Setter method for "formBean" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setFormBeanExpr(String formBeanExpr) {
        this.formBeanExpr = formBeanExpr;
    }

    /**
     * Setter method for "forward" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setForwardExpr(String forwardExpr) {
        this.forwardExpr = forwardExpr;
    }

    /**
     * Setter method for "mapping" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setMappingExpr(String mappingExpr) {
        this.mappingExpr = mappingExpr;
    }

    /**
     * Resets attribute values for tag reuse.
     */
    public void release() {
        super.release();
        setIdExpr(null);
        setFormBeanExpr(null);
        setForwardExpr(null);
        setMappingExpr(null);
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
                EvalHelper.evalString("id", getIdExpr(), this, pageContext)) != null) {
            setId(string);
        }

        if ((string =
                EvalHelper.evalString("formBean", getFormBeanExpr(), this,
                    pageContext)) != null) {
            setFormBean(string);
        }

        if ((string =
                EvalHelper.evalString("forward", getForwardExpr(), this,
                    pageContext)) != null) {
            setForward(string);
        }

        if ((string =
                EvalHelper.evalString("mapping", getMappingExpr(), this,
                    pageContext)) != null) {
            setMapping(string);
        }
    }
}
