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
 * The result has is a {@code BigDecimal}.
 *
 * @author Graff Stefan
 *
 * @since Struts 1.4.1
 */
import java.math.BigDecimal;

/**
 * Holds the (interim) result during processing the parsing-tree.
 *
 * @author Graff Stefan
 */
public class ValidWhenResultBigDecimal extends ValidWhenResult<BigDecimal> {

    /**
     * Constructs this class with the type {@code BigDecimal}.
     *
     * @param value the current value of
     *     the result
     */
    public ValidWhenResultBigDecimal(String value) {
        super(BigDecimal.class, new BigDecimal(value));
    }
}