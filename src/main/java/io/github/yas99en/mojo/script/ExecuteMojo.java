package io.github.yas99en.mojo.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;


/**
 * Executes scripts
 */
@Mojo(name="execute")
public final class ExecuteMojo extends AbstractMojo {
    private static final Pattern urlPattern = Pattern.compile("^jar:.*|^http://.*|^https://.*|^file:.*");

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Parameter(defaultValue = "${mojoExecution}", readonly = true)
    private MojoExecution execution;

    @Parameter(defaultValue = "javascript")
    private String engine;

    @Parameter(property="scriptmvn.arguments")
    private String[] arguments;

    @Parameter
    private List<String> scriptFiles;

    @Parameter
    private String scriptFile;

    @Parameter()
    private String script;

    @Parameter(defaultValue = "true")
    private boolean globalProject;

    @Parameter(defaultValue = "true")
    private boolean globalSettings;

    @Parameter(defaultValue = "true")
    private boolean globalLog;

    @Parameter(defaultValue = "mvn")
    private String prefix;

    private boolean relativeToProject = true;

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

        Mvn mvn = new Mvn(session, execution, log);

        setUpGlobals(eng, mvn);

        if(arguments == null) {
            arguments = new String[0];
        }

        if(scriptFiles != null) {
            evalScriptFiles(eng, mvn, scriptFiles, arguments, relativeToProject);
        }

        if(scriptFile != null) {
            evalScriptFile(eng, mvn, scriptFile, arguments, relativeToProject);
        }

        if(script != null) {
            evalScript(eng, mvn, script, arguments);
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
    }

    private static void evalScript(ScriptEngine eng, Mvn mvn, String script, String[] arguments) throws ScriptException {
        String pomXml = new File(mvn.project.getBasedir(), "pom.xml").getAbsolutePath();
        mvn.setScriptFile(pomXml);
        eng.put(ScriptEngine.FILENAME, pomXml);
        mvn.setArguments(arguments.clone());
        eng.put(ScriptEngine.ARGV, arguments.clone());
        eng.eval(script);
    }

    private static void evalScriptFile(ScriptEngine eng, Mvn mvn, String scriptFile, String[] arguments, boolean relativeToProject)
            throws IOException, ScriptException {

        mvn.setArguments(arguments.clone());
        eng.put(ScriptEngine.ARGV, arguments.clone());

        InputStream in = null;
        if(urlPattern.matcher(scriptFile).matches()) {
            URL url = new URL(scriptFile);
            mvn.log.debug(url);
            mvn.setScriptFile(scriptFile);
            eng.put(ScriptEngine.FILENAME, scriptFile);
            in = url.openStream();
        } else {
            File file = new File(scriptFile);
            if(!file.isAbsolute() && relativeToProject) {
                file = new File(mvn.project.getBasedir(), scriptFile);
            }
            mvn.log.debug(file);
            mvn.setScriptFile(file.getAbsolutePath());
            eng.put(ScriptEngine.FILENAME, file.getAbsolutePath());
            in = new FileInputStream(file);
        }

        Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

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
            List<String> scriptFiles, String[] arguments, boolean relativeToProject) throws IOException, ScriptException {
        for(String scriptFile: scriptFiles) {
            evalScriptFile(eng, mvn, scriptFile, arguments, relativeToProject);
        }
    }

    void setSession(MavenSession session) {
        this.session = session;
    }

    void setEngine(String engine) {
        this.engine = engine;
    }

    void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    void setScriptFiles(List<String> scriptFiles) {
        this.scriptFiles = scriptFiles;
    }

    void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
    }

    void setScript(String script) {
        this.script = script;
    }

    void setGlobalProject(boolean globalProject) {
        this.globalProject = globalProject;
    }

    void setGlobalSettings(boolean globalSettings) {
        this.globalSettings = globalSettings;
    }

    void setGlobalLog(boolean globalLog) {
        this.globalLog = globalLog;
    }

    void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isRelativeToProject() {
        return relativeToProject;
    }

    public void setRelativeToProject(boolean relativeToProject) {
        this.relativeToProject = relativeToProject;
    }
}
