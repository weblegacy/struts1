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

package org.apache.struts.tiles;

import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Basic implementation of Controller.  Implementations can extend this class
 * to insulate themselves from changes in the <code>Controller</code>
 * interface.
 */
public class ControllerSupport implements Controller {
    private static final long serialVersionUID = -4462385995896189607L;

    /**
     * Method associated to a tile and called immediately before tile is
     * included. This implementation does nothing.
     *
     * @param tileContext    Current tile context.
     * @param request        Current request
     * @param response       Current response
     * @param servletContext Current servlet context
     * @deprecated Use execute() instead.  This will be removed after
     *             Struts 1.2.
     */
    @Deprecated
    public void perform(
            ComponentContext tileContext,
            HttpServletRequest request,
            HttpServletResponse response,
            ServletContext servletContext)
            throws ServletException, IOException {
    }

    /**
     * @see org.apache.struts.tiles.Controller#execute(org.apache.struts.tiles.ComponentContext, jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse, jakarta.servlet.ServletContext)
     */
    public void execute(
            ComponentContext tileContext,
            HttpServletRequest request,
            HttpServletResponse response,
            ServletContext servletContext)
            throws Exception {

        perform(tileContext, request, response, servletContext);
    }
}