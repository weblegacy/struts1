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
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:html xhtml="true" lang="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Select / Options example results</title>
<html:base/>
<link rel="stylesheet" type="text/css" href="../../css/example.css" />
</head>
<body>
<html:link page="/jsp/options/source.jsp">
       <img src="../../images/code.gif" width="24" height="24" alt="View Source" class="icon" />
</html:link>
<a href="../../index.jsp"><img src="../../images/return.gif" height="24" width="24" alt="Return to examples page" class="icon" /></a>
<h1>Select / Options example results</h1>
<hr noshade="noshade"/>

<p>Fruit 1: <bean:write name="optionsForm" property="fruit1" /></p>
<p>Fruit 2: <bean:write name="optionsForm" property="fruit2" /></p>
<p>Fruit 3:
<logic:iterate name="optionsForm" property="fruit3" id="fruit">
    <bean:write name="fruit" />
</logic:iterate>
</p>

<p>Color 1: <bean:write name="optionsForm" property="color1" /></p>
<p>Color 2: <bean:write name="optionsForm" property="color2" /></p>
<p>Color 3: <bean:write name="optionsForm" property="color3" /></p>

<p>Day 1: <bean:write name="optionsForm" property="day1" /></p>
<p>Day 2: <bean:write name="optionsForm" property="day2" /></p>

<p>Book 1: <bean:write name="optionsForm" property="book1" /></p>
<p>Book 2: <bean:write name="optionsForm" property="book2" /></p>

<p>Animal 1: <bean:write name="optionsForm" property="animal1" /></p>
<p>Animal 2: <bean:write name="optionsForm" property="animal2" /></p>

</body>
</html:html>