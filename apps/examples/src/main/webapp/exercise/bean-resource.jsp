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
<html>
  <head>
    <title>Test struts-bean:resource Tag</title>
  </head>
  <body>
    <div align="center">
      <h1>Test struts-bean:resource Tag</h1>
    </div>
    <bean:resource id="webxml" name="/WEB-INF/web.xml" />
    <p>Display the contents of the
    <code>WEB-INF/web.xml</code> resource for this web application, with no filtering.</p>
    <hr />
    <pre>
<%= webxml %>
</pre>
    <hr />
    <p>Display the contents of the
    <code>WEB-INF/web.xml</code> resource for this web application, with filtering.</p>
    <hr />
    <pre>
<bean:write name="webxml" filter="true" />
</pre>
    <hr />
  </body>
</html>
