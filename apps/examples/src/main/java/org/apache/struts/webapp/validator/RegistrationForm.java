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

import java.io.Serializable;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;


/**
 * Form bean for the user registration page.
 *
*/
public final class RegistrationForm extends ValidatorForm implements Serializable {
    private static final long serialVersionUID = 3679135634630477325L;

    private String action = null;

    private String sFirstName = null;
    private String sLastName = null;
    private String sAddr = null;
    private CityStateZip csz = new CityStateZip();
    private String sPhone = null;
    private String sEmail = null;


    public String getAction() {
  return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFirstName() {
       return sFirstName;
    }

    public void setFirstName(String sFirstName) {
        this.sFirstName = sFirstName;
    }

    public String getLastName() {
       return sLastName;
    }

    public void setLastName(String sLastName) {
        this.sLastName = sLastName;
    }

    public String getAddr() {
       return sAddr;
    }

    public void setAddr(String sAddr) {
        this.sAddr = sAddr;
    }

    public CityStateZip getCityStateZip() {
       return csz;
    }

    public void setCityStateZip(CityStateZip csz) {
        this.csz = csz;
    }

    public String getPhone() {
       return sPhone;
    }

    public void setPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    public String getEmail() {
       return sEmail;
    }

    public void setEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
       action = null;
       sFirstName = null;
       sLastName = null;
       sAddr = null;
       csz = new CityStateZip();
       sPhone = null;
       sEmail = null;
    }

}
