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
<%@ page import="org.apache.struts.action.*, org.apache.struts.Globals" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html:html>
  <head>
    <title>Test Error and Message Tags</title><%
      ActionMessages errors = new ActionMessages();
      errors.add("property1", new ActionMessage("property1error1"));
      errors.add("property2", new ActionMessage("property2error1"));
      errors.add("property2", new ActionMessage("property2error2"));
      errors.add("property2", new ActionMessage("property2error3"));
      errors.add("property3", new ActionMessage("property3error1"));
      errors.add("property3", new ActionMessage("property3error2"));
      errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("globalError"));
      request.setAttribute(Globals.ERROR_KEY, errors);

      ActionMessages messages = new ActionMessages();
      messages.add("property1", new ActionMessage("property1message1"));
      messages.add("property2", new ActionMessage("property2message1"));
      messages.add("property2", new ActionMessage("property2message2"));
      messages.add("property2", new ActionMessage("property2message3"));
      messages.add("property3", new ActionMessage("property3message1"));
      messages.add("property3", new ActionMessage("property3message2"));
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("globalMessage"));
      request.setAttribute(Globals.MESSAGE_KEY, messages);
    %>
  </head>
  <body bgcolor="white">
    <div align="center">
      <h1>Test Error and Message Tags</h1>
    </div>
    <h3>ERRORS Tag</h3>
    <table border="1">
      <tr>
        <th>Test Type</th>
        <th>Correct Value</th>
        <th>Test Result</th>
      </tr>
      <tr>
        <td>Errors for Property 1</td>
        <td>
          <table>
            <tr>
              <td>Property 1, Error 1</td>
            </tr>
          </table>
        </td>
        <td>
          <html:errors property="property1" />
        </td>
      </tr>
      <tr>
        <td>Errors for Property 2</td>
        <td>
          <table>
            <tr>
              <td>Property 2, Error 1</td>
            </tr>
            <tr>
              <td>Property 2, Error 2</td>
            </tr>
            <tr>
              <td>Property 2, Error 3</td>
            </tr>
          </table>
        </td>
        <td>
          <html:errors property="property2" />
        </td>
      </tr>
      <tr>
        <td>All Errors</td>
        <td>
          <table>
            <tr>
              <td>Property 1, Error 1</td>
            </tr>
            <tr>
              <td>Property 2, Error 1</td>
            </tr>
            <tr>
              <td>Property 2, Error 2</td>
            </tr>
            <tr>
              <td>Property 2, Error 3</td>
            </tr>
            <tr>
              <td>Property 3, Error 1</td>
            </tr>
            <tr>
              <td>Property 3, Error 2</td>
            </tr>
            <tr>
              <td>Global Error</td>
            </tr>
          </table>
        </td>
        <td>
          <html:errors />
        </td>
      </tr>
    </table>
    <h3>MESSAGES Tag</h3>
    <table border="1">
      <tr>
        <th>Test Type</th>
        <th>Correct Value</th>
        <th>Test Result</th>
      </tr>
      <tr>
        <td>Messages for Property 1</td>
        <td>
          <table>
            <tr>
              <td>Property 1, Message 1</td>
            </tr>
          </table>
        </td>
        <td>
          <html:messages property="property1" message="true" id="msg" header="messages.header" footer="messages.footer">
            <tr>
              <td>
                <%= pageContext.getAttribute("msg") %>
              </td>
            </tr>
          </html:messages>
        </td>
      </tr>
      <tr>
        <td>Messages for Property 2</td>
        <td>
          <table>
            <tr>
              <td>Property 2, Message 1</td>
            </tr>
            <tr>
              <td>Property 2, Message 2</td>
            </tr>
            <tr>
              <td>Property 2, Message 3</td>
            </tr>
          </table>
        </td>
        <td>
          <html:messages property="property2" message="true" id="msg" header="messages.header" footer="messages.footer">
            <tr>
              <td>
                <%= pageContext.getAttribute("msg") %>
              </td>
            </tr>
          </html:messages>
        </td>
      </tr>
      <tr>
        <td>All Messages</td>
        <td>
          <table>
            <tr>
              <td>Property 1, Message 1</td>
            </tr>
            <tr>
              <td>Property 2, Message 1</td>
            </tr>
            <tr>
              <td>Property 2, Message 2</td>
            </tr>
            <tr>
              <td>Property 2, Message 3</td>
            </tr>
            <tr>
              <td>Property 3, Message 1</td>
            </tr>
            <tr>
              <td>Property 3, Message 2</td>
            </tr>
            <tr>
              <td>Global Message</td>
            </tr>
          </table>
        </td>
        <td>
          <html:messages message="true" id="msg" header="messages.header" footer="messages.footer">
            <tr>
              <td>
                <%= pageContext.getAttribute("msg") %>
              </td>
            </tr>
          </html:messages>
        </td>
      </tr>
    </table>
  </body>
</html:html>
