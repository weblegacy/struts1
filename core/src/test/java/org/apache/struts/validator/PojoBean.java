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
package org.apache.struts.validator;

import java.util.HashMap;
import java.util.Map;

/**
 * Test Bean class.
 */
public class PojoBean {
    protected String stringValue1;
    protected String stringValue2;
    protected int intValue1;
    protected int intValue2;
    protected Integer integerValue1;
    protected Integer integerValue2;
    protected float floatValue1;
    protected float floatValue2;
    protected double doubleValue1;
    protected double doubleValue2;
    protected PojoBean[] beans;
    protected Map<String, Object> map = new HashMap<>();
    protected String[] stringArray;

    /**
     * Default Constructor
     */
    public PojoBean() {
    }

    /**
     * Construct Bean with a pair of String values.
     */
    public PojoBean(String stringValue1, String stringValue2) {
        setStringValue1(stringValue1);
        setStringValue2(stringValue2);
    }

    /**
     * Construct Bean with a pair of integer values.
     */
    public PojoBean(int intValue1, int intValue2) {
        setIntValue1(intValue1);
        setIntValue2(intValue2);
        setIntegerValue1(intValue1);
        setIntegerValue2(intValue2);
    }

    /**
     * Set the stringValue1.
     */
    public void setStringValue1(String stringValue1) {
        this.stringValue1 = stringValue1;
    }

    /**
     * Return stringValue1.
     */
    public String getStringValue1() {
        return stringValue1;
    }

    /**
     * Set the stringValue2.
     */
    public void setStringValue2(String stringValue2) {
        this.stringValue2 = stringValue2;
    }

    /**
     * Return stringValue2.
     */
    public String getStringValue2() {
        return stringValue2;
    }

    /**
     * Set the stringArray.
     */
    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    /**
     * Return stringArray.
     */
    public String[] getStringArray() {
        return stringArray;
    }

    /**
     * Return Indexed value for stringArray.
     */
    public String getStringArray(int index) {
        return stringArray[index];
    }

    /**
     * Set the intValue1.
     */
    public void setIntValue1(int intValue1) {
        this.intValue1 = intValue1;
    }

    /**
     * Return intValue1.
     */
    public int getIntValue1() {
        return intValue1;
    }

    /**
     * Set the intValue2.
     */
    public void setIntValue2(int intValue2) {
        this.intValue2 = intValue2;
    }

    /**
     * Return intValue2.
     */
    public int getIntValue2() {
        return intValue2;
    }

    /**
     * Set the integerValue1.
     */
    public void setIntegerValue1(Integer integerValue1) {
        this.integerValue1 = integerValue1;
    }

    /**
     * Return integerValue1.
     */
    public Integer getIntegerValue1() {
        return integerValue1;
    }

    /**
     * Set the integerValue2.
     */
    public void setIntegerValue2(Integer integerValue2) {
        this.integerValue2 = integerValue2;
    }

    /**
     * Return integerValue2.
     */
    public Integer getIntegerValue2() {
        return integerValue2;
    }

    /**
     * Set the PojoBean[].
     */
    public void setBeans(PojoBean[] beans) {
        this.beans = beans;
    }

    /**
     * Return PojoBean[].
     */
    public PojoBean[] getBeans() {
        return beans;
    }

    /**
     * Return and indexed Bean
     */
    public PojoBean getBean(int index) {
        return beans[index];
    }

    /**
     * Return the Map
     */
    public Object getMap() {
        return map;
    }

    /**
     * Return the Map
     */
    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * Set a  Mapped property
     */
    public void setMapped(String key, Object value) {
        map.put(key, value);
    }

    /**
     * Set a  Mapped property
     */
    public Object getMapped(String key) {
        return map.get(key);
    }

    public float getFloatValue1() {
        return floatValue1;
    }

    public float getFloatValue2() {
        return floatValue2;
    }

    public void setFloatValue1(float floatValue1) {
        this.floatValue1 = floatValue1;
    }

    public void setFloatValue2(float floatValue2) {
        this.floatValue2 = floatValue2;
    }

    public double getDoubleValue1() {
        return doubleValue1;
    }

    public double getDoubleValue2() {
        return doubleValue2;
    }

    public void setDoubleValue1(double doubleValue1) {
        this.doubleValue1 = doubleValue1;
    }

    public void setDoubleValue2(double doubleValue2) {
        this.doubleValue2 = doubleValue2;
    }
}
