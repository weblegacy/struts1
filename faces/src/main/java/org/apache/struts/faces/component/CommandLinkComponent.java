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

package org.apache.struts.faces.component;


import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;


/**
 * <p>Custom component that emulates the JSF standard component class
 * <code>javax.faces.component.html.HtmlCommandLink</code> (and its
 * corresponding renderer) but is not tied to a particular implementation of
 * renderer for <code>javax.faces.component.UIForm</code>.</p>
 */

public class CommandLinkComponent extends UICommand {


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Create a new {@link CommandLinkComponent} with default properties.</p>
     */
    public CommandLinkComponent() {

        super();
        setRendererType("org.apache.struts.faces.CommandLink");

    }


    // ------------------------------------------------------ Instance Variables


    private String accesskey = null;
    private String charset = null;
    private String dir = null;
    private String hreflang = null;
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



    // ---------------------------------------------------- Component Properties


    public String getAccesskey() {
        ValueBinding vb = getValueBinding("accesskey");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return accesskey;
        }
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }


    public String getCharset() {
        ValueBinding vb = getValueBinding("charset");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return charset;
        }
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }


    public String getDir() {
        ValueBinding vb = getValueBinding("dir");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return dir;
        }
    }

    public void setDir(String dir) {
        this.dir = dir;
    }


    /**
     * <p>Return the component family to which this component belongs.</p>
     */
    public String getFamily() {

        return "org.apache.struts.faces.CommandLink";

    }


    public String getHreflang() {
        ValueBinding vb = getValueBinding("hreflang");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return hreflang;
        }
    }

    public void setHreflang(String hreflang) {
        this.hreflang = hreflang;
    }


    public String getLang() {
        ValueBinding vb = getValueBinding("lang");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return lang;
        }
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


    public String getOnblur() {
        ValueBinding vb = getValueBinding("onblur");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return onblur;
        }
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }


    public String getOnclick() {
        ValueBinding vb = getValueBinding("onclick");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return onclick;
        }
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }


    public String getOndblclick() {
        ValueBinding vb = getValueBinding("ondblclick");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return ondblclick;
        }
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }


    public String getOnfocus() {
        ValueBinding vb = getValueBinding("onfocus");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return onfocus;
        }
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }


    public String getOnkeydown() {
        ValueBinding vb = getValueBinding("onkeydown");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return onkeydown;
        }
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }


    public String getOnkeypress() {
        ValueBinding vb = getValueBinding("onkeypress");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return onkeypress;
        }
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }


    public String getOnkeyup() {
        ValueBinding vb = getValueBinding("onkeyup");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return onkeyup;
        }
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }


    public String getOnmousedown() {
        ValueBinding vb = getValueBinding("onmousedown");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return onmousedown;
        }
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }


    public String getOnmousemove() {
        ValueBinding vb = getValueBinding("onmousemove");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return onmousemove;
        }
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }


    public String getOnmouseout() {
        ValueBinding vb = getValueBinding("onmouseout");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return onmouseout;
        }
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }


    public String getOnmouseover() {
        ValueBinding vb = getValueBinding("onmouseover");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return onmouseover;
        }
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }


    public String getOnmouseup() {
        ValueBinding vb = getValueBinding("onmouseup");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return onmouseup;
        }
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }


    public String getRel() {
        ValueBinding vb = getValueBinding("rel");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return rel;
        }
    }

    public void setRel(String rel) {
        this.rel = rel;
    }


    public String getRev() {
        ValueBinding vb = getValueBinding("rev");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return rev;
        }
    }

    public void setRev(String rev) {
        this.rev = rev;
    }


    public String getStyle() {
        ValueBinding vb = getValueBinding("style");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return style;
        }
    }

    public void setStyle(String style) {
        this.style = style;
    }


    public String getStyleClass() {
        ValueBinding vb = getValueBinding("styleClass");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return styleClass;
        }
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }


    public String getTabindex() {
        ValueBinding vb = getValueBinding("tabindex");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return tabindex;
        }
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }


    public String getTarget() {
        ValueBinding vb = getValueBinding("target");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return target;
        }
    }

    public void setTarget(String target) {
        this.target = target;
    }


    public String getTitle() {
        ValueBinding vb = getValueBinding("title");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return title;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getType() {
        ValueBinding vb = getValueBinding("type");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext());
        } else {
            return type;
        }
    }

    public void setType(String type) {
        this.type = type;
    }


    // ---------------------------------------------------- StateManager Methods


    /**
     * <p>Restore the state of this component.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param state State object from which to restore our state
     */
    public void restoreState(FacesContext context, Object state) {

        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        accesskey = (String) values[1];
        charset = (String) values[2];
        dir = (String) values[3];
        hreflang = (String) values[4];
        lang = (String) values[5];
        onblur = (String) values[6];
        onclick = (String) values[7];
        ondblclick = (String) values[8];
        onfocus = (String) values[9];
        onkeydown = (String) values[10];
        onkeypress = (String) values[11];
        onkeyup = (String) values[12];
        onmousedown = (String) values[13];
        onmousemove = (String) values[14];
        onmouseout = (String) values[15];
        onmouseover = (String) values[16];
        onmouseup = (String) values[17];
        rel = (String) values[18];
        rev = (String) values[19];
        style = (String) values[20];
        styleClass = (String) values[21];
        tabindex = (String) values[22];
        target = (String) values[23];
        title = (String) values[24];
        type = (String) values[25];

    }


    /**
     * <p>Save the state of this component.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     */
    public Object saveState(FacesContext context) {

        Object values[] = new Object[26];
        values[0] = super.saveState(context);
        values[1] = accesskey;
        values[2] = charset;
        values[3] = dir;
        values[4] = hreflang;
        values[5] = lang;
        values[6] = onblur;
        values[7] = onclick;
        values[8] = ondblclick;
        values[9] = onfocus;
        values[10] = onkeydown;
        values[11] = onkeypress;
        values[12] = onkeyup;
        values[13] = onmousedown;
        values[14] = onmousemove;
        values[15] = onmouseout;
        values[16] = onmouseover;
        values[17] = onmouseup;
        values[18] = rel;
        values[19] = rev;
        values[20] = style;
        values[21] = styleClass;
        values[22] = tabindex;
        values[23] = target;
        values[24] = title;
        values[25] = type;
        return values;

    }


}
