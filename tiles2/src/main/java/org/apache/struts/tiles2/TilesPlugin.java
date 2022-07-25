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

package org.apache.struts.tiles2;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.chain.ComposableRequestProcessor;
import org.apache.struts.config.ControllerConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.PlugInConfig;
import org.apache.struts.tiles2.preparer.StrutsPreparerFactory;
import org.apache.struts.tiles2.util.PlugInConfigContextAdapter;
import org.apache.struts.util.ModuleUtils;
import org.apache.struts.util.RequestUtils;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.TilesException;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.context.ChainedTilesContextFactory;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.definition.DefinitionsFactory;
import org.apache.tiles.definition.UrlDefinitionsFactory;
import org.apache.tiles.factory.KeyedDefinitionsFactoryTilesContainerFactory;
import org.apache.tiles.factory.TilesContainerFactory;
import org.apache.tiles.impl.BasicTilesContainer;
import org.apache.tiles.impl.KeyedDefinitionsFactoryTilesContainer;
import org.apache.tiles.impl.KeyedDefinitionsFactoryTilesContainer.KeyExtractor;
import org.apache.tiles.servlet.context.ServletTilesRequestContext;

/**
 * Tiles Plugin used to initialize Tiles.
 * This plugin is to be used with Struts 1.1 in association with
 * {@link TilesRequestProcessor}.
 * <br>
 * This plugin creates one definition factory for each Struts-module. The definition factory
 * configuration is read first from 'web.xml' (backward compatibility), then it is
 * overloaded with values found in the plugin property values.
 * <br>
 * The plugin changes the Struts configuration by specifying a {@link TilesRequestProcessor} as
 * request processor. If you want to use your own RequestProcessor,
 * it should subclass TilesRequestProcessor.
 * <br>
 * This plugin can also be used to create one single factory for all modules.
 * This behavior is enabled by specifying <code>moduleAware=false</code> in each
 * plugin properties. In this case, the definition factory
 * configuration file is read by the first Tiles plugin to be initialized. The order is
 * determined by the order of modules declaration in web.xml. The first module
 * is always the default one if it exists.
 * The plugin should be declared in each struts-config.xml file in order to
 * properly initialize the request processor.
 *
 * @version $Rev$ $Date$
 * @since Struts 1.1
 */
// TODO Complete the plugin to be module-aware.
public class TilesPlugin implements PlugIn {

    /**
     * Defaults form Tiles 2 configuration in case of a module-aware
     * configuration.
     */
    private static final Map MODULE_AWARE_DEFAULTS =
        new HashMap();

    /**
     * Defaults form Tiles 2 configuration in case of a configuration without
     * modules.
     */
    private static final Map NO_MODULE_DEFAULTS =
        new HashMap();

    static {
        NO_MODULE_DEFAULTS.put(TilesContainerFactory
                .CONTEXT_FACTORY_INIT_PARAM,
                ChainedTilesContextFactory.class.getName());
        NO_MODULE_DEFAULTS.put(TilesContainerFactory
                .DEFINITIONS_FACTORY_INIT_PARAM,
                UrlDefinitionsFactory.class.getName());
        NO_MODULE_DEFAULTS.put(TilesContainerFactory
                .PREPARER_FACTORY_INIT_PARAM,
                StrutsPreparerFactory.class.getName());

        MODULE_AWARE_DEFAULTS.putAll(NO_MODULE_DEFAULTS);
        MODULE_AWARE_DEFAULTS.put(
                KeyedDefinitionsFactoryTilesContainerFactory
                .KEY_EXTRACTOR_CLASS_INIT_PARAM,
                ModuleKeyExtractor.class.getName());
        MODULE_AWARE_DEFAULTS.put(TilesContainerFactory
                .CONTAINER_FACTORY_INIT_PARAM,
                KeyedDefinitionsFactoryTilesContainerFactory.class.getName());
    }

    /**
     * Commons Logging instance.
     */
    protected static Log log = LogFactory.getLog(TilesPlugin.class);

    /**
     * Is the factory module aware?
     */
    protected boolean moduleAware = false;

    /**
     * The plugin config object provided by the ActionServlet initializing
     * this plugin.
     */
    protected PlugInConfig currentPlugInConfigObject = null;

    /**
     * The plugin config object adapted to become a context-like object, that
     * exposes init parameters methods.
     */
    protected PlugInConfigContextAdapter currentPlugInConfigContextAdapter = null;

    /**
     * Get the module aware flag.
     * @return <code>true</code>: user wants a single factory instance,
     * <code>false:</code> user wants multiple factory instances (one per module with Struts)
     */
    public boolean isModuleAware() {
        return moduleAware;
    }

    /**
     * Set the module aware flag.
     * This flag is only meaningful if the property <code>tilesUtilImplClassname</code> is not
     * set.
     * @param moduleAware <code>true</code>: user wants a single factory instance,
     * <code>false:</code> user wants multiple factory instances (one per module with Struts)
     */
    public void setModuleAware(boolean moduleAware) {
        this.moduleAware = moduleAware;
    }

    /**
     * <p>Receive notification that the specified module is being
     * started up.</p>
     *
     * @param servlet ActionServlet that is managing all the modules
     *  in this web application.
     * @param moduleConfig ModuleConfig for the module with which
     *  this plugin is associated.
     *
     * @exception ServletException if this <code>PlugIn</code> cannot
     *  be successfully initialized.
     */
    public void init(ActionServlet servlet, ModuleConfig moduleConfig)
        throws ServletException {

        currentPlugInConfigContextAdapter = new PlugInConfigContextAdapter(
                this.currentPlugInConfigObject, servlet.getServletContext());

        // Set RequestProcessor class
        this.initRequestProcessorClass(moduleConfig);

        // Initialize Tiles
        try {
            TilesContainerFactory factory;
            TilesContainer container;
            if (moduleAware) {
                factory = TilesContainerFactory.getFactory(
                        currentPlugInConfigContextAdapter,
                        MODULE_AWARE_DEFAULTS);
                container = TilesAccess.getContainer(
                        currentPlugInConfigContextAdapter);
                if (container == null) {
                    container = factory.createContainer(
                            currentPlugInConfigContextAdapter);
                    TilesAccess.setContainer(currentPlugInConfigContextAdapter,
                            container);
                }
                if (container instanceof KeyedDefinitionsFactoryTilesContainer) {
                    KeyedDefinitionsFactoryTilesContainer keyedContainer =
                        (KeyedDefinitionsFactoryTilesContainer) container;
                    // If we have a definition factory for the current module
                    // prefix then we are trying to re-initialize the same module,
                    // and it is wrong!
                    if (keyedContainer.getProperDefinitionsFactory(moduleConfig
                            .getPrefix()) != null) {
                        throw new ServletException("Tiles definitions factory for module '"
                                        + moduleConfig.getPrefix()
                                        + "' has already been configured");
                    }
                    if (factory instanceof KeyedDefinitionsFactoryTilesContainerFactory) {
                        DefinitionsFactory defsFactory =
                            ((KeyedDefinitionsFactoryTilesContainerFactory) factory)
                            .createDefinitionsFactory(currentPlugInConfigContextAdapter);
                        Map initParameters = new HashMap();
                        String param = (String) currentPlugInConfigObject
                                .getProperties().get(BasicTilesContainer
                                        .DEFINITIONS_CONFIG);
                        if (param == null) {
                            param = (String) currentPlugInConfigObject
                                .getProperties().get("definitions-config");
                        }
                        if (param != null) {
                            initParameters.put(BasicTilesContainer
                                    .DEFINITIONS_CONFIG, param);
                        }
                        keyedContainer.setDefinitionsFactory(moduleConfig.getPrefix(),
                                        defsFactory, initParameters);
                    } else {
                        log.warn("The created factory is not instance of "
                                + "KeyedDefinitionsFactoryTilesContainerFactory"
                                + " and cannot be configured correctly");
                    }
                } else {
                    log.warn("The created container is not instance of "
                            + "KeyedDefinitionsFactoryTilesContainer"
                            + " and cannot be configured correctly");
                }
            } else {
                factory = TilesContainerFactory
                        .getFactory(currentPlugInConfigContextAdapter);
                if (TilesAccess.getContainer(currentPlugInConfigContextAdapter) != null) {
                    throw new ServletException(
                            "Tiles container has already been configured");
                }
                container = factory.createContainer(
                        currentPlugInConfigContextAdapter);
                TilesAccess.setContainer(currentPlugInConfigContextAdapter,
                        container);
            }
        } catch (TilesException e) {
            log.fatal("Unable to retrieve tiles factory.", e);
            throw new IllegalStateException("Unable to instantiate container.");
        }
    }

    /**
     * End plugin.
     */
    public void destroy() {
        try {
            TilesAccess.setContainer(currentPlugInConfigContextAdapter, null);
        } catch (TilesException e) {
            log.warn("Unable to remove tiles container from service.");
        }
    }

    /**
     * Set RequestProcessor to appropriate Tiles {@link RequestProcessor}.
     * First, check if a RequestProcessor is specified. If yes, check if it extends
     * the appropriate {@link TilesRequestProcessor} class. If not, set processor class to
     * TilesRequestProcessor.
     *
     * @param config ModuleConfig for the module with which
     *  this plugin is associated.
     * @throws ServletException On errors.
     */
    protected void initRequestProcessorClass(ModuleConfig config)
        throws ServletException {

        String tilesProcessorClassname = TilesRequestProcessor.class.getName();
        ControllerConfig ctrlConfig = config.getControllerConfig();
        String configProcessorClassname = ctrlConfig.getProcessorClass();

        // Check if specified classname exist
        Class configProcessorClass;
        try {
            configProcessorClass =
                RequestUtils.applicationClass(configProcessorClassname);

        } catch (ClassNotFoundException ex) {
            log.fatal(
                "Can't set TilesRequestProcessor: bad class name '"
                    + configProcessorClassname
                    + "'.");
            throw new ServletException(ex);
        }

        // Check to see if request processor uses struts-chain.  If so,
        // no need to replace the request processor.
        if (ComposableRequestProcessor.class.isAssignableFrom(configProcessorClass)) {
            return;
        }

        // Check if it is the default request processor or Tiles one.
        // If true, replace by Tiles' one.
        if (configProcessorClassname.equals(RequestProcessor.class.getName())
            || configProcessorClassname.endsWith(tilesProcessorClassname)) {

            ctrlConfig.setProcessorClass(tilesProcessorClassname);
            return;
        }

        // Check if specified request processor is compatible with Tiles.
        Class tilesProcessorClass = TilesRequestProcessor.class;
        if (!tilesProcessorClass.isAssignableFrom(configProcessorClass)) {
            // Not compatible
            String msg =
                "TilesPlugin : Specified RequestProcessor not compatible with TilesRequestProcessor";
            if (log.isFatalEnabled()) {
                log.fatal(msg);
            }
            throw new ServletException(msg);
        }
    }

    /**
     * Method used by the ActionServlet initializing this plugin.
     * Set the plugin config object read from module config.
     * @param plugInConfigObject PlugInConfig.
     */
    public void setCurrentPlugInConfigObject(PlugInConfig plugInConfigObject) {
        this.currentPlugInConfigObject = plugInConfigObject;
    }

    /**
     * Extracts the definitions factory key according to the module prefix.
     */
    public static class ModuleKeyExtractor implements KeyExtractor {

        /** {@inheritDoc} */
        public String getDefinitionsFactoryKey(TilesRequestContext request) {
            String retValue = null;

            if (request instanceof ServletTilesRequestContext) {
                HttpServletRequest servletRequest =
                    (HttpServletRequest) ((ServletTilesRequestContext) request).getRequest();
                ModuleConfig config = ModuleUtils.getInstance().getModuleConfig(
                        servletRequest);

                if (config == null) {
                    // ModuleConfig not found in current request. Select it.
                    ModuleUtils.getInstance().selectModule(servletRequest,
                            servletRequest.getSession().getServletContext());
                    config = ModuleUtils.getInstance().getModuleConfig(servletRequest);
                }

                if (config != null) {
                    retValue = config.getPrefix();
                }
            }
            return retValue;
        }

    }
}
