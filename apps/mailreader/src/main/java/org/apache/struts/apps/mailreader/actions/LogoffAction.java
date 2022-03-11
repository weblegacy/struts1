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

package org.apache.struts.apps.mailreader.actions;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.apps.mailreader.Constants;
import org.apache.struts.apps.mailreader.dao.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * Log user out of the current session.
 * </p>
 *
 * @version $Rev$ $Date$
 */
public final class LogoffAction extends BaseAction {

    // See superclass for Javadoc
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        // Log event
        HttpSession session = request.getSession();
        User user = doGetUser(session);
        if (user != null) {
            if (log.isDebugEnabled()) {
                log.debug(
                        "LogoffAction: User '"
                                + user.getUsername()
                                + "' logged off in session "
                                + session.getId());
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug(
                        "LogoffActon: User logged off in session " +
                                session.getId());
            }
        }

        // Process user logoff by removing session attributes
        session.removeAttribute(Constants.SUBSCRIPTION_KEY);
        session.removeAttribute(Constants.USER_KEY);
        session.invalidate();

        // Done
        return doFindSuccess(mapping);

    }

}
