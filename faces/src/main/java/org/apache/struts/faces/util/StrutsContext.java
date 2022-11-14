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

package org.apache.struts.faces.util;


import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.faces.Constants;
import org.apache.struts.util.MessageResources;

import jakarta.faces.FacesException;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.servlet.http.HttpServletRequest;


/**
 * Context bean providing accessors for the Struts related request,
 * session, and application scope objects related to this request. Note
 * that this bean's methods will trigger exceptions unless there is a
 * {@code FacesContext} instance for this request.
 */
public class StrutsContext {


    // ------------------------------------------------------ Instance Variables


    /**
     * The {@code FacesContext} for the current request.
     */
    private final FacesContext fcontext;

    /**
     * The {@code ExternalContext} for the current request.
     */
    private final ExternalContext econtext;


    // ------------------------------------------------------ Constructors


    /**
     * Creates a new instance with
     * {@link FacesContext#getCurrentInstance()}.
     */
    public StrutsContext() {
        this(FacesContext.getCurrentInstance());
    }

    /**
     * Create a new instance with give {@code FacesContext}.
     *
     * @param facesContext The give {@code FacesContext}
     */
    public StrutsContext(FacesContext facesContext) {
        this.fcontext = facesContext;
        this.econtext = facesContext.getExternalContext();
    }


    // ---------------------------------------------------------- Public Methods


    /**
     * Return the {@code ActionEvent} for the current request
     * (if any).
     */
    public ActionEvent getActionEvent() {
        return Utils.getMapValue(ActionEvent.class, econtext.getRequestMap(),
                Constants.ACTION_EVENT_KEY);
    }

    /**
     * Return the {@code ActionMapping} for the current
     * request (if any).
     */
    public ActionMapping getActionMapping() {
        return Utils.getMapValue(ActionMapping.class, econtext.getRequestMap(),
                Globals.MAPPING_KEY);
    }

    /**
     * Return the {@code ActionMessages} instance containing
     * application error messages for this request (if any).
     */
    public ActionMessages getActionMessages() {
        return Utils.getMapValue(ActionMessages.class, econtext.getRequestMap(),
                Globals.MESSAGE_KEY);
    }

    /**
     * Return the {@code ActionServlet} instance for this
     * web application.
     */
    public ActionServlet getActionServlet() {
        return Utils.getMapValue(ActionServlet.class, econtext.getApplicationMap(),
                Globals.ACTION_SERVLET_KEY);
    }

    /**
     * Return {@code true} if a Boolean true value has been stored
     * in the request attribute indicating that this request has been
     * cancelled.
     */
    public boolean isCancelled() {
        final Object value = econtext.getRequestMap().get(Globals.CANCEL_KEY);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else {
            return false;
        }
    }

    /**
     * Return the exception that caused one of the Struts custom tags
     * to report a JspException (if any).
     */
    public Throwable getException() {
        return Utils.getMapValue(Throwable.class, econtext.getRequestMap(),
                Globals.EXCEPTION_KEY);
    }

    /**
     * Return the {@code ExternalContext} for the current request.
     */
    public ExternalContext getExternalContext() {
        return econtext;
    }

    /**
     * Return the {@code FacesContext} for the current request.
     */
    public FacesContext getFacesContext() {
        return fcontext;
    }

    /**
     * Return the {@code Locale} stored in the current user's
     * session (if any) for Struts based localization.
     */
    public Locale getLocale() {
        Locale locale = null;
        if (econtext.getSession(false) != null) {
            locale = Utils.getMapValue(Locale.class, econtext.getSessionMap(),
                    Globals.LOCALE_KEY);
        }

        if (locale == null) {
            locale = econtext.getRequestLocale();
        }

        return locale;
    }

    /**
     * Return the {@code MessageResources} instance for the
     * application module that is processing this request
     * (if any).
     */
    public MessageResources getMessageResources() {
        return Utils.getMapValue(MessageResources.class, econtext.getRequestMap(),
                Globals.MESSAGES_KEY);
    }

    /**
     * Return the {@code ModuleConfig} for the application module
     * to which this request has been assigned (if any).
     *
     * @return the {@code ModuleConfig} for the application module
     *
     * @throws IllegalArgumentException if no {@code ModuleConfig}
     *     can be found
     */
    public ModuleConfig getModuleConfig() {
        return getModuleConfig(econtext);
    }

    /**
     * Return the {@code ModuleConfig} for the application module
     * to which this request has been assigned (if any).
     *
     * @param facesContext The given {@code FacesContext}
     *
     * @return the {@code ModuleConfig} for the application module
     *
     * @throws IllegalArgumentException if no {@code ModuleConfig}
     *     can be found
     */
    public static ModuleConfig getModuleConfig(FacesContext facesContext) {
        return getModuleConfig(facesContext.getExternalContext());
    }

    /**
     * Return the {@code ModuleConfig} for the application module
     * to which this request has been assigned (if any).
     *
     * @param externalContext The given {@code ExternalContext}
     *
     * @return the {@code ModuleConfig} for the application module
     *
     * @throws IllegalArgumentException if no {@code ModuleConfig}
     *     can be found
     */
    public static ModuleConfig getModuleConfig(ExternalContext externalContext) {
        ModuleConfig moduleConfig = Utils.getMapValue(ModuleConfig.class,
                externalContext.getRequestMap(), Globals.MODULE_KEY);

        if (moduleConfig == null) {
            moduleConfig = Utils.getMapValue(ModuleConfig.class,
                    externalContext.getApplicationMap(), Globals.MODULE_KEY);
        }

        if (moduleConfig == null) {
            throw new IllegalArgumentException("Cannot find module configuration");
        }

        return moduleConfig;
    }

    /**
     * Return the absolute URI to be rendered as the value of the
     * {@code href} attribute.
     *
     * @param context {@code FacesContext} for the current request
     *
     * @throws IllegalArgumentException Request is neither
     *     {@code HttpServletRequest} nor {@code PortletRequest}.
     */
    public static String uri(FacesContext context) {
        final StringBuilder sb = new StringBuilder();

        final ExternalContext externalContext = context.getExternalContext();
        if (StrutsContext.isServletRequest(externalContext)) {
            servletUri(sb, externalContext);
        } else if (StrutsContext.isPortletRequest(externalContext)) {
            portletUri(sb, externalContext);
        } else {
            throw new IllegalArgumentException
                    ("Request is neither HttpServletRequest nor PortletRequest");
        }

        return sb.append(context.getViewRoot().getViewId()).toString();
    }

    /**
     * Appends an absolute URI for the current request suitable for use
     * in a portlet environment.
     *
     * <p>NOTE: Implementation must not require portlet API classes to
     * be present, so use reflection as needed.</p>
     *
     * @param context {@code FacesContext} for the current request
     */
    private static void portletUri(StringBuilder sb, ExternalContext context) {
        final Object request = context.getRequest();
        try {
            final String scheme = (String)
                    MethodUtils.invokeMethod(request, "getScheme", null);

            final String serverName = (String)
                    MethodUtils.invokeMethod(request, "getServerName", null);

            final int serverPort = (Integer)
                    MethodUtils.invokeMethod(request, "getServerPort", null);

            final String contextPath = (String)
                    MethodUtils.invokeMethod(request, "getContextPath", null);

            buildUri(sb, scheme, serverName, serverPort, contextPath);
        } catch (InvocationTargetException e) {
            throw new FacesException(e.getTargetException());
        } catch (Exception e) {
            throw new FacesException(e);
        }
    }

    /**
     * Return {@code true} if this is a portlet request instance.
     *
     * <p>NOTE: Implementation must not require portlet API classes to be
     * present.</p>
     *
     * @param context {@code FacesContext} for the current request
     */
    private static boolean isPortletRequest(ExternalContext context) {
        final Object request = context.getRequest();
        for (Class<?> clazz = request.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            Class<?> interfaces[] = clazz.getInterfaces();
            if (interfaces == null) {
                continue;
            }

            // Does this class implement PortletRequest?
            for (Class<?> inter : interfaces) {
                if ("javax.portlet.PortletRequest".equals(inter.getName()) ||
                    "jakarta.portlet.PortletRequest".equals(inter.getName())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Appends an absolute URI for the current request suitable for use
     * in a servlet environment.
     *
     * @param context {@code FacesContext} for the current request
     */
    private static void servletUri(StringBuilder sb, ExternalContext context) {
        final HttpServletRequest request = (HttpServletRequest)
            context.getRequest();

        buildUri(sb, request.getScheme(), request.getServerName(),
                request.getServerPort(), request.getContextPath());
    }

    /**
     * Return {@code true} if this is a servlet request instance.
     *
     * @param context {@code FacesContext} for the current request
     */
    private static boolean isServletRequest(ExternalContext context) {
        Object request = context.getRequest();
        return request instanceof HttpServletRequest;
    }

    /**
     * Appends a URI into the given StringBuilder from the
     * given parameters.
     *
     * @param sb The given {@Code StringBuilder} where the URI is
     *     append.
     * @param scheme The name of the scheme used to make this
     *     request.
     * @param serverName The host name of the server to which the
     *     request was sent.
     * @param serverPort The port number to which the request was
     *     sent.
     * @param contextPath The portion of the request URI that
     *     indicates the context of the request.
     */
    private static void buildUri(final StringBuilder sb, final String scheme, final String serverName, final int serverPort, final String contextPath) {
        sb.append(scheme)
          .append("://")
          .append(serverName);

        if ("http".equals(scheme) && serverPort == 80) {
        } else if ("https".equals(scheme) && (serverPort == 443)) {
        } else {
            sb.append(':')
              .append(serverPort);
        }

        sb.append(contextPath);
    }
}