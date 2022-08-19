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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Verify that each of the example apps starts and (at least)
 * displays its default page.
 */
public class AppsIT {

    private String port;

    @BeforeEach
    public void setUp() {
        port = System.getProperty("cargo.servlet.port");
    }

    /**
     * Verify that the Struts Blank app has started
     */
    @Test
    public void testStrutsBlank() {
        try (WebClient webClient = new WebClient()) {
            URL url = new URL("http://localhost:"
                    + port + "/struts-blank");
            HtmlPage page = (HtmlPage) webClient.getPage(url);

            assertEquals("Struts Blank Application", page.getTitleText());
        } catch (FailingHttpStatusCodeException | IOException e) {
            fail(e);
        }
    }

    /**
     * Verify that the Struts Cookbook app has started
     */
    @Test
    public void testStrutsCookbook() {
        try (WebClient webClient = new WebClient()) {
            URL url = new URL("http://localhost:"
                    + port + "/struts-cookbook");
            HtmlPage page = (HtmlPage) webClient.getPage(url);

            assertEquals("Struts Cookbook", page.getTitleText());
        } catch (FailingHttpStatusCodeException | IOException e) {
            fail(e);
        }
    }

    /**
     * Verify that the view source function is working
     * in the Struts Cookbook app.
     */
    @Test
    public void testStrutsCookbookViewSource() {
        try (WebClient webClient = new WebClient()) {
            URL url = new URL("http://localhost:"
                    + port + "/struts-cookbook/source.jsp"
                    + "?src=/WEB-INF/src/java/examples/SuccessAction.java");
            HtmlPage page = (HtmlPage) webClient.getPage(url);

            assertEquals("View Source", page.getTitleText());
        } catch (FailingHttpStatusCodeException | IOException e) {
            fail(e);
        }
    }

    /**
     * Verify that the Struts Examples app has started
     */
    @Test
    public void testStrutsExamples() {
        try (WebClient webClient = new WebClient()) {
            URL url = new URL("http://localhost:"
                    + port + "/struts-examples");
            HtmlPage page = (HtmlPage) webClient.getPage(url);

            assertEquals("Struts Examples", page.getTitleText());
        } catch (FailingHttpStatusCodeException | IOException e) {
            fail(e);
        }
    }

    /**
     * Verify that the Struts Faces Example 1 app has started
     */
    @Test
    public void testStrutsFacesExample1() {
        try (WebClient webClient = new WebClient()) {
            URL url = new URL("http://localhost:"
                    + port + "/struts-faces-example1");
            HtmlPage page = (HtmlPage) webClient.getPage(url);

            assertEquals("MailReader Demonstration Application",
                    page.getTitleText());
        } catch (FailingHttpStatusCodeException | IOException e) {
            fail(e);
        }
    }

    /**
     * Verify that the Struts Faces Example 2 app has started
     */
    @Test
    public void testStrutsFacesExample2() {
        try (WebClient webClient = new WebClient()) {
            URL url = new URL("http://localhost:"
                    + port + "/struts-faces-example2");
            HtmlPage page = (HtmlPage) webClient.getPage(url);

            assertEquals("Struts+Tiles+Faces Example Application",
                    page.getTitleText());
        } catch (FailingHttpStatusCodeException | IOException e) {
            fail(e);
        }
    }

    /**
     * Verify that the Struts Mailreader app has started
     */
    @Test
    public void testStrutsMailreader() {
        try (WebClient webClient = new WebClient()) {
            URL url = new URL("http://localhost:"
                    + port + "/struts-mailreader");
            HtmlPage page = (HtmlPage) webClient.getPage(url);

            assertEquals("MailReader Demonstration Application",
                    page.getTitleText());
        } catch (FailingHttpStatusCodeException | IOException e) {
            fail(e);
        }
    }

    /**
     * Verify that the Struts Scripting Mailreader app has started
     */
    @Test
    public void testStrutsScriptingMailreader() {
        try (WebClient webClient = new WebClient()) {
            URL url = new URL("http://localhost:" + port
                    + "/struts-scripting-mailreader");
            HtmlPage page = (HtmlPage) webClient.getPage(url);

            assertEquals("MailReader Demonstration Application",
                    page.getTitleText());
        } catch (FailingHttpStatusCodeException | IOException e) {
            fail(e);
        }
    }

    /**
     * Verify that the Struts EL Exercise Taglib app has started
     */
    @Test
    public void testStrutsELExcerciseTaglib() {
        try (WebClient webClient = new WebClient()) {
            URL url = new URL("http://localhost:" + port
                    + "/struts-el-example");
            HtmlPage page = (HtmlPage) webClient.getPage(url);

            assertEquals("Struts-EL Test Application", page.getTitleText());
        } catch (FailingHttpStatusCodeException | IOException e) {
            fail(e);
        }
    }
}
