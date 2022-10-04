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
package org.apache.strutsel.taglib.tiles;

import org.apache.struts.tiles.taglib.PutTag;

/**
 * <p>Put an attribute in enclosing attribute container tag. Enclosing
 * attribute container tag can be : &lt;insert&gt; or &lt;definition&gt;.
 * Exception is thrown if no appropriate tag can be found. Put tag can have
 * following atributes :</p>
 *
 * <ul>
 *
 * <li>name : Name of the attribute</li>
 *
 * <li>value | content : value to put as attribute</li>
 *
 * <li>type : value type. Only valid if value is a String and is set by
 * value="something" or by a bean. Possible type are : string (value is used
 * as direct string), page | template (value is used as a page url to insert),
 * definition (value is used as a definition name to insert)</li>
 *
 * <li>direct : Specify if value is to be used as a direct string or as a page
 * url to insert. This is another way to specify the type. It only apply if
 * value is set as a string, and type is not present.</li>
 *
 * <li>beanName : Name of a bean used for setting value. Only valid if value
 * is not set. If property is specified, value come from bean's property.
 * Otherwise, bean itself is used for value.</li>
 *
 * <li>beanProperty : Name of the property used for retrieving value.</li>
 *
 * <li>beanScope : Scope containing bean. </li>
 *
 * <li>role : Role to check when 'insert' will be called. If enclosing tag is
 * &lt;insert&gt;, role is checked immediately. If enclosing tag is
 * &lt;definition&gt;, role will be checked when this definition will be
 * inserted.</li>
 *
 * </li> Value can also come from tag body. Tag body is taken into account
 * only if value is not set by one of the tag attributes. In this case
 * Attribute type is "string", unless tag body define another type.</li>
 *
 * </ul>
 *
 * <p> This class is a subclass of the class <code>org.apache.struts.taglib.tiles.PutTag</code>
 * which provides most of the described functionality.  This subclass allows
 * all attribute values to be specified as expressions utilizing the
 * JavaServer Pages Standard Library expression language.  </p>
 *
 * @version $Rev$
 */
public class ELPutTag extends PutTag {
    private static final long serialVersionUID = -4202066798764529556L;
}