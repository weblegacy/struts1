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

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.config.ForwardConfig;

import java.util.Map;

/**
 * Copies any <code>ActionMessages</code> from the request to the session if a
 * redirecting forward is selected and the messages were not accessed. This
 * allows messages to be retained across a redirect, and then later released by
 * the <code>RemoveCachedMessages</code> command. The most common use case for
 * this command is when the validator or exception handler places messages in
 * the request.
 *
 * @version $Id$
 * @see RemoveCachedMessages
 * @since Struts 1.4.0
 */
public class CacheMessages extends ActionCommandBase {

    public boolean execute(ActionContext actionCtx) throws Exception {
        ForwardConfig forwardConfig = actionCtx.getForwardConfig();
        if ((forwardConfig != null) && forwardConfig.getRedirect()) {
            Map<String, Object> request = actionCtx.getRequestScope();
            Map<String, Object> session = actionCtx.getSessionScope();
            copyUnaccessedMessages(request, session, Globals.MESSAGE_KEY);
            copyUnaccessedMessages(request, session, Globals.ERROR_KEY);
        }

        return CONTINUE_PROCESSING;
    }

    /**
     * Adds any <code>ActionMessages</code> object to the destination scope,
     * stored under the specified key, if the messages were not accessed through
     * the source scope.
     *
     * @param fromScope The scope to check for unacessed messages.
     * @param toScope The scope to copy messages into.
     * @param key The key the messages are stored under.
     */
    private void copyUnaccessedMessages(Map<String, Object> fromScope, Map<String, Object> toScope, String key) {
        ActionMessages fromMessages = (ActionMessages) fromScope.get(key);
        if ((fromMessages != null) && !fromMessages.isAccessed()) {
            ActionMessages toMessages = (ActionMessages) toScope.get(key);
            if (toMessages != null) {
                toMessages.add(fromMessages);
            } else {
                toScope.put(key, fromMessages);
            }
        }
    }
}
