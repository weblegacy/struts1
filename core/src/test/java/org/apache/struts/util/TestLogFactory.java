package org.apache.struts.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;

public class TestLogFactory extends LogFactory {

    /**
     * The {@link org.apache.commons.logging.Log} instances that have
     * already been created, keyed by logger name.
     */
    protected Map<String, TestLog> instances = new ConcurrentHashMap<>();

    /**
     * Configuration attributes.
     */
    protected Map<String, Object> attributes = new ConcurrentHashMap<>();

    public TestLogFactory() {
    }

    public static TestLogFactory getInstance() {
        return (TestLogFactory) getFactory();
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public String[] getAttributeNames() {
        return attributes.keySet().toArray(new String[0]);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Log getInstance(Class clazz) throws LogConfigurationException {
        return getInstance(clazz.getName());
    }

    @Override
    public Log getInstance(String name) throws LogConfigurationException {
        return instances.computeIfAbsent(name, TestLog::new);
    }

    @Override
    public void release() {
        instances.clear();
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public String getOutput(Class<?> clazz) {
        return getOutput(clazz.getName());
    }

    public String getOutput(String name) {
        TestLog log = instances.get(name);
        return log == null ? "" : log.getOutput();
    }

    public void clearOutput(Class<?> clazz) {
        clearOutput(clazz.getName());
    }

    public void clearOutput(String name) {
        TestLog log = instances.get(name);
        if (log != null) {
            log.clearOutput();
        }
    }

}