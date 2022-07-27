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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- :TODO: It would be interesting to try this with frames and modules -->

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta name="generator" content="HTML Tidy for Windows (vers 1st July 2003), see www.w3.org" />

  <title>Struts Examples</title>
</head>

<body>
  <h1>Struts Examples</h1>

  <p>Each of these links lead to a separate "module" within this application.</p>

  <ul>
    <li><html:link module="/exercise" action="/welcome">Taglib Test Pages</html:link></li>

    <li><html:link module="/upload" action="/upload">Upload examples</html:link></li>
    <li><html:link module="/upload" action="/upload2">Upload Test for STR-3173</html:link></li>

    <li><html:link module="/validator" action="/welcome">Validator and Localization examples</html:link></li>

    <li><html:link module="/dispatch" action="/welcome">Dispatch Action examples</html:link></li>
  </ul>

  <p>These modules follow the "learn by example" school. Be sure to "look under the hood" to see how it's done.</p>

  <!-- For the sake of example, use the page form with this set of links: -->

  <h2><html:link module="/exercise" page="/welcome.do">Taglib Test Pages</html:link></h2>

  <p>These pages are designed to test the operation of the various taglibs that come bundled with Struts. If you examine the JSP templates, you'll see that these pages use a lot of scriptlets. We use scriptlets here in order to test the tags. <strong>Most production pages will not need to use scriplets.</strong> In fact many Struts developers <em>never</em> use scriplets in a production page.</p>

  <h2><html:link module="/upload" page="/upload.do">Upload example</html:link></h2>

  <p>Struts uses the Commons Upload package to provide support for uploading files. This example shows you how to upload a page for processing or how to just display it.</p>

  <h2><html:link module="/validator" page="/welcome.do">Validator examples</html:link></h2>

  <p>The Struts Validator uses the same resource bundles as the rest of your Struts application, making localization easy to do. These examples show you how to use the validator with and without JavaScript, as well as how to localize your validations.</p>

  <h2>But, wait, there's more ...</h2>

  <p>In addion to this application, there are three other applications available for you to install:</p>

  <ul>
    <li>Struts Blank</li>

    <li>Struts Cookbook (more examples)</li>

    <li>Struts MailReader</li>
  </ul>

  <p><strong>Struts Blank</strong> is an application template. Explode this WAR
  under your own application's name, and you can start coding! <strong>Struts
  Mailreader</strong> is a starter business application, much like the one most
  of you need to develop. <strong>Struts Cookbook</strong> is a collection of
  examples which demonstrate some of the more frequently used Struts Tags.</p>

  <hr />
</body>
</html>
