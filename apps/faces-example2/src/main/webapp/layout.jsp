<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
  <s:loadMessages     var="messages"/>
  <s:html          locale="true">
    <head>
      <title><s:message key="layout.title"/></title>
      <s:stylesheet path="/stylesheet.css"/>
    </head>
    <body>
      <table style="border:1px solid black;width:100%;border-spacing:5px">
        <tr>
          <th colspan="2" style="border:1px solid black;text-align:center">
            <f:subview id="header">
              <t:insert attribute="header" flush="false"/>
            </f:subview>
          </th>
        </tr>
        <tr>
          <td style="border:1px solid black;width:140px;vertical-align:top">
            <f:subview id="menu">
              <t:insert attribute="menu" flush="false"/>
            </f:subview>
          </td>
          <td style="border:1px solid black;text-align:left;vertical-align:top">
            <f:subview id="body">
              <t:insert attribute="body" flush="false"/>
            </f:subview>
          </td>
        </tr>
        <tr>
          <td colspan="2" style="border:1px solid black;text-align:center">
            <f:subview id="footer">
              <t:insert attribute="footer" flush="false"/>
            </f:subview>
          </td>
        </tr>
      </table>
    </body>
  </s:html>
</f:view>
