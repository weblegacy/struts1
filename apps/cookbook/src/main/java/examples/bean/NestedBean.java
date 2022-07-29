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

package examples.bean;

import java.io.Serializable;


/**
 * Another Example Bean
 *
 * @version $Rev$ $Date$
 */
public class NestedBean implements Serializable {
    private static final long serialVersionUID = 3815357718640563128L;

    // ------------------------------------------------------ Instance Variables

    /** A string value */
    private String stringValue = "This is a string from NestedBean";

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for NestedBean.
     */
    public NestedBean() {
        super();
    }

    // -------------------------------------------------------------- Properties


    /**
     * Returns the stringValue.
     * @return String
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * Sets the stringValue.
     * @param stringValue The stringValue to set
     */
    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

 }
