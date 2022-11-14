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
package org.apache.struts.tiles;

import java.util.Map;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRequest;

import org.apache.struts.tiles.xmlDefinition.I18nFactorySet;

/**
 * Test {@link I18nFactorySet}.
 *
 * @version $Rev$ $Date$
 */
public class CustomI18nFactorySet extends I18nFactorySet {
    private static final long serialVersionUID = 5068476450131483964L;

    /**
     * Constructor.
     * Init the factory by reading appropriate configuration file.
     * @param servletContext Servlet context.
     * @param properties Map containing all properties.
     * @throws FactoryNotFoundException Can't find factory configuration file.
     */
    public CustomI18nFactorySet(ServletContext servletContext, Map<String, Object> properties)
        throws DefinitionsFactoryException {
        super(servletContext, properties);
    }

    public org.apache.struts.tiles.xmlDefinition.DefinitionsFactory createFactory(
        Object key,
        ServletRequest request,
        ServletContext servletContext)
        throws DefinitionsFactoryException {
        return super.createFactory(key, request, servletContext);
    }



}
