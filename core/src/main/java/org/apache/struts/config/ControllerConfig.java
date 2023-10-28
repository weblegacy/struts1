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
package org.apache.struts.config;


/**
 * A JavaBean representing the configuration information of a
 * {@code <controller>} element in a Struts configuration file.
 *
 * @since Struts 1.1
 */
public class ControllerConfig extends BaseConfig {
    private static final long serialVersionUID = 7704474362520651283L;

    // ------------------------------------------------------------- Properties

    /**
     * The input buffer size for file uploads.
     */
    protected int bufferSize = 4096;

    /**
     * The content type and character encoding to be set on each response.
     */
    protected String contentType = "text/html";

    /**
     * The chain catalog name for this module.
     */
    protected String catalog = "struts";

    /**
     * The chain command to execute for each request.
     */
    protected String command = "servlet-standard";

    /**
     * The replacement pattern used to determine a context-relative URL from a
     * {@link ForwardConfig} element. The pattern may consist of any
     * combination of the following markers and characters:
     *
     * <ul>
     *
     * <li><strong>{@code $M}</strong> - Replaced by the module prefix for the
     * current module.</li>
     *
     * <li><strong>{@code $P}</strong> - Replaced by the {@code path} property
     * of a {@link ForwardConfig} instance.</li>
     *
     * <li><strong>{@code $$}</strong> - Renders a literal dollar sign ("$")
     * character in the resulting URL.</li>
     *
     * <li>A dollar sign followed by any other character is reserved for future
     * use, and both characters are silently swallowed.</li>
     *
     * <li>All other characters in the pattern are passed through
     * unchanged.</li>
     *
     * </ul>
     *
     * <p>If this property is set to {@code null}, a default pattern of
     * {@code $M$P} is utilized, which is backwards compatible with the hard
     * coded functionality in prior versions.</p>
     */
    protected String forwardPattern = null;

    /**
     * Should the {@code input} property of {@link ActionConfig} instances
     * associated with this module be treated as the name of a corresponding
     * {@link ForwardConfig}. A {@code false} value treats them as a
     * module-relative path (consistent with the hard coded behavior of
     * earlier versions of Struts.
     *
     * @since Struts 1.1
     */
    protected boolean inputForward = false;

    /**
     * Should we store a Locale object in the user's session if needed?
     */
    protected boolean locale = true;

    /**
     * The maximum file size to process for file uploads.
     */
    protected String maxFileSize = "250M";

    /**
     * The maximum permitted number of files that may be uploaded in a single
     * request. A value of -1 indicates no maximum.
     */
    protected long fileCountMax = -1;

    /**
     * The maximum file size to retain in memory.
     */
    protected String memFileSize = "256K";

    /**
     * The fully qualified Java class name of the MultipartRequestHandler class
     * to be used.
     */
    protected String multipartClass =
        "org.apache.struts.upload.CommonsMultipartRequestHandler";

    /**
     * Should we set no-cache HTTP headers on each response?
     */
    protected boolean nocache = false;

    /**
     * The replacement pattern used to determine a context-relative URL from
     * the {@code page} attribute of Struts tags and configuration properties.
     * The pattern may consist of any combination of the following markers and
     * characters:
     *
     * <ul>
     *
     * <li><strong>{@code $M}</strong> - Replaced by the module prefix for the
     * current module.</li>
     *
     * <li><strong>{@code $P}</strong> - Replaced by the {@code page} attribute
     * value being evaluated.</li>
     *
     * <li><strong>{@code $$}</strong> - Renders a literal dollar sign ("$")
     * character in the resulting URL.</li>
     *
     * <li>A dollar sign followed by any other character is reserved for future
     * use, and both characters are silently swallowed.</li>
     *
     * <li>All other characters in the pattern are passed through
     * unchanged.</li>
     *
     * </ul>
     *
     * <p>If this property is set to {@code null}, a default pattern of
     * {@code $M$P} is utilized, which is backwards compatible with the hard
     * coded functionality in prior versions.</p>
     */
    protected String pagePattern = null;

    /**
     * The fully qualified class name of the RequestProcessor implementation
     * class to be used for this module.
     */
    protected String processorClass =
        "org.apache.struts.chain.ComposableRequestProcessor";

    /**
     * The temporary working directory to use for file uploads.
     */
    protected String tempDir = null;

    public int getBufferSize() {
        return (this.bufferSize);
    }

    public void setBufferSize(int bufferSize) {
        if (configured) {
            throw new IllegalStateException("Configuration is frozen");
        }

        this.bufferSize = bufferSize;
    }

    public String getContentType() {
        return (this.contentType);
    }

    public void setContentType(String contentType) {
        if (configured) {
            throw new IllegalStateException("Configuration is frozen");
        }

        this.contentType = contentType;
    }

    public String getCatalog() {
        return (this.catalog);
    }

    public void setCatalog(String catalog) {
        if (configured) {
            throw new IllegalStateException("Configuration is frozen");
        }

        this.catalog = catalog;
    }

    public String getCommand() {
        return (this.command);
    }

    public void setCommand(String command) {
        if (configured) {
            throw new IllegalStateException("Configuration is frozen");
        }

        this.command = command;
    }

    public String getForwardPattern() {
        return (this.forwardPattern);
    }

    public void setForwardPattern(String forwardPattern) {
        this.forwardPattern = forwardPattern;
    }

    public boolean getInputForward() {
        return (this.inputForward);
    }

    public void setInputForward(boolean inputForward) {
        this.inputForward = inputForward;
    }

    public boolean getLocale() {
        return (this.locale);
    }

    public void setLocale(boolean locale) {
        if (configured) {
            throw new IllegalStateException("Configuration is frozen");
        }

        this.locale = locale;
    }

    public String getMaxFileSize() {
        return (this.maxFileSize);
    }

    public void setMaxFileSize(String maxFileSize) {
        if (configured) {
            throw new IllegalStateException("Configuration is frozen");
        }

        this.maxFileSize = maxFileSize;
    }

    public long getFileCountMax() {
        return (this.fileCountMax);
    }

    public void setFileCountMax(long fileCountMax) {
        if (configured) {
            throw new IllegalStateException("Configuration is frozen");
        }

        this.fileCountMax = fileCountMax;
    }

    public String getMemFileSize() {
        return (this.memFileSize);
    }

    public void setMemFileSize(String memFileSize) {
        if (configured) {
            throw new IllegalStateException("Configuration is frozen");
        }

        this.memFileSize = memFileSize;
    }

    public String getMultipartClass() {
        return (this.multipartClass);
    }

    public void setMultipartClass(String multipartClass) {
        if (configured) {
            throw new IllegalStateException("Configuration is frozen");
        }

        this.multipartClass = multipartClass;
    }

    public boolean getNocache() {
        return (this.nocache);
    }

    public void setNocache(boolean nocache) {
        if (configured) {
            throw new IllegalStateException("Configuration is frozen");
        }

        this.nocache = nocache;
    }

    public String getPagePattern() {
        return (this.pagePattern);
    }

    public void setPagePattern(String pagePattern) {
        this.pagePattern = pagePattern;
    }

    public String getProcessorClass() {
        return (this.processorClass);
    }

    public void setProcessorClass(String processorClass) {
        if (configured) {
            throw new IllegalStateException("Configuration is frozen");
        }

        this.processorClass = processorClass;
    }

    public String getTempDir() {
        return (this.tempDir);
    }

    public void setTempDir(String tempDir) {
        if (configured) {
            throw new IllegalStateException("Configuration is frozen");
        }

        this.tempDir = tempDir;
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Return a String representation of this object.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("ControllerConfig[");

        sb.append("bufferSize=");
        sb.append(this.bufferSize);

        if (this.contentType != null) {
            sb.append(",contentType=");
            sb.append(this.contentType);
        }

        if (this.forwardPattern != null) {
            sb.append(",forwardPattern=");
            sb.append(this.forwardPattern);
        }

        sb.append(",inputForward=");
        sb.append(this.inputForward);
        sb.append(",locale=");
        sb.append(this.locale);

        if (this.maxFileSize != null) {
            sb.append(",maxFileSize=");
            sb.append(this.maxFileSize);
        }

        sb.append("fileCountMax=");
        sb.append(this.fileCountMax);

        if (this.memFileSize != null) {
            sb.append(",memFileSize=");
            sb.append(this.memFileSize);
        }

        sb.append(",multipartClass=");
        sb.append(this.multipartClass);
        sb.append(",nocache=");
        sb.append(this.nocache);

        if (this.pagePattern != null) {
            sb.append(",pagePattern=");
            sb.append(this.pagePattern);
        }

        sb.append(",processorClass=");
        sb.append(this.processorClass);

        if (this.tempDir != null) {
            sb.append(",tempDir=");
            sb.append(this.tempDir);
        }

        sb.append("]");

        return (sb.toString());
    }
}