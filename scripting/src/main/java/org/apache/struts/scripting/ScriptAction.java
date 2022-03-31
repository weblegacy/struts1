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

// util imports:
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

// io imports:
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

// logging imports:
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// struts imports:
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

// misc imports:
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;
import org.apache.bsf.util.IOUtils;


/**
 *  This Action uses scripts to perform its action. The scripting framework is
 *  Apache's Bean Scripting Framework which allows the scripts to be written
 *  many of the popular scripting languages including JavaScript, Perl, Python,
 *  and even VBA. <br />
 *  <br />
 *  To determine what script will be executed, the "parameter" attribute of the
 *  action mapping should contain the name of the script relative to the web
 *  application root directory (i.e. http://server/app). <br />
 *  <br />
 *  Before the script completes, the next ActionForward needs to be specified.
 *  This can be done one of two ways:
 *  <ol>
 *    <li> Set <code>struts.forwardName</code> to the name of the forward</li>
 *
 *    <li> Set <code>struts.forward</code> to the actual ActionForward object
 *    </li>
 *  </ol>
 *  A number of pre-defined variables are available to the script:
 *  <ul>
 *    <li> <code>request</code> - The HTTP request</li>
 *    <li> <code>response</code> - The HTTP response</li>
 *    <li> <code>session</code> - The session</li>
 *    <li> <code>application</code> - The servlet context</li>
 *    <li> <code>struts</code> - A grouping of all Struts-related objects</li>
 *
 *    <li> <code>log</code> - A logging instance</li>
 *  </ul>
 *  You can add your own variables by creating a BSFManagerFilter and
 *  configuring it in struts-scripting.properties:
 *  <ul>
 *    <li> <code>struts-scripting.filters.FILTER_NAME.class=FILTER_CLASS</code>
 *    - The class implementing BSFManagerFilter where FILTER_NAME is the name
 *    you are calling the filter.</li>
 *    <li> <code>
 *     struts-scripting.filters.FILTER_NAME.PROPERTY_NAME=PROPERTY_VALUE
 *      </code> - A property to be used by the filter.</li>
 *  </ul>
 *  <br />
 *  <br />
 *  To use other scripting engines other than BeanShell, create a file called
 *  <code>struts-scripting.properties</code> and add two properties for each
 *  engine:
 *  <ul>
 *    <li> <code>struts-scripting.engine.ENGINE_NAME.class</code> - The class of
 *    the BSF engine where ENGINE_NAME is the name you are calling the engine.
 *    </li>
 *    <li> <code>struts-scripting.engine.ENGINE_NAME.extensions</code> - A
 *    comma-delimited list of file extensions that will be used to identify the
 *    engine to use to execute the script.</li>
 *  </ul>
 *  This code was originally based off code from JPublish, but has since been
 *  almost completely rewritten.
 */
public class ScriptAction extends Action {

    /**  The logging instance. */
    protected static final Log LOG = LogFactory.getLog(ScriptAction.class);

    /**  The default path to the properties file. */
    protected static final String PROPS_PATH = "/struts-scripting.properties";

    /**  The base property for alternate BSF engines. */
    protected static final String ENGINE_BASE = "struts-scripting.engine.";

    /**  The base property for classes that put new variables in the context. */
    protected static final String FILTERS_BASE = "struts-scripting.filters.";

    /**  A list of initialized filters. */
    private static BSFManagerFilter[] filters = null;

    /**  Holds the "compiled" scripts and their information. */
    private Map scripts = new Hashtable();

    static {
        Properties props = new Properties();
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
        int pos = ENGINE_BASE.length();
        for (Enumeration e = props.propertyNames(); e.hasMoreElements();) {
            String name = (String) e.nextElement();
            if (name.startsWith(ENGINE_BASE) && name.endsWith(".class")) {
                String type = name.substring(pos, name.indexOf('.', pos));
                String cls = props.getProperty(name);
                String ext = props.getProperty(ENGINE_BASE + type
                         + ".extensions", "");
                String[] exts = split(ext, ",");
                if (LOG.isInfoEnabled()) {
                    LOG.info("Loading BSF engine name:" + type + " class:"
                             + cls + " ext:" + ext);
                }
                BSFManager.registerScriptingEngine(type, cls, exts);
            }
        }
        filters = loadFilters(props);
    }


    /**
     *  Executes the script.
     *
     *@param  mapping        The action mapping
     *@param  form           The action form
     *@param  request        The request object
     *@param  response       The response object
     *@return                The action forward
     *@exception  Exception  If something goes wrong
     */
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
             throws Exception {

        BSFManager bsfManager = new BSFManager();

        String scriptName = null;
        try {
            scriptName = parseScriptName(mapping.getParameter(), bsfManager);
        } catch (Exception ex) {
            LOG.error("Unable to parse " + mapping.getParameter(), ex);
            throw new Exception("Unable to parse " + mapping.getParameter(), ex);
        }
        if (scriptName == null) {
            LOG.error("No script specified in the parameter attribute");
            throw new Exception("No script specified");
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Executing script: " + scriptName);
        }

        HttpSession session = request.getSession();
        ServletContext application = getServlet().getServletContext();

        Script script = loadScript(scriptName, application);

        bsfManager.declareBean("request", request,
                HttpServletRequest.class);

        bsfManager.declareBean("response", response,
                HttpServletResponse.class);

        if (session == null) {
            LOG.debug("HTTP session is null");
        } else {
            bsfManager.declareBean("session", session, HttpSession.class);
        }

        bsfManager.declareBean("application", application,
                ServletContext.class);

        bsfManager.declareBean("log", LOG, Log.class);
        StrutsInfo struts = new StrutsInfo(this, mapping, form,
                getResources(request));
        bsfManager.declareBean("struts", struts, StrutsInfo.class);

        for (int x = 0; x < filters.length; x++) {
            filters[x].apply(bsfManager);
        }

        bsfManager.exec(script.lang, script.file.getCanonicalPath(), 0, 0,
                script.string);

        ActionForward af = struts.getForward();
        return af;
    }


    /**
     *  Parses the script name and puts any url parameters in the context.
     *
     *@param  url            The script url consisting of a path and optional
     *      parameters
     *@param  manager        The BSF manager to declare new parameters in
     *@return                The name of the script to execute
     *@exception  Exception  If something goes wrong
     */
    protected String parseScriptName(String url, BSFManager manager)
             throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Parsing " + url);
        }
        String name = null;
        if (url != null) {
            String[] parsed = split(url, "?");
            name = parsed[0];
            if (parsed.length == 2) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Found a query string");
                }
                String[] args = split(parsed[1], "&");
                for (int x = 0; x < args.length; x++) {
                    String[] param = split(args[x], "=");
                    Object o = manager.lookupBean(param[0]);
                    if (o != null) {
                        LOG.warn("BSF variable " + param[0]
                                 + " already exists");
                        param[0] = "_" + param[0];
                    }
                    manager.declareBean(param[0], param[1], String.class);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Registering param " + param[0]
                                 + " with value " + param[1]);
                    }
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("No query string:" + parsed.length);
                }
            }
        }
        return name;
    }


    /**
     *  Loads the script from cache if possible. Reloads if the script has been
     *  recently modified.
     *
     *@param  name     The name of the script
     *@param  context  The servlet context
     *@return          The script object
     */
    protected Script loadScript(String name, ServletContext context) {

        Script script = (Script) scripts.get(name);
        if (script == null) {
            script = new Script();
            script.file = new File(context.getRealPath(name));
            try {
                script.lang =
                        BSFManager.getLangFromFilename(script.file.getName());
            } catch (BSFException ex) {
                LOG.warn(ex, ex);
            }
        }

        boolean reloadScript = false;
        long scriptLastModified = script.file.lastModified();
        if (scriptLastModified > script.timeLastLoaded) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Loading updated or new script: "
                        + script.file.getName());
            }
            reloadScript = true;
        }

        if (reloadScript || script.string == null) {
            synchronized (this) {
                script.timeLastLoaded = System.currentTimeMillis();
                FileReader reader = null;
                try {
                    reader = new FileReader(script.file);
                    script.string = IOUtils.getStringFromReader(reader);
                } catch (IOException ex) {
                    LOG.error("Unable to load script: " + script.file, ex);
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException ex) {
                            LOG.debug(ex, ex);
                        }
                    }
                }
            }
        }

        return script;
    }


    /**
     *  Loads and initializes the filters.
     *
     *@param  props  The properties defining the filters
     *@return        An array of the loaded filters
     */
    protected static BSFManagerFilter[] loadFilters(Properties props) {
        ArrayList list = new ArrayList();
        for (Enumeration e = props.propertyNames(); e.hasMoreElements();) {
            String prop = (String) e.nextElement();
            if (prop.startsWith(FILTERS_BASE) && prop.endsWith("class")) {
                String type = prop.substring(FILTERS_BASE.length(),
                        prop.indexOf(".", FILTERS_BASE.length()));
                String claz = props.getProperty(prop);
                try {
                    Class cls = Class.forName(claz);
                    BSFManagerFilter f = (BSFManagerFilter) cls.newInstance();
                    f.init(type, props);
                    list.add(f);
                    if (LOG.isInfoEnabled()) {
                        LOG.info("Loaded " + type + " filter: " + claz);
                    }
                } catch (Exception ex) {
                    LOG.error("Unable to load " + type + " filter: " + claz);
                }
            }
        }
        BSFManagerFilter[] filters = new BSFManagerFilter[list.size()];
        filters = (BSFManagerFilter[]) list.toArray(filters);
        return filters;
    }


    /**
     *  Splits a line with the given delimiter.
     *
     *@param  line       The line to split
     *@param  delimiter  The string to split with
     *@return            An array of substrings
     */
    protected static String[] split(String line, String delimiter) {
        if (line == null || "".equals(line)) {
            return new String[]{};
        }

        List lst = new ArrayList();
        for (Enumeration e = new StringTokenizer(line, delimiter);
            e.hasMoreElements();) {
            lst.add(e.nextElement());
        }
        String[] ret = new String[lst.size()];
        return (String[]) lst.toArray(ret);
    }


    // These methods seem necessary as some scripting engines are not able to
    // access Action's protected methods.  Ugly? yes... any suggestions?

    /**
     *  Saves a token.
     *
     *@param  req  The request object
     */
    public void saveToken(HttpServletRequest req) {
        super.saveToken(req);
    }


    /**
     *  Checks to see if the request is cancelled.
     *
     *@param  req  The request object
     *@return      True if cancelled
     */
    public boolean isCancelled(HttpServletRequest req) {
        return super.isCancelled(req);
    }


    /**
     *  Checks to see if the token is valid.
     *
     *@param  req  The request object
     *@return      True if valid
     */
    public boolean isTokenValid(HttpServletRequest req) {
        return super.isTokenValid(req);
    }


    /**
     *  Resets the token.
     *
     *@param  req  The request object
     */
    public void resetToken(HttpServletRequest req) {
        super.resetToken(req);
    }


    /**
     *  Gets the locale.
     *
     *@param  req  The request object
     *@return      The locale value
     */
    public Locale getLocale(HttpServletRequest req) {
        return super.getLocale(req);
    }


    /**
     *  Saves the messages to the request.
     *
     *@param  req  The request object
     *@param  mes  The action messages
     */
    public void saveMessages(HttpServletRequest req, ActionMessages mes) {
        super.saveMessages(req, mes);
    }


    /**
     *  Saves the errors to the request.
     *
     *@param  req    The request object
     *@param  errs   The action errors
     *@deprecated    Use saveErrors(HttpServletRequest, ActionMessages) instead.
     *      This will be removed after Struts 1.2.
     */
    public void saveErrors(HttpServletRequest req, ActionErrors errs) {
        super.saveErrors(req, errs);
    }


    /**  Represents a saved script. */
    class Script {

        /**  The script file. */
        public File file;

        /**  The language the script is in. */
        public String lang = null;

        /**  The time when the script was last used. */
        public long timeLastLoaded = 0;

        /**  The contents of the script file. */
        public String string = null;
    }
}
