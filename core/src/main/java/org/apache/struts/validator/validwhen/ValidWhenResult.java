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
package org.apache.struts.validator.validwhen;

/**
 * Holds the (interim) result during processing the parsing-tree.
 *
 * @author Graff Stefan
 *
 * @param <T> the current type of the result
 *
 * @since Struts 1.4.1
 */
public class ValidWhenResult<T> {
    /** The current type of the result. */
    protected final Class<T> type;

    /** The value of the result. */
    protected final T value;

    /**
     * Constructs this class.
     *
     * @param type the current type of
     *     the result
     * @param value the current value of
     *     the result
     */
    protected ValidWhenResult(Class<T> type, T value) {
//        System.out.println(">>> " + type + " " + value);
        this.type = type;
        this.value = value;
    }

    /**
     * Gets the current type of the result.
     *
     * @return the current type of the result
     */
    public Class<T> getType() {
        return type;
    }

    /**
     * Gets the current value of the result.
     *
     * @return the current value of the result
     */
    public T getValue() {
        return value;
    };

    /**
     * Gets the {@code Boolean}-value if the
     * current result is a boolean.
     *
     * @return the boolean-value if the value
     *     is a boolean otherwise {@code false}.
     */
    public boolean toBoolean() {
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}