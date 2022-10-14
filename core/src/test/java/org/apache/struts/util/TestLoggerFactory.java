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
package org.apache.struts.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * An implementation of {@link ILoggerFactory} which always returns
 * {@link TestLogger} instances.
 *
 * @author Stefan Graff
 *
 * @since Struts 1.4.2
 */
public class TestLoggerFactory implements ILoggerFactory {

    /**
     * The {@link TestLogger} instances that have
     * already been created, keyed by logger name.
     */
    protected Map<String, TestLogger> instances = new ConcurrentHashMap<>();

    /**
     * Constructor
     */
    public TestLoggerFactory() {
    }

    /**
     * Return an appropriate {@link TestLogger}
     * instance by name.
     */
    public Logger getLogger(String name) {
        return instances.computeIfAbsent(name, TestLogger::new);
    }

    /**
     * Returns all collected log-messages to a
     * class.
     *
     * @param clazz the logged class
     *
     * @return all collected log-messages
     */
    public String getOutput(Class<?> clazz) {
        return getOutput(clazz.getName());
    }

    /**
     * Returns all collected log-messages to a
     * named logger.
     *
     * @param name the name of the logger
     *
     * @return all collected log-messages
     */
    public String getOutput(String name) {
        TestLogger log = instances.get(name);
        return log == null ? "" : log.getOutput();
    }

    /**
     * Clears/Delete all log-messages of
     * all loggers.
     */
    public void release() {
        instances.clear();
    }

    /**
     * Clears/Delete all log-messages of the
     * class.
     *
     * @param clazz the logged class
     */
    public void clearOutput(Class<?> clazz) {
        clearOutput(clazz.getName());
    }

    /**
     * Clears/Delete all log-messages of a
     * named logger.
     *
     * @param name the name of the logger
     */
    public void clearOutput(String name) {
        TestLogger log = instances.get(name);
        if (log != null) {
            log.clearOutput();
        }
    }
}