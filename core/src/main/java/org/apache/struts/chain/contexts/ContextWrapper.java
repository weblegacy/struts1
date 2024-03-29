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
package org.apache.struts.chain.contexts;

import org.apache.commons.chain.Context;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Provide a base class for any Context Implementation which is primarily
 * intended for use in a subchain.
 *
 * <p>Classes which extend {@code ContextWrapper} may implement typesafe
 * property methods which also leave their values in the underlying
 * context.</p>
 */
public class ContextWrapper implements Context {
    private Context base;

    /**
     * <p> Instantiate object as a composite around the given Context. </p>
     *
     * @param context Context instance to wrap
     */
    public ContextWrapper(Context context) {
        this.base = context;
    }

    /**
     * Provide the underlying Context for this composite.
     *
     * @return The underlying Context
     */
    protected Context getBaseContext() {
        return this.base;
    }

    // -------------------------------
    // Map interface methods
    // -------------------------------
    public Set<Map.Entry<String, Object>> entrySet() {
        return this.base.entrySet();
    }

    public Set<String> keySet() {
        return this.base.keySet();
    }

    public Collection<Object> values() {
        return this.base.values();
    }

    public void clear() {
        this.base.clear();
    }

    public void putAll(Map<? extends String, ? extends Object> map) {
        // ISSUE: Should we check this call to putAll?
        this.base.putAll(map);
    }

    public Object remove(Object key) {
        return this.base.remove(key);
    }

    public Object put(String key, Object value) {
        // ISSUE: Should we check this call to put?
        return this.base.put(key, value);
    }

    public Object get(Object key) {
        return this.base.get(key);
    }

    public boolean containsValue(Object o) {
        return this.base.containsValue(o);
    }

    public boolean containsKey(Object o) {
        return this.base.containsKey(o);
    }

    public boolean isEmpty() {
        return this.base.isEmpty();
    }

    public int size() {
        return this.base.size();
    }
}