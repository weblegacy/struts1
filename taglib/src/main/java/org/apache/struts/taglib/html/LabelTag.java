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
package org.apache.struts.taglib.html;

import org.apache.struts.taglib.TagUtils;

import jakarta.servlet.jsp.JspException;

/**
 * Renders an HTML LABEL tag within the Struts framework.
 *
 * @version $Rev$
 * @since Struts 1.4
 */
public class LabelTag extends BaseInputTag {
    private static final long serialVersionUID = 4783688183017577922L;

    // ----------------------------------------------------- Instance Variables

    protected String forId = null;

    protected String key = null;

    protected boolean required = false;

    /**
     * The body content of this tag (if any).
     */
    protected String text = null;

    // ------------------------------------------------------------- Properties

    public String getForId() {
        return this.forId;
    }

    public String getKey() {
        return this.key;
    }

    public boolean getRequired() {
        return this.required;
    }

    public void setForId(String forId) {
        this.forId = forId;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    // ----------------------------------------------------- Constructor

    public LabelTag() {
        super();
    }

    // --------------------------------------------------------- Methods

    /**
     * Render the beginning of the hyperlink.
     * <p>
     * Support for indexed property since Struts 1.1
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        // Evaluate the body of this tag
        this.text = null;

        return (EVAL_BODY_BUFFERED);
    }

    /**
     * Save the associated label from the body content.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doAfterBody() throws JspException {
        if (this.bodyContent != null) {
            String value = this.bodyContent.getString().trim();
            if (value.length() > 0) {
                this.text = value;
            }
        }

        return (SKIP_BODY);
    }

    /**
     * Render the end of the hyperlink.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
        // Generate the opening element
        StringBuilder results = new StringBuilder("<label");
        prepareAttribute(results, "accesskey", getAccesskey());
        prepareAttribute(results, "for", getForId() != null ? getForId()
                : prepareName());
        prepareAttribute(results, "tabindex", getTabindex());
        prepareAttribute(results, "title", getTitle());
        results.append(prepareStyles());
        results.append(prepareEventHandlers());
        prepareFocusEvents(results);
        prepareOtherAttributes(results);
        results.append(">");

        // Prepare the label value
        this.value = message(this.text, this.key);
        prepareValue(results);

        // End tag
        results.append("</label>");
        TagUtils.getInstance().write(this.pageContext, results.toString());

        return (EVAL_PAGE);
    }

    /**
     * Returns the CSS style class that indicates a "required" styling. If no
     * styling is wanted, return <code>null</code>.
     *
     * @return the style class; can be <code>null</code>
     * @see #prepareAttribute(StringBuilder, String, Object)
     */
    protected String getRequiredStyleClass() {
        return "required";
    }

    /**
     * If this label is describes a required field, then the CSS style class
     * attribute gets appended with the "required" style, if not null, which
     * takes effect for both the normal and error style.
     *
     * @see #getRequiredStyleClass()
     * @see #prepareValue(StringBuilder)
     */
    protected void prepareAttribute(StringBuilder handlers, String name,
            Object value) {

        if ("class".equals(name) && this.required) {
            String requiredStyleClass = getRequiredStyleClass();
            if (requiredStyleClass != null) {
                value = (value != null) ? (value + " " + requiredStyleClass)
                        : requiredStyleClass;
            }
        }
        super.prepareAttribute(handlers, name, value);
    }

    /**
     * Performs any pre-processing on the <code>value</code> property before
     * printing it. The default behavior is to append an asterik inside a
     * <code>span</code> tag with a CSS class attribute of
     * {@link #getRequiredStyleClass()}, if the <code>required</code>
     * property is set.
     *
     * @param handlers The StringBuilder that output will be appended to.
     * @see #getRequiredStyleClass()
     */
    protected void prepareValue(StringBuilder handlers) {
        handlers.append(this.value);
        if (this.required) {
            handlers.append(" <span class=\"");
            handlers.append(getRequiredStyleClass());
            handlers.append("\">*</span>");
        }
    }

    /**
     * Release any acquired resources.
     */
    public void release() {
        super.release();
        this.forId = null;
        this.key = null;
        this.required = false;
        this.text = null;
    }
}
