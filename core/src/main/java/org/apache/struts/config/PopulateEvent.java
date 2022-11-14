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
package org.apache.struts.config;

import jakarta.servlet.RequestDispatcher;

/**
 * Constants relating to the reset and population events of action forms.
 *
 * @since Struts 1.4
 */
public abstract class PopulateEvent {

    /**
     * Specifies that population must always occur. This type may not be
     * combined with any other type.
     */
    public static final String ALL = "all";

    /**
     * Specifies that population occurs when the form is cancelled.
     */
    public static final String CANCEL = "cancel";

    /**
     * Specifies that population occurs when the current executing action has
     * been forwarded by another action. It should be noted that if the chained
     * action mapping refers to the same form bean as originating action, then
     * the form bean is repopulated and changes made by originating action are
     * lost.
     */
    public static final String CHAIN = "chain";

    /**
     * Specifies that population occurs when the current requested is part of a
     * forwarded request. Unlike {@link #CHAIN} which is limited to
     * action-to-action behavior, the forward can be from any servlet resource.
     *
     * @see RequestDispatcher#forward(jakarta.servlet.ServletRequest,
     *      jakarta.servlet.ServletResponse)
     */
    public static final String FORWARD = "forward";

    /**
     * Specifies that population occurs when the current requested is part of
     * any included request.
     *
     * @see RequestDispatcher#include(jakarta.servlet.ServletRequest,
     *      jakarta.servlet.ServletResponse)
     */
    public static final String INCLUDE = "include";

    /**
     * Specifies that population must never occur. This type may not be combined
     * with any other type.
     */
    public static final String NONE = "none";

    /**
     * Specifies that population occurs for an ordinary client request.
     */
    public static final String REQUEST = "request";
}
