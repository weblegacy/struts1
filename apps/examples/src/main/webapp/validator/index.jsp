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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.struts.validator.ValidatorPlugIn" session="true" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:html>
<head>
<title><bean:message key="index.title"/></title>
<html:base/>
</head>
<body bgcolor="white">

<logic:notPresent name="<%= Globals.MESSAGES_KEY %>" >
  <font color="red">
    ERROR:  Application resources not loaded -- check servlet container
    logs for error messages.
  </font>
</logic:notPresent>

<%-- :TODO: Need code to do this with moudles
<logic:notPresent name="<%= ValidatorPlugIn.VALIDATOR_KEY %>" >
  <font color="red">
    ERROR:  Validator resources not loaded -- check Commons Logging
    logs for error messages.
  </font>
</logic:notPresent>
--%>

<h3><bean:message key="registrationForm.title"/></h3>
<ul>
   <li><html:link action="/registration"><bean:message key="registrationForm.title"/></html:link></li>
   <!-- :TODO: Should have a non-JaveScript message-by-field example -->
   <li>
      <html:link action="/jsRegistration"><bean:message key="jsRegistrationForm.title"/></html:link> -
      <bean:message key="jsRegistrationForm.description"/>
   </li>
   <li>
      <html:link action="/multiRegistration"><bean:message key="multiRegistrationForm.title"/></html:link> -
      <bean:message key="multiRegistrationForm.description"/>
   </li>
</ul>

<h3><bean:message key="typeForm.title"/></h3>
<ul>
   <li>
      <html:link action="/type"><bean:message key="typeForm.title"/></html:link> -
      <bean:message key="typeForm.description"/>
   </li>
   <li>
      <html:link action="/editJsType"><bean:message key="jsTypeForm.title"/></html:link> -
      <bean:message key="jsTypeForm.description"/>
   </li>
</ul>

<h3><bean:message key="standard.title" bundle="i18nExample"/></h3>
<ul>
   <li>
      <html:link action="/viewI18nExample"><bean:message key="standard.title" bundle="i18nExample"/></html:link> -
      <bean:message key="standard.desc" bundle="i18nExample"/>
   </li>
   <li>
      <html:link action="/viewJsI18nExample"><bean:message key="javascript.title" bundle="i18nExample"/></html:link> -
      <bean:message key="javascript.desc" bundle="i18nExample"/>
   </li>
</ul>

<h3><bean:message key="bundles.title"/></h3>
<ul>
   <li>
      <html:link action="/viewBundleExamples"><bean:message key="bundlesExamples.title"/></html:link> -
      <bean:message key="bundlesExamples.description"/>
   </li>
   <li>
      <html:link action="/viewJsBundleExamples"><bean:message key="javascript.bundlesExamples.title"/></html:link> -
      <bean:message key="javascript.bundlesExamples.description"/>
   </li>
</ul>

<h3><bean:message key="validWhen.title"/></h3>
<ul>
   <li>
      <html:link action="/viewValidWhenExamples"><bean:message key="validWhenExamples.title"/></html:link> -
      <bean:message key="validWhenExamples.description"/>
   </li>
</ul>

<h3>Change Language | Changez Le Langage</h3>
<ul>
   <li><html:link action="/locale?language=en">English | Anglais</html:link></li>
   <li>
      <html:link action="/locale?language=fr">French | Francais</html:link> -
      <bean:message key="localeForm.fr"/>
   </li>
   <li>
      <html:link action="/locale?language=fr&country=CA">French Canadian | Francais Canadien</html:link> -
      <bean:message key="localeForm.frCA"/>
   </li>
   <li>
      <html:link action="/locale?language=ja" useLocalEncoding="true">Japanese | Japonais</html:link> -
      <bean:message key="localeForm.ja"/>
   </li>
</ul>

<p>&nbsp;</p>

<html:img page="/struts-power.gif" altKey="index.powered"/>

</body>
</html:html>
