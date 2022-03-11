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

import org.apache.struts.taglib.html.ParamTag;
import org.apache.strutsel.taglib.utils.EvalHelper;

import javax.servlet.jsp.JspException;

/**
 * Adds a new request parameter to its parent {@link ELLinkTag}. This subclass
 * allows all attribute values to be specified as expressions utilizing the
 * JavaServer Pages Standard Library expression language.
 * 
 * @version $Rev$ $Date$
 * @since Struts 1.3.6
 */
public class ELParamTag extends ParamTag {

    /**
     * Instance variable mapped to "name" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String nameExpr;

    /**
     * Instance variable mapped to "value" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String valueExpr;

    /**
     * Getter method for "name" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getNameExpr() {
        return (nameExpr);
    }

    /**
     * Getter method for "value" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getValueExpr() {
        return (valueExpr);
    }

    /**
     * Setter method for "name" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setNameExpr(String nameExpr) {
        this.nameExpr = nameExpr;
    }

    /**
     * Setter method for "value" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setValueExpr(String valueExpr) {
        this.valueExpr = valueExpr;
    }

    /**
     * Resets attribute values for tag reuse.
     */
    public void release() {
        super.release();
        setNameExpr(null);
        setValueExpr(null);
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
    private void evaluateExpressions() throws JspException {
        String string = null;

        if ((string = EvalHelper.evalString("name", getNameExpr(), this,
                pageContext)) != null) {
            setName(string);
        }

        if ((string = EvalHelper.evalString("value", getValueExpr(), this,
                pageContext)) != null) {
            setValue(string);
        }
    }
}
