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
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean-el" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html-el:html>
<head>
    <title>Test Replacements for struts-bean:define Tag</title>
</head>

<body bgcolor="white">

<div align="center">
    <h1>Test Replacements for struts-bean:define Tag</h1>
</div>

<h3>Test 1 -- Direct Scalar Variable Defines</h3>

<jsp:useBean id="test1" scope="page"
             class="org.apache.struts.webapp.el.exercise.TestBean"/>

<c:set var="test1_boolean" value="${test1.booleanProperty}"/>
<c:set var="test1_double" value="${test1.doubleProperty}"/>
<c:set var="test1_float" value="${test1.floatProperty}"/>
<c:set var="test1_int" value="${test1.intProperty}"/>
<c:set var="test1_long" value="${test1.longProperty}"/>
<c:set var="test1_short" value="${test1.shortProperty}"/>
<c:set var="test1_string" value="${test1.stringProperty}"/>
<c:set var="test1_value" value="ABCDE"/>

<table border="1">
    <tr>
        <th>Data Type</th>
        <th>Value</th>
    </tr>
    <tr>
        <td>boolean</td>
        <td><c:out value="${test1_boolean}"/></td>
    </tr>
    <tr>
        <td>double</td>
        <td><c:out value="${test1_double}"/></td>
    </tr>
    <tr>
        <td>float</td>
        <td><c:out value="${test1_float}"/></td>
    </tr>
    <tr>
        <td>int</td>
        <td><c:out value="${test1_int}"/></td>
    </tr>
    <tr>
        <td>long</td>
        <td><c:out value="${test1_long}"/></td>
    </tr>
    <tr>
        <td>short</td>
        <td><c:out value="${test1_short}"/></td>
    </tr>
    <tr>
        <td>string</td>
        <td><c:out value="${test1_string}"/></td>
    </tr>
    <tr>
        <td>value</td>
        <td><c:out value="${test1_value}"/></td>
    </tr>
</table>


<h3>Test 2 -- Nested Scalar Variable Defines</h3>

<jsp:useBean id="test2" scope="page"
             class="org.apache.struts.webapp.el.exercise.TestBean"/>

<bean:define id="test2_boolean" name="test2"
             property="nested.booleanProperty"/>
<bean:define id="test2_double" name="test2" property="nested.doubleProperty"/>
<bean:define id="test2_float" name="test2" property="nested.floatProperty"/>
<bean:define id="test2_int" name="test2" property="nested.intProperty"/>
<bean:define id="test2_long" name="test2" property="nested.longProperty"/>
<bean:define id="test2_short" name="test2" property="nested.shortProperty"/>
<bean:define id="test2_string" name="test2" property="nested.stringProperty"/>

<c:set var="test2_boolean" value="${test2.nested.booleanProperty}"/>
<c:set var="test2_double" value="${test2.nested.doubleProperty}"/>
<c:set var="test2_float" value="${test2.nested.floatProperty}"/>
<c:set var="test2_int" value="${test2.nested.intProperty}"/>
<c:set var="test2_long" value="${test2.nested.longProperty}"/>
<c:set var="test2_short" value="${test2.nested.shortProperty}"/>
<c:set var="test2_string" value="${test2.nested.stringProperty}"/>

<table border="1">
    <tr>
        <th>Data Type</th>
        <th>Value</th>
    </tr>
    <tr>
        <td>boolean</td>
        <td><c:out value="${test2_boolean}"/></td>
    </tr>
    <tr>
        <td>double</td>
        <td><c:out value="${test2_double}"/></td>
    </tr>
    <tr>
        <td>float</td>
        <td><c:out value="${test2_float}"/></td>
    </tr>
    <tr>
        <td>int</td>
        <td><c:out value="${test2_int}"/></td>
    </tr>
    <tr>
        <td>long</td>
        <td><c:out value="${test2_long}"/></td>
    </tr>
    <tr>
        <td>short</td>
        <td><c:out value="${test2_short}"/></td>
    </tr>
    <tr>
        <td>string</td>
        <td><c:out value="${test2_string}"/></td>
    </tr>
</table>


</body>
</html-el:html>
