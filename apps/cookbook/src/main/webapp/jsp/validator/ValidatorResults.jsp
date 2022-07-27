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
<title>Validator Example Results</title>
<html:base/>
<link rel="stylesheet" type="text/css" href="../../css/example.css" />
</head>
<body>
<html:link page="/jsp/validator/source.jsp">
    <img src="../../images/code.gif" width="24" height="24" alt="View Source" class="icon" />
</html:link>
<a href="../../index.jsp"><img src="../../images/return.gif" height="24" width="24" alt="Return to examples page" class="icon" /></a>
<h1>Validator Example Results</h1>
<hr noshade="noshade"/>

<p><strong>Byte:</strong> <bean:write name="validatorForm" property="byteValue" /></p>
<p><strong>Short:</strong> <bean:write name="validatorForm" property="shortValue" /></p>
<p><strong>Integer:</strong> <bean:write name="validatorForm" property="integerValue" /></p>
<p><strong>Long:</strong> <bean:write name="validatorForm" property="longValue" /></p>
<p><strong>Float:</strong> <bean:write name="validatorForm" property="floatValue" /></p>
<p><strong>Double:</strong> <bean:write name="validatorForm" property="doubleValue" /></p>
<p><strong>Credit Card:</strong> <bean:write name="validatorForm" property="creditCard" /></p>
<p><strong>Date:</strong> <bean:write name="validatorForm" property="date" /></p>
<p><strong>Email:</strong> <bean:write name="validatorForm" property="email" /></p>
<p><strong>Mask:</strong> <bean:write name="validatorForm" property="mask" /></p>
<p><strong>Min Length:</strong> <bean:write name="validatorForm" property="min" /></p>
<p><strong>Max Length:</strong> <bean:write name="validatorForm" property="max" /></p>
<p><strong>Range:</strong> <bean:write name="validatorForm" property="range" /></p>
<p><strong>Required:</strong> <bean:write name="validatorForm" property="required" /></p>
<p><strong>Password:</strong> <bean:write name="validatorForm" property="password" /></p>

</body>
</html:html>