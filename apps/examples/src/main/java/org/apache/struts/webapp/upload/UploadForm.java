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

package org.apache.struts.webapp.upload;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

/**
 * This class is a placeholder for form values.  In a multipart request, files are represented by
 * set and get methods that use the class org.apache.struts.upload.FormFile, an interface with
 * basic methods to retrieve file information.  The actual structure of the FormFile is dependant
 * on the underlying impelementation of multipart request handling.  The default implementation
 * that struts uses is org.apache.struts.upload.CommonsMultipartRequestHandler.
 *
 * @version $Rev$ $Date$
 */
public class UploadForm extends ValidatorForm {
    private static final long serialVersionUID = 9109939239360280904L;

    /**
     * The value of the text the user has sent as form data
     */
    protected String theText;

    /**
     * The value of the embedded query string parameter
     */
    protected String queryParam;

    /**
     * Whether or not to write to a file
     */
    protected boolean writeFile;

    /**
     * The file that the user has uploaded
     */
    protected FormFile theFile;

    /**
     * The file path to write to
     */
    protected String filePath;

    /**
     * Retrieve the value of the text the user has sent as form data
     */
    public String getTheText() {
        return theText;
    }

    /**
     * Set the value of the form data text
     */
    public void setTheText(String theText) {
        this.theText = theText;
    }

    /**
     * Retrieve the value of the query string parameter
     */
    public String getQueryParam() {
        return queryParam;
    }

    /**
     * Set the value of the query string parameter
     */
    public void setQueryParam(String queryParam) {
        this.queryParam = queryParam;
    }

    /**
     * Retrieve a representation of the file the user has uploaded
     */
    public FormFile getTheFile() {
        return theFile;
    }

    /**
     * Set a representation of the file the user has uploaded
     */
    public void setTheFile(FormFile theFile) {
        this.theFile = theFile;
    }

    /**
     * Set whether or not to write to a file
     */
    public void setWriteFile(boolean writeFile) {
        this.writeFile = writeFile;
    }

    /**
     * Get whether or not to write to a file
     */
    public boolean getWriteFile() {
        return writeFile;
    }

    /**
     * Set the path to write a file to
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Get the path to write a file to
     */
    public String getFilePath() {
        return filePath;
    }

    public void reset() {
        writeFile = false;
    }

    /**
     * Check to make sure the client hasn't exceeded the maximum allowed upload size inside of this
     * validate method.
     */
    public ActionErrors validate(
        ActionMapping mapping,
        HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        //has the maximum length been exceeded?
        Boolean maxLengthExceeded =
            (Boolean) request.getAttribute(
                MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);

        if ((maxLengthExceeded != null) && (maxLengthExceeded.booleanValue())) {
            if (errors == null) {
                errors = new ActionErrors();
            }
            errors.add(
                ActionMessages.GLOBAL_MESSAGE ,
                new ActionMessage("maxLengthExceeded"));
            errors.add(
                ActionMessages.GLOBAL_MESSAGE ,
                new ActionMessage("maxLengthExplanation"));
        }
        return errors;

    }
}
