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
        <title>
            Testing access of DynaActionForms through JSTL/Struts-EL
        </title>
    </head>

    <body bgcolor="white">
    <div align="center">
        <h1>Test access of DynaActionForms through JSTL/Struts-EL</h1>
    </div>
    <table border="1">
        <tr>
            <th>Property</th>
            <th>bean:write reference</th>
            <th>c:out reference</th>
        </tr>
        <tr>
            <td>foo</td>
            <td><bean:write name="dynabean" property="foo"/></td>
            <td><c:out value="${dynabean.map.foo}"/></td>
        </tr>
        <tr>
            <td>bar</td>
            <td><bean:write name="dynabean" property="bar"/></td>
            <td><c:out value="${dynabean.map.bar}"/></td>
        </tr>
        <tr>
            <td>thing</td>
            <td>
                <c:catch var="ex">
                    <%-- This will throw an exception, as the "thing" property is not
                            defined.
                    --%>
                    <bean:write name="dynabean" property="thing"/>
                </c:catch>
                <c:if test="${!empty ex}">
                    &lt;exception&gt;
                </c:if>
            </td>
                <%-- This will just return an empty string --%>
            <td><c:out value="${dynabean.map.thing}" default="<empty>"/></td>
        </tr>
    </table>
    </body>
</html-el:html>
