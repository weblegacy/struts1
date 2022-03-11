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


import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;


/**
 * <p>Render a <code>CommandLinkComponent</code> inside a
 * Struts-Faces <code>FormComponent</code>.</p>
 *
 * @version $Rev$ $Date$
 */

public class CommandLinkTag extends AbstractFacesTag {


    // ------------------------------------------------------ Instance Variables


    private String accesskey = null;
    private String action = null;
    private String actionListener = null;
    private String charset = null;
    private String dir = null;
    private String hreflang = null;
    private String immediate = null;
    private String lang = null;
    private String onblur = null;
    private String onclick = null;
    private String ondblclick = null;
    private String onfocus = null;
    private String onkeydown = null;
    private String onkeypress = null;
    private String onkeyup = null;
    private String onmousedown = null;
    private String onmousemove = null;
    private String onmouseout = null;
    private String onmouseover = null;
    private String onmouseup = null;
    private String rel = null;
    private String rev = null;
    private String style = null;
    private String styleClass = null;
    private String tabindex = null;
    private String target = null;
    private String title = null;
    private String type = null;


    // ---------------------------------------------------------- Tag Attributes


    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }


    public void setAction(String action) {
        this.action = action;
    }


    public void setactionListener(String actionListener) {
        this.actionListener = actionListener;
    }


    public void setCharset(String charset) {
        this.charset = charset;
    }


    public void setDir(String dir) {
        this.dir = dir;
    }


    public void setHreflang(String hreflang) {
        this.hreflang = hreflang;
    }


    public void setImmediate(String immediate) {
        this.immediate = immediate;
    }


    public void setLang(String lang) {
        this.lang = lang;
    }


    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }


    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }


    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }


    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }


    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }


    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }


    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }


    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }


    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }


    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }


    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }


    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }


    public void setRel(String rel) {
        this.rel = rel;
    }


    public void setRev(String rev) {
        this.rev = rev;
    }


    public void setStyle(String style) {
        this.style = style;
    }


    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }


    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }


    public void setTarget(String target) {
        this.target = target;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setType(String type) {
        this.type = type;
    }


    // ------------------------------------------------------------- Tag Methods


    /**
     * <p>Release any allocated resources.</p>
     */
    public void release() {

        super.release();
        accesskey = null;
        action = null;
        actionListener = null;
        charset = null;
        dir = null;
        hreflang = null;
        immediate = null;
        lang = null;
        onblur = null;
        onclick = null;
        ondblclick = null;
        onfocus = null;
        onkeydown = null;
        onkeypress = null;
        onkeyup = null;
        onmousedown = null;
        onmousemove = null;
        onmouseout = null;
        onmouseover = null;
        onmouseup = null;
        rel = null;
        rev = null;
        style = null;
        styleClass = null;
        tabindex = null;
        target = null;
        title = null;
        type = null;

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
        if (action != null) {
            if (isValueReference(action)) {
                MethodBinding mb = FacesContext.getCurrentInstance().
                    getApplication().createMethodBinding(action, null);
                ((ActionSource) component).setAction(mb);
            } else {
                final String outcome = action;
                MethodBinding mb = new ConstantMethodBinding(outcome);
                ((ActionSource) component).setAction(mb);
            }
        }
        if (actionListener != null) {
            if (isValueReference(actionListener)) {
                Class[] args = {ActionEvent.class};
                MethodBinding mb = FacesContext.getCurrentInstance().
                getApplication().createMethodBinding(actionListener, args);
                ((ActionSource) component).setActionListener(mb);
            }
        }
        setStringAttribute(component, "accesskey", accesskey);
        setStringAttribute(component, "charset", charset);
        setStringAttribute(component, "dir", dir);
        setStringAttribute(component, "hreflang", hreflang);
        setBooleanAttribute(component, "immediate", immediate);
        setStringAttribute(component, "lang", lang);
        setStringAttribute(component, "onblur", onblur);
        setStringAttribute(component, "onclick", onclick);
        setStringAttribute(component, "ondblclick", ondblclick);
        setStringAttribute(component, "onfocus", onfocus);
        setStringAttribute(component, "onkeydown", onkeydown);
        setStringAttribute(component, "onkeypress", onkeypress);
        setStringAttribute(component, "onkeyup", onkeyup);
        setStringAttribute(component, "onmousedown", onmousedown);
        setStringAttribute(component, "onmousemove", onmousemove);
        setStringAttribute(component, "onmouseout", onmouseout);
        setStringAttribute(component, "onmouseover", onmouseover);
        setStringAttribute(component, "onmouseup", onmouseup);
        setStringAttribute(component, "rel", rel);
        setStringAttribute(component, "rev", rev);
        setStringAttribute(component, "style", style);
        setStringAttribute(component, "styleClass", styleClass);
        setStringAttribute(component, "tabindex", tabindex);
        setStringAttribute(component, "target", target);
        setStringAttribute(component, "title", title);
        setStringAttribute(component, "type", type);

    }


}


// Private MethodBinding Implementation To Return A Constant Value

class ConstantMethodBinding extends MethodBinding {

    public ConstantMethodBinding(String outcome) {
        this.outcome = outcome;
    }

    private String outcome = null;

    public Object invoke(FacesContext context, Object params[]) {
        return (this.outcome);
    }

    public Class getType(FacesContext context) {
        return (String.class);
    }


}
