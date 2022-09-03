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
 * The result has is a {@code int}.
 *
 * @author Graff Stefan
 *
 * @since Struts 1.4.1
 */
public class ValidWhenResultInteger extends ValidWhenResult<Integer> {

    /**
     * Constructs this class with the type {@code int}.
     *
     * @param value the current value of
     *     the result
     */
    public ValidWhenResultInteger(String value) {
        super(int.class, Integer.decode(value));
    }
}