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
            Test struts-html:cancel Tag
        </title>
        <html:base/>
    </head>

    <body bgcolor="white">

    <p><strong>Cancel Not Allowed - Error Message</strong></p>

    <p>
        Pressing the Cancel button should display an error
        message, since Cancellable is not set for this Action
        but an Exception handler has been configured to handle
        the throw exception.
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
        <html:form action="/html-cancel-false">
            <html:submit property="submit"/>
            &#160;
            <html:reset/>
            <html:cancel/>
        </html:form>
    </p>

    <hr/>

    <p><strong>Cancel Not Allowed - Exception</strong></p>
    <p>
        Pressing this Cancel button should throw an
        <code>org.apache.struts.action.InvalidCancelException</code>
        since Cancellable is not set for this Action and no exception
        handler was configured.
    </p>

    <p>
        <html:form action="/html-cancel-exception">
            <html:submit property="submit"/>
            &#160;
            <html:reset/>
            <html:cancel/>
        </html:form>
    </p>

    <hr/>

    <p><strong>Cancel Allowed  (Validate true)</strong></p>
    <p>
        Pressing this Cancel button should return to the Welcome page,
        as Cancellable is set to true for this Action.
    </p>

    <p>
        <html:form action="/html-cancel-true">
            <html:submit property="submit"/>
            &#160;
            <html:reset/>
            <html:cancel/>
        </html:form>
    </p>
    <hr/>

    <p><strong>Cancel Ignored (Validate false)</strong></p>
    <p>
        Pressing this Cancel button should return to the Welcome page,
        as validate is set to false for this Action.
    </p>

    <p>
        <html:form action="/html-cancel-novalidate">
            <html:submit property="submit"/>
            &#160;
            <html:reset/>
            <html:cancel/>
        </html:form>
    </p>

    </body>
</html:html>
