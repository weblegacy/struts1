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
package org.apache.struts.chain.commands;

import org.apache.commons.chain.Command;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.config.ForwardConfig;

/**
 * Look up and execute a commons-chain {@code Command} based on properties of
 * the ActionContext's {@code forwardConfig} property.
 */
public class ExecuteForwardCommand extends ExecuteCommand {

    /**
     * Return the command specified by the {@code command} and {@code catalog}
     * properties of the {@code forwardConfig} property of the given
     * {@code ActionContext}. If {@code forwardConfig} is null, return null.
     *
     * @param context Our ActionContext
     *
     * @return Command to execute or null
     */
    protected Command<ActionContext> getCommand(ActionContext context) {
        ForwardConfig forwardConfig = context.getForwardConfig();

        if (forwardConfig == null) {
            return null;
        }

        return getCommand(forwardConfig.getCommand(), forwardConfig.getCatalog());
    }

    /**
     * Determine whether the forwardConfig should be processed.
     *
     * @param context The ActionContext we are processing
     *
     * @return {@code true} if the given {@code ActionContext} has a non-null
     *         {@code forwardConfig} property.
     */
    protected boolean shouldProcess(ActionContext context) {
        return (context.getForwardConfig() != null);
    }
}