<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="a" uri="/WEB-INF/app.tld" %>
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


<%-- FIXME <a:checkLogon/> --%>

<%--
<f:view>
<s:html locale="true">
<head>
  <title><c:choose>
    <c:when test="${subscriptionForm.action == 'Create'}">
      <s:message id="titleCreate" key="subscription.title.create"/>
    </c:when>
    <c:when test="${subscriptionForm.action == 'Delete'}">
      <s:message id="titleDelete" key="subscription.title.delete"/>
    </c:when>
    <c:when test="${subscriptionForm.action == 'Edit'}">
      <s:message id="titleEdit" key="subscription.title.edit"/>
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

<s:form            action="/saveSubscription"
                    focus="host">

  <h:inputHidden       id="action"
                    value="#{subscriptionForm.action}"/>

  <h:panelGrid    columns="2"
               styleClass="form-background"
              headerClass="form-header"
            columnClasses="form-prompt,form-field">

    <%-- Grid header element --%>

    <f:facet         name="header">
      <h:panelGroup>
        <c:choose>
          <c:when    test="${subscriptionForm.action == 'Create'}">
            <s:message id="headerCreate" key="subscription.title.create"/>
          </c:when>
          <c:when    test="${subscriptionForm.action == 'Delete'}">
            <s:message id="headerDelete" key="subscription.title.delete"/>
          </c:when>
          <c:when    test="${subscriptionForm.action == 'Edit'}">
            <s:message id="headerEdit" key="subscription.title.edit"/>
          </c:when>
          <c:otherwise>
            <h:outputText
                       id="headerUnknown"
                    value="UNKNOWN ACTION"/>
          </c:otherwise>
        </c:choose>
      </h:panelGroup>
    </f:facet>

    <%-- Grid data elements --%>

    <h:outputLabel    for="user">
      <s:message      key="prompt.username"/>
    </h:outputLabel>

    <s:write           id="user"
                   filter="true"
                    value="#{user.username}"/>

    <h:outputLabel    for="host">
      <s:message      key="prompt.mailHostname"/>
    </h:outputLabel>

    <c:choose>
      <c:when        test="${subscriptionForm.action == 'Create'}">
        <h:inputText   id="host"
                     size="50"
                    value="#{subscriptionForm.host}"/>
      </c:when>
      <c:otherwise>
        <h:panelGroup  id="hostGroup">
          <s:write     id="hostDisplay"
                   filter="true"
                    value="#{subscriptionForm.host}"/>
          <h:inputHidden
                       id="host"
                    value="#{subscriptionForm.host}"/>
        </h:panelGroup>
      </c:otherwise>
    </c:choose>

    <h:outputLabel    for="username">
      <s:message      key="prompt.mailUsername"/>
    </h:outputLabel>

    <h:inputText       id="username"
                     size="50"
                    value="#{subscriptionForm.username}"/>

    <h:outputLabel    for="password">
      <s:message      key="prompt.mailPassword"/>
    </h:outputLabel>

    <h:inputText       id="password"
                     size="50"
                    value="#{subscriptionForm.password}"/>

    <h:outputLabel    for="type">
      <s:message      key="prompt.mailServerType"/>
    </h:outputLabel>

    <h:selectOneMenu   id="type"
                    value="#{subscriptionForm.type}">
      <f:selectItem
                itemValue="imap"
                itemLabel="IMAP Protocol"/>
      <f:selectItem
                itemValue="pop3"
                itemLabel="POP3 Protocol"/>
    </h:selectOneMenu>

    <h:outputLabel    for="autoConnect">
      <s:message      key="prompt.autoConnect"/>
    </h:outputLabel>

    <h:selectBooleanCheckbox
                       id="autoConnect"
                    value="#{subscriptionForm.autoConnect}"/>

    <c:choose>
      <c:when        test="${subscriptionForm.action == 'Delete'}">
        <h:commandButton
                       id="confirm"
                     type="submit"
               styleClass="command-single"
                    value="Confirm"/>  <%-- FIXME - i18n --%>
      </c:when>
      <c:otherwise>
        <h:commandButton
                       id="save"
                     type="submit"
               styleClass="command-single"
                    value="Save"/>     <%-- FIXME - i18n --%>
      </c:otherwise>
    </c:choose>

    <h:panelGroup      id="reset_and_cancel">
      <h:commandButton id="reset"
                     type="reset"
               styleClass="command-multiple"
                    value="Reset"/>       <%-- FIXME - i18n --%>
      <h:commandButton id="cancel"
                     type="submit"
                  onclick="bCancel=true;"
               styleClass="command-multiple"
                    value="Cancel"/>     <%-- FIXME - i18n --%>
    </h:panelGroup>

  </h:panelGrid>

</s:form>

<%--
</body>
</s:html>
</f:view>
--%>
