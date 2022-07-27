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
<title>Simple form using DynaActionForm</title>
<html:base/>
<link rel="stylesheet" type="text/css" href="../../css/example.css" />
</head>
<body>
<html:link page="/jsp/dyna/source.jsp">
  <img src="../../images/code.gif" width="24" height="24" alt="View Source" class="icon" />
</html:link>
<a href="../../index.jsp"><img src="../../images/return.gif" height="24" width="24" alt="Return to examples page" class="icon" /></a>
<h1>Simple form using DynaActionForm</h1>
<hr noshade="noshade"/>

<p>Enter information into the fields below. Your entries will be displayed when you Submit the form.<br />
This is just to demonstrate the Struts html tags. The information that you enter is discarded.</p>
<p>There is no validation in this example. See the <html:link action="/prepareValidator">validator example</html:link> for use of
  validation with dynaforms.</p>
<hr noshade="noshade" />
<html:errors/>
<html:form action="/processDyna">
    <p>What's your first name?:<br/>
<html:text property="name" size="40" maxlength="50"/></p>
    <p> Enter a secret word or phrase:<br/>
<html:password property="secret" size="40" maxlength="50"/></p>
    <p>What is your favorite color?:<br/>
      <html:select property="color">
        <html:option value="red">Red</html:option>
        <html:option value="green">Green</html:option>
        <html:option value="blue">Blue</html:option>
      </html:select>
    </p>
    <p><html:checkbox property="confirm"/>Is that really your favorite color?</p>
    <p>How much do you like your chosen color?:<br />
        <html:radio property="rating" value="1">Actually, I hate it.</html:radio><br />
        <html:radio property="rating" value="2">Not so much.</html:radio><br />
        <html:radio property="rating" value="3">I'm indifferent</html:radio><br />
        <html:radio property="rating" value="4">It's pretty neat</html:radio><br />
        <html:radio property="rating" value="5">I painted my whole house with it.</html:radio>
    </p>
    <p>Enter a message (you may use html tags):<br />
        <html:textarea property="message" cols="40" rows="6"/>
    </p>
    <html:hidden property="hidden" value="Sssh! It's a secret. Nobody knows I'm here."/>
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
