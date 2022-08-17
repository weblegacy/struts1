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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:html>
  <head>
    <title>
      <bean:message key="typeForm.title" />
    </title>
    <html:base />
  </head>
  <body>
    <p><html:link action="showStrutsConfigType" target="_blank">struts-config-type.xml</html:link> &nbsp;
       <html:link action="showValidationType" target="_blank">validation-type.xml</html:link></p>

    <logic:messagesPresent>
      <bean:message key="errors.header" />
      <ul>
        <html:messages id="error">
          <li>
            <bean:write name="error" />
          </li>
        </html:messages>
      </ul>
      <hr />
    </logic:messagesPresent>
    <html:form action="jsType-submit" onsubmit="return validateJsTypeForm(this);">
      <html:hidden property="action" />
      <table style="border:none">
        <tr>
          <th align="left">
            <bean:message key="typeForm.name.displayname" />
          </th>
          <td align="left">
            <html:text property="name" size="15" maxlength="15" />
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.byte.displayname" />
          </th>
          <td align="left">
            <html:text property="byte" size="15" maxlength="15" />
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.short.displayname" />
          </th>
          <td align="left">
            <html:text property="short" size="15" maxlength="15" />
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.integer.displayname" />
          </th>
          <td align="left">
            <html:text property="integer" size="15" maxlength="15" />
          </td>
        <tr>
          <th align="left">
            <bean:message key="typeForm.intRange.displayname" />
          </th>
          <td align="left">
            <html:text property="intRange" size="15" maxlength="15" />
        </td>
        </tr><%--
                  <tr>
                    <th align="left">
                      <bean:message key="typeForm.long.displayname"/>
                    </th>
                    <td align="left">
                      <html:text property="long" size="15" maxlength="15"/>
                    </td>
                  </tr>
                --%>
        <tr>
          <th align="left">
            <bean:message key="typeForm.float.displayname" />
          </th>
          <td align="left">
            <html:text property="float" size="15" maxlength="15" />
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.floatRange.displayname" />
          </th>
          <td align="left">
            <html:text property="floatRange" size="15" maxlength="15" />
          </td>
        </tr><%--
                  <tr>
                    <th align="left">
                      <bean:message key="typeForm.double.displayname"/>
                    </th>
                    <td align="left">
                      <html:text property="double" size="15" maxlength="15"/>
                    </td>
                  </tr>
                --%>
        <tr>
          <th align="left">
            <bean:message key="typeForm.date.displayname" />
          </th>
          <td align="left">
            <html:text property="date" size="15" maxlength="15" />
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.creditCard.displayname" />
          </th>
          <td align="left">
          <html:text property="creditCard" size="16" maxlength="16" />(e.g. 4111111111111111, 5500000000000004)</td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.email.displayname" />
          </th>
          <td align="left">
            <html:text property="email" size="15" maxlength="15" />
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.mask.displayname" />
          </th>
          <td align="left">
            <html:text property="mask" size="15" maxlength="15" />
          </td>
        </tr>
        <tr>
          <th align="left">
            <bean:message key="typeForm.minMaxLength.displayname" />
          </th>
          <td align="left">
            <html:textarea property="minMaxLength" rows="2" cols="30" />
          </td>
        </tr>
        <tr>
          <th align="left">
          <bean:message key="typeForm.option.satisfaction" />:</th>
          <td align="left">
            <html:select property="satisfaction">
              <html:option value="" key="typeForm.option.select.one" />
              <html:options collection="satisfactionList" property="value" labelProperty="label" />
            </html:select>
          </td>
        </tr>
        <tr>
          <th align="left">
          <bean:message key="typeForm.option.os.list" />:</th>
          <td align="left">
            <html:select property="osList" multiple="true">
              <html:option value="" key="typeForm.option.select.many" />
              <html:options collection="osTypes" property="value" labelProperty="label" />
            </html:select>
          </td>
        </tr>
        <tr>
          <th align="left">
          <bean:message key="typeForm.radio.overall.satisfaction" />:</th>
          <td align="left">
            <logic:iterate id="satBean" name="satisfactionList" type="org.apache.struts.util.LabelValueBean">
              <html:radio property="overallSatisfaction" value="<%=satBean.getValue()%>">
                <%=satBean.getLabel()%>
              </html:radio>
            </logic:iterate>
          </td>
        </tr>
        <tr>
          <th align="left">
          <bean:message key="typeForm.checkbox.wouldrecommend" />:</th>
          <td align="left">
            <html:checkbox property="wouldRecommend" />
          </td>
        </tr>
        <tr>
          <th align="left">
          <bean:message key="typeForm.checkbox.used.languages" />:</th>
          <td align="left">
            <logic:iterate id="langBean" indexId="usedIndex" name="languageTypes" type="org.apache.struts.util.LabelValueBean">
              <html:multibox property="usedLanguages" value="<%=langBean.getValue()%>" /><%=langBean.getLabel()%>
            </logic:iterate>
          </td>
        </tr>
        <tr>
          <td>
          <html:submit property="submit" onclick="bCancel=false;">
            <bean:message key="button.save" />
          </html:submit>&#160;
          <html:reset>
            <bean:message key="button.reset" />
          </html:reset>&#160;
          <html:cancel onclick="bCancel=true;">
            <bean:message key="button.cancel" />
          </html:cancel></td>
        </tr>
      </table>
    </html:form>
    <html:javascript formName="jsTypeForm" dynamicJavascript="true" staticJavascript="true" />
  </body>
</html:html>
