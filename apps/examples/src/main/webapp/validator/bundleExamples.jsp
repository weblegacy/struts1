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
    <title><bean:message key="bundlesExamples.title" /></title>
    <html:base />
  </head>
  <body bgcolor="white">

    <p><strong><bean:message key="bundlesExamples.description"/></strong></p>
    <p><html:link action="showStrutsConfigBundles" target="_blank">struts-config-bundles.xml</html:link> &nbsp;
       <html:link action="showValidationBundles" target="_blank">validation-bundles.xml</html:link></p>

    <html:form action="validateBundleExamples">
      <table style="border:none">

        <!-- Name Field -->
        <tr>
          <td align="right"><b><bean:message key="bundlesForm.label.name" /></b></td>
          <td align="left"><html:text property="name" size="20" maxlength="25" /></td>
          <td align="left"><font color="red"><html:errors property="name" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Address Field -->
        <tr>
          <td align="right"><b><bean:message key="bundlesForm.label.address" /></b></td>
          <td align="left"><b><html:text property="address" size="20" maxlength="50" /></b></td>
          <td align="left"><font color="red"><html:errors property="address" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Phone Field -->
        <tr>
          <td align="right"><b><bean:message key="bundlesForm.label.phone" /></b></td>
          <td align="left"><b><html:text property="phone" size="20" maxlength="15" /></b></td>
          <td align="left"><font color="red"><html:errors property="phone" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Date of Birth Field -->
        <tr>
          <td align="right"><b><bean:message key="bundlesForm.label.dob" /></b></td>
          <td align="left"><b><html:text property="dob" size="20" maxlength="10" /></b></td>
          <td align="left"><font color="red"><html:errors property="dob" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Age Field -->
        <tr>
          <td align="right"><b><bean:message key="bundlesForm.label.age" /></b></td>
          <td align="left"><b><html:text property="age" size="20" maxlength="5" /></b></td>
          <td align="left"><font color="red"><html:errors property="age" header="empty"/></font>&nbsp;</td>
        </tr>

        <!-- Buttons -->
        <tr align="center">
          <td colspan="2"><html:submit><bean:message key="button.save" /></html:submit>&nbsp;
                          <html:reset><bean:message key="button.reset" /></html:reset>&nbsp;
                          <html:cancel><bean:message key="button.cancel" /></html:cancel></td>
          <td>&nbsp;</td>
        </tr>
      </table>
    </html:form>

    <hr />
    <br />
    <p><strong><bean:message key="bundlesExamples.notes.heading"/></strong></p>
    <ul>
        <li><bean:message key="bundlesExamples.notes1"/></li>
        <li><bean:message key="bundlesExamples.notes2"/></li>
        <li><bean:message key="bundlesExamples.notes3"/></li>
        <li><bean:message key="bundlesExamples.notes4"/></li>
        <li><bean:message key="bundlesExamples.notes5"/></li>
        <li><bean:message key="bundlesExamples.notes6"/></li>
    </ul>

  </body>
</html:html>
