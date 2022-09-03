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
 * The result has is a {@code String}.
 *
 * @author Graff Stefan
 *
 * @since Struts 1.4.1
 */
public class ValidWhenResultString extends ValidWhenResult<String> {

    /**
     * Constructs this class with the type {@code String}.
     *
     * @param value the current value of
     *     the result
     */
    public ValidWhenResultString(String value) {
        super(String.class, initValue(value));
    }

    /**
     * Trims the {@code value} and returns {@code null}
     * if the {@code value} is empty.
     *
     * @param value the value
     *
     * @return the trimmed {@code String} or {@code null}
     */
    private static String initValue(String value) {
        if (value == null) {
            return null;
        }

        String s = value.trim();
        return s.isEmpty() ? null : s;
    }
}