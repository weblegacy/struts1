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
package org.apache.struts.extras.plugins;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import jakarta.servlet.ServletException;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * <p>An implementation of <code>PlugIn</code> which can be configured to
 * instantiate a graph of objects using the Commons Digester and place the
 * root object of that graph into the Application context.</p>
 *
 * @version $Rev$
 * @see org.apache.struts.action.PlugIn
 * @since Struts 1.2
 */
public class DigestingPlugIn implements PlugIn {

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(DigestingPlugIn.class);

    protected static final String SOURCE_CLASSPATH = "classpath";
    protected static final String SOURCE_FILE = "file";
    protected static final String SOURCE_SERVLET = "servlet";
    protected String configPath = null;
    protected String configSource = SOURCE_SERVLET;
    protected String digesterPath = null;
    protected String digesterSource = SOURCE_SERVLET;
    protected String key = null;
    protected ModuleConfig moduleConfig = null;
    protected String rulesets = null;
    protected ActionServlet servlet = null;
    protected boolean push = false;

    /**
     * Constructor for DigestingPlugIn.
     */
    public DigestingPlugIn() {
        super();
    }

    /**
     * Receive notification that our owning module is being shut down.
     */
    public void destroy() {
        this.servlet = null;
        this.moduleConfig = null;
    }

    /**
     * <p>Initialize a <code>Digester</code> and use it to parse a
     * configuration file, resulting in a root object which will be placed
     * into the ServletContext.</p>
     *
     * @param servlet ActionServlet that is managing all the modules in this
     *                web application
     * @param config  ModuleConfig for the module with which this plug-in is
     *                associated
     * @throws ServletException if this <code>PlugIn</code> cannot be
     *                          successfully initialized
     */
    public void init(ActionServlet servlet, ModuleConfig config)
        throws ServletException {
        this.servlet = servlet;
        this.moduleConfig = config;

        Object obj = null;

        Digester digester = this.initializeDigester();

        if (this.push) {
            log.debug("push == true; pushing plugin onto digester stack");
            digester.push(this);
        }

        try {
            log.debug("XML data file: [path: {}, source: {}]",
                this.configPath, this.configSource);

            URL configURL =
                this.getConfigURL(this.configPath, this.configSource);

            if (configURL == null) {
                throw new ServletException(
                    "Unable to locate XML data file at [path: "
                    + this.configPath + ", source: " + this.configSource + "]");
            }

            URLConnection conn = configURL.openConnection();

            conn.setUseCaches(false);
            conn.connect();
            obj = digester.parse(conn.getInputStream());
        } catch (IOException e) {
            // TODO Internationalize msg
            log.error("Exception processing config", e);
            throw new ServletException(e);
        } catch (SAXException e) {
            // TODO Internationalize msg
            log.error("Exception processing config", e);
            throw new ServletException(e);
        }

        this.storeGeneratedObject(obj);
    }

    /**
     * Initialize the <code>Digester</code> which will be used to process the
     * main configuration.
     *
     * @return a Digester, ready to use.
     * @throws ServletException
     */
    protected Digester initializeDigester()
        throws ServletException {
        Digester digester = null;

        if ((this.digesterPath != null) && (this.digesterSource != null)) {
            try {
                log.debug("Initialize digester from XML [path: {}; source: {}]",
                    this.digesterPath, this.digesterSource);
                digester =
                    this.digesterFromXml(this.digesterPath, this.digesterSource);
            } catch (IOException e) {
                // TODO Internationalize msg
                log.error("Exception instantiating digester from XML", e);
                throw new ServletException(e);
            }
        } else {
            log.debug("No XML rules for digester; call newDigesterInstance()");
            digester = this.newDigesterInstance();
        }

        this.applyRuleSets(digester);

        return digester;
    }

    /**
     * <p>Instantiate a <code>Digester</code>.</p> <p>Subclasses may wish to
     * override this to provide a subclass of Digester, or to configure the
     * Digester using object methods.</p>
     *
     * @return a basic instance of <code>org.apache.commons.digester.Digester</code>
     */
    protected Digester newDigesterInstance() {
        return new Digester();
    }

    /**
     * <p>Instantiate a Digester from an XML input stream using the Commons
     * <code>DigesterLoader</code>.</p>
     *
     * @param path   the path to the digester rules XML to be found using
     *               <code>source</code>
     * @param source a string indicating the lookup method to be used with
     *               <code>path</code>
     * @return a configured Digester
     * @throws FileNotFoundException
     * @throws MalformedURLException
     * @see #getConfigURL(String, String)
     */
    protected Digester digesterFromXml(String path, String source)
        throws IOException {
        URL configURL = this.getConfigURL(path, source);

        if (configURL == null) {
            throw new NullPointerException("No resource '" + path
                + "' found in '" + source + "'");
        }

//        Digester3:
//        RulesModule rules = new FromXmlRulesModule() {
//            @Override
//            protected void loadRules() {
//                loadXMLRules(configURL);
//            }
//        };
//        return DigesterLoader.newLoader(rules).newDigester();
        return DigesterLoader.createDigester(configURL);
    }

    /**
     * Instantiate any <code>RuleSet</code> classes defined in the
     * <code>rulesets</code> property and use them to add rules to our
     * <code>Digester</code>.
     *
     * @param digester the Digester instance to add RuleSet objects to.
     * @throws ServletException
     */
    protected void applyRuleSets(Digester digester)
        throws ServletException {
        if ((this.rulesets == null) || (this.rulesets.trim().length() == 0)) {
            return;
        }

        rulesets = rulesets.trim();

        String ruleSet = null;

        while (rulesets.length() > 0) {
            int comma = rulesets.indexOf(",");

            if (comma < 0) {
                ruleSet = rulesets.trim();
                rulesets = "";
            } else {
                ruleSet = rulesets.substring(0, comma).trim();
                rulesets = rulesets.substring(comma + 1).trim();
            }

            // TODO Internationalize msg
            log.debug("Configuring custom Digester Ruleset of type {}"
                , ruleSet);

            try {
                RuleSet instance =
                    (RuleSet) RequestUtils.applicationInstance(ruleSet);

                digester.addRuleSet(instance);
            } catch (Exception e) {
                // TODO Internationalize msg
                log.error("Exception configuring custom Digester RuleSet", e);
                throw new ServletException(e);
            }
        }
    }

    /**
     * <p>Look up a resource path using one of a set of known path resolution
     * mechanisms and return a URL to the resource.</p>
     *
     * @param path   a String which is meaningful to one of the known
     *               resolution mechanisms.
     * @param source one of the known path resolution mechanisms:
     *
     *               <ul>
     *
     *               <li>file - the path is a fully-qualified filesystem
     *               path.</li>
     *
     *               <li>servlet - the path is a servlet-context relative
     *               path.</li>
     *
     *               <li>classpath - the path is a classpath-relative
     *               path.</li>
     *
     *               </ul>
     * @return a URL pointing to the given path in the given mechanism.
     * @throws java.io.FileNotFoundException
     * @throws java.net.MalformedURLException
     */
    protected URL getConfigURL(String path, String source)
        throws IOException {
        if (SOURCE_CLASSPATH.equals(source)) {
            return this.getClassPathURL(path);
        }

        if (SOURCE_FILE.equals(source)) {
            return this.getFileURL(path);
        }

        if (SOURCE_SERVLET.equals(source)) {
            return this.getServletContextURL(path);
        }

        // TODO Internationalize msg
        throw new IllegalArgumentException("ConfigSource " + source
            + " is not recognized");
    }

    /**
     * Given a string, return a URL to a classpath resource of that name.
     *
     * @param path a Classpath-relative string identifying a resource.
     * @return a URL identifying the resource on the classpath. TODO Do we
     *         need to be smarter about ClassLoaders?
     */
    protected URL getClassPathURL(String path) {
        return getClass().getClassLoader().getResource(path);
    }

    /**
     * Given a string, return a URL to a Servlet Context resource of that
     * name.
     *
     * @param path a Classpath-relative string identifying a resource.
     * @return a URL identifying the resource in the Servlet Context
     * @throws MalformedURLException
     */
    protected URL getServletContextURL(String path)
        throws IOException {
        return this.servlet.getServletContext().getResource(path);
    }

    /**
     * Given a string, return a URL to a Filesystem resource of that name.
     *
     * @param path a path to a file.
     * @return a URL identifying the resource in the in the file system.
     * @throws MalformedURLException
     * @throws FileNotFoundException
     */
    protected URL getFileURL(String path)
        throws IOException {
        File file = new File(path);

        return file.toURI().toURL();
    }

    /**
     * @param configPath the path to configuration information for this
     *                   PlugIn.
     * @see #configSource
     */
    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    /**
     * @return the configPath property
     * @see #configSource
     */
    public String getConfigPath() {
        return configPath;
    }

    /**
     * Set the source of the config file.  Should be one of the following:
     * <ul> <li> "classpath" - indicates that the configPath will be resolved
     * by the ClassLoader. </li> <li> "file" - indicates that the configPath
     * is a fully-qualified filesystem path. </li> <li> "servlet" - indicates
     * that the configPath will be found by the ServletContext. </li> </ul>
     *
     * @param configSource the source (lookup method) for the config file.
     * @see #configPath
     */
    public void setConfigSource(String configSource) {
        this.configSource = configSource;
    }

    /**
     * @return the string describing which access method should be used to
     *         resolve configPath.
     * @see #configPath
     */
    public String getConfigSource() {
        return configSource;
    }

    /**
     * This method is called after the Digester runs to store the generated
     * object somewhere.  This implementation places the given object into the
     * ServletContext under the attribute name as defined in
     * <code>key</code>.
     *
     * @param obj The object to save.
     */
    protected void storeGeneratedObject(Object obj) {
        log.debug("Put [{}] into application context [key:{}]",
            obj, this.key);
        this.servlet.getServletContext().setAttribute(this.getKey(), obj);
    }

    /**
     * @param key The ServletContext attribute name to store the generated
     *            object under.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return The ServletContext attribute name the generated object is
     *         stored under.
     */
    public String getKey() {
        return key;
    }

    /**
     * <p>A comma-delimited list of one or more classes which implement
     * <code>org.apache.commons.digester.RuleSet</code>. (Optional)</p>
     */
    public void setRulesets(String ruleSets) {
        this.rulesets = ruleSets;
    }

    /**
     * @return The configured list of <code>RuleSet</code> classes.
     */
    public String getRulesets() {
        return this.rulesets;
    }

    /**
     * <p>The path to a Digester XML configuration file, relative to the
     * <code>digesterSource</code> property. (Optional)</p>
     *
     * @see #digesterSource
     * @see #getConfigURL(String, String)
     */
    public void setDigesterPath(String digesterPath) {
        this.digesterPath = digesterPath;
    }

    /**
     * @return the configured path to a Digester XML config file, or null.
     * @see #digesterSource
     * @see #getConfigURL(String, String)
     */
    public String getDigesterPath() {
        return digesterPath;
    }

    /**
     * <p>The lookup mechanism to be used to resolve <code>digesterPath</code>
     * (optional). </p>
     *
     * @param digesterSource
     * @see #getConfigURL(String, String)
     */
    public void setDigesterSource(String digesterSource) {
        this.digesterSource = digesterSource;
    }

    /**
     * @return the configured lookup mechanism for resolving
     *         <code>digesterPath</code>.
     * @see #getConfigURL(String, String)
     */
    public String getDigesterSource() {
        return this.digesterSource;
    }

    /**
     * <p>If set to <code>true</code>, this PlugIn will be pushed onto the
     * Digester stack before the digester <code>parse</code> method is
     * called.</p> <p>Defaults to <code>false</code></p>
     *
     * @param push
     */
    public void setPush(boolean push) {
        this.push = push;
    }

    /**
     * @return Whether or not this <code>PlugIn</code> instance will be pushed
     *         onto the <code>Digester</code> stack before
     *         <code>digester.parse()</code> is called.
     */
    public boolean getPush() {
        return this.push;
    }
}