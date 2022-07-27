<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:html xhtml="true" lang="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Select / Options examples</title>
<html:base />
<link rel="stylesheet" type="text/css" href="../../css/example.css" />
</head>
<body>
<html:link page="/jsp/options/source.jsp">
  <img src="../../images/code.gif" width="24" height="24" alt="View Source" class="icon" />
</html:link>
<a href="../../index.jsp"><img src="../../images/return.gif" height="24" width="24" alt="Return to examples page" class="icon" /></a>
<h1>Select / Options examples</h1>
<hr noshade="noshade"/>
<p>View the HTML source to see the generated option values.</p>
<html:form action="/processOptions">
    <h2>1. Simple select tags</h2>
    <table width="100%" border="0" cellspacing="10" cellpadding="0">
        <tr valign="top">
            <td width="33%">
            <p>Single select, size=&quot;1&quot; (drop down list)</p>
            <p><html:select property="fruit1">
                <html:option value="Strawberry">Strawberry</html:option>
                <html:option value="Apple">Apple</html:option>
                <html:option value="Orange">Orange</html:option>
                <html:option value="Pear">Pear</html:option>
                <html:option value="Mango">Mango</html:option>
                <html:option value="Banana">Banana</html:option>
                <html:option value="Pineapple">Pineapple</html:option>
            </html:select></p>
            </td>
            <td width="33%">
            <p>Single select, size=&quot;4&quot;</p>
            <p><html:select property="fruit2" size="4">
                <html:option value="Strawberry">Strawberry</html:option>
                <html:option value="Apple">Apple</html:option>
                <html:option value="Orange">Orange</html:option>
                <html:option value="Pear">Pear</html:option>
                <html:option value="Mango">Mango</html:option>
                <html:option value="Banana">Banana</html:option>
                <html:option value="Pineapple">Pineapple</html:option>
            </html:select></p>
            </td>
            <td width="33%">
            <p>Multi-select, size=&quot;7&quot;</p>
            <p><html:select property="fruit3" size="7" multiple="true">
                <html:option value="Strawberry">Strawberry</html:option>
                <html:option value="Apple">Apple</html:option>
                <html:option value="Orange">Orange</html:option>
                <html:option value="Pear">Pear</html:option>
                <html:option value="Mango">Mango</html:option>
                <html:option value="Banana">Banana</html:option>
                <html:option value="Pineapple">Pineapple</html:option>
            </html:select></p>
            </td>
        </tr>
    </table>
    <h2>2. Populating options from arrays and collections</h2>
    <table width="100%" border="0" cellspacing="10" cellpadding="0">
        <tr>
            <td width="33%" valign="top">
            <p>Option values and labels populated from the same array:</p>
            <p><html:select property="color1" size="7">
                <html:options name="colors" />
                </html:select>
            </p>
            </td>
            <td width="33%" valign="top">
            <p>Option values and labels populated from different arrays:</p>
            <p><html:select property="color2" size="7">
                <html:options name="colorCodes" labelName="colors" />
            </html:select></p>
            </td>
            <td width="33%" valign="top">
            <p>Option values populated from an array and labels populated from a
            collection:</p>
            <p><html:select property="color3" size="7">
                <html:options name="colorCodes" labelName="colorCollection" />
            </html:select></p>

            </td>
        </tr>
    </table>
    <h2>3. Populating options from a Collection of LabelValueBeans</h2>
    <table width="100%" border="0" cellspacing="10" cellpadding="0">
        <tr valign="top">
            <td width="33%">
            <p>Options populated from a Collection of LabelValueBeans, using
            &lt;html:options&gt;:</p>
            <p><html:select property="day1" size="7">
                <html:options collection="days" property="value"
                    labelProperty="label" />
            </html:select></p>
            </td>
            <td width="33%">
            <p>Options populated from a Collection of LabelValueBeans, using
            &lt;html:optionsCollection&gt;:</p>
            <p><html:select property="day2" size="7">
                <html:optionsCollection name="days" />
            </html:select></p>
            </td>
            <td width="33%">&nbsp;</td>
        </tr>
    </table>
    <h2>4. Populating options from a Collection of custom beans</h2>
    <table width="100%" border="0" cellspacing="10" cellpadding="0">
        <tr valign="top">
            <td width="33%">
            <p>Options populated from a Collection of BookBeans, using<br />
            &lt;html:options&gt;:</p>
            <p><html:select property="book1" size="7">
                <html:options collection="books" property="isbn"
                    labelProperty="title" />
            </html:select></p>
            </td>
            <td width="33%">
            <p>Options populated from a Collection of BookBeans, using<br />
            &lt;html:optionsCollection&gt;:</p>
            <p><html:select property="book2" size="7">
                <html:optionsCollection name="books" value="isbn" label="title" />
            </html:select></p>
            </td>
            <td width="33%">&nbsp;</td>
        </tr>
    </table>
    <h2>4. Populating options from a Map</h2>
    <table width="100%" border="0" cellspacing="10" cellpadding="0">
        <tr valign="top">
            <td width="33%">
            <p>Options populated from a Map, using <br />
            &lt;html:options&gt;:</p>
            <p><html:select property="animal1" size="5">
                <html:options collection="animals" property="key"
                    labelProperty="value" />
            </html:select></p>
            </td>
            <td width="33%">
            <p>Options populated from a Map, using <br />
            &lt;html:optionsCollection&gt;:</p>
            <p><html:select property="animal2" size="5">
                <html:optionsCollection name="animals" value="key" label="value" />
            </html:select></p>
            </td>
            <td width="33%">&nbsp;</td>
        </tr>
    </table>

    <hr />
    <p><html:submit />
       <html:cancel />
       <html:reset />
    </p>
</html:form>

</body>
</html:html>