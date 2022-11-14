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
import jakarta.faces.event.ActionEvent;


/**
 * Backing bean for the <code>index.jsp</code> page.
 */

@Named
@RequestScoped
public class IndexBacking extends AbstractBacking {


    // -------------------------------------------------------- Static Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(IndexBacking.class);


    // ----------------------------------------------------------------- Actions


    /**
     * <p>Forward to the <em>Create Registration</em> action.</p>
     */
    public String create() {

        LOG.debug("create()");
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder sb = registration(context);
        sb.append("?action=Create");
        forward(context, sb.toString());
        return (null);

    }
    /**
     * <p>Testing actionListener call</p>
     * @param actionEvent
     */
    public void testListener(ActionEvent actionEvent){
//      System.out.println("================= it works!!");
    }

    /**
     * <p>Forward to the <em>Logon</em> page.</p>
     */
    public String logon() {

        LOG.debug("logon()");
        return "logon";

    }
}