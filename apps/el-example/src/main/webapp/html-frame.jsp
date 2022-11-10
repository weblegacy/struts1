<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN">
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
<%
    Integer border = 0;
    pageContext.setAttribute("border", border);
    String thing = "thisFrame";
    pageContext.setAttribute("thing", thing);
    String scrolling = "yes";
    pageContext.setAttribute("scrolling", scrolling);
%>
<html-el:html>
    <frameset rows="33%,33%,34%" cols="33%,33%,34%">
        <html-el:frame href="html-frame1.jsp"/>
        <html-el:frame href="html-frame1.jsp" noresize="${!empty pageScope}"/>
        <html-el:frame href="html-frame1.jsp"
                       transaction="${!empty pageScope}"/>
        <html-el:frame href="html-frame1.jsp" frameborder="${border}"/>
        <html-el:frame href="html-frame1.jsp" frameName="${thing}"/>
        <html-el:frame href="html-frame1.jsp" marginheight="${border + 10}"/>
        <html-el:frame href="html-frame1.jsp" marginwidth="${border + 15}"/>
        <html-el:frame href="html-frame1.jsp" scrolling="${scrolling}"/>
        <html-el:frame href="html-frame1.jsp" styleId="${thing}"/>
    </frameset>
</html-el:html>
