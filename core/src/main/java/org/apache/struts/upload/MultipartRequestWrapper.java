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
package org.apache.struts.upload;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * <p> This class functions as a wrapper around HttpServletRequest to provide
 * working getParameter methods for multipart requests. </p>
 */
public class MultipartRequestWrapper extends HttpServletRequestWrapper {
    /**
     * <p> The parameters for this multipart request </p>
     */
    protected Map<String, String[]> parameters;

    public MultipartRequestWrapper(HttpServletRequest request) {
        super(request);
        this.parameters = new HashMap<>();
    }

    /**
     * <p> Sets a parameter for this request.  The parameter is actually
     * separate from the request parameters, but calling on the getParameter()
     * methods of this class will work as if they weren't. </p>
     */
    public void setParameter(String name, String value) {
        String[] mValue = parameters.get(name);

        if (mValue == null) {
            mValue = new String[0];
        }

        String[] newValue = new String[mValue.length + 1];

        System.arraycopy(mValue, 0, newValue, 0, mValue.length);
        newValue[mValue.length] = value;

        parameters.put(name, newValue);
    }

    /**
     * <p> Attempts to get a parameter for this request.  It first looks in
     * the underlying HttpServletRequest object for the parameter, and if that
     * doesn't exist it looks for the parameters retrieved from the multipart
     * request </p>
     */
    public String getParameter(String name) {
        String value = getRequest().getParameter(name);

        if (value == null) {
            String[] mValue = parameters.get(name);

            if ((mValue != null) && (mValue.length > 0)) {
                value = mValue[0];
            }
        }

        return value;
    }

    /**
     * <p> Returns the names of the parameters for this request. The
     * enumeration consists of the normal request parameter names plus the
     * parameters read from the multipart request </p>
     */
    public Enumeration<String> getParameterNames() {
        Enumeration<String> baseParams = getRequest().getParameterNames();
        ArrayList<String> list = new ArrayList<>();

        while (baseParams.hasMoreElements()) {
            list.add(baseParams.nextElement());
        }

        Collection<String> multipartParams = parameters.keySet();
        list.addAll(multipartParams);

        return Collections.enumeration(list);
    }

    /**
     * <p> Returns the values of a parameter in this request. It first looks
     * in the underlying HttpServletRequest object for the parameter, and if
     * that doesn't exist it looks for the parameter retrieved from the
     * multipart request. </p>
     */
    public String[] getParameterValues(String name) {
        String[] value = getRequest().getParameterValues(name);

        if (value == null) {
            value = parameters.get(name);
        }

        return value;
    }

    /**
     * <p> Combines the parameters stored here with those in the underlying
     * request. If paramater values in the underlying request take precedence
     * over those stored here. </p>
     */
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new HashMap<>(parameters);

        map.putAll(getRequest().getParameterMap());

        return map;
    }
}