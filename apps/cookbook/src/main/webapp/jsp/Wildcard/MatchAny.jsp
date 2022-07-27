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
<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:html xhtml="true" lang="true">
<head>
    <link rel="stylesheet" type="text/css" href="css/example.css" />
    <title>Wildcard Mapping Example Demonstrating Match Any</title>
</head>
<body>
<html:link action="SourceWildcard">
    <img src="images/code.gif" width="24" height="24" alt="View Source" class="icon" />
</html:link>
<html:link action="Home">
    <img src="images/return.gif" height="24" width="24" alt="Return to examples page" class="icon" />
</html:link>
<h1>Wildcard Mapping Example Results - Match Any</h1>
<hr noshade="noshade"/>
<p>
    This page displays when any "WildCard" link is selected,
    except the "WildCard-Not" link.
</p>
<h2>Press back</h2>
</body>
</html:html>