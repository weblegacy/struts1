<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib prefix="s" uri="http://struts.apache.org/tags-faces" %>

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


<f:view>
<s:html            locale="true">
<head>
  <title>
    <s:message        key="mainMenu.title"/>
  </title>
  <s:base/>
  <s:stylesheet      path="/stylesheet.css"/>
</head>
<body>

<h:form                id="mainMenuForm">

  <h:panelGrid    columns="1"
              headerClass="list header"
               rowClasses="list row even,list row odd"
               styleClass="list">

    <f:facet         name="header">
      <h:panelGroup>
        <s:message    key="mainMenu.heading"/>
        <h:outputText
                    value="#{user.username}"/>
      </h:panelGroup>
    </f:facet>

    <s:commandLink     id="edit"
                   action="#{mainMenuBacking.edit}"
                immediate="true"
               styleClass="link">
      <f:param       name="action"
                    value="Edit"/>
      <s:message      key="mainMenu.registration"/>
    </s:commandLink>

    <s:commandLink     id="logoff"
                   action="#{mainMenuBacking.logoff}"
                immediate="true"
               styleClass="link">
      <s:message      key="mainMenu.logoff"/>
    </s:commandLink>

  </h:panelGrid>

</h:form>

</body>
</s:html>
</f:view>
