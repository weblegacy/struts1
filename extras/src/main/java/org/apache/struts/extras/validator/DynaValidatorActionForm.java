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
package org.apache.struts.extras.validator;

import java.io.Serializable;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * <p>This class extends <strong>DynaValidatorForm</strong> and provides basic
 * field validation based on an XML file.  The key passed into the validator
 * is the action element's 'path' attribute from the struts-config.xml which
 * should match the form element's name attribute in the validation.xml.</p>
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
 * @since Struts 1.1
 */
public class DynaValidatorActionForm extends DynaValidatorForm
    implements DynaBean, Serializable {
    private static final long serialVersionUID = 3980068452582336786L;

    /**
     * Returns the Validation key.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     * @return validation key - the action element's 'path' attribute in this
     *         case
     */
    public String getValidationKey(ActionMapping mapping,
        HttpServletRequest request) {
        return mapping.getPath();
    }
}
