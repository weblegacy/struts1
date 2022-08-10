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
 *     The "struts-bean-el" tag library contains JSP custom tags useful in
 *     defining
 *     new beans (in any desired scope) from a variety of possible sources, as
 *     well
 *     as a tag to render a particular bean (or bean property) to the output
 *     response.
 * </p>
 * <br>
 * <a id="doc.Description"></a>
 *
 * <div align="Center">
 *     <a href="#doc.Intro">[Introduction]</a>
 *     <a href="#doc.Functionality">[Bean-EL Functionality]</a>
 *     <a href="#doc.Examples">[Bean-EL Examples]</a>
 * </div>
 * <hr>
 * <a id="doc.Intro"></a>
 *
 * <h3>Introduction</h3>
 *
 * <p>
 *     The functionality of this tag library is entirely provided by the base
 *     "struts-bean" tag library in the Struts distribution. This derived tag
 *     library, "struts-bean-el", only provides a different way to evaluate
 *     attribute values, which is using the JavaServer Pages Standard Tag Library
 *     expression language engine, or the "JSTL EL" for short.
 * </p>
 *
 * <p>
 *     In general, the tags provided in the "struts-bean-el" library are a direct
 *     mapping from the "struts-bean" tag library. However, there are several
 *     tags in the base Struts tag library which were not "ported" to the
 *     "struts-bean-el" tag library, as it was determined that all of their
 *     functionality was provided by the JSTL. Information about these
 *     "non-ported" tags is provided in the information for the
 *     "org.apache.strutsel" package.
 * </p>
 * <a id="doc.Functionality"></a>
 *
 * <h3>Bean-EL Functionality</h3>
 *
 * <p>
 *     The functionality of the "bean-el" tags can be almost entirely understood
 *     from the documentation of the "struts-bean" base tag library.
 * </p>
 * <a id="doc.Examples"></a>
 *
 * <h3>Bean-EL Examples</h3>
 *
 * <p>
 *     The following are discrete examples of uses of the "bean-el" tags, in no
 *     particular order, but emphasizing the use of JSTL EL values as attribute
 *     values.
 * </p>
 *
 * <p><i>Example:</i></p>
 * <pre>
 *    &lt;bean-el:message key="${messKey}" arg0="${arg}"/&gt;</pre>
 *
 * <p><i>Example:</i></p>
 * <pre>
 *    &lt;bean-el:resource id="var" name="${filename}" /&gt;</pre>
 *
 * <p><i>Example:</i></p>
 * <pre>
 *    &lt;%-- Size of pagescope hash table --%&gt;
 *    &lt;bean-el:size id="pageScopeSize" collection="${pageScope}" /&gt;</pre>
 *
 * <p><i>Example:</i></p>
 * <pre>
 *    &lt;bean-el:struts id="mapping" mapping="${actionName}" /&gt;</pre>
 */
package org.apache.strutsel.taglib.bean;