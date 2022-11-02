<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib prefix="s" uri="http://struts.apache.org/tags-faces" %>


<!--

 Copyright 2002,2004 The Apache Software Foundation.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

-->


<%--
<f:view>
<s:html locale="true">
<head>
  <title><c:choose>
    <c:when test="${registrationForm.action == 'Create'}">
      <s:message key="registration.title.create"/>
    </c:when>
    <c:when test="${registrationForm.action == 'Edit'}">
      <s:message key="registration.title.edit"/>
    </c:when>
    <c:otherwise>
      UNKNOWN ACTION
    </c:otherwise>
  </c:choose></title>
  <s:base/>
  <s:stylesheet path="/stylesheet.css"/>
</head>
<body bgcolor="white">
--%>

<s:errors/>

<s:form            action="/saveRegistration"
                    focus="username"
                 onsubmit="return validateRegistrationForm(this);">

  <h:inputHidden       id="action"
                    value="#{registrationForm.action}"/>

  <h:panelGrid    columns="2"
               styleClass="form-background"
              headerClass="form-header"
            columnClasses="form-prompt,form-field">

    <%-- Grid header element --%>

    <f:facet name="header">
      <h:panelGroup>
        <c:choose>
          <c:when    test="${registrationForm.action == 'Create'}">
            <s:message
                      key="registration.header.create"/>
          </c:when>
          <c:when    test="${registrationForm.action == 'Edit'}">
            <s:message
                      key="registration.header.edit"/>
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

    <h:outputLabel    for="username">
      <s:message      key="prompt.username"/>
    </h:outputLabel>

    <h:panelGroup>
      <c:choose>
        <c:when      test="${registrationForm.action == 'Create'}">
          <h:inputText id="username"
                     size="16"
                    value="#{registrationForm.username}"/>
        </c:when>
        <c:when      test="${registrationForm.action == 'Edit'}">
          <h:panelGroup
                       id="usernameGroup">
            <s:write
                   filter="true"
                    value="#{registrationForm.username}"/>
            <h:inputHidden
                       id="username"
                         value="#{registrationForm.username}"/>
          </h:panelGroup>
        </c:when>
        <c:otherwise>
          <h:outputText
                       id="unknownActionMessage"
                    value="UNKNOWN ACTION"/>
        </c:otherwise>
      </c:choose>
    </h:panelGroup>

    <h:outputLabel    for="password">
      <s:message      key="prompt.password"/>
    </h:outputLabel>

    <h:inputText       id="password"
                     size="16"
                    value="#{registrationForm.password}"/>

    <h:outputLabel    for="password2">
      <s:message      key="prompt.password2"/>
    </h:outputLabel>

    <h:inputText       id="password2"
                     size="16"
                    value="#{registrationForm.password2}"/>

    <h:outputLabel    for="fullName">
      <s:message      key="prompt.fullName"/>
    </h:outputLabel>

    <h:inputText       id="fullName"
                     size="50"
                    value="#{registrationForm.fullName}"/>

    <h:outputLabel    for="fromAddress">
      <s:message      key="prompt.fromAddress"/>
    </h:outputLabel>

    <h:inputText       id="fromAddress"
                     size="50"
                    value="#{registrationForm.fromAddress}"/>

    <h:outputLabel    for="replyToAddress">
      <s:message      key="prompt.replyToAddress"/>
    </h:outputLabel>

    <h:inputText       id="replyToAddress"
                     size="50"
                    value="#{registrationForm.replyToAddress}"/>

    <h:commandButton   id="submit"
                     type="submit"
               styleClass="command-single"
                    value="Save"/>                        <%-- FIXME - i18n --%>

    <h:panelGroup>
      <h:commandButton id="reset"
                     type="reset"
               styleClass="command-multiple"
                    value="Reset"/>                     <%-- FIXME - i18n --%>
      <h:commandButton id="cancel" type="submit"
                  onclick="bCancel=true;"
               styleClass="command-multiple"
                    value="Cancel"/>                    <%-- FIXME - i18n --%>
    </h:panelGroup>

  </h:panelGrid>

  <s:javascript formName="registrationForm"
       dynamicJavascript="true"
        staticJavascript="false"/>
  <script type="text/javascript" src="staticJavascript.jsp"></script>

</s:form>

<c:if test="${registrationForm.action == 'Edit'}">

<h:form                id="subscriptions">

  <h:dataTable         id="table"
            columnClasses="list-column-host,list-column-user,list-column-type,
                           list-column-auto,list-column-action"
              headerClass="list-header"
               styleClass="list-background"
               rowClasses="list-row-even,list-row-odd"
                    value="#{user.subscriptions}"
                      var="subscription">

    <h:column          id="hostColumn">
      <f:facet       name="header">
        <s:message     id="hostHeader"
                      key="heading.host"/>
      </f:facet>
      <h:outputText    id="subhost"
                    value="#{subscription.host}"/>
    </h:column>

    <h:column          id="usernameColumn">
      <f:facet       name="header">
        <s:message     id="usernameHeader"
                      key="heading.user"/>
      </f:facet>
      <h:outputText    id="subusername"
                    value="#{subscription.username}"/>
    </h:column>

    <h:column          id="typeColumn">
      <f:facet       name="header">
        <s:message     id="typeHeader"
                      key="heading.type"/>
      </f:facet>
      <h:outputText    id="subtype"
                    value="#{subscription.type}"/>
    </h:column>

    <h:column          id="autoConnectColumn">
      <f:facet       name="header">
        <s:message     id="autoConnectHeader"
                      key="heading.autoConnect"/>
      </f:facet>
      <h:outputText    id="subauto"
                    value="#{subscription.autoConnect}"/>
    </h:column>

    <h:column          id="actionColumn">
      <f:facet       name="header">
        <s:message     id="actionHeader"
                      key="heading.action"/>
      </f:facet>
      <h:commandButton id="delete"
               styleClass="command-multiple"
                immediate="true"
                   action="#{registrationBacking.delete}"
                    value="#{registrationBacking.deleteLabel}"/>
      <h:commandButton id="edit"
               styleClass="command-multiple"
                immediate="true"
                   action="#{registrationBacking.edit}"
                    value="#{registrationBacking.editLabel}"/>
    </h:column>

  </h:dataTable>

  <h:commandButton     id="create"
                immediate="true"
                   action="#{registrationBacking.create}"
                    value="Add New"/>

</h:form>

</c:if>
<%--
</body>
</s:html>
</f:view>
--%>
