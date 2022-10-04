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

import org.apache.struts.taglib.html.ErrorsTag;

/**
 * <p>Custom tag that renders error messages if an appropriate request
 * attribute has been created.  The tag looks for a request attribute with a
 * reserved key, and assumes that it is either a String, a String array,
 * containing message keys to be looked up in the module's MessageResources,
 * or an object of type {@code org.apache.struts.action.ActionErrors}.</p>
 * <p>The following optional message keys will be utilized if corresponding
 * messages exist for them in the application resources:</p>
 *
 * <ul>
 *
 * <li><b>errors.header</b> - If present, the corresponding message will be
 * rendered prior to the individual list of error messages.</li>
 *
 * <li><b>errors.footer</b> - If present, the corresponding message will be
 * rendered following the individual list of error messages.</li>
 *
 * <li><b>errors.prefix</b> - If present, the corresponding message will be
 * rendered before each individual error message.</li>
 *
 * <li><b>errors.suffix</b> - If present, the corresponding message will be
 * rendered after each individual error message.</li>
 *
 * </ul>
 *
 * <p>This class is a subclass of the class {@code org.apache.struts.taglib.html.ErrorsTag}
 * which provides most of the described functionality. This subclass allows
 * all attribute values to be specified as expressions utilizing the
 * JavaServer Pages Standard Library expression language.</p>
 *
 * @version $Rev$
 */
public class ELErrorsTag extends ErrorsTag {
    private static final long serialVersionUID = -3399484052918626699L;
}