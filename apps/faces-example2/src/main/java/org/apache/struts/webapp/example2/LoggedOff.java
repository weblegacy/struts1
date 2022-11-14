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


package org.apache.struts.webapp.example2;


import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.faces.FacesException;
import jakarta.faces.context.FacesContext;


/**
 * <p>Backing bean for the <code>loggedoff.jsp</code> page.</p>
 */

@Named
@RequestScoped
public class LoggedOff {


    // ------------------------------------------------------ Instance Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(LoggedOff.class);


    // ----------------------------------------------------------------- Actions


    /**
     * <p>Begin the process of registering a new user.</p>
     */
    public String register() {

        FacesContext context = FacesContext.getCurrentInstance();
        LOG.debug("register({})", context);
        forward(context, "/editRegistration.do?action=Create");
        return (null);

    }


    /**
     * <p>Begin the process of logging on.</p>
     */
    public String logon() {

        FacesContext context = FacesContext.getCurrentInstance();
        LOG.debug("logon({})", context);
        forward(context, "/editLogon.do");
        return (null);

    }


    // --------------------------------------------------------- Private Methods


    /**
     * <p>Forward to the specified URL and mark this response as having
     * been completed.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param url Context-relative URL to forward to
     *
     * @exception FacesException if any error occurs
     */
    private void forward(FacesContext context, String url) {

        try {
            context.getExternalContext().dispatch(url);
        } catch (IOException e) {
            throw new FacesException(e);
        } finally {
            context.responseComplete();
        }

    }
}