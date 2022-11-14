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

import java.io.Serializable;
import java.util.Map;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResults;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>This class extends <strong>ActionForm</strong> and provides basic field
 * validation based on an XML file.  The key passed into the validator is the
 * action element's 'name' attribute from the struts-config.xml which should
 * match the form element's name attribute in the validation.xml.</p>
 *
 * <ul>
 *
 * <li>See <code>ValidatorPlugin</code> definition in struts-config.xml for
 * validation rules.</li>
 *
 * </ul>
 *
 * @version $Rev$ $Date: 2005-05-07 12:11:38 -0400 (Sat, 07 May 2005)
 *          $
 * @see org.apache.struts.action.ActionForm
 * @since Struts 1.1
 */
public class ValidatorForm extends ActionForm implements Serializable {
    private static final long serialVersionUID = 7373898264686375233L;

    /**
     * The {@code Log} instance for this class.
     */
    private transient final Logger log =
        LoggerFactory.getLogger(ValidatorForm.class);

    /**
     * The results returned from the validation performed by the
     * <code>Validator</code>.
     */
    protected ValidatorResults validatorResults = null;

    /**
     * Used to indicate the current page of a multi-page form.
     */
    protected int page = 0;

    /**
     * Gets page.
     *
     * @return page number
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets page.
     *
     * @param page page number
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Validate the properties that have been set from this HTTP request, and
     * return an <code>ActionErrors</code> object that encapsulates any
     * validation errors that have been found.  If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no
     * recorded error messages.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     * @return <code>ActionErrors</code> object that encapsulates any
     *         validation errors
     */
    public ActionErrors validate(ActionMapping mapping,
        HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        String validationKey = getValidationKey(mapping, request);

        ServletContext application;
        try {
            application = getServlet().getServletContext();
        } catch (NullPointerException e) {
            IllegalStateException e2 = new IllegalStateException(
                    "Missing ActionServlet instance for bean '" +
                    mapping.getName() +
                    "' (created outside of Struts?)");
            e2.initCause(e);
            throw e2;
        }

        // 2014/07/02 - security problem patch.
        // Author: NTT DATA Corporation
        int validationPage = determinePage(mapping, request);

        Validator validator =
            Resources.initValidator(validationKey, this, application, request,
                errors, validationPage);

        try {
            validatorResults = validator.validate();
        } catch (ValidatorException e) {
            log.error(e.getMessage(), e);
        }

        return errors;
    }

    // 2014/07/02 - security problem patch.
    // Author: NTT DATA Corporation
    /**
     * Determine validation page.<br>
     * If acceptPage of ActionMapping is null, then returns Integer.MAX_VALUE.
     * (multi-page validation is disabled. All validation fields are enabled.)<br>
     * If page property is less than acceptPage of ActionMapping, returns acceptPage value.<br>
     * If page property is greater than or equal to acceptPage of ActionMapping, returns page property value.
     *
     * @param mapping The mapping used to select this instance.
     * @param request The servlet request we are processing.
     *
     * @return validation page.
     *
     * @since Struts 1.4.1
     */
    protected int determinePage(ActionMapping mapping, HttpServletRequest request) {
        Integer acceptPage = mapping.getAcceptPage();
        return acceptPage != null ? Math.max(acceptPage.intValue(), getPage()) : Integer.MAX_VALUE;
    }

    /**
     * Returns the Validation key.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     * @return validation key - the form element's name in this case
     */
    public String getValidationKey(ActionMapping mapping,
        HttpServletRequest request) {
        return mapping.getAttribute();
    }

    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        setPage(0);
        validatorResults = null;
    }

    /**
     * Get results of the validation performed by the <code>Validator</code>.
     *
     * @return results of the validation
     */
    public ValidatorResults getValidatorResults() {
        return validatorResults;
    }

    /**
     * Set results of the validation performed by the <code>Validator</code>.
     *
     * @param validatorResults results of validation
     */
    public void setValidatorResults(ValidatorResults validatorResults) {
        this.validatorResults = validatorResults;
    }

    /**
     * Returns a <code>Map</code> of values returned from any validation that
     * returns a value other than <code>null</code> or <code>Boolean</code>
     * with the key the full property path of the field.
     *
     * @return <code>Map</code> of non-null values
     */
    public Map<String, Object> getResultValueMap() {
        return ((validatorResults != null)
        ? validatorResults.getResultValueMap() : null);
    }
}