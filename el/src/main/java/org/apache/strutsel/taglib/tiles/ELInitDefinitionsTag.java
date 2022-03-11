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

import org.apache.struts.tiles.taglib.InitDefinitionsTag;
import org.apache.strutsel.taglib.utils.EvalHelper;

import javax.servlet.jsp.JspException;

/**
 * Init definitions factory. <p> This class is a subclass of the class
 * <code>org.apache.struts.taglib.tiles.InitDefinitionsTag</code> which
 * provides most of the described functionality.  This subclass allows all
 * attribute values to be specified as expressions utilizing the JavaServer
 * Pages Standard Library expression language.
 *
 * @version $Rev$
 */
public class ELInitDefinitionsTag extends InitDefinitionsTag {
    /**
     * Instance variable mapped to "file" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String fileExpr;

    /**
     * Instance variable mapped to "classname" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String classnameExpr;

    /**
     * Getter method for "file" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getFileExpr() {
        return (fileExpr);
    }

    /**
     * Getter method for "classname" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getClassnameExpr() {
        return (classnameExpr);
    }

    /**
     * Setter method for "file" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setFileExpr(String fileExpr) {
        this.fileExpr = fileExpr;
    }

    /**
     * Setter method for "classname" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setClassnameExpr(String classnameExpr) {
        this.classnameExpr = classnameExpr;
    }

    /**
     * Resets attribute values for tag reuse.
     */
    public void release() {
        super.release();
        setFileExpr(null);
        setClassnameExpr(null);
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
                EvalHelper.evalString("file", getFileExpr(), this, pageContext)) != null) {
            setFile(string);
        }

        if ((string =
                EvalHelper.evalString("classname", getClassnameExpr(), this,
                    pageContext)) != null) {
            setClassname(string);
        }
    }
}
