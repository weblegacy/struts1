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

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Struts Cookbook</title>
<html:xhtml/>
<html:base/>
<link href="css/example.css" rel="stylesheet" type="text/css" />
</head>
<body>
<h2>Struts Examples with Code</h2>
<p>This is a collection of examples which demonstrate some of the more frequently
  used Struts Tags. Familiarity with the Java(tm) Programming Language and HTML
  is assumed. </p>
<p>To navigate your way through the examples, the following icons will help: </p>
<table border="0" cellspacing="5" width="85%" >
  <tr valign="top">
    <td width="30"><img alt="" src="images/execute.gif" /></td>
    <td>Execute the example</td>
  </tr>
  <tr valign="top">
    <td width="30"><img alt="" src="images/return.gif" height="24" width="24" /></td>
    <td>Return to this screen</td>
  </tr>
  <tr valign="top">
    <td><img alt="" src="images/code.gif" height="24" width="24" /></td>
    <td>View the source code for the example</td>
  </tr>
</table>
<br />
<table width="85%" border="0" cellpadding="2" cellspacing="5">
  <tr valign="top">
    <td>Simple Form using ActionForm</td>
    <td>
        <html:link action="/prepareSimple">
          <img src="images/execute.gif" alt="" hspace="4" border="0"  align="top" class="inline" />
        </html:link>
        <html:link action="/prepareSimple">Execute</html:link>
    </td>
    <td>
        <html:link page="/jsp/simple/source.jsp">
          <img src="images/code.gif" alt="" width="24" height="24" hspace="4" border="0" align="top" class="inline" />
        </html:link>
        <html:link page="/jsp/simple/source.jsp">
          View source
        </html:link>
    </td>
  </tr>
  <tr valign="top">
    <td>Simple Form using DynaActionForm</td>
    <td>
        <html:link action="/prepareDyna">
          <img src="images/execute.gif" alt="" hspace="4" border="0"  align="top" class="inline" />
        </html:link>
        <html:link action="/prepareDyna">Execute</html:link>
    </td>
    <td>
        <html:link page="/jsp/dyna/source.jsp">
          <img src="images/code.gif" alt="" width="24" height="24" hspace="4" border="0" align="top" class="inline" />
        </html:link>
        <html:link page="/jsp/dyna/source.jsp">
          View source
        </html:link>
    </td>
  </tr>
  <tr valign="top">
    <td>Select and Options tags</td>
    <td>
        <html:link action="/prepareOptions">
          <img src="images/execute.gif" alt="" hspace="4" border="0"  align="top" class="inline" />
        </html:link>
        <html:link action="/prepareOptions">Execute</html:link>
    </td>
    <td>
        <html:link page="/jsp/options/source.jsp">
          <img src="images/code.gif" alt="" width="24" height="24" hspace="4" border="0" align="top" class="inline" />
        </html:link>
        <html:link page="/jsp/options/source.jsp">
          View source
        </html:link>
    </td>
  </tr>
  <tr valign="top">
    <td>Multibox (for checkboxes)</td>
    <td>
        <html:link action="/prepareMultibox">
          <img src="images/execute.gif" alt="" hspace="4" border="0"  align="top" class="inline" />
        </html:link>
        <html:link action="/prepareMultibox">Execute</html:link>
    </td>
    <td>
        <html:link page="/jsp/multibox/source.jsp">
          <img src="images/code.gif" alt="" width="24" height="24" hspace="4" border="0" align="top" class="inline" />
        </html:link>
        <html:link page="/jsp/multibox/source.jsp">
          View source
        </html:link>
    </td>
  </tr>
  <tr valign="top">
    <td>Bean tags</td>
    <td> <html:link action="/prepareBean?param1=Test1&amp;param2=Test2"  > <img src="images/execute.gif" alt="" hspace="4" border="0"  align="top" class="inline" /> </html:link> <html:link action="/prepareBean?param1=Test1&amp;param2=Test2">Execute</html:link> </td>
    <td> <html:link page="/jsp/bean/source.jsp"> <img src="images/code.gif" alt="" width="24" height="24" hspace="4" border="0" align="top" class="inline" /> </html:link> <html:link page="/jsp/bean/source.jsp"> View
        source </html:link> </td>
  </tr>
  <tr valign="top">
    <td>Logic tags</td>
    <td><html:link action="/prepareLogic">
          <img src="images/execute.gif" alt="" hspace="4" border="0"  align="top" class="inline" />
        </html:link>
        <html:link action="/prepareLogic">Execute</html:link>
    </td>
    <td><html:link page="/jsp/logic/source.jsp">
          <img src="images/code.gif" alt="" width="24" height="24" hspace="4" border="0" align="top" class="inline" />
        </html:link>
        <html:link page="/jsp/logic/source.jsp">View source</html:link>
    </td>
  </tr>
  <tr valign="top">
    <td>Links</td>
    <td><html:link action="/prepareLinks">
          <img src="images/execute.gif" alt="" hspace="4" border="0"  align="top" class="inline" />
        </html:link>
        <html:link action="/prepareLinks">Execute</html:link>
    </td>
    <td><html:link page="/jsp/links/source.jsp">
          <img src="images/code.gif" alt="" width="24" height="24" hspace="4" border="0" align="top" class="inline" />
        </html:link>
        <html:link page="/jsp/links/source.jsp">View source</html:link>
    </td>
  </tr>
  <tr valign="top">
    <td>Validator</td>
    <td><html:link action="/prepareValidator">
          <img src="images/execute.gif" alt="" hspace="4" border="0"  align="top" class="inline" />
        </html:link>
        <html:link action="/prepareValidator">Execute</html:link>
    </td>
    <td><html:link page="/jsp/validator/source.jsp">
          <img src="images/code.gif" alt="" width="24" height="24" hspace="4" border="0" align="top" class="inline" />
        </html:link>
        <html:link page="/jsp/validator/source.jsp">View source</html:link>
    </td>
  </tr>
  <tr valign="top">
    <td>Message resources</td>
    <td><html:link action="/prepareMessages">
          <img src="images/execute.gif" alt="" hspace="4" border="0"  align="top" class="inline" />
        </html:link>
    <html:link action="/prepareMessages">Execute</html:link>    </td>
    <td><html:link page="/jsp/messages/source.jsp">
          <img src="images/code.gif" alt="" width="24" height="24" hspace="4" border="0" align="top" class="inline" />
        </html:link>
        <html:link page="/jsp/messages/source.jsp">View source</html:link>    </td>
  </tr>
  <tr valign="top">
    <td>Localization</td>
    <td><html:link action="/prepareLocalization">
          <img src="images/execute.gif" alt="" hspace="4" border="0"  align="top" class="inline" />
        </html:link>
        <html:link action="/prepareLocalization">Execute</html:link>
    </td>
    <td><html:link page="/jsp/localization/source.jsp">
          <img src="images/code.gif" alt="" width="24" height="24" hspace="4" border="0" align="top" class="inline" />
        </html:link>
        <html:link page="/jsp/localization/source.jsp">View source</html:link>
    </td>
  </tr>
  <tr valign="top">
    <td>Control duplication form submission</td>
    <td><html:link action="/prepareToken">
          <img src="images/execute.gif" alt="" hspace="4" border="0"  align="top" class="inline" />
        </html:link>
        <html:link action="/prepareToken">Execute</html:link>
    </td>
    <td><html:link page="/jsp/token/source.jsp">
          <img src="images/code.gif" alt="" width="24" height="24" hspace="4" border="0" align="top" class="inline" />
        </html:link>
        <html:link page="/jsp/token/source.jsp">View source</html:link>
    </td>
  </tr>
  <tr valign="top">
    <td>Wildcard Action Mappings</td>
    <td><html:link action="/PrepareWildcard">
          <img src="images/execute.gif" alt="" hspace="4" border="0"  align="top" class="inline" />
        </html:link>
        <html:link action="/PrepareWildcard">Execute</html:link>
    </td>
    <td><html:link action="/SourceWildcard">
          <img src="images/code.gif" alt="" width="24" height="24" hspace="4" border="0" align="top" class="inline" />
        </html:link>
        <html:link action="/SourceWildcard">View source</html:link>
    </td>
  </tr>

</table>
<p><img src="images/valid-xhtml10.png" alt="Valid XHTML 1.0!" height="31" width="88" /></p>
</body>

</html>