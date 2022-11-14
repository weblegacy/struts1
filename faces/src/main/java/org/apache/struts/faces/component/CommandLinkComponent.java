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


import jakarta.el.ValueExpression;
import jakarta.faces.component.UICommand;
import jakarta.faces.context.FacesContext;


/**
 * Custom component that emulates the JSF standard component class
 * {@code jakarta.faces.component.html.HtmlCommandLink} (and its
 * corresponding renderer) but is not tied to a particular implementation of
 * renderer for {@code jakarta.faces.component.UIForm}.
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


    private String accesskey;
    private String charset;
    private String dir;
    private String hreflang;
    private String lang;
    private String onblur;
    private String onclick;
    private String ondblclick;
    private String onfocus;
    private String onkeydown;
    private String onkeypress;
    private String onkeyup;
    private String onmousedown;
    private String onmousemove;
    private String onmouseout;
    private String onmouseover;
    private String onmouseup;
    private String rel;
    private String rev;
    private String style;
    private String styleClass;
    private String tabindex;
    private String target;
    private String title;
    private String type;



    // ---------------------------------------------------- Component Properties


    public String getAccesskey() {
        ValueExpression vb = getValueExpression("accesskey");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return accesskey;
        }
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }


    public String getCharset() {
        ValueExpression vb = getValueExpression("charset");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return charset;
        }
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }


    public String getDir() {
        ValueExpression vb = getValueExpression("dir");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
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
        ValueExpression vb = getValueExpression("hreflang");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return hreflang;
        }
    }

    public void setHreflang(String hreflang) {
        this.hreflang = hreflang;
    }


    public String getLang() {
        ValueExpression vb = getValueExpression("lang");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return lang;
        }
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


    public String getOnblur() {
        ValueExpression vb = getValueExpression("onblur");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return onblur;
        }
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }


    public String getOnclick() {
        ValueExpression vb = getValueExpression("onclick");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return onclick;
        }
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }


    public String getOndblclick() {
        ValueExpression vb = getValueExpression("ondblclick");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return ondblclick;
        }
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }


    public String getOnfocus() {
        ValueExpression vb = getValueExpression("onfocus");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return onfocus;
        }
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }


    public String getOnkeydown() {
        ValueExpression vb = getValueExpression("onkeydown");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return onkeydown;
        }
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }


    public String getOnkeypress() {
        ValueExpression vb = getValueExpression("onkeypress");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return onkeypress;
        }
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }


    public String getOnkeyup() {
        ValueExpression vb = getValueExpression("onkeyup");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return onkeyup;
        }
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }


    public String getOnmousedown() {
        ValueExpression vb = getValueExpression("onmousedown");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return onmousedown;
        }
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }


    public String getOnmousemove() {
        ValueExpression vb = getValueExpression("onmousemove");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return onmousemove;
        }
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }


    public String getOnmouseout() {
        ValueExpression vb = getValueExpression("onmouseout");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return onmouseout;
        }
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }


    public String getOnmouseover() {
        ValueExpression vb = getValueExpression("onmouseover");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return onmouseover;
        }
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }


    public String getOnmouseup() {
        ValueExpression vb = getValueExpression("onmouseup");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return onmouseup;
        }
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }


    public String getRel() {
        ValueExpression vb = getValueExpression("rel");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return rel;
        }
    }

    public void setRel(String rel) {
        this.rel = rel;
    }


    public String getRev() {
        ValueExpression vb = getValueExpression("rev");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return rev;
        }
    }

    public void setRev(String rev) {
        this.rev = rev;
    }


    public String getStyle() {
        ValueExpression vb = getValueExpression("style");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return style;
        }
    }

    public void setStyle(String style) {
        this.style = style;
    }


    public String getStyleClass() {
        ValueExpression vb = getValueExpression("styleClass");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return styleClass;
        }
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }


    public String getTabindex() {
        ValueExpression vb = getValueExpression("tabindex");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return tabindex;
        }
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }


    public String getTarget() {
        ValueExpression vb = getValueExpression("target");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return target;
        }
    }

    public void setTarget(String target) {
        this.target = target;
    }


    public String getTitle() {
        ValueExpression vb = getValueExpression("title");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
        } else {
            return title;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getType() {
        ValueExpression vb = getValueExpression("type");
        if (vb != null) {
            return (String) vb.getValue(getFacesContext().getELContext());
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
