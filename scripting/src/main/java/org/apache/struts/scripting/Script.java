package org.apache.struts.scripting;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jakarta.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a saved script.
 *
 * <p>This class is thread-safe.</p>
 *
 * @author Stefan Graff
 *
 * @since Struts 1.4.1
 */
class Script {

    /**
     * The {@code Log} instance for this class.
     */
    private final Logger log =
        LoggerFactory.getLogger(Script.class);

    /**  The name of the script file. */
    public final String name;

    /**
     * The script file as path. If {@code null} the
     * script file was read thru {@code InputStream}.
     */
    public final Path path;

    /**  The ScriptEngine for the script. */
    public final ScriptEngine scriptEngine;

    /** Indicator if the script-engine is compilable */
    public final boolean compilable;

    /**  The time when the script was last modified. */
    public FileTime lastModifiedTime;

    /**  The contents of the script file. */
    public String content;

    /**  The compiled script if it is possible from the script-engine. */
    public CompiledScript compiledScript;

    /** Saves the last io-exception. */
    private IOException ioe;

    /** Saves the last script-exception. */
    private ScriptException se;

    /**
     * Creates a new instance of this class.
     *
     * <p>A possible {@code ScriptException} and/or
     * {@code IOException} will be saved.</p>
     *
     * @param scriptEngineManager The entry-point to JSR223-scripting
     * @param context             The servlet context
     * @param name                The name of the script file
     */
    public Script(final ScriptEngineManager scriptEngineManager,
            final ServletContext context, final String name) {

        this.name = name;

        final int i = name.lastIndexOf('.');
        final String ext = i < 0 ? name : name.substring(i + 1);
        scriptEngine = scriptEngineManager.getEngineByExtension(ext);
        if (scriptEngine == null) {
            se = new ScriptException("No ScriptEngine found for file: " + name);
        }

        compilable = scriptEngine instanceof Compilable;

        final String realPath = context.getRealPath(name);
        final InputStream inputStream;
        if (realPath == null) {
            path = null;
            inputStream = context.getResourceAsStream(name);
        } else {
            path = Paths.get(realPath);
            inputStream = null;
        }

        if (path == null && inputStream == null) {
            se = new ScriptException("Could not find resource for file: " + name);
        }

        if (se != null) {
            return;
        }

        try (Reader r = path == null
                ? new InputStreamReader(inputStream, StandardCharsets.UTF_8)
                : Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

            lastModifiedTime = path != null
                    ? IOUtils.getLastModifiedTime(path)
                    : null;

            log.debug("Loading new script: {}", name);

            setContent(IOUtils.getStringFromReader(r));
        } catch (IOException e) {
            ioe = e;
        }
    }

    /**
     * Checks the script file if it has a new content. If so, the
     * new content is loaded and compiled if possible.
     *
     * <p>A possible {@code ScriptException} and/or
     * {@code IOException} will be saved.</p>
     *
     * @return {@code true} script file is updated
     */
    public boolean checkNewContent() {
        this.ioe = null;

        if (path == null) {
            return false;
        }

        try {
            final FileTime lastModifiedTime = IOUtils.getLastModifiedTime(path);
            if (this.lastModifiedTime != null &&
                    this.lastModifiedTime.compareTo(lastModifiedTime) >= 0) {

                return false;
            }

            synchronized (this) {
                log.debug("Loading updated script: {}", name);

                this.lastModifiedTime = lastModifiedTime;

                try (Reader r = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                    setContent(IOUtils.getStringFromReader(r));
                }
            }
        } catch (IOException e) {
            ioe = e;
        }

        return true;
    }

    /**
     * Returns a set of names values representing the state of this script.
     * The values are generally visible in this scripts using the associated
     * keys as variable names.
     *
     * @return a scope of named values
     */
    public Bindings getBindings() {
        final Bindings bindings = scriptEngine.getBindings(ScriptContext.ENGINE_SCOPE);
        bindings.clear();
        bindings.put(ScriptEngine.FILENAME, name);

        return bindings;
    }

    /**
     * Checks if an {@code ScriptException} and/or
     * {@code IOException} is saved.
     *
     * @throws IOException if an I/O error occurs
     * @throws ScriptException if compilation fails
     */
    public void checkExceptions() throws ScriptException, IOException {
        if (ioe != null) {
            throw ioe;
        }

        if (se != null) {
            throw se;
        }
    }

    /**
     * Executes the specified script. The default
     * {@code ScriptContext} for this {@code Script} is used.
     *
     * @return The value returned from the execution of the script.
     * @throws ScriptException if error occurs in script.
     */
    public Object eval() throws ScriptException {
        if (compilable) {
            if (compiledScript == null) {
                throw se == null ? new ScriptException("Script could not compiled") : se;
            }
            return compiledScript.eval();
        }

        if (content == null || content.isEmpty()) {
            throw se == null ? new ScriptException("Script could not compiled") : se;
        }
        return scriptEngine.eval(content);
    }

    /**
     * Compiles the {@code Content} if it is possible.
     *
     * <p>A possible {@code ScriptException} will be saved.</p>
     *
     * @param content The {@code Content} to compile.
     */
    private void setContent(final String content) {
        this.se = null;
        this.compiledScript = null;

        if (content == null || content.isEmpty()) {
            this.content = "";
        } else if (compilable) {
            this.content = null;

            try {
                this.compiledScript = ((Compilable) scriptEngine).compile(content);
            } catch (ScriptException e) {
                se = e;
            }
        } else {
            this.content = content;
        }
    }
}