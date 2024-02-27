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
package org.apache.struts.upload;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.apache.struts.Globals;
import org.apache.struts.mock.MockFormBean;
import org.apache.struts.mock.TestMockBase;
import org.apache.struts.util.RequestUtils;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link CommonsMultipartRequestHandler} class.
 *
 * @author ste-gr
 */
public class TestCommonsMultipartRequestHandler extends TestMockBase {
    // ------------------------------------------------------- Individual Tests

    /**
     * Test with maximum file size exceeded.
     */
    @Test
    public void testAcceptFileWithMaxFileSize() throws Exception {
        final String content = ("-----1234\r\n" +
                "Content-Disposition: form-data; name=\"files\"; filename=\"deleteme.txt\"\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "Unit test of CommonsMultipartRequestHandler" +
                "\r\n" +
                "-----1234--\r\n");

        moduleConfig.getControllerConfig().setMaxFileSize("5");

        final MockFormBean mockForm = processMultipart(content);

        testAttributes(true, true, false, false);

        final List<FormFile> files = mockForm.getFiles();
        assertNull(files, "No files expected");

        mockForm.getMultipartRequestHandler().rollback();
    }

    /**
     * Test with maximum request size exceeded.
     */
    @Test
    public void testAcceptFileWithMaxRequestSize() throws Exception {
        String content = ("-----1234\r\n" +
                "Content-Disposition: form-data; name=\"files\"; filename=\"deleteme.txt\"\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "Unit test of FileUploadInterceptor" +
                "\r\n" +
                "-----1234\r\n" +
                "Content-Disposition: form-data; name=\"normalFormField1\"\r\n" +
                "\r\n" +
                "it works" +
                "\r\n" +
                "-----1234\r\n" +
                "Content-Disposition: form-data; name=\"normalFormField2\"\r\n" +
                "\r\n" +
                "long string should not work" +
                "\r\n" +
                "-----1234--\r\n");

        moduleConfig.getControllerConfig().setMaxSize("300");

        final MockFormBean mockForm = processMultipart(content);

        testAttributes(true, false, false, false);

        final List<FormFile> files = mockForm.getFiles();
        assertNull(files, "No files expected");

        mockForm.getMultipartRequestHandler().rollback();
    }

    /**
     * Test for an successful upload.
     */
    @Test
    public void testSuccessUploadOfATextFileMultipartRequest() throws Exception {
        final String content = ("-----1234\r\n" +
                "Content-Disposition: form-data; name=\"files\"; filename=\"deleteme.txt\"\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "Unit test of CommonsMultipartRequestHandler" +
                "\r\n" +
                "-----1234--\r\n");

        final MockFormBean mockForm = processMultipart(content);

        testAttributes(false, false, false, false);

        final List<FormFile> files = mockForm.getFiles();
        assertNotNull(files, "Files expected");
        assertEquals(1, files.size(), "One file expected");
        assertEquals("text/html", files.get(0).getContentType());
        assertEquals("deleteme.txt", files.get(0).getFileName());

        mockForm.getMultipartRequestHandler().rollback();
    }

    /**
     * Test with no content.
     */
    @Test
    public void testEmptyContentTypeMultipartRequest() {
        final String content = "";

        final MockFormBean mockForm = processMultipart(content);

        testAttributes(false, false, false, false);

        final List<FormFile> files = mockForm.getFiles();
        assertNull(files, "No files expected");

        mockForm.getMultipartRequestHandler().rollback();
    }

    /**
     * Tests with multiple files sent with the same name.
     */
    @Test
    public void testMultipleAccept() throws Exception {
        final String htmlContent = "<html><head></head><body>html content</body></html>";
        final String plainContent = "plain content";
        final String bondary = "---1234";
        final String endline = "\r\n";

        final String content = encodeTextFile("test.html", "text/plain", plainContent) +
            encodeTextFile("test1.html", "text/html", htmlContent) +
            encodeTextFile("test2.html", "text/html", htmlContent) +
            endline +
            endline +
            endline +
            "--" +
            bondary +
            "--" +
            endline;

        final MockFormBean mockForm = processMultipart(content);

        testAttributes(false, false, false, false);

        final List<FormFile> files = mockForm.getFiles();
        assertNotNull(files, "Files expected");
        assertEquals(3, files.size(), "Three files expected");
        assertEquals("text/plain", files.get(0).getContentType());
        assertEquals("test.html", files.get(0).getFileName());
        assertEquals("text/html", files.get(1).getContentType());
        assertEquals("test1.html", files.get(1).getFileName());
        assertEquals("text/html", files.get(2).getContentType());
        assertEquals("test2.html", files.get(2).getFileName());

        mockForm.getMultipartRequestHandler().rollback();
    }

    /**
     * Tests with multiple files sent with the same name.
     */
    @Test
    public void testUnacceptedNumberOfFiles() throws Exception {
        final String htmlContent = "<html><head></head><body>html content</body></html>";
        final String plainContent = "plain content";
        final String bondary = "---1234";
        final String endline = "\r\n";

        final String content = encodeTextFile("test.html", "text/plain", plainContent) +
            encodeTextFile("test1.html", "text/html", htmlContent) +
            encodeTextFile("test2.html", "text/html", htmlContent) +
            endline +
            endline +
            endline +
            "--" +
            bondary +
            "--" +
            endline;

        moduleConfig.getControllerConfig().setFileCountMax(2);

        final MockFormBean mockForm = processMultipart(content);

        testAttributes(true, false, true, false);

        final List<FormFile> files = mockForm.getFiles();
        assertNull(files, "No files expected");

        mockForm.getMultipartRequestHandler().rollback();
    }

    /**
     * Test with maximum string length exceeded.
     */
    @Test
    public void testMultipartRequestMaxStringLength() throws Exception {
        String content = ("-----1234\r\n" +
                "Content-Disposition: form-data; name=\"files\"; filename=\"deleteme.txt\"\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "Unit test of FileUploadInterceptor" +
                "\r\n" +
                "-----1234\r\n" +
                "Content-Disposition: form-data; name=\"normalFormField1\"\r\n" +
                "\r\n" +
                "it works" +
                "\r\n" +
                "-----1234\r\n" +
                "Content-Disposition: form-data; name=\"normalFormField2\"\r\n" +
                "\r\n" +
                "long string should not work" +
                "\r\n" +
                "-----1234--\r\n");

        moduleConfig.getControllerConfig().setMaxStringLen("20");

        final MockFormBean mockForm = processMultipart(content);

        testAttributes(true, false, false, true);

        final List<FormFile> listFiles = mockForm.getFiles();
        assertNull(listFiles, "No files expected");

        final Map<String, FormFile[]> mapFiles = mockForm.getMultipartRequestHandler().getFileElements();
        assertNotNull(mapFiles, "Files expected");
        assertEquals(1, mapFiles.size(), "One file expected");

        final FormFile[] files = mapFiles.get("files");
        assertNotNull(files, "Files expected");
        assertEquals(1, files.length, "One file expected");
        assertEquals("text/html", files[0].getContentType());
        assertEquals("deleteme.txt", files[0].getFileName());

        final Map<String, String[]> texts = mockForm.getMultipartRequestHandler().getTextElements();
        assertNotNull(texts, "Texts expected");
        assertEquals(2, texts.size(), "Two texts expected");
        assertArrayEquals(new String[] {"it works"}, texts.get("normalFormField1"));
        assertArrayEquals(new String[] {""}, texts.get("normalFormField2"));

        mockForm.getMultipartRequestHandler().rollback();
    }

    private MockFormBean processMultipart(String content) {
        final MockFormBean  mockForm = new MockFormBean();

        // Set up the mock HttpServletRequest
        request.setMethod("POST");
        request.setContentType("multipart/form-data; boundary=---1234");
        request.setCharacterEncoding("UTF-8");
        request.setContent(content.getBytes(StandardCharsets.UTF_8));
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        request.setAttribute(Globals.MULTIPART_KEY, CommonsMultipartRequestHandler.class.getName());
        MultipartRequestWrapper multiRequest = new MultipartRequestWrapper(request);

        assertDoesNotThrow(() -> RequestUtils.populate(mockForm, multiRequest));

        return mockForm;
    }

    private void testAttributes(boolean maxLengthExpected, boolean maxByteLengthExpected,
            boolean maxFileCountExpected, boolean maxStringLengthExpected) {

        final Boolean maxLengthExceeded =
                (Boolean) request.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
        final Boolean maxByteLengthExceeded =
                (Boolean) request.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_BYTE_LENGTH_EXCEEDED);
        final Boolean maxFileCountExceeded =
                (Boolean) request.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_FILE_COUNT_EXCEEDED);
        final Boolean maxStringLengthExceeded =
                (Boolean) request.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_STRING_LENGTH_EXCEEDED);

        if (maxLengthExpected) {
            assertNotNull(maxLengthExceeded, "Max-lenght-exceeded expected (Object)");
            assertTrue(maxLengthExceeded.booleanValue(), "Max-lenght-exceeded expected (Value)");
        } else {
            assertNull(maxLengthExceeded, "No Max-lenght-exceeded expected");
        }

        if (maxByteLengthExpected) {
            assertNotNull(maxByteLengthExceeded, "Max-byte-lenght-exceeded expected (Object)");
            assertTrue(maxByteLengthExceeded.booleanValue(), "Max-byte-lenght-exceeded expected (Value)");
        } else {
            assertNull(maxByteLengthExceeded, "No Max-byte-lenght-exceeded expected");
        }

        if (maxFileCountExpected) {
            assertNotNull(maxFileCountExceeded, "Max-file-count-exceeded expected (Object)");
            assertTrue(maxFileCountExceeded.booleanValue(), "Max-file-count-exceeded expected (Value)");
        } else {
            assertNull(maxFileCountExceeded, "No Max-file-count-exceeded expected");
        }

        if (maxStringLengthExpected) {
            assertNotNull(maxStringLengthExceeded, "Max-string-lenght-exceeded expected (Object)");
            assertTrue(maxStringLengthExceeded.booleanValue(), "Max-string-lenght-exceeded expected (Value)");
        } else {
            assertNull(maxStringLengthExceeded, "No Max-string-lenght-exceeded expected");
        }
    }

    private String encodeTextFile(String filename, String contentType, String content) {
        return "\r\n" +
            "--" +
            "---1234" +
            "\r\n" +
            "Content-Disposition: form-data; name=\"" +
            "files" +
            "\"; filename=\"" +
            filename +
            "\"\r\n" +
            "Content-Type: " +
            contentType +
            "\r\n" +
            "\r\n" +
            content;
    }
}
