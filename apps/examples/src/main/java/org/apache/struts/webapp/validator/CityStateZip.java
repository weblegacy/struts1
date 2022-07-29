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


package org.apache.struts.webapp.validator;


/**
 * Just used to provide an indexed properties example.
 *
*/
public class CityStateZip implements java.io.Serializable {
    private static final long serialVersionUID = -4168043409871895808L;

    private String sCity = null;
    private String sStateProv = null;
    private String[] sZipPostal = new String[3];


    public String getCity() {
       return sCity;
    }

    public void setCity(String sCity) {
        this.sCity = sCity;
    }

    public String getStateProv() {
       return sStateProv;
    }

    public void setStateProv(String sStateProv) {
        this.sStateProv = sStateProv;
    }

    public String getZipPostal(int index) {
       return sZipPostal[index];
    }

    public void setZipPostal(int index, String value) {
        this.sZipPostal[index] = value;
    }

}
