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

package org.apache.struts.webapp.validator;

import java.io.InputStream;
import java.io.InputStreamReader;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action which retrieves a file specified in the parameter
 * and stores its contents in the request, so that they
 * can be displayed.
 */
public class ShowFileAction extends Action {
    private static final long serialVersionUID = -3817516672582496435L;

    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(ShowFileAction.class);

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                          throws Exception {

        // Get the file name
        String fileName = mapping.getParameter();
        StringBuilder fileContents = new StringBuilder();

        if(fileName != null) {

            InputStream input = servlet.getServletContext().getResourceAsStream(fileName);
            if (input == null) {
                LOG.warn("File Not Found: {}", fileName);
            } else {
                InputStreamReader inputReader = new InputStreamReader(input);
                char[] buffer = new char[1000];
                while (true) {
                    int lth = inputReader.read(buffer);
                    if (lth < 0) {
                        break;
                    } else {
                        fileContents.append(buffer, 0, lth);
                    }
                }
            }
        } else {
            LOG.error("No file name specified.");
        }


        // Store the File contents and name in the Request
        request.setAttribute("fileName", fileName);
        request.setAttribute("fileContents", fileContents.toString());

        return mapping.findForward("success");
    }
}