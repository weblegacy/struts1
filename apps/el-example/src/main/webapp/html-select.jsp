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
<%@ page language="java" import="org.apache.struts.util.LabelValueBean, java.util.ArrayList" %>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean-el" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el" %>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic-el" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html-el:html>
<head>
    <title>Test html-el:select Tag</title>
    <%
        String multipleValues[] =
                { "Multiple 0", "Multiple 1", "Multiple 2", "Multiple 3",
                        "Multiple 4",
                        "Multiple 5", "Multiple 6", "Multiple 7",
                        "Multiple 8", "Multiple 9" };
        pageContext.setAttribute("multipleValues", multipleValues);

        ArrayList<LabelValueBean> options = new ArrayList<LabelValueBean>();
        options.add(new LabelValueBean("Label 0", "Value 0"));
        options.add(new LabelValueBean("Label 1", "Value 1"));
        options.add(new LabelValueBean("Label 2", "Value 2"));
        options.add(new LabelValueBean("Label 3", "Value 3"));
        options.add(new LabelValueBean("Label 4", "Value 4"));
        options.add(new LabelValueBean("Label 5", "Value 5"));
        options.add(new LabelValueBean("Label 6", "Value 6"));
        options.add(new LabelValueBean("Label 7", "Value 7"));
        options.add(new LabelValueBean("Label 8", "Value 8"));
        options.add(new LabelValueBean("Label 9", "Value 9"));
        pageContext.setAttribute("options", options);

        String withNulls[] =
                { "String 0", null, "String 2" };
        pageContext.setAttribute("withNulls", withNulls);

    %>
</head>
<body bgcolor="white">

<div align="center">
    <h1>Test struts-html-el Select Tag</h1>
</div>

Whatever changes you make to properties should be reflected when the page
is redisplayed. Press "Save" to update, or "Cancel" to return to the
main menu.

<%--
     Ensure that the form bean exists before the form tag is processed. This
     is a simple (if not entirely clean) way of ensuring that the initial
     values assigned during bean instantiation will be available within the
     form, since reset() will not be called when the form bean already exists.

     The right way to fix this is to modify this webapp so that it does not
     refer directly to JSP pages, but goes through Action classes, and to
     either modify the TestBean class, adding an initialize() method, or to
     have an Action class set the initial values.
--%>
<jsp:useBean id="testbean" scope="session"
             class="org.apache.struts.webapp.el.exercise.TestBean"/>

<html-el:form action="html-select.do">
    <table border="0" width="100%">

        <tr>
            <th align="right">Single Select Allowed:</th>
            <td align="left">
                <html-el:select property="singleSelect" size="10">
                    <html-el:option value="Single 0" title="0">Single 0</html-el:option>
                    <html-el:option value="Single 1" title="1">Single 1</html-el:option>
                    <html-el:option value="Single 2" title="${1+1}">Single 2</html-el:option>
                    <html-el:option value="Single 3" title="3"
                                    disabled="${!empty pageScope}">
                        Single 3
                    </html-el:option>
                    <html-el:option value="Single 4" title="${2+2}">Single 4</html-el:option>
                    <html-el:option value="Single 5" title="5">Single 5</html-el:option>
                    <html-el:option value="Single 6" title="${3+3}">Single 6</html-el:option>
                    <html-el:option value="Single 7" title="7">Single 7</html-el:option>
                    <html-el:option value="Single 8" title="${4+4}">Single 8</html-el:option>
                    <html-el:option value="Single 9" titleKey="select.single.9" styleId="flork">Single
                        9</html-el:option>
                </html-el:select>
            </td>
        </tr>

        <tr>
            <th align="right">Multiple Select Allowed:</th>
            <td align="left">
                <html-el:select property="multipleSelect" size="10"
                                multiple="true">
                    <html-el:options name="multipleValues"
                                     labelName="multipleValues"/>
                </html-el:select>
            </td>
        </tr>

        <tr>
            <th align="right">Multiple Select From A Collection (Using &lt;html-el:options&gt;):</th>
            <td align="left">
                <html-el:select property="collectionSelect" size="10"
                                multiple="true">
                    <html-el:options collection="options" property="value"
                                     labelProperty="label"/>
                </html-el:select>
            </td>
        </tr>

        <tr>
            <th align="right">Multiple Select From A Collection (Using &lt;html-el:optionsCollection&gt;):</th>
            <td align="left">
                <html-el:select property="beanCollectionSelect" size="10"
                                multiple="true">
                    <html-el:optionsCollection name="testbean"
                                               property="beanCollection"
                                               filter="${empty pageScope}"/>
                </html-el:select>
            </td>
        </tr>

        <tr>
            <th align="right">
                Collection with null labels and values:
            </th>
            <td align="left">
                <html-el:select property="withNulls" size="3">
                    <html-el:options name="withNulls" labelName="withNulls"
                                     filter="${empty pageScope}"/>
                </html-el:select>
            </td>
        </tr>

        <tr>
            <th align="right">
                Disabled select
            </th>
            <td align="left">
                <html-el:select disabled="${!empty pageScope}"
                                property="withNulls" size="3">
                    <html-el:options name="withNulls" labelName="withNulls"
                                     filter="${empty pageScope}"/>
                </html-el:select>
            </td>
        </tr>

        <tr>
            <td align="right">
                <html-el:submit>Save</html-el:submit>
            </td>
            <td align="left">
                <html-el:reset>Reset</html-el:reset>
                <html-el:cancel>Cancel</html-el:cancel>
            </td>
        </tr>

    </table>

</html-el:form>


</html-el:html>
