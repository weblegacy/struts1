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
package org.apache.struts.chain.commands.generic;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.chain.web.servlet.ServletWebContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *  JUnitTest case for class: {@link WrappingLookupCommand}
 */
public class TestWrappingLookupCommand {

    /* setUp method for test case */
    @BeforeEach
    protected void setUp() {
    }

    /* tearDown method for test case */
    @AfterEach
    protected void tearDown() {
    }

    @Test
    public void testSame() throws ClassNotFoundException, InstantiationException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        WrappingLookupCommand command = new WrappingLookupCommand();
        Context testContext = new ContextBase();

        Context wrapped = command.getContext(testContext);

        assertNotNull(wrapped);
        assertSame(testContext, wrapped);
    }

    @Test
    public void testWrapContextSubclass()
        throws ClassNotFoundException, InstantiationException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        WrappingLookupCommand command = new WrappingLookupCommand();

        command.setWrapperClassName(ServletActionContext.class.getName());

        Context testContext = new ServletWebContext();

        Context wrapped = command.getContext(testContext);

        assertNotNull(wrapped);
        assertInstanceOf(ServletActionContext.class, wrapped);
    }
}
