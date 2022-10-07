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
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic-el" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html-el:html>
<head>
    <title>Test Struts presence tags and Replacements</title>
</head>

<body bgcolor="white">

<div align="center">
    <h1>Test Struts presence tags and Replacements</h1>
</div>

<jsp:useBean id="bean" scope="page"
             class="org.apache.struts.webapp.el.exercise.TestBean"/>

<table border="1">
<tr>
    <th>Test Type</th>
    <th>Correct Value</th>
    <th>Test Result</th>
</tr>
<tr>
    <td>Role</td>
    <td>notPresent</td>
    <td>
        <logic-el:present role="fubar">
            present
        </logic-el:present>
        <logic-el:notPresent role="fubar">
            notPresent
        </logic-el:notPresent>
    </td>
</tr>
<tr>
    <td>Bean</td>
    <td>present</td>
    <td>
        <c:choose>
            <c:when test="${not empty bean}">
                present
            </c:when>
            <c:otherwise>
                notPresent
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Bean</td>
    <td>notPresent</td>
    <td>
        <c:choose>
            <c:when test="${not empty FOOBAR}">
                present
            </c:when>
            <c:otherwise>
                notPresent
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Cookie</td>
    <td>
        <c:choose>
            <c:when test="${pageContext.request.requestedSessionIdFromCookie}">
                present
            </c:when>
            <c:otherwise>
                notPresent
            </c:otherwise>
        </c:choose>
    </td>
    <td>
        <c:choose>
            <c:when test='${not empty cookie["JSESSIONID"]}'>
                present
            </c:when>
            <c:otherwise>
                notPresent
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Cookie</td>
    <td>notPresent</td>
    <td>
        <c:choose>
            <c:when test='${not empty cookie["FOOBAR"]}'>
                present
            </c:when>
            <c:otherwise>
                notPresent
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Header</td>
    <td>present</td>
    <td>
        <c:choose>
            <c:when test='${not empty header["User-Agent"]}'>
                present
            </c:when>
            <c:otherwise>
                notPresent
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Header</td>
    <td>notPresent</td>
    <td>
        <c:choose>
            <c:when test='${not empty header["FOOBAR"]}'>
                present
            </c:when>
            <c:otherwise>
                notPresent
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Parameter</td>
    <td>present</td>
    <td>
        <c:choose>
            <c:when test='${not empty param["param1"]}'>
                present
            </c:when>
            <c:otherwise>
                notPresent
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Parameter</td>
    <td>notPresent</td>
    <td>
        <c:choose>
            <c:when test='${not empty param["FOOBAR"]}'>
                present
            </c:when>
            <c:otherwise>
                notPresent
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Property</td>
    <td>present</td>
    <td>
        <c:choose>
            <c:when test="${not empty bean.stringProperty}">
                present
            </c:when>
            <c:otherwise>
                notPresent
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Property</td>
    <td>notPresent</td>
    <td>
        <c:choose>
            <c:when test="${not empty bean.nullProperty}">
                present
            </c:when>
            <c:otherwise>
                notPresent
            </c:otherwise>
        </c:choose>
    </td>
</tr>
</table>

</body>
</html-el:html>
