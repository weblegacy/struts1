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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Tests to make sure the filtering system is working.
 */
public class TestFilter implements ScriptContextFilter {

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(TestFilter.class);

    /**
     * Initializes the filter.
     *
     * @param name The name of the filter
     * @param props The properties
     */
    public void init(String name, Properties props) {
        log.info("Initializing TestFilter");
    }

    /**
     * Applies the filter.
     *
     * @param context The scripting context
     *
     * @return The scripting context
     */
    public ScriptContext apply(ScriptContext context) {
        log.info("Filtering in TestFilter");
        return context;
    }
}