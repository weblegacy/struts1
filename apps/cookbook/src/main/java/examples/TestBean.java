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

package examples;

import java.io.Serializable;


/**
 * An example bean for Bean Examples
 *
 * @version $Rev$ $Date$
 */
public class TestBean implements Serializable {
    private static final long serialVersionUID = 1119235675677502359L;

    // ------------------------------------------------------ Instance Variables

    /** A boolean value */
    private boolean booleanValue = false;

    /** A double value */
    private double doubleValue = 45213.451;

    /** A float value */
    private float floatValue = -123.582F;

    /** An integer value */
    private int intValue = 256;

    /** A long integer value */
    private long longValue = 1321546L;

    /** A short integer value */
    private short shortValue = 257;

    /** A string value */
    private String stringValue = "Hello, world!";

    /** A dateValue value */
    private java.util.Date dateValue = new java.util.Date();

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for TestBean.
     */
    public TestBean() {
        super();
    }

    // -------------------------------------------------------------- Properties

    /**
     * Returns the booleanValue.
     * @return boolean
     */
    public boolean isBooleanValue() {
        return booleanValue;
    }

    /**
     * Returns the doubleValue.
     * @return double
     */
    public double getDoubleValue() {
        return doubleValue;
    }

    /**
     * Returns the floatValue.
     * @return float
     */
    public float getFloatValue() {
        return floatValue;
    }

    /**
     * Returns the intValue.
     * @return int
     */
    public int getIntValue() {
        return intValue;
    }

    /**
     * Returns the longValue.
     * @return long
     */
    public long getLongValue() {
        return longValue;
    }

    /**
     * Returns the shortValue.
     * @return short
     */
    public short getShortValue() {
        return shortValue;
    }

    /**
     * Returns the stringValue.
     * @return String
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * Sets the booleanValue.
     * @param booleanValue The booleanValue to set
     */
    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    /**
     * Sets the doubleValue.
     * @param doubleValue The doubleValue to set
     */
    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    /**
     * Sets the floatValue.
     * @param floatValue The floatValue to set
     */
    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    /**
     * Sets the intValue.
     * @param intValue The intValue to set
     */
    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    /**
     * Sets the longValue.
     * @param longValue The longValue to set
     */
    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    /**
     * Sets the shortValue.
     * @param shortValue The shortValue to set
     */
    public void setShortValue(short shortValue) {
        this.shortValue = shortValue;
    }

    /**
     * Sets the stringValue.
     * @param stringValue The stringValue to set
     */
    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    /**
     * Returns the dateValue.
     * @return java.util.Date
     */
    public java.util.Date getDateValue() {
        return dateValue;
    }

    /**
     * Sets the dateValue.
     * @param date The date to set
     */
    public void setDateValue(java.util.Date date) {
        this.dateValue = date;
    }

}
