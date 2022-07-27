<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Source Code for Link examples</title>
<html:base/>
<link rel="stylesheet" type="text/css" href="../../css/example.css" />
</head>
<body>
<html:link action="/processLinks">
       <img src="../../images/execute.gif" width="24" height="24" alt="Execute example" class="icon" />
</html:link>
<a href="../../index.jsp"><img src="../../images/return.gif" height="24" width="24" alt="Return to examples page" class="icon" /></a>
<h1>Source Code for Link examples</h1>
<hr noshade="noshade"/>

<h2>JavaServer Pages</h2>
<p><html:link page="/source.jsp?src=/jsp/links/Links.jsp">Links.jsp</html:link></p>
<p><html:link page="/source.jsp?src=/jsp/links/LinksResults.jsp">LinkResults.jsp</html:link></p>

<h2>Actions</h2>
<p><html:link page="/source.jsp?src=/WEB-INF/src/java/examples/links/PrepareLinksAction.java">PrepareLinksAction.java</html:link></p>
<p><html:link page="/source.jsp?src=/WEB-INF/src/java/examples/links/ProcessLinksAction.java">ProcessLinksAction.java</html:link></p>

<h2>ActionForm</h2>
<p>None</p>

<h2>Configuration Files</h2>
<p><html:link page="/source.jsp?src=/WEB-INF/struts-config.xml">struts-config.xml</html:link></p>

<h2>Misc.</h2>
<p>None</p>

</body>
</html>
