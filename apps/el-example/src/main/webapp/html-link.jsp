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
<html-el:html xhtml="${!empty pageScope}">
<head>
    <title>Test html-el:link Tag</title>
    <%
        String newValue = "New string value";
        pageContext.setAttribute("newValue", newValue);
        java.util.HashMap<String, Object> newValues = new java.util.HashMap<String, Object>();
        newValues.put("floatProperty", Float.valueOf((float) 444.0));
        newValues.put("intProperty", Integer.valueOf(555));
        newValues.put("stringArray", new String[]
                { "Value 1", "Value 2", "Value 3" });
        pageContext.setAttribute("newValues", newValues);
    %>
</head>

<body bgcolor="white">

<div align="center">
    <h1>Test struts-html Link Tag</h1>
</div>

The following links should hyperlink back to this page, with various
combinations of request parameters used to modify the previous values of
the associated form bean. Press the "Cancel" button to return to the
main menu.

<html-el:form action="html-link.do">
<table style="border:none;width:100%">

<tr>
    <th colspan="4" align="center">Current Values</th>
</tr>

<tr>
    <th align="right">booleanProperty</th>
    <td align="left">
        <html-el:checkbox property="booleanProperty"/>
    </td>
    <th align="right">intProperty</th>
    <td align="left">
        <html-el:text property="intProperty" size="16" title="intProperty"
                      accesskey="i" tabindex="27" styleId="abc"
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
</tr>

<tr>
    <th align="right">doubleProperty</th>
    <td align="left">
        <html-el:text property="doubleProperty" size="16"/>
    </td>
    <th align="right">longProperty</th>
    <td align="left">
        <html-el:text property="longProperty" size="16"/>
    </td>
</tr>

<tr>
    <th align="right">floatProperty</th>
    <td align="left">
        <html-el:text property="floatProperty" size="16"/>
    </td>
    <th align="right">stringProperty</th>
    <td align="left">
        <html-el:text property="stringProperty" size="16"/>
    </td>
</tr>

<tr>
    <th align="right">stringArray</th>
    <td align="left" colspan="3">
        <html-el:text property="stringArray[0]" size="16"/>
        <html-el:text property="stringArray[1]" size="16"/>
    </td>
</tr>

<tr>
    <th colspan="4" align="center">
        Hyperlinks To Be Tested (via page attribute)
    </th>
</tr>

<tr>
    <td colspan="4" align="center">
        <html-el:link page="/html-link.do" accesskey="1" tabindex="5"
                      title="No modifications at all" styleId="def">
            No modifications at all
        </html-el:link>
    </td>
</tr>

<tr>
    <td colspan="4" align="center">
        <html-el:link
                page="/html-link.do?doubleProperty=321.321&longProperty=321321"
                accesskey="2" tabindex="4"
                title="Double and long via hard coded changes">
            Double and long via hard coded changes
        </html-el:link>
    </td>
</tr>

<tr>
    <td colspan="4" align="center">
        <html-el:link page="/html-link.do" accesskey="3" tabindex="3"
                      paramId="stringProperty" paramName="newValue"
                      title="String via paramId and paramName">
            String via paramId and paramName
        </html-el:link>
    </td>
</tr>

<tr>
    <td colspan="4" align="center">
        <html-el:link page="/html-link.do" accesskey="3" tabindex="3"
                      title="String via nested param tag">
            String via nested param tag
            <html-el:param name="stringProperty" value="${newValue}" />
        </html-el:link>
    </td>
</tr>

<tr>
    <td colspan="4" align="center">
        <html-el:link page="/html-link.do" accesskey="4" tabindex="2"
                      paramId="booleanProperty"
                      paramName="testbean"
                      paramProperty="nested.booleanProperty"
                      title="Boolean via paramId, paramName, and paramValue">
            Boolean via paramId, paramName, and paramValue
        </html-el:link>
    </td>
</tr>

<tr>
    <td colspan="4" align="center">
        <html-el:link page="/html-link.do" accesskey="5" tabindex="1"
                      name="newValues"
                      title="Float, int, and stringArray via name (Map)"
                      onblur="showevent(event)"
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
                >
            Float, int, and stringArray via name (Map)
        </html-el:link>
    </td>
</tr>

<tr>
    <th colspan="4" align="center">
        Hyperlinks To Be Tested (via action attribute)
    </th>
</tr>

<tr>
    <td colspan="4" align="center">
        <html-el:link action="html-link" accesskey="1" tabindex="5"
                      title="No modifications at all" styleId="def">
            No modifications at all
        </html-el:link>
    </td>
</tr>

<tr>
    <td colspan="4" align="center">
        <html-el:link
                action="html-link?doubleProperty=321.321&longProperty=321321"
                accesskey="2" tabindex="4"
                title="Double and long via hard coded changes">
            Double and long via hard coded changes
        </html-el:link>
    </td>
</tr>

<tr>
    <td colspan="4" align="center">
        <html-el:link action="html-link" accesskey="3" tabindex="3"
                      paramId="stringProperty" paramName="newValue"
                      title="String via paramId and paramName">
            String via paramId and paramName
        </html-el:link>
    </td>
</tr>

<tr>
    <td colspan="4" align="center">
        <html-el:link action="html-link" accesskey="4" tabindex="2"
                      paramId="booleanProperty"
                      paramName="testbean"
                      paramProperty="nested.booleanProperty"
                      title="Boolean via paramId, paramName, and paramValue">
            Boolean via paramId, paramName, and paramValue
        </html-el:link>
    </td>
</tr>

<tr>
    <td colspan="4" align="center">
        <html-el:link action="html-link" accesskey="5" tabindex="1"
                      name="newValues"
                      title="Float, int, and stringArray via name (Map)"
                      onblur="showevent(event)"
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
                >
            Float, int, and stringArray via name (Map)
        </html-el:link>
    </td>
</tr>

<tr>
    <th colspan="4" align="center">Reset and Cancel Buttons</th>
</tr>

<tr>
    <td colspan="4" align="center">
        <html-el:reset>Reset</html-el:reset>
        <html-el:cancel>Cancel</html-el:cancel>
    </td>
</tr>

</table>

</html-el:form>

<script>
    <!--
    function showevent(evt) {
        window.status = evt.type;
    }
    // -->
</script>

</body>
</html-el:html>
