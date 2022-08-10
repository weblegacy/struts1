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
 *     The "struts-logic-el" tag library contains tags that are useful in
 *     managing
 *     conditional generation of output text, looping over object collections for
 *     repetitive generation of output text, and application flow management.
 * </p>
 * <br>
 * <a id="doc.Description"></a>
 *
 * <div align="Center">
 *     <a href="#doc.Intro">[Introduction]</a>
 *     <a href="#doc.Functionality">[Logic-EL Functionality]</a>
 *     <a href="#doc.Examples">[Logic-EL Examples]</a>
 * </div>
 * <hr>
 * <a id="doc.Intro"></a>
 *
 * <h3>Introduction</h3>
 *
 * <p>
 *     The functionality of this tag library is entirely provided by the base
 *     "struts-logic" tag library in the Struts distribution. This derived tag
 *     library, "struts-logic-el", only provides a different way to evaluate
 *     attribute values, which is using the JavaServer Pages Standard Tag Library
 *     expression language engine, or the "JSTL EL" for short.
 * </p>
 *
 * <p>
 *     In general, the tags provided in the "struts-logic-el" library are a
 *     direct
 *     mapping from the "struts-logic" tag library. However, there are several
 *     tags in the base Struts tag library which were not "ported" to the
 *     "struts-logic-el" tag library, as it was determined that all of their
 *     functionality was provided by the JSTL. Information about these
 *     "non-ported" tags is provided in the information for the
 *     "org.apache.strutsel" package.
 * </p>
 *
 * <p>
 *     In addition, specific to the "struts-logic-el" library, two tags in this
 *     library have one additional attribute over their counterpart in the base
 *     Struts tag library. These tags are the <code>match</code> and
 *     <code>notMatch</code> tags, and the additional attribute is named
 *     <code>expr</code>, which is a value intended to be evaluated by the JSTL
 *     EL
 *     engine. More details about these tags and their attributes is provided in
 *     more detailed documentation about the package and its tags.
 * </p>
 * <a id="doc.Functionality"></a>
 *
 * <h3>Logic-EL Functionality</h3>
 *
 * <p>
 *     The functionality of the "logic-el" tags can be almost entirely understood
 *     from the documentation of the "struts-logic" base tag library. The only
 *     exception is the new attribute added to the <code>match</code> and
 *     <code>notMatch</code> tags, being the <code>expr</code> attribute.
 * </p>
 *
 * <p>
 *     The <code>match</code> and <code>notMatch</code> tags provide the ability
 *     to
 *     check to see whether a specific value is (or is not) a substring of a
 *     value
 *     obtained from either a cookie, request header, request parameter, or bean
 *     property. The addition of the <code>expr</code> attribute allows the
 *     obtained value to come from an arbitrary expression language value.
 * </p>
 * <a id="doc.Examples"></a>
 *
 * <h3>Logic-EL Examples</h3>
 *
 * <p>
 *     The following are discrete examples of uses of the "logic-el" tags, in no
 *     particular order, but emphasizing the use of JSTL EL values as attribute
 *     values.
 * </p>
 *
 * <p><i>Example:</i></p>
 * <pre>
 *    &lt;logic-el:forward name="${forwardName}"/&gt;</pre>
 *
 * <p><i>Example:</i></p>
 * <pre>
 *    &lt;%-- Iterates through all HTTP headers. --%&gt;
 *    &lt;logic-el:iterate id="item" collection="${header}"&gt;
 *    &lt;tr&gt;
 *    &lt;td&gt;&lt;c:out value="${item}.key"/&gt;&lt;/td&gt;
 *    &lt;td&gt;&lt;c:out value="${item}.value"/&gt;&lt;/td&gt;
 *    &lt;/tr&gt;
 *    &lt;/logic-el:iterate&gt;</pre>
 *
 * <p><i>Example:</i></p>
 * <pre>
 *    &lt;logic-el:match cookie="${cookieName}" value="${cookieValue}"&gt;
 *    Match succeeded.
 *    &lt;/logic-el:match&gt;</pre>
 *
 * <p><i>Example:</i></p>
 * <pre>
 *    &lt;logic-el:match expr='${hash["foo"]}' value="${matchValue}"&gt;
 *    Match succeeded.
 *    &lt;/logic-el:match&gt;</pre>
 *
 * <p><i>Example:</i></p>
 * <pre>
 *    &lt;logic-el:messagesPresent property="${messageKey}"&gt;
 *    Message found.
 *    &lt;/logic-el:messagesPresent&gt;</pre>
 *
 * <p><i>Example:</i></p>
 * <pre>
 *    &lt;logic-el:redirect href="http://localhost:${portnum}/factory" /&gt;</pre>
 */
package org.apache.strutsel.taglib.logic;