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
<c:if test='${not empty param["redirectType"]}'>
    <c:choose>
        <c:when test='${param["redirectType"] eq "forward"}'>
            <logic-el:redirect forward='${param["param1"]}'
                               transaction="${!empty pageScope}"/>
        </c:when>
        <c:when test='${param["redirectType"] eq "href"}'>
            <logic-el:redirect href='${param["param1"]}'/>
        </c:when>
        <c:when test='${param["redirectType"] eq "page"}'>
            <logic-el:redirect page='${param["param1"]}'/>
        </c:when>
    </c:choose>
</c:if>
<html-el:html>
    <head>
        <title>Test Struts &lt;logic:redirect&gt; tag</title>
    </head>

    <body>
    <div align="center">
        <h1>Test Struts &lt;logic:redirect&gt; tag</h1>
    </div>
    <table>
        <tr>
            <td>
                Redirect with <html-el:link forward="redirectForward">
                Forward</html-el:link> to index page
            </td>
        </tr>
        <tr>
            <td>
                Redirect with <html-el:link forward="redirectHref">
                Href</html-el:link> to http:/struts.apache.org/
            </td>
        </tr>
        <tr>
            <td>
                Redirect with <html-el:link forward="redirectPage">
                Page</html-el:link> to index page
            </td>
        </tr>
    </table>
    </body>
</html-el:html>
