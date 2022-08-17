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
    <title><bean:message key="javascript.title" bundle="i18nExample" /></title>
    <html:base />
  </head>
  <body bgcolor="white">

    <h2><strong><bean:message key="javascript.desc" bundle="i18nExample"/></strong></h2>
    <p><bean:message key="long.desc" bundle="i18nExample"/></p>

     <table style="border:none;width:95%">
       <tr><td align="left">
          <html:link action="/welcome"><bean:message key="label.back" bundle="i18nExample"/></html:link> &nbsp;
          <html:link action="showStrutsConfigI18nExample" target="_blank">struts-config-i18nVariables.xml</html:link> &nbsp;
          <html:link action="showValidationI18nExample" target="_blank">validation-i18nVariables.xml</html:link>
       </td>
       <td align="right">
          <table style="border:none"><tr>
            <td><b><bean:message key="label.current" bundle="i18nExample"/></b>: <strong><bean:message key="current.locale" bundle="i18nExample"/></strong></td>
            <td>&nbsp;</td>
            <td><html:link action="/switchJsI18nExample?language=en&country=US"><bean:message key="locale.en_us" bundle="i18nExample"/></html:link></td>
            <td><html:link action="/switchJsI18nExample?language=en&country=GB"><bean:message key="locale.en_gb" bundle="i18nExample"/></html:link></td>
            <td><html:link action="/switchJsI18nExample?language=fr"><bean:message key="locale.fr" bundle="i18nExample"/></html:link></td>
            <td><html:link action="/switchJsI18nExample?language=jp"><bean:message key="locale.jp" bundle="i18nExample"/></html:link></td>
          </tr></table>
       </td></tr>
     </table>

    <hr />
    <h2><bean:message key="form.heading" bundle="i18nExample"/></h2>

    <html:form action="validateI18nExample" onsubmit="return validateI18nForm(this);">
      <table style="border:none">

        <!-- Name -->
        <tr>
          <td align="right"><b><bean:message key="label.name" bundle="i18nExample"/></b></td>
          <td align="left"><html:text property="name" size="20" errorStyle="background-color: yellow" /></td>
          <td align="left"><font color="red"><html:errors property="name" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Address Line 1 -->
        <tr>
          <td align="right"><b><bean:message key="label.address" bundle="i18nExample" /></b></td>
          <td align="left"><b><html:text property="address1" size="20" errorStyle="background-color: yellow" /></b></td>
          <td align="left"><font color="red"><html:errors property="address1" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Address Line 2 -->
        <tr>
          <td>&nbsp;</td>
          <td align="left"><b><html:text property="address2" size="20" errorStyle="background-color: yellow" /></b></td>
          <td align="left"><font color="red"><html:errors property="address2" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Address City -->
        <tr>
          <td align="right"><bean:message key="label.city" bundle="i18nExample" /></td>
          <td align="left"><b><html:text property="city" size="20" errorStyle="background-color: yellow" /></b></td>
          <td align="left"><font color="red"><html:errors property="city" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Address State -->
        <tr>
          <td align="right"><bean:message key="label.state" bundle="i18nExample" /></td>
          <td align="left"><b><html:text property="state" size="20" errorStyle="background-color: yellow" /></b></td>
          <td align="left"><font color="red"><html:errors property="state" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Address Zip/Post Code -->
        <tr>
          <td align="right"><bean:message key="label.zip" bundle="i18nExample" /></td>
          <td align="left"><b><html:text property="zip" size="10" errorStyle="background-color: yellow" /></b></td>
          <td align="left"><font color="red"><html:errors property="zip" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Phone -->
        <tr>
          <td align="right"><b><bean:message key="label.phone" bundle="i18nExample" /></b></td>
          <td align="left"><b><html:text property="phone" size="10" errorStyle="background-color: yellow" /></b></td>
          <td align="left"><font color="red"><html:errors property="phone" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Date of Birth -->
        <tr>
          <td align="right"><b><bean:message key="label.dob" bundle="i18nExample" /></b></td>
          <td align="left"><b><html:text property="dob" size="10" errorStyle="background-color: yellow" /></b></td>
          <td align="left"><font color="red"><html:errors property="dob" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Age -->
        <tr>
          <td align="right"><b><bean:message key="label.age" bundle="i18nExample" /></b></td>
          <td align="left"><b><html:text property="age" size="20" errorStyle="background-color: yellow" /></b></td>
          <td align="left"><font color="red"><html:errors property="age" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Date of Employment -->
        <tr>
          <td align="right"><b><bean:message key="label.doe" bundle="i18nExample" /></b></td>
          <td align="left"><b><html:text property="doe" size="20" errorStyle="background-color: yellow" /></b></td>
          <td align="left"><font color="red"><html:errors property="doe" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Employee Number -->
        <tr>
          <td align="right"><b><bean:message key="label.emplno" bundle="i18nExample" /></b></td>
          <td align="left"><b><html:text property="emplno" size="20" errorStyle="background-color: yellow" /></b></td>
          <td align="left"><font color="red"><html:errors property="emplno" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Annual Salary -->
        <tr>
          <td align="right"><b><bean:message key="label.salary" bundle="i18nExample" /></b></td>
          <td align="left"><b><html:text property="salary" size="20" errorStyle="background-color: yellow" /></b></td>
          <td align="left"><font color="red"><html:errors property="salary" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Weekly Hours -->
        <tr>
          <td align="right"><b><bean:message key="label.hours" bundle="i18nExample" /></b></td>
          <td align="left"><b><html:text property="hours" size="20" errorStyle="background-color: yellow" /></b></td>
          <td align="left"><font color="red"><html:errors property="hours" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Buttons -->
        <tr align="center">
          <td colspan="2"><html:submit><bean:message key="label.save" bundle="i18nExample"/></html:submit>&nbsp;
                          <html:reset><bean:message key="label.reset" bundle="i18nExample"/></html:reset>&nbsp;
                          <html:cancel><bean:message key="label.cancel" bundle="i18nExample"/></html:cancel></td>
          <td>&nbsp;</td>
        </tr>
      </table>
    </html:form>

    <hr />
    <br />
    <p><strong><bean:message key="rules.heading" bundle="i18nExample"/></strong></p>
    <ul>
        <li><strong><bean:message key="label.name" bundle="i18nExample"/></strong> -
            <bean:message key="rules.name" bundle="i18nExample"/>.</li>
        <li><strong><bean:message key="label.zip" bundle="i18nExample"/></strong> -
            <bean:message key="rules.zip" bundle="i18nExample"/>.</li>
        <li><strong><bean:message key="label.phone" bundle="i18nExample"/></strong> -
            <bean:message key="rules.phone" bundle="i18nExample"/>.</li>
        <li><strong><bean:message key="label.dob" bundle="i18nExample"/></strong> -
            <bean:message key="rules.dob" bundle="i18nExample"/>.</li>
        <li><strong><bean:message key="label.age" bundle="i18nExample"/></strong> -
            <bean:message key="rules.age" bundle="i18nExample"/>.</li>
        <li><strong><bean:message key="label.doe" bundle="i18nExample"/></strong> -
            <bean:message key="rules.doe" bundle="i18nExample"/>.</li>
        <li><strong><bean:message key="label.emplno" bundle="i18nExample"/></strong> -
            <bean:message key="rules.emplno" bundle="i18nExample"/>.</li>
        <li><strong><bean:message key="label.salary" bundle="i18nExample"/></strong> -
            <bean:message key="rules.salary" bundle="i18nExample"/>.</li>
        <li><strong><bean:message key="label.hours" bundle="i18nExample"/></strong> -
            <bean:message key="rules.hours" bundle="i18nExample"/>.</li>
    </ul>

    <p><strong><bean:message key="notes.heading" bundle="i18nExample"/></strong></p>
    <ul>
        <li><bean:message key="notes1" bundle="i18nExample"/></li>
        <li><bean:message key="notes2" bundle="i18nExample"/>
            <ul>
                <li><bean:message key="notes3" bundle="i18nExample"/></li>
                <li><bean:message key="notes4" bundle="i18nExample"/></li>
            </ul>
        </li>
        <li><bean:message key="notes5" bundle="i18nExample"/></li>
    </ul>

    <html:javascript formName="i18nForm" dynamicJavascript="true" staticJavascript="true" />

  </body>
</html:html>
