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
    <title>Test struts logic-el Match Tags</title>
</head>

<body bgcolor="white">

<div align="center">
    <h1>Test struts logic-el Match Tags</h1>
</div>

<jsp:useBean id="bean" scope="page"
             class="org.apache.struts.webapp.el.exercise.TestBean"/>
<c:set var="jcookie" value='${cookie["JSESSIONID"].value}'/>
<c:set var="uaheader" value='${header["User-Agent"]}'/>
<c:set var="rparam" value='${param["param1"]}'/>
<%
    pageContext.setAttribute("string", "String test value");
%>

<table border="1">
<tr>
    <th>Test Type</th>
    <th>Variable Content</th>
    <th>Value Content</th>
    <th>Correct Value Test</th>
    <th>Test Result</th>
</tr>
<tr>
    <td>Cookie / Any</td>
        <%-- This isn't an exact parallel.  With "bean:cookie", you can specify a
                       default value.  That would take another step with this. --%>
    <td><c:out value="${jcookie}"/></td>
    <td>0</td>
    <td>contains</td>
    <td>
        <c:choose>
            <c:when test="${not empty jcookie}">
                <%-- The functionality of "logic:match" will eventually be available
                     through a string function in the EL expression. --%>
                <logic-el:match expr="${jcookie}" value="0">
                    match
                </logic-el:match>
                <logic-el:notMatch expr="${jcookie}" value="0">
                    notMatch
                </logic-el:notMatch>
            </c:when>
            <c:otherwise>
                missing
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Cookie / End</td>
    <td><c:out value="${jcookie}"/></td>
    <td>0</td>
    <td>ends with</td>
    <td>
        <c:choose>
            <c:when test="${not empty jcookie}">
                <logic-el:match expr="${jcookie}" location="end" value="0">
                    match
                </logic-el:match>
                <logic-el:notMatch expr="${jcookie}" location="end" value="0">
                    notMatch
                </logic-el:notMatch>
            </c:when>
            <c:otherwise>
                missing
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Cookie / Start</td>
    <td><c:out value="${jcookie}"/></td>
    <td>0</td>
    <td>starts with</td>
    <td>
        <c:choose>
            <c:when test="${not empty jcookie}">
                <logic-el:match expr="${jcookie}" location="start" value="0">
                    match
                </logic-el:match>
                <logic-el:notMatch expr="${jcookie}" location="start"
                                   value="0">
                    notMatch
                </logic-el:notMatch>
            </c:when>
            <c:otherwise>
                missing
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Header / Any</td>
    <td><c:out value="${uaheader}"/></td>
    <td>Mozilla</td>
    <td>contains</td>
    <td>
        <c:choose>
            <c:when test="${not empty uaheader}">
                <logic-el:match expr="${uaheader}" value="Mozilla">
                    match
                </logic-el:match>
                <logic-el:notMatch expr="${uaheader}" value="Mozilla">
                    notMatch
                </logic-el:notMatch>
            </c:when>
            <c:otherwise>
                missing
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Header / End</td>
    <td><c:out value="${uaheader}"/></td>
    <td>Mozilla</td>
    <td>ends with</td>
    <td>
        <c:choose>
            <c:when test="${not empty uaheader}">
                <logic-el:match expr="${uaheader}" location="end"
                                value="Mozilla">
                    match
                </logic-el:match>
                <logic-el:notMatch expr="${uaheader}" location="end"
                                   value="Mozilla">
                    notMatch
                </logic-el:notMatch>
            </c:when>
            <c:otherwise>
                missing
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Header / Start</td>
    <td><c:out value="${uaheader}"/></td>
    <td>Mozilla</td>
    <td>starts with</td>
    <td>
        <c:choose>
            <c:when test="${not empty uaheader}">
                <logic-el:match expr="${uaheader}" location="start"
                                value="Mozilla">
                    match
                </logic-el:match>
                <logic-el:notMatch expr="${uaheader}" location="start"
                                   value="Mozilla">
                    notMatch
                </logic-el:notMatch>
            </c:when>
            <c:otherwise>
                missing
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Name / Any</td>
    <td><c:out value="${string}"/></td>
    <td>value</td>
    <td>contains</td>
    <td>
        <logic-el:match name="string" value="value">
            match
        </logic-el:match>
        <logic-el:notMatch name="string" value="value">
            notMatch
        </logic-el:notMatch>
    </td>
</tr>
<tr>
    <td>Name / End</td>
    <td><c:out value="${string}"/></td>
    <td>value</td>
    <td>ends with</td>
    <td>
        <logic-el:match name="string" location="end" value="value">
            match
        </logic-el:match>
        <logic-el:notMatch name="string" location="end" value="value">
            notMatch
        </logic-el:notMatch>
    </td>
</tr>
<tr>
    <td>Name / Start</td>
    <td><c:out value="${string}"/></td>
    <td>value</td>
    <td>starts with</td>
    <td>
        <logic-el:match name="string" location="start" value="value">
            match
        </logic-el:match>
        <logic-el:notMatch name="string" location="start" value="value">
            notMatch
        </logic-el:notMatch>
    </td>
</tr>
<tr>
    <td>Parameter / Any</td>
    <td><c:out value="${rparam}"/></td>
    <td>value1</td>
    <td>contains</td>
    <td>
        <c:choose>
            <c:when test="${not empty rparam}">
                <logic-el:match expr="${rparam}" value="value1">
                    match
                </logic-el:match>
                <logic-el:notMatch expr="${rparam}" value="value1">
                    notMatch
                </logic-el:notMatch>
            </c:when>
            <c:otherwise>
                missing
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Parameter / End</td>
    <td><c:out value="${rparam}"/></td>
    <td>value1</td>
    <td>ends with</td>
    <td>
        <c:choose>
            <c:when test="${not empty rparam}">
                <logic-el:match expr="${rparam}" location="end"
                                value="value1">
                    match
                </logic-el:match>
                <logic-el:notMatch expr="${rparam}" location="end"
                                   value="value1">
                    notMatch
                </logic-el:notMatch>
            </c:when>
            <c:otherwise>
                missing
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Parameter / Start</td>
    <td><c:out value="${rparam}"/></td>
    <td>value1</td>
    <td>starts with</td>
    <td>
        <c:choose>
            <c:when test="${not empty rparam}">
                <logic-el:match expr="${rparam}" location="start"
                                value="value1">
                    match
                </logic-el:match>
                <logic-el:notMatch expr="${rparam}" location="start"
                                   value="value1">
                    notMatch
                </logic-el:notMatch>
            </c:when>
            <c:otherwise>
                missing
            </c:otherwise>
        </c:choose>
    </td>
</tr>
<tr>
    <td>Property / Any</td>
    <td><c:out value="${bean.stringProperty}"/></td>
    <td>FOO</td>
    <td>contains</td>
    <td>
        <logic-el:match expr="${bean.stringProperty}" value="FOO">
            match
        </logic-el:match>
        <logic-el:notMatch expr="${bean.stringProperty}" value="FOO">
            notMatch
        </logic-el:notMatch>
    </td>
</tr>
<tr>
    <td>Property / End</td>
    <td><c:out value="${bean.stringProperty}"/></td>
    <td>FOO</td>
    <td>ends with</td>
    <td>
        <logic-el:match expr="${bean.stringProperty}" location="end"
                        value="FOO">
            match
        </logic-el:match>
        <logic-el:notMatch expr="${bean.stringProperty}"
                           location="end" value="FOO">
            notMatch
        </logic-el:notMatch>
    </td>
</tr>
<tr>
    <td>Property / Start</td>
    <td><c:out value="${bean.stringProperty}"/></td>
    <td>FOO</td>
    <td>starts with</td>
    <td>
        <logic-el:match expr="${bean.stringProperty}"
                        location="start" value="FOO">
            match
        </logic-el:match>
        <logic-el:notMatch expr="${bean.stringProperty}"
                           location="start" value="FOO">
            notMatch
        </logic-el:notMatch>
    </td>
</tr>
</table>

</body>
</html-el:html>
