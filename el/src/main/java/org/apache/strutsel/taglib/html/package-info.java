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

/**
 * <p>
 *     <a id="doc.Description">The "struts-html-el" tag library</a> contains
 *     JSP
 *     custom tags useful in creating dynamic HTML user interfaces, including
 *     input
 *     forms.
 * </p>
 * <br>
 * <a id="doc.Description"></a>
 *
 * <div align="Center">
 *     <a href="#doc.Intro">[Introduction]</a>
 *     <a href="#doc.Functionality">[Html-EL Functionality]</a>
 *     <a href="#doc.Examples">[Html-EL Examples]</a>
 * </div>
 * <hr>
 * <a id="doc.Intro"></a>
 *
 * <h3>Introduction</h3>
 *
 * <p>
 *     The functionality of this tag library is entirely provided by the base
 *     "struts-html" tag library in the Struts distribution. This derived tag
 *     library, "struts-html-el", only provides a different way to evaluate
 *     attribute values, which is using the JavaServer Pages Standard Tag Library
 *     expression language engine, or the "JSTL EL" for short.
 * </p>
 * <a id="doc.Functionality"></a>
 *
 * <h3>Html-EL Functionality</h3>
 *
 * <p>
 *     The functionality of the "html-el" tags can be almost entirely understood
 *     from the documentation of the "struts-html" base tag library.
 * </p>
 * <a id="doc.Examples"></a>
 *
 * <h3>Html-EL Examples</h3>
 *
 * <p>
 *     The following are discrete examples of uses of the "html-el" tags, in no
 *     particular order, but emphasizing the use of JSTL EL values as attribute
 *     values.
 * </p>
 *
 * <p><i>Example:</i></p>
 * <pre>
 *    &lt;html-el:button onblur="handler('${arg}')"
 *    styleClass='${styleClass["button"]'
 *    titleKey='${titleKey["button"]}'
 *    value='${buttonValue["button"]}' /&gt;</pre>
 *
 * <p><i>Example:</i></p>
 * <pre>
 *    &lt;html-el:checkbox property="button" value="${flagValue}"/&gt;</pre>
 *
 * <p><i>Example:</i></p>
 * <pre>
 *    &lt;html-el:link page="/doit.do" accesskey='${linkkey["doit"]}' &gt;
 *    A link
 *    &lt;/html-el:link&gt;</pre>
 *
 * <p><i>Example:</i></p>
 * <pre>
 *    &lt;html-el:link page="/doit.do"
 *    onclick="openModal('/editSalary.do?id=${employee.id}')" &gt;
 *    A link
 *    &lt;/html-el:link&gt;</pre>
 */
package org.apache.strutsel.taglib.html;