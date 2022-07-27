<%@ page contentType="text/html;charset=UTF-8" %>
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
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:html>
<head>
<title><bean:message key="index.title"/></title>
<html:base/>
</head>
<body bgcolor="white">

    <p>
       <html:link forward="module-root"><bean:message key="index.home"/></html:link>
    </p>

    <hr />
    <p>
       <strong>Change Language | Changez Le Langage:</strong>
       &nbsp;
       <html:link action="/locale?language=en">English | Anglais</html:link>
       &nbsp;
       <html:link action="/locale?language=fr">French | Francais</html:link>
    </p>

    <hr />

    <h3><bean:message key="index.title"/></h3>

    <ul>
       <li><html:link action="/dispatch"><bean:message key="dispatch.title"/></html:link></li>
       <li><html:link action="/mapping"><bean:message key="mapping.title"/></html:link></li>
       <li><html:link action="/lookup"><bean:message key="lookup.title"/></html:link></li>
       <li><html:link action="/actionDispatcher"><bean:message key="actionDispatcher.title"/></html:link></li>
       <li><html:link action="/eventAction"><bean:message key="eventAction.title"/></html:link></li>
       <li><html:link action="/eventDispatcher"><bean:message key="eventDispatcher.title"/></html:link></li>
    </ul>

</body>
</html:html>
