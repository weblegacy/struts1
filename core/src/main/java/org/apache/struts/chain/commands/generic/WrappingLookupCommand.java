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

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.CatalogFactory;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;
import org.apache.struts.chain.commands.util.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Variant on chain LookupCommand which can optionally wrap the context it
 * passes to the looked up command in an alternative class.
 */
public class WrappingLookupCommand implements Filter<Context> {

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(WrappingLookupCommand.class);

    // ------------------------------------------------------ Instance Variables

    /**
     * Field for property.
     */
    private String catalogName = null;

    /**
     * Field for property.
     */
    private String name = null;

    /**
     * Field for property.
     */
    private String nameKey = null;

    /**
     * Field for property.
     */
    private String wrapperClassName = null;

    /**
     * Field for property.
     */
    private boolean optional = false;

    /**
     * Zero-argument constructor.
     */
    public WrappingLookupCommand() {
        catalogName = null;
        name = null;
        nameKey = null;
        optional = false;
    }

    /**
     * Return CatalogName property.
     *
     * @return Value of CatalogName property.
     */
    public String getCatalogName() {
        return catalogName;
    }

    /**
     * Set CatalogName property.
     *
     * @param catalogName New value for CatalogName
     */
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    /**
     * Retrieve Name property.
     *
     * @return Value of Name property
     */
    public String getName() {
        return name;
    }

    /**
     * Set Name property.
     *
     * @param name New value for Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return NameKey property.
     *
     * @return Value of NameKey property.
     */
    public String getNameKey() {
        return nameKey;
    }

    /**
     * Set NameKey property.
     *
     * @param nameKey New value for NameKey
     */
    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    /**
     * Test Optional property.
     *
     * @return TRUE if Optional is TRUE.
     */
    public boolean isOptional() {
        return optional;
    }

    /**
     * Set Optional property.
     *
     * @param optional New value for Optional
     */
    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    /**
     * Return the WrapperClass property.
     *
     * @return The WrapperClass property
     */
    public String getWrapperClassName() {
        return wrapperClassName;
    }

    /**
     * Set WrappClassName property.
     *
     * @param wrapperClassName The name of a WrapperClass
     */
    public void setWrapperClassName(String wrapperClassName) {
        this.wrapperClassName = wrapperClassName;
    }

    /**
     * Invoke the Command for a Context, returning TRUE if processing should
     * halt.
     *
     * @param context Our ActionContext
     *
     * @return TRUE if processing should halt
     *
     * @throws Exception On any error
     */
    public boolean execute(Context context)
        throws Exception {
        log.trace("execute [{}]", this);

        Command<Context> command = getCommand(context);

        if (command != null) {
            return command.execute(getContext(context));
        } else {
            return CONTINUE_PROCESSING;
        }
    }

    /**
     * Process the Exception for any Command that is a filter.
     *
     * @param context   Our ActionContext
     * @param exception The Exception thrown by another Command in a Chain
     *
     * @return TRUE if there is a Filter to process
     */
    public boolean postprocess(Context context, Exception exception) {
        Command<Context> command = getCommand(context);

        if (command != null && command instanceof Filter) {
            try {
                final Filter<Context> filter = (Filter<Context>) command;
                return filter.postprocess(getContext(context), exception);
            } catch (NoSuchMethodException | IllegalAccessException |
                    InvocationTargetException | InstantiationException |
                    ClassNotFoundException ex) {
                log.error("Error wrapping context in postprocess", ex);
            }
        }

        return false;
    }

    /**
     * Return the Command to process for this Context.
     *
     * @param context The Context we are processing
     *
     * @return The Command to process for this Context
     */
    protected Command<Context> getCommand(Context context) {
        CatalogFactory<Context> catalogFactory = CatalogFactory.getInstance();
        String catalogName = getCatalogName();
        Catalog<Context> catalog;

        if (catalogName == null) {
            catalog = catalogFactory.getCatalog();
            catalogName = "{default}"; // for debugging purposes
        } else {
            catalog = catalogFactory.getCatalog(catalogName);
        }

        if (catalog == null) {
            throw new IllegalArgumentException("Cannot find catalog '"
                + catalogName + "'");
        }

        Command<Context> command;
        String name = getName();

        if (name == null) {
            name = (String) context.get(getNameKey());
        }

        if (name != null) {
            log.debug("Lookup command {} in catalog {}",
                name, catalogName);

            command = catalog.getCommand(name);

            log.debug("Found command {}; optional: {}",
                command, isOptional());

            if ((command == null) && !isOptional()) {
                throw new IllegalArgumentException("Cannot find command " + "'"
                    + name + "' in catalog '" + catalogName + "'");
            } else {
                return command;
            }
        } else {
            throw new IllegalArgumentException("No command name");
        }
    }

    /**
     * If the wrapperClassName property is not null, return a Context of the
     * type specified by wrapperClassName, instantiated using a single-arg
     * constructor which takes the context passed as an argument to this
     * method.</p>
     *
     * <p>This method throws an exception if the wrapperClass cannot be found,
     * or if there are any errors instantiating the wrapping context.</p>
     *
     * @param context Context we are processing
     *
     * @return Context wrapper
     *
     * @throws ClassNotFoundException    On failed instantiation
     * @throws InstantiationException    On failed instantiation
     * @throws InvocationTargetException On failed instantiation
     * @throws IllegalAccessException    On failed instantiation
     * @throws NoSuchMethodException     On failed instantiation
     */
    protected Context getContext(Context context)
        throws ClassNotFoundException, InstantiationException,
            InvocationTargetException, IllegalAccessException,
            NoSuchMethodException {
        if (wrapperClassName == null) {
            log.debug("No defined wrapper class; "
                + "returning original context.");

            return context;
        }

        log.debug("Looking for wrapper class: {}", wrapperClassName);

        Class<?> wrapperClass = ClassUtils.getApplicationClass(wrapperClassName);

        log.debug("Instantiating wrapper class");

        return (Context) ConstructorUtils.invokeConstructor(wrapperClass, context);
    }
}