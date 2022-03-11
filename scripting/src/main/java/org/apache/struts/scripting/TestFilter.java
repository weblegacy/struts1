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

// util imports:
import java.util.Properties;

// logging imports:
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// misc imports:
import org.apache.bsf.BSFManager;


/**
 *  Tests to make sure the filtering system is working.
 */
public class TestFilter implements BSFManagerFilter {

    /** Logging instance. */
    private static final Log LOG = LogFactory.getLog(TestFilter.class);

    /**
     *  Initializes the filter.
     *
     * @param name The name of the filter
     * @param props The properties
     */
    public void init(String name, Properties props) {
        LOG.info("Initializing TestFilter");
    }

    /**
     *  Applies the filter.
     *
     * @param mgr The bsf manager
     * @return The bsf manager
     */
    public BSFManager apply(BSFManager mgr) {
        LOG.info("Filtering in TestFilter");
        return mgr;
    }
}
