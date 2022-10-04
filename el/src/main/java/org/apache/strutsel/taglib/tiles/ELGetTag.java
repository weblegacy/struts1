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

import org.apache.struts.tiles.taglib.GetTag;

/**
 * This is the tag handler for &lt;tiles-el:get&gt;, which gets content from
 * the request scope and either includes the content or prints it, depending
 * upon the value of the content's <code>direct</code> attribute. <p> This tag
 * is intended to be compatible with the same tag from Templates (David
 * Geary).  Implementation extends InsertTag for facility (no so well). The
 * only difference is the default value of attribute 'ignore', which is
 * <code>true</code> for this tag (default behavior of David Geary's
 * templates). <p> This class is a subclass of the class
 * <code>org.apache.struts.taglib.tiles.GetTag</code> which provides most of
 * the described functionality.  This subclass allows all attribute values to
 * be specified as expressions utilizing the JavaServer Pages Standard Library
 * expression language.
 *
 * @version $Rev$
 */
public class ELGetTag extends GetTag {
    private static final long serialVersionUID = -2048413008318042294L;
}