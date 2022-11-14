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
package org.apache.struts.tiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jakarta.servlet.ServletException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.ModuleConfigFactory;
import org.apache.struts.config.PlugInConfig;
import org.apache.struts.mock.MockActionServlet;
import org.apache.struts.mock.TestMockBase;
import org.apache.struts.tiles.xmlDefinition.I18nFactorySet;
import org.apache.struts.util.RequestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTilesPlugin extends TestMockBase {


  protected ModuleConfig module1;
  protected ModuleConfig module2;
  protected MockActionServlet actionServlet;

  /**
   * The {@code Log} instance for this class.
   */
  private final Logger log =
    LoggerFactory.getLogger(TestTilesPlugin.class);

    // ----------------------------------------------------- Setup and Teardown


    @BeforeEach
    public void setUp()
    {

    super.setUp();
    TilesUtil.testReset();
    actionServlet = new MockActionServlet(context, config);
    }


    @AfterEach
    public void tearDown() {

        super.tearDown();

    }


    // ------------------------------------------------------- Individual Tests


    /**
     * Create a module configuration
     * @param moduleName
     */
    public ModuleConfig createModuleConfig(
        String moduleName,
        String configFileName,
        boolean moduleAware) {

        ModuleConfig moduleConfig =
            ModuleConfigFactory.createFactory().createModuleConfig(moduleName);

        context.setAttribute(Globals.MODULE_KEY + moduleName, moduleConfig);

        // Set tiles plugin
        PlugInConfig pluginConfig = new PlugInConfig();
        pluginConfig.setClassName("org.apache.struts.tiles.TilesPlugin");

        pluginConfig.addProperty(
            "moduleAware",
            (moduleAware == true ? "true" : "false"));

        pluginConfig.addProperty(
            "definitions-config",
            "/org/apache/struts/tiles/config/" + configFileName);

        moduleConfig.addPlugInConfig(pluginConfig);
        return moduleConfig;
    }

    /**
     * Fake call to init module plugins
     * @param moduleConfig
     * @throws ServletException If something goes wrong during initialization.
     * @throws InvocationTargetException Bean properties problems.
     * @throws InstantiationException Bean properties problems.
     * @throws IllegalAccessException Bean properties problems.
     * @throws ClassNotFoundException Bean properties problems.
     */
  public void initModulePlugIns( ModuleConfig moduleConfig)
          throws ClassNotFoundException, IllegalAccessException,
          InstantiationException, InvocationTargetException, ServletException
  {
  PlugInConfig plugInConfigs[] = moduleConfig.findPlugInConfigs();
  PlugIn plugIns[] = new PlugIn[plugInConfigs.length];

  context.setAttribute(Globals.PLUG_INS_KEY + moduleConfig.getPrefix(), plugIns);
  for (int i = 0; i < plugIns.length; i++) {
      plugIns[i] =
          (PlugIn) RequestUtils.applicationInstance(plugInConfigs[i].getClassName());
      BeanUtils.populate(plugIns[i], plugInConfigs[i].getProperties());
        // Pass the current plugIn config object to the PlugIn.
        // The property is set only if the plugin declares it.
        // This plugin config object is needed by Tiles
      BeanUtils.copyProperty( plugIns[i], "currentPlugInConfigObject", plugInConfigs[i]);
      plugIns[i].init(actionServlet, moduleConfig);
  }
  }

    // ---------------------------------------------------------- absoluteURL()


    /**
     * Test multi factory creation when moduleAware=true.
     *
     * @throws ServletException If something goes wrong during initialization.
     * @throws InvocationTargetException Bean properties problems.
     * @throws InstantiationException Bean properties problems.
     * @throws IllegalAccessException Bean properties problems.
     * @throws ClassNotFoundException Bean properties problems.
     */
    @Test
    public void testMultiFactory() throws ClassNotFoundException,
            IllegalAccessException, InstantiationException,
            InvocationTargetException, ServletException {
        // init TilesPlugin
        module1 = createModuleConfig("/module1", "tiles-defs.xml", true);
        module2 = createModuleConfig("/module2", "tiles-defs.xml", true);
        initModulePlugIns(module1);
        initModulePlugIns(module2);

        // mock request context
        request.setAttribute(Globals.MODULE_KEY, module1);
        request.setPathElements("/myapp", "/module1/foo.do", null, null);
        // Retrieve factory for module1
        DefinitionsFactory factory1 =
            TilesUtil.getDefinitionsFactory(request, context);

        assertNotNull(factory1, "factory found");
        assertEquals(
            "/module1",
            factory1.getConfig().getFactoryName(),
            "factory name");

        // mock request context
        request.setAttribute(Globals.MODULE_KEY, module2);
        request.setPathElements("/myapp", "/module2/foo.do", null, null);
        // Retrieve factory for module2
        DefinitionsFactory factory2 =
            TilesUtil.getDefinitionsFactory(request, context);
        assertNotNull(factory2, "factory found");
        assertEquals(
            "/module2",
            factory2.getConfig().getFactoryName(),
            "factory name");

        // Check that factory are different
        assertNotSame(factory1, factory2, "Factory from different modules");
    }

    /**
     * Test single factory creation when moduleAware=false.
     *
     * @throws ServletException If something goes wrong during initialization.
     * @throws InvocationTargetException Bean properties problems.
     * @throws InstantiationException Bean properties problems.
     * @throws IllegalAccessException Bean properties problems.
     * @throws ClassNotFoundException Bean properties problems.
     */
  @Test
  public void testSingleSharedFactory()
          throws ClassNotFoundException, IllegalAccessException,
          InstantiationException, InvocationTargetException, ServletException
  {
    // init TilesPlugin
  module1 = createModuleConfig( "/module1", "tiles-defs.xml", false );
  module2 = createModuleConfig( "/module2", "tiles-defs.xml", false );
  initModulePlugIns(module1);
  try {
      initModulePlugIns(module2);
      fail("An exception should have been thrown");
  } catch (ServletException e) {
      // It is ok
      log.debug("Intercepted a ServletException, it is ok", e);
  }

    // mock request context
  request.setAttribute(Globals.MODULE_KEY, module1);
  request.setPathElements("/myapp", "/module1/foo.do", null, null);
    // Retrieve factory for module1
  DefinitionsFactory factory1 = TilesUtil.getDefinitionsFactory( request, context);
  assertNotNull( factory1, "factory found");
  assertEquals( "/module1", factory1.getConfig().getFactoryName(), "factory name" );

    // mock request context
  request.setAttribute(Globals.MODULE_KEY, module2);
  request.setPathElements("/myapp", "/module2/foo.do", null, null);
    // Retrieve factory for module2
  DefinitionsFactory factory2 = TilesUtil.getDefinitionsFactory( request, context);
  assertNotNull( factory2, "factory found");
  assertEquals( "/module1", factory2.getConfig().getFactoryName(), "factory name" );

    // Check that factory are different
  assertEquals(factory1, factory2, "Same factory");
  }

  /**
   * Test I18nFactorySet.
   */
  @Test
  public void testI18FactorySet_A() {

     Locale locale = null;
     ComponentDefinition definition = null;
     org.apache.struts.tiles.xmlDefinition.DefinitionsFactory factory = null;

     Map<String, Object> properties = new HashMap<>();

     // Set the file name
     properties.put(I18nFactorySet.DEFINITIONS_CONFIG_PARAMETER_NAME,
                    "config/I18nFactorySet-A.xml");

     try {
         CustomI18nFactorySet i18nFactorySet = new CustomI18nFactorySet(context, properties);
         String defName = "A-DEFAULT";

         // Default Locale
         locale = new Locale("", "", "");
         factory = i18nFactorySet.createFactory(locale , request, context);
         assertNotNull(factory, "DefinitionsFactory is nullfor locale='" + print(locale) + "'");
         definition = factory.getDefinition(defName, request, context);
         assertNotNull(definition, "Definition '" + defName + "' Not Found for locale='" + print(locale) + "'");
         assertEquals(defName, definition.getName(), "Definition '" + defName + "' for locale='" + print(locale) + "'");

         // Variant Only
         locale = new Locale("", "", "XX");
         factory = i18nFactorySet.createFactory(locale , request, context);
         assertNotNull(factory, "DefinitionsFactory is null for locale='" + print(locale) + "'");
         definition = factory.getDefinition(defName, request, context);
         assertNotNull(definition, "Definition '" + defName + "' Not Found for locale='" + print(locale) + "'");
         assertEquals(defName, definition.getName(), "Definition '" + defName + "' for locale='" + print(locale) + "'");

         // No Language, Country & Variant Locale
         locale = new Locale("", "US", "XX");
         factory = i18nFactorySet.createFactory(locale , request, context);
         assertNotNull(factory, "DefinitionsFactory is null for locale='" + print(locale) + "'");
         definition = factory.getDefinition(defName, request, context);
         assertNotNull(definition, "Definition '" + defName + "' Not Found for locale='" + print(locale) + "'");
         assertEquals(defName, definition.getName(), "Definition '" + defName + "' for locale='" + print(locale) + "'");

         // Language & Country
         locale = new Locale("en", "US");
         factory = i18nFactorySet.createFactory(locale , request, context);
         assertNotNull(factory, "DefinitionsFactory is null for locale='" + print(locale) + "'");
         definition = factory.getDefinition(defName, request, context);
         assertNotNull(definition, "Definition '" + defName + "' Not Found for locale='" + print(locale) + "'");
         assertEquals(defName, definition.getName(), "Definition '" + defName + "' for locale='" + print(locale) + "'");

     } catch(Exception ex) {
         fail(ex.toString());
     }
  }


  /**
   * Test I18nFactorySet.
   */
  @Test
  public void testI18FactorySet_B() {

     Locale locale = null;
     ComponentDefinition definition = null;
     org.apache.struts.tiles.xmlDefinition.DefinitionsFactory factory = null;

     Map<String, Object> properties = new HashMap<>();

     // Set the file name
     properties.put(I18nFactorySet.DEFINITIONS_CONFIG_PARAMETER_NAME,
                    "config/I18nFactorySet-B.xml");

     try {

         CustomI18nFactorySet i18nFactorySet = new CustomI18nFactorySet(context, properties);
         String defName = null;

         // Default Locale
         locale = new Locale("", "", "");
         factory = i18nFactorySet.createFactory(locale , request, context);
         assertNotNull(factory, "1. DefinitionsFactory is nullfor locale='" + print(locale) + "'");
         defName = "B-DEFAULT";
         definition = factory.getDefinition(defName, request, context);
         assertNotNull(definition, "2. Definition '" + defName + "' Not Found for locale='" + print(locale) + "'");
         assertEquals(defName, definition.getName(), "3. Definition '" + defName + "' for locale='" + print(locale) + "'");

         // Variant Only
         locale = new Locale("", "", "XX");
         factory = i18nFactorySet.createFactory(locale , request, context);
         assertNotNull(factory, "4. DefinitionsFactory is null for locale='" + print(locale) + "'");
         defName = "B___XX";
         definition = factory.getDefinition(defName, request, context);
         assertNotNull(definition, "5. Definition '" + defName + "' Not Found for locale='" + print(locale) + "'");
         assertEquals(defName, definition.getName(), "6. Definition '" + defName + "' for locale='" + print(locale) + "'");
         defName = "B-DEFAULT";
         definition = factory.getDefinition(defName, request, context);
         assertNotNull(definition, "7. Definition '" + defName + "' Not Found for locale='" + print(locale) + "'");
         assertEquals(defName, definition.getName(), "8. Definition '" + defName + "' for locale='" + print(locale) + "'");

         // No Language, Country & Unknown Variant
         locale = new Locale("", "US", "XX");
         factory = i18nFactorySet.createFactory(locale , request, context);
         assertNotNull(factory, "9. DefinitionsFactory is null for locale='" + print(locale) + "'");
         defName = "B__US";
         definition = factory.getDefinition(defName, request, context);
         assertNotNull(definition, "10. Definition '" + defName + "' Not Found for locale='" + print(locale) + "'");
         assertEquals(defName, definition.getName(), "11. Definition '" + defName + "' for locale='" + print(locale) + "'");

         // Language & Country
         locale = new Locale("en", "US");
         factory = i18nFactorySet.createFactory(locale , request, context);
         assertNotNull(factory, "12. DefinitionsFactory is null for locale='" + print(locale) + "'");
         defName = "B_en_US";
         definition = factory.getDefinition(defName, request, context);
         assertNotNull(definition, "13. Definition '" + defName + "' Not Found for locale='" + print(locale) + "'");
         assertEquals(defName, definition.getName(), "14. Definition '" + defName + "' for locale='" + print(locale) + "'");

         // Language, Country & Unknown Variant
         locale = new Locale("en", "GB", "XX");
         factory = i18nFactorySet.createFactory(locale , request, context);
         assertNotNull(factory, "15. DefinitionsFactory is null for locale='" + print(locale) + "'");
         defName = "B_en_GB";
         definition = factory.getDefinition(defName, request, context);
         assertNotNull(definition, "16. Definition '" + defName + "' Not Found for locale='" + print(locale) + "'");
         assertEquals(defName, definition.getName(), "17. Definition '" + defName + "' for locale='" + print(locale) + "'");

         // Language, Unknown Country & Unknown Variant
         locale = new Locale("en", "FR", "XX");
         factory = i18nFactorySet.createFactory(locale , request, context);
         assertNotNull(factory, "18. DefinitionsFactory is null for locale='" + print(locale) + "'");
         defName = "B_en";
         definition = factory.getDefinition(defName, request, context);
         assertNotNull(definition, "19. Definition '" + defName + "' Not Found for locale='" + print(locale) + "'");
         assertEquals(defName, definition.getName(), "20. Definition '" + defName + "' for locale='" + print(locale) + "'");

     } catch(Exception ex) {
         fail(ex.toString());
     }

  }

  /**
   * String representation of a Locale. A bug in the
   * Locale.toString() method results in Locales with
   * just a variant being incorrectly displayed.
   */
  private String print(Locale locale) {

      return locale.getLanguage() + "_" +
                locale.getCountry() + "_" +
                locale.getVariant();
  }
}