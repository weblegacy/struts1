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

package org.apache.struts.faces.application;

import java.beans.FeatureDescriptor;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import jakarta.el.CompositeELResolver;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.ELResolver;
import jakarta.el.PropertyNotWritableException;

import org.apache.struts.action.DynaActionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines property resolution behavior on instances of {@link DynaActionForm}.
 *
 * <p>This resolver handles base objects of type {@code DynaActionForm}
 * with requested property name {code map}.</p>
 *
 * <p>This resolver is in read-only mode, which means that {@link #isReadOnly}
 * will always return {@code true} and {@link #setValue} will always throw
 * {@code PropertyNotWritableException}.</p>
 *
 * <p>{@code ELResolver}s are combined together using
 * {@link CompositeELResolver}s, to define rich semantics for evaluating
 * an expression. See the JavaDocs for {@link ELResolver} for details.</p>
 *
 * @see CompositeELResolver
 * @see ELResolver
 * @see DynaActionForm#getMap()
 * @since Struts 1.4.1
 */
public class DynaActionFormELResolver extends ELResolver {

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(DynaActionFormELResolver.class);

    /**
     * Creates a new read {@code DynaActionFormELResolver}.
     */
    public DynaActionFormELResolver() {
        log.debug("Creating new Dyna-Action-From-ELResolver instance");
    }

    /**
     * If the base object is a {@code DynaActionForm} and the requested
     * property name is {code map} then the type {@code Map.class} will be
     * returned.
     *
     * <p>If the base is a {@code DynaActionForm} and the requested property
     * name is {code map}, the {@code propertyResolved} property of the
     * {@code ELContext} object must be set to {@code true} by this resolver,
     * before returning. If this property is not {@code true} after this
     * method is called, the caller should ignore the return value.</p>
     *
     * <p>Assuming the base is a {@code DynaActionForm} and the requested
     * property name is {code map}, this method will always return
     * {@code Map.class}. This is because a {@code DynaActionForm} with the
     * property 'map' accepts only the object {@code Map} as the value for
     * this given key.</p>
     *
     * @param context The context of this evaluation.
     * @param base The base to analyze. Only bases of type
     *     {@code DynaActionForm} are handled by this resolver.
     * @param property The key to return the acceptable type for. Only keys
     *     with value {@code map} are handled by this resolver.
     *
     * @return If the {@code propertyResolved} property of {@code ELContext}
     *     was set to {@code true}, then the most general acceptable type;
     *     otherwise undefined.
     *
     * @throws NullPointerException if context is {@code null}
     * @throws ELException if an exception was thrown while performing
     *     the property or variable resolution. The thrown exception
     *     must be included as the cause property of this exception, if
     *     available.
     */
    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }

        if (test(base, property)) {
            log.trace("Returning property-type '{}' for DynaActionForm '{}'",
                property, base);

            context.setPropertyResolved(true);
            return Map.class;
        }

        return null;
    }

    /**
     * If the base object is a {@code DynaActionForm} and the requested
     * property name is {@code map}, returns the {@code map} of the
     * {@code DynaActionForm}. Otherwise {@code null} is returned.
     *
     * <p>If the base is a {@code DynaActionForm} and the requested property
     * name is {@code map}, the {@code propertyResolved} property of the
     * {@code ELContext} object must be set to {@code true} by this resolver,
     * before returning. If this property is not {@code true} after this method
     * is called, the caller should ignore the return value.</p>
     *
     * <p>Just as in {@link DynaActionForm#getMap()}, just because {@code null}
     * is returned doesn't mean there is no mapping for the key; it's also
     * possible that the method explicitly returns {@code null}.</p>
     *
     * @param context The context of this evaluation.
     * @param base The base to be analyzed. Only bases of type
     *     {@code DynaActionForm} are handled by this resolver.
     * @param property The key whose associated value is to be returned. Only keys
     *     with value {@code map} are handled by this resolver.
     *
     * @return If the {@code propertyResolved} property of {@code ELContext}
     *     was set to {@code true}, then the value associated with the given key.
     *
     * @throws NullPointerException if context is {@code null}.
     * @throws ELException if an exception was thrown while performing
     *     the property or variable resolution. The thrown exception
     *     must be included as the cause property of this exception, if
     *     available.
     */
    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }

        if (test(base, property)) {
            log.trace("Returning property-value '{}' for DynaActionForm '{}'",
                property, base);

            context.setPropertyResolved(true);
            return (((DynaActionForm) base).getMap());
        }

        return null;
    }

    /**
     * If the base is a {@code DynaActionForm} and the requested property
     * name is {@code map}, this method always throw
     * {@code PropertyNotWritableException}, because the {@code map}
     * property is a ready-only property of the {@code DynaActionForm}
     * class.
     *
     * @param context The context of this evaluation.
     * @param base The base to be modified. Only bases of type
     *     {@code DynaActionForm} are handled by this resolver.
     * @param property The key with which the specified value is to be
     *     associated. Only keys with value {@code map} are handled by
     *     this resolver.
     * @param value The value to be associated with the specified key.

     * @throws NullPointerException if context is {@code null}.
     * @throws PropertyNotWritableException is always throw, because
     *     the {@code map} property is a ready-only property of the
     *     {@code DynaActionForm} class.
     * @throws ELException if an exception was thrown while performing
     *     the property or variable resolution. The thrown exception
     *     must be included as the cause property of this exception, if
     *     available.
     */
    @Override
    public void setValue(ELContext context, Object base, Object property, Object value) {
        if (context == null) {
            throw new NullPointerException();
        }

        if (test(base, property)) {
            log.trace("Set property-value '{}' for DynaActionForm '{}'",
                property, base);

            throw new PropertyNotWritableException(property.toString());
        }
    }

    /**
     * If the base is a {@code DynaActionForm} and the requested property
     * name is {@code map}, returns whether a call to {@link #setValue}
     * will always fail. This method always returns {@code true}, because
     * the {@code map} property is a ready-only property of the
     * {@code DynaActionForm} class.
     *
     * <p>If the base is a {@code DynaActionForm} and the requested property
     * name is {@code map}, the {@code propertyResolved} property of the
     * {@code ELContext} object must be set to {@code true} by this resolver,
     * before returning. If this property is not {@code true} after this
     * method is called, the caller should ignore the return value.</p>
     *
     * @param context The context of this evaluation.
     * @param base The base to analyze. Only bases of type
     *     {@code DynaActionForm} are handled by this resolver.
     * @param property The key to return the read-only status for. Only keys
     *     with value {@code map} are handled by this resolver.
     *
     * @return If the {@code propertyResolved} property of {@code ELContext}
     *     was set to {@code true}, then {@code true} if calling the
     *     {@code setValue} method will always fail or {@code false} if it
     *     is possible that such a call may succeed; otherwise undefined.
     *
     * @throws NullPointerException if context is {@code null}
     * @throws ELException if an exception was thrown while performing
     *     the property or variable resolution. The thrown exception
     *     must be included as the cause property of this exception, if
     *     available.
     */
    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }

        if (test(base, property)) {
            log.trace("Return ready-only status for property '{}' for DynaActionForm '{}'",
                property, base);

            context.setPropertyResolved(true);
            return true;
        }

        return false;
    }

    /**
     * If the base object is a {@code DynaActionForm}, returns an
     * {@code Iterator} containing the set of keys available in the
     * {@code DynaActionForm}. Otherwise, returns {@code null}.
     *
     * <p>The {@code Iterator} returned must contain zero or more
     * instances of {@link FeatureDescriptor}. Each info object
     * contains information about a key in the Map, and is initialized
     * as follows:</p>
     * <ul>
     *     <li>displayName - The return value of calling the
     *         {@code toString} method on this key, or {@code null} if
     *         the key is {@code null}.</li>
     *     <li>name - Same as displayName property.</li>
     *     <li>shortDescription - JavaDoc-Code of
     *         {@link DynaActionForm#getMap()}</li>
     *     <li>expert - {@code false}</li>
     *     <li>hidden - {@code false}</li>
     *     <li>preferred - {@code true}</li>
     * </ul>
     * In addition, the following named attributes must be set in the
     * returned {@code FeatureDescriptor}s:
     * <ul>
     *     <li>{@link ELResolver#TYPE} - The return value of calling
     *         the {@code getClass()} method on this key, or
     *         {@code null} if the key is {@code null}.</li>
     *     <li>{@link ELResolver#RESOLVABLE_AT_DESIGN_TIME} -
     *         {@code true}</li>
     * </ul>
     *
     * @param context The context of this evaluation.
     * @param base The object whose keys are to be iterated over. Only
     *     bases of type {@code DynaActionForm} are handled by this
     *     resolver.
     *
     * @return An {@code Iterator} containing zero or more (possibly
     *     infinitely more) {@code FeatureDescriptor} objects, each
     *     representing a key in this {@code DynaActionForm}, or
     *     {@code null} if the base object is not a
     *     {@code DynaActionForm}.
     */
    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(
            ELContext context,
            Object base) {

        if (base instanceof DynaActionForm) {
            log.trace("Get Feature-Descriptors for DynaActionForm '{}'", base);

            final FeatureDescriptor descriptor = new FeatureDescriptor();
            descriptor.setName("map");
            descriptor.setDisplayName("map");
            descriptor.setExpert(false);
            descriptor.setHidden(false);
            descriptor.setPreferred(true);
            descriptor.setShortDescription("Returns the Map containing the property "
                    + "values. This is done mostly to facilitate accessing the "
                    + "DynaActionForm through JavaBeans accessors, in order to use "
                    + "the JavaServer Pages Standard Tag Library (JSTL).");
            descriptor.setValue(TYPE, Map.class);
            descriptor.setValue(RESOLVABLE_AT_DESIGN_TIME, Boolean.TRUE);

            return Collections.singleton(descriptor).iterator();
        }

        return null;
    }

    /**
     * If the base object is a {@code DynaActionForm}, returns the most
     * general type that this resolver accepts for the {@code property}
     * argument. Otherwise, returns {@code null}.
     *
     * <p>Assuming the base is a {@code DynaActionForm}, this method will
     * always return {@code Object.class}. This is because any object is
     * accepted as a key and is coerced into a string.</p>
     *
     * @param context The context of this evaluation.
     * @param base The base to analyze. Only bases of type
     *     {@code DynaActionForm} are handled by this resolver.
     *
     * @return {@code null} if base is not a {@code DynaActionForm}; otherwise
     *     {@code Object.class}.
     */
    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        if (base instanceof DynaActionForm) {
            log.trace("Get Common-Property-Type for DynaActionForm '{}'", base);

            return Object.class;
        }
        return null;
    }

    /**
     * If the base object is a {@code DynaActionForm} and the requested
     * property name is {@code map}.
     *
     * @param base The requested base object. Only bases of type
     *     {@code DynaActionForm} are handled by this resolver.
     * @param property The requested key. Only keys
     *     with value {@code map} are handled by this resolver.
     *
     * @return {@code true} if request could handle with this resolver.
     */
    private static boolean test(final Object base, final Object property) {
        return base instanceof DynaActionForm && "map".equals(property);
    }
}