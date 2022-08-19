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
        <title>Test bean-el:include Tag and Replacements</title>
    </head>

    <body bgcolor="white">

    <div align="center">
        <h1>Test bean-el:include Tag and Replacements</h1>
    </div>

    <c:import url="/index.jsp" var="index"/>
    <bean-el:include id="index2" page="/index.jsp"/>

    <p>Display the contents returned by invoking <code>/index.jsp</code>
        using <code>&lt;c:import&gt;</code>, with no filtering.</p>
    <hr>
    <pre>
        <c:out value="${index}" escapeXml="false"/>
    </pre>
    <hr>

    <p>Display the contents returned by invoking <code>/index.jsp</code>
        using <code>&lt;c:import&gt;</code>, with filtering.</p>
    <hr>
    <pre>
        <c:out value="${index}" escapeXml="true"/>
    </pre>
    <hr>

    <p>Display the contents returned by invoking <code>/index.jsp</code>
        using <code>&lt;bean-el:include&gt;</code>, with no filtering.</p>
    <hr>
    <pre>
        <c:out value="${index2}" escapeXml="false"/>
    </pre>
    <hr>

    <p>Display the contents returned by invoking <code>/index.jsp</code>
        using <code>&lt;bean-el:include&gt;</code>, with filtering.</p>
    <hr>
    <pre>
        <c:out value="${index2}" escapeXml="true"/>
    </pre>
    <hr>

    </body>
</html-el:html>
