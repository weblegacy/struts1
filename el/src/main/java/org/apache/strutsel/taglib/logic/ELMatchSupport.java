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

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;

/**
 * This class is used as a helper class for both the <code>org.apache.strutsel.taglib.logic.ELMatchTag</code>
 * and <code>org.apache.strutsel.taglib.logic.ELNotMatchTag</code> classes.
 * It's <code>condition</code> method encapsulates the common logic needed to
 * examine the <code>location</code> attribute to determine how to do the
 * comparison.
 */
class ELMatchSupport {
    /**
     * Performs a comparison of an expression and a value, with an optional
     * location specifier in the expression (start or end).
     *
     * @param desired     Indication of whether the "truth" value of the
     *                    comparison is whether the expression and value are
     *                    equal, or not equal.
     * @param expr        Expression to test against a value.
     * @param value       Value to test against an expression.
     * @param location    if set, is "start" or "end" to indicate to look at
     *                    the start or end of the expression for the value.
     *                    If null, look anywhere in the expression.
     * @param messages    <code>MessageResources</code> object to reference
     *                    for error message text.
     * @param pageContext used to save exception information, if needed.
     * @return true if comparison result equals desired value, false
     *         otherwise.
     */
    public static boolean condition(boolean desired, String expr, String value,
        String location, MessageResources messages, PageContext pageContext)
        throws JspException {
        boolean result = false;

        if (expr != null) {
            // Perform the comparison requested by the location attribute
            boolean matched = false;

            if (location == null) {
                matched = (expr.indexOf(value) >= 0);
            } else if (location.equals("start")) {
                matched = expr.startsWith(value);
            } else if (location.equals("end")) {
                matched = expr.endsWith(value);
            } else {
                JspException e =
                    new JspException(messages.getMessage("logic.location",
                            location));

                TagUtils.getInstance().saveException(pageContext, e);
                throw e;
            }

            result = (matched == desired);
        }

        return (result);
    }
}
