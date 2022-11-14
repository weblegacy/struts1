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
package org.apache.struts.taglib.logic;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;

import jakarta.servlet.jsp.JspException;

import java.util.Iterator;

/**
 * Evaluate to <code>true</code> if an <code>ActionMessages</code> class or a
 * class that can be converted to an <code>ActionMessages</code> class is in
 * request scope under the specified key and there is at least one message in
 * the class or for the property specified.
 *
 * @version $Rev$ $Date: 2005-11-19 12:36:20 -0500 (Sat, 19 Nov 2005)
 *          $
 * @since Struts 1.1
 */
public class MessagesPresentTag extends ConditionalTagBase {
    private static final long serialVersionUID = 4848602425179872796L;

    /**
     * If this is set to 'true', then the <code>Globals.MESSAGE_KEY</code>
     * will be used to retrieve the messages from scope.
     */
    protected String message = null;

    /**
     * The name of the page-scoped attribute to be populated
     * with the message count of the specifie dproperty.
     */
    protected String count;

    public MessagesPresentTag() {
        name = Globals.ERROR_KEY;
    }

    public String getMessage() {
        return (this.message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    /**
     * Evaluate the condition that is being tested by this particular tag, and
     * return <code>true</code> if the nested body content of this tag should
     * be evaluated, or <code>false</code> if it should be skipped.
     *
     * @throws JspException if a JSP exception occurs
     */
    protected boolean condition()
        throws JspException {
        return (condition(true));
    }

    /**
     * Evaluate the condition that is being tested by this particular tag, and
     * return <code>true</code> if there is at least one message in the class
     * or for the property specified.
     *
     * @param desired Desired outcome for a true result
     * @throws JspException if a JSP exception occurs
     */
    protected boolean condition(boolean desired)
        throws JspException {
        ActionMessages am = null;

        String key = name;

        if ((message != null) && "true".equalsIgnoreCase(message)) {
            key = Globals.MESSAGE_KEY;
        }

        try {
            am = TagUtils.getInstance().getActionMessages(pageContext, key);
        } catch (JspException e) {
            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        // Acquire the collection of messages
        Iterator<ActionMessage> iterator;
        int size;
        if (property == null) {
            iterator = am.get();
            size = am.size();
        } else {
            iterator = am.get(property);
            size = am.size(property);
        }

        // Expose the count when specified
        if (count != null) {
            pageContext.setAttribute(count, Integer.valueOf(size));
        }

        return (iterator.hasNext() == desired);
    }

    public int doEndTag() throws JspException {
        if (count != null) {
            pageContext.removeAttribute(count);
        }
        return super.doEndTag();
    }

    /**
     * Release all allocated resources.
     */
    public void release() {
        super.release();
        name = Globals.ERROR_KEY;
        message = null;
        count = null;
    }
}
