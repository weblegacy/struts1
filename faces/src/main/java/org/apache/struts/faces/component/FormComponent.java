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


import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.DynaActionFormClass;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.faces.util.StrutsContext;
import org.apache.struts.faces.util.Utils;
import org.apache.struts.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.el.ValueExpression;
import jakarta.faces.component.UIForm;
import jakarta.faces.context.FacesContext;


/**
 * <strong>FormComponent</strong> is a specialized subclass of
 * {@code jakarta.faces.component.UIForm} that supports automatic
 * creation of form beans in request or session scope.
 *
 * @version $Rev$ $Date$
 */
public class FormComponent extends UIForm {


    // -------------------------------------------------------- Static Variables


    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(FormComponent.class);


    // ------------------------------------------------------ Instance Variables


    private String action;
    private String enctype;
    private String focus;
    private String focusIndex;
    private String onreset;
    private String onsubmit;
    private String style;
    private String styleClass;
    private String target;


    // ---------------------------------------------------- Component Properties


    /**
     * <p>Return the Struts action path to which this form should be submitted.
     * </p>
     */
    public String getAction() {

        if (this.action != null) {
            return (this.action);
        }
        ValueExpression vb = getValueExpression("action");
        if (vb != null) {
            return ((String) vb.getValue(getFacesContext().getELContext()));
        } else {
            return (null);
        }

    }


    /**
     * <p>Set the Struts action to which this form should be submitted.</p>
     *
     * @param action The new action path
     */
    public void setAction(String action) {

        this.action = action;

    }


    /**
     * <p>Return the encoding type for this form submit.</p>
     */
    public String getEnctype() {

        if (this.enctype != null) {
            return (this.enctype);
        }
        ValueExpression vb = getValueExpression("enctype");
        if (vb != null) {
            return ((String) vb.getValue(getFacesContext().getELContext()));
        } else {
            return (null);
        }

    }


    /**
     * <p>Set the encoding type for this form submit.</p>
     *
     * @param enctype The new enctype path
     */
    public void setEnctype(String enctype) {

        this.enctype = enctype;

    }


    /**
     * <p>Return the component family to which this component belongs.</p>
     */
    public String getFamily() {

        return "org.apache.struts.faces.Form";

    }


    /**
     * <p>Return the focus element name.</p>
     */
    public String getFocus() {

        if (this.focus != null) {
            return (this.focus);
        }
        ValueExpression vb = getValueExpression("focus");
        if (vb != null) {
            return ((String) vb.getValue(getFacesContext().getELContext()));
        } else {
            return (null);
        }

    }


    /**
     * <p>Set the focus element name.</p>
     *
     * @param focus The new focus path
     */
    public void setFocus(String focus) {

        this.focus = focus;

    }


    /**
     * <p>Return the focus element index.</p>
     */
    public String getFocusIndex() {

        if (this.focusIndex != null) {
            return (this.focusIndex);
        }
        ValueExpression vb = getValueExpression("focusIndex");
        if (vb != null) {
            return ((String) vb.getValue(getFacesContext().getELContext()));
        } else {
            return (null);
        }

    }


    /**
     * <p>Set the focus element index.</p>
     *
     * @param focusIndex The new focusIndex path
     */
    public void setFocusIndex(String focusIndex) {

        this.focusIndex = focusIndex;

    }


    /**
     * <p>Return the JavaScript to execute on form reset.</p>
     */
    public String getOnreset() {

        if (this.onreset != null) {
            return (this.onreset);
        }
        ValueExpression vb = getValueExpression("onreset");
        if (vb != null) {
            return ((String) vb.getValue(getFacesContext().getELContext()));
        } else {
            return (null);
        }

    }


    /**
     * <p>Set the JavaScript to execute on form reset.</p>
     *
     * @param onreset The new onreset path
     */
    public void setOnreset(String onreset) {

        this.onreset = onreset;

    }


    /**
     * <p>Return the JavaScript to execute on form submit.</p>
     */
    public String getOnsubmit() {

        if (this.onsubmit != null) {
            return (this.onsubmit);
        }
        ValueExpression vb = getValueExpression("onsubmit");
        if (vb != null) {
            return ((String) vb.getValue(getFacesContext().getELContext()));
        } else {
            return (null);
        }

    }


    /**
     * <p>Set the JavaScript to execute on form submit.</p>
     *
     * @param onsubmit The new onsubmit path
     */
    public void setOnsubmit(String onsubmit) {

        this.onsubmit = onsubmit;

    }


    /**
     * <p>Return the CSS style(s) to be rendered for this component.</p>
     */
    public String getStyle() {

        ValueExpression vb = getValueExpression("style");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return style;
        }

    }


    /**
     * <p>Set the CSS style(s) to be rendered for this component.</p>
     *
     * @param style The new CSS style(s)
     */
    public void setStyle(String style) {

        this.style = style;

    }


    /**
     * <p>Return the CSS style class(es) to be rendered for this component.</p>
     */
    public String getStyleClass() {

        ValueExpression vb = getValueExpression("styleClass");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return styleClass;
        }

    }


    /**
     * <p>Set the CSS style class(es) to be rendered for this component.</p>
     *
     * @param styleClass The new CSS style class(es)
     */
    public void setStyleClass(String styleClass) {

        this.styleClass = styleClass;

    }


    /**
     * <p>Return the target frame for the response to this form submit.</p>
     */
    public String getTarget() {

        ValueExpression vb = getValueExpression("target");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return target;
        }

    }


    /**
     * <p>Set the target frame for the response to this form submit.</p>
     *
     * @param target The new CSS target(s)
     */
    public void setTarget(String target) {

        this.target = target;

    }


    // ---------------------------------------------------------- UIForm Methods


    /**
     * <p>Create an instance of the form bean (if necessary) before
     * delegating to the standard decoding process.</p>
     *
     * @param context FacesContext for the request we are processing
     */
    public void processDecodes(FacesContext context) {

        if (context == null) {
            throw new NullPointerException();
        }

        String clientId = getClientId(context);
        log.debug("processDecodes({})", clientId);

        // Create the form bean (if necessary)
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        if (params.containsKey(clientId)) {
            createActionForm(context);
        }

        // Perform the standard decode processing
        super.processDecodes(context);

    }


    /**
     * <p>Restore our state from the specified object.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param state Object containing our saved state
     */
    public void restoreState(FacesContext context, Object state) {

        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        action = (String) values[1];
        enctype = (String) values[2];
        focus = (String) values[3];
        focusIndex = (String) values[4];
        onreset = (String) values[5];
        onsubmit = (String) values[6];
        style = (String) values[7];
        styleClass = (String) values[8];
        target = (String) values[9];

    }


    /**
     * <p>Create and return an object representing our state to be saved.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    public Object saveState(FacesContext context) {

        Object values[] = new Object[10];
        values[0] = super.saveState(context);
        values[1] = action;
        values[2] = enctype;
        values[3] = focus;
        values[4] = focusIndex;
        values[5] = onreset;
        values[6] = onsubmit;
        values[7] = style;
        values[8] = styleClass;
        values[9] = target;
        return (values);

    }


    // ---------------------------------------------------------- Public Methods


    /**
     * <p>Create an appropriate form bean in the appropriate scope, if one
     * does not already exist.</p>
     *
     * @param context FacesContext for the current request
     *
     * @exception IllegalArgumentException if no ActionConfig for the
     *  specified action attribute can be located
     * @exception IllegalArgumentException if no FormBeanConfig for the
     *  specified form bean can be located
     * @exception IllegalArgumentException if no ModuleConfig can be
     *  located for this application module
     */
    public void createActionForm(FacesContext context) {

        final StrutsContext strutsContext = new StrutsContext(context);

        // Look up the application module configuration information we need
        ModuleConfig moduleConfig = strutsContext.getModuleConfig();

        // Look up the ActionConfig we are processing
        String action = getAction();
        ActionConfig actionConfig = moduleConfig.findActionConfig(action);
        if (actionConfig == null) {
            throw new IllegalArgumentException("Cannot find action '" +
                                               action + "' configuration");
        }

        // Does this ActionConfig specify a form bean?
        String name = actionConfig.getName();
        if (name == null) {
            return;
        }

        // Look up the FormBeanConfig we are processing
        FormBeanConfig fbConfig = moduleConfig.findFormBeanConfig(name);
        if (fbConfig == null) {
            throw new IllegalArgumentException("Cannot find form bean '" +
                                               name + "' configuration");
        }

        // Does a usable form bean attribute already exist?
        String attribute = actionConfig.getAttribute();
        String scope = actionConfig.getScope();
        ActionForm instance = null;
        if ("request".equals(scope)) {
            instance = Utils.getMapValue(ActionForm.class,
                    context.getExternalContext().getRequestMap(), attribute);
        } else if ("session".equals(scope)) {
            context.getExternalContext().getSession(true);
            instance = Utils.getMapValue(ActionForm.class,
                    context.getExternalContext().getSessionMap(), attribute);
        }
        if (instance != null) {
            if (fbConfig.getDynamic()) {
                String className =
                    ((DynaBean) instance).getDynaClass().getName();
                if (className.equals(fbConfig.getName())) {
                    log.debug(" Recycling existing DynaActionForm instance "
                        + "of type '{}'", className);
                    return;
                }
            } else {
                try {
                    Class<?> configClass =
                        RequestUtils.applicationClass(fbConfig.getType());
                    if (configClass.isAssignableFrom(instance.getClass())) {
                        log.debug(" Recycling existing ActionForm instance "
                            + "of class '{}'", instance.getClass().getName());
                        return;
                    }
                } catch (Throwable t) {
                    IllegalArgumentException t2 = new IllegalArgumentException
                        ("Cannot load form bean class '" +
                         fbConfig.getType() + "'");
                    t2.initCause(t);
                    throw t2;
                }
            }
        }

        // Create a new form bean instance
        if (fbConfig.getDynamic()) {
            try {
                DynaActionFormClass dynaClass =
                    DynaActionFormClass.createDynaActionFormClass(fbConfig);
                instance = (ActionForm) dynaClass.newInstance();
                log.debug(" Creating new DynaActionForm instance "
                    + "of type '{}'", fbConfig.getType());
                log.trace(" --> {}", instance);
            } catch (Throwable t) {
                IllegalArgumentException t2 = new IllegalArgumentException
                    ("Cannot create form bean of type '" +
                     fbConfig.getType() + "'");
                t2.initCause(t);
                throw t2;
            }
        } else {
            try {
                instance = (ActionForm)
                    RequestUtils.applicationInstance(fbConfig.getType());
                log.debug(" Creating new ActionForm instance "
                    + "of type '{}'", fbConfig.getType());
                log.trace(" --> {}", instance);
            } catch (Throwable t) {
                IllegalArgumentException t2 = new IllegalArgumentException
                    ("Cannot create form bean of class '" +
                     fbConfig.getType() + "'");
                t2.initCause(t);
                throw t2;
            }
        }

        // Configure and cache the form bean instance in the correct scope
        ActionServlet servlet = Utils.getMapValue(ActionServlet.class,
                context.getExternalContext().getApplicationMap(),
                Globals.ACTION_SERVLET_KEY);
        instance.setServlet(servlet);
        if ("request".equals(scope)) {
            context.getExternalContext().getRequestMap().put
                (attribute, instance);
        } else if ("session".equals(scope)) {
            context.getExternalContext().getSessionMap().put
                (attribute, instance);
        }

    }
}