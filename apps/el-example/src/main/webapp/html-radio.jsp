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
        <title>Test html-el:radio Tag</title>
    </head>

    <body bgcolor="white">

    <div align="center">
        <h1>Test struts html-el:radio Tag</h1>
    </div>

    <html-el:form action="html-radio.do">
        <table>
            <tr>
                <td>
                    <html-el:radio property="stringProperty" value="flagOne"
                                   title="Flag One" tabindex="3" accesskey="1"
                                   disabled="${!empty pageScope}"/>
                </td>
                <td>flag one (disabled if not empty pageScope)</td>
            </tr>
            <tr>
                <td>
                    <html-el:radio property="stringProperty" value="flagTwo"
                                   title="Flag Two" tabindex="2"
                                   accesskey="2"/>
                </td>
                <td>flag two</td>
            </tr>
            <tr>
                <td>
                    <html-el:radio property="stringProperty" value="flagThree"
                                   title="Flag Three" tabindex="1"
                                   accesskey="3"
                                   onblur="showevent(event)"
                                   onchange="showevent(event)"
                                   onclick="showevent(event)"
                                   ondblclick="showevent(event)"
                                   onfocus="showevent(event)"
                                   onkeydown="showevent(event)"
                                   onkeypress="showevent(event)"
                                   onkeyup="showevent(event)"
                                   onmousedown="showevent(event)"
                                   onmousemove="showevent(event)"
                                   onmouseout="showevent(event)"
                                   onmouseover="showevent(event)"
                                   onmouseup="showevent(event)"
                            />
                </td>
                <td>flag three</td>
            </tr>
            <tr>
                <td>
                    <html-el:submit>Save</html-el:submit>
                </td>
            </tr>
        </table>
    </html-el:form>

    <script>
        <!--
        function showevent(evt)
        {
            window.status = evt.type;
        }
        // -->
    </script>

    </body>
</html-el:html>
