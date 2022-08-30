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

package org.apache.struts.faces.util;


import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.struts.util.MessageResources;


/**
 * A limited immutable {@code Map} implementation that wraps the
 * {@code MessageResources} instance for the specified {@code Locale}.
 * Exposing the messages as a {@code Map} makes them easily accessible
 * via value binding expressions, as well as JSP 2.0 expression
 * language expressions.
 */
public class MessagesMap implements Map<String, String> {


    // ------------------------------------------------------ Instance Variables


    /**
     * The {@code Locale} for which to return messages, or
     * {@code null} for the system default {@code Locale}.
     */
    private final Locale locale;


    /**
     * The {@code MessageResources} being wrapped by this
     * {@link MessagesMap}.
     */
    private final MessageResources messages;


    // ------------------------------------------------------------ Constructors


    /**
     * Construct a new {@link MessagesMap} instance that wraps the specified
     * {@code MessageResources} instance, and returns messages for the
     * specified {@code Locale}.
     *
     * @param messages {@code MessageResources} instance to wrap
     * @param locale {@code Locale} for which to retrieve messages, or
     *     {@code null} for the system default {@code Locale}
     *
     * @throws NullPointerException if {@code messages} is {@code null}
     */
    public MessagesMap(MessageResources messages, Locale locale) {
        super();
        if (messages == null) {
            throw new NullPointerException();
        }
        this.messages = messages;
        this.locale = locale;
    }


    // ---------------------------------------------------------- Public Methods


    /**
     * The {@code clear()} method is not supported.
     */
    public void clear() {
        throw new UnsupportedOperationException();
    }


    /**
     * Return {@code true} if there is a message for the specified key.
     *
     * @param key Message key to evaluate
     */
    public boolean containsKey(Object key) {
        return key != null && messages.isPresent(locale, key.toString());
    }


    /**
     * The {@code containsValue()} method is not supported.
     *
     * @param value Value to evaluate
     */
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }


    /**
     * The {@code entrySet()} method is not supported.
     */
    public Set<Map.Entry<String, String>> entrySet() {
        throw new UnsupportedOperationException();
    }


    /**
     * The {@code equals} method checks whether equal
     * {@code MessageResources} and {@code Locale} are
     * being wrapped.
     *
     * @param o The object to be compared
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (getClass() != o.getClass()) {
            return (false);
        }

        final MessagesMap other = (MessagesMap) o;
        if (!messages.equals(other.getMessages())) {
            return false;
        }

        return Objects.equals(locale, other.locale);
    }


    /**
     * Return the message string for the specified key.
     *
     * @param key Key for message to return
     */
    public String get(Object key) {
        if (key == null) {
            return "??????";
        } else {
            return messages.getMessage(locale, key.toString());
        }
    }


    /**
     * The {@code hashCode()} method returns values that will be
     * identical if the {@code equals} method returns {@code true}.
     */
    @Override
    public int hashCode() {
        int value = messages.hashCode();
        if (locale != null) {
            value = value ^ locale.hashCode();
        }
        return value;
    }


    /**
     * The {@code isEmpty()} method returns {@code false}, on the
     * assumption that there is always at least one message available.
     */
    public boolean isEmpty() {
        return false;
    }


    /**
     * The {@code keySet()} method is not supported.
     */
    public Set<String> keySet() {
        throw new UnsupportedOperationException();
    }


    /**
     * The {@code put()} method is not supported.
     *
     * @param key Key to store
     * @param value Value to store
     */
    public String put(String key, String value) {
        throw new UnsupportedOperationException();
    }


    /**
     * The {@code putAll()} method is not supported.
     *
     * @param map Keys and values to store
     */
    public void putAll(Map<? extends String, ? extends String> map) {
        throw new UnsupportedOperationException();
    }


    /**
     * The {@code remove()} method is not supported.
     *
     * @param key Key to remove
     */
    public String remove(Object key) {
        throw new UnsupportedOperationException();
    }


    /**
     * The {@code size()} method is not supported.
     */
    public int size() {
        throw new UnsupportedOperationException();
    }


    /**
     * The {@code values()} method is not supported.
     */
    public Collection<String> values() {
        throw new UnsupportedOperationException();
    }


    // --------------------------------------------------------- Package Methods


    /**
     * Return the {@code Locale} we object we are wrapping.
     */
    Locale getLocale() {
        return this.locale;
    }


    /**
     * Return the {@code MessageResources} object we are wrapping.
     */
    MessageResources getMessages() {
        return this.messages;
    }


}