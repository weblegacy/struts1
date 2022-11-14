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
package org.apache.struts.scripting;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This Action uses scripts to perform its action. The scripting framework is
 * Bean Scripting Framework 3.0 (JSR 223) which allows the scripts to be written
 * many of the popular scripting languages including JavaScript, Perl, Python,
 * and even VBA.
 *
 * <p>To determine what script will be executed, the "parameter" attribute of the
 * action mapping should contain the name of the script relative to the web
 * application root directory (i.e. https://server/app).</p>
 *
 * <p>Before the script completes, the next {@code ActionForward} needs to be
 * specified. This can be done one of two ways:</p>
 *
 * <ol>
 *   <li>Set {@code struts.forwardName} to the name of the forward</li>
 *   <li>Set {@code struts.forward} to the actual ActionForward object</li>
 *  </ol>
 *
 * <p>A number of pre-defined variables are available to the script:</p>
 *
 * <ul>
 *   <li> {@code request} - The HTTP request</li>
 *   <li> {@code response} - The HTTP response</li>
 *   <li> {@code session} - The session</li>
 *   <li> {@code application} - The servlet context</li>
 *   <li> {@code struts} - A grouping of all Struts-related objects</li>
 *   <li> {@code log} - A logging instance</li>
 * </ul>
 *
 * <p>You can add your own variables by creating a {@link ScriptContextFilter}
 * and configuring it in struts-scripting.properties:</p>
 *
 * <ul>
 *   <li>{@code struts-scripting.filters.FILTER_NAME.class=FILTER_CLASS}
 *       - The class implementing {@code ScriptContextFilter} where FILTER_NAME
 *         is the name you are calling the filter.</li>
 *   <li> {@code struts-scripting.filters.FILTER_NAME.PROPERTY_NAME=PROPERTY_VALUE}
 *       - A property to be used by the filter.</li>
 * </ul>
 *
 * <p>To use other scripting engines, add them to the classpath.</p>
 *
 * <p>To register more extensions to a scripting engine, create a file called
 * {@code struts-scripting.properties} and add one propertie for each
 * engine:</p>
 *
 * <ul>
 *    <li>{@code struts-scripting.engine.ENGINE_NAME.extensions}
 *        - A comma-delimited list of file extensions that will be used to
 *        identify the engine to use to execute the script.</li>
 * </ul>
 *
 * <p>This code was originally based off code from JPublish, but has since been
 * almost completely rewritten.</p>
 */
public class ScriptAction extends Action {
    private static final long serialVersionUID = -383996253054413439L;

    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(ScriptAction.class);

    /** The entry-point to JSR223-scripting */
    private static final ScriptEngineManager SCRIPT_ENGINE_MANAGER = new ScriptEngineManager();

    /**  The default path to the properties file. */
    protected static final String PROPS_PATH = "/struts-scripting.properties";

    /**  The base property for alternate BSF engines. */
    protected static final String ENGINE_BASE = "struts-scripting.engine.";

    /**  The base property for classes that put new variables in the context. */
    protected static final String FILTERS_BASE = "struts-scripting.filters.";

    /**  A list of initialized filters. */
    private static ScriptContextFilter[] filters = null;

    /**  Holds the "compiled" scripts and their information. */
    private ConcurrentHashMap<String, Script> scripts = new ConcurrentHashMap<>();

    static {
        final Properties props = new Properties();
        try {
            InputStream in =
                    ScriptAction.class.getClassLoader()
                    .getResourceAsStream(PROPS_PATH);
            if (in == null) {
                in =
                        ScriptAction.class.getClassLoader().getResourceAsStream(
                        "/struts-bsf.properties");
                if (in != null) {
                    LOG.warn("The struts-bsf.properties file has been "
                            + "deprecated.  Please use "
                            + "struts-scripting.properties instead.");
                } else {
                    LOG.warn("struts-scripting.properties not found, using "
                             + "default engine mappings.");
                }
            }

            if (in != null) {
                props.load(in);
            }
        } catch (Exception ex) {
            LOG.warn("Unable to load struts-scripting.properties, using "
                     + " default engine mappings.");
        }

        final List<ScriptEngineFactory> allSefs = SCRIPT_ENGINE_MANAGER.getEngineFactories();

        final int pos = ENGINE_BASE.length();
        for (Enumeration<?> e = props.propertyNames(); e.hasMoreElements();) {
            final String propName = e.nextElement().toString();
            if (propName.startsWith(ENGINE_BASE) && propName.endsWith(".extensions")) {
                final String name = propName.substring(pos, propName.indexOf('.', pos));

                ScriptEngineFactory[] sefs =
                        allSefs.stream().filter((sef) -> sef.getNames().contains(name))
                        .toArray(ScriptEngineFactory[]::new);

                if (sefs.length == 0) {
                    LOG.warn("No ScriptEngineFactory found - name: '{}'", name);
                    continue;
                } else if (sefs.length > 1) {
                    LOG.warn("More than one ScriptEngineFactory found, taking the first one - name: '{}'", name);
                }

                final ScriptEngineFactory sef = sefs[0];
                LOG.info("Found ScriptingEngineFactory - name: {} language: {} {}",
                    name, sef.getLanguageName(), sef.getLanguageVersion());

                final String propValue = props.getProperty(propName).trim();
                final String[] exts = propValue.split(",");
                if (exts.length == 0) {
                    continue;
                }

                LOG.atInfo().log(() -> {
                    final StringBuilder sb = new StringBuilder();
                    sb.append("Registering extension");
                    if (exts.length > 1) {
                        sb.append('s');
                    }
                    sb
                        .append(" to ScriptingEngineFactory - name: '")
                        .append(name)
                        .append("' ext");

                    if (exts.length > 1) {
                        sb.append('s');
                    }
                    sb.append(": ");
                    if (exts.length == 1) {
                        sb
                            .append('\'')
                            .append(exts[0])
                            .append('\'');
                    } else {
                        sb.append(Arrays.toString(exts));
                    }
                    return sb.toString();
                });

                for (String ext : exts) {
                    ext = ext.trim();
                    if (!ext.isEmpty()) {
                        SCRIPT_ENGINE_MANAGER.registerEngineExtension(ext, sef);
                    }
                }
            }
        }
        filters = loadFilters(props);
    }


    /**
     * Executes the script.
     *
     * @param  mapping  The action mapping
     * @param  form     The action form
     * @param  request  The request object
     * @param  response The response object
     *
     * @return The action forward
     *
     * @throws Exception If something goes wrong
     */
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
             throws Exception {

        final Map<String, String> params = new LinkedHashMap<>();
        final String scriptName = parseScriptName(mapping.getParameter(), params);
        if (scriptName == null) {
            LOG.error("No script specified in the parameter attribute");
            throw new Exception("No script specified");
        }

        LOG.debug("Executing script: {}", scriptName);

        HttpSession session = request.getSession();
        ServletContext application = getServlet().getServletContext();

        Script script = loadScript(scriptName, application);

        Bindings bindings = script.getBindings();
        bindings.putAll(params);

        bindings.put("request", request);

        bindings.put("response", response);

        if (session == null) {
            LOG.debug("HTTP session is null");
        } else {
            bindings.put("session", session);
        }

        bindings.put("application", application);

        bindings.put("log", LOG);
        StrutsInfo struts = new StrutsInfo(this, mapping, form,
                getResources(request));
        bindings.put("struts", struts);

        final ScriptContext scriptContext = script.scriptEngine.getContext();
        for (ScriptContextFilter filter : filters) {
            filter.apply(scriptContext);
        }

        script.eval();

        ActionForward af = struts.getForward();
        return af;
    }


    /**
     * Parses the script name and puts any URL parameters in
     * the context.
     *
     * @param url The script URL consisting of a path and
     *     optional parameters
     * @param params A parameter-map to declare new parameters in
     *
     * @return The name of the script to execute
     */
    protected String parseScriptName(String url, Map<String, String> params) {

        LOG.debug("Parsing {}", url);

        if (url == null) {
            return null;
        }

        final String[] parsed = url.split("\\?", 2);

        if (parsed.length == 0) {
            return null;
        }

        if (parsed.length == 1) {
            LOG.debug("No query string: {}", parsed[0]);
            return parsed[0];
        }

        LOG.debug("Found a query string");

        final String[] args = parsed[1].split("&");
        for (String arg : args) {
            final int i = arg.indexOf('=');
            String key = urlDecode(i > 0 ? arg.substring(0, i) : arg);

            while (params.containsKey(key)) {
                LOG.warn("Script variable {} already exists", key);
                key = "_" + key;
            }

            final String value = i > 0 && arg.length() > i + 1
                    ? urlDecode(arg.substring(i + 1))
                    : null;

            params.put(key, value);
            LOG.debug("Registering param {} with value {}",
                key, value);
        }

        return parsed[0];
    }

    /**
     * Decodes a {@code application/x-www-form-urlencoded} string
     * using a the UTF-8-encoding scheme.
     *
     * @param s the {@code String} to decode
     *
     * @return the newly decoded {@code String}
     *
     * @see URLDecoder#decode(java.lang.String, java.lang.String)
     */
    private String urlDecode(final String s) {
        if (s == null) {
            return null;
        }

        try {
            return URLDecoder.decode(s, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            // Should never thrown
            LOG.error("URL-Decode: ", e);
        }

        return null;
    }


    /**
     *  Loads the script from cache if possible. Reloads if the script has been
     *  recently modified.
     *
     * @param  name    The name of the script
     * @param  context The servlet context
     *
     * @return         The script object
     *
     * @throws IOException if an I/O error occurs
     * @throws ScriptException if compilation fails
     */
    protected Script loadScript(final String name, final ServletContext context)
            throws IOException, ScriptException {

        final Script script = scripts.compute(name, (key, oldValue) -> {
            if (oldValue == null) {
                return new Script(SCRIPT_ENGINE_MANAGER, context, key);
            }

            oldValue.checkNewContent();
            return oldValue;
        });

        try {
            script.checkExceptions();
        } catch (IOException e) {
            LOG.error("Unable to load script: {}", script.name, e);
            throw e;
        } catch (ScriptException e) {
            LOG.error("Unable to compile script: {}", script.name, e);
            throw e;
        }
        return script;
    }


    /**
     *  Loads and initializes the filters.
     *
     *@param  props  The properties defining the filters
     *@return        An array of the loaded filters
     */
    protected static ScriptContextFilter[] loadFilters(Properties props) {
        ArrayList<ScriptContextFilter> list = new ArrayList<>();
        for (Enumeration<?> e = props.propertyNames(); e.hasMoreElements();) {
            final String propName = e.nextElement().toString().trim();
            if (propName.startsWith(FILTERS_BASE) && propName.endsWith("class")) {
                String name = propName.substring(FILTERS_BASE.length(),
                        propName.indexOf(".", FILTERS_BASE.length()));
                String clazz = props.getProperty(propName).trim();
                try {
                    Class<? extends ScriptContextFilter> cls =
                            Class.forName(clazz).asSubclass(ScriptContextFilter.class);
                    ScriptContextFilter f = cls.getDeclaredConstructor().newInstance();
                    f.init(name, props);
                    list.add(f);
                    LOG.info("Loaded {} filter: {}", name, clazz);
                } catch (Exception ex) {
                    LOG.error("Unable to load {} filter: {}", name, clazz);
                }
            }
        }
        return list.toArray(new ScriptContextFilter[0]);
    }


    // These methods seem necessary as some scripting engines are not able to
    // access Action's protected methods.  Ugly? yes... any suggestions?

    /**
     * Saves a token.
     *
     * @param  req  The request object
     */
    public void saveToken(HttpServletRequest req) {
        super.saveToken(req);
    }

    /**
     * Checks to see if the request is cancelled.
     *
     * @param  req  The request object
     * @return True if cancelled
     */
    public boolean isCancelled(HttpServletRequest req) {
        return super.isCancelled(req);
    }

    /**
     * Checks to see if the token is valid.
     *
     * @param  req  The request object
     * @return True if valid
     */
    public boolean isTokenValid(HttpServletRequest req) {
        return super.isTokenValid(req);
    }

    /**
     * Resets the token.
     *
     * @param  req  The request object
     */
    public void resetToken(HttpServletRequest req) {
        super.resetToken(req);
    }

    /**
     * Gets the locale.
     *
     * @param  req  The request object
     * @return The locale value
     */
    public Locale getLocale(HttpServletRequest req) {
        return super.getLocale(req);
    }

    /**
     * Saves the messages to the request.
     *
     * @param  req  The request object
     * @param  mes  The action messages
     */
    public void saveMessages(HttpServletRequest req, ActionMessages mes) {
        super.saveMessages(req, mes);
    }

    /**
     * Saves the errors to the request.
     *
     * @param  req    The request object
     * @param  errs   The action errors
     *
     * @deprecated Use saveErrors(HttpServletRequest, ActionMessages) instead.
     *     This will be removed after Struts 1.2.
     */
    @Deprecated
    public void saveErrors(HttpServletRequest req, ActionErrors errs) {
        super.saveErrors(req, errs);
    }
}