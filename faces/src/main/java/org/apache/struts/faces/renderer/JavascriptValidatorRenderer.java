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

package org.apache.struts.faces.renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.Var;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.Globals;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.faces.component.FormComponent;
import org.apache.struts.faces.component.JavascriptValidatorComponent;
import org.apache.struts.faces.util.StrutsContext;
import org.apache.struts.faces.util.Utils;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.Resources;
import org.apache.struts.validator.ValidatorPlugIn;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;

/**
 * {@code Renderer} implementation for the
 * {@code JavascriptValidator} tag from the
 * <em>Struts-Faces Integration Library</em>.</p>
 *
 * @version $Rev$ $Date$
 */
public class JavascriptValidatorRenderer extends AbstractRenderer {

    private final static Function<ValidatorAction, Integer> SIZE_VA = (va) ->
        va == null || va.getDepends() == null ? 0 : va.getDepends().length();

    private final static Comparator<ValidatorAction> COMP_VA = (va1, va2) -> {
        final int size1 = SIZE_VA.apply(va1);
        final int size2 = SIZE_VA.apply(va2);

        if (size1 == 0) {
            return size2 == 0 ? 0 : -1;
        } else if (size2 == 0) {
            return 1;
        }
        return va1.getDependencyList().size()
                - va2.getDependencyList().size();
    };

    private final static String HTML_BEGIN_COMMENT = "\n<!-- Begin \n";

    private final static String HTML_END_COMMENT = "//End --> \n";


    // ---------------------------------------------------------- Public Methods


    /**
     * Render the beginning {@code script} tag.
     *
     * @param context FacesContext for the current request
     * @param component UIComponent to be rendered
     *
     * @exception IOException if an input/output error occurs while rendering
     * @exception NullPointerException if {@code context}
     *  or {@code component} is {@code null}
     */
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        if (context == null || component == null) {
            throw new NullPointerException();
        }

        final StringBuilder results = new StringBuilder();

        final StrutsContext strutsContext = new StrutsContext(context);
        JavascriptValidatorComponent jsv = (JavascriptValidatorComponent) component;

        ModuleConfig config = strutsContext.getModuleConfig();

        ValidatorResources resources = Utils.getMapValue(ValidatorResources.class,
                context.getExternalContext().getApplicationMap(),
                ValidatorPlugIn.VALIDATOR_KEY + config.getPrefix());

        Locale locale = strutsContext.getLocale();

        // Look up the MessageResources bundle to be used
        String bundle = jsv.getBundle();
        if (bundle == null) {
            bundle = Globals.MESSAGES_KEY;
        }

        Form form = resources.getForm(locale, jsv.getFormName());
        if (form != null) {
            if (jsv.isDynamicJavascript()) {
                MessageResources messages = Utils.getMapValue(MessageResources.class,
                        context.getExternalContext().getApplicationMap(), bundle);

                List<ValidatorAction> lActions = new ArrayList<>();
                List<String> lActionMethods = new ArrayList<>();

                // Get List of actions for this Form
                for (Field field : form.getFields()) {

                    for (String depends : field.getDependencyList()) {

                        if (depends != null && !lActionMethods.contains(depends)) {
                            lActionMethods.add(depends);
                        }
                    }

                }

                // Create list of ValidatorActions based on lActionMethods
                for (Iterator<String> i = lActionMethods.iterator(); i.hasNext();) {
                    String depends = i.next();
                    ValidatorAction va = resources.getValidatorAction(depends);

                    // throw nicer NPE for easier debugging
                    if (va == null) {
                        throw new NullPointerException(
                            "Depends string \""
                                + depends
                                + "\" was not found in validator-rules.xml.");
                    }

                    String javascript = va.getJavascript();
                    if (javascript != null && javascript.length() > 0) {
                        lActions.add(va);
                    } else {
                        i.remove();
                    }
                }

                Collections.sort(lActions, COMP_VA);

                String methods = null;
                for (ValidatorAction va : lActions) {

                    if (methods == null) {
                        methods = va.getMethod() + "(form)";
                    } else {
                        methods += " && " + va.getMethod() + "(form)";
                    }
                }

                results.append(getJavascriptBegin(methods, jsv));

                final String formClientId = getFormClientId(jsv);
                final String formClientIdFunc = formClientId.replace(':', '_');

                for (ValidatorAction va : lActions) {
                    String jscriptVar = null;
                    String functionName = null;

                    if (va.getJsFunctionName() != null
                        && va.getJsFunctionName().length() > 0) {
                        functionName = va.getJsFunctionName();
                    } else {
                        functionName = va.getName();
                    }

                    results.append("    function " +
                      formClientIdFunc + "_" + functionName +
                      " () { \n");

                    for (Field field : form.getFields()) {

                        // Skip indexed fields for now until there is a good
                        // way to handle error messages (and the length of the
                        // list (could retrieve from scope?))
                        if (field.isIndexed()
                            || field.getPage() != jsv.getPage()
                            || !field.isDependency(va.getName())) {

                            continue;
                        }

                        String message =
                            Resources.getMessage(messages, locale, va, field);

                        message = (message != null) ? message : "";

                        jscriptVar = this.getNextVar(jscriptVar);

                        results.append(
                            "     this."
                                + jscriptVar
                                + " = new Array(\""
                                + formClientId
                                + ":"
                                + field.getKey()
                                + "\", \""
                                + message
                                + "\", ");

                        results.append("new Function (\"varName\", \"");

                        Map<String, Var> vars = field.getVars();
                        // Loop through the field's variables.
                        for (Map.Entry<String, Var> entry : vars.entrySet()) {
                            String varName = entry.getKey();
                            Var var = entry.getValue();
                            String varValue = var.getValue();
                            String jsType = var.getJsType();

                            // skip requiredif variables field, fieldIndexed,
                            // fieldTest, fieldValue
                            if (varName.startsWith("field")) {
                                continue;
                            }

                            if (Var.JSTYPE_INT.equalsIgnoreCase(jsType)) {
                                results.append(
                                    "this."
                                        + varName
                                        + "="
                                        + ValidatorUtils.replace(
                                            varValue,
                                            "\\",
                                            "\\\\")
                                        + "; ");
                            } else if (Var.JSTYPE_REGEXP.equalsIgnoreCase(
                                jsType)) {
                                results.append(
                                    "this."
                                        + varName
                                        + "=/"
                                        + ValidatorUtils.replace(
                                            varValue,
                                            "\\",
                                            "\\\\")
                                        + "/; ");
                            } else if (Var.JSTYPE_STRING.equalsIgnoreCase(
                                jsType)) {
                                results.append(
                                    "this."
                                        + varName
                                        + "='"
                                        + ValidatorUtils.replace(
                                            varValue,
                                            "\\",
                                            "\\\\")
                                        + "'; ");
                                // So everyone using the latest format doesn't
                                // need to change their xml files immediately.
                            } else if ("mask".equalsIgnoreCase(varName)) {
                                results.append(
                                    "this."
                                        + varName
                                        + "=/"
                                        + ValidatorUtils.replace(
                                            varValue,
                                            "\\",
                                            "\\\\")
                                        + "/; ");
                            } else {
                                results.append(
                                    "this."
                                        + varName
                                        + "='"
                                        + ValidatorUtils.replace(
                                            varValue,
                                            "\\",
                                            "\\\\")
                                        + "'; ");
                            }
                        }

                        results.append(" return this[varName];\"));\n");
                    }
                    results.append("    } \n\n");
                }
            } else if (jsv.isStaticJavascript()) {
                results.append(getStartElement(jsv));
                if (jsv.isHtmlComment()) {
                    results.append(HTML_BEGIN_COMMENT);
                }
            }
        }

        if (jsv.isStaticJavascript()) {
            results.append(getJavascriptStaticMethods(resources));
        }

        if (form != null
                && (jsv.isDynamicJavascript() || jsv.isStaticJavascript())) {

            results.append(getJavascriptEnd(jsv));
        }

        writer.write(results.toString());
    }

    // ----------------------------------------------------------- Properties

    /**
     * Returns the opening script element and some initial JavaScript.
     */
    protected String getJavascriptBegin(String methods, JavascriptValidatorComponent component) {
        StringBuilder sb = new StringBuilder();
        String name =
            component.getFormName().substring(0, 1).toUpperCase()
                + component.getFormName().substring(1);

        sb.append(getStartElement(component));

        if (isXhtml(component) && component.isCdata()) {
            sb.append("<![CDATA[\r\n");
        }

        if (!isXhtml(component) && component.isHtmlComment()) {
            sb.append(HTML_BEGIN_COMMENT);
        }
        sb.append("\n     var bCancel = false; \n\n");

        if (component.getMethod() == null || component.getMethod().isEmpty()) {
            sb.append(
                "    function validate"
                    + name
                    + "(form) {                                          "
                    + "                         \n");
        } else {
            sb.append(
                "    function "
                    + component.getMethod()
                    + "(form) {                                          "
                    + "                         \n");
        }
        sb.append("        if (bCancel) \n");
        sb.append("      return true; \n");
        sb.append("        else \n");

        // Always return true if there aren't any JavaScript validation methods
        if (methods == null || methods.isEmpty()) {
            sb.append("       return true; \n");
        } else {
            sb.append("       return " + methods + "; \n");
        }

        sb.append("   } \n\n");

        return sb.toString();
    }

    protected String getJavascriptStaticMethods(ValidatorResources resources) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n\n");

        for (ValidatorAction va : resources.getValidatorActions().values()) {
            if (va != null) {
                String javascript = va.getJavascript();
                if (javascript != null && javascript.length() > 0) {
                    sb.append(javascript + "\n");
                }
            }
        }

        return sb.toString();
    }

    /**
     * Returns the closing script element.
     */
    protected String getJavascriptEnd(JavascriptValidatorComponent component) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n");
        if (!isXhtml(component) && component.isHtmlComment()){
            sb.append(HTML_END_COMMENT);
        }

        if (isXhtml(component) && component.isCdata()) {
            sb.append("]]>\r\n");
        }

        sb.append("</script>\n\n");

        return sb.toString();
    }

    /**
     * The value {@code null} will be returned at the end of the sequence.
     * &nbsp;&nbsp;&nbsp; ex: "zz" will return {@code null}
     */
    private String getNextVar(String input) {
        if (input == null) {
            return "aa";
        }

        input = input.toLowerCase();

        for (int i = input.length(); i > 0; i--) {
            int pos = i - 1;

            char c = input.charAt(pos);
            c++;

            if (c <= 'z') {
                if (i == 0) {
                    return c + input.substring(pos, input.length());
                } else if (i == input.length()) {
                    return input.substring(0, pos) + c;
                } else {
                    return input.substring(0, pos) + c + input.substring(pos,
                      input.length() - 1);
                }
            } else {
                input = replaceChar(input, pos, 'a');
            }

        }

        return null;

    }

    /**
     * Replaces a single character in a {@code String}
     */
    private String replaceChar(String input, int pos, char c) {
        if (pos == 0) {
            return c + input.substring(pos, input.length());
        } else if (pos == input.length()) {
            return input.substring(0, pos) + c;
        } else {
            return input.substring(0, pos) + c + input.substring(pos,
              input.length() - 1);
        }
    }

    /**
     * Constructs the beginning &lt;script&gt; element depending on
     * xhtml status.
     */
    private String getStartElement(JavascriptValidatorComponent component) {
        StringBuilder start =
          new StringBuilder("<script type=\"text/javascript\"");

        // there is no language attribute in xhtml
        if (!isXhtml(component)) {
            start.append(" language=\"Javascript\"");
        }

        if (component.getSrc() != null) {
            start.append(" src=\"" + component.getSrc() + "\"");
        }

        start.append("> \n");
        return start.toString();
    }


    /**
     * <p>Return the {@code clientId} of the form component for which
     * we are rendering validation Javascript.</p>
     *
     * @exception IllegalStateException if we are not nested inside a
     *  UIComponentTag with a child FormComponent matching our form name
     */
    private String getFormClientId(final JavascriptValidatorComponent component){
        final String formName = component.getFormName();

        for (UIComponent current = component; current != null; current = current.getParent()) {
            List<UIComponent> kids = current.getChildren();
            for (UIComponent kid : kids) {
                if (kid instanceof FormComponent
                        && formName.equals(kid.getAttributes().get("beanName"))) {

                    return kid.getClientId(FacesContext.getCurrentInstance());
                }
            }
        }

        throw new IllegalArgumentException
            ("Cannot find child FormComponent for form '" + formName + "'");
    }
}
