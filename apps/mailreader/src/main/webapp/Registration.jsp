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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:html>
<head>
    <logic:equal name="RegistrationForm" property="task"
                 scope="request" value="Create">
        <title><bean:message key="registration.title.create"/></title>
    </logic:equal>
    <logic:equal name="RegistrationForm" property="task"
                 scope="request" value="Edit">
        <title><bean:message key="registration.title.edit"/></title>
    </logic:equal>
    <html:base/>
</head>

<body bgcolor="white">

<html:errors/>

<html:form action="/SaveRegistration" focus="username"
           onsubmit="return validateRegistrationForm(this);">
    <html:hidden property="task"/>
    <table style="border:none;width:100%">

        <tr>
            <th align="right">
                <bean:message key="prompt.username"/>:
            </th>
            <td align="left">
                <logic:equal name="RegistrationForm" property="task"
                             scope="request" value="Create">
                    <html:text property="username" size="16" maxlength="16"/>
                </logic:equal>
                <logic:equal name="RegistrationForm" property="task"
                             scope="request" value="Edit">
                    <html:hidden property="username" write="true"/>
                </logic:equal>
            </td>
        </tr>

        <tr>
            <th align="right">
                <bean:message key="prompt.password"/>:
            </th>
            <td align="left">
                <html:password property="password" size="16" maxlength="16"/>
            </td>
        </tr>

        <tr>
            <th align="right">
                <bean:message key="prompt.password2"/>:
            </th>
            <td align="left">
                <html:password property="password2" size="16" maxlength="16"/>
            </td>
        </tr>

        <tr>
            <th align="right">
                <bean:message key="prompt.fullName"/>:
            </th>
            <td align="left">
                <html:text property="fullName" size="50"/>
            </td>
        </tr>

        <tr>
            <th align="right">
                <bean:message key="prompt.fromAddress"/>:
            </th>
            <td align="left">
                <html:text property="fromAddress" size="50"/>
            </td>
        </tr>

        <tr>
            <th align="right">
                <bean:message key="prompt.replyToAddress"/>:
            </th>
            <td align="left">
                <html:text property="replyToAddress" size="50"/>
            </td>
        </tr>

        <tr>
            <td align="right">
                <html:submit property="DO_SUBMIT">
                    <bean:message key="button.save"/>
                </html:submit>
            </td>
            <td align="left">
                <html:reset property="DO_RESET">
                    <bean:message key="button.reset"/>
                </html:reset>
                &nbsp;
                <html:cancel>
                    <bean:message key="button.cancel"/>
                </html:cancel>
            </td>
        </tr>

    </table>
</html:form>

<logic:equal name="RegistrationForm" property="task"
             scope="request" value="Edit">

    <div align="center">
        <h3><bean:message key="heading.subscriptions"/></h3>
    </div>

    <table style="border:1px solid black;width:100%">

        <tr>
            <th style="border:1px solid black;text-align:center;width:30%">
                <bean:message key="heading.host"/>
            </th>
            <th style="border:1px solid black;text-align:center;width:25%">
                <bean:message key="heading.user"/>
            </th>
            <th style="border:1px solid black;text-align:center;width:10%">
                <bean:message key="heading.type"/>
            </th>
            <th style="border:1px solid black;text-align:center;width:10%">
                <bean:message key="heading.autoConnect"/>
            </th>
            <th style="border:1px solid black;text-align:center;width:15%">
                <bean:message key="heading.action"/>
            </th>
        </tr>

        <logic:iterate name="user" property="subscriptions" id="subscription">
            <tr>
                <td style="border:1px solid black;text-align:left">
                    <bean:write name="subscription" property="host"/>
                </td>
                <td style="border:1px solid black;text-align:left">
                    <bean:write name="subscription" property="username"/>
                </td>
                <td style="border:1px solid black;text-align:center">
                    <bean:write name="subscription" property="type"/>
                </td>
                <td style="border:1px solid black;text-align:center">
                    <bean:write name="subscription" property="autoConnect"/>
                </td>
                <td style="border:1px solid black;text-align:center">
                    <html:link action="/DeleteSubscription"
                               paramName="subscription" paramId="host"
                               paramProperty="host">
                        <bean:message key="registration.deleteSubscription"/>
                    </html:link>
                    &nbsp;
                    <html:link action="/EditSubscription"
                               paramName="subscription" paramId="host"
                               paramProperty="host">
                        <bean:message key="registration.editSubscription"/>
                    </html:link>
                </td>
            </tr>
        </logic:iterate>

    </table>

    <html:link action="/EditSubscription">
        <bean:message key="registration.addSubscription"/>
    </html:link>


</logic:equal>

<html:javascript formName="RegistrationForm"
                 dynamicJavascript="true"
                 staticJavascript="false"/>
<script type="text/javascript" src="StaticJavascript.jsp"></script>

<jsp:include page="Footer.jsp"/>
</body>
</html:html>
