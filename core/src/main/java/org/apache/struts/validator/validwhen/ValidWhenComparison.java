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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum for comparing two values.
 *
 * @author Graff Stefan
 *
 * @since Struts 1.4.1
 */
public enum ValidWhenComparison {
    /** Compare V1 &lt;= V2 */
    LESS_EQUAL(ValidWhenParser.LESSEQUALSIGN) {
        @Override
        protected boolean compareTo(int compareTo) {
            return compareTo <= 0;
        }
    },
    /** Compare V1 &lt; V2 */
    LESS_THAN(ValidWhenParser.LESSTHANSIGN) {
        @Override
        protected boolean compareTo(int compareTo) {
            return compareTo < 0;
        }
    },
    /** Compare V1 == V2 */
    EQUAL(ValidWhenParser.EQUALSIGN) {
        @Override
        protected boolean compareWithNull(Object v1, Object v2) {
            return v1 == v2;
        }

        @Override
        protected boolean compareTo(int compareTo) {
            return compareTo == 0;
        }
    },
    /** Compare V1 &gt; V2 */
    GREATER_THAN(ValidWhenParser.GREATERTHANSIGN) {
        @Override
        protected boolean compareTo(int compareTo) {
            return compareTo > 0;
        }
    },
    /** Compare V1 &gt;= V2 */
    GREATER_EQUAL(ValidWhenParser.GREATEREQUALSIGN) {
        @Override
        protected boolean compareTo(int compareTo) {
            return compareTo >= 0;
        }
    },
    /** Compare V1 != V2 */
    NOT_EQUAL(ValidWhenParser.NOTEQUALSIGN) {
        @Override
        protected boolean compareWithNull(Object v1, Object v2) {
            return v1 != v2;
        }

        @Override
        protected boolean compareTo(int compareTo) {
            return compareTo != 0;
        }
    };

    /**
     * {@code Map} to get the correct Comparison for Token-Number.
     */
    private final static Map<Integer, ValidWhenComparison> COMP_MAPPER = new HashMap<>();

    static {
        for (ValidWhenComparison comp : ValidWhenComparison.values()) {
            COMP_MAPPER.put(comp.tokenNum, comp);
        }
    }

    /**
     * Get the {@code ValidWhenComparison} for given
     * token-number.
     *
     * @param tokenNum the token-number
     *
     * @return the {@code ValidWhenComparison} or
     *     {@code null} when not found
     */
    public static ValidWhenComparison getComp(int tokenNum) {
        return COMP_MAPPER.get(tokenNum);
    }

    /**
     * The token-number for this {@code ValidWhenComparison}
     */
    private final int tokenNum;

    /**
     * Constructor for this class.
     *
     * @param tokenNum the token-number
     */
    private ValidWhenComparison(int tokenNum) {
        this.tokenNum = tokenNum;
    }

    /**
     * Method to compare two {@code values} where at least
     * one of them is {@code null}.
     *
     * @param v1 first value
     * @param v2 second value
     *
     * @return {@code true} or {@code false} depending of
     *     the {@code ValidWhenComparison}
     */
    protected boolean compareWithNull(Object v1, Object v2) {
        return false;
    }

    /**
     * Method to compare two {@code values}, neither of
     * which is {@code null}.
     *
     * @param v1 first value
     * @param v2 second value
     *
     * @return {@code true} or {@code false} depending of
     *     the {@code ValidWhenComparison}
     */
    protected <T extends Comparable<T>> boolean compareTo(T v1, T v2) {
        return compareTo(v1.compareTo(v2));
    }

    /**
     * Method for the correct interpretation of the
     * return-value of {@code compareTo}, depending on
     * {@code ValidWhenComparison}.
     *
     * @param compareTo the return-value of {@code compareTo}
     *
     * @return {@code true} or {@code false} depending of
     *     the {@code ValidWhenComparison}
     */
    protected abstract boolean compareTo(int compareTo);

    /**
     * Compares two {@code value}s with consideration
     * of {@code ValidWhenComparison}.
     *
     * <p>Following the comparing-steps:</p>
     * <ol>
     *   <li>Tests whether at least one value is {@code null}.
     *   If so, then it is only possible to compare for
     *   (non)-equality.</li>
     *   <li>Try to convert both values into numbers and compares them.</li>
     *   <li>Compares both values as {@code String}s.</code>
     * </ol>
     *
     * @param v1 first value
     * @param v2 second value
     *
     * @return {@code true} or {@code false} depending of
     *     the {@code ValidWhenComparison}
     */
    public boolean compare(ValidWhenResult<?> v1, ValidWhenResult<?> v2) {
        Object o1 = v1.getValue();
        Object o2 = v2.getValue();

        if (o1 == null || o2 == null) {
            return compareWithNull(o1, o2);
        }

        try {
            BigDecimal i1 = convertBigDecimal(o1);
            BigDecimal i2 = convertBigDecimal(o2);

            return compareTo(i1, i2);
        } catch (NumberFormatException e) {
            // No problem, test strings
        }

        String s1 = v1.toString();
        String s2 = v2.toString();
        return compareTo(s1, s2);
    }

    /**
     * Tries to convert {@code val} to a {@code BigDecimal}.
     *
     * @param val Value to convert to a {@code BigDecimal}
     *
     * @return the converted {@code BigDecimal}-value
     *
     * @throws NumberFormatException if {@code val} is not a valid
     *     representation of a {@code BigDecimal}.
     */
    private static BigDecimal convertBigDecimal(Object val) {
        if (val instanceof BigDecimal) {
            return (BigDecimal)val;
        } else if (val instanceof Integer) {
            return new BigDecimal((Integer)val);
        } else {
            return new BigDecimal(val.toString());
        }
    }
}