package io.github.yas99en.mojo.script;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Executes scripts.
 * Intended to be used for command line.
 */
@Mojo(name="cli")
public final class CliMojo extends AbstractMojo {
    private final ExecuteMojo executeMojo = new ExecuteMojo();

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Override
    public void execute() throws MojoExecutionException {
        executeMojo.setSession(session);
        executeMojo.setEngine(System.getProperty("scriptmvn.cli.engine", "javascript"));
        executeMojo.setArguments(getArguments() );
        executeMojo.setScriptFiles(null);
        executeMojo.setScript(System.getProperty("scriptmvn.cli.script"));
        executeMojo.setScriptFile(System.getProperty("scriptmvn.cli.scriptFile"));
        executeMojo.setGlobalLog(true);
        executeMojo.setGlobalProject(true);
        executeMojo.setGlobalSettings(true);
        executeMojo.setRelativeToProject(false);
        executeMojo.setPrefix("mvn");
        executeMojo.setLog(getLog());
        executeMojo.setPluginContext(getPluginContext());
        executeMojo.execute();
    }

    private String[] getArguments() {
        String arguments = System.getProperty("scriptmvn.cli.arguments");
        return CsvParser.splitByCommna(arguments);
    }
    
}
