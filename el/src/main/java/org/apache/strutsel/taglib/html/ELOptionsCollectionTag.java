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
package org.apache.strutsel.taglib.html;

import org.apache.struts.taglib.html.OptionsCollectionTag;

/**
 * Tag for creating multiple &lt;select&gt; options from a collection. The
 * collection may be part of the enclosing form, or may be independent of the
 * form. Each element of the collection must expose a 'label' and a 'value',
 * the property names of which are configurable by attributes of this tag. <p>
 * The collection may be an array of objects, a Collection, an Enumeration, an
 * Iterator, or a Map. <p> <b>NOTE</b> - This tag requires a Java2 (JDK 1.2 or
 * later) platform. <p> This class is a subclass of the class
 * <code>org.apache.struts.taglib.html.OptionsCollectionTag</code> which
 * provides most of the described functionality.  This subclass allows all
 * attribute values to be specified as expressions utilizing the JavaServer
 * Pages Standard Library expression language.
 *
 * @version $Rev$
 */
public class ELOptionsCollectionTag extends OptionsCollectionTag {
    private static final long serialVersionUID = 7020162905128599366L;
}