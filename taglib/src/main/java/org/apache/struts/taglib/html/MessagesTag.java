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

import java.util.Iterator;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

/**
 * Custom tag that iterates the elements of a message collection. It defaults
 * to retrieving the messages from <code>Globals.ERROR_KEY</code>, but if the
 * message attribute is set to true then the messages will be retrieved from
 * <code>Globals.MESSAGE_KEY</code>. This is an alternative to the default
 * <code>ErrorsTag</code>.
 *
 * @version $Rev$ $Date: 2005-11-08 23:50:53 -0500 (Tue, 08 Nov 2005)
 *          $
 * @since Struts 1.1
 */
public class MessagesTag extends BodyTagSupport {
    private static final long serialVersionUID = -7665117721919199746L;

    /**
     * The message resources for this package.
     */
    protected static MessageResources messageResources =
        MessageResources.getMessageResources(Constants.Package
            + ".LocalStrings");

    /**
     * Iterator of the elements of this error collection, while we are
     * actually running.
     */
    protected Iterator<ActionMessage> iterator = null;

    /**
     * Whether or not any error messages have been processed.
     */
    protected boolean processed = false;

    /**
     * The name of the scripting variable to be exposed.
     */
    protected String id = null;

    /**
     * The servlet context attribute key for our resources.
     */
    protected String bundle = null;

    /**
     * The session attribute key for our locale.
     */
    protected String locale = Globals.LOCALE_KEY;

    /**
     * The request attribute key for our error messages (if any).
     */
    protected String name = Globals.ERROR_KEY;

    /**
     * The name of the property for which error messages should be returned,
     * or <code>null</code> to return all errors.
     */
    protected String property = null;

    /**
     * The message resource key for errors header.
     */
    protected String header = null;

    /**
     * The message resource key for errors footer.
     */
    protected String footer = null;

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

    /**
     * Filter the message replacement values for characters that are
     * sensitive in HTML? Default is <code>false</code>.
     */
    protected boolean filterArgs = false;

    public String getId() {
        return (this.id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBundle() {
        return (this.bundle);
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public String getLocale() {
        return (this.locale);
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return (this.property);
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getHeader() {
        return (this.header);
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return (this.footer);
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getMessage() {
        return (this.message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getFilterArgs() {
        return (this.filterArgs);
    }

    public void setFilterArgs(boolean filterArgs) {
        this.filterArgs = filterArgs;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    /**
     * Construct an iterator for the specified collection, and begin looping
     * through the body once per element.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        // Initialize for a new request.
        processed = false;

        // Were any messages specified?
        ActionMessages messages = null;

        // Make a local copy of the name attribute that we can modify.
        String name = this.name;

        if ((message != null) && "true".equalsIgnoreCase(message)) {
            name = Globals.MESSAGE_KEY;
        }

        try {
            messages =
                TagUtils.getInstance().getActionMessages(pageContext, name);
        } catch (JspException e) {
            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        // Acquire the collection we are going to iterate over
        int size;
        if (property == null) {
            this.iterator = messages.get();
            size = messages.size();
        } else {
            this.iterator = messages.get(property);
            size = messages.size(property);
        }

        // Expose the count when specified
        if (count != null) {
            pageContext.setAttribute(count, Integer.valueOf(size));
        }

        // Store the first value and evaluate, or skip the body if none
        if (!this.iterator.hasNext()) {
            return SKIP_BODY;
        }

        // process the first message
        processMessage(iterator.next());

        if ((header != null) && (header.length() > 0)) {
            String headerMessage =
                TagUtils.getInstance().message(pageContext, bundle, locale,
                    header);

            if (headerMessage != null) {
                TagUtils.getInstance().write(pageContext, headerMessage);
            }
        }

        // Set the processed variable to true so the
        // doEndTag() knows processing took place
        processed = true;

        return (EVAL_BODY_BUFFERED);
    }

    /**
     * Make the next collection element available and loop, or finish the
     * iterations if there are no more elements.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doAfterBody() throws JspException {
        // Render the output from this iteration to the output stream
        if (bodyContent != null) {
            TagUtils.getInstance().writePrevious(pageContext,
                bodyContent.getString());
            bodyContent.clearBody();
        }

        // Decide whether to iterate or quit
        if (iterator.hasNext()) {
            processMessage(iterator.next());

            return (EVAL_BODY_BUFFERED);
        } else {
            return (SKIP_BODY);
        }
    }

    /**
     * Process a message.
     */
    private void processMessage(ActionMessage report)
        throws JspException {
        String msg = null;

        if (report.isResource()) {
            Object[] values = report.getValues();
            if (filterArgs) {
                values = filterMessageReplacementValues(values);
            }

            msg = TagUtils.getInstance().message(pageContext, bundle, locale,
                    report.getKey(), values);

            if (msg == null) {
                String bundleName = (bundle == null) ? "default" : bundle;

                msg = messageResources.getMessage("messagesTag.notfound",
                        report.getKey(), bundleName);
            }
        } else {
            msg = report.getKey();
        }

        if (msg == null) {
            pageContext.removeAttribute(id);
        } else {
            pageContext.setAttribute(id, msg);
        }
    }

    /**
     * Performs filtering on the elements of specified Array.
     * Filtering is only performed on elements which are instances of
     * <code>String</code>.
     *
     * @param values The message values to be filtered
     */
    private Object[] filterMessageReplacementValues(Object[] values) {
       if (values == null) {
           return (null);
       }

       Object[] filteredArgs = new Object[values.length];
       for (int i = 0; i < values.length; ++i) {
           if (values[i] instanceof String) {
               filteredArgs[i] = TagUtils.getInstance().filter((String) values[i]);
           } else {
               filteredArgs[i] = values[i];
           }
       }

       return filteredArgs;
    }

    /**
     * Clean up after processing this enumeration.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
        if (processed && (footer != null) && (footer.length() > 0)) {
            String footerMessage =
                TagUtils.getInstance().message(pageContext, bundle, locale,
                    footer);

            if (footerMessage != null) {
                TagUtils.getInstance().write(pageContext, footerMessage);
            }
        }

        if (count != null) {
            pageContext.removeAttribute(count);
        }

        return EVAL_PAGE;
    }

    /**
     * Release all allocated resources.
     */
    public void release() {
        super.release();
        iterator = null;
        processed = false;
        id = null;
        bundle = null;
        locale = Globals.LOCALE_KEY;
        name = Globals.ERROR_KEY;
        property = null;
        header = null;
        footer = null;
        message = null;
        filterArgs = false;
        count = null;
    }
}