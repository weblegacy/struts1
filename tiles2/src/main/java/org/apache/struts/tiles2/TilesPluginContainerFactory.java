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

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.apache.tiles.TilesContainer;
import org.apache.tiles.definition.DefinitionsFactory;
import org.apache.tiles.definition.UnresolvingLocaleDefinitionsFactory;
import org.apache.tiles.evaluator.AttributeEvaluatorFactory;
import org.apache.tiles.factory.BasicTilesContainerFactory;
import org.apache.tiles.locale.LocaleResolver;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.ApplicationResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TilesPluginContainerFactory extends BasicTilesContainerFactory {

    /**
     * The {@code Log} instance for this class.
     */
    private transient final Logger log =
        LoggerFactory.getLogger(TilesPluginContainerFactory.class);

    @Override
    public TilesContainer createContainer(ApplicationContext applicationContext) {
        TilesPluginContainer container = instantiateContainer(applicationContext);
        container.setApplicationContext(applicationContext);
        LocaleResolver resolver = createLocaleResolver(applicationContext);
        container.setLocaleResolver(resolver);
        container.setDefinitionsFactory(createDefinitionsFactory(applicationContext,
                resolver));
        AttributeEvaluatorFactory attributeEvaluatorFactory = createAttributeEvaluatorFactory(
                applicationContext, resolver);
        container.setAttributeEvaluatorFactory(attributeEvaluatorFactory);
        container.setPreparerFactory(createPreparerFactory(applicationContext));
        TilesContainer injectedContainer = createDecoratedContainer(container, applicationContext);
        container.setRendererFactory(createRendererFactory(applicationContext,
                injectedContainer, attributeEvaluatorFactory));
        return injectedContainer;
    }

    @Override
    protected TilesPluginContainer instantiateContainer(
            ApplicationContext context) {
        return new TilesPluginContainer();
    }

    /**
     * Returns a list containing the URLs to be parsed. By default, it returns a
     * list containing the URL point to "definitions-config".
     *
     * @param applicationContext The Tiles application context.
     *
     * @return The source URLs.
     * @since Struts 1.4.1
     */
    protected List<ApplicationResource> getSources(ApplicationContext applicationContext) {
        String param = applicationContext
                .getInitParams().get(DefinitionsFactory.DEFINITIONS_CONFIG);
        if (param == null) {
            param = applicationContext
                    .getInitParams().get("definitions-config");
        }

        boolean error = true;
        if (param != null) {
            if (param.isEmpty()) {
                log.warn("getSources - empty parameters");
            } else if (param.contains("\u0000")) {
                log.warn("getSources - ignore file with nul-characters '{}'", param.replace('\u0000', '_') );
            } else {
                try {
                    final Path p = Paths.get(param).normalize();
                    if (p.toString().charAt(0) != '.') {
                        error = false;
                    } else {
                        log.warn("getSources - path not normalized '{}'", p.toString());
                    }
                } catch (InvalidPathException e) {
                    log.warn("getSources - illegal path '{}'", e.getInput(), e);
                    param = null;
                }
            }
        }

        if (error) {
            param = "/WEB-INF/tiles.xml";
            log.debug("getSources - use default '{}'", param);
        } else {
            log.debug("getSources - use '{}'", param);
        }

        return Collections
                .singletonList(applicationContext.getResource(param));
    }

    /**
     * Creates the definitions factory. By default it creates a
     * {@link UnresolvingLocaleDefinitionsFactory} with default dependencies.
     *
     * @param container The Tiles-Plugin-Container.
     * @param applicationContext The Tiles application context.
     *
     * @return The definitions factory.
     * @since Struts 1.4.1
     */
    public DefinitionsFactory createDefinitionsFactory(TilesPluginContainer container,
            ApplicationContext applicationContext) {
        LocaleResolver resolver = container.getLocaleResolverIntern();
        return createDefinitionsFactory(applicationContext, resolver);
    }
}