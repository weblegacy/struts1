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


package org.apache.struts.webapp.example2;


import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;


/**
 * Generate a URL-encoded hyperlink to the specified URI, with
 * associated query parameters selecting a specified Subscription.
 *
 * @author Craig R. McClanahan
 * @version $Rev$ $Date$
 */

public class LinkSubscriptionTag extends UIComponentTag {


    // -------------------------------------------------------------- Attributes


    /**
     * The attribute name.
     */
    protected String name = "subscription";

    public void setName(String name) {
        this.name = name;
    }


    /**
     * The context-relative URI.
     */
    protected String page = null;

    public void setPage(String page) {
        this.page = page;
    }


    // ---------------------------------------------------------- Public Methods


    /**
     * Return the component type for this tag.</p>
     */
    public String getComponentType() {

        return ("Output");

    }


    /**
     * <p>Return the renderer type associated with this tag.</p>
     */
    public String getRendererType() {

        return ("LinkSubscription");

    }


    /**
     * <p>Release resources allocated to this tag instance.</p>
     */
    public void release() {

        super.release();
        this.name = "subscription";
        this.page = null;

    }


    // ------------------------------------------------------- Protected Methods


    /**
     * <p>Override attributes set on this tag instance.</p>
     *
     * @param component Component whose attributes should be overridden
     */
    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        if (name != null) {
            if (isValueReference(name)) {
                ValueBinding vb =
                    getFacesContext().getApplication().createValueBinding(name);
                component.setValueBinding("name", vb);
            } else {
                component.getAttributes().put("name", name);
            }
        }
        if (page != null) {
            if (isValueReference(page)) {
                ValueBinding vb =
                    getFacesContext().getApplication().createValueBinding(page);
                component.setValueBinding("page", vb);
            } else {
                component.getAttributes().put("page", page);
            }
        }

    }


}
