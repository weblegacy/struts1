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

package examples.validator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

/**
 * A custom validator example
 *
 * @version $Rev$ $Date$
 */
public class CustomValidator {

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for CustomValidator.
     */
    public CustomValidator() {
        super();
    }

    // ---------------------------------------------------------- Public Methods

    /**
     * Example validator for comparing the equality of two fields
     *
     * http://struts.apache.org/userGuide/dev_validator.html
     * http://www.raibledesigns.com/page/rd/20030226
     */
    public static boolean validateTwoFields(
        Object bean,
        ValidatorAction va,
        Field field,
        ActionMessages errors,
        HttpServletRequest request) {

        String value =
            ValidatorUtils.getValueAsString(bean, field.getProperty());
        String property2 = field.getVarValue("secondProperty");
        String value2 = ValidatorUtils.getValueAsString(bean, property2);

        if (!GenericValidator.isBlankOrNull(value)) {
            try {
                if (!value.equals(value2)) {
                    errors.add(
                        field.getKey(),
                        Resources.getActionMessage(request, va, field));

                    return false;
                }
            } catch (Exception e) {
                errors.add(
                    field.getKey(),
                    Resources.getActionMessage(request, va, field));
                return false;
            }
        }
        return true;
    }

}
