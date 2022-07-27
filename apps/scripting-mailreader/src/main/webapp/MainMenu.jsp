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
<html>
<head>
    <title><bean:message key="mainMenu.title"/></title>
    <link rel="stylesheet" type="text/css" href="base.css"/>
</head>

<body>
<h3><bean:message key="mainMenu.heading"/> <bean:write name="user"
                                                       property="fullName"/></h3>
<ul>
    <li><html:link action="/EditRegistration?task=Edit"><bean:message
            key="mainMenu.registration"/></html:link></li>
    <li><html:link action="/Logoff"><bean:message key="mainMenu.logoff"/>
    </html:link></li>
</ul>
</body>
</html>
