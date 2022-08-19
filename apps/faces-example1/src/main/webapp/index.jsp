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
    <s:message        key="index.title"/>
  </title>
  <s:base/>
  <s:stylesheet      path="/stylesheet.css"/>
</head>
<body>

<%--
<logic:notPresent name="database" scope="application">
  <font color="red">
    ERROR:  User database not loaded -- check servlet container logs
    for error messages.
  </font>
  <hr>
</logic:notPresent>
--%>

<%--
<logic:notPresent name="org.apache.struts.action.MESSAGE" scope="application">
  <font color="red">
    ERROR:  Application resources not loaded -- check servlet container
    logs for error messages.
  </font>
</logic:notPresent>
--%>

<h:form                id="indexForm">

  <h:panelGrid    columns="1"
              headerClass="list header"
               rowClasses="list row even,list row odd"
               styleClass="list">

    <f:facet         name="header">
      <s:message      key="index.heading"/>
    </f:facet>

    <s:commandLink     id="test"
           actionListener="#{indexBacking.testListener}"
                immediate="true"
               styleClass="link"
                 rendered="#{false}">
               <h:outputText value="Click here to test ActionListener"></h:outputText>
    </s:commandLink>

    <s:commandLink     id="create"
                   action="#{indexBacking.create}"
               styleClass="link">
      <f:param       name="action"
                    value="Create"/>
      <s:message      key="index.registration"/>
    </s:commandLink>

    <s:commandLink     id="logon"
                   action="#{indexBacking.logon}"
                immediate="true"
               styleClass="link">
      <s:message      key="index.logon"/>
    </s:commandLink>

    <h:message for="test"></h:message>

  </h:panelGrid>

</h:form>

<p>&nbsp;</p>
<h:outputLink value="tour.do">
  <font size="-1"><s:message key="index.tour"/></font>
</h:outputLink>
<p>&nbsp;</p>

<h:graphicImage value="/struts-power.gif" alt="Powered by Struts"/>

</body>
</s:html>
</f:view>
