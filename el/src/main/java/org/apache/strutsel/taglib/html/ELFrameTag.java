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
package org.apache.strutsel.taglib.html;

import org.apache.struts.taglib.html.FrameTag;

/**
 * Generate an HTML <code>&lt;frame&gt;</code> tag with similar capabilities
 * as those the <code>&lt;html:link&gt;</code> tag provides for hyperlink
 * elements.  The <code>src</code> element is rendered using the same
 * technique that {@link org.apache.struts.taglib.html.LinkTag LinkTag} uses
 * to render the <code>href</code> attribute of a hyperlink.  Additionally,
 * the HTML 4.0 frame tag attributes <code>noresize</code>,
 * <code>scrolling</code>, <code>marginheight</code>, <code>marginwidth</code>,
 * <code>frameborder</code>, and <code>longdesc</code> are supported.  The
 * frame <code>name</code> attribute is rendered based on the
 * <code>frameName</code> property. <p> Note that the value of
 * <code>longdesc</code> is intended to be a URI, but currently no rewriting
 * is supported.  The attribute is set directly from the property value. <p>
 * This class is a subclass of the class <code>org.apache.struts.taglib.html.FrameTag</code>
 * which provides most of the described functionality.  This subclass allows
 * all attribute values to be specified as expressions utilizing the
 * JavaServer Pages Standard Library expression language.
 *
 * @version $Rev$
 */
public class ELFrameTag extends FrameTag {
    private static final long serialVersionUID = 6386189000643164187L;
}