package io.github.yas99en.mojo.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

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
    @Parameter( defaultValue = "${session}", readonly = true )
    private MavenSession session;

    @Parameter(defaultValue="javascript")
    private String engine;

    @Parameter(property="script.arguments")
    private String[] arguments;

    @Parameter
    private String script;

    @Parameter
    private String scriptFile;

    @Parameter(defaultValue = "true")
    private boolean global;

    @Parameter(defaultValue = "mvn")
    private String prefix;

    private Log log;

    public void execute() throws MojoExecutionException, MojoFailureException {
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
        log.info("prefix: "+prefix);
        
        if(!prefix.isEmpty()) {
        	eng.put(prefix, mvn);
        }

        if(global) {
            eng.put("project", mvn.project);
            eng.put("settings", mvn.settings);
            eng.put("session", mvn.session);
            eng.put("log", mvn.log);
        }

        if(arguments == null) {
        	arguments = new String[0];
        }

        if(script != null) {
            mvn.setArguments(arguments.clone());
            eng.put(ScriptEngine.ARGV, arguments.clone());
            String pomXml = new File(mvn.project.getBasedir(), "pom.xml").getAbsolutePath();
            mvn.setScriptFile(pomXml);
            eng.put(ScriptEngine.FILENAME, pomXml);
            eng.eval(script);
        }

        if(scriptFile != null) {
            mvn.setArguments(arguments.clone());
            eng.put(ScriptEngine.ARGV, arguments.clone());
            mvn.setScriptFile(scriptFile);
            eng.put(ScriptEngine.FILENAME, scriptFile);
            Reader reader = new BufferedReader(new FileReader(scriptFile));
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
    }
}
