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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General purpose utility methods related to generating a servlet response in
 * the Struts controller framework.
 *
 * @version $Rev$ $Date: 2005-08-21 14:46:28 -0400 (Sun, 21 Aug 2005)
 *          $
 */
public class ResponseUtils {
    // ------------------------------------------------------- Static Variables

    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
        MessageResources.getMessageResources(
            "org.apache.struts.util.LocalStrings");

    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(ResponseUtils.class);

    // --------------------------------------------------------- Public Methods

    /**
     * Filter the specified string for characters that are senstive to HTML
     * interpreters, returning the string with these characters replaced by
     * the corresponding character entities.
     *
     * @param value The string to be filtered and returned
     */
    public static String filter(String value) {
        if ((value == null) || (value.length() == 0)) {
            return value;
        }

        StringBuilder result = null;
        String filtered = null;

        for (int i = 0; i < value.length(); i++) {
            filtered = null;

            switch (value.charAt(i)) {
            case '<':
                filtered = "&lt;";

                break;

            case '>':
                filtered = "&gt;";

                break;

            case '&':
                filtered = "&amp;";

                break;

            case '"':
                filtered = "&quot;";

                break;

            case '\'':
                filtered = "&#39;";

                break;
            }

            if (result == null) {
                if (filtered != null) {
                    result = new StringBuilder(value.length() + 50);

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

        return (result == null) ? value : result.toString();
    }

    /**
     * URLencodes a string assuming the character encoding is UTF-8.
     *
     * @param url
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
     * @param enc The character encoding the urlencode is performed on.
     * @return String The encoded url.
     */
    public static String encodeURL(String url, String enc) {
        String str = null;

        try {
            if ((enc == null) || (enc.length() == 0)) {
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