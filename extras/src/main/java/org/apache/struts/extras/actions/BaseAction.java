/*
 * $Id: $
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
package org.apache.struts.extras.actions;

import org.apache.struts.action.Action;
import org.apache.struts.util.MessageResources;

/**
 * <p>BaseAction is provided as an intermediate class for shared funtionality
 * between <code>Action</code> and any stock implementation provided in this
 * package.</p>
 *
 * @version $Rev$ $Date$
 * @since Struts 1.3
 */
public abstract class BaseAction extends Action {
    private static final long serialVersionUID = 7514980838116602408L;

    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
        MessageResources.getMessageResources(
            "org.apache.struts.extras.actions.LocalStrings");
}
