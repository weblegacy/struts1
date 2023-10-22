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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html:html>
    <head>
        <title>Test html:button Tag</title>
    </head>

    <body bgcolor="white">

    <div align="center">
        <h1>Test struts html:button Tag</h1>
    </div>

    <h2>Button</h2>
    <p>
        <html:button property="Test" value="Test"/>
    </p>
    <p>
        <html:button property="Test äöü" value="Test äöü"/>
    </p>
    <p>
        Test for  XML Entities not handled correctly #11:
        <html:button property="Test &auml;&ouml;&uuml;" value="Test &auml;&ouml;&uuml;"/>
    </p>

    <h2>Cancel</h2>
    <p>
        <html:cancel property="Test" value="Test"/>
    </p>
    <p>
        <html:cancel property="Test äöü" value="Test äöü"/>
    </p>
    <p>
        Test for  XML Entities not handled correctly #11:
        <html:cancel property="Test &auml;&ouml;&uuml;" value="Test &auml;&ouml;&uuml;"/>
    </p>

    <h2>Reset</h2>
    <p>
        <html:reset property="Test" value="Test"/>
    </p>
    <p>
        <html:reset property="Test äöü" value="Test äöü"/>
    </p>
    <p>
        Test for  XML Entities not handled correctly #11:
        <html:reset property="Test &auml;&ouml;&uuml;" value="Test &auml;&ouml;&uuml;"/>
    </p>

    <hr/>

    <p>
        <html:link action="welcome">
            Return to the Taglib Exercises main page
        </html:link>
    </p>

    </body>
</html:html>