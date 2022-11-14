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
import java.io.Serializable;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * A controller is a piece of code called before rendering a jsp page.
 * A controller can be associated to a tile. See &lt;insert&gt; or
 * &lt;definition&gt; for association syntax.
 */
public interface Controller extends Serializable {

    /**
     * Method associated to a tile and called immediately before the tile
     * is included.
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
            throws ServletException, IOException;

    /**
     * Method associated to a tile and called immediately before the tile
     * is included.
     *
     * @param tileContext    Current tile context.
     * @param request        Current request
     * @param response       Current response
     * @param servletContext Current servlet context
     */
    public void execute(
            ComponentContext tileContext,
            HttpServletRequest request,
            HttpServletResponse response,
            ServletContext servletContext)
            throws Exception;
}