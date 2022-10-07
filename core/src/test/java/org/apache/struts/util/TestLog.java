package org.apache.struts.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.logging.Log;

public class TestLog implements Log {

    protected final String name;

    /** The date and time format to use in the log message */
    protected final static DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS ZZZZZ");

    protected final StringBuffer buffer = new StringBuffer();

    public TestLog(String name) {
        this.name = name;
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public boolean isFatalEnabled() {
        return true;
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void trace(Object message) {
        trace(message, null);
    }

    @Override
    public void trace(Object message, Throwable t) {
        log("TRACE", message, t);
    }

    @Override
    public void debug(Object message) {
        debug(message, null);
    }

    @Override
    public void debug(Object message, Throwable t) {
        log("DEBUG", message, t);
    }

    @Override
    public void info(Object message) {
        info(message, null);
    }

    @Override
    public void info(Object message, Throwable t) {
        log("INFO", message, t);
    }

    @Override
    public void warn(Object message) {
        warn(message, null);
    }

    @Override
    public void warn(Object message, Throwable t) {
        log("WARN", message, t);
    }

    @Override
    public void error(Object message) {
        error(message, null);
    }

    @Override
    public void error(Object message, Throwable t) {
        log("ERROR", message, t);
    }

    @Override
    public void fatal(Object message) {
        fatal(message, null);
    }

    @Override
    public void fatal(Object message, Throwable t) {
        log("FATAL", message, t);
    }

    /**
     * Do the actual logging.
     *
     * @param level the log-level
     * @param message The message itself (typically a String)
     * @param t The exception whose stack trace should be logged
     */
    protected void log(String level, Object message, Throwable t) {
        StringBuilder sb = new StringBuilder();

        sb
            .append(DATE_TIME_FORMAT.format(OffsetDateTime.now()))
            .append(" [")
            .append(level)
            .append("] ")
            .append(name)
            .append(" - ")
            .append(message);

        if(t != null) {
            sb
                .append(" <")
                .append(t)
                .append('>');

            StringWriter sw = new StringWriter(1024);
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sb.append(sw.getBuffer());
        }

        buffer
            .append(sb)
            .append('\n');
    }

    String getOutput() {
        return buffer.toString();
    }

    void clearOutput() {
        buffer.setLength(0);
    }

}