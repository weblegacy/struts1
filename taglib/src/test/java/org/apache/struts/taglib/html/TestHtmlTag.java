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
package org.apache.struts.taglib.html;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.apache.struts.mock.MockPageContext;
import org.apache.struts.mock.MockServletConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link HtmlTag}.
 */
public class TestHtmlTag {

    private MockServletConfig       config;
    private MockHttpServletRequest  request;
    private MockHttpServletResponse response;
    private MockPageContext         pageContext;
    private HtmlTag                 htmlTag;

    /**
     * Set up mock objects.
     */
    @BeforeEach
    public void setUp() {
        config      = new MockServletConfig();
        request     = new MockHttpServletRequest();
        response    = new MockHttpServletResponse();
        pageContext = new MockPageContext(config, request, response);
        htmlTag     = new HtmlTag();
        htmlTag.setPageContext(pageContext);
    }

    /**
     * Test the "lang" attribute with valid characters.
     */
    @Test
    public void testValidLangTrue() {

        // switch to render "lang" attribute
        htmlTag.setLang(true);

        // Render for Locale.US
        request.setLocale(Locale.US);
        assertEquals("<html lang=\"en-US\">", htmlTag.renderHtmlStartElement(), "render en_US");

        // Render for Locale.ENGLISH
        request.setLocale(Locale.ENGLISH);
        assertEquals("<html lang=\"en\">", htmlTag.renderHtmlStartElement(), "render en");

        // Test valid characters
        request.setLocale(new Locale("abcd-efghijklmnopqrstuvwxyz", "ABCDEFGHIJKLM-NOPQRSTUVWXYZ", ""));
        assertEquals("<html lang=\"abcd-efghijklmnopqrstuvwxyz-ABCDEFGHIJKLM-NOPQRSTUVWXYZ\">", htmlTag.renderHtmlStartElement(), "valid characters");

    }

    /**
     * Test the "lang" attribute with valid characters.
     */
    @Test
    public void testValidLangFalse() {

        // switch to NOT render "lang" attribute
        htmlTag.setLang(false);

        // Ignore for Locale.US
        request.setLocale(Locale.US);
        assertEquals("<html>", htmlTag.renderHtmlStartElement(), "ignore en_US");

        // Ignore for Locale.ENGLISH
        request.setLocale(Locale.ENGLISH);
        assertEquals("<html>", htmlTag.renderHtmlStartElement(), "ignore en");

    }

    /**
     * Test an invalid "language"
     */
    @Test
    public void testInvalidLanguage() {

        // switch to render "lang" attribute
        htmlTag.setLang(true);

        // make sure HtmlTag is setup to render "lang" using a valid value
        request.setLocale(Locale.US);
        assertEquals("<html lang=\"en-US\">", htmlTag.renderHtmlStartElement(), "check valid");

        // Test script injection
        request.setLocale(new Locale("/><script>alert()</script>", "", ""));
        assertEquals("<html>", htmlTag.renderHtmlStartElement(), "invalid <script>");

        // Test <
        request.setLocale(new Locale("abc<def", "", ""));
        assertEquals("<html>", htmlTag.renderHtmlStartElement(), "invalid LT");

        // Test >
        request.setLocale(new Locale("abc>def", "", ""));
        assertEquals("<html>", htmlTag.renderHtmlStartElement(), "invalid GT");

        // Test /
        request.setLocale(new Locale("abc/def", "", ""));
        assertEquals("<html>", htmlTag.renderHtmlStartElement(), "invalid SLASH");

        // Test &
        request.setLocale(new Locale("abc&def", "", ""));
        assertEquals("<html>", htmlTag.renderHtmlStartElement(), "invalid AMP");

    }

    /**
     * Test an invalid "country"
     */
    @Test
    public void testInvalidCountry() {

        // switch to render "lang" attribute
        htmlTag.setLang(true);

        // make sure HtmlTag is setup to render "lang" using a valid value
        request.setLocale(Locale.US);
        assertEquals("<html lang=\"en-US\">", htmlTag.renderHtmlStartElement(), "check valid");

        // Test script injection
        request.setLocale(new Locale("en", "/><script>alert()</script>", ""));
        assertEquals("<html lang=\"en\">", htmlTag.renderHtmlStartElement(), "invalid <script>");

        // Test <
        request.setLocale(new Locale("en", "abc<def", ""));
        assertEquals("<html lang=\"en\">", htmlTag.renderHtmlStartElement(), "invalid LT");

        // Test >
        request.setLocale(new Locale("en", "abc>def", ""));
        assertEquals("<html lang=\"en\">", htmlTag.renderHtmlStartElement(), "invalid GT");

        // Test /
        request.setLocale(new Locale("en", "abc/def", ""));
        assertEquals("<html lang=\"en\">", htmlTag.renderHtmlStartElement(), "invalid SLASH");

        // Test &
        request.setLocale(new Locale("en", "abc&def", ""));
        assertEquals("<html lang=\"en\">", htmlTag.renderHtmlStartElement(), "invalid AMP");

    }
}
