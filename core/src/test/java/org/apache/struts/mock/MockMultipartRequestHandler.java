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

import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Mock <strong>MultipartRequestHandler</strong> object for unit tests.
 */
public class MockMultipartRequestHandler implements MultipartRequestHandler {

    /** mock ActionServlet instance. */
    private ActionServlet servlet;

    /** mock ActionMapping instance. */
    private ActionMapping mapping = new ActionMapping();

    /** request elements. */
    private HashMap<String, String[]> elements;

    /**
     * Convenience method to set a reference to a mock ActionServlet instance.
     *
     * @param servlet Mock servlet instance.
     */
    public void setServlet(ActionServlet servlet) {
        this.servlet = servlet;
    }

    /**
     * Convenience method to set a reference to a mock ActionMapping instance.
     *
     * @param mapping Mock action mapping instance.
     */
    public void setMapping(ActionMapping mapping) {
        this.mapping = mapping;
    }

    /**
     * Get the mock ActionServlet instance.
     *
     * @return The mock servlet instance.
     */
    public ActionServlet getServlet() {
        return this.servlet;
    }

    /**
     * Get the ActionMapping instance for this mock request.
     *
     * @return The mock action mapping instance.
     */
    public ActionMapping getMapping() {
        return this.mapping;
    }

    /**
      * Mock parsing of the ServletInputStream.
      *
      * <p>Constructs a {@code HashMap} of elements from the
      * HttpServletRequest's parameters - no {@code FormFile} elements are
      * created.</p>
      *
      * @param request Mock request instance.
      *
      * @throws ServletException If there is a problem with processing the
      *                          request.
      */
    public void handleRequest(HttpServletRequest request) throws ServletException {
        elements = new HashMap<>(request.getParameterMap());
    }

    /**
     * This method is called on to retrieve all the text input elements of the
     * request.
     *
     * @return A HashMap where the keys and values are the names and values of
     *         the request input parameters
     */
    public HashMap<String, String[]> getTextElements() {
        return this.elements;
    }

    /**
     * This method is called on to retrieve all the FormFile input elements of
     * the request.
     *
     * @return This mock implementation returns an empty {@code HashMap}
     */
    public HashMap<String, List<FormFile>> getFileElements() {
        return new HashMap<>();
    }

    /**
     * This method returns all elements of a multipart request.
     *
     * @return This mock implementation returns a HashMap where the keys are
     *         input names and values are either Strings (no FormFile elements)
     */
    public HashMap<String, Object> getAllElements() {
        return new HashMap<>(this.elements);
    }

    /**
     * Mock {@code rollback()} method does nothing.
     */
    public void rollback() {
        // ignore
    }

    /**
     * Mock {@code finish()} method does nothing.
     */
    public void finish() {
        // ignore
    }
}