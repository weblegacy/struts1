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


<h:panelGrid      columns="1">

  <f:facet           name="header">
    <h:outputText   value="#{messages['mainMenu.heading']} #{user.username}"/>
  </f:facet>

  <h:outputLink     value="editRegistration.do">
    <f:param         name="action"
                    value="Edit"/>
    <h:outputText   value="#{messages['mainMenu.registration']}"/>
  </h:outputLink>

  <h:outputLink     value="logoff.do">
    <h:outputText   value="#{messages['mainMenu.logoff']}"/>
  </h:outputLink>

</h:panelGrid>


