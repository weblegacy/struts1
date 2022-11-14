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


import org.apache.struts.faces.util.Utils;

import jakarta.el.ValueExpression;
import jakarta.faces.component.UIComponent;
import jakarta.faces.webapp.UIComponentELTag;


/**
 * Generate a URL-encoded hyperlink to the specified URI, with
 * associated query parameters selecting a specified Subscription.
 *
 * @author Craig R. McClanahan
 * @version $Rev$ $Date$
 */

public class LinkSubscriptionTag extends UIComponentELTag {


    // -------------------------------------------------------------- Attributes


    /**
     * The attribute name.
     */
    protected ValueExpression _name;

    public void setName(ValueExpression name) {
        this._name = name;
    }


    /**
     * The context-relative URI.
     */
    protected ValueExpression _page;

    public void setPage(ValueExpression page) {
        this._page = page;
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
        this._name = null;
        this._page = null;

    }


    // ------------------------------------------------------- Protected Methods


    /**
     * <p>Override attributes set on this tag instance.</p>
     *
     * @param component Component whose attributes should be overridden
     */
    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        Utils.setStringProperty(component, "name", _name, "subscription");
        Utils.setStringProperty(component, "page", _page);

    }
}