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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * This interface represents a file that has been uploaded by a client. It is
 * the only interface or class in upload package which is typically referenced
 * directly by a Struts application.
 */
public interface FormFile extends Serializable {

    /**
     * Returns the content type for this file.
     *
     * @return A String representing content type.
     */
    public String getContentType();

    /**
     * Sets the content type for this file.
     *
     * @param contentType The content type for the file.
     */
    public void setContentType(String contentType);

    /**
     * Returns the size of this file.
     *
     * @return The size of the file, in bytes.
     *
     * @throws IllegalStateException if size is greater than 2GB
     *
     * @see #getFileLength()
     *
     * @deprecated use {@link #getFileLength()}
     */
    @Deprecated
    public int getFileSize();

    /**
     * Sets the file size.
     *
     * @param size The size of the file, in bytes.
     *
     * @see #setFileLength(long)
     *
     * @deprecated use {@link #setFileLength(long)}
     */
    @Deprecated
    public void setFileSize(int size);

    /**
     * Returns the length of this file.
     *
     * @return The length of the file, in bytes.
     */
    public long getFileLength();

    /**
     * Sets the file length.
     *
     * @param fileLength The length of the file, in bytes.
     */
    public void setFileLength(long fileLength);

    /**
     * Returns the file name of this file. This is the base name of the file,
     * as supplied by the user when the file was uploaded.
     *
     * @return The base file name.
     */
    public String getFileName();

    /**
     * Sets the file name of this file.
     *
     * @param fileName The base file name.
     */
    public void setFileName(String fileName);

    /**
     * Returns the data for the entire file as byte array. Care is needed when
     * using this method, since a large upload could easily exhaust available
     * memory. The preferred method for accessing the file data is
     * {@link #getInputStream() getInputStream}.
     *
     * @return The file data as a byte array.
     *
     * @throws FileNotFoundException if the uploaded file is not found. Some
     *                               implementations may not deal with files
     *                               and/or throw this exception.
     * @throws IOException           if an error occurred while reading the
     *                               file.
     */
    public byte[] getFileData()
        throws FileNotFoundException, IOException;

    /**
     * Returns an input stream for this file. The caller must close the stream
     * when it is no longer needed.
     *
     * @throws FileNotFoundException if the uploaded file is not found. Some
     *                               implementations may not deal with files
     *                               and/or throw this exception.
     * @throws IOException           if an error occurred while reading the
     *                               file.
     */
    public InputStream getInputStream()
        throws FileNotFoundException, IOException;

    /**
     * Destroys all content for the uploaded file, including any underlying
     * data files.
     *
     * @throws IOException           if an error occurred while destroying all
     *                               content.
     */
    public void destroy() throws IOException;
}