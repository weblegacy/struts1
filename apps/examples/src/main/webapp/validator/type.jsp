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
<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %><%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %><%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %><%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<html:html>
  <head>
    <title>
      <bean:message key="typeForm.title" />
    </title>
    <html:base />
  </head>
  <body bgcolor="white">
    <p><html:link action="showStrutsConfigType" target="_blank">struts-config-type.xml</html:link> &nbsp;
       <html:link action="showValidationType" target="_blank">validation-type.xml</html:link></p>

    <logic:messagesPresent>
      <bean:message key="errors.header" />
      <hr />
    </logic:messagesPresent>
    <html:form action="type-submit">
      <html:hidden property="action" />
      <table style="border:none">
        <tr>
          <th align="left">
            <bean:message key="typeForm.byte.displayname" />
          </th>
          <td align="left">
            <html:text property="byte" size="15" maxlength="15" errorStyle="background-color: yellow" />
          </td>
          <td align="left">&nbsp;
            <html:messages property="byte" id="error"><bean:write name="error" /></html:messages>
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.short.displayname" />
          </th>
          <td align="left">
            <html:text property="short" size="15" maxlength="15" errorStyle="background-color: yellow" />
          </td>
          <td align="left">&nbsp;
            <html:messages property="short" id="error"><bean:write name="error" /></html:messages>
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.integer.displayname" />
          </th>
          <td align="left">
            <html:text property="integer" size="15" maxlength="15" errorStyle="background-color: yellow" />
          </td>
          <td align="left">&nbsp;
            <html:messages property="integer" id="error"><bean:write name="error" /></html:messages>
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.intRange.displayname" />
          </th>
          <td align="left">
            <html:text property="intRange" size="15" maxlength="15" errorStyle="background-color: yellow" />
          </td>
          <td align="left">&nbsp;
            <html:messages property="intRange" id="error"><bean:write name="error" /></html:messages>
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.long.displayname" />
          </th>
          <td align="left">
            <html:text property="long" size="15" maxlength="15" errorStyle="background-color: yellow" />
          </td>
          <td align="left">&nbsp;
            <html:messages property="long" id="error"><bean:write name="error" /></html:messages>
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.float.displayname" />
          </th>
          <td align="left">
            <html:text property="float" size="15" maxlength="15" errorStyle="background-color: yellow" />
          </td>
          <td align="left">&nbsp;
            <html:messages property="float" id="error"><bean:write name="error" /></html:messages>
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.floatRange.displayname" />
          </th>
          <td align="left">
            <html:text property="floatRange" size="15" maxlength="15" errorStyle="background-color: yellow" />
          </td>
          <td align="left">&nbsp;
            <html:messages property="floatRange" id="error"><bean:write name="error" /></html:messages>
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.double.displayname" />
          </th>
          <td align="left">
            <html:text property="double" size="15" maxlength="15" errorStyle="background-color: yellow" />
          </td>
          <td align="left">&nbsp;
            <html:messages property="double" id="error"><bean:write name="error" /></html:messages>
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.date.displayname" />
          </th>
          <td align="left">
            <html:text property="date" size="15" maxlength="15" errorStyle="background-color: yellow" />
          </td>
          <td align="left">&nbsp;
            <html:messages property="date" id="error"><bean:write name="error" /></html:messages>
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.creditCard.displayname" />
          </th>
          <td align="left">
             <html:text property="creditCard" size="16" maxlength="16" errorStyle="background-color: yellow" />
          </td>
          <td align="left">
            <html:messages property="creditCard" id="error"><bean:write name="error" /></html:messages>
            &nbsp;(e.g. 4111111111111111, 5500000000000004)
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.email.displayname" />
          </th>
          <td align="left">
            <html:text property="email" size="15" maxlength="15" errorStyle="background-color: yellow" />
          </td>
          <td align="left">&nbsp;
            <html:messages property="email" id="error"><bean:write name="error" /></html:messages>
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.mask.displayname" />
          </th>
          <td align="left">
            <html:text property="mask" size="15" maxlength="15" errorStyle="background-color: yellow" />
          </td>
          <td align="left">&nbsp;
            <html:messages property="mask" id="error"><bean:write name="error" /></html:messages>
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.minMaxLength.displayname" />
          </th>
          <td align="left">
            <html:textarea property="minMaxLength" rows="2" cols="30" errorStyle="background-color: yellow" />
          </td>
          <td align="left">&nbsp;
            <html:messages property="minMaxLength" id="error"><bean:write name="error" /></html:messages>
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.url.displayname" />
          </th>
          <td align="left">
            <html:text property="url" size="30" maxlength="100" errorStyle="background-color: yellow" />
          </td>
          <td align="left">&nbsp;
            <html:messages property="url" id="error"><bean:write name="error" /></html:messages>
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.nested" />
          </th>
          <td align="left">&#160;</td>
          <td align="left">&#160;</td>
        </tr>
        <nested:iterate property="nameList">
          <tr>
            <th align="left">&#160;</th>
            <td align="left">
              <nested:text property="value" size="15" maxlength="15" errorStyle="background-color: yellow" />
            </td>
            <td align="left">&nbsp;
              <nested:messagesPresent property="value">
                  <nested:messages id="error" property="value">
                      <bean:write name="error" />
                  </nested:messages>
              </nested:messagesPresent>
            </td>
          </tr>
        </nested:iterate>
        <tr>
          <td>
          <html:submit property="submit">
            <bean:message key="button.save" />
          </html:submit>&#160;
          <html:reset>
            <bean:message key="button.reset" />
          </html:reset>&#160;
          <html:cancel>
            <bean:message key="button.cancel" />
          </html:cancel></td>
        </tr>
      </table>
    </html:form>
  </body>
</html:html>
