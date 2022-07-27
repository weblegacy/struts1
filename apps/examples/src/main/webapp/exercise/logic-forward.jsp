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
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html>
  <head>
    <title>Test struts-logic:forward and struts-logic:redirect Tags</title>
  </head>
  <body>
    <div align="center">
      <h1>Test struts-logic:forward and struts-logic:redirect Tags</h1>
    </div>
    <p>The following links should return to this page.</p>
    <ul>
    <li><a href="logic-forward.do?value=test">Standard hyperlink back to this page</a></li>
    <li><html:link action="/logic-forward-test-forward?value=test">Forward to this page</html:link></li>
    <li><html:link action="/logic-redirect-test-action?value=test">Redirect to this page, via action attribute</html:link></li>
    <li><html:link action="/logic-redirect-test-forward?value=test">Redirect to this page, via forward attribute</html:link></li>
    <li><html:link action="/logic-redirect-test-page?value=test">Redirect to this page, via page attribute</html:link></li>
    </ul>
    <hr />
    <ul>
      <li>
        <html:link forward="relative">Taglibs Exercise welcome page</html:link>
      </li>
    </ul>
  </body>
</html>
