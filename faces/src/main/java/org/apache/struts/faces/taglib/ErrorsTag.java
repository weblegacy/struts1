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


import javax.faces.component.UIComponent;


/**
 * <p>Render a set of validation or business logic error messages, for
 * the <em>Struts-Faces Integration Library</em>.</p>
 *
 *
 * @version $Rev$ $Date$
 */

public class ErrorsTag extends AbstractFacesTag {


    // ---------------------------------------------------------- Tag Attributes


    /**
     * <p>The property name for which to report errors.</p>
     */
    protected String property = null;

    public void setProperty(String property) {
        this.property = property;
    }


    // ---------------------------------------------------------- Public Methods


    /**
     * <p>Return the type of component to be created for this tag.</p>
     */
    public String getComponentType() {

        return ("org.apache.struts.faces.Errors");

    }


    /**
     * <p>Return the <code>rendererType</code> to be used for rendering
     * our component.</p>
     */
    public String getRendererType() {

        return ("org.apache.struts.faces.Errors");

    }


    /**
     * <p>Release any variables allocated during use of this tag instance.</p>
     */
    public void release() {

        super.release();
        this.property = null;

    }


    // -------------------------------------------------- UIComponentTag Methods


    /**
     * <p>Override attributes set on this tag instance.</p>
     *
     * @param component Component whose attributes should be overridden
     */
    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        setStringAttribute(component, "property", property);

    }


    // ------------------------------------------------------- Protected Methods


}
