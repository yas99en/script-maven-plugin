package io.github.yas99en.mojo.script;

import java.util.Arrays;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Executes scripts.
 * Intended to be used from command line.
 */
@Mojo(name="cli")
public final class CliMojo extends ScriptMojo {
    private final ExecuteMojo executeMojo = new ExecuteMojo();

    @Override
    public void execute() throws MojoExecutionException {
        executeMojo.setSession(session);
        executeMojo.setExecution(execution);
        executeMojo.setEngine(System.getProperty("scriptmvn.cli.engine", "javascript"));
        executeMojo.setArguments(getArrayProperty("scriptmvn.cli.arguments"));
        executeMojo.setScript(System.getProperty("scriptmvn.cli.script"));
        executeMojo.setScriptFile(System.getProperty("scriptmvn.cli.scriptFile"));
        executeMojo.setScriptFiles(getListProperty("scriptmvn.cli.scriptFiles"));
        executeMojo.setGlobalLog(true);
        executeMojo.setGlobalProject(true);
        executeMojo.setGlobalSettings(true);
        executeMojo.setRelativeToProject(false);
        executeMojo.setPrefix("mvn");
        executeMojo.setLog(getLog());
        executeMojo.setPluginContext(getPluginContext());
        executeMojo.execute();
    }

    private String[] getArrayProperty(String name) throws MojoExecutionException {
        String value = System.getProperty(name);
        String[] tokens = CsvParser.parseLine(value);
        String[] evaluated = new String[tokens.length];
        for(int i = 0; i < tokens.length; i++) {
            evaluated[i] = evaluate(tokens[i]);
        }
        return evaluated;
    }

    private List<String> getListProperty(String name) throws MojoExecutionException {
        return Arrays.asList(getArrayProperty(name));
    }
}
