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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.MessageResourcesConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p> An abstract <strong>Action</strong> that dispatches to the subclass
 * mapped <code>execute</code> method. This is useful in cases where an HTML
 * form has multiple submit buttons with the same name. The button name is
 * specified by the <code>parameter</code> property of the corresponding
 * ActionMapping. To configure the use of this action in your
 * <code>struts-config.xml</code> file, create an entry like this:</p> <pre>
 *   &lt;action path="/test"
 *           type="org.example.MyAction"
 *           name="MyForm"
 *          scope="request"
 *          input="/test.jsp"
 *      parameter="method"/&gt;
 * </pre> <p>
 *
 * which will use the value of the request parameter named "method" to locate
 * the corresponding key in ApplicationResources. For example, you might have
 * the following ApplicationResources.properties:</p> <pre>
 *    button.add=Add Record
 *    button.delete=Delete Record
 *  </pre><p>
 *
 * And your JSP would have the following format for submit buttons:</p> <pre>
 *   &lt;html:form action="/test"&gt;
 *    &lt;html:submit property="method"&gt;
 *      &lt;bean:message key="button.add"/&gt;
 *    &lt;/html:submit&gt;
 *    &lt;html:submit property="method"&gt;
 *      &lt;bean:message key="button.delete"/&gt;
 *    &lt;/html:submit&gt;
 *  &lt;/html:form&gt;
 *  </pre> <p>
 *
 * Your subclass must implement both getKeyMethodMap and the methods defined
 * in the map. An example of such implementations are:</p>
 * <pre>
 *  protected Map getKeyMethodMap() {
 *      Map map = new HashMap();
 *      map.put("button.add", "add");
 *      map.put("button.delete", "delete");
 *      return map;
 *  }
 *
 *  public ActionForward add(ActionMapping mapping,
 *          ActionForm form,
 *          HttpServletRequest request,
 *          HttpServletResponse response)
 *          throws IOException, ServletException {
 *      // do add
 *      return mapping.findForward("success");
 *  }
 *
 *  public ActionForward delete(ActionMapping mapping,
 *          ActionForm form,
 *          HttpServletRequest request,
 *          HttpServletResponse response)
 *          throws IOException, ServletException {
 *      // do delete
 *      return mapping.findForward("success");
 *  }
 * </pre>
 * <p> <strong>Notes</strong> - If duplicate values exist for the keys
 * returned by getKeys, only the first one found will be returned. If no
 * corresponding key is found then an exception will be thrown. You can
 * override the method <code>unspecified</code> to provide a custom handler.
 * If the submit was cancelled (a <code>html:cancel</code> button was
 * pressed), the custom handler <code>cancelled</code> will be used instead.
 */
public abstract class LookupDispatchAction extends DispatchAction {
    private static final long serialVersionUID = 8608624951935780151L;

    /**
     * The {@code Log} instance for this class.
     */
    private transient final Logger log =
        LoggerFactory.getLogger(LookupDispatchAction.class);

    /**
     * Reverse lookup map from resource value to resource key.
     */
    protected HashMap<Locale, Map<String, String>> localeMap = new HashMap<>();

    /**
     * Resource key to method name lookup.
     */
    protected Map<String, String> keyMethodMap = null;

    // ---------------------------------------------------------- Public Methods

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @param form     The optional ActionForm bean for this request (if any)
     * @return Describes where and how control should be forwarded.
     * @throws Exception if an error occurs
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        return super.execute(mapping, form, request, response);
    }

    /**
     * This is the first time this Locale is used so build the reverse lookup
     * Map. Search for message keys in all configured MessageResources for the
     * current module.
     *
     * @param request    The HTTP request we are processing
     * @param userLocale The locale for this request
     * @return The reverse lookup map for the specified locale.
     */
    private Map<String, String> initLookupMap(HttpServletRequest request, Locale userLocale) {
        Map<String, String> lookupMap = new HashMap<>();

        this.keyMethodMap = this.getKeyMethodMap();

        ModuleConfig moduleConfig =
            (ModuleConfig) request.getAttribute(Globals.MODULE_KEY);

        MessageResourcesConfig[] mrc =
            moduleConfig.findMessageResourcesConfigs();

        // Look through all module's MessageResources
        for (int i = 0; i < mrc.length; i++) {
            MessageResources resources =
                this.getResources(request, mrc[i].getKey());

            // Look for key in MessageResources
            for (String key : this.keyMethodMap.keySet()) {
                String text = resources.getMessage(userLocale, key);

                // Found key and haven't added to Map yet, so add the text
                if ((text != null) && !lookupMap.containsKey(text)) {
                    lookupMap.put(text, key);
                }
            }
        }

        return lookupMap;
    }

    /**
     * Provides the mapping from resource key to method name.
     *
     * @return Resource key / method name map.
     */
    protected abstract Map<String, String> getKeyMethodMap();

    /**
     * Lookup the method name corresponding to the client request's locale.
     *
     * @param request The HTTP request we are processing
     * @param keyName The parameter name to use as the properties key
     * @param mapping The ActionMapping used to select this instance
     * @return The method's localized name.
     * @throws ServletException if keyName cannot be resolved
     * @since Struts 1.2.0
     */
    protected String getLookupMapName(HttpServletRequest request,
        String keyName, ActionMapping mapping)
        throws ServletException {
        // Based on this request's Locale get the lookupMap
        Map<String, String> lookupMap = null;

        synchronized (localeMap) {
            Locale userLocale = this.getLocale(request);

            lookupMap = this.localeMap.get(userLocale);

            if (lookupMap == null) {
                lookupMap = this.initLookupMap(request, userLocale);
                this.localeMap.put(userLocale, lookupMap);
            }
        }

        // Find the key for the resource
        String key = lookupMap.get(keyName);

        if (key == null) {
            String message =
                messages.getMessage("dispatch.resource", mapping.getPath());
            log.error("{} '{}'", message, keyName);
            throw new ServletException(message);
        }

        // Find the method name
        String methodName = keyMethodMap.get(key);

        if (methodName == null) {
            String message =
                messages.getMessage("dispatch.lookup", mapping.getPath(), key);

            throw new ServletException(message);
        }

        return methodName;
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
        // Identify the method name to be dispatched to.
        // dispatchMethod() will call unspecified() if name is null
        String keyName = request.getParameter(parameter);

        if ((keyName == null) || (keyName.length() == 0)) {
            return null;
        }

        String methodName = getLookupMapName(request, keyName, mapping);

        return methodName;
    }
}