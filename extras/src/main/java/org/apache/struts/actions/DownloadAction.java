/*
 * $Id: $
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
package org.apache.struts.actions;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This is an abstract base class that minimizes the amount of special coding
 * that needs to be written to download a file. All that is required to use
 * this class is to extend it and implement the <code>getStreamInfo()</code>
 * method so that it returns the relevant information for the file (or other
 * stream) to be downloaded. Optionally, the <code>getBufferSize()</code>
 * method may be overridden to customize the size of the buffer used to
 * transfer the file.
 *
 * @since Struts 1.2.6
 */
public abstract class DownloadAction extends BaseAction {
    /**
     * If the <code>getBufferSize()</code> method is not overridden, this is
     * the buffer size that will be used to transfer the data to the servlet
     * output stream.
     */
    protected static final int DEFAULT_BUFFER_SIZE = 4096;

    /**
     * Returns the information on the file, or other stream, to be downloaded
     * by this action. This method must be implemented by an extending class.
     *
     * @param mapping  The ActionMapping used to select this instance.
     * @param form     The optional ActionForm bean for this request (if
     *                 any).
     * @param request  The HTTP request we are processing.
     * @param response The HTTP response we are creating.
     * @return The information for the file to be downloaded.
     * @throws Exception if an exception occurs.
     */
    protected abstract StreamInfo getStreamInfo(ActionMapping mapping,
        ActionForm form, HttpServletRequest request,
        HttpServletResponse response)
        throws Exception;

    /**
     * Returns the size of the buffer to be used in transferring the data to
     * the servlet output stream. This method may be overridden by an
     * extending class in order to customize the buffer size.
     *
     * @return The size of the transfer buffer, in bytes.
     */
    protected int getBufferSize() {
        return DEFAULT_BUFFER_SIZE;
    }

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping  The ActionMapping used to select this instance.
     * @param form     The optional ActionForm bean for this request (if
     *                 any).
     * @param request  The HTTP request we are processing.
     * @param response The HTTP response we are creating.
     * @return The forward to which control should be transferred, or
     *         <code>null</code> if the response has been completed.
     * @throws Exception if an exception occurs.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        StreamInfo info = getStreamInfo(mapping, form, request, response);
        String contentType = info.getContentType();
        InputStream stream = info.getInputStream();

        try {
            response.setContentType(contentType);
            copy(stream, response.getOutputStream());
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        // Tell Struts that we are done with the response.
        return null;
    }

    /**
     * Copy bytes from an <code>InputStream</code> to an
     * <code>OutputStream</code>.
     *
     * @param input  The <code>InputStream</code> to read from.
     * @param output The <code>OutputStream</code> to write to.
     * @return the number of bytes copied
     * @throws IOException In case of an I/O problem
     */
    public int copy(InputStream input, OutputStream output)
        throws IOException {
        byte[] buffer = new byte[getBufferSize()];
        int count = 0;
        int n = 0;

        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }

        return count;
    }

    /**
     * The information on a file, or other stream, to be downloaded by the
     * <code>DownloadAction</code>.
     */
    public static interface StreamInfo {
        /**
         * Returns the content type of the stream to be downloaded.
         *
         * @return The content type of the stream.
         */
        String getContentType();

        /**
         * Returns an input stream on the content to be downloaded. This
         * stream will be closed by the <code>DownloadAction</code>.
         *
         * @return The input stream for the content to be downloaded.
         * @throws IOException if an error occurs
         */
        InputStream getInputStream()
            throws IOException;
    }

    /**
     * A concrete implementation of the <code>StreamInfo</code> interface
     * which simplifies the downloading of a file from the disk.
     */
    public static class FileStreamInfo implements StreamInfo {
        /**
         * The content type for this stream.
         */
        private String contentType;

        /**
         * The file to be downloaded.
         */
        private File file;

        /**
         * Constructs an instance of this class, based on the supplied
         * parameters.
         *
         * @param contentType The content type of the file.
         * @param file        The file to be downloaded.
         */
        public FileStreamInfo(String contentType, File file) {
            this.contentType = contentType;
            this.file = file;
        }

        /**
         * Returns the content type of the stream to be downloaded.
         *
         * @return The content type of the stream.
         */
        public String getContentType() {
            return this.contentType;
        }

        /**
         * Returns an input stream on the file to be downloaded. This stream
         * will be closed by the <code>DownloadAction</code>.
         *
         * @return The input stream for the file to be downloaded.
         * @throws IOException if an error occurs
         */
        public InputStream getInputStream()
            throws IOException {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            return bis;
        }
    }

    /**
     * A concrete implementation of the <code>StreamInfo</code> interface
     * which simplifies the downloading of a web application resource.
     */
    public static class ResourceStreamInfo implements StreamInfo {
        /**
         * The content type for this stream.
         */
        private String contentType;

        /**
         * The servlet context for the resource to be downloaded.
         */
        private ServletContext context;

        /**
         * The path to the resource to be downloaded.
         */
        private String path;

        /**
         * Constructs an instance of this class, based on the supplied
         * parameters.
         *
         * @param contentType The content type of the file.
         * @param context     The servlet context for the resource.
         * @param path        The path to the resource to be downloaded.
         */
        public ResourceStreamInfo(String contentType, ServletContext context,
            String path) {
            this.contentType = contentType;
            this.context = context;
            this.path = path;
        }

        /**
         * Returns the content type of the stream to be downloaded.
         *
         * @return The content type of the stream.
         */
        public String getContentType() {
            return this.contentType;
        }

        /**
         * Returns an input stream on the resource to be downloaded. This
         * stream will be closed by the <code>DownloadAction</code>.
         *
         * @return The input stream for the resource to be downloaded.
         * @throws IOException if an error occurs
         */
        public InputStream getInputStream()
            throws IOException {
            return context.getResourceAsStream(path);
        }
    }
}
