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

import org.apache.struts.taglib.html.ImgTag;
import org.apache.strutsel.taglib.utils.EvalHelper;

import javax.servlet.jsp.JspException;

/**
 * <p>Generate an IMG tag to the specified image URI. </p>
 *
 * <p>TODO:</p>
 *
 * <ul>
 *
 * <li>make the <strong>alt</strong> and <strong>src</strong> settable from
 * properties (for i18n)</li>
 *
 * <li>handle <strong>onLoad</strong>, <strong>onAbort</strong>, and
 * <strong>onError</strong> events (my JavaScript book is very old, there may
 * be more unsupported events in the past couple of IE versions) </li>
 *
 * </ul>
 *
 * <p> This class is a subclass of the class <code>org.apache.struts.taglib.html.ImgTag</code>
 * which provides most of the described functionality.  This subclass allows
 * all attribute values to be specified as expressions utilizing the
 * JavaServer Pages Standard Library expression language. </p>
 *
 * @version $Rev$
 */
public class ELImgTag extends ImgTag {
    /**
     * Instance variable mapped to "action" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String actionExpr;

    /**
     * Instance variable mapped to "module" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String moduleExpr;

    /**
     * Instance variable mapped to "align" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String alignExpr;

    /**
     * Instance variable mapped to "alt" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String altExpr;

    /**
     * Instance variable mapped to "altKey" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String altKeyExpr;

    /**
     * Instance variable mapped to "border" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String borderExpr;

    /**
     * Instance variable mapped to "bundle" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String bundleExpr;

    /**
     * Instance variable mapped to "dir" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String dirExpr;

    /**
     * Instance variable mapped to "height" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String heightExpr;

    /**
     * Instance variable mapped to "hspace" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String hspaceExpr;

    /**
     * Instance variable mapped to "imageName" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String imageNameExpr;

    /**
     * Instance variable mapped to "ismap" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String ismapExpr;

    /**
     * Instance variable mapped to "lang" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String langExpr;

    /**
     * Instance variable mapped to "locale" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String localeExpr;

    /**
     * Instance variable mapped to "name" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String nameExpr;

    /**
     * Instance variable mapped to "onclick" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String onclickExpr;

    /**
     * Instance variable mapped to "ondblclick" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String ondblclickExpr;

    /**
     * Instance variable mapped to "onkeydown" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String onkeydownExpr;

    /**
     * Instance variable mapped to "onkeypress" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String onkeypressExpr;

    /**
     * Instance variable mapped to "onkeyup" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String onkeyupExpr;

    /**
     * Instance variable mapped to "onmousedown" tag attribute. (Mapping set
     * in associated BeanInfo class.)
     */
    private String onmousedownExpr;

    /**
     * Instance variable mapped to "onmousemove" tag attribute. (Mapping set
     * in associated BeanInfo class.)
     */
    private String onmousemoveExpr;

    /**
     * Instance variable mapped to "onmouseout" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String onmouseoutExpr;

    /**
     * Instance variable mapped to "onmouseover" tag attribute. (Mapping set
     * in associated BeanInfo class.)
     */
    private String onmouseoverExpr;

    /**
     * Instance variable mapped to "onmouseup" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String onmouseupExpr;

    /**
     * Instance variable mapped to "paramId" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String paramIdExpr;

    /**
     * Instance variable mapped to "page" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String pageExpr;

    /**
     * Instance variable mapped to "pageKey" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String pageKeyExpr;

    /**
     * Instance variable mapped to "paramName" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String paramNameExpr;

    /**
     * Instance variable mapped to "paramProperty" tag attribute. (Mapping set
     * in associated BeanInfo class.)
     */
    private String paramPropertyExpr;

    /**
     * Instance variable mapped to "paramScope" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String paramScopeExpr;

    /**
     * Instance variable mapped to "property" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String propertyExpr;

    /**
     * Instance variable mapped to "scope" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String scopeExpr;

    /**
     * Instance variable mapped to "src" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String srcExpr;

    /**
     * Instance variable mapped to "srcKey" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String srcKeyExpr;

    /**
     * Instance variable mapped to "style" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String styleExpr;

    /**
     * Instance variable mapped to "styleClass" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String styleClassExpr;

    /**
     * Instance variable mapped to "styleId" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String styleIdExpr;

    /**
     * Instance variable mapped to "title" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String titleExpr;

    /**
     * Instance variable mapped to "titleKey" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String titleKeyExpr;

    /**
     * Instance variable mapped to "useLocalEncoding" tag attribute. (Mapping
     * set in associated BeanInfo class.)
     */
    private String useLocalEncodingExpr;

    /**
     * Instance variable mapped to "usemap" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String usemapExpr;

    /**
     * Instance variable mapped to "vspace" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String vspaceExpr;

    /**
     * Instance variable mapped to "width" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    private String widthExpr;

    /**
     * Getter method for "action" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getActionExpr() {
        return (actionExpr);
    }

    /**
     * Getter method for "module" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getModuleExpr() {
        return (moduleExpr);
    }

    /**
     * Getter method for "align" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getAlignExpr() {
        return (alignExpr);
    }

    /**
     * Getter method for "alt" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getAltExpr() {
        return (altExpr);
    }

    /**
     * Getter method for "altKey" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getAltKeyExpr() {
        return (altKeyExpr);
    }

    /**
     * Getter method for "border" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getBorderExpr() {
        return (borderExpr);
    }

    /**
     * Getter method for "bundle" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getBundleExpr() {
        return (bundleExpr);
    }

    /**
     * Getter method for "dir" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getDirExpr() {
        return (dirExpr);
    }

    /**
     * Getter method for "height" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getHeightExpr() {
        return (heightExpr);
    }

    /**
     * Getter method for "hspace" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getHspaceExpr() {
        return (hspaceExpr);
    }

    /**
     * Getter method for "imageName" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getImageNameExpr() {
        return (imageNameExpr);
    }

    /**
     * Getter method for "ismap" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getIsmapExpr() {
        return (ismapExpr);
    }

    /**
     * Getter method for "lang" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getLangExpr() {
        return (langExpr);
    }

    /**
     * Getter method for "locale" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getLocaleExpr() {
        return (localeExpr);
    }

    /**
     * Getter method for "name" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getNameExpr() {
        return (nameExpr);
    }

    /**
     * Getter method for "onclick" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getOnclickExpr() {
        return (onclickExpr);
    }

    /**
     * Getter method for "ondblclick" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getOndblclickExpr() {
        return (ondblclickExpr);
    }

    /**
     * Getter method for "onkeydown" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getOnkeydownExpr() {
        return (onkeydownExpr);
    }

    /**
     * Getter method for "onkeypress" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getOnkeypressExpr() {
        return (onkeypressExpr);
    }

    /**
     * Getter method for "onkeyup" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getOnkeyupExpr() {
        return (onkeyupExpr);
    }

    /**
     * Getter method for "onmousedown" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getOnmousedownExpr() {
        return (onmousedownExpr);
    }

    /**
     * Getter method for "onmousemove" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getOnmousemoveExpr() {
        return (onmousemoveExpr);
    }

    /**
     * Getter method for "onmouseout" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getOnmouseoutExpr() {
        return (onmouseoutExpr);
    }

    /**
     * Getter method for "onmouseover" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getOnmouseoverExpr() {
        return (onmouseoverExpr);
    }

    /**
     * Getter method for "onmouseup" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getOnmouseupExpr() {
        return (onmouseupExpr);
    }

    /**
     * Getter method for "paramId" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getParamIdExpr() {
        return (paramIdExpr);
    }

    /**
     * Getter method for "page" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getPageExpr() {
        return (pageExpr);
    }

    /**
     * Getter method for "pageKey" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getPageKeyExpr() {
        return (pageKeyExpr);
    }

    /**
     * Getter method for "paramName" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getParamNameExpr() {
        return (paramNameExpr);
    }

    /**
     * Getter method for "paramProperty" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getParamPropertyExpr() {
        return (paramPropertyExpr);
    }

    /**
     * Getter method for "paramScope" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getParamScopeExpr() {
        return (paramScopeExpr);
    }

    /**
     * Getter method for "property" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getPropertyExpr() {
        return (propertyExpr);
    }

    /**
     * Getter method for "scope" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getScopeExpr() {
        return (scopeExpr);
    }

    /**
     * Getter method for "src" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getSrcExpr() {
        return (srcExpr);
    }

    /**
     * Getter method for "srcKey" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getSrcKeyExpr() {
        return (srcKeyExpr);
    }

    /**
     * Getter method for "style" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getStyleExpr() {
        return (styleExpr);
    }

    /**
     * Getter method for "styleClass" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getStyleClassExpr() {
        return (styleClassExpr);
    }

    /**
     * Getter method for "styleId" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getStyleIdExpr() {
        return (styleIdExpr);
    }

    /**
     * Getter method for "title" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getTitleExpr() {
        return (titleExpr);
    }

    /**
     * Getter method for "titleKey" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getTitleKeyExpr() {
        return (titleKeyExpr);
    }

    /**
     * Getter method for "useLocalEncoding" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public String getUseLocalEncodingExpr() {
        return (useLocalEncodingExpr);
    }

    /**
     * Getter method for "usemap" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getUsemapExpr() {
        return (usemapExpr);
    }

    /**
     * Getter method for "vspace" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getVspaceExpr() {
        return (vspaceExpr);
    }

    /**
     * Getter method for "width" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public String getWidthExpr() {
        return (widthExpr);
    }

    /**
     * Setter method for "action" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setActionExpr(String actionExpr) {
        this.actionExpr = actionExpr;
    }

    /**
     * Setter method for "module" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setModuleExpr(String moduleExpr) {
        this.moduleExpr = moduleExpr;
    }

    /**
     * Setter method for "align" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setAlignExpr(String alignExpr) {
        this.alignExpr = alignExpr;
    }

    /**
     * Setter method for "alt" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setAltExpr(String altExpr) {
        this.altExpr = altExpr;
    }

    /**
     * Setter method for "altKey" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setAltKeyExpr(String altKeyExpr) {
        this.altKeyExpr = altKeyExpr;
    }

    /**
     * Setter method for "border" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setBorderExpr(String borderExpr) {
        this.borderExpr = borderExpr;
    }

    /**
     * Setter method for "bundle" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setBundleExpr(String bundleExpr) {
        this.bundleExpr = bundleExpr;
    }

    /**
     * Setter method for "dir" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setDirExpr(String dirExpr) {
        this.dirExpr = dirExpr;
    }

    /**
     * Setter method for "height" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setHeightExpr(String heightExpr) {
        this.heightExpr = heightExpr;
    }

    /**
     * Setter method for "hspace" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setHspaceExpr(String hspaceExpr) {
        this.hspaceExpr = hspaceExpr;
    }

    /**
     * Setter method for "imageName" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setImageNameExpr(String imageNameExpr) {
        this.imageNameExpr = imageNameExpr;
    }

    /**
     * Setter method for "ismap" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setIsmapExpr(String ismapExpr) {
        this.ismapExpr = ismapExpr;
    }

    /**
     * Setter method for "lang" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setLangExpr(String langExpr) {
        this.langExpr = langExpr;
    }

    /**
     * Setter method for "locale" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setLocaleExpr(String localeExpr) {
        this.localeExpr = localeExpr;
    }

    /**
     * Setter method for "name" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setNameExpr(String nameExpr) {
        this.nameExpr = nameExpr;
    }

    /**
     * Setter method for "onclick" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setOnclickExpr(String onclickExpr) {
        this.onclickExpr = onclickExpr;
    }

    /**
     * Setter method for "ondblclick" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setOndblclickExpr(String ondblclickExpr) {
        this.ondblclickExpr = ondblclickExpr;
    }

    /**
     * Setter method for "onkeydown" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setOnkeydownExpr(String onkeydownExpr) {
        this.onkeydownExpr = onkeydownExpr;
    }

    /**
     * Setter method for "onkeypress" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setOnkeypressExpr(String onkeypressExpr) {
        this.onkeypressExpr = onkeypressExpr;
    }

    /**
     * Setter method for "onkeyup" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setOnkeyupExpr(String onkeyupExpr) {
        this.onkeyupExpr = onkeyupExpr;
    }

    /**
     * Setter method for "onmousedown" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setOnmousedownExpr(String onmousedownExpr) {
        this.onmousedownExpr = onmousedownExpr;
    }

    /**
     * Setter method for "onmousemove" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setOnmousemoveExpr(String onmousemoveExpr) {
        this.onmousemoveExpr = onmousemoveExpr;
    }

    /**
     * Setter method for "onmouseout" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setOnmouseoutExpr(String onmouseoutExpr) {
        this.onmouseoutExpr = onmouseoutExpr;
    }

    /**
     * Setter method for "onmouseover" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setOnmouseoverExpr(String onmouseoverExpr) {
        this.onmouseoverExpr = onmouseoverExpr;
    }

    /**
     * Setter method for "onmouseup" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setOnmouseupExpr(String onmouseupExpr) {
        this.onmouseupExpr = onmouseupExpr;
    }

    /**
     * Setter method for "paramId" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setParamIdExpr(String paramIdExpr) {
        this.paramIdExpr = paramIdExpr;
    }

    /**
     * Setter method for "page" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setPageExpr(String pageExpr) {
        this.pageExpr = pageExpr;
    }

    /**
     * Setter method for "pageKey" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setPageKeyExpr(String pageKeyExpr) {
        this.pageKeyExpr = pageKeyExpr;
    }

    /**
     * Setter method for "paramName" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setParamNameExpr(String paramNameExpr) {
        this.paramNameExpr = paramNameExpr;
    }

    /**
     * Setter method for "paramProperty" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setParamPropertyExpr(String paramPropertyExpr) {
        this.paramPropertyExpr = paramPropertyExpr;
    }

    /**
     * Setter method for "paramScope" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setParamScopeExpr(String paramScopeExpr) {
        this.paramScopeExpr = paramScopeExpr;
    }

    /**
     * Setter method for "property" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setPropertyExpr(String propertyExpr) {
        this.propertyExpr = propertyExpr;
    }

    /**
     * Setter method for "scope" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setScopeExpr(String scopeExpr) {
        this.scopeExpr = scopeExpr;
    }

    /**
     * Setter method for "src" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setSrcExpr(String srcExpr) {
        this.srcExpr = srcExpr;
    }

    /**
     * Setter method for "srcKey" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setSrcKeyExpr(String srcKeyExpr) {
        this.srcKeyExpr = srcKeyExpr;
    }

    /**
     * Setter method for "style" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setStyleExpr(String styleExpr) {
        this.styleExpr = styleExpr;
    }

    /**
     * Setter method for "styleClass" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setStyleClassExpr(String styleClassExpr) {
        this.styleClassExpr = styleClassExpr;
    }

    /**
     * Setter method for "styleId" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setStyleIdExpr(String styleIdExpr) {
        this.styleIdExpr = styleIdExpr;
    }

    /**
     * Setter method for "title" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setTitleExpr(String titleExpr) {
        this.titleExpr = titleExpr;
    }

    /**
     * Setter method for "titleKey" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setTitleKeyExpr(String titleKeyExpr) {
        this.titleKeyExpr = titleKeyExpr;
    }

    /**
     * Setter method for "useLocalEncoding" tag attribute. (Mapping set in
     * associated BeanInfo class.)
     */
    public void setUseLocalEncodingExpr(String useLocalEncodingExpr) {
        this.useLocalEncodingExpr = useLocalEncodingExpr;
    }

    /**
     * Setter method for "usemap" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setUsemapExpr(String usemapExpr) {
        this.usemapExpr = usemapExpr;
    }

    /**
     * Setter method for "vspace" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setVspaceExpr(String vspaceExpr) {
        this.vspaceExpr = vspaceExpr;
    }

    /**
     * Setter method for "width" tag attribute. (Mapping set in associated
     * BeanInfo class.)
     */
    public void setWidthExpr(String widthExpr) {
        this.widthExpr = widthExpr;
    }

    /**
     * Resets attribute values for tag reuse.
     */
    public void release() {
        super.release();
        setActionExpr(null);
        setModuleExpr(null);
        setAlignExpr(null);
        setAltExpr(null);
        setAltKeyExpr(null);
        setBorderExpr(null);
        setBundleExpr(null);
        setDirExpr(null);
        setHeightExpr(null);
        setHspaceExpr(null);
        setImageNameExpr(null);
        setIsmapExpr(null);
        setLangExpr(null);
        setLocaleExpr(null);
        setNameExpr(null);
        setOnclickExpr(null);
        setOndblclickExpr(null);
        setOnkeydownExpr(null);
        setOnkeypressExpr(null);
        setOnkeyupExpr(null);
        setOnmousedownExpr(null);
        setOnmousemoveExpr(null);
        setOnmouseoutExpr(null);
        setOnmouseoverExpr(null);
        setOnmouseupExpr(null);
        setPageExpr(null);
        setPageKeyExpr(null);
        setParamIdExpr(null);
        setParamNameExpr(null);
        setParamPropertyExpr(null);
        setParamScopeExpr(null);
        setPropertyExpr(null);
        setScopeExpr(null);
        setSrcExpr(null);
        setSrcKeyExpr(null);
        setStyleExpr(null);
        setStyleClassExpr(null);
        setStyleIdExpr(null);
        setTitleExpr(null);
        setTitleKeyExpr(null);
        setUseLocalEncodingExpr(null);
        setUsemapExpr(null);
        setVspaceExpr(null);
        setWidthExpr(null);
    }

    /**
     * Process the start tag.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        evaluateExpressions();

        return (super.doStartTag());
    }

    /**
     * Processes all attribute values which use the JSTL expression evaluation
     * engine to determine their values.
     *
     * @throws JspException if a JSP exception has occurred
     */
    private void evaluateExpressions()
        throws JspException {
        String string = null;
        Boolean bool = null;

        if ((string =
                EvalHelper.evalString("action", getActionExpr(), this,
                    pageContext)) != null) {
            setAction(string);
        }

        if ((string =
                EvalHelper.evalString("module", getModuleExpr(), this,
                    pageContext)) != null) {
            setModule(string);
        }

        if ((string =
                EvalHelper.evalString("align", getAlignExpr(), this, pageContext)) != null) {
            setAlign(string);
        }

        if ((string =
                EvalHelper.evalString("alt", getAltExpr(), this, pageContext)) != null) {
            setAlt(string);
        }

        if ((string =
                EvalHelper.evalString("altKey", getAltKeyExpr(), this,
                    pageContext)) != null) {
            setAltKey(string);
        }

        if ((string =
                EvalHelper.evalString("border", getBorderExpr(), this,
                    pageContext)) != null) {
            setBorder(string);
        }

        if ((string =
                EvalHelper.evalString("bundle", getBundleExpr(), this,
                    pageContext)) != null) {
            setBundle(string);
        }

        if ((string =
        		EvalHelper.evalString("dir", getDirExpr(), this,
        			pageContext)) != null) {
        	setDir(string);
        }
        
        if ((string =
                EvalHelper.evalString("height", getHeightExpr(), this,
                    pageContext)) != null) {
            setHeight(string);
        }

        if ((string =
                EvalHelper.evalString("hspace", getHspaceExpr(), this,
                    pageContext)) != null) {
            setHspace(string);
        }

        if ((string =
                EvalHelper.evalString("imageName", getImageNameExpr(), this,
                    pageContext)) != null) {
            setImageName(string);
        }

        if ((string =
                EvalHelper.evalString("ismap", getIsmapExpr(), this, pageContext)) != null) {
            setIsmap(string);
        }

        if ((string =
            	EvalHelper.evalString("lang", getLangExpr(), this,
            		pageContext)) != null) {
        	setLang(string);
        }

        if ((string =
                EvalHelper.evalString("locale", getLocaleExpr(), this,
                    pageContext)) != null) {
            setLocale(string);
        }

        if ((string =
                EvalHelper.evalString("name", getNameExpr(), this, pageContext)) != null) {
            setName(string);
        }

        if ((string =
                EvalHelper.evalString("onclick", getOnclickExpr(), this,
                    pageContext)) != null) {
            setOnclick(string);
        }

        if ((string =
                EvalHelper.evalString("ondblclick", getOndblclickExpr(), this,
                    pageContext)) != null) {
            setOndblclick(string);
        }

        if ((string =
                EvalHelper.evalString("onkeydown", getOnkeydownExpr(), this,
                    pageContext)) != null) {
            setOnkeydown(string);
        }

        if ((string =
                EvalHelper.evalString("onkeypress", getOnkeypressExpr(), this,
                    pageContext)) != null) {
            setOnkeypress(string);
        }

        if ((string =
                EvalHelper.evalString("onkeyup", getOnkeyupExpr(), this,
                    pageContext)) != null) {
            setOnkeyup(string);
        }

        if ((string =
                EvalHelper.evalString("onmousedown", getOnmousedownExpr(),
                    this, pageContext)) != null) {
            setOnmousedown(string);
        }

        if ((string =
                EvalHelper.evalString("onmousemove", getOnmousemoveExpr(),
                    this, pageContext)) != null) {
            setOnmousemove(string);
        }

        if ((string =
                EvalHelper.evalString("onmouseout", getOnmouseoutExpr(), this,
                    pageContext)) != null) {
            setOnmouseout(string);
        }

        if ((string =
                EvalHelper.evalString("onmouseover", getOnmouseoverExpr(),
                    this, pageContext)) != null) {
            setOnmouseover(string);
        }

        if ((string =
                EvalHelper.evalString("onmouseup", getOnmouseupExpr(), this,
                    pageContext)) != null) {
            setOnmouseup(string);
        }

        if ((string =
                EvalHelper.evalString("paramId", getParamIdExpr(), this,
                    pageContext)) != null) {
            setParamId(string);
        }

        if ((string =
                EvalHelper.evalString("page", getPageExpr(), this, pageContext)) != null) {
            setPage(string);
        }

        if ((string =
                EvalHelper.evalString("pageKey", getPageKeyExpr(), this,
                    pageContext)) != null) {
            setPageKey(string);
        }

        if ((string =
                EvalHelper.evalString("paramName", getParamNameExpr(), this,
                    pageContext)) != null) {
            setParamName(string);
        }

        if ((string =
                EvalHelper.evalString("paramProperty", getParamPropertyExpr(),
                    this, pageContext)) != null) {
            setParamProperty(string);
        }

        if ((string =
                EvalHelper.evalString("paramScope", getParamScopeExpr(), this,
                    pageContext)) != null) {
            setParamScope(string);
        }

        if ((string =
                EvalHelper.evalString("property", getPropertyExpr(), this,
                    pageContext)) != null) {
            setProperty(string);
        }

        if ((string =
                EvalHelper.evalString("scope", getScopeExpr(), this, pageContext)) != null) {
            setScope(string);
        }

        if ((string =
                EvalHelper.evalString("src", getSrcExpr(), this, pageContext)) != null) {
            setSrc(string);
        }

        if ((string =
                EvalHelper.evalString("srcKey", getSrcKeyExpr(), this,
                    pageContext)) != null) {
            setSrcKey(string);
        }

        if ((string =
                EvalHelper.evalString("style", getStyleExpr(), this, pageContext)) != null) {
            setStyle(string);
        }

        if ((string =
                EvalHelper.evalString("styleClass", getStyleClassExpr(), this,
                    pageContext)) != null) {
            setStyleClass(string);
        }

        if ((string =
                EvalHelper.evalString("styleId", getStyleIdExpr(), this,
                    pageContext)) != null) {
            setStyleId(string);
        }

        if ((string =
                EvalHelper.evalString("title", getTitleExpr(), this, pageContext)) != null) {
            setTitle(string);
        }

        if ((string =
                EvalHelper.evalString("titleKey", getTitleKeyExpr(), this,
                    pageContext)) != null) {
            setTitleKey(string);
        }

        if ((bool =
                EvalHelper.evalBoolean("useLocalEncoding",
                    getUseLocalEncodingExpr(), this, pageContext)) != null) {
            setUseLocalEncoding(bool.booleanValue());
        }

        if ((string =
                EvalHelper.evalString("usemap", getUsemapExpr(), this,
                    pageContext)) != null) {
            setUsemap(string);
        }

        if ((string =
                EvalHelper.evalString("vspace", getVspaceExpr(), this,
                    pageContext)) != null) {
            setVspace(string);
        }

        if ((string =
                EvalHelper.evalString("width", getWidthExpr(), this, pageContext)) != null) {
            setWidth(string);
        }
    }
}
