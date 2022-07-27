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
    <title>Test struts-logic Comparison Tags (Numeric)</title>
  </head>
  <body bgcolor="white">
    <div align="center">
      <h1>Test struts-logic Comparison Tags (Numeric)</h1>
    </div>
    <jsp:useBean id="bean" scope="page" class="org.apache.struts.webapp.exercise.TestBean" /><%
      String doub1 = "321.0";
      String doub2 = "111.0";
      String doub3 = "333.0";
      String long1 = "321";
      String long2 = "111";
      String long3 = "333";
      String short1 = "987";
      String short2 = "654";
      String short3 = "999";
    %>
    <table border="1">
      <tr>
        <th>Test Type</th>
        <th>Variable Content</th>
        <th>Value Content</th>
        <th>Correct Value</th>
        <th>Test Result</th>
      </tr>
      <tr>
        <td>double / EQ</td>
        <td>
          <bean:write name="bean" property="doubleProperty" />
        </td>
        <td>
          <%= doub1 %>
        </td>
        <td>equal greaterEqual lessEqual</td>
        <td>
          <logic:equal name="bean" property="doubleProperty" value="<%= doub1 %>">equal</logic:equal>
          <logic:greaterEqual name="bean" property="doubleProperty" value="<%= doub1 %>">greaterEqual</logic:greaterEqual>
          <logic:greaterThan name="bean" property="doubleProperty" value="<%= doub1 %>">greaterThan</logic:greaterThan>
          <logic:lessEqual name="bean" property="doubleProperty" value="<%= doub1 %>">lessEqual</logic:lessEqual>
          <logic:lessThan name="bean" property="doubleProperty" value="<%= doub1 %>">lessThan</logic:lessThan>
          <logic:notEqual name="bean" property="doubleProperty" value="<%= doub1 %>">notEqual</logic:notEqual>
        </td>
      </tr>
      <tr>
        <td>double / GT</td>
        <td>
          <bean:write name="bean" property="doubleProperty" />
        </td>
        <td>
          <%= doub2 %>
        </td>
        <td>greaterEqual greaterThan notEqual</td>
        <td>
          <logic:equal name="bean" property="doubleProperty" value="<%= doub2 %>">equal</logic:equal>
          <logic:greaterEqual name="bean" property="doubleProperty" value="<%= doub2 %>">greaterEqual</logic:greaterEqual>
          <logic:greaterThan name="bean" property="doubleProperty" value="<%= doub2 %>">greaterThan</logic:greaterThan>
          <logic:lessEqual name="bean" property="doubleProperty" value="<%= doub2 %>">lessEqual</logic:lessEqual>
          <logic:lessThan name="bean" property="doubleProperty" value="<%= doub2 %>">lessThan</logic:lessThan>
          <logic:notEqual name="bean" property="doubleProperty" value="<%= doub2 %>">notEqual</logic:notEqual>
        </td>
      </tr>
      <tr>
        <td>double / LT</td>
        <td>
          <bean:write name="bean" property="doubleProperty" />
        </td>
        <td>
          <%= doub3 %>
        </td>
        <td>lessEqual lessThan notEqual</td>
        <td>
          <logic:equal name="bean" property="doubleProperty" value="<%= doub3 %>">equal</logic:equal>
          <logic:greaterEqual name="bean" property="doubleProperty" value="<%= doub3 %>">greaterEqual</logic:greaterEqual>
          <logic:greaterThan name="bean" property="doubleProperty" value="<%= doub3 %>">greaterThan</logic:greaterThan>
          <logic:lessEqual name="bean" property="doubleProperty" value="<%= doub3 %>">lessEqual</logic:lessEqual>
          <logic:lessThan name="bean" property="doubleProperty" value="<%= doub3 %>">lessThan</logic:lessThan>
          <logic:notEqual name="bean" property="doubleProperty" value="<%= doub3 %>">notEqual</logic:notEqual>
        </td>
      </tr>
      <tr>
        <td>float / EQ</td>
        <td>
          <bean:write name="bean" property="floatProperty" />
        </td>
        <td>
          <%= doub1 %>
        </td>
        <td>lessEqual lessThan notEqual</td>
        <td>
          <logic:equal name="bean" property="floatProperty" value="<%= doub1 %>">equal</logic:equal>
          <logic:greaterEqual name="bean" property="floatProperty" value="<%= doub1 %>">greaterEqual</logic:greaterEqual>
          <logic:greaterThan name="bean" property="floatProperty" value="<%= doub1 %>">greaterThan</logic:greaterThan>
          <logic:lessEqual name="bean" property="floatProperty" value="<%= doub1 %>">lessEqual</logic:lessEqual>
          <logic:lessThan name="bean" property="floatProperty" value="<%= doub1 %>">lessThan</logic:lessThan>
          <logic:notEqual name="bean" property="floatProperty" value="<%= doub1 %>">notEqual</logic:notEqual>
        </td>
      </tr>
      <tr>
        <td>float / GT</td>
        <td>
          <bean:write name="bean" property="floatProperty" />
        </td>
        <td>
          <%= doub2 %>
        </td>
        <td>greaterEqual greaterThan notEqual</td>
        <td>
          <logic:equal name="bean" property="floatProperty" value="<%= doub2 %>">equal</logic:equal>
          <logic:greaterEqual name="bean" property="floatProperty" value="<%= doub2 %>">greaterEqual</logic:greaterEqual>
          <logic:greaterThan name="bean" property="floatProperty" value="<%= doub2 %>">greaterThan</logic:greaterThan>
          <logic:lessEqual name="bean" property="floatProperty" value="<%= doub2 %>">lessEqual</logic:lessEqual>
          <logic:lessThan name="bean" property="floatProperty" value="<%= doub2 %>">lessThan</logic:lessThan>
          <logic:notEqual name="bean" property="floatProperty" value="<%= doub2 %>">notEqual</logic:notEqual>
        </td>
      </tr>
      <tr>
        <td>float / LT</td>
        <td>
          <bean:write name="bean" property="floatProperty" />
        </td>
        <td>
          <%= doub3 %>
        </td>
        <td>lessEqual lessThan notEqual</td>
        <td>
          <logic:equal name="bean" property="floatProperty" value="<%= doub3 %>">equal</logic:equal>
          <logic:greaterEqual name="bean" property="floatProperty" value="<%= doub3 %>">greaterEqual</logic:greaterEqual>
          <logic:greaterThan name="bean" property="floatProperty" value="<%= doub3 %>">greaterThan</logic:greaterThan>
          <logic:lessEqual name="bean" property="floatProperty" value="<%= doub3 %>">lessEqual</logic:lessEqual>
          <logic:lessThan name="bean" property="floatProperty" value="<%= doub3 %>">lessThan</logic:lessThan>
          <logic:notEqual name="bean" property="floatProperty" value="<%= doub3 %>">notEqual</logic:notEqual>
        </td>
      </tr>
      <tr>
        <td>int / EQ</td>
        <td>
          <bean:write name="bean" property="intProperty" />
        </td>
        <td>
          <%= long1 %>
        </td>
        <td>lessEqual lessThan notEqual</td>
        <td>
          <logic:equal name="bean" property="intProperty" value="<%= long1 %>">equal</logic:equal>
          <logic:greaterEqual name="bean" property="intProperty" value="<%= long1 %>">greaterEqual</logic:greaterEqual>
          <logic:greaterThan name="bean" property="intProperty" value="<%= long1 %>">greaterThan</logic:greaterThan>
          <logic:lessEqual name="bean" property="intProperty" value="<%= long1 %>">lessEqual</logic:lessEqual>
          <logic:lessThan name="bean" property="intProperty" value="<%= long1 %>">lessThan</logic:lessThan>
          <logic:notEqual name="bean" property="intProperty" value="<%= long1 %>">notEqual</logic:notEqual>
        </td>
      </tr>
      <tr>
        <td>int / GT</td>
        <td>
          <bean:write name="bean" property="intProperty" />
        </td>
        <td>
          <%= long2 %>
        </td>
        <td>greaterEqual greaterThan notEqual</td>
        <td>
          <logic:equal name="bean" property="intProperty" value="<%= long2 %>">equal</logic:equal>
          <logic:greaterEqual name="bean" property="intProperty" value="<%= long2 %>">greaterEqual</logic:greaterEqual>
          <logic:greaterThan name="bean" property="intProperty" value="<%= long2 %>">greaterThan</logic:greaterThan>
          <logic:lessEqual name="bean" property="intProperty" value="<%= long2 %>">lessEqual</logic:lessEqual>
          <logic:lessThan name="bean" property="intProperty" value="<%= long2 %>">lessThan</logic:lessThan>
          <logic:notEqual name="bean" property="intProperty" value="<%= long2 %>">notEqual</logic:notEqual>
        </td>
      </tr>
      <tr>
        <td>int / LT</td>
        <td>
          <bean:write name="bean" property="intProperty" />
        </td>
        <td>
          <%= long3 %>
        </td>
        <td>lessEqual lessThan notEqual</td>
        <td>
          <logic:equal name="bean" property="intProperty" value="<%= long3 %>">equal</logic:equal>
          <logic:greaterEqual name="bean" property="intProperty" value="<%= long3 %>">greaterEqual</logic:greaterEqual>
          <logic:greaterThan name="bean" property="intProperty" value="<%= long3 %>">greaterThan</logic:greaterThan>
          <logic:lessEqual name="bean" property="intProperty" value="<%= long3 %>">lessEqual</logic:lessEqual>
          <logic:lessThan name="bean" property="intProperty" value="<%= long3 %>">lessThan</logic:lessThan>
          <logic:notEqual name="bean" property="intProperty" value="<%= long3 %>">notEqual</logic:notEqual>
        </td>
      </tr>
      <tr>
        <td>long / EQ</td>
        <td>
          <bean:write name="bean" property="longProperty" />
        </td>
        <td>
          <%= long1 %>
        </td>
        <td>equal greaterEqual lessEqual</td>
        <td>
          <logic:equal name="bean" property="longProperty" value="<%= long1 %>">equal</logic:equal>
          <logic:greaterEqual name="bean" property="longProperty" value="<%= long1 %>">greaterEqual</logic:greaterEqual>
          <logic:greaterThan name="bean" property="longProperty" value="<%= long1 %>">greaterThan</logic:greaterThan>
          <logic:lessEqual name="bean" property="longProperty" value="<%= long1 %>">lessEqual</logic:lessEqual>
          <logic:lessThan name="bean" property="longProperty" value="<%= long1 %>">lessThan</logic:lessThan>
          <logic:notEqual name="bean" property="longProperty" value="<%= long1 %>">notEqual</logic:notEqual>
        </td>
      </tr>
      <tr>
        <td>long / GT</td>
        <td>
          <bean:write name="bean" property="longProperty" />
        </td>
        <td>
          <%= long2 %>
        </td>
        <td>greaterEqual greaterThan notEqual</td>
        <td>
          <logic:equal name="bean" property="longProperty" value="<%= long2 %>">equal</logic:equal>
          <logic:greaterEqual name="bean" property="longProperty" value="<%= long2 %>">greaterEqual</logic:greaterEqual>
          <logic:greaterThan name="bean" property="longProperty" value="<%= long2 %>">greaterThan</logic:greaterThan>
          <logic:lessEqual name="bean" property="longProperty" value="<%= long2 %>">lessEqual</logic:lessEqual>
          <logic:lessThan name="bean" property="longProperty" value="<%= long2 %>">lessThan</logic:lessThan>
          <logic:notEqual name="bean" property="longProperty" value="<%= long2 %>">notEqual</logic:notEqual>
        </td>
      </tr>
      <tr>
        <td>long / LT</td>
        <td>
          <bean:write name="bean" property="longProperty" />
        </td>
        <td>
          <%= long3 %>
        </td>
        <td>lessEqual lessThan notEqual</td>
        <td>
          <logic:equal name="bean" property="longProperty" value="<%= long3 %>">equal</logic:equal>
          <logic:greaterEqual name="bean" property="longProperty" value="<%= long3 %>">greaterEqual</logic:greaterEqual>
          <logic:greaterThan name="bean" property="longProperty" value="<%= long3 %>">greaterThan</logic:greaterThan>
          <logic:lessEqual name="bean" property="longProperty" value="<%= long3 %>">lessEqual</logic:lessEqual>
          <logic:lessThan name="bean" property="longProperty" value="<%= long3 %>">lessThan</logic:lessThan>
          <logic:notEqual name="bean" property="longProperty" value="<%= long3 %>">notEqual</logic:notEqual>
        </td>
      </tr>
      <tr>
        <td>short / EQ</td>
        <td>
          <bean:write name="bean" property="shortProperty" />
        </td>
        <td>
          <%= short1 %>
        </td>
        <td>equal greaterEqual lessEqual</td>
        <td>
          <logic:equal name="bean" property="shortProperty" value="<%= short1 %>">equal</logic:equal>
          <logic:greaterEqual name="bean" property="shortProperty" value="<%= short1 %>">greaterEqual</logic:greaterEqual>
          <logic:greaterThan name="bean" property="shortProperty" value="<%= short1 %>">greaterThan</logic:greaterThan>
          <logic:lessEqual name="bean" property="shortProperty" value="<%= short1 %>">lessEqual</logic:lessEqual>
          <logic:lessThan name="bean" property="shortProperty" value="<%= short1 %>">lessThan</logic:lessThan>
          <logic:notEqual name="bean" property="shortProperty" value="<%= short1 %>">notEqual</logic:notEqual>
        </td>
      </tr>
      <tr>
        <td>short / GT</td>
        <td>
          <bean:write name="bean" property="shortProperty" />
        </td>
        <td>
          <%= short2 %>
        </td>
        <td>greaterEqual greaterThan notEqual</td>
        <td>
          <logic:equal name="bean" property="shortProperty" value="<%= short2 %>">equal</logic:equal>
          <logic:greaterEqual name="bean" property="shortProperty" value="<%= short2 %>">greaterEqual</logic:greaterEqual>
          <logic:greaterThan name="bean" property="shortProperty" value="<%= short2 %>">greaterThan</logic:greaterThan>
          <logic:lessEqual name="bean" property="shortProperty" value="<%= short2 %>">lessEqual</logic:lessEqual>
          <logic:lessThan name="bean" property="shortProperty" value="<%= short2 %>">lessThan</logic:lessThan>
          <logic:notEqual name="bean" property="shortProperty" value="<%= short2 %>">notEqual</logic:notEqual>
        </td>
      </tr>
      <tr>
        <td>short / LT</td>
        <td>
          <bean:write name="bean" property="shortProperty" />
        </td>
        <td>
          <%= short3 %>
        </td>
        <td>lessEqual lessThan notEqual</td>
        <td>
          <logic:equal name="bean" property="shortProperty" value="<%= short3 %>">equal</logic:equal>
          <logic:greaterEqual name="bean" property="shortProperty" value="<%= short3 %>">greaterEqual</logic:greaterEqual>
          <logic:greaterThan name="bean" property="shortProperty" value="<%= short3 %>">greaterThan</logic:greaterThan>
          <logic:lessEqual name="bean" property="shortProperty" value="<%= short3 %>">lessEqual</logic:lessEqual>
          <logic:lessThan name="bean" property="shortProperty" value="<%= short3 %>">lessThan</logic:lessThan>
          <logic:notEqual name="bean" property="shortProperty" value="<%= short3 %>">notEqual</logic:notEqual>
        </td>
      </tr>
    </table>
  </body>
</html>
