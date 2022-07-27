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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Locale;

import org.apache.struts.config.MessageResourcesConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link PropertyMessageResources}.
 *
 * @version $Revision$
 */
public class TestPropertyMessageResources {


    private static final String FOO_RESOURCES = "org.apache.struts.util.Foo";

    private Locale defaultLocale;

    // ----------------------------------------------------- Setup and Teardown
    @BeforeEach
    public void setUp() {
        // cache the default locale
        defaultLocale = Locale.getDefault();
    }

    @AfterEach
    public void tearDown() {
        // restore the default locale
        Locale.setDefault(defaultLocale);
    }

    // ------------------------------------------------------- Individual Tests

    /**
     * Test Struts default PropertyMessageResources behaviour
     */
    @Test
    public void testDefaultMode() {

        Locale.setDefault(Locale.US);

        // Create message resources - default Struts Behaviour
//        MessageResources resources = createMessageResources(FOO_RESOURCES, true, "DEFAULT");
        MessageResources resources = createMessageResources(FOO_RESOURCES, true, null);

        // Test language (& default) only keys
        assertEquals("LANG default", resources.getMessage(Locale.FRANCE,  "key.lang"), "key.lang FRANCE" ); // no cached en_US
        assertEquals("LANG en",      resources.getMessage(Locale.ENGLISH, "key.lang"), "key.lang English");
        assertEquals("LANG en",      resources.getMessage(Locale.US,      "key.lang"), "key.lang US"     );
        assertEquals("LANG en",      resources.getMessage(Locale.ITALY,   "key.lang"), "key.lang ITALY"  ); // cached en_US
        assertEquals("LANG de",      resources.getMessage(Locale.GERMAN,  "key.lang"), "key.lang German" );
        assertEquals("LANG de",      resources.getMessage(Locale.GERMANY, "key.lang"), "key.lang GERMANY");

        // Test country (& default) only keys
        assertEquals("COUNTRY en_US", resources.getMessage(Locale.FRANCE,  "key.country"), "key.country FRANCE" );
        assertEquals("COUNTRY en_US", resources.getMessage(Locale.ENGLISH, "key.country"), "key.country English");
        assertEquals("COUNTRY en_US", resources.getMessage(Locale.US,      "key.country"), "key.country US"     );
        assertEquals("COUNTRY en_US", resources.getMessage(Locale.ITALY,   "key.country"), "key.country ITALY"  );
        assertEquals("COUNTRY en_US", resources.getMessage(Locale.GERMAN,  "key.country"), "key.country German" );
        assertEquals("COUNTRY de_DE", resources.getMessage(Locale.GERMANY, "key.country"), "key.country GERMANY");

        // Test Unique Keys with wrong Locale
        assertNull(                resources.getMessage(Locale.GERMAN,  "key.en"),    "Wrong Locale en only"   );
        assertEquals("en_US only", resources.getMessage(Locale.GERMANY, "key.en_US"), "Wrong Locale en_US only");

        // Run tests with common expected results
        commonTests(resources);
    }

    /**
     * Test JSTL compatible PropertyMessageResources behaviour
     */
    @Test
    public void testJstlMode() {

        Locale.setDefault(Locale.US);

        // Create message resources - default Struts Behaviour
        MessageResources resources = createMessageResources(FOO_RESOURCES, true, "JSTL");

        // Test language (& default) only keys
        assertEquals("LANG default", resources.getMessage(Locale.FRANCE,  "key.lang"), "key.lang FRANCE" );
        assertEquals("LANG en",      resources.getMessage(Locale.ENGLISH, "key.lang"), "key.lang English");
        assertEquals("LANG en",      resources.getMessage(Locale.US,      "key.lang"), "key.lang US"     );
        assertEquals("LANG default", resources.getMessage(Locale.ITALY,   "key.lang"), "key.lang ITALY"  );
        assertEquals("LANG de",      resources.getMessage(Locale.GERMAN,  "key.lang"), "key.lang German" );
        assertEquals("LANG de",      resources.getMessage(Locale.GERMANY, "key.lang"), "key.lang GERMANY");

        // Test country (& default) only keys
        assertEquals("COUNTRY default", resources.getMessage(Locale.FRANCE,  "key.country"), "key.country FRANCE" );
        assertEquals("COUNTRY default", resources.getMessage(Locale.ENGLISH, "key.country"), "key.country English");
        assertEquals("COUNTRY en_US",   resources.getMessage(Locale.US,      "key.country"), "key.country US"     );
        assertEquals("COUNTRY default", resources.getMessage(Locale.ITALY,   "key.country"), "key.country ITALY"  );
        assertEquals("COUNTRY default", resources.getMessage(Locale.GERMAN,  "key.country"), "key.country German" );
        assertEquals("COUNTRY de_DE",   resources.getMessage(Locale.GERMANY, "key.country"), "key.country GERMANY");

        // Test Unique Keys with wrong Locale
        assertNull(resources.getMessage(Locale.GERMAN,  "key.en"),    "Wrong Locale en only"   );
        assertNull(resources.getMessage(Locale.GERMANY, "key.en_US"), "Wrong Locale en_US only");

        // Run tests with common expected results
        commonTests(resources);

    }

    /**
     * Test "PropertyResourceBundle" compatible PropertyMessageResources behaviour
     */
    @Test
    public void testResourceBundleMode() {

        Locale.setDefault(Locale.US);

        // Create message resources - default Struts Behaviour
        MessageResources resources = createMessageResources(FOO_RESOURCES, true, "RESOURCE");

        // Test language (& default) only keys
        assertEquals("LANG en", resources.getMessage(Locale.FRANCE,  "key.lang"), "key.lang FRANCE" );
        assertEquals("LANG en", resources.getMessage(Locale.ENGLISH, "key.lang"), "key.lang English");
        assertEquals("LANG en", resources.getMessage(Locale.US,      "key.lang"), "key.lang US"     );
        assertEquals("LANG en", resources.getMessage(Locale.ITALY,   "key.lang"), "key.lang ITALY"  );
        assertEquals("LANG de", resources.getMessage(Locale.GERMAN,  "key.lang"), "key.lang German" );
        assertEquals("LANG de", resources.getMessage(Locale.GERMANY, "key.lang"), "key.lang GERMANY");

        // Test country (& default) only keys
        assertEquals("COUNTRY en_US", resources.getMessage(Locale.FRANCE,  "key.country"), "key.country FRANCE" );
        assertEquals("COUNTRY en_US", resources.getMessage(Locale.ENGLISH, "key.country"), "key.country English");
        assertEquals("COUNTRY en_US", resources.getMessage(Locale.US,      "key.country"), "key.country US"     );
        assertEquals("COUNTRY en_US", resources.getMessage(Locale.ITALY,   "key.country"), "key.country ITALY"  );
        assertEquals("COUNTRY en_US", resources.getMessage(Locale.GERMAN,  "key.country"), "key.country German" );
        assertEquals("COUNTRY de_DE", resources.getMessage(Locale.GERMANY, "key.country"), "key.country GERMANY");

        // Test Unique Keys with wrong Locale
        assertEquals("en only",    resources.getMessage(Locale.GERMAN,  "key.en"), "Wrong Locale en only"      );
        assertEquals("en_US only", resources.getMessage(Locale.GERMANY, "key.en_US"), "Wrong Locale en_US only");

        // Run tests with common expected results
        commonTests(resources);
    }

    /**
     * Tests with common expected results
     */
    private void commonTests(MessageResources resources) {

        // Test "null" Locale
        assertEquals("ALL default",  resources.getMessage((Locale)null,   "key.all"),     "null Locale");

        // Test Default only key with all Locales
        assertEquals("default only", resources.getMessage(Locale.ENGLISH, "key.default"), "Check default en"   );
        assertEquals("default only", resources.getMessage(Locale.US,      "key.default"), "Check default en_US");
        assertEquals("default only", resources.getMessage(Locale.GERMAN,  "key.default"), "Check default de"   );
        assertEquals("default only", resources.getMessage(Locale.GERMANY, "key.default"), "Check default de_DE");

        // Test key in all locales
        assertEquals("ALL en",       resources.getMessage(Locale.ENGLISH, "key.all"),     "Check ALL en"   );
        assertEquals("ALL en_US",    resources.getMessage(Locale.US,      "key.all"),     "Check ALL en_US");
        assertEquals("ALL de",       resources.getMessage(Locale.GERMAN,  "key.all"),     "Check ALL de"   );
        assertEquals("ALL de_DE",    resources.getMessage(Locale.GERMANY, "key.all"),     "Check ALL de_DE");

        // Test key unique to each locale
        assertEquals("en only",      resources.getMessage(Locale.ENGLISH, "key.en"),      "Check en only"   );
        assertEquals("en_US only",   resources.getMessage(Locale.US,      "key.en_US"),   "Check en_US only");
        assertEquals("de only",      resources.getMessage(Locale.GERMAN,  "key.de"),      "Check de only"   );
        assertEquals("de_DE only",   resources.getMessage(Locale.GERMANY, "key.de_DE"),   "Check de_DE only");

        // Test unique keys with incorrect Locale
        assertNull(                  resources.getMessage(Locale.ENGLISH, "missing"),     "Missing default"   );
        assertNull(                  resources.getMessage(Locale.US,      "key.de"),      "Missing de only"   );
        assertNull(                  resources.getMessage(Locale.US,      "key.de_DE"),   "Missing de_DE only");
    }

    /**
     * Create the PropertyMessageResources.
     */
    private MessageResources createMessageResources(String file, boolean returnNull, String mode) {
        MessageResourcesConfig config = new MessageResourcesConfig();
        config.setNull(returnNull);
        if (mode != null) {
            config.setProperty("mode", mode);
        }
        PropertyMessageResourcesFactory factory = new PropertyMessageResourcesFactory();
        factory.setConfig(config);
        factory.setReturnNull(returnNull);
        return factory.createResources(file);
    }
}
