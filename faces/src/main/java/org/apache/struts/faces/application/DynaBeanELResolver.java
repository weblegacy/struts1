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
import java.util.Arrays;
import java.util.Iterator;

import jakarta.el.CompositeELResolver;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.ELResolver;
import jakarta.el.PropertyNotFoundException;
import jakarta.el.PropertyNotWritableException;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Defines property resolution behavior on instances of {@link DynaBean}.
 *
 * <p>This resolver handles base objects of type {@code DynaBean}.
 * It accepts any object as a property and uses that object as a key in
 * the map. The resulting value is the value in the map that is associated with
 * that key.</p>
 *
 * <p>This resolver can be constructed in read-only mode, which means that
 * {@link #isReadOnly} will always return {@code true} and {@link #setValue}
 * will always throw {@code PropertyNotWritableException}.</p>
 *
 * <p>{@code ELResolver}s are combined together using
 * {@link CompositeELResolver}s, to define rich semantics for evaluating
 * an expression. See the JavaDocs for {@link ELResolver} for details.</p>
 *
 * @see CompositeELResolver
 * @see ELResolver
 * @see DynaBean
 * @since Struts 1.4.1
 */
public class DynaBeanELResolver extends ELResolver {

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(DynaBeanELResolver.class);

    /**
     * Flag if this {@code ELRsolver} is in read-only-mode {@code true}.
     */
    private final boolean readOnly;

    /**
     * Creates a new read/write {@code DynaBeanELResolver}.
     */
    public DynaBeanELResolver() {
        this(false);
    }

    /**
     * Creates a new {@code DynaBeanELResolver} whose read-only status is
     * determined by the given parameter.
     *
     * @param readOnly {@code true} if this resolver cannot modify
     *     properties; {@code false} otherwise.
     */
    public DynaBeanELResolver(boolean readOnly) {
        log.debug("Creating new Dyna-Action-From-ELResolver "
            + "instance with read-only: '{}'", readOnly);

        this.readOnly = readOnly;
    }

    /**
     * If the base object is a {@code DynaBean}, returns the most general
     * acceptable type for a value in this map..
     *
     * <p>If the base is a {@code DynaBean}, the {@code propertyResolved}
     * property of the {@code ELContext} object must be set to {@code true}
     * by this resolver, before returning. If this property is not
     * {@code true} after this method is called, the caller should ignore
     * the return value.</p>
     *
     * <p>Assuming the base is a {@code DynaBean}, this method will always
     * return the Java class representing the data type of the underlying
     * property value.</p>
     *
     * @param context The context of this evaluation.
     * @param base The base to analyze. Only bases of type
     *     {@code DynaBean} are handled by this resolver.
     * @param property The key to return the acceptable type for.
     *
     * @return If the {@code propertyResolved} property of {@code ELContext}
     *     was set to {@code true}, then the most general acceptable type;
     *     otherwise undefined.
     *
     * @throws NullPointerException if context is {@code null}
     * @throws PropertyNotFoundException if the given property name is
     *     not found.
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

        if (base instanceof DynaBean) {
            log.trace("Returning property-type '{}' for DynaBean '{}'",
                property, base);

            final DynaBean dynaBean = (DynaBean) base;
            final String key = property.toString();
            final DynaProperty dynaProperty = getDynaProperty(dynaBean, key);
            if (dynaProperty == null) {
                throw new PropertyNotFoundException(key);
            }

            context.setPropertyResolved(true);
            return dynaProperty.getType();
        }

        return null;
    }

    /**
     * If the base object is a {@code DynaBean}, returns the value associated
     * with the given key, as specified by the {@code property} argument. If
     * the key was not found, {@code PropertyNotFoundException} is thrown.
     *
     * <p>If the base is a {@code DynaBean}, the {@code propertyResolved}
     * property of the {@code ELContext} object must be set to {@code true}
     * by this resolver, before returning. If this property is not
     * {@code true} after this method is called, the caller should ignore
     * the return value.</p>
     *
     * <p>Just as in {@link DynaBean#get(String)}, just because {@code null}
     * is returned doesn't mean there is no mapping for the key; it's also
     * possible that the method explicitly returns {@code null}.</p>
     *
     * @param context The context of this evaluation.
     * @param base The base to be analyzed. Only bases of type
     *     {@code DynaBean} are handled by this resolver.
     * @param property The key whose associated value is to be returned.
     *
     * @return If the {@code propertyResolved} property of {@code ELContext}
     *     was set to {@code true}, then the value associated with the given key.
     *
     * @throws NullPointerException if context is {@code null}.
     * @throws PropertyNotFoundException if the given property does not
     *     exists.
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

        if (base instanceof DynaBean) {
            log.trace("Returning dynamic property '{}' for DynaBean '{}'",
                property, base);

            final DynaBean dynaBean = (DynaBean) base;
            final String key = property.toString();
            final DynaProperty dynaProperty = getDynaProperty(dynaBean, key);
            if (dynaProperty == null) {
                throw new PropertyNotFoundException(key);
            }

            context.setPropertyResolved(true);
            return dynaBean.get(key);
        }

        return null;
    }


    /**
     * If the base is a {@code DynaBean}, attempts to set the value
     * associated with the given key, as specified by the
     * {@code property} argument.
     *
     * <p>If the base is a {@code DynaBean}, the {@code propertyResolved}
     * property of the {@code ELContext} object must be set to {@code true}
     * by this resolver, before returning. If this property is not
     * {@code true} after this method is called, the caller can safely
     * assume no value was set.</p>
     *
     * <p>If this resolver was constructed in read-only mode, this method will
     * always throw {@code PropertyNotWritableException}.</p>
     *
     * @param context The context of this evaluation.
     * @param base The base to be modified. Only bases of type
     *     {@code DynaBean} are handled by this resolver.
     * @param property The key with which the specified value is to be
     *     associated.
     * @param value The value to be associated with the specified key.

     * @throws NullPointerException if context is {@code null} or if
     *     an attempt is made to set a primitive property to {@code null}.
     * @throws ConversionException if the specified value cannot be
     *      converted to the type required for this property.
     * @throws PropertyNotFoundException if the given property does not
     *     exists.
     * @throws PropertyNotWritableException if this resolver was constructed
     *     in read-only mode.
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

        if (base instanceof DynaBean) {
            log.trace("Setting dynamic property '{}' for DynaBean '{}'",
                property, base);

            final DynaBean dynaBean = (DynaBean) base;
            final String key = property.toString();
            final DynaProperty dynaProperty = getDynaProperty(dynaBean, key);
            if (dynaProperty == null) {
                throw new PropertyNotFoundException(key);
            }

            if (readOnly) {
                throw new PropertyNotWritableException();
            }

            context.setPropertyResolved(true);
            dynaBean.set(key, value);
        }
    }

    /**
     * If the base object is a {@code DynaBean}, returns whether a call to
     * {@link #setValue} will always fail.
     *
     * <p>If the base is a {@code DynaBean}, the {@code propertyResolved}
     * property of the {@code ELContext} object must be set to {@code true}
     * by this resolver, before returning. If this property is not
     * {@code true} after this method is called, the caller should ignore
     * the return value.</p>
     *
     * <p>If this resolver was constructed in read-only mode, this method will
     * always return {@code true}.</p>
     *
     * @param context The context of this evaluation.
     * @param base The base to analyze. Only bases of type {@code DynaBean}
     *     are handled by this resolver.
     * @param property The key to return the read-only status for.
     *
     * @return If the {@code propertyResolved} property of {@code ELContext}
     *     was set to {@code true}, then {@code true} if calling the
     *     {@code setValue} method will always fail or {@code false} if it
     *     is possible that such a call may succeed; otherwise undefined.
     *
     * @throws NullPointerException if context is {@code null}.
     * @throws PropertyNotFoundException if the given property does not
     *     exists.
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

        if (base instanceof DynaBean) {
            log.trace("Return ready-only status for dynamic property '{}' for DynaBean '{}'",
                property, base);

            final DynaBean dynaBean = (DynaBean) base;
            final String key = property.toString();
            final DynaProperty dynaProperty = getDynaProperty(dynaBean, key);
            if (dynaProperty == null) {
                throw new PropertyNotFoundException(key);
            }

            context.setPropertyResolved(true);
            return readOnly;
        }

        return false;
    }

    /**
     * Returns information about the set of variables or properties that
     * can be resolved for the given {@code base} object. One use for
     * this method is to assist tools in auto-completion.
     *
     * <p>If the {@code base} parameter is {@code null}, the resolver
     * must enumerate the list of top-level variables it can resolve.</p>
     *
     * <p>The {@code Iterator} returned must contain zero or more
     * instances of {@link FeatureDescriptor}, in no guaranteed
     * order. Each info object contains information about a property
     * in the {@code DynaBean}, as obtained by calling the
     * {@link org.apache.commons.beanutils.DynaClass#getDynaProperties()}
     * method. The {@code FeatureDescriptor} is initialized using the same
     * fields as are present in the {@code DynaProperty}, with the
     * additional required named attributes "{@code type}" and
     * "{@code resolvableAtDesignTime}" set as follows:</p>
     * <ul>
     *     <li>{@link ELResolver#TYPE} - The runtime type of the property, from
     *         {link org.apache.commons.beanutils.DynaProperty#getType()}.</li>
     *     <li>{@link ELResolver#RESOLVABLE_AT_DESIGN_TIME} - {@code true}.</li>
     * </ul>
     *
     * <p>The caller should be aware that the {@code Iterator}
     * returned might iterate through a very large or even infinitely large
     * set of properties. Care should be taken by the caller to not get
     * stuck in an infinite loop.</p>
     *
     * <p>This is a "best-effort" list.  Not all {@code ELResolver}s
     * will return completely accurate results, but all must be callable
     * at both design-time and runtime (i.e. whether or not
     * {@link java.beans.Beans#isDesignTime()} returns {@code true}),
     * without causing errors.</p>
     *
     * <p>The {@code propertyResolved} property of the
     * {@code ELContext} is not relevant to this method.
     * The results of all {@code ELResolver}s are concatenated
     * in the case of composite resolvers.</p>
     *
     * @param context The context of this evaluation.
     * @param base The base object whose set of valid properties is to
     *     be enumerated, or {@code null} to enumerate the set of
     *     top-level variables that this resolver can evaluate.
     * @return An {@code Iterator} containing zero or more (possibly
     *     infinitely more) {@code FeatureDescriptor} objects, or
     *     {@code null} if this resolver does not handle the given
     *     {@code base} object or that the results are too complex to
     *     represent with this method
     * @see java.beans.FeatureDescriptor
     */
    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(
            ELContext context,
            Object base) {

        if (base instanceof DynaBean) {
            log.trace("Get Feature-Descriptors for DynaBean '{}'", base);

            final DynaBean dynaBean = (DynaBean) base;
            final DynaProperty[] properties = dynaBean.getDynaClass().getDynaProperties();

            final int iMax = properties.length;
            final FeatureDescriptor[] descriptors = new FeatureDescriptor[iMax];
            for (int i = 0; i < iMax; i++) {
                final DynaProperty property = properties[i];

                final FeatureDescriptor descriptor = new FeatureDescriptor();
                descriptor.setName(property.getName());
                descriptor.setDisplayName(property.getName());
                descriptor.setExpert(false);
                descriptor.setHidden(false);
                descriptor.setPreferred(true);
                descriptor.setShortDescription(null);
                descriptor.setValue(TYPE, property.getType());
                descriptor.setValue(RESOLVABLE_AT_DESIGN_TIME, Boolean.TRUE);

                descriptors[i] = descriptor;
            }

            return Arrays.asList(descriptors).iterator();
        }

        return null;
    }

    /**
     * If the base object is a {@code DynaBean}, returns the most
     * general type that this resolver accepts for the {@code property}
     * argument. Otherwise, returns {@code null}.
     *
     * <p>Assuming the base is a {@code DynaBean}, this method will
     * always return {@code Object.class}. This is because any object is
     * accepted as a key and is coerced into a string.</p>
     *
     * @param context The context of this evaluation.
     * @param base The base to analyze. Only bases of type
     *     {@code DynaBean} are handled by this resolver.
     *
     * @return {@code null} if base is not a {@code DynaBean}; otherwise
     *     {@code Object.class}.
     */
    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        if (base instanceof DynaBean) {
            log.trace("Get Common-Property-Type for DynaBean '{}'", base);

            return Object.class;
        }
        return null;
    }

    /**
     * Return the {@code DynaProperty} describing the specified property
     * of the specified {@code DynaBean}, or {@code null} if there is no
     * such property defined on the underlying {@code DynaClass}.
     *
     * @param bean {@code DynaBean} to be checked
     * @param property The property to be checked
     */
    private DynaProperty getDynaProperty(DynaBean bean, String property)
        throws PropertyNotFoundException {

        DynaProperty dynaProperty = null;
        try {
            dynaProperty = bean.getDynaClass().getDynaProperty(property);
        } catch (IllegalArgumentException e) {
            log.trace("Get Dyna-Property '{}'", property, e);
        }

        return (dynaProperty);
    }
}