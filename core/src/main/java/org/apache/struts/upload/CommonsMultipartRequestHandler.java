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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.fileupload2.core.DiskFileItem;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.core.FileUploadByteCountLimitException;
import org.apache.commons.fileupload2.core.FileUploadException;
import org.apache.commons.fileupload2.core.FileUploadFileCountLimitException;
import org.apache.commons.fileupload2.core.FileUploadSizeException;
import org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;

/**
 * This class implements the {@code MultipartRequestHandler} interface by
 * providing a wrapper around the Jakarta Commons FileUpload library.
 *
 * @since Struts 1.1
 */
public class CommonsMultipartRequestHandler implements MultipartRequestHandler {
    // ----------------------------------------------------- Manifest Constants

    /**
     * The default value for the maximum allowable size, in bytes, of an
     * uploaded file. The value is equivalent to 250MB.
     */
    public static final long DEFAULT_SIZE_MAX = 250 * 1024 * 1024;

    /**
     * The default value for the threshold which determines whether an uploaded
     * file will be written to disk or cached in memory. The value is
     * equivalent to 250KB.
     */
    public static final int DEFAULT_SIZE_THRESHOLD = 256 * 1024;

    // ----------------------------------------------------- Instance Variables

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(CommonsMultipartRequestHandler.class);

    /**
     * The combined text and file request parameters.
     */
    private HashMap<String, Object> elementsAll;

    /**
     * The file request parameters.
     */
    private HashMap<String, List<FormFile>> elementsFile;

    /**
     * The text request parameters.
     */
    private HashMap<String, String[]> elementsText;

    /**
     * The action mapping with which this handler is associated.
     */
    private ActionMapping mapping;

    /**
     * The servlet with which this handler is associated.
     */
    private ActionServlet servlet;

    /**
     * Indicator if we run on a windows-system.
     */
    private final static boolean WIN_SYSTEM =
            System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("windows");

    // ---------------------------------------- MultipartRequestHandler Methods

    /**
     * Retrieves the servlet with which this handler is associated.
     *
     * @return The associated servlet.
     */
    public ActionServlet getServlet() {
        return this.servlet;
    }

    /**
     * Sets the servlet with which this handler is associated.
     *
     * @param servlet The associated servlet.
     */
    public void setServlet(ActionServlet servlet) {
        this.servlet = servlet;
    }

    /**
     * Retrieves the action mapping with which this handler is associated.
     *
     * @return The associated action mapping.
     */
    public ActionMapping getMapping() {
        return this.mapping;
    }

    /**
     * Sets the action mapping with which this handler is associated.
     *
     * @param mapping The associated action mapping.
     */
    public void setMapping(ActionMapping mapping) {
        this.mapping = mapping;
    }

    /**
     * Parses the input stream and partitions the parsed items into a set of
     * form fields and a set of file items. In the process, the parsed items
     * are translated from Commons FileUpload {@code FileItem} instances to
     * Struts {@code FormFile} instances.
     *
     * @param request The multipart request to be processed.
     *
     * @throws ServletException if an unrecoverable error occurs.
     */
    public void handleRequest(HttpServletRequest request)
        throws ServletException {
        // Get the app config for the current request.
        ModuleConfig ac =
            (ModuleConfig) request.getAttribute(Globals.MODULE_KEY);

        // Create a factory for disk-based file items
        DiskFileItemFactory.Builder factory = DiskFileItemFactory.builder();

        // Set the maximum size that will be stored in memory.
        factory.setBufferSize((int) getSizeThreshold(ac));

        // Set the the location for saving data on disk.
        factory.setFile(getRepositoryFile(ac));

        // Create a new file upload handler
        JakartaServletFileUpload upload = new JakartaServletFileUpload(factory.get());

        // The following line is to support an "EncodingFilter"
        // see http://issues.apache.org/bugzilla/show_bug.cgi?id=23255
        upload.setHeaderCharset(Charset.forName(request.getCharacterEncoding()));

        // Set the maximum size before a FileUploadException will be thrown.
        upload.setSizeMax(getSizeMax(ac));

        // Sets the maximum number of files allowed per request.
        upload.setFileCountMax(getFileCountMax(ac));

        // Create the hash maps to be populated.
        elementsText = new HashMap<>();
        elementsFile = new HashMap<>();
        elementsAll = new HashMap<>();

        // Parse the request into file items.
        List<FileItem> items = null;

        try {
            items = upload.parseRequest(request);
        } catch (FileUploadSizeException e) {
            // Special handling for uploads that are too big.
            request.setAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED,
                Boolean.TRUE);
            clearInputStream(request);
            return;
        } catch (FileUploadException e) {
            log.error("Failed to parse multipart request", e);
            clearInputStream(request);
            throw new ServletException(e);
        }

        // Partition the items into form fields and files.
        for (FileItem item : items) {

            if (item.isFormField()) {
                addTextParameter(request, item);
            } else {
                addFileParameter(item);
            }
        }
    }

    /**
     * Returns a hash map containing the text (that is, non-file) request
     * parameters.
     *
     * @return The text request parameters.
     */
    public HashMap<String, String[]> getTextElements() {
        return this.elementsText;
    }

    /**
     * Returns a hash map containing the file (that is, non-text) request
     * parameters.
     *
     * @return The file request parameters.
     */
    public HashMap<String, List<FormFile>> getFileElements() {
        return this.elementsFile;
    }

    /**
     * Returns a hash map containing both text and file request parameters.
     *
     * @return The text and file request parameters.
     */
    public HashMap<String, Object> getAllElements() {
        return this.elementsAll;
    }

    /**
     * Cleans up when a problem occurs during request processing.
     */
    public void rollback() {
        for (List<FormFile> files : elementsFile.values()) {
            for (FormFile formFile : files) {
                try {
                    formFile.destroy();
                } catch (IOException e) {
                    log.atWarn()
                        .setMessage("Failed to destroy FormFile {}")
                        .addArgument(formFile.getFileName())
                        .setCause(e)
                        .log();
                }
            }
        }
    }

    /**
     * Cleans up at the end of a request.
     */
    public void finish() {
        rollback();
    }

    // -------------------------------------------------------- Support Methods

    /**
     * Finishes reading the input stream from an aborted upload. Fix for
     * STR-2700 to prevent Window machines from hanging.
     */
    protected void clearInputStream(HttpServletRequest request) {
        try {
            if (WIN_SYSTEM) {
                ServletInputStream is = request.getInputStream();
                byte[] data = new byte[DEFAULT_SIZE_THRESHOLD];
                int bytesRead = 0;
                do {
                    bytesRead = is.read(data);
                } while (bytesRead > -1);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Returns the maximum allowable size, in bytes, of an uploaded file. The
     * value is obtained from the current module's controller configuration.
     *
     * @param mc The current module's configuration.
     *
     * @return The maximum allowable file size, in bytes.
     */
    protected long getSizeMax(ModuleConfig mc) {
        return convertSizeToBytes(mc.getControllerConfig().getMaxFileSize(),
            DEFAULT_SIZE_MAX);
    }

    /**
     * Returns the size threshold which determines whether an uploaded file
     * will be written to disk or cached in memory.
     *
     * @param mc The current module's configuration.
     *
     * @return The size threshold, in bytes.
     */
    protected long getSizeThreshold(ModuleConfig mc) {
        return convertSizeToBytes(mc.getControllerConfig().getMemFileSize(),
            DEFAULT_SIZE_THRESHOLD);
    }

    /**
     * Converts a size value from a string representation to its numeric value.
     * The string must be of the form nnnm, where nnn is an arbitrary decimal
     * value, and m is a multiplier. The multiplier must be one of 'K', 'M' and
     * 'G', representing kilobytes, megabytes and gigabytes respectively.
     *
     * <p>If the size value cannot be converted, for example due to invalid
     * syntax, the supplied default is returned instead.</p>
     *
     * @param sizeString  The string representation of the size to be
     *                    converted.
     * @param defaultSize The value to be returned if the string is invalid.
     *
     * @return The actual size in bytes.
     */
    protected long convertSizeToBytes(String sizeString, long defaultSize) {
        int multiplier = 1;

        if (sizeString.endsWith("K")) {
            multiplier = 1024;
        } else if (sizeString.endsWith("M")) {
            multiplier = 1024 * 1024;
        } else if (sizeString.endsWith("G")) {
            multiplier = 1024 * 1024 * 1024;
        }

        if (multiplier != 1) {
            sizeString = sizeString.substring(0, sizeString.length() - 1);
        }

        long size = 0;

        try {
            size = Long.parseLong(sizeString);
        } catch (NumberFormatException nfe) {
            log.warn("Invalid format for file size ('{}'). Using default.",
                sizeString);
            size = defaultSize;
            multiplier = 1;
        }

        return size * multiplier;
    }

    /**
     * Returns the maximum permitted number of files that may be uploaded in a
     * single request. A value of -1 indicates no maximum. The value is
     * obtained from the current module's controller configuration.
     *
     * @param mc The current module's configuration.
     *
     * @return The maximum allowable file size, in bytes.
     */
    protected long getFileCountMax(ModuleConfig mc) {
        return mc.getControllerConfig().getFileCountMax();
    }

    /**
     * Returns the path to the temporary directory to be used for uploaded
     * files which are written to disk. The directory used is determined from
     * the first of the following to be non-empty.
     * 
     * <ol>
     * <li>A temp dir explicitly defined either using the {@code tempDir}
     * servlet init param, or the {@code tempDir} attribute of the
     * &lt;controller&gt; element in the Struts config file.</li>
     * <li>The container-specified temp dir, obtained from the
     * {@code jakarta.servlet.context.tempdir} servlet context attribute.</li>
     * <li>The temp dir specified by the {@code java.io.tmpdir} system
     * property.</li>
     * </ol>
     *
     * @param mc The module config instance for which the path should be
     *           determined.
     *
     * @return The path to the directory to be used to store uploaded files.
     */
    protected File getRepositoryFile(ModuleConfig mc) {
        File tempDirFile = null;

        // First, look for an explicitly defined temp dir.
        String tempDir = mc.getControllerConfig().getTempDir();

        // If none, look for a container specified temp dir.
        if (tempDir == null || tempDir.isEmpty()) {
            if (servlet != null) {
                ServletContext context = servlet.getServletContext();
                tempDirFile =
                    (File) context.getAttribute("jakarta.servlet.context.tempdir");

                if (tempDirFile != null) {
                    tempDirFile = tempDirFile.getAbsoluteFile();
                }
            }

            // If none, pick up the system temp dir.
            if (tempDirFile == null) {
                tempDir = System.getProperty("java.io.tmpdir");
            }
        }

        if (tempDirFile == null && tempDir != null && !tempDir.isEmpty()) {
            tempDirFile = new File(tempDir);
        }

        log.trace("File upload temp dir: {}", tempDirFile);

        return tempDirFile;
    }

    /**
     * Adds a regular text parameter to the set of text parameters for this
     * request and also to the list of all parameters. Handles the case of
     * multiple values for the same parameter by using an array for the
     * parameter value.
     *
     * @param request The request in which the parameter was specified.
     * @param item    The file item for the parameter to add.
     */
    protected void addTextParameter(HttpServletRequest request, FileItem item) {
        String name = item.getFieldName();
        String value = null;
        boolean haveValue = false;
        Charset encoding = null;

        if (item instanceof DiskFileItem) {
            encoding = ((DiskFileItem)item).getCharset();
            log.debug("DiskFileItem.getCharset=[{}]", encoding);
        }

        if (encoding == null) {
            encoding = Charset.forName(request.getCharacterEncoding());
            log.debug("request.getCharacterEncoding=[{}]", encoding);
        }

        if (encoding != null) {
            try {
                value = item.getString(encoding);
                haveValue = true;
            } catch (Exception e) {
                // Handled below, since haveValue is false.
            }
        }

        if (!haveValue) {
            try {
                value = item.getString(StandardCharsets.ISO_8859_1);
            } catch (java.io.UnsupportedEncodingException uee) {
                value = item.getString();
            }

            haveValue = true;
        }

        if (request instanceof MultipartRequestWrapper) {
            MultipartRequestWrapper wrapper = (MultipartRequestWrapper) request;

            wrapper.setParameter(name, value);
        }

        String[] oldArray = elementsText.get(name);
        String[] newArray;

        if (oldArray != null) {
            newArray = Arrays.copyOf(oldArray, oldArray.length + 1);
            newArray[oldArray.length] = value;
        } else {
            newArray = new String[] { value };
        }

        elementsText.put(name, newArray);
        elementsAll.put(name, newArray);
    }

    /**
     * Adds a file parameter to the set of file parameters for this request and
     * also to the list of all parameters.
     *
     * @param item The file item for the parameter to add.
     */
    protected void addFileParameter(FileItem item) {
        final String name = item.getFieldName();
        if (elementsFile.containsKey(name)) {
            List<FormFile> files = elementsFile.get(name);
            if (files == null) {
                files = new ArrayList<>();
                elementsFile.put(name, files);
                elementsAll.put(name, files);
            }

            files.add(new CommonsFormFile(item));
        }
    }

    // ---------------------------------------------------------- Inner Classes

    /**
     * This class implements the Struts {@code FormFile} interface by wrapping
     * the Commons FileUpload {@code FileItem} interface. This implementation
     * is <i>read-only</i>; any attempt to modify an instance of this class
     * will result in an {@code UnsupportedOperationException}.
     */
    static class CommonsFormFile implements FormFile, Serializable {
        private static final long serialVersionUID = -6784594200973351263L;

        /**
         * The {@code FileItem} instance wrapped by this object.
         */
        FileItem fileItem;

        /**
         * Constructs an instance of this class which wraps the supplied file
         * item.
         *
         * @param fileItem The Commons file item to be wrapped.
         */
        public CommonsFormFile(FileItem fileItem) {
            this.fileItem = fileItem;
        }

        /**
         * Returns the content type for this file.
         *
         * @return A String representing content type.
         */
        public String getContentType() {
            return fileItem.getContentType();
        }

        /**
         * Sets the content type for this file.
         *
         * <p>NOTE: This method is not supported in this implementation.</p>
         *
         * @param contentType A string representing the content type.
         */
        public void setContentType(String contentType) {
            throw new UnsupportedOperationException(
                "The setContentType() method is not supported.");
        }

        /**
         * Returns the size, in bytes, of this file.
         *
         * @return The size of the file, in bytes.
         *
         * @deprecated Use {@link #getFileLength()}
         */
        @Deprecated
        public int getFileSize() {
            long size = fileItem.getSize();
            if (size > Integer.MAX_VALUE) {
                throw new IllegalStateException("Size is greater than 2 GB; use getFileLength()");
            }
            return (int) size;
        }

        /**
         * Sets the size, in bytes, for this file.
         *
         * <p>NOTE: This method is not supported in this implementation.</p>
         *
         * @param filesize The size of the file, in bytes.
         *
         * @deprecated Use {@link #setFileLength(long)}
         */
        @Deprecated
        public void setFileSize(int filesize) {
            throw new UnsupportedOperationException(
                "The setFileSize() method is not supported.");
        }

        /**
         * Returns the length of this file.
         *
         * @return The length of the file, in bytes.
         *
         * @throws IllegalStateException if size is greater than 2GB
         */
        public long getFileLength() {
            return fileItem.getSize();
        }

        /**
         * Sets the length, in bytes, for this file.
         *
         * <p>NOTE: This method is not supported in this implementation.</p>
         *
         * @param fileLength The length of the file, in bytes.
         */
        public void setFileLength(long fileLength) {
            throw new UnsupportedOperationException(
                "The setFileLength() method is not supported.");
        }

        /**
         * Returns the (client-side) file name for this file.
         *
         * @return The client-size file name.
         */
        public String getFileName() {
            return getBaseFileName(fileItem.getName());
        }

        /**
         * Sets the (client-side) file name for this file.
         *
         * <p>NOTE: This method is not supported in this implementation.</p>
         *
         * @param fileName The client-side name for the file.
         */
        public void setFileName(String fileName) {
            throw new UnsupportedOperationException(
                "The setFileName() method is not supported.");
        }

        /**
         * Returns the data for this file as a byte array. Note that this may
         * result in excessive memory usage for large uploads. The use of the
         * {@link #getInputStream() getInputStream} method is encouraged as an
         * alternative.
         *
         * @return An array of bytes representing the data contained in this
         *         form file.
         *
         * @throws FileNotFoundException If some sort of file representation
         *                               cannot be found for the FormFile
         * @throws IOException           If there is some sort of IOException
         */
        public byte[] getFileData()
            throws FileNotFoundException, IOException {
            return fileItem.get();
        }

        /**
         * Get an InputStream that represents this file. This is the preferred
         * method of getting file data.
         *
         * @throws FileNotFoundException If some sort of file representation
         *                               cannot be found for the FormFile
         * @throws IOException           If there is some sort of IOException
         */
        public InputStream getInputStream()
            throws FileNotFoundException, IOException {
            return fileItem.getInputStream();
        }

        /**
         * Destroy all content for this form file. Implementations should
         * remove any temporary files or any temporary file data stored
         * somewhere.
         */
        public void destroy() {
            fileItem.delete();
        }

        /**
         * Returns the base file name from the supplied file path. On the
         * surface, this would appear to be a trivial task. Apparently,
         * however, some Linux JDKs do not implement {@code File.getName()}
         * correctly for Windows paths, so we attempt to take care of that
         * here.
         *
         * @param filePath The full path to the file.
         *
         * @return The base file name, from the end of the path.
         */
        protected String getBaseFileName(String filePath) {
            // First, ask the JDK for the base file name.
            String fileName = new File(filePath).getName();

            // Now check for a Windows file name parsed incorrectly.
            int colonIndex = fileName.indexOf(":");

            if (colonIndex == -1) {
                // Check for a Windows SMB file path.
                colonIndex = fileName.indexOf("\\\\");
            }

            int backslashIndex = fileName.lastIndexOf("\\");

            if ((colonIndex > -1) && (backslashIndex > -1)) {
                // Consider this filename to be a full Windows path, and parse
                // it accordingly to retrieve just the base file name.
                fileName = fileName.substring(backslashIndex + 1);
            }

            return fileName;
        }

        /**
         * Returns the (client-side) file name for this file.
         *
         * @return The client-size file name.
         */
        public String toString() {
            return getFileName();
        }
    }
}