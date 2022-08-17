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
 *     <a id="doc.Description">The "Struts-EL" library</a>
 *     contains a set of tag libraries, all of whose classes are derived from
 *     classes in the Struts tag libraries. The Struts-EL tags all implement the
 *     same attributes as their counterparts in the base Struts tag libraries.
 *     The
 *     difference provided by Struts-EL is that the tag attribute values are not
 *     evaluated as run-time scriptlets (sometimes called "rtexprvalue"s), but
 *     are
 *     instead evaluated by the expression language engine in the JavaServer
 *     Pages
 *     Standard Tag Library (often called the JSTL).
 * </p>
 *
 * <div align="Center">
 *     <p>
 *         <a href="#doc.Intro">[Introduction]</a>
 *         <a href="#doc.Html">[HTML-EL Tag Library]</a>
 *         <a href="#doc.Logic">[Logic-EL Tag Library]</a>
 *         <a href="#doc.Bean">[Bean-EL Tag Library]</a>
 * </div>
 * <hr>
 *
 * <h3><a id="doc.Intro">Introduction</a></h3>
 *
 * <p>
 *     The Struts-EL library is intended to be used alongside the Struts library,
 *     and with the JSTL. In the development of the Struts-EL library, each tag
 *     in
 *     the Struts tag libraries was examined to see if all of its functionality
 *     is
 *     covered in an existing tag in the JSTL. If this was the case, then it was
 *     decided to <strong>not</strong> include a version of this tag in the
 *     Struts-EL library. The tags that were "ported" to Struts-EL were deemed to
 *     have functionality which the JSTL could not directly cover.
 * </p>
 *
 * <p>
 *     The rest of this package description will briefly review the Struts tags
 *     which were <strong>not</strong> ported to the Struts-EL library (or which
 *     were "on the bubble", and why. The detailed package descriptions for each
 *     section of the library (html-el, logic-el, and bean-el) will demonstrate
 *     in
 *     detail the usage of the Struts-EL tags, focusing on attribute value
 *     evaluation issues. Details of the operation of these tags in the Struts
 *     framework can be learned from the package descriptions and documentation
 *     for
 *     the base Struts library.
 * </p>
 * <hr>
 *
 * <h3><a id="doc.Html">HTML-EL tag library</a></h3>
 * This is a short section. The JSTL does not include any functionality for
 * generating HTML elements, thus every tag in the "struts-html" tag library was
 * ported to the "struts-html-el" tag library.
 * <hr>
 *
 * <h3><a id="doc.Logic">Logic-EL tag library</a></h3>
 *
 * <p>
 *     The following table lists the "struts-logic" tags which were not ported to
 *     the "struts-logic-el" tag library, including which JSTL elements or
 *     features
 *     will provide that functionality. Examples after the table will demonstrate
 *     these.
 * </p>
 * <table style="border:1px solid black">
 *     <tr>
 *         <th style="border:1px solid black">
 *             Struts-Logic tag
 *         </th>
 *         <th style="border:1px solid black">
 *             JSTL tags or feature
 *         </th>
 *     </tr>
 *     <tr>
 *         <td style="border:1px solid black">
 *             empty
 *         </td>
 *         <td style="border:1px solid black">
 *             c:if, c:when, EL
 *         </td>
 *     </tr>
 *     <tr>
 *         <td style="border:1px solid black">
 *             equal
 *         </td>
 *         <td  style="border:1px solid black">
 *             c:if, c:when, EL
 *         </td>
 *     </tr>
 *     <tr>
 *         <td  style="border:1px solid black">
 *             greaterEqual
 *         </td>
 *         <td  style="border:1px solid black">
 *             c:if, c:when, EL
 *         </td>
 *     </tr>
 *     <tr>
 *         <td  style="border:1px solid black">
 *             greaterThan
 *         </td>
 *         <td  style="border:1px solid black">
 *             c:if, c:when, EL
 *         </td>
 *     </tr>
 *     <tr>
 *         <td  style="border:1px solid black">
 *             lessEqual
 *         </td>
 *         <td  style="border:1px solid black">
 *             c:if, c:when, EL
 *         </td>
 *     </tr>
 *     <tr>
 *         <td  style="border:1px solid black">
 *             lessThan
 *         </td>
 *         <td  style="border:1px solid black">
 *             c:if, c:when, EL
 *         </td>
 *     </tr>
 *     <tr>
 *         <td  style="border:1px solid black">
 *             notEmpty
 *         </td>
 *         <td  style="border:1px solid black">
 *             c:if, c:when, EL
 *         </td>
 *     </tr>
 *     <tr>
 *         <td  style="border:1px solid black">
 *             notEqual
 *         </td>
 *         <td  style="border:1px solid black">
 *             c:if, c:when, EL
 *         </td>
 *     </tr>
 * </table>
 * <p>
 *     The following are some examples of "Before" and "After", where the first
 *     example is pure Struts usage, and the second example will be pure JSTL
 *     usage, not involving Struts-EL at all.
 * </p>
 *
 * <p><i>Struts Example:</i></p>
 * <pre>
 *    &lt;logic:empty name="foo" property="stuff"&gt;
 *    Some stuff
 *    &lt;/logic:empty&gt;</pre>
 *
 * <p><i>JSTL Version:</i></p>
 * <pre>
 *    &lt;c:if test="${empty foo.stuff}"&gt;
 *    Some stuff
 *    &lt;/c:if&gt;</pre>
 *
 * <p><i>Struts Example:</i></p>
 * <pre>
 *    &lt;logic:notEmpty name="foo" property="stuff"&gt;
 *    Some stuff
 *    &lt;/logic:notEmpty&gt;</pre>
 *
 * <p><i>JSTL Version:</i></p>
 * <pre>
 *    &lt;c:if test="${!empty foo.stuff}"&gt;
 *    Some stuff
 *    &lt;/c:if&gt;</pre>
 *
 * <p><i>Struts Example:</i></p>
 * <pre>
 *    &lt;logic:equal name="foo" property="stuff" value="&lt;%=thing.getStuff()%&gt;"&gt;
 *    Some stuff
 *    &lt;/logic:equal&gt;</pre>
 *
 * <p><i>JSTL Version:</i></p>
 * <pre>
 *    &lt;%-- Assumes "thing" is a scoped variable --%&gt;
 *    &lt;c:if test="${foo.stuff eq thing.stuff}"&gt;
 *    Some stuff
 *    &lt;/c:if&gt;</pre>
 *
 * <p><i>Struts Example:</i></p>
 * <pre>
 *    &lt;logic:greaterThan name="foo" property="stuff" value="&lt;%=thing.getStuff()%&gt;"&gt;
 *    Some stuff
 *    &lt;/logic:empty&gt;</pre>
 *
 * <p><i>JSTL Version:</i></p>
 * <pre>
 *    &lt;%-- Assumes "thing" is a scoped variable --%&gt;
 *    &lt;c:if test="${foo.stuff ge thing.stuff}"&gt;
 *    Some stuff
 *    &lt;/c:if&gt;</pre>
 *
 * <p><i>Struts Example:</i></p>
 * <pre>
 *    &lt;logic:present cookie="shoppingCart"&gt;
 *    Some stuff
 *    &lt;/logic:present&gt;</pre>
 *
 * <p><i>JSTL Version:</i></p>
 * <pre>
 *    &lt;c:if test='${!empty cookie["shoppingCart"]}"&gt;
 *    Some stuff
 *    &lt;/c:if&gt;</pre>
 *
 * <p><i>Struts Example:</i></p>
 * <pre>
 *    &lt;logic:present header="User-Agent"&gt;
 *    Some stuff
 *    &lt;/logic:present&gt;</pre>
 *
 * <p><i>JSTL Version:</i></p>
 * <pre>
 *    &lt;c:if test='${!empty header["User-Agent"]}"&gt;
 *    Some stuff
 *    &lt;/c:if&gt;</pre>
 * <hr>
 *
 * <h3><a id="doc.Bean">Bean-EL tag library</a></h3>
 *
 * <p>
 *     The following table lists the "struts-bean" tags which were not ported to
 *     the "struts-bean-el" tag library, including which JSTL elements or
 *     features
 *     will provide that functionality. Examples after the table will demonstrate
 *     these.
 * </p>
 * <table style="border:1px solid black">
 *     <tr>
 *         <th style="border:1px solid black">
 *             Struts-Bean tag
 *         </th>
 *         <th style="border:1px solid black">
 *             JSTL tags or feature
 *         </th>
 *     </tr>
 *     <tr>
 *         <td  style="border:1px solid black">
 *             cookie
 *         </td>
 *         <td  style="border:1px solid black">
 *             c:set, EL
 *         </td>
 *     </tr>
 *     <tr>
 *         <td  style="border:1px solid black">
 *             define
 *         </td>
 *         <td  style="border:1px solid black">
 *             c:set, EL
 *         </td>
 *     </tr>
 *     <tr>
 *         <td  style="border:1px solid black">
 *             header
 *         </td>
 *         <td  style="border:1px solid black">
 *             c:set, EL
 *         </td>
 *     </tr>
 *     <tr>
 *         <td  style="border:1px solid black">
 *             include
 *         </td>
 *         <td  style="border:1px solid black">
 *             c:import
 *         </td>
 *     </tr>
 *     <tr>
 *         <td  style="border:1px solid black">
 *             parameter
 *         </td>
 *         <td  style="border:1px solid black">
 *             c:set, EL
 *         </td>
 *     </tr>
 *     <tr>
 *         <td  style="border:1px solid black">
 *             write
 *         </td>
 *         <td  style="border:1px solid black">
 *             c:out
 *         </td>
 *     </tr>
 * </table>
 * <p>
 *     Note that the "bean:resource" Struts tag is similar, at least
 *     superficially,
 *     to the functionality of the "c:import" tag, but "bean:resource"
 *     <strong>was</strong> ported to the Struts-EL library. This is because
 *     resources requested through the "c:import" tag may be processed through a
 *     mapped servlet, preventing direct access to the resource. The
 *     "bean:resource" tag allows direct access to the resource, without an
 *     intervening servlet. For instance, if it is desired to obtain the raw text
 *     of a JSP page, using "c:import" will not work, because the JSP page will
 *     be
 *     processed by the JSP servlet. However, using "bean:resource" will retrieve
 *     just the text of the JSP page, if that is desired.
 * </p>
 *
 * <p>
 *     Also note that some functionality of the "bean:include" tag, which was not
 *     ported to the Struts-EL library, is not available in the JSTL. This
 *     includes the ability to specify the name of a Struts forward, and the
 *     ability to include the current transaction control token. These features
 *     will be addressed in a future minor release of Struts-EL.
 * </p>
 *
 * <p>
 *     The following are some examples of "Before" and "After", where the first
 *     example is pure Struts usage, and the second example will be pure JSTL
 *     usage, not involving Struts-EL at all.
 * </p>
 *
 * <p><i>Struts Example:</i></p>
 * <pre>
 *    &lt;bean:cookie id="cookieVal" name="stuff"/&gt;</pre>
 *
 * <p><i>JSTL Version:</i></p>
 * <pre>
 *    &lt;c:set var="cookieVal" value='${cookie["stuff"]}'/&gt;</pre>
 *
 * <p><i>Struts Example:</i></p>
 * <pre>
 *    &lt;bean:define id="thing" name="foo" property="stuff"/&gt;</pre>
 *
 * <p><i>JSTL Version:</i></p>
 * <pre>
 *    &lt;c:set var="thing" value="${foo.stuff}"/&gt;</pre>
 *
 * <p><i>Struts Example:</i></p>
 * <pre>
 *    &lt;bean:header id="headerVal" name="stuff"/&gt;</pre>
 *
 * <p><i>JSTL Version:</i></p>
 * <pre>
 *    &lt;c:set var="headerVal" value='${header["stuff"]}'/&gt;</pre>
 *
 * <p><i>Struts Example:</i></p>
 * <pre>
 *    &lt;bean:include id="stuffOut"
 *    href="https://somewhere.com/stuff.jsp"/&gt;</pre>
 *
 * <p><i>JSTL Version:</i></p>
 * <pre>
 *    &lt;c:import var="stuffOut"
 *    value="https://somewhere.com/stuff.jsp"/&gt;</pre>
 *
 * <p><i>Struts Example:</i></p>
 * <pre>
 *    &lt;bean:parameter id="parameterVal" name="stuff"/&gt;</pre>
 *
 * <p><i>JSTL Version:</i></p>
 * <pre>
 *    &lt;c:set var="parameterVal" value='${param["stuff"]}'/&gt;</pre>
 */
package org.apache.strutsel.taglib;