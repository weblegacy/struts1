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

<html:html xhtml="true" lang="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Validator Example using DynaValidatorActionForm</title>
<html:base />
<link rel="stylesheet" type="text/css" href="../../css/example.css" />
</head>
<body>
<html:link page="/jsp/validator/source.jsp">
  <img src="../../images/code.gif" width="24" height="24" alt="View Source" class="icon" />
</html:link>
<a href="../../index.jsp"><img src="../../images/return.gif" height="24" width="24" alt="Return to examples page" class="icon" /></a>
<h1>Validator Example using DynaValidatorActionForm</h1>
<hr noshade="noshade"/>
<p>Enter information into the fields below. Your entries will be displayed when you Submit the form.</p>
<p>* = required field. Other fields must be blank or in the required format.</p>
<hr noshade="noshade" />

<html:errors/>
<html:form action="validatorAction" method="get">

    <table>
    <tr><td>Byte:</td><td><html:text property="byteValue" /> -128 .. 127</td></tr>
    <tr><td>Short:</td><td><html:text property="shortValue" /> -32768 .. 32767</td></tr>
    <tr><td>Integer:</td><td><html:text property="integerValue" /> -2147483648 .. 2147483647</td></tr>
    <tr><td>Long:</td><td><html:text property="longValue" /> -9223372036854775808 .. 9223372036854775807</td></tr>
    <tr><td>Float:</td><td><html:text property="floatValue" /> 1.4E-45 .. 3.4028235E38</td></tr>
    <tr><td>Double:</td><td><html:text property="doubleValue" /> 4.9E-324 .. 1.7976931348623157E308</td></tr>
    <tr><td>Credit Card:</td><td><html:text property="creditCard" /> e.g. 4444333322221111 (no spaces) </td></tr>
    <tr><td>Date:</td><td><html:text property="date" /> mm/dd/yyyy</td></tr>
    <tr><td>Email:</td><td><html:text property="email" /></td></tr>
    <tr><td>Mask:</td><td><html:text property="mask" /> US zip code e.g. 90210</td></tr>
    <tr><td>Min Length:</td><td><html:text property="min" /> (minimum 5 characters)</td></tr>
    <tr><td>Max Length:</td><td><html:text property="max" /> (maximum 10 characters)</td></tr>
    <tr><td>Range:</td><td><html:text property="range" /> 100 .. 1000</td></tr>
    <tr><td>* Required:</td><td><html:text property="required" /></td></tr>
    </table>
    <p>These two fields must contain the same value:</p>
    <table>
    <tr><td>* Password:</td><td><html:password property="password" redisplay="false"/> (minimum 5 characters)</td></tr>
    <tr><td>* Password confirmation:</td><td><html:password property="password2"  redisplay="false"/></td></tr>
    </table>

    <hr noshade="noshade" />
    <p>
        <html:submit>
            <bean:message key="button.submit" />
        </html:submit>
        <html:cancel/>
    </p>
</html:form>

</body>
</html:html>