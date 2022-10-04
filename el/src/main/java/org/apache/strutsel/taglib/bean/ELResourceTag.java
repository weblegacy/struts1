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
package org.apache.strutsel.taglib.bean;

import org.apache.struts.taglib.bean.ResourceTag;

/**
 * Define a scripting variable based on the contents of the specified web
 * application resource. <p> This class is a subclass of the class
 * <code>org.apache.struts.taglib.bean.ResourceTag</code> which provides most
 * of the described functionality.  This subclass allows all attribute values
 * to be specified as expressions utilizing the JavaServer Pages Standard
 * Library expression language.
 *
 * @version $Rev$
 */
public class ELResourceTag extends ResourceTag {
    private static final long serialVersionUID = 5748871138055936783L;
}