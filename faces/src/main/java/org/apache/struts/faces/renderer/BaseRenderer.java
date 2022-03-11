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

package org.apache.struts.faces.renderer;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p><code>Renderer</code> implementation for the <code>base</code> tag
 * from the <em>Struts-Faces Integration Library</em>.</p>
 *
 * @version $Rev$ $Date$
 */

public class BaseRenderer extends AbstractRenderer {


    // -------------------------------------------------------- Static Variables


    /**
     * <p>The <code>Log</code> instance for this class.</p>
     */
    private static Log log = LogFactory.getLog(BaseRenderer.class);


    // ---------------------------------------------------------- Public Methods


    /**
     * <p>Render an HTML <code>base</code> element.</p>
     *
     * @param context FacesContext for the request we are processing
     * @param component UIComponent to be rendered
     *
     * @exception IOException if an input/output error occurs while rendering
     * @exception NullPointerException if <code>context</code>
     *  or <code>component</code> is null
     */
    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {

        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }

        if (log.isTraceEnabled()) {
            log.trace("viewId='" + context.getViewRoot().getViewId() +
                      "' --> uri='" + uri(context) + "'");
        }

        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("base", component);
        writer.writeURIAttribute("href", uri(context), null);
        String target = (String) component.getAttributes().get("target");
        if (target != null) {
            writer.writeAttribute("target", target, "target");
        }
        writer.endElement("base");
        writer.writeText("\n", null);

    }



    // ------------------------------------------------------- Protected Methods


    /**
     * <p>Return <code>true</code> if this is a portlet request instance.
     * NOTE:  Implementation must not require portlet API classes to be
     * present.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    protected boolean isPortletRequest(FacesContext context) {

        Object request = context.getExternalContext().getRequest();
        Class clazz = request.getClass();
        while (clazz != null) {
            // Does this class implement PortletRequest?
            Class interfaces[] = clazz.getInterfaces();
            if (interfaces == null) {
                interfaces = new Class[0];
            }
            for (int i = 0; i < interfaces.length; i++) {
                if ("javax.portlet.PortletRequest".equals
                    (interfaces[i].getName())) {
                    return (true);
                }
            }
            // Try our superclass (if any)
            clazz = clazz.getSuperclass();
        }
        return (false);

    }


    /**
     * <p>Return <code>true</code> if this is a servlet request instance.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    protected boolean isServletRequest(FacesContext context) {

        Object request = context.getExternalContext().getRequest();
        return (request instanceof HttpServletRequest);

    }


    /**
     * <p>Return an absolute URI for the current page suitable for use
     * in a portlet environment.  NOTE:  Implementation must not require
     * portlet API classes to be present, so use reflection as needed.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    protected String portletUri(FacesContext context) {

        Object request = context.getExternalContext().getRequest();
        try {
            String scheme = (String)
                MethodUtils.invokeMethod(request, "getScheme", null);
            StringBuffer sb = new StringBuffer(scheme);
            sb.append("://");
            sb.append(MethodUtils.invokeMethod(request, "getServerName", null));
            Integer port = (Integer)
                MethodUtils.invokeMethod(request, "getServerPort", null);
            if ("http".equals(scheme) && (port.intValue() == 80)) {
                ;
            } else if ("https".equals(scheme) && (port.intValue() == 443)) {
                ;
            } else {
                sb.append(":" + port);
            }
            sb.append
                (MethodUtils.invokeMethod(request, "getContextPath", null));
            sb.append(context.getViewRoot().getViewId());
            return (sb.toString());
        } catch (InvocationTargetException e) {
            throw new FacesException(e.getTargetException());
        } catch (Exception e) {
            throw new FacesException(e);
        }

    }


    /**
     * <p>Return an absolute URI for the current page suitable for use
     * in a servlet environment.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    protected String servletUri(FacesContext context) {

        HttpServletRequest request = (HttpServletRequest)
            context.getExternalContext().getRequest();
        StringBuffer sb = new StringBuffer(request.getScheme());
        sb.append("://");
        sb.append(request.getServerName());
        if ("http".equals(request.getScheme()) &&
            (80 == request.getServerPort())) {
            ;
        } else if ("https".equals(request.getScheme()) &&
                   (443 == request.getServerPort())) {
            ;
        } else {
            sb.append(":" + request.getServerPort());
        }
        sb.append(request.getContextPath());
        sb.append(context.getViewRoot().getViewId());
        return (sb.toString());

    }


    /**
     * <p>Return the absolute URI to be rendered as the value of the
     * <code>href</code> attribute.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    protected String uri(FacesContext context) {

        if (isServletRequest(context)) {
            return (servletUri(context));
        } else if (isPortletRequest(context)) {
            return (portletUri(context));
        } else {
            throw new IllegalArgumentException
                ("Request is neither HttpServletRequest nor PortletRequest");
        }

    }


}
