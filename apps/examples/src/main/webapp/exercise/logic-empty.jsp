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
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html>
  <head>
    <title>Test struts-logic Emptiness Tags</title>
  </head>
  <body bgcolor="white">
    <div align="center">
      <h1>Test struts-logic Emptiness Tags</h1>
    </div>
    <jsp:useBean id="bean" scope="page" class="org.apache.struts.webapp.exercise.TestBean" />
    <table border="1">
      <tr>
        <th>Test Type</th>
        <th>Correct Value</th>
        <th>Test Result</th>
      </tr>
      <tr>
        <td>missing bean, no property attribute</td>
        <td>empty</td>
        <td>
          <logic:empty name="missingBean">empty</logic:empty>
          <logic:notEmpty name="missingBean">notEmpty</logic:notEmpty>
        </td>
      </tr>
      <tr>
        <td>not-missing bean, no property attribute</td>
        <td>notEmpty</td>
        <td>
          <logic:empty name="bean">empty</logic:empty>
          <logic:notEmpty name="bean">notEmpty</logic:notEmpty>
        </td>
      </tr>
      <tr>
        <td>null</td>
        <td>empty</td>
        <td>
          <logic:empty name="bean" property="nullProperty">empty</logic:empty>
          <logic:notEmpty name="bean" property="nullProperty">notEmpty</logic:notEmpty>
        </td>
      </tr>
      <tr>
        <td>empty string</td>
        <td>empty</td>
        <td>
          <logic:empty name="bean" property="emptyStringProperty">empty</logic:empty>
          <logic:notEmpty name="bean" property="emptyStringProperty">notEmpty</logic:notEmpty>
        </td>
      </tr>
      <tr>
        <td>non-empty string</td>
        <td>notEmpty</td>
        <td>
          <logic:empty name="bean" property="stringProperty">empty</logic:empty>
          <logic:notEmpty name="bean" property="stringProperty">notEmpty</logic:notEmpty>
        </td>
      </tr>
      <tr>
        <td>empty collection</td>
        <td>empty</td>
        <td>
          <logic:empty name="bean" property="emptyListProperty">empty</logic:empty>
          <logic:notEmpty name="bean" property="emptyListProperty">notEmpty</logic:notEmpty>
        </td>
      </tr>
      <tr>
        <td>non-empty collection</td>
        <td>notEmpty</td>
        <td>
          <logic:empty name="bean" property="listProperty">empty</logic:empty>
          <logic:notEmpty name="bean" property="listProperty">notEmpty</logic:notEmpty>
        </td>
      </tr>
      <tr>
        <td>empty map</td>
        <td>empty</td>
        <td>
          <logic:empty name="bean" property="emptyMapProperty">empty</logic:empty>
          <logic:notEmpty name="bean" property="emptyMapProperty">notEmpty</logic:notEmpty>
        </td>
      </tr>
      <tr>
        <td>non-empty map</td>
        <td>notEmpty</td>
        <td>
          <logic:empty name="bean" property="mapProperty">empty</logic:empty>
          <logic:notEmpty name="bean" property="mapProperty">notEmpty</logic:notEmpty>
        </td>
      </tr>
      <tr>
        <td>unsupported object</td>
        <td>notEmpty</td>
        <td>
          <logic:empty name="bean" property="intProperty">empty</logic:empty>
          <logic:notEmpty name="bean" property="intProperty">notEmpty</logic:notEmpty>
        </td>
      </tr>
    </table>
  </body>
</html>
