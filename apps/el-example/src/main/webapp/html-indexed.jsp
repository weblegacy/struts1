<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean-el" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el" %>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic-el" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html-el:html>
    <head>
        <title>Test indexed HTML tags</title>
    </head>

    <body bgcolor="white">
    <div align="center">
        <h1>Test indexed HTML tags</h1>
    </div>

    <html-el:form action="html-indexed.do">
        <table>
            <logic-el:iterate collection="${testbean.coords}"
                              id="coord" indexId="ctr">
                <tr>
                    <td>
                        X:<html-el:text name="coord" property="x"
                                        indexed="true"/>
                    </td>
                    <td>
                        Y:<html-el:text name="coord" property="y"
                                        indexed="true"/>
                    </td>
                </tr>
            </logic-el:iterate>
            <tr>
                <td>
                    <html-el:submit property="submitValue">
                        Submit Changes
                    </html-el:submit>
                </td>
            </tr>
        </table>
        (
        <logic-el:iterate collection="${testbean.coords}"
                          id="coord" indexId="ctr">
            [<c:out value="${coord.x}"/>,<c:out value="${coord.y}"/>]
        </logic-el:iterate>
        )
        <table>
            <tr>
                <logic-el:iterate collection="${testbean.images}" id="image"
                                  indexId="ctr">
                    <td>
                        <html-el:image src="${image}" property="imageCoords"
                                       indexed="true"/>
                    </td>
                </logic-el:iterate>
            </tr>
            <tr>
                <logic-el:iterate collection="${testbean.imageCoords}"
                                  id="coord"
                                  indexId="ctr">
                    <td>
                        (<c:out value="${coord.x}"/>,<c:out
                            value="${coord.y}"/>)
                    </td>
                </logic-el:iterate>
            </tr>
        </table>
    </html-el:form>
    </body>
</html-el:html>
