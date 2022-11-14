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

package org.apache.struts.webapp.example2;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.faces.application.ViewHandler;
import jakarta.faces.application.ViewHandlerWrapper;
import jakarta.faces.context.FacesContext;


/**
 * Custom {@code ViewHandler} implementation that adds features
 * specific to the Struts-Faces Integration Library. It leverages the
 * "decorator pattern" customization strategy that JSF supports, by
 * delegating most processing to the {@code ViewHandler} instance
 * handed to our constructor.
 */
public class ViewHandlerImpl extends ViewHandlerWrapper {


    // -------------------------------------------------------- Static Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(ViewHandlerImpl.class);


    // ------------------------------------------------------------ Constructors


    /**
     * Construct a {@code ViewHandlerImpl} decorating the
     * specified {@code ViewHandler} instance.
     *
     * @param oldViewHandler {@code ViewHandler} to be decorated
     */
    public ViewHandlerImpl(ViewHandler oldViewHandler) {
        super(oldViewHandler);
        LOG.debug("Creating ViewHandler instance, wrapping handler {}",
            oldViewHandler);
    }


    // ----------------------------------------------------- Specialized Methods


    /**
     * Replace extension {@code .jsp} with {@code .faces}.
     */
    public String getActionURL(FacesContext context, String viewId) {
        String ret = super.getActionURL(context, viewId);
        int i = 0;
        if (ret.endsWith(".do")) {
            i = 3;
        } else if (ret.endsWith(".jsp")) {
            i = 4;
        }
        if (i > 0) {
            ret = ret.substring(0, ret.length() - i) + ".faces";
        }
        return ret;
    }
}