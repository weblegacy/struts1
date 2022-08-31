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

package org.apache.struts.scripting;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;


/**
 * Holds Struts objects.
 */
public class StrutsInfo {

    /** Forward name. */
    private String forwardName = null;

    /** Forward object. */
    private ActionForward forward = null;

    /** ActionForm for this request. */
    private ActionForm form = null;

    /** ActionMapping for this request. */
    private ActionMapping mapping = null;

    /** ScriptAction instance for this request. */
    private ScriptAction action = null;

    /** The message resources for this request. */
    private MessageResources res = null;

    /**
     * Constructor.
     *
     * @param action The action instance
     * @param mapping The action mapping
     * @param form  The action form
     * @param res   The message resources for the current locale
     */
    public StrutsInfo(ScriptAction action, ActionMapping mapping,
        ActionForm form, MessageResources res) {
        this.action = action;
        this.mapping = mapping;
        this.form = form;
        this.res = res;
    }

    /**
     * Sets the forward name.
     *
     * @param f The forward name
     */
    public void setForwardName(String f) {
        forwardName = f;
    }

    /**
     * Gets the forward object. If none is set, it tries to find the set
     * forward name.
     *
     * @return The action forward
     */
    public ActionForward getForward() {
        if (forward == null) {
            if (forwardName != null) {
                return mapping.findForward(forwardName);
            }
        }
        return forward;
    }

    /**
     * Sets the action forward object.
     *
     * @param f The action forward
     */
    public void setForward(ActionForward f) {
        forward = f;
    }

    /**
     * Sets the action form.
     *
     * @param form The action form
     */
    public void setForm(ActionForm form) {
        this.form = form;
    }

    /**
     * Sets the action mapping.
     *
     * @param mapping The action mapping
     */
    public void setMapping(ActionMapping mapping) {
        this.mapping = mapping;
    }

    /**
     * Sets the action instance.
     *
     * @param action The Struts action
     */
    public void setAction(ScriptAction action) {
        this.action = action;
    }

    /**
     * Sets the message resources.
     *
     * @param res The message resources
     */
    public void setMessages(MessageResources res) {
        this.res = res;
    }

    /**
     * Gets the action form.
     *
     * @return The action form
     */
    public ActionForm getForm() {
        return form;
    }

    /**
     * Gets the action mapping.
     *
     * @return The action mapping
     */
    public ActionMapping getMapping() {
        return mapping;
    }

    /**
     * Gets the action instance.
     *
     * @return The Struts action
     */
    public ScriptAction getAction() {
        return action;
    }

    /**
     * Gets the message resources.
     *
     * @return The message resources
     */
    public MessageResources getMessages() {
        return res;
    }
}