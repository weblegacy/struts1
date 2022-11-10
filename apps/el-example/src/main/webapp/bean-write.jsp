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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html-el:html>
<head>
    <title>Test Replacements for struts bean:write Tag</title>
</head>

<body bgcolor="white">

<div align="center">
    <h1>Test Replacements for struts bean:write Tag</h1>
</div>

<h3>Test 1 -- Scalar Variable Lookups</h3>

<%
    pageContext.setAttribute("test1.boolean", Boolean.TRUE);
    pageContext.setAttribute("test1.double", Double.valueOf(321.0));
    pageContext.setAttribute("test1.float", Float.valueOf((float) 123.0));
    pageContext.setAttribute("test1.int", Integer.valueOf(123));
    pageContext.setAttribute("test1.long", Long.valueOf(321));
    pageContext.setAttribute("test1.short", Short.valueOf((short) 987));
    pageContext.setAttribute("test1.string", "This is a string");
%>

<table border="1">
    <tr>
        <th>Data Type</th>
        <th>Value</th>
    </tr>
    <tr>
        <td>boolean</td>
            <%-- Notice that the "value" attribute is not "${test1.boolean}".  This would
                 be interpreted as the "boolean" attribute of the "test1" scoped
                 variable, whereas this scoped variable is really named "test1.boolean".
            --%>
        <td><c:out value='${pageScope["test1.boolean"]}'/></td>
    </tr>
    <tr>
        <td>double</td>
        <td><c:out value='${pageScope["test1.double"]}'/></td>
    </tr>
    <tr>
        <td>float</td>
        <td><c:out value='${pageScope["test1.float"]}'/></td>
    </tr>
    <tr>
        <td>int</td>
        <td><c:out value='${pageScope["test1.int"]}'/></td>
    </tr>
    <tr>
        <td>long</td>
        <td><c:out value='${pageScope["test1.long"]}'/></td>
    </tr>
    <tr>
        <td>short</td>
        <td><c:out value='${pageScope["test1.short"]}'/></td>
    </tr>
    <tr>
        <td>String</td>
        <td><c:out value='${pageScope["test1.string"]}'/></td>
    </tr>
</table>

<h3>Test 2 -- Scalar Property Lookups</h3>

<jsp:useBean id="test2" scope="page"
             class="org.apache.struts.webapp.el.exercise.TestBean"/>

<table border="1">
    <tr>
        <th>Data Type</th>
        <th>Value</th>
    </tr>
    <tr>
        <td>boolean</td>
        <td><c:out value="${test2.booleanProperty}"/></td>
    </tr>
    <tr>
        <td>double</td>
        <td><c:out value="${test2.doubleProperty}"/></td>
    </tr>
    <tr>
        <td>float</td>
        <td><c:out value="${test2.floatProperty}"/></td>
    </tr>
    <tr>
        <td>int</td>
        <td><c:out value="${test2.intProperty}"/></td>
    </tr>
    <tr>
        <td>long</td>
        <td><c:out value="${test2.longProperty}"/></td>
    </tr>
    <tr>
        <td>short</td>
        <td><c:out value="${test2.shortProperty}"/></td>
    </tr>
    <tr>
        <td>String</td>
        <td><c:out value="${test2.stringProperty}"/></td>
    </tr>
</table>

<h3>Test 3 - Integer Array And Indexed Lookups</h3>

<jsp:useBean id="test3" scope="page"
             class="org.apache.struts.webapp.el.exercise.TestBean"/>

<table border="1">
    <tr>
        <th>Correct Value</th>
        <th>Array Result</th>
        <th>Indexed Result</th>
    </tr>
    <c:forEach items="${test3.intArray}" var="item" varStatus="indexId">
        <tr>
            <td><c:out value="${indexId.index * 10}"/></td>
            <td><c:out value="${test3.intArray[indexId.index]}"/>
            <td>
                    <%-- Simulated "indexed" arrays, ala Struts, is not available in the EL.
                    <c:out value="${test3.intIndexed[indexId.index]}"/>
                    --%>
                N/A
            </td>
        </tr>
    </c:forEach>
</table>


<h3>Test 4 - String Array And Indexed Lookups</h3>

<jsp:useBean id="test4" scope="page"
             class="org.apache.struts.webapp.el.exercise.TestBean"/>

<table border="1">
    <tr>
        <th>Correct Value</th>
        <th>Array Result</th>
        <th>Indexed Result</th>
    </tr>
    <c:forEach items="${test4.stringArray}" var="item" varStatus="indexId">
        <tr>
            <td><c:out value="${indexId.index}"/></td>
            <td><c:out value="${test4.stringArray[indexId.index]}"/></td>
            <td>
                    <%-- Simulated "indexed" arrays, ala Struts, is not available in the EL.
                      <c:out value="${test4.stringIndexed[indexId.index]}"/>
                    --%>
                N/A
            </td>
        </tr>
    </c:forEach>
</table>


<h3>Test 5 -- Nested Scalar Property Lookups</h3>

<jsp:useBean id="test5" scope="page"
             class="org.apache.struts.webapp.el.exercise.TestBean"/>

<table border="1">
    <tr>
        <th>Data Type</th>
        <th>Value</th>
    </tr>
    <tr>
        <td>boolean</td>
        <td><c:out value="${test5.nested.booleanProperty}"/></td>
    </tr>
    <tr>
        <td>double</td>
        <td><c:out value="${test5.nested.doubleProperty}"/></td>
    </tr>
    <tr>
        <td>float</td>
        <td><c:out value="${test5.nested.floatProperty}"/></td>
    </tr>
    <tr>
        <td>int</td>
        <td><c:out value="${test5.nested.intProperty}"/></td>
    </tr>
    <tr>
        <td>long</td>
        <td><c:out value="${test5.nested.longProperty}"/></td>
    </tr>
    <tr>
        <td>short</td>
        <td><c:out value="${test5.nested.shortProperty}"/></td>
    </tr>
    <tr>
        <td>String</td>
        <td><c:out value="${test5.nested.stringProperty}"/></td>
    </tr>
</table>

<h3>Test 6 - Nested Integer Array And Indexed Lookups</h3>

<jsp:useBean id="test6" scope="page"
             class="org.apache.struts.webapp.el.exercise.TestBean"/>

<table border="1">
    <tr>
        <th>Correct Value</th>
        <th>Array Result</th>
        <th>Indexed Result</th>
    </tr>
    <c:forEach items="${test6.nested.intArray}" var="item"
               varStatus="indexId">
        <tr>
            <td><c:out value="${indexId.index * 10}"/></td>
            <td><c:out value="${test6.nested.intArray[indexId.index]}"/></td>
            <td>
                    <%-- Simulated "indexed" arrays, ala Struts, is not available in the EL.
                      <c:out value="${test6.nested.intIndexed[indexId.index]}"/>
                    --%>
                N/A
            </td>
        </tr>
    </c:forEach>
</table>


</body>
</html-el:html>
