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
package org.apache.struts.mock;

import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>General purpose form bean for unit tests.</p>
 *
 * @version $Rev$ $Date: 2005-05-07 12:45:39 -0400 (Sat, 07 May 2005)
 *          $
 */
public class MockFormBean extends ActionForm {
    private static final long serialVersionUID = -8677959176030940512L;

    /*
     * <p>
     * Flag to indicate whether certain methods should complete properly
     * or throw an Exception
     * </p>
     */
    private boolean throwException = false;
    private boolean returnNulls = false;
    private String defaultValue;
    private Double defaultDouble;
    private int arrayCount;
    protected boolean booleanProperty = false;
    protected String stringProperty = null;

    // ------------------- Constructors
    public MockFormBean() {
        this(null);
    }

    public MockFormBean(boolean throwException, boolean returnNulls) {
        super();
        this.throwException = throwException;
        this.returnNulls = returnNulls;
    }

    public MockFormBean(boolean throwException) {
        this.throwException = throwException;
    }

    public MockFormBean(boolean throwException, boolean returnNulls,
        String defaultValue) {
        this(throwException, returnNulls);
        this.defaultValue = defaultValue;
    }

    public MockFormBean(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public MockFormBean(boolean throwException, boolean returnNulls,
        String defaultValue, int arrayCount) {
        this(throwException, returnNulls, defaultValue);
        this.arrayCount = arrayCount;
    }

    public MockFormBean(boolean throwException, boolean returnNulls,
        Double defaultDouble) {
        this(throwException, returnNulls);
        this.defaultDouble = defaultDouble;
    }

    // ------------------- public methods
    public String getJustThrowAnException()
        throws Exception {
        throw new Exception();
    }

    public Object getThrowIllegalAccessException()
            throws Exception {

        throw new IllegalAccessException();
//      return null;
    }

    public String getStringValue()
        throws Exception {
        if (throwException) {
            throw new Exception();
        }

        if (returnNulls) {
            return null;
        }

        return defaultValue;
    }

    public String[] getStringArray()
        throws Exception {
        if (throwException) {
            throw new Exception();
        }

        if (returnNulls) {
            return null;
        }

        String[] rtn = new String[arrayCount];

        for (int i = 0; i < rtn.length; i++) {
            rtn[i] = defaultValue + i;
        }

        return rtn;
    }

    public Double getDoubleValue()
        throws Exception {
        if (throwException) {
            throw new Exception();
        }

        if (returnNulls) {
            return null;
        }

        return defaultDouble;
    }

    public boolean getBooleanProperty() {
        return (this.booleanProperty);
    }

    public void setBooleanProperty(boolean booleanProperty) {
        this.booleanProperty = booleanProperty;
    }

    public Map<String, String> getMapProperty() {
        HashMap<String, String> map = new HashMap<>();

        map.put("foo1", "bar1");
        map.put("foo2", "bar2");

        return (map);
    }

    public Map<String, String[]> getMapPropertyArrayValues() {
        HashMap<String, String[]> map = new HashMap<>();

        map.put("foo1", new String[] { "bar1", "baz1" });
        map.put("foo2", new String[] { "bar2", "baz2" });

        return (map);
    }

    public String getStringProperty() {
        return (this.stringProperty);
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }
}
