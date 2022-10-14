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
package org.apache.struts.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.LegacyAbstractLogger;
import org.slf4j.helpers.MessageFormatter;

/**
 * Logger to collect all log-messages.
 *
 * @author Stefan Graff
 *
 * @since Struts 1.4.2
 */
public class TestLogger extends LegacyAbstractLogger {
    private static final long serialVersionUID = 2770246600981179562L;

    /** The date and time format to use in the log message */
    protected final static DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS ZZZZZ");

    /** Buffer to collect all log-messages */
    protected final StringBuffer buffer = new StringBuffer();

    /**
     * Constructor which sets the name of this logger
     *
     * @param name the name of this logger
     */
    public TestLogger(String name) {
        this.name = name;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    protected String getFullyQualifiedCallerName() {
        return null;
    }

    @Override
    protected void handleNormalizedLoggingCall(Level level, Marker marker, String messagePattern, Object[] arguments,
            Throwable throwable) {
        final StringBuilder sb = new StringBuilder();

        // Append current date/time, level and name
        sb
            .append(DATE_TIME_FORMAT.format(OffsetDateTime.now()))
            .append(" [")
            .append(level)
            .append("] ")
            .append(name)
            .append(" - ");

        // Append marker
        if (marker != null) {
            sb
                .append(' ')
                .append(marker.getName())
                .append(' ');
        }

        // Append the message
        sb.append(MessageFormatter.basicArrayFormat(messagePattern, arguments));

        // Append throwable
        if(throwable != null) {
            sb
                .append(" <")
                .append(throwable)
                .append('>');

            StringWriter sw = new StringWriter(1024);
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            pw.close();
            sb.append(sw.getBuffer());
        }
        sb.append('\n');

        // Append all to buffer
        buffer.append(sb);
    }

    /**
     * Returns the full buffer with all collected log-messages.
     *
     * @return buffer with collected log-messages
     */
    String getOutput() {
        return buffer.toString();
    }

    /**
     * Deletes all collected log-messages
     */
    void clearOutput() {
        buffer.setLength(0);
    }
}