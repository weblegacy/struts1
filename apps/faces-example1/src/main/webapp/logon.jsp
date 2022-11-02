<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
  <title>
    <h:outputText   value="#{messages['logon.title']}"/>
  </title>
  <s:base              id="base"/>
  <s:stylesheet      path="/stylesheet.css"/>
</head>
<body bgcolor="white">

<s:errors/>

<s:form                id="logon"
                   action="/logon"
                    focus="username"
                 onsubmit="return validateLogonForm(this);"
               styleClass="form">

  <h:panelGrid    columns="2"
               styleClass="grid"
              headerClass="grid header"
            columnClasses="grid column0,grid column1"
              footerClass="grid footer"
               rowClasses="grid row even,grid row odd">

    <%-- Grid header element --%>

    <f:facet name="header">
      <h:outputText value="#{messages['logon.header']}"/>
    </f:facet>

    <%-- Grid data elements --%>

    <h:outputLabel    for="username"
               styleClass="label">
      <h:outputText value="#{messages['prompt.username']}"/>
    </h:outputLabel>

    <h:inputText       id="username"
                     size="16"
               styleClass="field"
                    value="#{logonForm.username}"/>

    <h:outputLabel    for="password"
               styleClass="label">
      <h:outputText value="#{messages['prompt.password']}"/>
    </h:outputLabel>

    <h:inputSecret     id="password"
                     size="16"
               styleClass="password"
                    value="#{logonForm.password}"/>

    <h:commandButton   id="submit"
                     type="submit"
               styleClass="submit"
                    value="#{messages['button.logon']}"/>

    <h:commandButton   id="reset"
                     type="reset"
               styleClass="reset"
                    value="#{messages['button.reset']}"/>

    <%-- Grid footer element --%>

    <f:facet name="footer">
      <h:outputText value="#{messages['logon.footer']}"/>
    </f:facet>

  </h:panelGrid>

</s:form>

<s:javascript    formName="logonForm"
        dynamicJavascript="true"
         staticJavascript="false"/>
<script type="text/javascript" src="staticJavascript.faces"></script>

</body>
</s:html>
</f:view>
