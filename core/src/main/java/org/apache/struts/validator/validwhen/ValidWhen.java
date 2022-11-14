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
package org.apache.struts.validator.validwhen;

import jakarta.servlet.http.HttpServletRequest;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.Resources;
import org.apache.struts.validator.validwhen.ValidWhenParser.ExpressionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains the validwhen validation that is used in the
 * validator-rules.xml file.
 *
 * @since Struts 1.2
 */
public class ValidWhen {

    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(ValidWhen.class);

    /**
     * The message resources for this package.
     */
    private static MessageResources sysmsgs =
        MessageResources.getMessageResources(
            "org.apache.struts.validator.LocalStrings");

    /**
     * Returns true if <code>obj</code> is null or a String.
     */
    private static boolean isString(Object obj) {
        return (obj == null) ? true : String.class.isInstance(obj);
    }

    /**
     * Checks if the field matches the boolean expression specified in
     * <code>test</code> parameter.
     *
     * @param bean    The bean validation is being performed on.
     * @param va      The <code>ValidatorAction</code> that is currently being
     *                performed.
     * @param field   The <code>Field</code> object associated with the
     *                current field being validated.
     * @param errors  The <code>ActionMessages</code> object to add errors to
     *                if any validation errors occur.
     * @param request Current request object.
     * @return <code>true</code> if meets stated requirements,
     *         <code>false</code> otherwise.
     */
    public static boolean validateValidWhen(Object bean, ValidatorAction va,
        Field field, ActionMessages errors, Validator validator,
        HttpServletRequest request) {
        Object form = validator.getParameterValue(Validator.BEAN_PARAM);
        String value = null;
        boolean valid = false;
        int index = -1;

        if (field.isIndexed()) {
            String key = field.getKey();

            final int leftBracket = key.indexOf("[");
            final int rightBracket = key.indexOf("]");

            if ((leftBracket > -1) && (rightBracket > -1)) {
                index =
                    Integer.parseInt(key.substring(leftBracket + 1,
                        rightBracket));
            }
        }

        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }

        String test = null;

        try {
            test =
                Resources.getVarValue("test", field, validator, request, true);
        } catch (IllegalArgumentException ex) {
            LOG.atError().log(() -> sysmsgs.getMessage("validation.failed", "validwhen",
                field.getProperty(), validator.getFormName(), ex.toString()));

            String userErrorMsg = sysmsgs.getMessage("system.error");

            errors.add(field.getKey(), new ActionMessage(userErrorMsg, false));

            return false;
        }

        // Create the Lexer
        ValidWhenLexer lexer = null;

        try {
            lexer = new ValidWhenLexer(CharStreams.fromString(test));
        } catch (Exception ex) {
            LOG.error("ValidWhenLexer Error for field '{}'",
                field.getKey(), ex);

            String userErrorMsg = sysmsgs.getMessage("system.error");

            errors.add(field.getKey(), new ActionMessage(userErrorMsg, false));

            return false;
        }

        // Create the Parser
        ValidWhenParser parser = null;

        try {
            parser = new ValidWhenParser(new CommonTokenStream(lexer));
        } catch (Exception ex) {
            LOG.error("ValidWhenParser Error for field '{}'",
                field.getKey(), ex);

            String userErrorMsg = sysmsgs.getMessage("system.error");

            errors.add(field.getKey(), new ActionMessage(userErrorMsg, false));

            return false;
        }

        ValidWhenEvaluator validWhenEvaluator = new ValidWhenEvaluator(form, value, index);

        try {
            ExpressionContext expressionContext = parser.expression();
            ValidWhenResult<?> result = validWhenEvaluator.visitExpression(expressionContext);
            valid = result == null ? false : result.toBoolean();
        } catch (Exception ex) {
            LOG.error("ValidWhen Error for field '{}'",
                field.getKey(), ex);

            String userErrorMsg = sysmsgs.getMessage("system.error");

            errors.add(field.getKey(), new ActionMessage(userErrorMsg, false));

            return false;
        }

        if (!valid) {
            errors.add(field.getKey(),
                Resources.getActionMessage(validator, request, va, field));

            return false;
        }

        return true;
    }
}