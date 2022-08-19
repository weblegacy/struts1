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
        <title>Test Replacements for struts emptiness tags</title>
    </head>

    <body bgcolor="white">

    <div align="center">
        <h1>Test Replacements for struts emptiness tags</h1>
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
            <td>null</td>
            <td>empty</td>
            <td>
                <c:choose>
                    <c:when test="${empty bean.nullProperty}">
                        empty
                    </c:when>
                    <c:otherwise>
                        notEmpty
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>empty string</td>
            <td>empty</td>
            <td>
                <c:choose>
                    <c:when test="${empty bean.emptyStringProperty}">
                        empty
                    </c:when>
                    <c:otherwise>
                        notEmpty
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>non-empty string</td>
            <td>notEmpty</td>
            <td>
                <c:choose>
                    <c:when test="${empty bean.stringProperty}">
                        empty
                    </c:when>
                    <c:otherwise>
                        notEmpty
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>non-string object</td>
            <td>notEmpty</td>
            <td>
                <c:choose>
                    <c:when test="${empty bean.intProperty}">
                        empty
                    </c:when>
                    <c:otherwise>
                        notEmpty
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>

    </body>
</html-el:html>
