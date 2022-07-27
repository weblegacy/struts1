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
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="org.apache.struts.apps.mailreader.Constants" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html:html>
    <head>
        <title>Unexpected Error</title>
        <html:base/>
    </head>

    <h3>An unexpected error has occured</h3>
    <logic:present name="<%=Constants.ERROR_KEY%>">
        <ul>
            <logic:iterate id="error" name="<%=Constants.ERROR_KEY%>">
                <li><bean:write name="error"/></li>
            </logic:iterate>
        </ul>
    </logic:present>
    <logic:present name="<%=Globals.EXCEPTION_KEY%>">
        <p><bean:write name="<%=Globals.EXCEPTION_KEY%>"
                       property="message"/></p>
    </logic:present>

    <jsp:include page="Footer.jsp"/>

    </body>
</html:html>
