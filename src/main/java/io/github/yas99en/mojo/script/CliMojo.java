package io.github.yas99en.mojo.script;

import java.util.Arrays;
import java.util.List;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Executes scripts.
 * Intended to be used from command line.
 */
@Mojo(name="cli")
public final class CliMojo extends AbstractMojo {
    private final ExecuteMojo executeMojo = new ExecuteMojo();

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Parameter(defaultValue = "${mojoExecution}", readonly = true)
    private MojoExecution execution;

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

    private String[] getArrayProperty(String name) {
        String value = System.getProperty(name);
        return CsvParser.parseLine(value);
    }

    private List<String> getListProperty(String name) {
        return Arrays.asList(getArrayProperty(name));
    }
}
