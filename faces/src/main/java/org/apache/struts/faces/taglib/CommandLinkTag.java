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

package org.apache.struts.faces.taglib;


import org.apache.struts.faces.util.Utils;

import jakarta.el.MethodExpression;
import jakarta.el.ValueExpression;
import jakarta.faces.component.UIComponent;


/**
 * <p>Render a <code>CommandLinkComponent</code> inside a
 * Struts-Faces <code>FormComponent</code>.</p>
 *
 * @version $Rev$ $Date$
 */

public class CommandLinkTag extends AbstractFacesTag {


    // ------------------------------------------------------ Instance Variables


    private ValueExpression _accesskey;
    private MethodExpression _action;
    private MethodExpression _actionListener;
    private ValueExpression _charset;
    private ValueExpression _dir;
    private ValueExpression _hreflang;
    private ValueExpression _immediate;
    private ValueExpression _lang;
    private ValueExpression _onblur;
    private ValueExpression _onclick;
    private ValueExpression _ondblclick;
    private ValueExpression _onfocus;
    private ValueExpression _onkeydown;
    private ValueExpression _onkeypress;
    private ValueExpression _onkeyup;
    private ValueExpression _onmousedown;
    private ValueExpression _onmousemove;
    private ValueExpression _onmouseout;
    private ValueExpression _onmouseover;
    private ValueExpression _onmouseup;
    private ValueExpression _rel;
    private ValueExpression _rev;
    private ValueExpression _style;
    private ValueExpression _styleClass;
    private ValueExpression _tabindex;
    private ValueExpression _target;
    private ValueExpression _title;
    private ValueExpression _type;


    // ---------------------------------------------------------- Tag Attributes


    public void setAccesskey(ValueExpression accesskey) {
        this._accesskey = accesskey;
    }


    public void setAction(MethodExpression action) {
        this._action = action;
    }


    public void setActionListener(MethodExpression actionListener) {
        this._actionListener = actionListener;
    }


    public void setCharset(ValueExpression charset) {
        this._charset = charset;
    }


    public void setDir(ValueExpression dir) {
        this._dir = dir;
    }


    public void setHreflang(ValueExpression hreflang) {
        this._hreflang = hreflang;
    }


    public void setImmediate(ValueExpression immediate) {
        this._immediate = immediate;
    }


    public void setLang(ValueExpression lang) {
        this._lang = lang;
    }


    public void setOnblur(ValueExpression onblur) {
        this._onblur = onblur;
    }


    public void setOnclick(ValueExpression onclick) {
        this._onclick = onclick;
    }


    public void setOndblclick(ValueExpression ondblclick) {
        this._ondblclick = ondblclick;
    }


    public void setOnfocus(ValueExpression onfocus) {
        this._onfocus = onfocus;
    }


    public void setOnkeydown(ValueExpression onkeydown) {
        this._onkeydown = onkeydown;
    }


    public void setOnkeypress(ValueExpression onkeypress) {
        this._onkeypress = onkeypress;
    }


    public void setOnkeyup(ValueExpression onkeyup) {
        this._onkeyup = onkeyup;
    }


    public void setOnmousedown(ValueExpression onmousedown) {
        this._onmousedown = onmousedown;
    }


    public void setOnmousemove(ValueExpression onmousemove) {
        this._onmousemove = onmousemove;
    }


    public void setOnmouseout(ValueExpression onmouseout) {
        this._onmouseout = onmouseout;
    }


    public void setOnmouseover(ValueExpression onmouseover) {
        this._onmouseover = onmouseover;
    }


    public void setOnmouseup(ValueExpression onmouseup) {
        this._onmouseup = onmouseup;
    }


    public void setRel(ValueExpression rel) {
        this._rel = rel;
    }


    public void setRev(ValueExpression rev) {
        this._rev = rev;
    }


    public void setStyle(ValueExpression style) {
        this._style = style;
    }


    public void setStyleClass(ValueExpression styleClass) {
        this._styleClass = styleClass;
    }


    public void setTabindex(ValueExpression tabindex) {
        this._tabindex = tabindex;
    }


    public void setTarget(ValueExpression target) {
        this._target = target;
    }


    public void setTitle(ValueExpression title) {
        this._title = title;
    }


    public void setType(ValueExpression type) {
        this._type = type;
    }


    // ------------------------------------------------------------- Tag Methods


    /**
     * <p>Release any allocated resources.</p>
     */
    public void release() {

        super.release();
        _accesskey = null;
        _action = null;
        _actionListener = null;
        _charset = null;
        _dir = null;
        _hreflang = null;
        _immediate = null;
        _lang = null;
        _onblur = null;
        _onclick = null;
        _ondblclick = null;
        _onfocus = null;
        _onkeydown = null;
        _onkeypress = null;
        _onkeyup = null;
        _onmousedown = null;
        _onmousemove = null;
        _onmouseout = null;
        _onmouseover = null;
        _onmouseup = null;
        _rel = null;
        _rev = null;
        _style = null;
        _styleClass = null;
        _tabindex = null;
        _target = null;
        _title = null;
        _type = null;

    }


    // ---------------------------------------------------------- Public Methods


    /**
     * <p>Return the type of component to be created for this tag.</p>
     */
    public String getComponentType() {

        return ("org.apache.struts.faces.CommandLink");

    }


    /**
     * <p>Return the <code>rendererType</code> to be used for rendering
     * our component.</p>
     */
    public String getRendererType() {

        return ("org.apache.struts.faces.CommandLink");

    }


    // ------------------------------------------------------- Protected Methods


    /**
     * <p>Override attributes set on this tag instance.</p>
     *
     * @param component Component whose attributes should be overridden
     */
    protected void setProperties(UIComponent component) {

        super.setProperties(component);

        Utils.setActionProperty(component, _action);
        Utils.setActionListenerProperty(component, _actionListener);

        Utils.setStringProperty(component, "accesskey", _accesskey);
        Utils.setStringProperty(component, "charset", _charset);
        Utils.setStringProperty(component, "dir", _dir);
        Utils.setStringProperty(component, "hreflang", _hreflang);
        Utils.setBooleanProperty(component, "immediate", _immediate);
        Utils.setStringProperty(component, "lang", _lang);
        Utils.setStringProperty(component, "onblur", _onblur);
        Utils.setStringProperty(component, "onclick", _onclick);
        Utils.setStringProperty(component, "ondblclick", _ondblclick);
        Utils.setStringProperty(component, "onfocus", _onfocus);
        Utils.setStringProperty(component, "onkeydown", _onkeydown);
        Utils.setStringProperty(component, "onkeypress", _onkeypress);
        Utils.setStringProperty(component, "onkeyup", _onkeyup);
        Utils.setStringProperty(component, "onmousedown", _onmousedown);
        Utils.setStringProperty(component, "onmousemove", _onmousemove);
        Utils.setStringProperty(component, "onmouseout", _onmouseout);
        Utils.setStringProperty(component, "onmouseover", _onmouseover);
        Utils.setStringProperty(component, "onmouseup", _onmouseup);
        Utils.setStringProperty(component, "rel", _rel);
        Utils.setStringProperty(component, "rev", _rev);
        Utils.setStringProperty(component, "style", _style);
        Utils.setStringProperty(component, "styleClass", _styleClass);
        Utils.setStringProperty(component, "tabindex", _tabindex);
        Utils.setStringProperty(component, "target", _target);
        Utils.setStringProperty(component, "title", _title);
        Utils.setStringProperty(component, "type", _type);

    }
}