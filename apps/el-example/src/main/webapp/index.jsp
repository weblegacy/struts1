<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%--
    Licensed to the Apache Software Foundation (ASF) under one or more
    contributor license agreements.  See the NOTICE file distributed with
    this work for additional information regarding copyright ownership.
    The ASF licenses this file to You under the Apache License, Version 2.0
    (the "License"); you may not use this file except in compliance with
    the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
--%>
<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el" %>
<html>
<head>
    <title>Struts-EL Test Application</title>
</head>

<body bgcolor="white">

<div align="center">
    <h1>Struts-EL Test Pages</h1>
</div>

<h3>BEAN Tags</h3>
<ul>
    <li><a href="bean-include.jsp">&lt;bean:include&gt;</a>[<a
            href="showSource.jsp?path=/bean-include.jsp">Source</a>]</li>
    <li><a href="bean-resource.jsp">&lt;bean:resource&gt;</a>[<a
            href="showSource.jsp?path=/bean-resource.jsp">Source</a>]</li>
    <li><a href="bean-size.jsp">&lt;bean:size&gt;</a>[<a
            href="showSource.jsp?path=/bean-size.jsp">Source</a>]</li>
</ul>

<h3>HTML Tags</h3>
<ul>
    <li><a href="html-link.jsp">&lt;html:link&gt;</a>[<a
            href="showSource.jsp?path=/html-link.jsp">Source</a>]</li>
    <li><a href="html-multibox.jsp">&lt;html:multibox&gt;</a>[<a
            href="showSource.jsp?path=/html-multibox.jsp">Source</a>]</li>
    <li><a href="html-radio.jsp">&lt;html:radio&gt;</a>[<a
            href="showSource.jsp?path=/html-radio.jsp">Source</a>]</li>
    <li><a href="html-button.jsp">&lt;html:button&gt;</a>[<a
            href="showSource.jsp?path=/html-button.jsp">Source</a>]</li>
    <li><a href="html-file.jsp">&lt;html:file&gt;</a>[<a
            href="showSource.jsp?path=/html-file.jsp">Source</a>]</li>
    <li><a href="html-frame.jsp">&lt;html:frame&gt;</a>[<a
            href="showSource.jsp?path=/html-frame.jsp">Source</a>]</li>
    <li><a href="html-input.jsp">&lt;html:input&gt;</a>[<a
            href="showSource.jsp?path=/html-input.jsp">Source</a>]</li>
    <li><a href="html-select.jsp">&lt;html:select&gt;</a>[<a
            href="showSource.jsp?path=/html-select.jsp">Source</a>]</li>
    <li><a href="html-setters.jsp">Scalar Setters</a>[<a
            href="showSource.jsp?path=/html-setters.jsp">Source</a>]</li>
    <li><a href="html-indexed.jsp">Indexed Tags</a>[<a
            href="showSource.jsp?path=/html-indexed.jsp">Source</a>]</li>
</ul>

<h3>LOGIC Tags</h3>
<ul>
    <li><a href="logic-iterate.jsp">Iterate Tag</a>[<a
            href="showSource.jsp?path=/logic-iterate.jsp">Source</a>]</li>
    <li><a href="logic-match.jsp?param1=value1">Match Tags</a>[<a
            href="showSource.jsp?path=/logic-match.jsp">Source</a>]</li>
    <li><a href="logic-present.jsp?param1=value1">Presence Tags</a>[<a
            href="showSource.jsp?path=/logic-present.jsp">Source</a>]</li>
    <li><a href="logic-redirect.jsp">&lt;logic:redirect&gt;</a>[<a
            href="showSource.jsp?path=/logic-redirect.jsp">Source</a>]</li>
</ul>

<h2>Replacements for Struts tags not ported to Struts-EL</h2>

<h3>BEAN Tags</h3>
<ul>
    <li><a href="bean-cookie.jsp">&lt;bean:cookie&gt;</a>[<a
            href="showSource.jsp?path=/bean-cookie.jsp">Source</a>]</li>
    <li><a href="bean-define.jsp">&lt;bean:define&gt;</a>[<a
            href="showSource.jsp?path=/bean-define.jsp">Source</a>]</li>
    <li><a href="bean-header.jsp">&lt;bean:header&gt;</a>[<a
            href="showSource.jsp?path=/bean-header.jsp">Source</a>]</li>
    <li><a href="bean-parameter.jsp?param1=value1&param2=value2">&lt;bean:parameter&gt;</a>[<a
            href="showSource.jsp?path=/bean-parameter.jsp">Source</a>]</li>
    <li><a href="bean-write.jsp">&lt;bean:write&gt;</a>[<a
            href="showSource.jsp?path=/bean-write.jsp">Source</a>]</li>
</ul>

<h3>LOGIC Tags</h3>
<ul>
    <li><a href="logic-compare.jsp">Comparison Tags</a>[<a
            href="showSource.jsp?path=/logic-compare.jsp">Source</a>]</li>
    <li><a href="logic-empty.jsp">Emptiness Tags</a>[<a
            href="showSource.jsp?path=/logic-empty.jsp">Source</a>]</li>
</ul>

<h2>Other Testable Features</h2>
<ul>
    <li>
        <html-el:link page="/bean-dyna.do">
            DynaActionForm References in EL[<a
            href="showSource.jsp?path=/bean-dyna.jsp">Source</a>]
        </html-el:link>
    </li>
</ul>
</body>
</html>
