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


package org.apache.struts.webapp.example2;


import org.apache.struts.action.ActionMapping;


/**
 * Implementation of <strong>ActionMapping</strong> for the Struts
 * example application.  It defines the following custom properties:
 * <ul>
 * <li><b>failure</b> - The context-relative URI to which this request
 *     should be forwarded if a validation error occurs on the input
 *     information (typically goes back to the input form).
 * <li><b>success</b> - The context-relative URI to which this request
 *     should be forwarded if the requested action is successfully
 *     completed.
 * </ul>
 *
 * @author Craig R. McClanahan
 * @version $Rev$ $Date$
 */

public final class ApplicationMapping extends ActionMapping {
    private static final long serialVersionUID = 8451778618992418542L;


    // --------------------------------------------------- Instance Variables


    /**
     * The failure URI for this mapping.
     */
    private String failure = null;


    /**
     * The success URI for this mapping.
     */
    private String success = null;


    // ----------------------------------------------------------- Properties


    /**
     * Return the failure URI for this mapping.
     */
    public String getFailure() {

    return (this.failure);

    }


    /**
     * Set the failure URI for this mapping.
     *
     * @param failure The failure URI for this mapping
     */
    public void setFailure(String failure) {

    this.failure = failure;

    }


    /**
     * Return the success URI for this mapping.
     */
    public String getSuccess() {

    return (this.success);

    }


    /**
     * Set the success URI for this mapping.
     *
     * @param success The success URI for this mapping
     */
    public void setSuccess(String success) {

    this.success = success;

    }


}
