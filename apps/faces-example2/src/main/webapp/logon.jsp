<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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


<s:errors/>

<s:form            action="/logon"
                    focus="username"
                 onsubmit="return validateLogonForm(this);">

  <h:panelGrid    columns="2"
               styleClass="form-background"
              headerClass="form-header"
            columnClasses="form-prompt,form-field"
              footerClass="form-footer">

    <%-- Grid header element --%>

    <f:facet         name="header">
      <s:message      key="logon.header"/>
    </f:facet>

    <%-- Grid data elements --%>

    <h:outputLabel    for="username">
      <s:message      key="prompt.username"/>
    </h:outputLabel>

    <h:inputText       id="username"
                     size="16"
                    value="#{logonForm.username}"/>

    <h:outputLabel    for="password">
      <s:message      key="prompt.password"/>
    </h:outputLabel>

    <h:inputSecret     id="password" size="16"
                    value="#{logonForm.password}"/>

    <h:commandButton   id="submit"
                     type="submit"
               styleClass="command-single"
                    value="Log On"/>

    <h:commandButton   id="reset"
                     type="reset"
               styleClass="command-single"
                    value="Reset"/>

    <%-- Grid footer element --%>

    <f:facet         name="footer">
      <s:message      key="logon.footer"/>
    </f:facet>

  </h:panelGrid>

  <s:javascript formName="logonForm"
       dynamicJavascript="true"
        staticJavascript="false"/>
  <script type="text/javascript" src="staticJavascript.jsp"></script>

</s:form>

