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
    <title>Test struts-bean:define Tag</title>
  </head>
  <body>
    <div align="center">
      <h1>Test struts-bean:define Tag</h1>
    </div>
    <h3>Test 1 -- Direct Scalar Variable Defines</h3>
    <jsp:useBean id="test1" scope="page" class="org.apache.struts.webapp.exercise.TestBean" />
    <bean:define id="test1_boolean" name="test1" property="booleanProperty" />
    <bean:define id="test1_double" name="test1" property="doubleProperty" />
    <bean:define id="test1_float" name="test1" property="floatProperty" />
    <bean:define id="test1_int" name="test1" property="intProperty" />
    <bean:define id="test1_long" name="test1" property="longProperty" />
    <bean:define id="test1_short" name="test1" property="shortProperty" />
    <bean:define id="test1_string" name="test1" property="stringProperty" />
    <bean:define id="test1_value" value="ABCDE" />
    <table border="1">
      <tr>
        <th>Data Type</th>
        <th>Correct Value</th>
        <th>Test Result</th>
      </tr>
      <tr>
        <td>boolean</td>
        <td>
          <jsp:getProperty name="test1" property="booleanProperty" />
        </td>
        <td>
          <%= test1_boolean %>
        </td>
      </tr>
      <tr>
        <td>double</td>
        <td>
          <jsp:getProperty name="test1" property="doubleProperty" />
        </td>
        <td>
          <%= test1_double %>
        </td>
      </tr>
      <tr>
        <td>float</td>
        <td>
          <jsp:getProperty name="test1" property="floatProperty" />
        </td>
        <td>
          <%= test1_float %>
        </td>
      </tr>
      <tr>
        <td>int</td>
        <td>
          <jsp:getProperty name="test1" property="intProperty" />
        </td>
        <td>
          <%= test1_int %>
        </td>
      </tr>
      <tr>
        <td>long</td>
        <td>
          <jsp:getProperty name="test1" property="longProperty" />
        </td>
        <td>
          <%= test1_long %>
        </td>
      </tr>
      <tr>
        <td>short</td>
        <td>
          <jsp:getProperty name="test1" property="shortProperty" />
        </td>
        <td>
          <%= test1_short %>
        </td>
      </tr>
      <tr>
        <td>string</td>
        <td>
          <jsp:getProperty name="test1" property="stringProperty" />
        </td>
        <td>
          <%= test1_string %>
        </td>
      </tr>
      <tr>
        <td>value</td>
        <td>
          <bean:write name="test1_value" />
        </td>
        <td>
          <%= test1_value %>
        </td>
      </tr>
    </table>
    <h3>Test 2 -- Nested Scalar Variable Defines</h3>
    <jsp:useBean id="test2" scope="page" class="org.apache.struts.webapp.exercise.TestBean" />
    <bean:define id="test2_boolean" name="test2" property="nested.booleanProperty" />
    <bean:define id="test2_double" name="test2" property="nested.doubleProperty" />
    <bean:define id="test2_float" name="test2" property="nested.floatProperty" />
    <bean:define id="test2_int" name="test2" property="nested.intProperty" />
    <bean:define id="test2_long" name="test2" property="nested.longProperty" />
    <bean:define id="test2_short" name="test2" property="nested.shortProperty" />
    <bean:define id="test2_string" name="test2" property="nested.stringProperty" />
    <table border="1">
      <tr>
        <th>Data Type</th>
        <th>Correct Value</th>
        <th>Test Result</th>
      </tr>
      <tr>
        <td>boolean</td>
        <td>
          <jsp:getProperty name="test2" property="booleanProperty" />
        </td>
        <td>
          <%= test2_boolean %>
        </td>
      </tr>
      <tr>
        <td>double</td>
        <td>
          <jsp:getProperty name="test2" property="doubleProperty" />
        </td>
        <td>
          <%= test2_double %>
        </td>
      </tr>
      <tr>
        <td>float</td>
        <td>
          <jsp:getProperty name="test2" property="floatProperty" />
        </td>
        <td>
          <%= test2_float %>
        </td>
      </tr>
      <tr>
        <td>int</td>
        <td>
          <jsp:getProperty name="test2" property="intProperty" />
        </td>
        <td>
          <%= test2_int %>
        </td>
      </tr>
      <tr>
        <td>long</td>
        <td>
          <jsp:getProperty name="test2" property="longProperty" />
        </td>
        <td>
          <%= test2_long %>
        </td>
      </tr>
      <tr>
        <td>short</td>
        <td>
          <jsp:getProperty name="test2" property="shortProperty" />
        </td>
        <td>
          <%= test2_short %>
        </td>
      </tr>
      <tr>
        <td>string</td>
        <td>
          <jsp:getProperty name="test2" property="stringProperty" />
        </td>
        <td>
          <%= test2_string %>
        </td>
      </tr>
    </table>
  </body>
</html>
