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

package org.apache.struts.faces.taglib;

import org.apache.struts.faces.util.Utils;

import jakarta.el.ValueExpression;
import jakarta.faces.component.UIComponent;

/**
 * Custom tag that generates JavaScript for client side validation based
 * on the validation rules loaded by the <code>ValidatorPlugIn</code>
 * defined in the struts-config.xml file.  This is based on the code in
 * the corresponding class of the Struts HTML tag library, modified as needed
 * to reflect differences in the way JavaServer Faces renders field
 * identifiers.
 *
 * @version $Rev$ $Date$
 */
public class JavascriptValidatorTag extends AbstractFacesTag {

    // ---------------------------------------------------------- Tag Attributes


    /**
     * The name of the form that corresponds with the action name
     * in struts-config.xml. Specifying a form name places a
     * &lt;script&gt; &lt;/script&gt; around the JavaScript.
     */
    protected ValueExpression _formName;

    /**
     * Sets the key (form name) that will be used
     * to retrieve a set of validation rules to be
     * performed on the bean passed in for validation.
     * Specifying a form name places a &lt;script&gt;
     * &lt;/script&gt; tag around the JavaScript.
     */
    public void setFormName(ValueExpression formName) {
        this._formName = formName;
    }


    /**
     * The current page number of a multi-part form.
     * Only valid when the {@code formName} attribute is set.
     */
    protected ValueExpression _page;

    /**
     * Sets the current page number of a multi-part form.
     * Only field validations with a matching page number
     * will be generated that match the current page number.
     * Only valid when the {@code formName} attribute is set.
     */
    public void setPage(ValueExpression page) {
        this._page = page;
    }


    /**
     * This will be used as is for the JavaScript validation method name if it
     * has a value. This is the method name of the main JavaScript method that
     * the form calls to perform validations.
     */
    protected ValueExpression _method;

    /**
     * Sets the method name that will be used for the JavaScript
     * validation method name if it has a value.  This overrides
     * the auto-generated method name based on the key (form name)
     * passed in.
     */
    public void setMethod(ValueExpression method) {
        this._method = method;
    }


    /**
     * The static JavaScript methods will only be printed if this is set to
     * {@code true}.
     */
    protected ValueExpression _staticJavascript;

    /**
     * Sets whether or not to generate the static
     * JavaScript.  If this is set to {@code true}, which
     * is the default, the static JavaScript will be generated.
     */
    public void setStaticJavascript(ValueExpression staticJavascript) {
        this._staticJavascript = staticJavascript;
    }


    /**
     * The dynamic JavaScript objects will only be generated if this is set to
     * {@code true}.
     */
    protected ValueExpression _dynamicJavascript;

    /**
     * Sets whether or not to generate the dynamic
     * JavaScript.  If this is set to {@code true}, which
     * is the default, the dynamic JavaScript will be generated.
     */
    public void setDynamicJavascript(ValueExpression dynamicJavascript) {
        this._dynamicJavascript = dynamicJavascript;
    }


    /**
     * The {@code src} attribute for html script element (used to include an
     * external script resource). The {@code src} attribute is only recognized
     * when the {@code formName} attribute is specified.
     */
    protected ValueExpression _src;

    /**
     * Sets the {@code src} attribute's value when defining
     * the html script element. The {@code src} attribute is only recognized
     * when the {@code formName} attribute is specified.
     */
    public void setSrc(ValueExpression src) {
        this._src = src;
    }


    /**
     * The JavaScript methods will enclosed with html comments if this is set to
     * {@code true}.
     */
    protected ValueExpression _htmlComment;

    /**
     * Sets whether or not to delimit the JavaScript with html comments.
     * If this is set to {@code true}, which is the default, the
     * HTML-Comment will be surround the JavaScript.
     */
    public void setHtmlComment(ValueExpression htmlComment) {
        this._htmlComment = htmlComment;
    }


    /**
     * Hide JavaScript methods in a CDATA section for XHTML when {@code true}.
     */
    protected ValueExpression _cdata;

    /**
     * Sets the CDATA-status.
     * @param cdata The CDATA to set
     */
    public void setCdata(ValueExpression cdata) {
        this._cdata = cdata;
    }


    // ------------------------------------------------------------- Tag Methods


    /**
     * <p>Release any allocated resources.</p>
     */
    public void release() {

        super.release();
        _formName = null;
        _page = null;
        _method = null;
        _staticJavascript = null;
        _dynamicJavascript = null;
        _src = null;
        _htmlComment = null;
        _cdata = null;

    }


    // ---------------------------------------------------------- Public Methods


    /**
     * Return the type of component to be created for this tag.
     */
    public String getComponentType() {

        return ("org.apache.struts.faces.JavascriptValidator");

    }


    /**
     * Return the {@code rendererType} to be used for rendering
     * our component.
     */
    public String getRendererType() {

        return ("org.apache.struts.faces.JavascriptValidator");

    }


    // ------------------------------------------------------- Protected Methods


    /**
     * Override attributes set on this tag instance.
     *
     * @param component Component whose attributes should be overridden
     */
    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        Utils.setStringProperty(component, "formName", _formName);
        Utils.setIntegerProperty(component, "page", _page);
        Utils.setStringProperty(component, "method", _method);
        Utils.setBooleanProperty(component, "staticJavascript", _staticJavascript);
        Utils.setBooleanProperty(component, "dynamicJavascript", _dynamicJavascript);
        Utils.setStringProperty(component, "src", _src);
        Utils.setBooleanProperty(component, "htmlComment", _htmlComment);
        Utils.setBooleanProperty(component, "cdata", _cdata);

    }
}