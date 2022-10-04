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
    <title>Test struts-html-el Property Setters</title>
</head>
<body bgcolor="white">

<div align="center">
    <h1>Test struts-html-el Property Setters</h1>
</div>

Whatever changes you make to properties should be reflected when the page
is redisplayed. Press "Save" to update, or "Cancel" to return to the
main menu.

<html-el:form action="html-setters.do">
<table border="0" width="100%">

<tr>
    <th align="center" colspan="4">Scalar Properties</th>
</tr>

<tr>
    <th align="right">booleanProperty</th>
    <td align="left">
        <html-el:checkbox property="booleanProperty"/>
    </td>
    <th align="right">nested.booleanProperty</th>
    <td align="left">
        <html-el:checkbox property="nested.booleanProperty"/>
    </td>
</tr>

<tr>
    <th align="right">doubleProperty</th>
    <td align="left">
        <html-el:text property="doubleProperty" size="32"/>
    </td>
    <th align="right">nested.doubleProperty</th>
    <td align="left">
        <html-el:text property="nested.doubleProperty" size="32"/>
    </td>
</tr>

<tr>
    <th align="right">floatProperty</th>
    <td align="left">
        <html-el:text property="floatProperty" size="32"/>
    </td>
    <th align="right">nested.floatProperty</th>
    <td align="left">
        <html-el:text property="nested.floatProperty" size="32"/>
    </td>
</tr>

<tr>
    <th align="right">intProperty</th>
    <td align="left">
        <html-el:text property="intProperty" size="32"/>
    </td>
    <th align="right">nested.intProperty</th>
    <td align="left">
        <html-el:text property="nested.intProperty" size="32"/>
    </td>
</tr>

<tr>
    <th align="right">longProperty</th>
    <td align="left">
        <html-el:text property="longProperty" size="32"/>
    </td>
    <th align="right">nested.longProperty</th>
    <td align="left">
        <html-el:text property="nested.longProperty" size="32"/>
    </td>
</tr>

<tr>
    <th align="right">stringProperty</th>
    <td align="left">
        <html-el:text property="stringProperty" size="32"/>
    </td>
    <th align="right">nested.stringProperty</th>
    <td align="left">
        <html-el:text property="nested.stringProperty" size="32"/>
    </td>
</tr>

<tr>
    <th align="center" colspan="4">Indexed Properties</th>
</tr>

<tr>
    <th align="right">intIndexed[0]</th>
    <td align="left">
        <html-el:text property="intIndexed[0]" size="32"/>
    </td>
    <th align="right">nested.intIndexed[0]</th>
    <td align="left">
        <html-el:text property="nested.intIndexed[0]" size="32"/>
    </td>
</tr>

<tr>
    <th align="right">intIndexed[1]</th>
    <td align="left">
        <html-el:text property="intIndexed[1]" size="32"/>
    </td>
    <th align="right">nested.intIndexed[1]</th>
    <td align="left">
        <html-el:text property="nested.intIndexed[1]" size="32"/>
    </td>
</tr>

<tr>
    <th align="right">stringIndexed[0]</th>
    <td align="left">
        <html-el:text property="stringIndexed[0]" size="32"/>
    </td>
    <th align="right">nested.stringIndexed[0]</th>
    <td align="left">
        <html-el:text property="nested.stringIndexed[0]" size="32"/>
    </td>
</tr>

<tr>
    <th align="right">stringIndexed[1]</th>
    <td align="left">
        <html-el:text property="stringIndexed[1]" size="32"/>
    </td>
    <th align="right">nested.stringIndexed[1]</th>
    <td align="left">
        <html-el:text property="nested.stringIndexed[1]" size="32"/>
    </td>
</tr>

<tr>
    <th align="right">stringIndexed[1]</th>
    <td align="left">
        <html-el:textarea disabled="${!empty pageScope}"
                          property="stringIndexed[1]" rows="${1+3}"/>
    </td>
    <th align="right">nested.stringIndexed[1]</th>
    <td align="left">
        <html-el:textarea disabled="${!empty pageScope}"
                          property="nested.stringIndexed[1]"/>
    </td>
</tr>

<tr>
    <th align="right">stringIndexed[1]</th>
    <td align="left">
        <html-el:text property="stringIndexed[1]"
                      disabled="${!empty pageScope}" size="32"/>
    </td>
    <th align="right">nested.stringIndexed[1]</th>
    <td align="left">
        <html-el:text property="nested.stringIndexed[1]"
                      disabled="${!empty pageScope}" size="32"/>
    </td>
</tr>

<tr>
    <td>&nbsp;</td>
    <td align="right">
        <html-el:submit>Save</html-el:submit>
    </td>
    <td align="left">
        <html-el:reset>Reset</html-el:reset>
        <html-el:cancel>Cancel</html-el:cancel>
    </td>
    <td>&nbsp;</td>
</tr>

</table>

</html-el:form>


</html-el:html>
