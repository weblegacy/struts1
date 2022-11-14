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
package org.apache.strutsel.taglib.logic;

import jakarta.servlet.jsp.JspException;

import org.apache.struts.taglib.logic.MatchTag;

/**
 * Evaluate the nested body content of this tag if the specified value is a
 * substring of the specified variable.
 * <p>This class is a subclass of the class
 * {@code org.apache.struts.taglib.logic.MatchTag} which provides most of
 * the described functionality. This subclass allows all attribute values to
 * be specified as expressions utilizing the JavaServer Pages Standard
 * Library expression language.</p>
 *
 * @version $Rev$
 */
public class ELMatchTag extends MatchTag {
    private static final long serialVersionUID = -8525557473502640319L;

    /**
     * Evaluated value of expression.
     */
    private String expr;

    /**
     * Returns the evaluated expression.
     */
    public String getExpr() {
        return expr;
    }

    /**
     * Sets the evaluated expression.
     */
    public void setExpr(String expr) {
        this.expr = expr;
    }

    /**
     * Releases state of custom tag so this instance can be reused.
     */
    public void release() {
        super.release();
        setExpr(null);
    }

    /**
     * Evaluates the condition that is being tested by this particular tag,
     * and returns {@code true} if the nested body content of this tag
     * should be evaluated, or {@code false} if it should be skipped.
     *
     * @param desired Desired value for a true result
     * @throws JspException if a JSP exception occurs
     */
    protected boolean condition(boolean desired)
        throws JspException {
        boolean result = false;

        if (getExpr() != null) {
            result =
                ELMatchSupport.condition(desired, getExpr(), value,
                    location, messages, pageContext);
        } else {
            result = super.condition(desired);
        }

        return result;
    }
}