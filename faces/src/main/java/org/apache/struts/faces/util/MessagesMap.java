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
import java.util.Set;

import org.apache.struts.util.MessageResources;


/**
 * <p>A limited immutable <code>Map</code> implementation that wraps the
 * <code>MessageResources</code> instance for the specified
 * <code>Locale</code>.  Exposing the messages as a <code>Map</code>
 * makes them easily accessible via value binding expressions, as
 * well as JSP 2.0 expression language expressions.
 */

public class MessagesMap implements Map {


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Construct a new {@link MessagesMap} instance that wraps the
     * specified <code>MessageResources</code> instance, and returns messages
     * for the specified <code>Locale</code>.</p>
     *
     * @param messages <code>MessageResources</code> instance to wrap
     * @param locale <code>Locale</code> for which to retrieve messages,
     *  or <code>null</code> for the system default <code>Locale</code>
     *
     * @exception NullPointerException if <code>messages</code>
     *  is <code>null</code>
     */
    public MessagesMap(MessageResources messages, Locale locale) {

        super();
        if (messages == null) {
            throw new NullPointerException();
        }
        this.messages = messages;
        this.locale = locale;

    }


    // ------------------------------------------------------ Instance Variables


    /**
     * <p>The <code>Locale</code> for which to return messages, or
     * <code>null</code> for the system default <code>Locale</code>.</p>
     */
    private Locale locale = null;


    /**
     * <p>The <code>MessageResources</code> being wrapped by this
     * {@link MessagesMap}.</p>
     */
    private MessageResources messages = null;


    // ---------------------------------------------------------- Public Methods


    /**
     * <p>The <code>clear()</code> method is not supported.</p>
     */
    public void clear() {

        throw new UnsupportedOperationException();

    }


    /**
     * <p>Return <code>true</code> if there is a message for the
     * specified key.</p>
     *
     * @param key Message key to evaluate
     */
    public boolean containsKey(Object key) {

        if (key == null) {
            return (false);
        } else {
            return (messages.isPresent(locale, key.toString()));
        }

    }


    /**
     * <p>The <code>containsValue()</code> method is not supported.</p>
     *
     * @param value Value to evaluate
     */
    public boolean containsValue(Object value) {

        throw new UnsupportedOperationException();

    }


    /**
     * <p>The <code>entrySet()</code> method is not supported.</p>
     */
    public Set entrySet() {

        throw new UnsupportedOperationException();

    }


    /**
     * <p>The <code>equals</code> method checks whether equal
     * <code>MessageResources</code> and <code>Locale</code> are
     * being wrapped.</p>
     *
     * @param o The object to be compared
     */
    public boolean equals(Object o) {

        if (!(o instanceof MessagesMap)) {
            return (false);
        }
        MessagesMap other = (MessagesMap) o;
        if (!messages.equals(other.getMessages())) {
            return (false);
        }
        if (locale == null) {
            return (other.getLocale() == null);
        } else {
            return (locale.equals(other.getLocale()));
        }

    }


    /**
     * <p>Return the message string for the specified key.</p>
     *
     * @param key Key for message to return
     */
    public Object get(Object key) {

        if (key == null) {
            return ("??????");
        } else {
            return (messages.getMessage(locale, key.toString()));
        }

    }


    /**
     * <p>The <code>hashCode()</code> method returns values that will
     * be identical if the <code>equals</code> method returns <code>true</code>.
     * </p>
     */
    public int hashCode() {

        int value = messages.hashCode();
        if (locale != null) {
            value = value ^ locale.hashCode();
        }
        return (value);

    }


    /**
     * <p>The <code>isEmpty()</code> method returns <code>false</code>, on the
     * assumption that there is always at least one message available.</p>
     */
    public boolean isEmpty() {

        return (false);

    }


    /**
     * <p>The <code>keySet()</code> method is not supported.</p>
     */
    public Set keySet() {

        throw new UnsupportedOperationException();

    }


    /**
     * <p>The <code>put()</code> method is not supported.</p>
     *
     * @param key Key to store
     * @param value Value to store
     */
    public Object put(Object key, Object value) {

        throw new UnsupportedOperationException();

    }


    /**
     * <p>The <code>putAll()</code> method is not supported.</p>
     *
     * @param map Keys and values to store
     */
    public void putAll(Map map) {

        throw new UnsupportedOperationException();

    }


    /**
     * <p>The <code>remove()</code> method is not supported.</p>
     *
     * @param key Key to remove
     */
    public Object remove(Object key) {

        throw new UnsupportedOperationException();

    }


    /**
     * <p>The <code>size()</code> method is not supported.</p>
     */
    public int size() {

        throw new UnsupportedOperationException();

    }


    /**
     * <p>The <code>values()</code> method is not supported.</p>
     */
    public Collection values() {

        throw new UnsupportedOperationException();

    }


    // --------------------------------------------------------- Package Methods


    /**
     * <p>Return the <code>Locale</code> we object we are wrapping.</p>
     */
    Locale getLocale() {

        return (this.locale);

    }


    /**
     * <p>Return the <code>MessageResources</code> object we are wrapping.</p>
     */
    MessageResources getMessages() {

        return (this.messages);

    }



}
