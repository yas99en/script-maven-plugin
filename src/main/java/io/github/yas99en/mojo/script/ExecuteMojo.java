package io.github.yas99en.mojo.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "execute")
public class ExecuteMojo extends AbstractMojo {
    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Parameter(defaultValue = "javascript")
    private String engine;

    @Parameter(property="scriptmvn.arguments")
    private String[] arguments;

    @Parameter
    private List<String> scriptFiles;

    @Parameter
    private String scriptFile;

    @Parameter
    private String script;

    @Parameter(defaultValue = "true")
    private boolean globalProject;

    @Parameter(defaultValue = "true")
    private boolean globalSettings;

    @Parameter(defaultValue = "true")
    private boolean globalLog;

    @Parameter(defaultValue = "mvn")
    private String prefix;

    private Log log;

    public void execute() throws MojoExecutionException {
        log = getLog();

        try {
            evaluate();
        } catch (ScriptException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        } catch (RuntimeException e) {
            if(e.getCause() instanceof MojoExecutionException) {
                throw (MojoExecutionException)e.getCause();
            } else {
                throw e;
            }
        }
    }

    private void evaluate() throws ScriptException, IOException, MojoExecutionException {
        ScriptEngineManager manager = new ScriptEngineManager();
        log.info("engine: " + engine);
        ScriptEngine eng = manager.getEngineByName(engine);
        if(eng == null) {
            throw new MojoExecutionException("engine not found for: " + engine);
        }

        Mvn mvn = new Mvn(session, log);

        setUpGlobals(eng, mvn);

        if(scriptFiles != null) {
            evalScriptFiles(eng, mvn, scriptFiles);
        }

        if(scriptFile != null) {
            evalScriptFile(eng, mvn, scriptFile);
        }

        if(script != null) {
            evalScript(eng, mvn, script);
        }
    }

    private void setUpGlobals(ScriptEngine eng, Mvn mvn) {
        log.debug("prefix: "+prefix);
        if(!prefix.isEmpty()) {
            eng.put(prefix, mvn);
        }

        if(globalProject) {
            eng.put("project", mvn.project);
        }
        if(globalSettings) {
            eng.put("settings", mvn.settings);
        }
        if(globalLog) {
            eng.put("log", mvn.log);
        }

        if(arguments == null) {
            arguments = new String[0];
        }
        mvn.setArguments(arguments.clone());
        eng.put(ScriptEngine.ARGV, arguments.clone());
    }

    private static void evalScript(ScriptEngine eng, Mvn mvn, String script) throws ScriptException {
        String pomXml = new File(mvn.project.getBasedir(), "pom.xml").getAbsolutePath();
        mvn.setScriptFile(pomXml);
        eng.put(ScriptEngine.FILENAME, pomXml);
        eng.eval(script);
    }

    private static void evalScriptFile(ScriptEngine eng, Mvn mvn, String scriptFile)
            throws IOException, ScriptException {
        File file = new File(scriptFile);
        if(!file.isAbsolute()) {
            file = new File(mvn.project.getBasedir(), scriptFile);
        }
        mvn.log.debug(file);
        mvn.setScriptFile(file.getAbsolutePath());
        eng.put(ScriptEngine.FILENAME, file.getAbsolutePath());
        Reader reader = new BufferedReader(new FileReader(file));
        try {
            eng.eval(reader);
        } finally {
            try {
                reader.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void evalScriptFiles(ScriptEngine eng, Mvn mvn,
            List<String> scriptFiles) throws IOException, ScriptException {
        for(String scriptFile: scriptFiles) {
            evalScriptFile(eng, mvn, scriptFile);
        }
    }
}
