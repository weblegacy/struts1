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

import java.util.Properties;

import javax.script.ScriptContext;


/**
 * Defines a class that wants to manipulate the contents of the scripting
 * context before the script is executed. An example would be a class that puts
 * business facade classes in the context.
 */
public interface ScriptContextFilter {

    /**
     * Initializes the filter. Properties can be retrieved as:
     * {@code struts-scripting.filters.FILTER_NAME.PROPERTY_NAME=PROPERTY_VALUE}
     * where FILTER_NAME is the "name" parameter.
     *
     * @param  name   The name of the filter
     * @param  props  The properties
     */
    void init(String name, Properties props);


    /**
     * Applies the filter.
     *
     * @param context The scripting context
     *
     * @return The scripting context
     */
    ScriptContext apply(ScriptContext context);
}