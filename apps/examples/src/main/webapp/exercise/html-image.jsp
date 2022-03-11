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
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html>
  <head>
    <title>Test struts-html:image Tag</title>
  </head>
  <body>
    <div align="center">
      <h1>Test struts-html:image Tag</h1>
    <p>
        Click on the <strong><i>powered by</i></strong> images below to submit the form:
    </p>
    <p>
        Last Submitted:
        <font color="blue">
            <%= new java.util.Date() %>
        </font>
    </p>
    </div>
    <html:form>
        <table border="1" cellspacing="2" cellpadding="4" align="center">
        <tr>
            <td>Standard image tag</td>
            <td>
                <input type="image" src="struts-power.gif">
            </td>
        </tr>
        <tr>
            <td>Struts image tag via page attribute</td>
            <td>
                <html:image page="/struts-power.gif" />
            </td>
        </tr>
        <tr>
            <td>Struts image tag via page attribute, current module</td>
            <td>
                <html:image page="/struts-power.gif"/>
            </td>
         </tr>
         <tr>
            <td>Struts image tag via page attribute, default module</td>
            <td>
                <html:image page="/struts-power.gif" module="/validator"/>
            </td>
         </tr>
    </html:form>
  </body>
</html>
