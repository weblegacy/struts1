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
<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:html xhtml="true" lang="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Simple ActionForm Example</title>
<html:base/>
<link rel="stylesheet" type="text/css" href="../../css/example.css" />
</head>
<body>
<html:link page="/jsp/simple/source.jsp">
       <img src="../../images/code.gif" width="24" height="24" alt="View Source" class="icon" />
</html:link>
<a href="../../index.jsp"><img src="../../images/return.gif" height="24" width="24" alt="Return to examples page" class="icon" /></a>
<h1>Simple Form Results</h1>
<hr noshade="noshade"/>
<p>Hello <bean:write name="simpleForm" property="name" />,</p>
<p><strong>You told me a secret:</strong> <bean:write name="simpleForm" property="secret" /></p>
<p><strong>You said that your favorite color is:</strong> <bean:write name="simpleForm" property="color" /></p>

<logic:equal name="simpleForm" property="confirm" value="true">
    <p>You confirmed that this <em>is</em> your favorite color.</p>
</logic:equal>
<logic:notEqual name="simpleForm" property="confirm" value="true">
    <p>... but you lied. Shame on you!</p>
</logic:notEqual>

<p><strong>On scale of 1 to 5 you rated your color:</strong>
    <bean:write name="simpleForm" property="rating" />
</p>
<p><strong>This was lurking in a hidden field:</strong><br />
    <bean:write name="simpleForm" property="hidden" /></p>
<p><strong>You wrote this message:</strong></p>
<p><bean:write name="simpleForm" property="message" filter="false"/></p>
</body>
</html:html>