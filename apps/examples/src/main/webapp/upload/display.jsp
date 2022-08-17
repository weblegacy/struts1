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
<html>
<body>
<p>
<b>The Text:</b>&nbsp;<%= request.getAttribute("text") %>
</p>
<p>
<b>The Query Parameter:</b>&nbsp;<%= request.getAttribute("queryValue") %>
</p>
<p>
<b>The File name:</b>&nbsp;<%= request.getAttribute("fileName") %>
</p>
<p>
<b>The File content type:</b>&nbsp;<%= request.getAttribute("contentType") %>
</p>
<p>
<b>The File size:</b>&nbsp;<%= request.getAttribute("size") %>
</p>
<p>
<b>The File data:</b>
</p>
<hr />
<pre>
<%= request.getAttribute("data") %>
</pre>
<hr />

<hr />
<h3>Request Parameters</h3>

    <p>Display the request parameter values to show that the multipart request
       retains them after a forward.</p>

    <b>The Text:</b>&nbsp;<%= request.getParameter("theText") %><br>
    <b>Write File:</b>&nbsp;<%= request.getParameter("writeFile") %><br>
    <b>File Path:</b>&nbsp;<%= request.getParameter("filePath") %><br>

    <hr />
</body>
</html>
