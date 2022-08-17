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
    <title>Test struts-html:img Tag</title>
  </head>
  <body>
    <div align="center">
      <h1>Test struts-html:img Tag</h1>
    </div>

    <table style="border:1px solid black;border-spacing:2px;margin-left:auto;margin-right:auto">
    <tr>
    <td  style="border:1px solid black;padding:4px">
    Standard img tag
    </td>
    <td  style="border:1px solid black;padding:4px">
    <img src="struts-power.gif">
    </td>
    </tr>
    <tr>
    <td  style="border:1px solid black;padding:4px">
    Struts img tag via page attribute
    </td>
    <td  style="border:1px solid black;padding:4px">
    <html:img page="/struts-power.gif" />
    </td>
    </tr>
    <tr>
    <td  style="border:1px solid black;padding:4px">
    Struts img tag via action attribute
    </td>
    <td  style="border:1px solid black;padding:4px">
    <html:img action="/html-img-action" />
    </td>
    </tr>
    <tr>
    <td  style="border:1px solid black;padding:4px">
    Struts img tag via action attribute and actionId
    </td>
    <td  style="border:1px solid black;padding:4px">
    <html:img action="imgAction" />
    </td>
    </tr>
    <tr>
    <td  style="border:1px solid black;padding:4px">
    Struts img tag via page attribute, default module
    </td>
    <td  style="border:1px solid black;padding:4px">
    <html:img page="/exercise/struts-power.gif" module="/"/>
    </td>
    </tr>
    </table>
  </body>
</html>
