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
package org.apache.struts.dispatcher;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.chain.contexts.ActionContext;

import java.util.StringTokenizer;

/**
 * This abstract class is a template for choosing the target method that is
 * named in the list-encoded <code>parameter</code> attribute of the
 * {@link ActionMapping} that matches a submission parameter. This is useful for
 * developers who prefer to use many submit buttons, images, or submit links on
 * a single form and whose related actions exist in a single action.
 * <p>
 * For utility purposes, you can use the <code>key=value</code> notation to
 * alias methods so that they are exposed as different form element names, in
 * the event of a naming conflict or otherwise. In this example, the
 * <em>recalc</em> button (via a submission parameter) will invoke the
 * <code>recalculate</code> method. The security-minded person may find this
 * feature valuable to obfuscate and not expose the methods.
 * <p>
 * The <em>default</em> key is purely optional, but encouraged when nothing
 * matches (such as the user pressing the enter key). If this is not specified
 * and no parameters match the list of method keys, the method resolution
 * returns <code>null</code>.
 * <p>
 * The order of the parameters are guaranteed to be iterated in the order
 * specified. If multiple buttons were accidently submitted, the first match in
 * the list will be dispatched.
 *
 * @version $Rev$
 * @since Struts 1.4
 */
public abstract class AbstractEventMappingDispatcher extends AbstractMappingDispatcher {
    private static final long serialVersionUID = -8559616664806617100L;

    /**
     * The method key, if present, to use if other specified method keys do not
     * match a request parameter.
     */
    protected static final String DEFAULT_METHOD_KEY = "default";

    /**
     * Constructs a new dispatcher with the specified method resolver.
     *
     * @param methodResolver the method resolver
     */
    public AbstractEventMappingDispatcher(MethodResolver methodResolver) {
        super(methodResolver);
    }

    /**
     * Determines whether the specified method key is a submission parameter.
     *
     * @param context the current action context
     * @param methodKey the method key
     * @return <code>true</code> if match; otherwise <code>false</code>
     * @see #resolveMethodName(ActionContext)
     */
    protected abstract boolean isSubmissionParameter(ActionContext context, String methodKey);

    protected final String resolveMethodName(ActionContext context) {
        // Obtain the mapping parameter
        String mappingParameter = super.resolveMethodName(context);
        if (mappingParameter == null) {
            return null;
        }

        // Parse it as a comma-separated list
        StringTokenizer st = new StringTokenizer(mappingParameter, ",");
        String defaultMethodName = null;

        while (st.hasMoreTokens()) {
            String methodKey = st.nextToken().trim();
            String methodName = methodKey;

            // The key can either be a direct method name or an alias
            // to a method as indicated by a "key=value" signature
            int equals = methodKey.indexOf('=');
            if (equals > -1) {
                methodName = methodKey.substring(equals + 1).trim();
                methodKey = methodKey.substring(0, equals).trim();
            }

            // Set the default if it passes by
            if (methodKey.equals(DEFAULT_METHOD_KEY)) {
                defaultMethodName = methodName;
            }

            // Is it a match?
            if (isSubmissionParameter(context, methodKey)) {
                return methodName;
            }
        }

        return defaultMethodName;
    }

}
