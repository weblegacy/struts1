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

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

import java.util.ArrayList;

/**
 * This is the <code>BeanInfo</code> descriptor for the
 * <code>org.apache.strutsel.taglib.html.ELSelectTag</code> class.  It is
 * needed to override the default mapping of custom tag attribute names to
 * class attribute names. <p> This is because the value of the unevaluated EL
 * expression has to be kept separately from the evaluated value, which is
 * stored in the base class. This is related to the fact that the JSP compiler
 * can choose to reuse different tag instances if they received the same
 * original attribute values, and the JSP compiler can choose to not re-call
 * the setter methods, because it can assume the same values are already set.
 */
public class ELSelectTagBeanInfo extends SimpleBeanInfo {
    public PropertyDescriptor[] getPropertyDescriptors() {
        ArrayList proplist = new ArrayList();

        try {
            proplist.add(new PropertyDescriptor("alt", ELSelectTag.class, null,
                    "setAltExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("altKey", ELSelectTag.class,
                    null, "setAltKeyExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("bundle", ELSelectTag.class,
                    null, "setBundleExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("dir", ELSelectTag.class,
                    null, "setDirExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("disabled", ELSelectTag.class,
                    null, "setDisabledExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("errorKey", ELSelectTag.class,
                    null, "setErrorKeyExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("errorStyle",
                    ELSelectTag.class, null, "setErrorStyleExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("errorStyleClass",
                    ELSelectTag.class, null, "setErrorStyleClassExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("errorStyleId",
                    ELSelectTag.class, null, "setErrorStyleIdExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("indexed", ELSelectTag.class,
                    null, "setIndexedExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("lang", ELSelectTag.class,
                    null, "setLangExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("multiple", ELSelectTag.class,
                    null, "setMultipleExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("name", ELSelectTag.class,
                    null, "setNameExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onblur", ELSelectTag.class,
                    null, "setOnblurExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onchange", ELSelectTag.class,
                    null, "setOnchangeExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onclick", ELSelectTag.class,
                    null, "setOnclickExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("ondblclick",
                    ELSelectTag.class, null, "setOndblclickExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onfocus", ELSelectTag.class,
                    null, "setOnfocusExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onkeydown", ELSelectTag.class,
                    null, "setOnkeydownExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onkeypress",
                    ELSelectTag.class, null, "setOnkeypressExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onkeyup", ELSelectTag.class,
                    null, "setOnkeyupExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onmousedown",
                    ELSelectTag.class, null, "setOnmousedownExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onmousemove",
                    ELSelectTag.class, null, "setOnmousemoveExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onmouseout",
                    ELSelectTag.class, null, "setOnmouseoutExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onmouseover",
                    ELSelectTag.class, null, "setOnmouseoverExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onmouseup", ELSelectTag.class,
                    null, "setOnmouseupExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("property", ELSelectTag.class,
                    null, "setPropertyExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("size", ELSelectTag.class,
                    null, "setSizeExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("style", ELSelectTag.class,
                    null, "setStyleExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("styleClass",
                    ELSelectTag.class, null, "setStyleClassExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("styleId", ELSelectTag.class,
                    null, "setStyleIdExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("tabindex", ELSelectTag.class,
                    null, "setTabindexExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("title", ELSelectTag.class,
                    null, "setTitleExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("titleKey", ELSelectTag.class,
                    null, "setTitleKeyExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("value", ELSelectTag.class,
                    null, "setValueExpr"));
        } catch (IntrospectionException ex) {
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];

        return ((PropertyDescriptor[]) proplist.toArray(result));
    }
}
