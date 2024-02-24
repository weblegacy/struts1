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
<%@page import="org.apache.struts.util.ResponseUtils" %>
<html>
<body>
<p>
<b>The Text:</b>&nbsp;<%= ResponseUtils.filter((String) request.getAttribute("text")) %>
</p>
<p>
<b>The Query Parameter:</b>&nbsp;<%= ResponseUtils.filter((String)request.getAttribute("queryValue")) %>
</p>
<p>
<b>Number of selected files:</b>&nbsp;<%= ResponseUtils.filter((String)request.getAttribute("fileCount")) %>
</p>
<p>
<b>Informations of the first selected file:</b>
</p>
<p>
<b>The File name:</b>&nbsp;<%= ResponseUtils.filter((String) request.getAttribute("fileName")) %>
</p>
<p>
<b>The File content type:</b>&nbsp;<%= ResponseUtils.filter((String) request.getAttribute("contentType")) %>
</p>
<p>
<b>The File size:</b>&nbsp;<%= ResponseUtils.filter((String) request.getAttribute("size")) %>
</p>
<p>
<b>The File data:</b>
</p>
<hr />
<pre>
<%= ResponseUtils.filter((String) request.getAttribute("data")) %>
</pre>
<hr />

<hr />
<h3>Request Parameters</h3>

    <p>Display the request parameter values to show that the multipart request
       retains them after a forward.</p>

    <b>The Text:</b>&nbsp;<%= ResponseUtils.filter((String)request.getParameter("theText")) %><br>
    <b>Write File:</b>&nbsp;<%= ResponseUtils.filter((String)request.getParameter("writeFile")) %><br>
    <b>File Path:</b>&nbsp;<%= ResponseUtils.filter((String)request.getParameter("filePath")) %><br>

    <hr />
</body>
</html>
