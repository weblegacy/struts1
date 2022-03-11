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

import org.apache.struts.tiles.taglib.GetAttributeTag;
import org.apache.strutsel.taglib.utils.EvalHelper;

import javax.servlet.jsp.JspException;

/**
 * This is the tag handler for &lt;tiles-el:get&gt;, which gets content from
 * the request scope and either includes the content or prints it, depending
 * upon the value of the content's <code>direct</code> attribute. <p> This tag
 * is intended to be compatible with the same tag from Templates (David
 * Geary).  Implementation extends InsertTag for facility (no so well). The
 * only difference is the default value of attribute 'ignore', which is
 * <code>true</code> for this tag (default behavior of David Geary's
 * templates). <p> This class is a subclass of the class
 * <code>org.apache.struts.taglib.tiles.GetAttributeTag</code> which provides
 * most of the described functionality.  This subclass allows all attribute
 * values to be specified as expressions utilizing the JavaServer Pages
 * Standard Library expression language.
 *
 * @version $Rev$
 */
public class ELGetAttributeTag extends GetAttributeTag {
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
     * Instance variable mapped to "role" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String roleExpr;

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
     * Getter method for "role" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getRoleExpr() {
        return (roleExpr);
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
        setNameExpr(null);
        setIgnoreExpr(null);
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
        Boolean bool = null;

        if ((string =
                EvalHelper.evalString("name", getNameExpr(), this, pageContext)) != null) {
            setName(string);
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
    }
}
