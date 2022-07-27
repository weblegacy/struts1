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
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html>
  <head>
    <title>Test struts-logic Match Tags</title>
  </head>
  <body bgcolor="white">
    <div align="center">
      <h1>Test struts-logic Match Tags</h1>
    </div>
    <jsp:useBean id="bean" scope="page" class="org.apache.struts.webapp.exercise.TestBean" />
    <bean:cookie id="cookie" name="JSESSIONID" value="JSESSIONID-IS-UNDEFINED" />
    <bean:header id="header" name="User-Agent" value="USER-AGENT-IS-UNDEFINED" />
    <bean:parameter id="param" name="param1" value="PARAMETER-IS-UNDEFINED" /><%
      pageContext.setAttribute("string", "String test value");
    %>
    <table border="1">
      <tr>
        <th>Test Type</th>
        <th>Variable Content</th>
        <th>Value Content</th>
        <th>Match Test</th>
        <th>Test Result</th>
      </tr>
      <tr>
        <td>Cookie / Any</td>
        <td>
          <bean:write name="cookie" property="value" />
        </td>
        <td>0</td>
        <td>contains</td>
        <td>
          <logic:present cookie="JSESSIONID">
            <logic:match cookie="JSESSIONID" value="0">match</logic:match>
            <logic:notMatch cookie="JSESSIONID" value="0">notMatch</logic:notMatch>
          </logic:present>
          <logic:notPresent cookie="JSESSIONID">missing</logic:notPresent>
        </td>
      </tr>
      <tr>
        <td>Cookie / End</td>
        <td>
          <bean:write name="cookie" property="value" />
        </td>
        <td>0</td>
        <td>ends with</td>
        <td>
          <logic:present cookie="JSESSIONID">
            <logic:match cookie="JSESSIONID" location="end" value="0">match</logic:match>
            <logic:notMatch cookie="JSESSIONID" location="end" value="0">notMatch</logic:notMatch>
          </logic:present>
          <logic:notPresent cookie="JSESSIONID">missing</logic:notPresent>
        </td>
      </tr>
      <tr>
        <td>Cookie / Start</td>
        <td>
          <bean:write name="cookie" property="value" />
        </td>
        <td>0</td>
        <td>starts with</td>
        <td>
          <logic:present cookie="JSESSIONID">
            <logic:match cookie="JSESSIONID" location="start" value="0">match</logic:match>
            <logic:notMatch cookie="JSESSIONID" location="start" value="0">notMatch</logic:notMatch>
          </logic:present>
          <logic:notPresent cookie="JSESSIONID">missing</logic:notPresent>
        </td>
      </tr>
      <tr>
        <td>Header / Any</td>
        <td>
          <bean:write name="header" />
        </td>
        <td>Mozilla</td>
        <td>contains</td>
        <td>
          <logic:present header="User-Agent">
            <logic:match header="User-Agent" value="Mozilla">match</logic:match>
            <logic:notMatch header="User-Agent" value="Mozilla">notMatch</logic:notMatch>
          </logic:present>
          <logic:notPresent header="User-Agent">missing</logic:notPresent>
        </td>
      </tr>
      <tr>
        <td>Header / End</td>
        <td>
          <bean:write name="header" />
        </td>
        <td>Mozilla</td>
        <td>ends with</td>
        <td>
          <logic:present header="User-Agent">
            <logic:match header="User-Agent" location="end" value="Mozilla">match</logic:match>
            <logic:notMatch header="User-Agent" location="end" value="Mozilla">notMatch</logic:notMatch>
          </logic:present>
          <logic:notPresent header="User-Agent">missing</logic:notPresent>
        </td>
      </tr>
      <tr>
        <td>Header / Start</td>
        <td>
          <bean:write name="header" />
        </td>
        <td>Mozilla</td>
        <td>starts with</td>
        <td>
          <logic:present header="User-Agent">
            <logic:match header="User-Agent" location="start" value="Mozilla">match</logic:match>
            <logic:notMatch header="User-Agent" location="start" value="Mozilla">notMatch</logic:notMatch>
          </logic:present>
          <logic:notPresent header="User-Agent">missing</logic:notPresent>
        </td>
      </tr>
      <tr>
        <td>Name / Any</td>
        <td>
          <bean:write name="string" />
        </td>
        <td>value</td>
        <td>contains</td>
        <td>
          <logic:match name="string" value="value">match</logic:match>
          <logic:notMatch name="string" value="value">notMatch</logic:notMatch>
        </td>
      </tr>
      <tr>
        <td>Name / End</td>
        <td>
          <bean:write name="string" />
        </td>
        <td>value</td>
        <td>ends with</td>
        <td>
          <logic:match name="string" location="end" value="value">match</logic:match>
          <logic:notMatch name="string" location="end" value="value">notMatch</logic:notMatch>
        </td>
      </tr>
      <tr>
        <td>Name / Start</td>
        <td>
          <bean:write name="string" />
        </td>
        <td>value</td>
        <td>starts with</td>
        <td>
          <logic:match name="string" location="start" value="value">match</logic:match>
          <logic:notMatch name="string" location="start" value="value">notMatch</logic:notMatch>
        </td>
      </tr>
      <tr>
        <td>Parameter / Any</td>
        <td>
          <bean:write name="param" />
        </td>
        <td>value1</td>
        <td>contains</td>
        <td>
          <logic:present parameter="param1">
            <logic:match parameter="param1" value="value1">match</logic:match>
            <logic:notMatch parameter="param1" value="value1">notMatch</logic:notMatch>
          </logic:present>
          <logic:notPresent parameter="param1">missing</logic:notPresent>
        </td>
      </tr>
      <tr>
        <td>Parameter / End</td>
        <td>
          <bean:write name="param" />
        </td>
        <td>value1</td>
        <td>ends with</td>
        <td>
          <logic:present parameter="param1">
            <logic:match parameter="param1" location="end" value="value1">match</logic:match>
            <logic:notMatch parameter="param1" location="end" value="value1">notMatch</logic:notMatch>
          </logic:present>
          <logic:notPresent parameter="param1">missing</logic:notPresent>
        </td>
      </tr>
      <tr>
        <td>Parameter / Start</td>
        <td>
          <bean:write name="param" />
        </td>
        <td>value1</td>
        <td>starts with</td>
        <td>
          <logic:present parameter="param1">
            <logic:match parameter="param1" location="start" value="value1">match</logic:match>
            <logic:notMatch parameter="param1" location="start" value="value1">notMatch</logic:notMatch>
          </logic:present>
          <logic:notPresent parameter="param1">missing</logic:notPresent>
        </td>
      </tr>
      <tr>
        <td>Property / Any</td>
        <td>
          <bean:write name="bean" property="stringProperty" />
        </td>
        <td>FOO</td>
        <td>contains</td>
        <td>
          <logic:match name="bean" property="stringProperty" value="FOO">match</logic:match>
          <logic:notMatch name="bean" property="stringProperty" value="FOO">notMatch</logic:notMatch>
        </td>
      </tr>
      <tr>
        <td>Property / End</td>
        <td>
          <bean:write name="bean" property="stringProperty" />
        </td>
        <td>FOO</td>
        <td>ends with</td>
        <td>
          <logic:match name="bean" property="stringProperty" location="end" value="FOO">match</logic:match>
          <logic:notMatch name="bean" property="stringProperty" location="end" value="FOO">notMatch</logic:notMatch>
        </td>
      </tr>
      <tr>
        <td>Property / Start</td>
        <td>
          <bean:write name="bean" property="stringProperty" />
        </td>
        <td>FOO</td>
        <td>starts with</td>
        <td>
          <logic:match name="bean" property="stringProperty" location="start" value="FOO">match</logic:match>
          <logic:notMatch name="bean" property="stringProperty" location="start" value="FOO">notMatch</logic:notMatch>
        </td>
      </tr>
    </table>
  </body>
</html>
