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


import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>Backing bean for the <code>mainMenu.jsp</code> page.</p>
 */

public class MainMenuBacking extends AbstractBacking {


    // -------------------------------------------------------- Static Variables


    private static final Log log = LogFactory.getLog(MainMenuBacking.class);


    // ----------------------------------------------------------------- Actions


    /**
     * <p>Forward to the <em>Edit Registration</em> action.</p>
     */
    public String edit() {

        if (log.isDebugEnabled()) {
            log.debug("edit()");
        }
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuffer sb = registration(context);
        sb.append("?action=Edit");
        forward(context, sb.toString());
        return (null);

    }


    /**
     * <p>Forward to the <em>Logoff</em> action.</p>
     */
    public String logoff() {

        if (log.isDebugEnabled()) {
            log.debug("logoff()");
        }
        FacesContext context = FacesContext.getCurrentInstance();
        forward(context, logoff(context).toString());
        return (null);

    }


}
