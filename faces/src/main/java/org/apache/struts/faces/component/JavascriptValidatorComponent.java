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

package org.apache.struts.faces.component;

import jakarta.el.ValueExpression;
import jakarta.faces.component.UIOutput;
import jakarta.faces.context.FacesContext;

/**
 * {@code JavascriptValidatorComponent} is a specialized
 * subclass of {@code UIOutput} that supports automatic creation of
 * JavaScript for client side validation based on the validation
 * rules loaded by the {@code ValidatorPlugIn} defined in the
 * {@code struts-config.xml} file. This is based on the code in
 * the corresponding class of the Struts HTML tag library, modified
 * as needed to reflect differences in the way JavaServer Faces
 * renders field identifiers.
 *
 * @version $Rev$ $Date$
 */
public class JavascriptValidatorComponent extends UIOutput {


    // ------------------------------------------------------------ Constructors


    /**
     * Create a new {@link JavascriptValidatorComponent} with default properties.
     */
    public JavascriptValidatorComponent() {

        super();
        setRendererType("org.apache.struts.faces.JavascriptValidator");

    }


    // ------------------------------------------------------ Instance Variables


    /**
     * MessageResources attribute key to use for message lookup.
     */
    private String bundle = null;

    /**
     * The name of the form that corresponds with the action name
     * in struts-config.xml. Specifying a form name places a
     * &lt;script&gt; &lt;/script&gt; around the JavaScript.
     */
    private String formName = null;

    /**
     * The current page number of a multi-part form.
     * Only valid when the {@code formName} attribute is set.
     */
    private int page = 0;
    private boolean pageSet = false;

    /**
     * This will be used as is for the JavaScript validation method name if it
     * has a value. This is the method name of the main JavaScript method that
     * the form calls to perform validations.
     */
    private String method = null;

    /**
     * The static JavaScript methods will only be printed if this is set to
     * {@code true}.
     */
    private boolean staticJavascript = true;
    private boolean staticJavascriptSet = false;

    /**
     * The dynamic JavaScript objects will only be generated if this is set to
     * {@code true}.
     */
    private boolean dynamicJavascript = true;
    private boolean dynamicJavascriptSet = false;

    /**
     * The {@code src} attribute for html script element (used to include an
     * external script resource). The {@code src} attribute is only recognized
     * when the {@code formName} attribute is specified.
     */
    private String src = null;

    /**
     * The JavaScript methods will enclosed with html comments if this is set to
     * {@code true}.
     */
    private boolean htmlComment = true;
    private boolean htmlCommentSet = false;

    /**
     * Hide JavaScript methods in a CDATA section for XHTML when {@code true}.
     */
    private boolean cdata = true;
    private boolean cdataSet = false;


    // ---------------------------------------------------- Component Properties


    /**
     * Return the MessageResources key.
     */
    public String getBundle() {

        if (bundle != null) {
            return bundle;
        }
        ValueExpression vb = getValueExpression("bundle");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return null;
        }

    }


    /**
     * Set the MessageResources key.
     *
     * @param bundle The new key
     */
    public void setBundle(String bundle) {

        this.bundle = bundle;

    }

    /**
     * Return the component family to which this component belongs.
     */
    public String getFamily() {

        return "org.apache.struts.faces.JavascriptValidator";

    }

    /**
     * Gets the key (form name) that will be used
     * to retrieve a set of validation rules to be
     * performed on the bean passed in for validation.
     */
    public String getFormName() {

        if (formName != null) {
            return formName;
        }
        ValueExpression vb = getValueExpression("formName");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return null;
        }

    }

    /**
     * Sets the key (form name) that will be used
     * to retrieve a set of validation rules to be
     * performed on the bean passed in for validation.
     * Specifying a form name places a
     * &lt;script&gt; &lt;/script&gt; tag around the javascript.
     */
    public void setFormName(String formName) {

        this.formName = formName;

    }

    /**
     * Gets the current page number of a multi-part form.
     * Only field validations with a matching page numer
     * will be generated that match the current page number.
     * Only valid when the formName attribute is set.
     */
    public int getPage() {

        if (pageSet) {
            return page;
        }
        ValueExpression vb = getValueExpression("page");
        if (vb != null) {
            return (Integer) vb.getValue(getFacesContext().getELContext());
        } else {
            return 0;
        }

    }

    /**
     * Sets the current page number of a multi-part form.
     * Only field validations with a matching page numer
     * will be generated that match the current page number.
     * Only valid when the formName attribute is set.
     */
    public void setPage(int page) {

        this.page = page;
        this.pageSet = true;

    }

    /**
     * Gets the method name that will be used for the JavaScript
     * validation method name if it has a value.  This overrides
     * the auto-generated method name based on the key (form name)
     * passed in.
     */
    public String getMethod() {

        if (method != null) {
            return method;
        }
        ValueExpression vb = getValueExpression("method");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return null;
        }

    }

    /**
     * Sets the method name that will be used for the JavaScript
     * validation method name if it has a value.  This overrides
     * the auto-generated method name based on the key (form name)
     * passed in.
     */
    public void setMethod(String method) {

        this.method = method;

    }

    /**
     * Gets whether or not to generate the static
     * JavaScript.  If this is set to 'true', which
     * is the default, the static JavaScript will be generated.
     */
    public boolean isStaticJavascript() {

        if (staticJavascriptSet) {
            return staticJavascript;
        }
        ValueExpression vb = getValueExpression("staticJavascript");
        if (vb != null) {
            return (Boolean) vb.getValue(getFacesContext().getELContext());
        } else {
            return true;
        }

    }

    /**
     * Sets whether or not to generate the static
     * JavaScript.  If this is set to 'true', which
     * is the default, the static JavaScript will be generated.
     */
    public void setStaticJavascript(boolean staticJavascript) {

        this.staticJavascript = staticJavascript;
        this.staticJavascriptSet = true;

    }

    /**
     * Gets whether or not to generate the dynamic
     * JavaScript.  If this is set to 'true', which
     * is the default, the dynamic JavaScript will be generated.
     */
    public boolean isDynamicJavascript() {

        if (dynamicJavascriptSet) {
            return dynamicJavascript;
        }
        ValueExpression vb = getValueExpression("dynamicJavascript");
        if (vb != null) {
            return (Boolean) vb.getValue(getFacesContext().getELContext());
        } else {
            return true;
        }

    }

    /**
     * Sets whether or not to generate the dynamic
     * JavaScript.  If this is set to 'true', which
     * is the default, the dynamic JavaScript will be generated.
     */
    public void setDynamicJavascript(boolean dynamicJavascript) {

        this.dynamicJavascript = dynamicJavascript;
        this.dynamicJavascriptSet = true;

    }

    /**
     * Gets the src attribute's value when defining
     * the html script element.
     */
    public String getSrc() {

        if (src != null) {
            return src;
        }
        ValueExpression vb = getValueExpression("src");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return null;
        }

    }

    /**
     * Sets the src attribute's value when defining
     * the html script element. The src attribute is only recognized
     * when the formName attribute is specified.
     */
    public void setSrc(String src) {

        this.src = src;

    }

    /**
     * Gets whether or not to delimit the JavaScript with HTML comments.
     * If this is set to 'true', which is the default, the HTML-
     * Comment will be surround the JavaScript.
     */
   public boolean isHtmlComment() {

       if (htmlCommentSet) {
           return htmlComment;
       }
       ValueExpression vb = getValueExpression("htmlComment");
       if (vb != null) {
           return (Boolean) vb.getValue(getFacesContext().getELContext());
       } else {
           return true;
       }

   }

   /**
    * Sets whether or not to delimit the JavaScript with HTML comments.
    * If this is set to 'true', which is the default, the HTML-
    * Comment will be surround the JavaScript.
    */
   public void setHtmlComment(boolean htmlComment) {

       this.htmlComment = htmlComment;
       this.htmlCommentSet = true;

   }

   /**
    * Returns the cdata setting "true" or "false".
    * @return String - "true" if JavaScript will be hidden in a CDATA section
    */
   public boolean isCdata() {

       if (cdataSet) {
           return cdata;
       }
       ValueExpression vb = getValueExpression("cdata");
       if (vb != null) {
           return (Boolean) vb.getValue(getFacesContext().getELContext());
       } else {
           return true;
       }

   }

   /**
    * Sets the CDATA status.
    * @param cdata The CDATA to set
    */
   public void setCdata(boolean cdata) {

       this.cdata = cdata;
       this.cdataSet = true;

   }


   // ---------------------------------------------------------- UIForm Methods


   /**
    * <p>Restore our state from the specified object.</p>
    *
    * @param context <code>FacesContext</code> for the current request
    * @param state Object containing our saved state
    */
   public void restoreState(FacesContext context, Object state) {

       Object values[] = (Object[]) state;
       super.restoreState(context, values[0]);
       bundle = (String) values[1];
       formName = (String) values[2];
       page = (Integer) values[3];
       pageSet = (Boolean) values[4];
       method = (String) values[5];
       staticJavascript =  (Boolean) values[6];
       staticJavascriptSet =  (Boolean) values[7];
       dynamicJavascript =  (Boolean) values[8];
       dynamicJavascriptSet =  (Boolean) values[9];
       src = (String) values[10];
       htmlComment =  (Boolean) values[11];
       htmlCommentSet =  (Boolean) values[12];
       cdata =  (Boolean) values[13];
       cdataSet =  (Boolean) values[14];

   }


   /**
    * <p>Create and return an object representing our state to be saved.</p>
    *
    * @param context <code>FacesContext</code> for the current request
    */
   public Object saveState(FacesContext context) {

       Object values[] = new Object[15];
       values[0] = super.saveState(context);
       values[1] = bundle;
       values[2] = formName;
       values[3] = page;
       values[4] = pageSet;
       values[5] = method;
       values[6] = staticJavascript;
       values[7] = staticJavascriptSet;
       values[8] = dynamicJavascript;
       values[9] = dynamicJavascriptSet;
       values[10] = src;
       values[11] = htmlComment;
       values[12] = htmlCommentSet;
       values[13] = cdata;
       values[14] = cdataSet;
       return values;

   }


}
