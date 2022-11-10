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
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html:html>
  <head>
    <title>Test html:link Tag</title><%
          String newValue = "New string value";
          pageContext.setAttribute("newValue", newValue);
          java.util.HashMap<String, Object> newValues = new java.util.HashMap<String, Object>();
          newValues.put("floatProperty", Float.valueOf((float) 444.0));
          newValues.put("intProperty", Integer.valueOf(555));
          newValues.put("stringArray", new String[]
           { "Value 1", "Value 2", "Value 3" });
          pageContext.setAttribute("newValues", newValues);
        %>
  </head>
  <body>
    <div align="center">
      <h1>Test struts-html Link Tag</h1>
    </div>
    <p>The following links should hyperlink back to this page, with various combinations of request parameters used to modify the previous values of the associated form bean. Press the "Cancel" button to return to the main menu.</p>
    <html:form action="/html-link-submit">
      <table style="border:none;width:100%">
        <tr>
          <th colspan="4" align="center">Current Values</th>
        </tr>
        <tr>
          <th align="right">booleanProperty</th>
          <td align="left">
            <html:checkbox property="booleanProperty" />
          </td>
          <th align="right">intProperty</th>
          <td align="left">
            <html:text property="intProperty" size="16" />
          </td>
        </tr>
        <tr>
          <th align="right">doubleProperty</th>
          <td align="left">
            <html:text property="doubleProperty" size="16" />
          </td>
          <th align="right">longProperty</th>
          <td align="left">
            <html:text property="longProperty" size="16" />
          </td>
        </tr>
        <tr>
          <th align="right">floatProperty</th>
          <td align="left">
            <html:text property="floatProperty" size="16" />
          </td>
          <th align="right">stringProperty</th>
          <td align="left">
            <html:text property="stringProperty" size="16" />
          </td>
        </tr>
        <tr>
          <th align="right">stringArray</th>
          <td align="left" colspan="3">
            <html:text property="stringArray[0]" size="16" />
            <html:text property="stringArray[1]" size="16" />
          </td>
        </tr>
        <tr>
          <th colspan="4" align="center">Hyperlinks To Be Tested via page attribute</th>
        </tr>
        <tr>
          <td colspan="4" align="center">
            <html:link action="/html-link-submit">No modifications at all</html:link>
          </td>
        </tr>
        <tr>
          <td colspan="4" align="center">
            <html:link action="/html-link-submit?doubleProperty=321.321&amp;longProperty=321321">Double and long via hard coded changes</html:link>
          </td>
        </tr>
        <tr>
          <td colspan="4" align="center">
            <html:link action="/html-link-submit" paramId="stringProperty" paramName="newValue">String via paramId and paramName</html:link>
          </td>
        </tr>
        <tr>
          <td colspan="4" align="center">
            <html:link action="/html-link-submit" paramId="booleanProperty" paramName="testbean" paramProperty="nested.booleanProperty">Boolean via paramId, paramName, and paramValue</html:link>
          </td>
        </tr>
        <tr>
          <td colspan="4" align="center">
            <html:link action="/html-link-submit" name="newValues">Float, int, and stringArray via name (Map)</html:link>
          </td>
        </tr>
        <tr>

 <tr>
   <th colspan="4" align="center">
     Hyperlinks To Be Tested via action and module attributes
   </th>
 </tr>

 <tr>
   <td colspan="4" align="center">
     <html:link action="/html-link">
       No modifications at all, but link to the default module.
     </html:link>
   </td>
 </tr>

 <tr>
   <td colspan="4" align="center">
     <html:link action="/html-link?doubleProperty=321.321&amp;longProperty=321321"
             module="/exercise">
       Double and long via hard coded changes (module)
     </html:link>
   </td>
 </tr>

 <tr>
   <td colspan="4" align="center">
     <html:link action="/html-link"
             paramId="stringProperty" paramName="newValue">
       String via paramId and paramName
     </html:link>
   </td>
 </tr>

 <tr>
   <td colspan="4" align="center">
     <html:link action="/html-link"
             paramId="booleanProperty"
             paramName="testbean" paramProperty="nested.booleanProperty"
             module="/exercise">
       Boolean via paramId, paramName, and paramValue (module)
     </html:link>
   </td>
 </tr>

 <tr>
   <td colspan="4" align="center">
     <html:link action="/html-link"
                name="newValues">
       Float, int, and stringArray via name (Map)
     </html:link>
   </td>
 </tr>

   <tr>
     <th colspan="4" align="center">Reset and Cancel Buttons</th>
        </tr>
        <tr>
          <td colspan="4" align="center">
            <html:reset>Reset</html:reset>
            <html:cancel>Cancel</html:cancel>
          </td>
        </tr>
      </table>
    </html:form>

    <p>The following list tests relative, context-relative, and absolute links. Press (back) to return.</p>
    <p>Forwards</p>
    <ul>
      <li>
        <html:link forward="relative">module welcome page</html:link>
      </li>
      <li>
        <html:link forward="context-relative">application welcome page (context relative attribute)</html:link>
      </li>
      <li>
        <html:link forward="module-root">application welcome page (module attribute)</html:link>
      </li>
      <li>
        <html:link forward="module-validator">validator welcome page</html:link>
      </li>
      <li>
        <html:link forward="absolute">Struts website</html:link>
      </li>
      <li>
        <html:link forward="redirect-default">Redirect to the default page ("/")</html:link>
      </li>
    </ul>
    <p>Actions</p>
    <ul>
      <li>
        <html:link action="/welcome" module="/">application welcome page (module="/")</html:link>
      </li>
      <li>
        <html:link action="/welcome" module="">application welcome page (module="")</html:link>
      </li>
      <li>
        <html:link action="/welcome" module="/exercise">Exercise module welcome page</html:link>
      </li>
      <li>
        <html:link action="/upload" module="/upload">Upload welcome page</html:link>
      </li>
      <li>
        <html:link action="/welcome" module="/validator">Validator welcome page</html:link>
      </li>
      </ul>

    <p>Pages</p>
    <ul>
      <li>
        <html:link page="/welcome.do" module="">application welcome page (module="")</html:link>
      </li>
      <li>
        <html:link page="/welcome.do" module="/">application welcome page (module="/")</html:link>
      </li>
      <li>
        <html:link page="/welcome.do" module="/exercise">Exercise module welcome page</html:link>
      </li>
      <li>
        <html:link page="/upload.do" module="/upload">Upload welcome page</html:link>
      </li>
      <li>
        <html:link page="/welcome.do" module="/validator">Validator welcome page</html:link>
      </li>
      </ul>
  </body>
</html:html>
