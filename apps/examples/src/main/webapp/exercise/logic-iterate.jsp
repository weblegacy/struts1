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
    <title>Test struts-logic Iterate Tag</title>
  </head>
  <body bgcolor="white">
    <%
      {
        java.util.ArrayList<String> list = new java.util.ArrayList<String>();
        list.add("First");
        list.add("Second");
        list.add("Third");
        list.add("Fourth");
        list.add("Fifth");
        pageContext.setAttribute("list", list, PageContext.PAGE_SCOPE);

        int intArray[] = new int[]
         { 0, 10, 20, 30, 40 };
        pageContext.setAttribute("intArray", intArray, PageContext.PAGE_SCOPE);
      }
    %>
    <div align="center">
      <h1>Test struts-logic Iterate Tag</h1>
    </div>
    <jsp:useBean id="bean" scope="page" class="org.apache.struts.webapp.exercise.TestBean" />
    <jsp:useBean id="list" scope="page" class="java.util.ArrayList" />
    <h3>Test 1 - Iterate Over A String Array [0..4]</h3>
    <ol>
      <logic:iterate id="element" name="bean" property="stringArray" indexId="index">
        <li>
        <em>
          <bean:write name="element" />
        </em>&#160;[
        <bean:write name="index" />]</li>
      </logic:iterate>
    </ol>
    <h3>Test 2 - Iterate Over A String Array [0..2]</h3>
    <ol>
      <logic:iterate id="element" name="bean" property="stringArray" indexId="index" length="3">
        <li>
        <em>
          <bean:write name="element" />
        </em>&#160;[
        <bean:write name="index" />]</li>
      </logic:iterate>
    </ol>
    <h3>Test 3 - Iterate Over A String Array [3..4]</h3>
    <ol>
      <logic:iterate id="element" name="bean" property="stringArray" indexId="index" offset="3">
        <li>
        <em>
          <bean:write name="element" />
        </em>&#160;[
        <bean:write name="index" />]</li>
      </logic:iterate>
    </ol>
    <h3>Test 4 - Iterate Over A String Array [1..3]</h3>
    <ol>
      <logic:iterate id="element" name="bean" property="stringArray" indexId="index" offset="1" length="3">
        <li>
        <em>
          <bean:write name="element" />
        </em>&#160;[
        <bean:write name="index" />]</li>
      </logic:iterate>
    </ol>
    <h3>Test 5 - Iterate Over an Array List</h3>
    <ol>
      <logic:iterate id="item" name="list" indexId="index">
        <li>
        <em>
          <bean:write name="item" />
        </em>&#160;[
        <bean:write name="index" />]</li>
      </logic:iterate>
    </ol>
    <h3>Test 6 - Iterate Over an Array List [0..2]</h3>
    <ol>
      <logic:iterate id="item" name="list" indexId="index" offset="0" length="3">
        <li>
        <em>
          <bean:write name="item" />
        </em>&#160;[
        <bean:write name="index" />]</li>
      </logic:iterate>
    </ol>
    <h3>Test 7 - Iterate Over an Array List [2..4]</h3>
    <ol>
      <logic:iterate id="item" name="list" indexId="index" offset="2" length="3">
        <li>
        <em>
          <bean:write name="item" />
        </em>&#160;[
        <bean:write name="index" />]</li>
      </logic:iterate>
    </ol>
    <h3>Test 8 - Iterate Over an int array</h3>
    <ol>
      <logic:iterate id="item" name="intArray" indexId="index">
        <li>
        <em>
          <bean:write name="item" />
        </em>&#160;[
        <bean:write name="index" />]</li>
      </logic:iterate>
    </ol>
    <h3>Test 9 - Iterate Over an int array [0..2]</h3>
    <ol>
      <logic:iterate id="item" name="intArray" indexId="index" length="3">
        <li>
        <em>
          <bean:write name="item" />
        </em>&#160;[
        <bean:write name="index" />]</li>
      </logic:iterate>
    </ol>
    <h3>Test 10 - Iterate Over an int array [2..4]</h3>
    <ol>
      <logic:iterate id="item" name="intArray" indexId="index" offset="2" length="3">
        <li>
        <em>
          <bean:write name="item" />
        </em>&#160;[
        <bean:write name="index" />]</li>
      </logic:iterate>
    </ol>
    <h3>Test 11 - Iterate over a List using the 'collection' attribute</h3>
    <ol>
      <logic:iterate id="item" collection="<%= list %>" indexId="index">
        <li>
        <em>
          <bean:write name="item" />
        </em>&#160;[
        <bean:write name="index" />]</li>
      </logic:iterate>
    </ol>
  </body>
</html>
