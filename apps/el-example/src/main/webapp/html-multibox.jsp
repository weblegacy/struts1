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
    <title>Test html-el:multibox Tag</title>
</head>
<body bgcolor="white">

<div align="center">
    <h1>Test struts html-el:multibox Tag</h1>
</div>

Whatever changes you make to properties should be reflected when the page
is redisplayed. When first started, all of the listed checkboxes should not
be selected. Press "Save" to update, or "Cancel" to return to the
main menu.

<html-el:form action="html-multibox.do">
<table border="0" width="100%">

<tr>
    <th align="center" colspan="4">String Array Values</th>
</tr>

<tr>
    <th align="right">String 0</th>
    <td align="left">
        <html-el:multibox property="stringMultibox">
            String 0
        </html-el:multibox>
    </td>
    <th align="right">(nested) String 0</th>
    <td align="left">
        <html-el:multibox property="nested.stringMultibox" value="String 0"/>
    </td>
</tr>

<tr>
    <th align="right">String 1</th>
    <td align="left">
        <html-el:multibox property="stringMultibox">
            String 1
        </html-el:multibox>
    </td>
    <th align="right">(nested) String 1</th>
    <td align="left">
        <html-el:multibox property="nested.stringMultibox" value="String 1"/>
    </td>
</tr>

<tr>
    <th align="right">String 2</th>
    <td align="left">
        <html-el:multibox property="stringMultibox">
            String 2
        </html-el:multibox>
    </td>
    <th align="right">(nested) String 2</th>
    <td align="left">
        <html-el:multibox property="nested.stringMultibox" value="String 2"/>
    </td>
</tr>

<tr>
    <th align="right">String 3</th>
    <td align="left">
        <html-el:multibox property="stringMultibox">
            String 3
        </html-el:multibox>
    </td>
    <th align="right">(nested) String 3</th>
    <td align="left">
        <html-el:multibox property="nested.stringMultibox" value="String 3"/>
    </td>
</tr>

<tr>
    <th align="right">String 4</th>
    <td align="left">
        <html-el:multibox property="stringMultibox">
            String 4
        </html-el:multibox>
    </td>
    <th align="right">(nested) String 4</th>
    <td align="left">
        <html-el:multibox property="nested.stringMultibox" value="String 4"/>
    </td>
</tr>

<tr>
    <th align="center" colspan="4">Integer Array Values</th>
</tr>

<tr>
    <th align="right">0</th>
    <td align="left">
        <html-el:multibox property="intMultibox" value="0"/>
    </td>
    <th align="right">(nested) 0</th>
    <td align="left">
        <html-el:multibox property="nested.intMultibox">
            0
        </html-el:multibox>
    </td>
</tr>

<tr>
    <th align="right">10</th>
    <td align="left">
        <html-el:multibox property="intMultibox" value="10"/>
    </td>
    <th align="right">(nested) 10</th>
    <td align="left">
        <html-el:multibox property="nested.intMultibox">
            10
        </html-el:multibox>
    </td>
</tr>

<tr>
    <th align="right">20</th>
    <td align="left">
        <html-el:multibox property="intMultibox" value="20"/>
    </td>
    <th align="right">(nested) 20</th>
    <td align="left">
        <html-el:multibox property="nested.intMultibox">
            20
        </html-el:multibox>
    </td>
</tr>

<tr>
    <th align="right">30</th>
    <td align="left">
        <html-el:multibox property="intMultibox" value="30"/>
    </td>
    <th align="right">(nested) 30</th>
    <td align="left">
        <html-el:multibox property="nested.intMultibox">
            30
        </html-el:multibox>
    </td>
</tr>

<tr>
    <th align="right">40</th>
    <td align="left">
        <html-el:multibox property="intMultibox" value="40"/>
    </td>
    <th align="right">(nested) 40</th>
    <td align="left">
        <html-el:multibox property="nested.intMultibox">
            40
        </html-el:multibox>
    </td>
</tr>

<tr>
    <th align="right">50</th>
    <td align="left">
        <html-el:multibox property="intMultibox" value="50"/>
    </td>
    <th align="right">(nested) 50</th>
    <td align="left">
        <html-el:multibox property="nested.intMultibox"
                          disabled="${!empty pageScope}">
            50
        </html-el:multibox>
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
