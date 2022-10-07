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
package org.apache.struts.config;

/**
 * This interface is to be implemented by any plugin for custom modification of
 * a module after it is been configured but before it is frozen. This allows for
 * overriding or adding properties after standard initialization.
 * <p>
 * The types of child configurations provided are {@link ActionConfig},
 * {@link ExceptionConfig}, {@link ForwardConfig} and
 * {@link MessageResourcesConfig}. If interested in a particular configuration
 * class, use the <code>instanceof</code> operator to check before casting.
 * <p>
 * Possible post-processors implementations may include property substitutions (<code>${...}</code>),
 * querying additional configuration from a repository, extended configuration
 * validation, preparing message resources for a particular domain/host,
 * logging, etc.
 *
 * @see BaseConfig
 * @see ActionConfig
 * @see ExceptionConfig
 * @see ForwardConfig
 * @see MessageResourcesConfig
 * @see ModuleConfig
 * @since Struts 1.4
 * @version $Rev$
 */
public interface ModuleConfigPostProcessor {

    /**
     * Applies this post-processor to the specified configuration object after
     * it has been initialized by Struts but before it is frozen.
     *
     * @param config the configuration
     * @param moduleConfig the parent module configuration
     */
    void postProcessAfterInitialization(BaseConfig config, ModuleConfig moduleConfig);

    /**
     * Modify the specified module after its standard initialization.
     *
     * @param moduleConfig the module configuration
     */
    void postProcessAfterInitialization(ModuleConfig moduleConfig);

    /**
     * Applies this post-processor to the specified configuration object
     * <i>before</i> it has been initialized and processed by Struts (such as
     * heirarchy extensions).
     *
     * @param config the configuration
     * @param moduleConfig the parent module configuration
     */
    void postProcessBeforeInitialization(BaseConfig config, ModuleConfig moduleConfig);

}
