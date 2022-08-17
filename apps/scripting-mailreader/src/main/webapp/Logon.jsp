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

<html:xhtml/>
<html>
<head>
    <title><bean:message key="logon.title"/></title>
</head>

<body>
<html:errors/>

<html:form action="/SubmitLogon" focus="username"
           onsubmit="return validateLogonForm(this);">
    <table style="border:none;width:100%">

        <tr>
            <th align="right">
                <bean:message key="prompt.username"/>:
            </th>
            <td align="left">
                <html:text property="username" size="16" maxlength="18"/>
            </td>
        </tr>

        <tr>
            <th align="right">
                <bean:message key="prompt.password" bundle="alternate"/>:
            </th>
            <td align="left">
                <html:password property="password" size="16" maxlength="18"
                               redisplay="false"/>
            </td>
        </tr>

        <tr>
            <td align="right">
                <html:submit property="Submit" value="Submit"/>
            </td>
            <td align="left">
                <html:reset/>
            </td>
        </tr>

    </table>

</html:form>

<html:javascript formName="LogonForm"
                 dynamicJavascript="true"
                 staticJavascript="false"/>
<script type="text/javascript" src="StaticJavascript.jsp"></script>

<jsp:include page="Footer.jsp"/>
</body>
</html>
