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
 * <code>org.apache.strutsel.taglib.html.ELFileTag</code> class.  It is needed
 * to override the default mapping of custom tag attribute names to class
 * attribute names. <p> This is because the value of the unevaluated EL
 * expression has to be kept separately from the evaluated value, which is
 * stored in the base class. This is related to the fact that the JSP compiler
 * can choose to reuse different tag instances if they received the same
 * original attribute values, and the JSP compiler can choose to not re-call
 * the setter methods, because it can assume the same values are already set.
 */
public class ELFileTagBeanInfo extends SimpleBeanInfo {
    public PropertyDescriptor[] getPropertyDescriptors() {
        ArrayList proplist = new ArrayList();

        try {
            proplist.add(new PropertyDescriptor("accesskey", ELFileTag.class,
                    null, "setAccesskeyExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("accept", ELFileTag.class,
                    null, "setAcceptExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("alt", ELFileTag.class, null,
                    "setAltExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("altKey", ELFileTag.class,
                    null, "setAltKeyExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("bundle", ELFileTag.class,
                    null, "setBundleExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("dir", ELFileTag.class,
                    null, "setDirExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("disabled", ELFileTag.class,
                    null, "setDisabledExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("errorKey", ELFileTag.class,
                    null, "setErrorKeyExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("errorStyle", ELFileTag.class,
                    null, "setErrorStyleExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("errorStyleClass",
                    ELFileTag.class, null, "setErrorStyleClassExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("errorStyleId",
                    ELFileTag.class, null, "setErrorStyleIdExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("indexed", ELFileTag.class,
                    null, "setIndexedExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("lang", ELFileTag.class,
                    null, "setLangExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("maxlength", ELFileTag.class,
                    null, "setMaxlengthExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("name", ELFileTag.class, null,
                    "setNameExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onblur", ELFileTag.class,
                    null, "setOnblurExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onchange", ELFileTag.class,
                    null, "setOnchangeExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onclick", ELFileTag.class,
                    null, "setOnclickExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("ondblclick", ELFileTag.class,
                    null, "setOndblclickExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onfocus", ELFileTag.class,
                    null, "setOnfocusExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onkeydown", ELFileTag.class,
                    null, "setOnkeydownExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onkeypress", ELFileTag.class,
                    null, "setOnkeypressExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onkeyup", ELFileTag.class,
                    null, "setOnkeyupExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onmousedown", ELFileTag.class,
                    null, "setOnmousedownExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onmousemove", ELFileTag.class,
                    null, "setOnmousemoveExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onmouseout", ELFileTag.class,
                    null, "setOnmouseoutExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onmouseover", ELFileTag.class,
                    null, "setOnmouseoverExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("onmouseup", ELFileTag.class,
                    null, "setOnmouseupExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("property", ELFileTag.class,
                    null, "setPropertyExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("size", ELFileTag.class, null,
                    "setSizeExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("style", ELFileTag.class, null,
                    "setStyleExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("styleClass", ELFileTag.class,
                    null, "setStyleClassExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("styleId", ELFileTag.class,
                    null, "setStyleIdExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("tabindex", ELFileTag.class,
                    null, "setTabindexExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("title", ELFileTag.class, null,
                    "setTitleExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("titleKey", ELFileTag.class,
                    null, "setTitleKeyExpr"));
        } catch (IntrospectionException ex) {
        }

        try {
            proplist.add(new PropertyDescriptor("value", ELFileTag.class, null,
                    "setValueExpr"));
        } catch (IntrospectionException ex) {
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];

        return ((PropertyDescriptor[]) proplist.toArray(result));
    }
}
