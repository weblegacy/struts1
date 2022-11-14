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
package org.apache.struts.scripting;

import java.util.Enumeration;
import java.util.Properties;

import javax.script.Bindings;
import javax.script.ScriptContext;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Takes request parameters and declares variables with them. If a variable is
 * already exists with that name, a "_" is prepended to the name. Both Strings
 * and arrays are recognized.
 */
public class RequestToVariableFilter implements ScriptContextFilter {

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(TestFilter.class);

    /**
     * Initializes the filter.
     *
     * @param  name   The name of the filter
     * @param  props  The properties
     */
    public void init(String name, Properties props) { }

    /**
     * Applies the filter.
     *
     * @param context The scripting context
     *
     * @return The scripting context
     */
    public ScriptContext apply(ScriptContext context) {
        final HttpServletRequest request =
                (HttpServletRequest) context.getAttribute("request");

        final Bindings bindings = context.getBindings(ScriptContext.ENGINE_SCOPE);

        for (Enumeration<String> e = request.getParameterNames();
                e.hasMoreElements();) {

            final String name = e.nextElement();
            String newName = name;
            while (bindings.containsKey(newName)) {
                newName = "_" + newName;
            }

            final String[] values = request.getParameterValues(name);
            if (values.length > 1) {
                bindings.put(newName, values);
                log.debug("creating array var {}", newName);
            } else {
                bindings.put(newName, values[0]);
                log.debug("creating string var {}", newName);
            }
        }
        log.debug("Done filtering");
        return context;
    }
}