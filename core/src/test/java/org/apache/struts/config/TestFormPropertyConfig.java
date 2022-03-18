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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link FormPropertyConfig} class.
 *
 * @version $Rev$ $Date$
 */
public class TestFormPropertyConfig {
    @Test
    public void testBasicInherit()
        throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        FormPropertyConfig base =
            new FormPropertyConfig("base", "java.lang.String[]", "", 10);
        String baseCount = "10";

        base.setProperty("count", baseCount);

        FormPropertyConfig sub = new FormPropertyConfig();

        sub.setName("base");

        sub.inheritFrom(base);

        assertEquals(base.getType(), sub.getType(), "Type was not inherited");
        assertEquals(base.getInitial(), sub.getInitial(), "Initial is incorrect");
        assertEquals(base.getSize(), sub.getSize(), "Size was not inherited");
        assertEquals(baseCount, sub.getProperty("count"),
            "Arbitrary config property was not inherited");
    }

    @Test
    public void testInheritWithInitialOverride()
        throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        FormPropertyConfig base =
            new FormPropertyConfig("base", "java.lang.String", "value");

        FormPropertyConfig sub = new FormPropertyConfig();

        sub.setName("base");

        String initial = "otherValue";

        sub.setInitial(initial);

        sub.inheritFrom(base);

        assertEquals(base.getType(), sub.getType(), "Type was not inherited");
        assertEquals(initial, sub.getInitial(), "Initial is incorrect");
        assertEquals(base.getSize(), sub.getSize(), "Size is incorrect");
    }

    @Test
    public void testInheritWithTypeOverride()
        throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        FormPropertyConfig base =
            new FormPropertyConfig("base", "java.lang.String", "");

        FormPropertyConfig sub = new FormPropertyConfig();

        sub.setName("base");
        sub.setType("java.lang.Integer");

        sub.inheritFrom(base);

        assertEquals("java.lang.Integer", sub.getType(), "Type is incorrect");
        assertEquals(base.getInitial(), sub.getInitial(), "Initial is incorrect");
        assertEquals(base.getSize(), sub.getSize(), "Size is incorrect");
    }

    @Test
    public void testInheritWithTypeOverride2()
        throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        FormPropertyConfig base =
            new FormPropertyConfig("base", "java.lang.String", "");

        FormPropertyConfig sub = new FormPropertyConfig();

        sub.setName("base");

        String type = "java.lang.Integer[]";
        int size = 10;

        sub.setType(type);
        sub.setSize(size);

        sub.inheritFrom(base);

        assertEquals(type, sub.getType(), "Type is incorrect");
        assertEquals(base.getInitial(), sub.getInitial(), "Initial is incorrect");
        assertEquals(size, sub.getSize(), "Size is incorrect");
    }

    @Test
    public void testInheritWithSizeOverride()
        throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        FormPropertyConfig base =
            new FormPropertyConfig("base", "java.lang.String[]", "", 20);

        FormPropertyConfig sub = new FormPropertyConfig();

        sub.setName("base");

        int size = 50;

        sub.setSize(size);

        sub.inheritFrom(base);

        assertEquals(base.getType(), sub.getType(), "Type was not inherited");
        assertEquals(base.getInitial(), sub.getInitial(), "Initial is incorrect");
        assertEquals(size, sub.getSize(), "Size is incorrect");
    }
}
