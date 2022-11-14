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


package org.apache.struts.webapp.example;


import java.io.IOException;

import jakarta.faces.FacesException;
import jakarta.faces.context.FacesContext;


/**
 * <p>Abstract base class for backing beans.</p>
 */

abstract class AbstractBacking {


    // ------------------------------------------------------- Protected Methods


    /**
     * <p>Return the context relative path for the specified action.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param action Name of the requested action
     */
    protected StringBuilder action(FacesContext context, String action) {

        // FIXME - assumes extension mapping for Struts
        StringBuilder sb = new StringBuilder(action);
        sb.append(".do");
        return (sb);

    }


    /**
     * <p>Forward to the specified URL and mark this response as having
     * been completed.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param url Context-relative URL to forward to
     *
     * @exception FacesException if any error occurs
     */
    protected void forward(FacesContext context, String url) {

        try {
            context.getExternalContext().dispatch(url);
        } catch (IOException e) {
            throw new FacesException(e);
        } finally {
            context.responseComplete();
        }

    }


    /**
     * <p>Return the context relative base URL for the "logoff"
     * action.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    protected StringBuilder logoff(FacesContext context) {

        return (action(context, "/logoff"));

    }


    /**
     * <p>Return the context relative base URL for the "logon"
     * action.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    protected StringBuilder logon(FacesContext context) {

        return (action(context, "/logon"));

    }


    /**
     * <p>Return the context relative base URL for the "edit registration"
     * action.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    protected StringBuilder registration(FacesContext context) {

        return (action(context, "/editRegistration"));

    }


    /**
     * <p>Return the context relative base URL for the "edit subscriptions"
     * action.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    protected StringBuilder subscription(FacesContext context) {

        return (action(context, "/editSubscription"));

    }


}
