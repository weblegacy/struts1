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


package org.apache.struts.webapp.example2.plugin;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import jakarta.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.apps.mailreader.dao.impl.memory.MemoryUserDatabase;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.webapp.example2.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p><strong>MemoryDatabasePlugIn</strong> initializes and finalizes the
 * persistent storage of User and Subscription information for the Struts
 * Demonstration Application, using an in-memory database backed by an
 * XML file.</p>
 *
 * <p><strong>IMPLEMENTATION WARNING</strong> - If this web application is run
 * from a WAR file, or in another environment where reading and writing of the
 * web application resource is impossible, the initial contents will be copied
 * to a file in the web application temporary directory provided by the
 * container.  This is for demonstration purposes only - you should
 * <strong>NOT</strong> assume that files written here will survive a restart
 * of your servlet container.</p>
 *
 * @author Craig R. McClanahan
 * @version $Rev$ $Date$
 */

public final class MemoryDatabasePlugIn implements PlugIn {


    // ----------------------------------------------------- Instance Variables


    /**
     * The {@link MemoryUserDatabase} object we construct and make available.
     */
    private MemoryUserDatabase database = null;


    /**
     * The {@code Log} instance for this class.
     */
    private final static Logger LOG =
        LoggerFactory.getLogger(MemoryDatabasePlugIn.class);


    /**
     * The {@link ActionServlet} owning this application.
     */
    private ActionServlet servlet = null;


    // ------------------------------------------------------------- Properties


    /**
     * The web application resource path of our persistent database
     * storage file.
     */
    private String pathname = "/WEB-INF/database.xml";

    public String getPathname() {
        return (this.pathname);
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }


    // --------------------------------------------------------- PlugIn Methods


    /**
     * Gracefully shut down this database, releasing any resources
     * that were allocated at initialization.
     */
    public void destroy() {

        LOG.info("Finalizing memory database plug in");

        if (database != null) {
            try {
                database.close();
            } catch (Exception e) {
                LOG.error("Closing memory database", e);
            }
        }

    servlet.getServletContext().removeAttribute(Constants.DATABASE_KEY);
        database = null;
        servlet = null;
        database = null;

    }


    /**
     * Initialize and load our initial database from persistent storage.
     *
     * @param servlet The ActionServlet for this web application
     * @param config The ApplicationConfig for our owning module
     *
     * @exception ServletException if we cannot configure ourselves correctly
     */
    public void init(ActionServlet servlet, ModuleConfig config)
        throws ServletException {

        LOG.info("Initializing memory database plug in from '{}'",
            pathname);

        // Remember our associated configuration and servlet
        this.servlet = servlet;

        // Construct a new database and make it available
        database = new MemoryUserDatabase();
        try {
            String path = calculatePath();
            LOG.debug(" Loading database from '{}'", path);
            database.setPathname(path);
            database.open();
        } catch (Exception e) {
            LOG.error("Opening memory database", e);
            throw new ServletException("Cannot load database from '" +
                                       pathname + "'", e);
        }

        // Make the initialized database available
        servlet.getServletContext().setAttribute(Constants.DATABASE_KEY,
                                                 database);

        // Setup and cache other required data
        setupCache(servlet, config);

    }


    // --------------------------------------------------------- Public Methods


    // ------------------------------------------------------ Protected Methods


    /**
     * <p>Cache commonly required data as servlet context attributes.</p>
     *
     * @param servlet The <code>ActionServlet</code> instance running
     *  this webapp
     * @param config The <code>ModuleConfig</code> for this application module
     */
    protected void setupCache(ActionServlet servlet, ModuleConfig config) {

        // Set up list of server types under "serverTypes"
        ArrayList<LabelValueBean> serverTypes = new ArrayList<>();
        serverTypes.add(new LabelValueBean("IMAP Protocol", "imap"));
        serverTypes.add(new LabelValueBean("POP3 Protocol", "pop3"));
        servlet.getServletContext().setAttribute("serverTypes", serverTypes);

    }




    // -------------------------------------------------------- Private Methods


    /**
     * Calculate and return an absolute pathname to the XML file to contain
     * our persistent storage information.
     *
     * @exception Exception if an input/output error occurs
     */
    private String calculatePath() throws Exception {

        // Can we access the database via file I/O?
        String path = servlet.getServletContext().getRealPath(pathname);
        if (path != null) {
            return (path);
        }

        // Does a copy of this file already exist in our temporary directory
        File dir = (File)
            servlet.getServletContext().getAttribute
            ("jakarta.servlet.context.tempdir");
        File file = new File(dir, "struts-example-database.xml");
        if (file.exists()) {
            return (file.getAbsolutePath());
        }

        // Copy the static resource to a temporary file and return its path
        InputStream is =
            servlet.getServletContext().getResourceAsStream(pathname);
        BufferedInputStream bis = new BufferedInputStream(is, 1024);
        FileOutputStream os =
            new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        byte buffer[] = new byte[1024];
        while (true) {
            int n = bis.read(buffer);
            if (n <= 0) {
                break;
            }
            bos.write(buffer, 0, n);
        }
        bos.close();
        bis.close();
        return (file.getAbsolutePath());

    }
}