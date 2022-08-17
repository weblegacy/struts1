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
    <title>Test struts-bean Size Tag</title>
  </head>
  <body>
    <%
        java.util.ArrayList<String> data = new java.util.ArrayList<String>();
        data.add("First");
        data.add("Second");
        data.add("Third");
        data.add("Fourth");
        data.add("Fifth");
        pageContext.setAttribute("list", data, PageContext.PAGE_SCOPE);
        java.util.HashMap<String, String> temp = new java.util.HashMap<String, String>();
        temp.put("First", "0");
        temp.put("Second", "1");
        temp.put("Third", "2");
        temp.put("Fourth", "3");
        temp.put("Fifth", "4");
        pageContext.setAttribute("map", temp, PageContext.PAGE_SCOPE);
    %>
    <bean:size id="dataSize" collection="<%= data %>" />
    <bean:size id="tempSize" collection="<%= temp %>" />
    <div align="center">
      <h1>Test struts-bean Size Tag</h1>
    </div>
    <jsp:useBean id="bean" scope="page" class="org.apache.struts.webapp.exercise.TestBean" />
    <bean:size id="stringSize" name="bean" property="stringArray" />
    <bean:size id="intSize" name="bean" property="intArray" />
    <jsp:useBean id="list" scope="page" class="java.util.ArrayList" />
    <bean:size id="listSize" name="list" />
    <jsp:useBean id="map" scope="page" class="java.util.HashMap" />
    <bean:size id="mapSize" name="map" />
    <table border="1">
      <tr>
        <th>Collection Type</th>
        <th>Correct Value</th>
        <th>Test Result</th>
      </tr>
      <tr>
        <td>Bean (List)</td>
        <td align="center">
          <%= list.size() %>
        </td>
        <td align="center">
          <bean:write name="listSize" />
        </td>
      </tr>
      <tr>
        <td>Bean (Map)</td>
        <td align="center">
          <%= map.size() %>
        </td>
        <td align="center">
          <bean:write name="mapSize" />
        </td>
      </tr>
      <tr>
        <td>Collection (List)</td>
        <td align="center">
          <%= data.size() %>
        </td>
        <td align="center">
          <bean:write name="dataSize" />
        </td>
      </tr>
      <tr>
        <td>Collection (Map)</td>
        <td align="center">
          <%= temp.size() %>
        </td>
        <td align="center">
          <bean:write name="tempSize" />
        </td>
      </tr>
      <tr>
        <td>Property (int[])</td>
        <td align="center">
          <% int intValues[] = bean.getIntArray(); out.print(intValues.length); %>
        </td>
        <td align="center">
          <bean:write name="intSize" />
        </td>
      </tr>
      <tr>
        <td>Property (String[])</td>
        <td align="center">
          <% String stringValues[] = bean.getStringArray(); out.print(stringValues.length); %>
        </td>
        <td align="center">
          <bean:write name="stringSize" />
        </td>
      </tr>
    </table>
  </body>
</html>
