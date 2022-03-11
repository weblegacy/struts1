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
import java.util.Enumeration;
import java.util.Properties;

// logging imports:
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// misc imports:
import javax.servlet.http.HttpServletRequest;
import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;


/**
 *  Takes request parameters and declares variables with them. If a variable is
 *  already exists with that name, a "_" is prepended to the name. Both Strings
 *  and arrays are recognized.
 */
public class RequestToVariableFilter implements BSFManagerFilter {

    /**  The logging instance. */
    private static final Log LOG = LogFactory.getLog(TestFilter.class);


    /**
     *  Initializes the filter.
     *
     *@param  name   The name of the filter
     *@param  props  The properties
     */
    public void init(String name, Properties props) { }


    /**
     *  Applies the filter.
     *
     *@param  mgr  The bsf manager
     *@return      The bsf manager
     */
    public BSFManager apply(BSFManager mgr) {
        HttpServletRequest request =
                (HttpServletRequest) mgr.lookupBean("request");
        String[] values = null;
        String name = null;
        String newName = null;
        Object o = null;
        for (Enumeration e = request.getParameterNames();
                e.hasMoreElements();) {
            name = (String) e.nextElement();
            o = mgr.lookupBean(name);
            if (o == null) {
                newName = name;
            } else {
                newName = "_" + name;
            }

            values = request.getParameterValues(name);
            try {
                if (values.length > 1) {
                    mgr.declareBean(newName, values, values.getClass());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("creating array var " + newName);
                    }
                } else {
                    mgr.declareBean(newName, values[0], String.class);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("creating string var " + newName);
                    }
                }
            } catch (BSFException ex) {
                LOG.warn("Unable to set variable " + newName, ex);
            }

        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Done filtering");
        }
        return mgr;
    }
}
