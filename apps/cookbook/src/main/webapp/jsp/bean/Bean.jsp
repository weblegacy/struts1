<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:html xhtml="true" lang="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Bean tag examples</title>
<html:base />
<link rel="stylesheet" type="text/css" href="../../css/example.css" />
</head>
<body>
<html:link page="/jsp/bean/source.jsp">
    <img src="../../images/code.gif" width="24" height="24" alt="View Source" class="icon" />
</html:link>
<a href="../../index.jsp"><img src="../../images/return.gif" height="24" width="24" alt="Return to examples page" class="icon" /></a>
<h1>Bean tag examples</h1>
<hr noshade="noshade"/>

<h2>&lt;bean:write&gt;</h2>

<h3>1. Display bean properties</h3>

<table class="result">
  <tr><td class="label">Boolean:</td>
  <td><bean:write name="example" property="booleanValue" /></td></tr>
  <tr><td class="label">Double:</td>
  <td><bean:write name="example" property="doubleValue" /></td></tr>
  <tr><td class="label">Float:</td>
  <td><bean:write name="example" property="floatValue" /></td></tr>
  <tr><td class="label">Integer:</td>
  <td><bean:write name="example" property="intValue" /></td></tr>
  <tr><td class="label">Long integer:</td>
  <td><bean:write name="example" property="longValue" /></td></tr>
  <tr><td class="label">Short integer:</td>
  <td><bean:write name="example" property="shortValue" /></td></tr>
  <tr><td class="label">String:</td>
  <td><bean:write name="example" property="stringValue" /></td></tr>
  <tr><td class="label">Date:</td>
  <td><bean:write name="example" property="dateValue" /></td></tr>
</table>


<h3>2. Formatting output</h3>
<table class="result">
  <tr><td class="label">Formatted number (using format):</td>
  <td><bean:write name="example" property="doubleValue" format="$0,000.00"/></td></tr>
  <tr><td class="label">Formatted number (using formatKey):</td>
  <td><bean:write name="example" property="floatValue" formatKey="format.currency"/></td></tr>
  <tr><td class="label">Formatted date (using format):</td>
  <td><bean:write name="example" property="dateValue" format="MMM d yyyy '@' HH:mm"/></td></tr>
  <tr><td class="label">Formatted date (using formatKey):</td>
  <td><bean:write name="example" property="dateValue" formatKey="format.date"/></td></tr>
</table>

<h3>3. Display values from collections, arrays and nested beans</h3>
<table class="result">
  <tr><td class="label">List entry:</td>
  <td><bean:write name="example" property="list[2]" /></td></tr>
  <tr><td class="label">Array entry:</td>
  <td><bean:write name="example" property="array[3]" /></td></tr>
  <tr><td class="label">Nested property:</td>
  <td><bean:write name="example" property="nested.stringValue"/></td></tr>
</table>


<h3>4. Filtering HTML tags in output</h3>
<table class="result">
  <tr><td class="label">Filtered text:</td>
  <td><bean:write name="example" property="html"/></td></tr>
  <tr><td class="label">Unfiltered text:</td>
  <td><bean:write name="example" property="html" filter="false"/></td></tr>
</table>


<h2>&lt;bean:define&gt;</h2>
<bean:define id="define1" value="This is a test string"/>
<bean:define name="example" property="nested" id="define2" />
<table class="result">
  <tr><td class="label">String value:</td>
  <td><bean:write name="define1" /></td></tr>
  <tr><td class="label">Bean:</td>
  <td><bean:write name="define2" property="stringValue" /></td></tr>
</table>


<h2>&lt;bean:header&gt;</h2>
<bean:header name="Accept-Encoding" id="encodings" />
<table class="result">
  <tr><td class="label">Accept-Encodings:</td>
  <td><bean:write name="encodings" /></td></tr>
</table>

<%-- Not working in webappcabaret
<h2>&lt;bean:include&gt;</h2>
<bean:include page="/jsp/bean/include.inc" id="include"/>
<div class="result">
<bean:write name="include" filter="false"/>
</div>
--%>

<h2>&lt;bean:message&gt;</h2>
<div class="result">
<p><bean:message key="message.example.simple"/></p>
<p><bean:message key="message.example.replaceable" arg0="a slightly more complex" arg1="2 replaceable" /></p>
<p><bean:message key="message.example.replaceable" arg0="the same template" arg1="different" /></p>
</div>

<h2>&lt;bean:parameters&gt;</h2>
<bean:parameter name="param1" id="p1" value=""/>
<bean:parameter name="param2" id="p2" value=""/>
<bean:parameter name="param3" id="p3" value="DEFAULT"/>
<table class="result">
  <tr><td class="label">Parameter 1:</td>
  <td><bean:write name="p1" /></td></tr>
  <tr><td class="label">Parameter 2:</td>
  <td><bean:write name="p2" /></td></tr>
  <tr><td class="label">Parameter 3:</td>
  <td><bean:write name="p3" /></td></tr>
</table>


<h2>&lt;bean:size&gt;</h2>
<bean:size name="example" property="list" id="listSize" />
<table class="result">
  <tr><td class="label">List size:</td>
  <td><bean:write name="listSize" /></td></tr>
</table>


<h2>&lt;bean:cookie&gt;</h2>
<p>Display the properties of our current session ID cookie (if there is one):</p>
<bean:cookie id="sess" name="JSESSIONID" value="JSESSIONID-IS-UNDEFINED"/>
<table class="result">
  <tr><th>Property Name</th><th>Value</th></tr>
  <tr><td class="label">comment</td>
  <td><bean:write name="sess" property="comment"/></td></tr>
  <tr><td class="label">domain</td>
  <td><bean:write name="sess" property="domain"/></td></tr>
  <tr><td class="label">maxAge</td>
  <td><bean:write name="sess" property="maxAge"/></td></tr>
<%-- Fails on Tomcat 3.3.1a
  <tr><td class="label">name</td>
  <td><bean:write name="sess" property="name"/></td></tr>
--%>
  <tr><td class="label">path</td>
  <td><bean:write name="sess" property="path"/></td></tr>
  <tr><td class="label">secure</td>
  <td><bean:write name="sess" property="secure"/></td></tr>
  <tr><td class="label">value</td>
  <td><bean:write name="sess" property="value"/></td></tr>
  <tr><td class="label">version</td>
  <td><bean:write name="sess" property="version"/></td></tr>
</table>

<p>Display the properties of an undefined cookie that was given the default value UNKNOWN_VALUE:</p>
<bean:cookie id="dummy" name="UNKNOWN_COOKIE" value="UNKNOWN_VALUE"/>
<table class="result">
  <tr><th>Property Name</th><th>Value</th></tr>
  <tr><td class="label">comment</td>
  <td><bean:write name="dummy" property="comment"/></td></tr>
  <tr><td class="label">domain</td>
  <td><bean:write name="dummy" property="domain"/></td></tr>
  <tr><td class="label">maxAge</td>
  <td><bean:write name="dummy" property="maxAge"/></td></tr>
<%-- Fails on Tomcat 3.3.1a
  <tr><td class="label">name</td>
  <td><bean:write name="dummy" property="name"/></td></tr>
--%>
  <tr><td class="label">path</td>
  <td><bean:write name="dummy" property="path"/></td></tr>
  <tr><td class="label">secure</td>
  <td><bean:write name="dummy" property="secure"/></td></tr>
  <tr><td class="label">value</td>
  <td><bean:write name="dummy" property="value"/></td></tr>
  <tr><td class="label">version</td>
  <td><bean:write name="dummy" property="version"/></td></tr>
</table>

</body>
</html:html>