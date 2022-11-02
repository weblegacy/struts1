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
<s:loadMessages       var="messages"/>
<s:html            locale="true">
<head>
  <title><c:choose>
    <c:when          test="${registrationForm.action == 'Create'}">
      <s:message      key="registration.title.create"/>
    </c:when>
    <c:when          test="${registrationForm.action == 'Edit'}">
      <s:message      key="registration.title.edit"/>
    </c:when>
    <c:otherwise>
      UNKNOWN ACTION
    </c:otherwise>
  </c:choose></title>
  <s:base/>
  <s:stylesheet      path="/stylesheet.css"/>
</head>
<body>

<s:errors/>

<s:form                id="registration"
                   action="/saveRegistration"
                    focus="username"
                 onsubmit="return validateRegistrationForm(this);"
               styleClass="center form">

  <h:inputHidden       id="action"
                    value="#{registrationForm.action}"/>

  <h:panelGrid    columns="2"
               styleClass="grid"
              headerClass="grid header"
            columnClasses="grid column0,grid column1">

    <%-- Grid header element --%>

    <f:facet name="header">
      <h:panelGroup>
        <c:choose>
          <c:when    test="${registrationForm.action == 'Create'}">
           <s:message key="registration.header.create"/>
          </c:when>
          <c:when    test="${registrationForm.action == 'Edit'}">
           <s:message key="registration.header.edit"/>
          </c:when>
          <c:otherwise>
           <h:outputText
                       id="unknownActionTitle"
                    value="UNKNOWN ACTION"/>
          </c:otherwise>
        </c:choose>
      </h:panelGroup>
    </f:facet>

    <%-- Grid data elements --%>

    <h:outputLabel    for="username"
               styleClass="label">
      <s:message      key="prompt.username"/>
    </h:outputLabel>

    <h:panelGroup>
      <c:choose>
        <c:when      test="${registrationForm.action == 'Create'}">
          <h:inputText id="username"
                     size="16"
               styleClass="field"
                    value="#{registrationForm.username}"/>
        </c:when>
        <c:when      test="${registrationForm.action == 'Edit'}">
          <h:panelGroup
                       id="usernameGroup">
            <s:write
                   filter="true"
               styleClass="value"
                    value="#{registrationForm.username}"/>
            <h:inputHidden
                       id="username"
                    value="#{registrationForm.username}"/>
          </h:panelGroup>
        </c:when>
        <c:otherwise>
          <h:outputText
                       id="unknownActionMessage"
               styleClass="value"
                    value="UNKNOWN ACTION"/>
        </c:otherwise>
      </c:choose>
    </h:panelGroup>

    <h:outputLabel    for="password"
               styleClass="label">
      <s:message      key="prompt.password"/>
    </h:outputLabel>

    <h:inputText       id="password"
                     size="16"
               styleClass="field"
                    value="#{registrationForm.password}"/>

    <h:outputLabel    for="password2"
               styleClass="label">
      <s:message      key="prompt.password2"/>
    </h:outputLabel>

    <h:inputText       id="password2"
                     size="16"
               styleClass="field"
                    value="#{registrationForm.password2}"/>

    <h:outputLabel    for="fullName"
               styleClass="label">
      <s:message      key="prompt.fullName"/>
    </h:outputLabel>

    <h:inputText       id="fullName"
                     size="50"
               styleClass="field"
                    value="#{registrationForm.fullName}"/>

    <h:outputLabel    for="fromAddress"
               styleClass="label">
      <s:message      key="prompt.fromAddress"/>
    </h:outputLabel>

    <h:inputText       id="fromAddress"
                     size="50"
               styleClass="field"
                    value="#{registrationForm.fromAddress}"/>

    <h:outputLabel    for="replyToAddress"
               styleClass="label">
      <s:message      key="prompt.replyToAddress"/>
    </h:outputLabel>

    <h:inputText       id="replyToAddress"
                     size="50"
               styleClass="field"
                    value="#{registrationForm.replyToAddress}"/>

    <h:commandButton   id="submit"
                     type="submit"
               styleClass="submit"
                    value="#{messages['button.save']}"/>

    <h:panelGroup>
      <h:commandButton id="reset"
                     type="reset"
               styleClass="reset"
                    value="#{messages['button.reset']}"/>
      <h:commandButton id="cancel"
                     type="submit"
               styleClass="cancel"
                  onclick="bCancel=true;"
                    value="#{messages['button.cancel']}"/>
    </h:panelGroup>

  </h:panelGrid>

</s:form>

<c:if test="${registrationForm.action == 'Edit'}">

<h:form id="subscriptions">

  <h:dataTable         id="table"
                  binding="#{registrationBacking.table}"
            columnClasses="list column left,list column left,list column center,
                           list column center,list column center"
              headerClass="list header"
               styleClass="wide list"
               rowClasses="list row even,list row odd"
                    value="#{user.subscriptions}"
                      var="subscription">

    <h:column          id="hostColumn">
      <f:facet       name="header">
        <s:message     id="hostHeader"
                      key="heading.host"/>
      </f:facet>
      <h:outputText    id="subhost"
               styleClass="value"
                    value="#{subscription.host}"/>
    </h:column>

    <h:column          id="usernameColumn">
      <f:facet       name="header">
        <s:message     id="usernameHeader"
                      key="heading.user"/>
      </f:facet>
      <h:outputText    id="subusername"
               styleClass="value"
                    value="#{subscription.username}"/>
    </h:column>

    <h:column          id="typeColumn">
      <f:facet       name="header">
        <s:message     id="typeHeader"
                      key="heading.type"/>
      </f:facet>
      <h:selectOneMenu id="type"
               styleClass="field"
                    value="#{subscription.type}">
        <f:selectItem
                itemValue="imap"
                itemLabel="IMAP Protocol"/>
        <f:selectItem
                itemValue="pop3"
                itemLabel="POP3 Protocol"/>
      </h:selectOneMenu>
<%--
      <h:outputText    id="subtype"
               styleClass="value"
                    value="#{subscription.type}"/>
--%>
    </h:column>

    <h:column          id="autoConnectColumn">
      <f:facet       name="header">
        <s:message     id="autoConnectHeader"
                      key="heading.autoConnect"/>
      </f:facet>
      <h:selectBooleanCheckbox
                       id="autoConnect"
               styleClass="field"
                    value="#{subscription.autoConnect}"/>
    </h:column>

    <h:column          id="actionColumn">
      <f:facet       name="header">
        <s:message     id="actionHeader"
                      key="heading.action"/>
      </f:facet>
      <h:commandButton id="delete"
               styleClass="submit"
                immediate="true"
                   action="#{registrationBacking.delete}"
                    value="#{messages['button.delete']}"/>
      <h:commandButton id="edit"
               styleClass="submit"
                immediate="true"
                   action="#{registrationBacking.edit}"
                    value="#{messages['button.edit']}"/>
    </h:column>

  </h:dataTable>

  <h:commandButton     id="create"
                immediate="true"
                   action="#{registrationBacking.create}"
               styleClass="submit"
                    value="#{messages['button.add']}"/>

  <h:commandButton      id="update"
                 immediate="false"
                    action="#{registrationBacking.update}"
                styleClass="submit"
                     value="#{messages['button.update']}"/>

</h:form>

</c:if>

<s:javascript    formName="registrationForm"
        dynamicJavascript="true"
         staticJavascript="false"/>
<script type="text/javascript" src="staticJavascript.faces"></script>

</body>
</s:html>
</f:view>
