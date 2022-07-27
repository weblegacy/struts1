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
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="org.apache.struts.action.ActionMessages" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:html xhtml="true" lang="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Logic Tags Example</title>
<html:base />
<link rel="stylesheet" type="text/css" href="../../css/example.css" />
</head>
<body>
<html:link page="/jsp/logic/source.jsp">
  <img src="../../images/code.gif" width="24" height="24" alt="View Source" class="icon" />
</html:link>
<a href="../../index.jsp"><img src="../../images/return.gif" height="24" width="24" alt="Return to examples page" class="icon" /></a>
<h1>Logic Tags Example</h1>
<hr noshade="noshade"/>

<h2>Present / Not Present</h2>

<h3>Cookie</h3>
<logic:present cookie="JSESSIONID">
    <p>Session cookie is present.</p>
</logic:present>
<logic:notPresent cookie="UNKNOWN">
    <p>UNKNOWN cookie is not present.</p>
</logic:notPresent>

<h3>Parameter</h3>
<logic:present parameter="param">
    <bean:parameter name="param" id="test"/>
    <p><bean:write name="test"/></p>
</logic:present>
<logic:notPresent parameter="param">
    <p>Parameter 'param' not present.
        <html:link action="/prepareLogic?param=The parameter is present">
        Redisplay page with parameter present.
      </html:link>
    </p>
</logic:notPresent>

<h3>Bean</h3>
<logic:present name="testBean">
    <p>'testBean' is present.</p>
</logic:present>
<logic:notPresent name="anotherTestBean">
    <p>'anotherTestBean' is not present.</p>
</logic:notPresent>

<logic:present name="testBean" property="fred">
    <p>'fred' property is present on 'testBean'</p>
</logic:present>
<logic:notPresent name="testBean" property="fred">
    <p>'fred' property is not present on 'testBean'</p>
</logic:notPresent>
<logic:present name="testBean" property="stringValue">
    <p>'stringValue' property is present on 'testBean'</p>
</logic:present>


<h2>Empty / Not Empty</h2>
<logic:present name="items">
    <p>'items' was found.</p>
</logic:present>
<logic:empty name="items">
    <p>'items' is empty</p>
</logic:empty>
<logic:notEmpty name="items">
    <p>'items' is not empty</p>
<%--    <bean:size collection="items" id="itemsSize"/>
    <p>Items has <bean:write name="itemsSize" /> items.</p>
--%>
</logic:notEmpty>


<h2>Comparison tags</h2>
<logic:equal name="intValue" value="7">
    <p>intValue == 7</p>
</logic:equal>
<logic:greaterEqual name="intValue" value="7">
    <p>intValue &gt;= 7</p>
</logic:greaterEqual>
<logic:greaterEqual name="intValue" value="6">
    <p>intValue &gt;= 6</p>
</logic:greaterEqual>
<logic:greaterThan name="intValue" value="6">
    <p>intValue &gt; 6</p>
</logic:greaterThan>

<logic:lessEqual name="intValue" value="7">
    <p>intValue &lt;= 7</p>
</logic:lessEqual>
<logic:lessEqual name="intValue" value="8">
    <p>intValue &lt;= 8</p>
</logic:lessEqual>
<logic:lessThan name="intValue" value="8">
    <p>intValue &lt; 8</p>
</logic:lessThan>

<logic:match name="stringValue" value="world">
    <p>stringValue matches 'world'</p>
</logic:match>
<logic:notMatch name="stringValue" value="earth">
    <p>stringValue does not match 'earth'</p>
</logic:notMatch>
<logic:notMatch name="stringValue" value="world">
    <p>stringValue does not match 'world'</p>
</logic:notMatch>


<h2>Iteration</h2>
<h4>Book list:</h4>
<ol>
<logic:iterate name="books" id="book" indexId="index">
    <li><bean:write name="book" property="title"/></li>
</logic:iterate>
</ol>
<h4>Book list (entries 3-5):</h4>
<ol start="3">
<logic:iterate name="books" id="book" offset="2" length="3">
    <li><bean:write name="book" property="title"/></li>
</logic:iterate>
</ol>


<h2>Checking for and displaying messages</h2>
<h3>Errors:</h3>

<logic:messagesPresent>
<p>Global errors:</p>
<ul>
 <html:messages id="error" property="<%=ActionErrors.GLOBAL_MESSAGE%>">
 <li><bean:write name="error"/></li>
 </html:messages>
</ul>
<p>Errors for 'test':</p>
<ul>
 <html:messages id="error" property="test">
 <li><bean:write name="error"/></li>
 </html:messages>
</ul>
</logic:messagesPresent>
<logic:messagesNotPresent>
    <p>There are no errors</p>
</logic:messagesNotPresent>


<h3>Messages:</h3>
<logic:messagesPresent message="true">
 <ul>
 <html:messages id="msg" message="true" property="<%=ActionMessages.GLOBAL_MESSAGE%>">
 <li><bean:write name="msg"/></li>
 </html:messages>
 </ul>
<p>Messages for 'test':</p>
<ul>
 <html:messages id="msg" property="test" message="true">
 <li><bean:write name="msg"/></li>
 </html:messages>
</ul>
</logic:messagesPresent>
<logic:messagesNotPresent message="true">
    <p>There are no messages</p>
</logic:messagesNotPresent>

</body>
</html:html>