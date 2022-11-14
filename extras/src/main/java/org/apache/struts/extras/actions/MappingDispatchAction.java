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
package org.apache.struts.extras.actions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>An abstract <strong>Action</strong> that dispatches to a public method
 * that is named by the <code>parameter</code> attribute of the corresponding
 * ActionMapping.  This is useful for developers who prefer to combine many
 * related actions into a single Action class.</p>
 *
 * <p>To configure the use of this action in your <code>struts-config.xml</code>
 * file, create an entry like this:</p>
 *
 * <pre><code>
 *   &lt;action path="/saveSubscription"
 *           type="org.example.SubscriptionAction"
 *           name="subscriptionForm"
 *          scope="request"
 *          input="/subscription.jsp"
 *      parameter="method"/&gt;
 * </code></pre>
 *
 * <p>where 'method' is the name of a method in your subclass of
 * MappingDispatchAction that has the same signature (other than method name)
 * of the standard Action.execute method.  For example, you might combine the
 * methods for managing a subscription into a  single MappingDispatchAction
 * class using the following methods:</p>
 *
 * <ul>
 *
 * <li>public ActionForward create(ActionMapping mapping, ActionForm form,
 * HttpServletRequest request, HttpServletResponse response) throws
 * Exception</li>
 *
 * <li>public ActionForward edit(ActionMapping mapping, ActionForm form,
 * HttpServletRequest request, HttpServletResponse response) throws
 * Exception</li>
 *
 * <li>public ActionForward save(ActionMapping mapping, ActionForm form,
 * HttpServletRequest request, HttpServletResponse response) throws
 * Exception</li>
 *
 * <li>public ActionForward delete(ActionMapping mapping, ActionForm form,
 * HttpServletRequest request, HttpServletResponse response) throws
 * Exception</li>
 *
 * <li>public ActionForward list(ActionMapping mapping, ActionForm form,
 * HttpServletRequest request, HttpServletResponse response) throws
 * Exception</li>
 *
 * </ul>
 *
 * <p>for which you would create corresponding &lt;action&gt; configurations
 * that reference this class:</p>
 *
 * <pre><code>
 *  &lt;action path="/createSubscription"
 *          type="org.example.SubscriptionAction"
 *          parameter="create"&gt;
 *      &lt;forward name="success" path="/editSubscription.jsp"/&gt;
 *  &lt;/action&gt;
 *
 *  &lt;action path="/editSubscription"
 *          type="org.example.SubscriptionAction"
 *          parameter="edit"&gt;
 *      &lt;forward name="success" path="/editSubscription.jsp"/&gt;
 *  &lt;/action&gt;
 *
 *  &lt;action path="/saveSubscription"
 *          type="org.example.SubscriptionAction"
 *          parameter="save"
 *          name="subscriptionForm"
 *          validate="true"
 *          input="/editSubscription.jsp"
 *          scope="request"&gt;
 *      &lt;forward name="success" path="/savedSubscription.jsp"/&gt;
 *  &lt;/action&gt;
 *
 *  &lt;action path="/deleteSubscription"
 *          type="org.example.SubscriptionAction"
 *          name="subscriptionForm"
 *          scope="request"
 *          input="/subscription.jsp"
 *          parameter="delete"&gt;
 *      &lt;forward name="success" path="/deletedSubscription.jsp"/&gt;
 *  &lt;/action&gt;
 *
 *  &lt;action path="/listSubscriptions"
 *          type="org.example.SubscriptionAction"
 *          parameter="list"&gt;
 *      &lt;forward name="success" path="/subscriptionList.jsp"/&gt;
 *  &lt;/action&gt;
 * </code></pre>
 *
 * <p><strong>NOTE</strong> - Unlike DispatchAction, mapping characteristics
 * may differ between the various handlers, so you can combine actions in the
 * same class that, for example, differ in their use of forms or validation.
 * Also, a request parameter, which would be visible to the application user,
 * is not required to enable selection of the handler method. </p>
 *
 * @version $Rev$ $Date$
 * @since Struts 1.2
 */
public class MappingDispatchAction extends DispatchAction {
    private static final long serialVersionUID = 8605425680931524975L;

    // -------------------------------------------------------- Class Variables

    /**
     * The {@code Log} instance for this class.
     */
    private transient final Logger log =
        LoggerFactory.getLogger(MappingDispatchAction.class);

    // --------------------------------------------------------- Public Methods

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * This method dispatches the request to other methods of
     * <code>MappingDispatchAction</code> using the 'parameter' attribute of
     * <code>ActionMapping</code> and Java Introspection.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return Return an <code>ActionForward</code> instance describing where
     *         and how control should be forwarded, or <code>null</code> if
     *         the response has already been completed.
     * @throws Exception if an exception occurs
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        // Use the overridden getMethodName.
        return super.execute(mapping, form, request, response);
    }

    /**
     * Method which is dispatched to when there is no value for the parameter
     * in the ActionMapping.  Subclasses of <code>MappingDispatchAction</code>
     * should override this method if they wish to provide default behavior
     * different than throwing a ServletException.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return Return an <code>ActionForward</code> instance describing where
     *         and how control should be forwarded, or <code>null</code> if
     *         the response has already been completed.
     * @throws Exception if an exception occurs
     */
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        String message =
            messages.getMessage("mapping.parameter", mapping.getPath());

        log.error(message);

        throw new ServletException(message);
    }

    /**
     * <p>Returns the parameter value.</p>
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The <code>ActionMapping</code> parameter's value
     */
    protected String getParameter(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        return mapping.getParameter();

    }

    /**
     * Returns the method name, given a parameter's value.
     *
     * @param mapping   The ActionMapping used to select this instance
     * @param form      The optional ActionForm bean for this request (if
     *                  any)
     * @param request   The HTTP request we are processing
     * @param response  The HTTP response we are creating
     * @param parameter The <code>ActionMapping</code> parameter's name
     * @return The method's name.
     * @throws Exception if an error occurs
     * @since Struts 1.2.0
     */
    protected String getMethodName(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response,
        String parameter) throws Exception {
        // Return the unresolved mapping parameter.
        return parameter;
    }
}