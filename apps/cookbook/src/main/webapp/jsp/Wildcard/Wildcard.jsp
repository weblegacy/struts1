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
    <title>Wildcard Mappings Example</title>
</head>
<body>
<p>
<html:link action="SourceWildcard">
  <img src="images/code.gif" width="24" height="24" alt="View Source" class="icon" />
</html:link>
<html:link action="Home">
    <img src="images/return.gif" height="24" width="24" alt="Return to examples page" class="icon" />
</html:link>
</p>
<h1>Wildcard Example</h1>
<hr noshade="noshade"/>
<h2>Select a link. </h2>
<ul>
    <li>
        <html:link action="Wildcard-Any">
            This link demonstrates the Wildcard mapping feature.
        </html:link>
    </li>
    <li>
        <html:link action="Wildcard-Not">
            This link demonstrates that a specific mapping will override a
            less-specific Wildcard.
        </html:link>
    </li>
</ul>
</body>
</html:html>