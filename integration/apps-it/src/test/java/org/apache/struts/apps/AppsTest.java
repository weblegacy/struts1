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
package org.apache.struts.apps;

import java.net.URL;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.WebClient;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Verify that each of the example apps starts and (at least)
 * displays its default page.
 */
public class AppsTest extends TestCase {

    private String version;
    private String port;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppsTest(String testName) {
        super(testName);
    }

    public void setUp() throws Exception {
        super.setUp();
        version = System.getProperty("version");
        port = System.getProperty("cargo.servlet.port");
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(AppsTest.class);
        return new Tomcat5xTestSetup(suite);
    }

    /**
     * Verify that the Struts Blank app has started
     */
    public void testStrutsBlank() throws Exception {
        WebClient webClient = new WebClient();
        URL url = new URL("http://localhost:"
                + port + "/struts-blank-" + version);
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        assertEquals("Struts Blank Application", page.getTitleText());
    }

    /**
     * Verify that the Struts Cookbook app has started
     */
    public void testStrutsCookbook() throws Exception {
        WebClient webClient = new WebClient();
        URL url = new URL("http://localhost:"
                + port + "/struts-cookbook-" + version);
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        assertEquals("Struts Cookbook", page.getTitleText());
    }

    /**
     * Verify that the view source function is working
     * in the Struts Cookbook app.
     */
    public void testStrutsCookbookViewSource() throws Exception {
        WebClient webClient = new WebClient();
        URL url = new URL("http://localhost:"
                + port + "/struts-cookbook-" + version + "/source.jsp"
                + "?src=/WEB-INF/src/java/examples/SuccessAction.java");
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        assertEquals("View Source", page.getTitleText());
    }

    /**
     * Verify that the Struts Examples app has started
     */
    public void testStrutsExamples() throws Exception {
        WebClient webClient = new WebClient();
        URL url = new URL("http://localhost:"
                + port + "/struts-examples-" + version);
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        assertEquals("Struts Examples", page.getTitleText());
    }

    /**
     * Verify that the Struts Faces Example 1 app has started
     */
    public void testStrutsFacesExample1() throws Exception {
        WebClient webClient = new WebClient();
        URL url = new URL("http://localhost:"
                + port + "/struts-faces-example1-" + version);
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        assertEquals("MailReader Demonstration Application",
                page.getTitleText());
    }

    /**
     * Verify that the Struts Faces Example 2 app has started
     */
    public void testStrutsFacesExample2() throws Exception {
        WebClient webClient = new WebClient();
        URL url = new URL("http://localhost:"
                + port + "/struts-faces-example2-" + version);
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        assertEquals("Struts+Tiles+Faces Example Application",
                page.getTitleText());
    }

    /**
     * Verify that the Struts Mailreader app has started
     */
    public void testStrutsMailreader() throws Exception {
        WebClient webClient = new WebClient();
        URL url = new URL("http://localhost:"
                + port + "/struts-mailreader-" + version);
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        assertEquals("MailReader Demonstration Application",
                page.getTitleText());
    }

    /**
     * Verify that the Struts Scripting Mailreader app has started
     */
    public void testStrutsScriptingMailreader() throws Exception {
        WebClient webClient = new WebClient();
        URL url = new URL("http://localhost:" + port
                + "/struts-scripting-mailreader-" + version);
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        assertEquals("MailReader Demonstration Application",
                page.getTitleText());
    }

    /**
     * Verify that the Struts EL Exercise Taglib app has started
     */
    public void testStrutsELExcerciseTaglib() throws Exception {
        WebClient webClient = new WebClient();
        URL url = new URL("http://localhost:" + port
                + "/struts-el-example-" + version);
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        assertEquals("Struts-EL Test Application", page.getTitleText());
    }

}
