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
package org.apache.struts.extras.plugins;

import jakarta.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.MessageResourcesConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.PlugInConfig;
import org.apache.struts.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Convenient implementation of {@link PlugIn} that performs as many
 * verification tests on the information stored in the {@link ModuleConfig}
 * for this module as is practical.  Based on the setting of the
 * <code>fatal</code> property (which defaults to <code>true</code>), the
 * detection of any such errors will cause a <code>ServletException</code> to
 * be thrown from the <code>init</code> method, which will ultimately cause
 * the initialization of your Struts controller servlet to fail.</p>
 *
 * @version $Rev$ $Date$
 * @since Struts 1.1
 */
public class ModuleConfigVerifier implements PlugIn {

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(ModuleConfigVerifier.class);

    // ----------------------------------------------------- Instance Variables

    /**
     * <p>The {@link ModuleConfig} instance for our module.</p>
     */
    protected ModuleConfig config = null;

    /**
     * <p>The {@link ActionServlet} instance we are associated with.</p>
     */
    protected ActionServlet servlet = null;

    // ------------------------------------------------------------- Properties

    /**
     * <p>Should the existence of configuration errors be fatal.</p>
     */
    private boolean fatal = true;

    /**
     * <p>Return the "configuation errors are fatal" flag.</p>
     */
    public boolean isFatal() {
        return (this.fatal);
    }

    /**
     * <p>Set the "configuration errors are fatal" flag.</p>
     *
     * @param fatal The new flag value
     */
    public void setFatal(boolean fatal) {
        this.fatal = fatal;
    }

    // --------------------------------------------------------- Public Methods

    /**
     * <p>Receive notification that our owning module is being shut down.</p>
     */
    public void destroy() {
        ; // No action required
    }

    // See interface for Javadoc.
    public void init(ActionServlet servlet, ModuleConfig config)
        throws ServletException {
        this.servlet = servlet;
        this.config = config;

        boolean ok = true;

        log.atInfo().log(() -> servlet.getInternal().getMessage("configVerifying"));

        // Perform detailed validations of each portion of ModuleConfig
        // :TODO: Complete methods to verify Action, Controller, et al, configurations.

        /*
        if (!verifyActionConfigs()) {
            ok = false;
        }
        */
        if (!verifyActionMappingClass()) {
            ok = false;
        }

        /*
        if (!verifyControllerConfig()) {
            ok = false;
        }
        if (!verifyExceptionConfigs()) {
            ok = false;
        }
        if (!verifyFormBeanConfigs()) {
            ok = false;
        }
        */
        if (!verifyForwardConfigs()) {
            ok = false;
        }

        if (!verifyMessageResourcesConfigs()) {
            ok = false;
        }

        if (!verifyPlugInConfigs()) {
            ok = false;
        }

        // Throw an exception on a fatal error
        log.atInfo().log(() -> servlet.getInternal().getMessage("configCompleted"));

        if (!ok && isFatal()) {
            throw new ServletException(servlet.getInternal().getMessage("configFatal"));
        }
    }

    // ------------------------------------------------------ Protected Methods

    /**
     * <p>Return <code>true</code> if information returned by
     * <code>config.getActionMappingClass</code> is all valid; otherwise, log
     * error messages and return <code>false</code>.</p>
     */
    protected boolean verifyActionMappingClass() {
        String amcName = config.getActionMappingClass();

        if (amcName == null) {
            log.atError().log(() -> servlet.getInternal().getMessage("verifyActionMappingClass.missing"));

            return (false);
        }

        try {
            RequestUtils.applicationClass(amcName);
        } catch (ClassNotFoundException e) {
            log.atError().log(() -> servlet.getInternal().getMessage("verifyActionMappingClass.invalid",
                    amcName));

            return (false);
        }

        return (true);
    }

    /**
     * <p>Return <code>true</code> if information returned by
     * <code>config.findForwardConfigs</code> is all valid; otherwise, log
     * error messages and return <code>false</code>.</p>
     */
    protected boolean verifyForwardConfigs() {
        boolean ok = true;
        ForwardConfig[] fcs = config.findForwardConfigs();

        for (ForwardConfig fc : fcs) {
            String path = fc.getPath();

            if (path == null) {
                log.atError().log(() -> servlet.getInternal().getMessage("verifyForwardConfigs.missing",
                        fc.getName()));
                ok = false;
            } else if (!path.startsWith("/")) {
                log.atError().log(() -> servlet.getInternal().getMessage("verifyForwardConfigs.invalid",
                        path, fc.getName()));
            }
        }

        return (ok);
    }

    /**
     * <p>Return <code>true</code> if information returned by
     * <code>config.findMessageResourcesConfigs</code> is all valid;
     * otherwise, log error messages and return <code>false</code>.</p>
     */
    protected boolean verifyMessageResourcesConfigs() {
        boolean ok = true;
        MessageResourcesConfig[] mrcs = config.findMessageResourcesConfigs();

        for (int i = 0; i < mrcs.length; i++) {
            String factory = mrcs[i].getFactory();

            if (factory == null) {
                log.atError().log(() -> servlet.getInternal().getMessage("verifyMessageResourcesConfigs.missing"));
                ok = false;
            } else {
                try {
                    RequestUtils.applicationClass(factory);
                } catch (ClassNotFoundException e) {
                    log.atError().log(() -> servlet.getInternal().getMessage("verifyMessageResourcesConfigs.invalid",
                            factory));
                    ok = false;
                }
            }

            String key = mrcs[i].getKey();

            if (key == null) {
                log.atError().log(() -> servlet.getInternal().getMessage("verifyMessageResourcesConfigs.key"));
            }
        }

        return (ok);
    }

    /**
     * <p>Return <code>true</code> if information returned by
     * <code>config.findPluginConfigs</code> is all valid; otherwise, log
     * error messages and return <code>false</code>.</p>
     */
    protected boolean verifyPlugInConfigs() {
        boolean ok = true;
        PlugInConfig[] pics = config.findPlugInConfigs();

        for (int i = 0; i < pics.length; i++) {
            String className = pics[i].getClassName();

            if (className == null) {
                log.atError().log(() -> servlet.getInternal().getMessage("verifyPlugInConfigs.missing"));
                ok = false;
            } else {
                try {
                    RequestUtils.applicationClass(className);
                } catch (ClassNotFoundException e) {
                    log.atError().log(() -> servlet.getInternal().getMessage("verifyPlugInConfigs.invalid",
                            className));
                    ok = false;
                }
            }
        }

        return (ok);
    }
}