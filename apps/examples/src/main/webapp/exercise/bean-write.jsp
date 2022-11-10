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
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html>
  <head>
    <title>Test struts-bean:write Tag</title>
  </head>
  <body>
    <div align="center">
      <h1>Test struts-bean:write Tag</h1>
    </div>
    <h3>Test 1 -- Scalar Variable Lookups</h3><%
      pageContext.setAttribute("test1.boolean", Boolean.TRUE);
      pageContext.setAttribute("test1.double", Double.valueOf(321.0));
      pageContext.setAttribute("test1.float", Float.valueOf((float) 123.0));
      pageContext.setAttribute("test1.int", Integer.valueOf(123));
      pageContext.setAttribute("test1.long", Long.valueOf(321));
      pageContext.setAttribute("test1.short", Short.valueOf((short) 987));
      pageContext.setAttribute("test1.string", "This is a string");
    %>
    <table border="1">
      <tr>
        <th>Data Type</th>
        <th>Correct Value</th>
        <th>Test Result</th>
      </tr>
      <tr>
        <td>boolean</td>
        <td>
          <%= pageContext.getAttribute("test1.boolean") %>
        </td>
        <td>
          <bean:write name="test1.boolean" />
        </td>
      </tr>
      <tr>
        <td>double</td>
        <td>
          <%= pageContext.getAttribute("test1.double") %>
        </td>
        <td>
          <bean:write name="test1.double" />
        </td>
      </tr>
      <tr>
        <td>float</td>
        <td>
          <%= pageContext.getAttribute("test1.float") %>
        </td>
        <td>
          <bean:write name="test1.float" />
        </td>
      </tr>
      <tr>
        <td>int</td>
        <td>
          <%= pageContext.getAttribute("test1.int") %>
        </td>
        <td>
          <bean:write name="test1.int" />
        </td>
      </tr>
      <tr>
        <td>long</td>
        <td>
          <%= pageContext.getAttribute("test1.long") %>
        </td>
        <td>
          <bean:write name="test1.long" />
        </td>
      </tr>
      <tr>
        <td>short</td>
        <td>
          <%= pageContext.getAttribute("test1.short") %>
        </td>
        <td>
          <bean:write name="test1.short" />
        </td>
      </tr>
      <tr>
        <td>String</td>
        <td>
          <%= pageContext.getAttribute("test1.string") %>
        </td>
        <td>
          <bean:write name="test1.string" />
        </td>
      </tr>
    </table>
    <h3>Test 2 -- Scalar Property Lookups</h3>
    <jsp:useBean id="test2" scope="page" class="org.apache.struts.webapp.exercise.TestBean" />
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
          <bean:write name="test2" property="booleanProperty" />
        </td>
      </tr>
      <tr>
        <td>double</td>
        <td>
          <jsp:getProperty name="test2" property="doubleProperty" />
        </td>
        <td>
          <bean:write name="test2" property="doubleProperty" />
        </td>
      </tr>
      <tr>
        <td>float</td>
        <td>
          <jsp:getProperty name="test2" property="floatProperty" />
        </td>
        <td>
          <bean:write name="test2" property="floatProperty" />
        </td>
      </tr>
      <tr>
        <td>int</td>
        <td>
          <jsp:getProperty name="test2" property="intProperty" />
        </td>
        <td>
          <bean:write name="test2" property="intProperty" />
        </td>
      </tr>
      <tr>
        <td>long</td>
        <td>
          <jsp:getProperty name="test2" property="longProperty" />
        </td>
        <td>
          <bean:write name="test2" property="longProperty" />
        </td>
      </tr>
      <tr>
        <td>short</td>
        <td>
          <jsp:getProperty name="test2" property="shortProperty" />
        </td>
        <td>
          <bean:write name="test2" property="shortProperty" />
        </td>
      </tr>
      <tr>
        <td>String</td>
        <td>
          <jsp:getProperty name="test2" property="stringProperty" />
        </td>
        <td>
          <bean:write name="test2" property="stringProperty" />
        </td>
      </tr>
    </table>
    <h3>Test 3 - Integer Array And Indexed Lookups</h3>
    <jsp:useBean id="test3" scope="page" class="org.apache.struts.webapp.exercise.TestBean" />
    <table border="1">
      <tr>
        <th>Correct Value</th>
        <th>Array Result</th>
        <th>Indexed Result</th>
      </tr><% for (int index = 0; index < 5; index++) { %>
      <tr>
        <td>
          <%= index * 10 %>
        </td>
        <td>
          <bean:write name="test3" property='<%= "intArray[" + index + "]" %>' />
        </td>
        <td>
          <bean:write name="test3" property='<%= "intIndexed[" + index + "]" %>' />
        </td>
      </tr><% } %>
    </table>
    <h3>Test 4 - String Array And Indexed Lookups</h3>
    <jsp:useBean id="test4" scope="page" class="org.apache.struts.webapp.exercise.TestBean" />
    <table border="1">
      <tr>
        <th>Correct Value</th>
        <th>Array Result</th>
        <th>Indexed Result</th>
      </tr><% for (int index = 0; index < 5; index++) { %>
      <tr>
        <td>
          <%= "String " + index %>
        </td>
        <td>
          <bean:write name="test4" property='<%= "stringArray[" + index + "]" %>' />
        </td>
        <td>
          <bean:write name="test4" property='<%= "stringIndexed[" + index + "]" %>' />
        </td>
      </tr><% } %>
    </table>
    <h3>Test 5 -- Nested Scalar Property Lookups</h3>
    <jsp:useBean id="test5" scope="page" class="org.apache.struts.webapp.exercise.TestBean" />
    <table border="1">
      <tr>
        <th>Data Type</th>
        <th>Correct Value</th>
        <th>Test Result</th>
      </tr>
      <tr>
        <td>boolean</td>
        <td>
          <jsp:getProperty name="test5" property="booleanProperty" />
        </td>
        <td>
          <bean:write name="test5" property="nested.booleanProperty" />
        </td>
      </tr>
      <tr>
        <td>double</td>
        <td>
          <jsp:getProperty name="test5" property="doubleProperty" />
        </td>
        <td>
          <bean:write name="test5" property="nested.doubleProperty" />
        </td>
      </tr>
      <tr>
        <td>float</td>
        <td>
          <jsp:getProperty name="test5" property="floatProperty" />
        </td>
        <td>
          <bean:write name="test5" property="nested.floatProperty" />
        </td>
      </tr>
      <tr>
        <td>int</td>
        <td>
          <jsp:getProperty name="test5" property="intProperty" />
        </td>
        <td>
          <bean:write name="test5" property="nested.intProperty" />
        </td>
      </tr>
      <tr>
        <td>long</td>
        <td>
          <jsp:getProperty name="test5" property="longProperty" />
        </td>
        <td>
          <bean:write name="test5" property="nested.longProperty" />
        </td>
      </tr>
      <tr>
        <td>short</td>
        <td>
          <jsp:getProperty name="test5" property="shortProperty" />
        </td>
        <td>
          <bean:write name="test5" property="nested.shortProperty" />
        </td>
      </tr>
      <tr>
        <td>String</td>
        <td>
          <jsp:getProperty name="test5" property="stringProperty" />
        </td>
        <td>
          <bean:write name="test5" property="nested.stringProperty" />
        </td>
      </tr>
    </table>
    <h3>Test 6 - Nested Integer Array And Indexed Lookups</h3>
    <jsp:useBean id="test6" scope="page" class="org.apache.struts.webapp.exercise.TestBean" />
    <table border="1">
      <tr>
        <th>Correct Value</th>
        <th>Array Result</th>
        <th>Indexed Result</th>
      </tr><% for (int index = 0; index < 5; index++) { %>
      <tr>
        <td>
          <%= index * 10 %>
        </td>
        <td>
          <bean:write name="test6" property='<%= "nested.intArray[" + index + "]" %>' />
        </td>
        <td>
          <bean:write name="test6" property='<%= "nested.intIndexed[" + index + "]" %>' />
        </td>
      </tr><% } %>
    </table>
  </body>
</html>
