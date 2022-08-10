/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * Nested tags &amp; supporting classes extend the base struts tags to
 * allow them to relate to each other in a nested nature. The fundamental
 * logic of the original tags don't change, except in that all references
 * to beans and bean properties will be managed in a nested context. <br>
 * <br>
 * <a id="doc.Description"></a>
 *
 * <div align="Center"> [&nbsp;<a href="#doc.Intro">Introduction</a>
 *     &nbsp;] [&nbsp;<a href="#doc.FoundationConcepts">Foundation&nbsp;Concepts&nbsp;-&nbsp;model</a>
 *     &nbsp;] [&nbsp;<a href="#doc.TaggingConcepts">Foundation&nbsp;Concepts&nbsp;-&nbsp;tags</a>
 *     &nbsp;] [&nbsp;<a href="#doc.TagList">Nested&nbsp;Tag&nbsp;List</a>
 *     &nbsp;] [&nbsp;<a href="#doc.PropertyProperty">The&nbsp;"property"&nbsp;Property</a>
 *     &nbsp;] [&nbsp;<a href="#doc.ImplementationDetails">Implementation&nbsp;Details</a>
 *     &nbsp;]</div>
 * <hr><a id="doc.Intro"></a>
 *
 * <h3>Introduction</h3>
 *
 * <p>The nesting extension provides the ability to define a nested object model
 *     and efficiently represent and manage that model through JSP's custom
 *     tags.</p>
 *
 * <p>It's written in a layer that extends the current Struts tags, building
 *     on their logic and functionality. The layer enables the tags to be aware
 *     of the tags which surround them so they can correctly provide the nesting
 *     property reference to the Struts system. Struts already supported
 *     properties
 *     which use "dot notation" in accessing nested objects and properties.</p>
 * <pre>e.g. myProperty.childProperty.finalProperty</pre>
 *
 * <p>Because of this the controller servlet excellently manages this nested
 *     model. These tags are about bringing such ease of management to the JSP
 *     view of the architecture.</p>
 * <hr><a id="doc.FoundationConcepts"></a>
 *
 * <h3>Foundation Concepts - model.</h3>
 *
 * <p> A bean holds a reference to another bean internally, and all access
 *     to that bean is handled through the current bean. This act of having
 *     one bean's access go through another bean is known as "nesting beans".
 *     The first bean is known as the parent bean. The bean which it references,
 *     is known as a child bean. The terms "parent" and "child" are commonly
 *     used to describe the model's hierarchy.</p>
 *
 * <p><b>A simple example...</b><br/>
 *     Take an object which represents a monkey. The monkey's job is to pick
 *     bunches of bananas. On each bunch picked hangs many bananas. If this
 *     case was translated to bean objects, the monkey object would have a
 *     reference
 *     to the bunch objects he picked, and each bunch object would hold a
 *     reference
 *     to the bananas hanging in the bunch.</p>
 *
 * <p><b>To describe this...</b><br/>
 *     The monkey object is the parent to the bunch object, and the bunch
 *     object is a child of the monkey object. The bunch object is parent to
 *     its child banana objects, and the child banana objects children of the
 *     bunch object. The monkey is higher in the hierarchy than the bananas,
 *     and the bananas lower in the hierarchy to the bunches.</p>
 *
 * <p> One special term to remember is for the most parent class, which is
 *     known as the "root" object which starts the hierarchy.</p>
 * <hr><a id="doc.TaggingConcepts"></a>
 *
 * <h3>Foundation Concepts - tags.</h3>
 *
 * <p> What the tags provide is an efficient way or representing the above
 *     models within JSP tag markup. As a result the tags take on similar
 *     relationships
 *     to each other. A tag can be the parent of another, and similarly be a
 *     child of a parent tag. However the most important part to remember, is
 *     that the properties of parent tags define the nested property for the
 *     child tags' properties.</p>
 *
 * <p> One issue which may confuse the new developer, is that even though
 *     a tag is a parent tag in a markup sense (the opening tag and closing tag
 *     are either side of another tag) does not immediately mean that the child
 *     tag will be relative to that tag. Why? Some tags make bad parents. In
 *     other
 *     words, they're not logical steps in defining a hierarchy.</p>
 *
 * <p> For example the relationship between the select tag and the options
 *     tag. The html:options tag "must" be surrounded by a parent html:select
 *     tag.</p>
 * <pre>eg:<br> &lt;html:select name="myBean" property="mySelectProperty" &gt;
 *    <br> &lt;html:options name="myBean" property="myOptionsProperty" &gt;<br>
 *    &lt;/html:select&gt;</pre>
 *
 * <p> In the nested context, this would cause undesired results if the select
 *     tag was a parent. The bean reference would become...</p>
 * <pre> mySelectProperty.myOptionsProperty</pre>
 * ...which when translated, Struts would go to the value of the select
 * property and then try to get your options list from that returned value.
 * The extended logic tags are the same. You don't want to extend your properties
 * within the objects the logic tags are evaluating.
 * <p> To get manage this, the tags in the nested extension are categorised
 *     into parent tags and non-parent tags. Those which implement <code>
 *     org.apache.struts.taglib.nested.NestedParentSupport</code>
 *     are classed as parents, and the nested system knows they define levels
 *     in the nested hierarchy. Every other tag, does not, and will be skipped
 *     if found to be a "markup parent" (like our select tag) and not a "nested
 *     parent".</p>
 *
 * <p> There are also the special case of starting off the hierarchy with
 *     a "root" tag. These tags are what the extension requires to provide them
 *     with the bean by which the structure will be based on.</p>
 * <hr><a id="doc.TagList"></a>
 *
 * <h3>Nested Tag List.</h3>
 *
 * <p> Here's a list of tags in the nested extension, grouped by parent/context
 *     functionality. "root", "nested parent", "markup parent" &amp; "basic".
 * </p>
 *
 * <p><b>Root Tags</b></p>
 * <table style="border:1px solid black">
 *     <tbody>
 *         <tr>
 *             <th style="border:1px solid black">
 *                 markup name
 *             </th>
 *             <th style="border:1px solid black">
 *                 brief description
 *             </th>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 html:form
 *             </td>
 *             <td style="border:1px solid black">
 *                 For backwards compatibility, you can use the typical form
 *                 tag
 *                 to implement your nested hierarchy.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:form
 *             </td>
 *             <td style="border:1px solid black">
 *                 An extension of the above <code>html:form</code>, this is
 *                 just
 *                 to provide definition in the nested tag library.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:root
 *             </td>
 *             <td style="border:1px solid black">
 *                 When you don't want to configure a form, you can use any
 *                 bean
 *                 which is in "scope" by specifying its name within this
 *                 tag.
 *             </td>
 *         </tr>
 *     </tbody>
 * </table>
 * <p></p>
 * <br/>
 *
 * <p><b>Nested Parent Tags</b> (Affect the hierarchy)</p>
 * <table style="border:1px solid black">
 *     <tbody>
 *         <tr>
 *             <th style="border:1px solid black">
 *                 markup name
 *             </th>
 *             <th style="border:1px solid black">
 *                 brief description
 *             </th>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:nest
 *             </td>
 *             <td style="border:1px solid black">
 *                 This tag executes no logic, simply representing a nesting
 *                 level
 *                 for the rest of the markup to relate to.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:iterate
 *             </td>
 *             <td style="border:1px solid black">
 *                 Extension of <code>logic:iterate</code> you can use it to
 *                 iterate
 *                 through a list, and have all child references nest within
 *                 the
 *                 beans returned from this iterated collection.
 *             </td>
 *         </tr>
 *     </tbody>
 * </table>
 * <p></p>
 * <br/>
 *
 * <p><b>Markup Parent Tags</b> (marked-up like parent tags, but don't affect
 *     the hierarchy)</p>
 * <table style="border:1px solid black">
 *     <tbody>
 *         <tr>
 *             <th style="border:1px solid black">
 *                 markup name
 *             </th>
 *             <th style="border:1px solid black">
 *                 brief description
 *             </th>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:select
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>html:select</code> extension. Provides the logic to
 *                 render
 *                 a select box in Html.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:empty
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>logic:empty</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:notEmpty
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>logic:notEmpty</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:equal
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>logic:equal</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:notEqual
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>logic:notEqual</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:greaterEqual
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>logic:greaterEqual</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:greaterThan
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>logic:greaterThan</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:lessEqual
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>logic:lessEqual</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:lessThan
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>logic:lessThan</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:match
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>logic:match</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:notMatch
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>logic:notMatch</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:present
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>logic:present</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:notPresent
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>logic:notPresent</code> extension.
 *             </td>
 *         </tr>
 *     </tbody>
 * </table>
 * <p></p>
 * <br/>
 *
 * <p><b>Basic tags</b> (usually a tag which has no body content)</p>
 * <table style="border:1px solid black">
 *     <tbody>
 *         <tr>
 *             <th style="border:1px solid black">
 *                 markup name
 *             </th>
 *             <th style="border:1px solid black">
 *                 brief description
 *             </th>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:checkbox
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>html:</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:hidden
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>html:hidden</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:define
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>bean:define</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:image
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>html:image</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:img
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>html:img</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:link
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>html:link</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:message
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>bean:message</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:multibox
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>html:multibox</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:options
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>html:options</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:optionsCollection
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>html:optionsCollection</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:password
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>html:password</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:radio
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>html:radio</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:select
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>html:select</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:size
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>bean:size</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:submit
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>html:submit</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:text
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>html:text</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:textarea
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>html:textarea</code> extension.
 *             </td>
 *         </tr>
 *         <tr>
 *             <td style="border:1px solid black">
 *                 nested:write
 *             </td>
 *             <td style="border:1px solid black">
 *                 <code>bean:write</code> extension.
 *             </td>
 *         </tr>
 *     </tbody>
 * </table>
 * <p></p>
 * <hr><a id="doc.PropertyProperty"></a>
 *
 * <h3>The relative references and the "property" property.</h3>
 *
 * <p>Use of the "property" property is exactly the same as the original Struts
 *     tag with minor addition. Appends the provided property to the nested
 *     property of the tags that surround it. You can use additional nesting
 *     (use "dot notation") within the provided property as the current struts
 *     system allows but there is now a tag which can provide this in a "cleaner"
 *     fashion. :)</p>
 *
 * <p>The one other addition to the "property" property, is the ability to step
 *     backwards in the heirarchy in the familiar directory fashion; e.g.
 *     "../../myPropertyName"</p>
 *
 * <p>As expected this allows you to step backwards in the nested model to access
 *     a higher level in the object tree. The implementation uses the
 *     StringTokenizer
 *     working off the "/" delimiter and counts the tokens. This was going to
 *     be denied, enforcing the ".." fashion, but on consideration, allowed
 *     for some easier-to-read naming possibilities.</p>
 *
 * <p>Consider "propertyOne.propertyTwo.propertyThree.propertyFour". With the
 *     current nesting level beneath "propertyFour" you can instead use
 *     "two/three/four/anotherProperty"
 *     which is easier to understand than "../../../anotherProperty". Doesn't
 *     sound like much, but makes life easier when traversing large jsp pages
 *     for tags defining your object model.</p>
 *
 * <p>Also implemented is the also familiar directory fashion of a leading "/"
 *     to reference from the root of the model and start over. e.g.
 *     "/propertyOne".
 *     This allow a convenient way to move around a few levels as well as
 *     "forking"
 *     in the object structure among other felixble approaches to structure.</p>
 *
 * <p><b>Parent References...</b><br/>
 *     "property" properties, including the relative properties described
 *     above, all end up referencing a property of a child bean. For example
 *     "/myProperty" will return an object from the "myProperty" of the root
 *     bean.
 *     The fact that a property is specified means that you are accessing the
 *     result of that property. This results in never being able to properly
 *     access
 *     a parent object itself within its current related context.</p>
 *
 * <p> Take for example you simply want to print out a list of String objects.
 *     In a bean you create a list of them, offer them out to the system via
 *     a getter, and you markup using the nested:iterate or logic:iterate tag
 *     (both contain the same issues). The only way to get at the object itself
 *     is get the iterate tag to declare a scripting variable. With the nested
 *     tags you can now simply reference the object using a parent reference of
 *     "./" or "this/". Any property ending in the "/" will be treaded as a
 *     parent
 *     reference. So if you use "parent/" as your property, it will step back
 *     one parent and use this block's parent. The special cases to use the
 *     parent
 *     of the current nested block are "./" or "this/". Not just the iterate tag,
 *     this will return the object represented by any nested parent tag.</p>
 *
 * <p> This allows you to be in a nested tag block and use the custom tags
 *     work directly against the parent defined object, indexed or otherwise.
 *     So to be in an iterate block, and to print out the String representation
 *     of the current iterated object, you can now use...</p>
 * <pre>eg:<br> &lt;nested:iterate property="myItemList" &gt;<br> &lt;html:write
 *    property="this/" &gt;&lt;br&gt;<br> &lt;/html:iterate&gt;</pre>
 *
 * <p> or if you want to print the string value of a parent the other side
 *     of the object...</p>
 * <pre>eg:<br> &lt;nested:iterate property="myBeanList" &gt;<br> &lt;nested:iterate
 *    property="myItemList" &gt;<br> &lt;html:write property="beanListObject/"
 *    &gt;&lt;br&gt;<br> &lt;/html:iterate&gt;<br> &lt;/html:iterate&gt;</pre>
 *
 * <p> The fact that it didn't use the special cases of "./" or "this/" means
 *     that it steps back in the hierarchy as a typical relative reference.
 *     This is unlimited the amount of steps you can take back in the hierarchy.
 *     For example, to go back three parents your property would be
 *     "one/two/three/".
 * </p>
 *
 * <p><b>Note:</b> The logic identifies the leading "/" and then reads the
 *     property
 *     from the last index of "/". Resulting in "/three/four/anotherProperty"
 *     working the same as "/anotherProperty".</p>
 *
 * <p><b>Note:</b> If you're busily nesting away, and a parent tag has a leading
 *     "/" property, the contained tags will append to this new structure.
 *     Handy, but you have to keep it in mind.</p>
 *
 * <p><b>Note:</b> If you try to reference beneath the level of the nesting,
 *     it will simply act like as if a leading "/" property was defined.</p>
 *
 * <p><b>Parent Reference Note:</b> The only thing to keep in mind with parent
 *     references is that you cannot parent reference the root bean. This is
 *     because the resulting property would be an empty string. Something that
 *     the BeanUtils/PropertyUtils cannot handle (if this is a requirement, you
 *     could use a "fake nested property". A getter which simply returns the same
 *     bean instance ("this") and simply add an extra <code>nested:nest</code>
 *     level at the start of your hierarchy. Works just fine).</p>
 * <hr><br/>
 * <img src="doc-files/nestedUML.png" alt="nested UML">
 * <a id="doc.ImplementationDetails"></a>
 *
 * <h3>Implementation Details.</h3>
 * <b>NestedPropertyHelper</b>
 * <ul>
 *     <li>This class embodies all of the logic that runs the nested tagging
 *         system.
 *         It defines a static method, "setNestedProperties", which the nested
 *         tags
 *         pass themselves into to have their appropriate properties set.</li>
 *
 *     <li>The tag extensions themselves implement options of three interfaces
 *         which
 *         define functionality for the various types of nested usage. When
 *         traversing
 *         the tag hierarchy back up to the root of the structure, these tags
 *         define
 *         the result of the current tag.</li>
 * </ul>
 * <b>NestedTagSupport Interface...</b>
 * <ul>
 *     <li>This is the base of the interfaces. Simply put, any tag that we need to
 *         single out of the standard struts tags for use by the nesting system
 *         can implement this or its children.</li>
 * </ul>
 * <b>NestedPropertySupport Interface...</b>
 * <ul>
 *     <li>Tags that implement this interface will have the provided property
 *         attribute
 *         appended to the parenting nested attribute. This is the heart of the
 *         matter.
 *     </li>
 *
 *     <li>This is the basic property, and so far all the nested tags support this
 *         to have their nested properties written correctly.</li>
 * </ul>
 * <b>NestedNameSupport Interface...</b>
 * <ul>
 *     <li>This interface means that the implementing tag wants to have it's name
 *         tag looked after by the nesting system. This is automatic, and the
 *         name
 *         is written for the tag from the root tag. If the JSP is a form, then
 *         it will look to the form tag and get a hold of the bean name that is
 *         defined in the struts-config.xml file for the action, otherwise, a
 *         nested:root
 *         tag must be provided for this means.</li>
 *
 *     <li>This extends the NestedPropertySupport interface as, at time of
 *         writing,
 *         all tags which used a "name" attribute, required a property attribute
 *         in some way to make it useful. This could change, and it's only a
 *         small
 *         refactoring to make it work for the instance if it's relevant for
 *         nesting.</li>
 *
 *     <li><b>Note:</b> At the moment, if the tag implements this interface, the
 *         name attribute will be rewritten by the system on all counts. I find
 *         it
 *         hard to picture a valid requirement for inter-mixing multiple object
 *         structures
 *         (which distinguishable names would allow) in the one JSP page which
 *         couldn't
 *         be more efficiently provided by the current nesting model working over
 *         the one model. Time may prove this idea wrong.</li>
 * </ul>
 * <b>ParentTagSupport</b>
 * <ul>
 *     <li>This tag identifies for the system those tags which define levels in
 *         the
 *         nested hierarchy. Namely the "getNestedProperty()" method that yields
 *         to calling tags the fully qualified nested property of the parent tag.
 *         In the case of a NestedIterator being the parent tag, it will also
 *         append
 *         the current index reference. e.g. propertyOne.propertyTwo[5]</li>
 * </ul>
 */
package org.apache.struts.taglib.nested;