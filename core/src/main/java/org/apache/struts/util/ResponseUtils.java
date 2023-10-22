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
package org.apache.struts.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General purpose utility methods related to generating a servlet response in
 * the Struts controller framework.
 */
public class ResponseUtils {

    // ------------------------------------------------------- Static Variables

    /**
     * The {@code Log} instance for this class.
     */
    private static final Logger LOG =
        LoggerFactory.getLogger(ResponseUtils.class);

    /**
     * Pattern to find XML-Entity-Patterns.
     *
     * <p>Valid patterns are:</p>
     *
     * <ul>
     * <li>&amp;[a-zA-Z][a-z-A-Z0-9]*; - &amp;amp;, &amp;quot;</li>
     * <li>&amp;#[0-9]+; - &amp;#32;, &amp;#64;</li>
     * <li>&amp;#x[a-fA-F0-9]+; - &amp;#x20, &amp;3f</li>
     * </ul>
     *
     * <p>See also <a href="https://www.w3.org/TR/xml11/#sec-references">
     * XML-Reference 1.1</a>.</p>
     */
    private static final Pattern XML_ENTITY_PATTERN =
            Pattern.compile("&(?:[a-zA-Z][a-zA-Z\\d]*|#\\d+|#x[a-fA-F\\d]+);");

    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
        MessageResources.getMessageResources(
            "org.apache.struts.util.LocalStrings");

    // ----------------------------------------------------------- Constructors

    /**
     * Class is not instanceable.
     */
    protected ResponseUtils() {
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Filter the specified string for characters that are sensitive to HTML
     * interpreters, returning the string with these characters replaced by
     * the corresponding character entities.
     *
     * @param value The string to be filtered and returned
     *
     * @return String The filtered string
     */
    public static String filter(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        final int length = value.length();

        StringBuilder result = null;
        String filtered;
        Matcher entityMatcher = null;

        for (int i = 0; i < length; i++) {
            filtered = null;

            switch (value.charAt(i)) {
            case '<':
                filtered = "&lt;";

                break;

            case '>':
                filtered = "&gt;";

                break;

            case '&':
                if (entityMatcher == null) {
                    entityMatcher = XML_ENTITY_PATTERN.matcher(value);
                }
                entityMatcher.region(i, length);
                if (entityMatcher.lookingAt()) {
                    filtered = value.substring(entityMatcher.start(), entityMatcher.end());
                    i += filtered.length() - 1;
                    if (result == null) {
                        continue;
                    }
                } else {
                    filtered = "&amp;";
                }

                break;

            case '"':
                filtered = "&quot;";

                break;

            case '\'':
                filtered = "&#39;";

                break;

             default:
                    break;
            }

            if (result == null) {
                if (filtered != null) {
                    result = new StringBuilder(length + 50);

                    if (i > 0) {
                        result.append(value.substring(0, i));
                    }

                    result.append(filtered);
                }
            } else {
                if (filtered == null) {
                    result.append(value.charAt(i));
                } else {
                    result.append(filtered);
                }
            }
        }

        return result == null ? value : result.toString();
    }

    /**
     * URLencodes a string assuming the character encoding is UTF-8.
     *
     * @param url The url to encode
     *
     * @return String The encoded url in UTF-8
     */
    public static String encodeURL(String url) {
        return encodeURL(url, "UTF-8");
    }

    /**
     * Use the URLEncoder.encode() with the given encoding. When the
     * encoding charset didn't found, then it will be tried with the
     * default-charset.
     *
     * @param url The url to encode
     * @param enc The character encoding the urlencode is performed on.
     *
     * @return String The encoded url.
     */
    public static String encodeURL(String url, String enc) {
        String str = null;

        try {
            if (enc == null || enc.isEmpty()) {
                enc = "UTF-8";
            }

            str = URLEncoder.encode(url, enc);
        } catch (UnsupportedEncodingException e) {
            LOG.debug("The named encoding is not supported: {}", enc, e);

            try {
                str = URLEncoder.encode(url, Charset.defaultCharset().toString());
            } catch (UnsupportedEncodingException e1) {
                // Should never happen - the system should always have the platform default
                LOG.debug("The default-encoding is not supported: {}", enc, e1);

                str = url;
            }
        }

        return str;
    }
}
