package org.apache.struts.scripting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

/**
 * Supporter class for {@code Struts-Scripting}.
 *
 * @author Stefan Graff
 *
 * @since Struts 1.4.1
 */
class IOUtils {

    private IOUtils() {}

    /**
     * Reads from the give {@code Path} the last modified time. If
     * the given {@code Path} is {@code null}, {@code null} will be
     * returned.
     *
     * @param path the given {@code Path}
     *
     * @return the last modified time or {@code null} if the given
     *     {@code Path} is {@code null}
     *
     * @throws IOException if an I/O error occurs
     */
    static FileTime getLastModifiedTime(final Path path) throws IOException {
        if (path == null) {
            return null;
        }

        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        return attr.lastModifiedTime();
    }

    /**
     * Reads all lines from {@code Reader} and save it
     * into a {@code String}.
     *
     * @param reader the given {@code Reader}
     *
     * @return the whole text from {@code Reader}
     *
     * @throws IOException if an I/O error occurs
     */
    static String getStringFromReader(Reader reader) throws IOException {
        try (BufferedReader br = reader instanceof BufferedReader
                ? (BufferedReader)reader
                : new BufferedReader(reader)) {

            final String lineSeparator = System.lineSeparator();
            final StringBuilder sb = new StringBuilder(8 * 1024);

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(lineSeparator).append(line);
            }

            return sb.substring(sb.length() ==  0 ? 0 : lineSeparator.length());
        }
    }
}