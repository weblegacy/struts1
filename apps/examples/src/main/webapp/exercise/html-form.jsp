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
        <title>
            Test struts-html:form Tag
        </title>
        <html:base/>
    </head>

    <body bgcolor="white">

    <p><strong><i>Post Back</i> Form example</strong></p>

    <p>
        Pressing the Submit button should re-display this page.
    </p>

    <logic:messagesPresent>
    <p>
        <font color="red"><strong>
            <html:messages id="msg">
                <bean:write name="msg"/>
            </html:messages>
         </strong></font>
    </p>
    </logic:messagesPresent>

    <p>
        <html:form>
            <html:submit property="submit"/>
        </html:form>
    </p>
    <p>
        Use the links below to change the <i>postback</i> action (both return
        to this page).
    </p>
    <ul>
       <li>Switch to <html:link action="html-form">/html-form</html:link></li>
       <li>Switch to <html:link action="html-form-postback">/html-form-postback</html:link></li>
    </ul>
    <hr/>

    <p><html:link action="welcome">Return to the Taglib Exercises main page</html:link></p>

    </body>
</html:html>
