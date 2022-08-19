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
        <title>Test Replacements for struts-bean:header Tag</title>
    </head>

    <body bgcolor="white">

    <div align="center">
        <h1>Test Replacements for struts-bean:header Tag</h1>
    </div>

    Display the values of the headers included in this request.<br><br>

    <table border="1">
        <tr>
            <th>Header Name</th>
            <th>Header Value</th>
        </tr>
        <c:forEach items="${pageContext.request.headerNames}" var="name">
            <tr>
                <td><c:out value="${name}"/></td>
                <td><c:out value="${header[name]}"/></td>
            </tr>
        </c:forEach>
        <bean:header id="dummy" name="UNKNOWN-HEADER" value="UNKNOWN VALUE"/>
        <tr>
            <td>UNKNOWN HEADER</td>
            <td><c:out value="${dummy}"/></td>
        </tr>
    </table>

    </body>
</html-el:html>
