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
    <h3>Test 7 - Localized format patterns</h3><%
      pageContext.setAttribute("test7.double", Double.valueOf(1234567.89));
      pageContext.setAttribute("test7.date", new java.util.Date(123456789));
    %>
    <h4>Doubles</h4>
    <table style="border:none">
      <tr>
        <td>
          <table border="1">
            <tr>
              <th>Language</th>
              <th>Double format</th>
            </tr>
            <tr>
              <td>de
              <bean:message key="locale.de" /></td>
              <td>1.234.567,89</td>
            </tr>
            <tr>
              <td>en
              <bean:message key="locale.en" /></td>
              <td>1,234,567.89</td>
            </tr>
            <tr>
              <td>fr
              <bean:message key="locale.fr" /></td>
              <td>1 234 567,89</td>
            </tr>
          </table>
        </td>
        <td>
          <table border="1">
            <tr>
              <th>Default format</th>
              <th>Using Format Attribute</th>
              <th>Using Format Key</th>
            </tr>
            <tr>
              <td>
                <%= pageContext.getAttribute("test7.double") %>
              </td>
              <td>[#,000.00]
              <bean:write name="test7.double" format="#,000.00" /></td>
              <td>[
              <bean:message key="double.pattern" />]
              <bean:write name="test7.double" formatKey="double.pattern" /></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <h4>Dates</h4>
    <table style="border:none">
      <tr>
        <td>
          <table border="1">
            <tr>
              <th>Language</th>
              <th>Date format</th>
            </tr>
            <tr>
              <td>de
              <bean:message key="locale.de" /></td>
              <td>Fr, Jan 2, '70</td>
            </tr>
            <tr>
              <td>en
              <bean:message key="locale.en" /></td>
              <td>Fri, Jan 2, '70</td>
            </tr>
            <tr>
              <td>fr
              <bean:message key="locale.fr" /></td>
              <td>ven., janv. 2, '70</td>
            </tr>
          </table>
        </td>
        <td>
          <table border="1">
            <tr>
              <th>Default format</th>
              <th>Using Format Attribute</th>
              <th>Using Format Key</th>
            </tr>
            <tr>
              <td>
                <%= pageContext.getAttribute("test7.date") %>
              </td>
              <td>[EEE, MMM d, ''yy]
              <bean:write name="test7.date" format="EEE, MMM d, ''yy" /></td>
              <td>[
              <bean:message key="date.pattern" />]
              <bean:write name="test7.date" formatKey="date.pattern" /></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <h4>Sprache | Language | Langage</h4>
    <ul>
      <li>
        <html:link action="/locale?page=/bean-write-2.jsp&language=de">German | Deutsch</html:link>
        <bean:message key="locale.de" />
      </li>
      <li>
        <html:link action="/locale?page=/bean-write-2.jsp&language=en">English | Anglais</html:link>
        <bean:message key="locale.en" />
      </li>
      <li>
        <html:link action="/locale?page=/bean-write-2.jsp&language=fr">French | Francais</html:link>
        <bean:message key="locale.fr" />
      </li>
    </ul>
  </body>
</html>
