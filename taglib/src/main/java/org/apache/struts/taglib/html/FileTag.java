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
package org.apache.struts.taglib.html;

/**
 * Custom tag for input fields of type "file".
 */
public class FileTag extends BaseFieldTag {
    private static final long serialVersionUID = 1740523851758372845L;

    // ----------------------------------------------------- Instance Variables

    /**
     * This property is used to set or return one or multiple files selected
     * with the file upload button.
     *
     * @since 1.5.0
     */
    private boolean multiple = false;

    /**
     * Construct a new instance of this tag.
     */
    public FileTag() {
        super();
        this.type = "file";
    }

    // ------------------------------------------------------------- Properties

    /**
     * Sets the multiple property.
     *
     * @since 1.5.0
     */
    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    /**
     * Returns the multiple property.
     *
     * @since 1.5.0
     */
    public boolean getMultiple() {
        return multiple;
    }

    // --------------------------------------------------------- Public Methods

    /**
     * 'Hook' to enable tags to be extended and additional attributes added.
     *
     * @param handlers The StringBuilder that output will be appended to.
     */
    @Override
    protected void prepareOtherAttributes(StringBuilder handlers) {
        super.prepareOtherAttributes(handlers);

        if (getMultiple()) {
            handlers.append(" multiple=\"multiple\"");
        }
    }

    /**
     * Release any acquired resources.
     */
    @Override
    public void release() {
        super.release();
        multiple = false;
    }
}
