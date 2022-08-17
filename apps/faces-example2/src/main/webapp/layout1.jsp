<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib prefix="s" uri="http://struts.apache.org/tags-faces" %>
<%@ taglib prefix="t" uri="http://struts.apache.org/tags-tiles" %>


<!--

 Copyright 2002,2004 The Apache Software Foundation.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

-->


<f:view>
  <s:html locale="true">
    <head>
      <title>
        <s:message key="layout.title"/>
      </title>
    </head>
    <body>
      <table style="border:1px solid black;width:100%;border-spacing:5px">
        <tr>
          <th colspan="2" style="border:1px solid black;text-align:center">
              <t:insert attribute="header" flush="false"/>
          </th>
        </tr>
        <tr>
          <td style="border:1px solid black;width:140px;vertical-align:top">
              <t:insert attribute="menu" flush="false"/>
          </td>
          <td style="border:1px solid black;text-align:left;vertical-align:top">
              <t:insert attribute="body" flush="false"/>
          </td>
        </tr>
        <tr>
          <td colspan="2" style="border:1px solid black;text-align:center">
              <t:insert attribute="footer" flush="false"/>
          </td>
        </tr>
      </table>
    </body>
  </s:html>
</f:view>
