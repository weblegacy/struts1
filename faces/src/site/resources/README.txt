The Struts-Faces Integration Library (Version 1.0) README File
$Id$


============
INTRODUCTION:
============

This package contains an add-on library that supports the use of
JavaServer Faces (JSF) user interface technology in a Struts based web
application, in place of the Struts custom tag libraries.

The Struts-Faces Integration Library should work with any implementation
of JavaServer Faces, version 1.0 or later.  It has primarily been tested
against version 1.1 of the JavaServer Faces reference implementation,
available at:

  http://java.sun.com/j2ee/javaserverfaces/


========================
NEW AND REVISED FEATURES:
========================

* As of the nightly build 20040902, the URI for the struts-faces tag library
  has changed.  You should now be using:

    <%@ taglib prefix="s" uri="http://struts.apache.org/tags-faces" %>

  instead of:

    <%@ taglib prefix="s" uri="http://jakarta.apache.org/struts/tags-faces" %>

* It is now possible to mix pure JavaServer Faces pages, and those
  using the struts-faces integration library, in the same webapp.  Previously,
  it was required to use only Struts-based handlers for form submits.

* All attributes of the component tags in the Struts-Faces integration library
  have been "value binding enabled", meaning you can use value binding
  expressions ("#{...}") to calculate attribute values dynamically.

* It is now possible to use the Struts-Faces Integration Library in conjunction
  with application modules using Tiles.

* You may now use a managed bean named "struts" at the beginning of any
  value binding expression in order to gain access to request, session, and
  application scope objects provided by Struts.  See the Javadocs for the
  implementation class (org.apache.struts.faces.util.StrutsContext)
  for more information about what objects are available.

* You may now use either prefix mapping (/faces/*) or extension mapping
  (*.faces) for the JavaServer Faces controller servlet.

* In addition to the <s:message> tag that operates as a direct replacement
  for <html:message>, you may also consider using the new <s:loadMessages>
  tag that exposes a MessageResources instance as a request attribute
  containing a Map.  This makes the messages included in the instance
  available via value binding expressions (or JSP 2.0 EL expressions).
  For example, the logon.jsp page of the example application includes:

    ...
    <s:loadMessages var="messages"/>
    ...
    <h:outputText value="#{messages['logon.header']}"/>
    ...

  to create the header text for the logon form.  You may either specify the
  application scope key for the MessageResources bunde you want, or omit
  the "messages" attribute to load the default MessageResources for the
  current application module.

* You can leverage advanced JavaServer Faces features in a Struts based
  web application.  For example, the converted "Mail Reader" example includes
  using the <h:dataTable> for multi-row input as well as output.

* If your Struts application stores a Locale in the session attribute
  named Globals.LOCALE_KEY (i.e. using the usual Struts technique for
  establishing a user specific locale), this setting will be copied to
  the UIViewRoot of the corresponding JSF view, which will therefore
  cause it to change the Locale used by JSF components and renderers also.

* New command link component (and associated <s:commandLink> tag that mirrors
  the functionality of the standard <h:commandLink> component, but works when
  inside a Struts <s:form> component.

This release of the Struts-Faces Integration Library (Version 1.0) has the
following revised features relative to the previous (0.4) release:

* The "focus" attribute on the <s:form> tag should work properly
  in all cases now.

* Integration with the Validator Framework should work properly
  in all cases now.

=======================================================
USING THE STRUTS-FACES LIBRARY IN YOUR OWN APPLICATIONS:
=======================================================

Using the Struts-Faces integration library in your own Struts-based web
applications is straightforward, and requires the following steps:

* Add the "struts-faces.jar" file from the "lib" subdirectory of this
  release into the "/WEB-INF/lib" subdirectory of your webapp.

* Add the following JAR files from the JavaServer Faces reference
  implementation's "lib" directory to your application's
  "/WEB-INF/lib" directory:  jsf-api.jar, jsf-impl.jar,
  or install them in your servlet container's shared folder.
  (You can also use any other JSF implementation that has
  been certified to be compatible with the JSF specification.)

* Add the following JAR files, containing the JSTL release (or
  from the JavaServer Faces release) to your application's
  "/WEB-INF/lib" directory:  jstl.jar, standard.jar, or
  install them in your servlet container's shared folder.

* Add the servlet definition for the JavaServer Faces servlet into
  your web application's deployment descriptor (/WEB-INF/web.xml):

    <servlet>
      <servlet-name>faces</servlet-name>
      <servlet-class>jakarta.faces.webapp.FacesServlet</servlet-class>
      <load-on-startup>1</load-on-startup>
    </servlet>

* If you have a <load-on-startup> element on your declaration of the
  Struts controller servlet, modify the value to be "2" or greater
  so that FacesServlet is initialized first.

* Add the servlet mapping for the JavaServer Faces servlet into
  your web application's deployment descriptor (/WEB-INF/web.xml):

    <servlet-mapping>
      <servlet-name>faces</servlet-name>
      <url-pattern>*.faces</url-pattern>
    </servlet-mapping>

* The tag library in the Struts-Faces integration library (as well
  as those in the JavaServer Faces reference implementation) are
  embedded in the JAR files themselves, and rely on the ability of a
  Servlet 2.3 (or later) container to automatically recognize them.
  Therefore, there is no need to copy the TLD files into the WEB-INF
  subdirectory of your web application.

* Modify the JSP pages of your web application to use the JSTL,
  JavaServer Faces, and Struts-Faces integration library tags, instead
  of the traditional Struts tag libraries.  This migration can occur
  one page at a time, as you become familiar with the new technologies.
  You will want to note the following points in particular:

  - Include the following tag library directives at the top of your
    page in order to declare them:

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
    <%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
    <%@ taglib prefix="s" uri="http://struts.apache.org/tags-faces" %>

  - The Struts-Faces tag library (prefix "s" above) contains replacements
    for functionality in the existing Struts HTML tag library that are
    not directly provided by JavaServer Faces components.  You should
    convert your existing use of the Struts HTML variants of these tags
    to use the Struts-Faces version instead.  (Functionality and attributes
    should be basically compatible, so this is usually just a matter of
    changing the tag prefixes.)

  - In particular, you must use the Struts-Faces version of the
    form tag (<s:form>) in order to activate standard Struts features
    like automatic creation of the form bean, and looking up the
    appropriate action to invoke based on the "action" attribute.

  - Replace the use of tags from the Struts HTML library with user interface
    component tags provided by the JavaServer Faces reference implementation,
    by other third party libraries, or by your application itself.  For
    example, on the logon.jsp page, the username field was changed from:

      <html:text property="username" size="16" maxlength="18"/>

    to the following JavaServer Faces Component tag:

      <h:inputText id="username" size="16" maxlength="18"
              value="#{logonForm.username}"/>

  - JavaServer Faces provides its own mechanisms for internationalizing
    user interfaces.  These can be used directly; however, to ease the
    transition for existing Struts-based web applications, the Struts-Faces
    integration library supports the <s:message/> tag, which is functionally
    equivalent to the previous <bean:message> tag.

  - Optionally, replace the use of tags from the Struts BEAN and LOGIC
    libraries with corresponding functionality from JSTL tags.  This is
    recommended, because JSTL tags are more powerful than their Struts
    library counterparts, and the expression language syntax is the same
    as that used for value reference expressions.

* Modify your struts-config.xml file to include identification of the custom
  request processor implementation class to be used, by adding the following
  element in the appropriate location (typically just before any existing
  <message-resources> and <plug-in> elements), one of the following
  controller element declarations.

  If your application module does *not* use Tiles:

    <controller>
      <set-property property="processorClass"
       value="org.apache.struts.faces.application.FacesRequestProcessor"/>
    </controller>

  If your application module *does* use Tiles:

    <controller>
      <set-property property="processorClass"
       value="org.apache.struts.faces.application.FacesTilesRequestProcessor"/>
    </controller>


* For each JSP page that you have modified to use JavaServer Faces
  components instead of traditional Struts tags, modify any <forward>
  elements in your webapp's struts-config.xml file to include "/faces"
  in front of the path to that page (if you are using prefix mapping),
  or a ".faces" extension (if you are using extension mapping.  For
  example, change:

    <forward name="registration" path="/registration.jsp"/>

  to this:

    <forward name="registration" path="/registration.faces"/>

* In most circumstances, you should not need to make any changes in
  your Actions, or the business logic classes invoked by your actions.
  They are still invoked as part of the standard Struts request
  processing lifecycle, and are still expected to return an
  ActionForward (or null) defining what view layer technology
  should be invoked next.

    NOTE:  If you have a command component whose "immediate" property
    is set to "true", it will be processed as it would in a pure
    JavaServer Faces based application.  Only command components with
    immediate="false" (which is the default value), that are nested
    inside a Struts-Faces <s:form> tag, will be forwarded through the
    normal Struts request processing lifecycle.

* If your application contains cancel buttons rendered by the <html:cancel>
  tag, you should replace them with an <h:commandButton> that has an
  "id" attribute set to "cancel" in order for this button to be recognized
  by Struts as a cancel button.

* If your application itself provides additional UIComponent and/or
  Renderer implementations, you must register them with the default
  JavaServer Faces RenderKit before they can be used.  The simplest
  way to do this is to define a "faces-config.xml" file that contains
  the declaration for your custom classes.  Such a file can be included
  either in the "/WEB-INF" directory of your web application, or in the
  "META-INF" directory of a JAR file included in "/WEB-INF/lib".
  (The Struts-Faces Integration Library itself uses the latter technique
  to register its custom components automatically for any web application
  that includes "struts-faces.jar" in its "/WEB-INF/lib" directory.)


=================
KNOWN LIMITATIONS:
=================

The following items identify functionality areas that have not yet been
fully implemented or tested:

* Use of the Struts-Faces integration library in multiple application modules
  (likely to not work without the addition of a Filter to set the correct
  module configuration instance).

* Use of the "forwardPattern" or "pagePattern" attributes on the
  <controller> element.

* Use of the Struts Nested tag libraries.

* Use of the Struts-EL tag library (although this should be unnecessary,
  since you are free to use JSTL tags directly).

* Use of an <action> element with a "forward" attribute, forwarding
  to a JSF page.

* Use of a custom RequestProcessor subclass.  The Struts-Faces integration
  library provides its own custom subclasses
  (org.apache.struts.faces.application.FacesRequestProcessor or
  org.apache.struts.faces.application.FacesTilesRequestProcessor), which must
  be used (or subclassed) for the integration to operate successfuly.

* The JavaServer Faces specification describes several restrictions on
  combining JSF component tags and other JSP source elements (including
  custom tags from other tag libraries) on the same page.  See
  Section 9.2.8 of the JSF spec for more information.

