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


import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.faces.context.FacesContext;


/**
 * <p>Backing bean for the <code>mainMenu.jsp</code> page.</p>
 */

@Named
@RequestScoped
public class MainMenuBacking extends AbstractBacking {


    // -------------------------------------------------------- Static Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(MainMenuBacking.class);


    // ----------------------------------------------------------------- Actions


    /**
     * <p>Forward to the <em>Edit Registration</em> action.</p>
     */
    public String edit() {

        LOG.debug("edit()");
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder sb = registration(context);
        sb.append("?action=Edit");
        forward(context, sb.toString());
        return (null);

    }


    /**
     * <p>Forward to the <em>Logoff</em> action.</p>
     */
    public String logoff() {

        LOG.debug("logoff()");
        FacesContext context = FacesContext.getCurrentInstance();
        forward(context, logoff(context).toString());
        return (null);

    }
}