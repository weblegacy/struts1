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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This abstract class is a template for choosing the target method that is
 * named by the <code>parameter</code> attribute of the corresponding
 * {@link ActionMapping}. The attribute value, if not provided, defaults to
 * <code>execute</code>.
 *
 * @version $Rev$
 * @since Struts 1.4
 */
public abstract class AbstractMappingDispatcher extends AbstractDispatcher {
    private static final long serialVersionUID = 1882936058355907522L;

    /**
     * The {@code Log} instance for this class.
     */
    private transient final Logger log =
            LoggerFactory.getLogger(AbstractMappingDispatcher.class);

    private String defaultMappingParameter;

    /**
     * Constructs a new dispatcher with the specified method resolver.
     *
     * @param methodResolver the method resolver
     */
    public AbstractMappingDispatcher(MethodResolver methodResolver) {
        super(methodResolver);
        setDefaultMappingParameter(EXECUTE_METHOD_NAME);
    }

    /**
     * Retrieves the default mapping parameter value. This value is used by
     * {@link #resolveMethodName(ActionContext)} if the mapping did not provide
     * a value.
     *
     * @return the mapping parameter value or <code>null</code>
     * @see #setDefaultMappingParameter(String)
     */
    protected final String getDefaultMappingParameter() {
        return defaultMappingParameter;
    }

    /**
     * Resolves the method name by obtaining the <code>parameter</code>
     * attribute from the {@link ActionMapping}.
     *
     * @param context the current action context
     * @throws IllegalStateException if the parameter cannot be resolved
     * @return the parameter attribute value
     */
    protected String resolveMethodName(ActionContext context) {
        // Null out an empty string parameter
        ActionMapping mapping = (ActionMapping) context.getActionConfig();
        String parameter = mapping.getParameter();
        if ("".equals(parameter)) {
            parameter = null;
        }

        // Assign the default if the mapping did not provide a value
        if (parameter == null) {
            parameter = defaultMappingParameter;
        }

        // Parameter is required
        if (parameter == null) {
            String message = messages.getMessage(MSG_KEY_MISSING_MAPPING_PARAMETER, mapping.getPath());
            log.error(message);
            throw new IllegalStateException(message);
        }

        return parameter;
    }

    /**
     * Stores the new default mapping parameter value.
     *
     * @param defaultMappingParameter the parameter value
     * @see #getDefaultMappingParameter()
     */
    protected final void setDefaultMappingParameter(String defaultMappingParameter) {
        this.defaultMappingParameter = defaultMappingParameter;
    }
}