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

package examples.multibox;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * An ActionForm for the Multibox examples
 *
 * @version $Rev$ $Date$
 */
public class MultiboxActionForm extends ActionForm {
    private static final long serialVersionUID = -7784260148584459143L;

    // ------------------------------------------------------ Instance Variables

    /** Fruits */
    private String[] fruits = {};

    /** Colors */
    private String[] colors = {};

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for MultiboxActionForm.
     */
    public MultiboxActionForm() {
        super();
    }

    // ---------------------------------------------------------- Public Methods

    /**
     * Clear all checkboxes
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {

        /*
         * The ActionForm reset method should only be used to *clear*
         * checkboxes. The correct place to *set* default checkbox values is in
         * the 'prepare' action, called prior to displaying the form page.
         */
        String[] empty = {};
        this.fruits = empty;
        this.colors = empty;

    }

    /**
     * Validate the properties that have been set from this HTTP request,
     * and return an <code>ActionMessages</code> object that encapsulates any
     * validation errors that have been found.  If no errors are found, return
     * <code>null</code> or an <code>ActionMessages</code> object with no
     * recorded error messages.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     *
     * @return ActionMessages if any validation errors occurred
     */
    public ActionErrors validate(
        ActionMapping mapping,
        HttpServletRequest request) {

        /*
         * We're not doing any validation (yet) so return null to
         * indicate that there were no errors. (We don't
         * actually need to override this nethod unles we're doing
         * validation - but it's here for reference)
         */
        return null;
    }

    // -------------------------------------------------------------- Properties

    /**
     * Returns the colors.
     * @return String[]
     */
    public String[] getColors() {
        return colors;
    }

    /**
     * Returns the fruits.
     * @return String[]
     */
    public String[] getFruits() {
        return fruits;
    }

    /**
     * Sets the colors.
     * @param colors The colors to set
     */
    public void setColors(String[] colors) {
        this.colors = colors;
    }

    /**
     * Sets the fruits.
     * @param fruits The fruits to set
     */
    public void setFruits(String[] fruits) {
        this.fruits = fruits;
    }

}
