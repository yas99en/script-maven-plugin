package io.github.yas99en.mojo.script;

import java.util.List;

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

    @Parameter(property="scriptmvn.cli.arguments")
    private String[] cliArguments;

    @Parameter(property="scriptmvn.cli.scriptFiles")
    private List<String> cliScriptFiles;

    @Override
    public void execute() throws MojoExecutionException {
        executeMojo.setSession(session);
        executeMojo.setEngine(System.getProperty("scriptmvn.cli.engine", "javascript"));
        executeMojo.setArguments(cliArguments);
        executeMojo.setScriptFiles(cliScriptFiles);
        executeMojo.setScript(System.getProperty("scriptmvn.cli.script"));
        executeMojo.setGlobalLog(true);
        executeMojo.setGlobalProject(true);
        executeMojo.setGlobalSettings(true);
        executeMojo.setPrefix("mvn");
        executeMojo.setLog(getLog());
        executeMojo.setPluginContext(getPluginContext());
        executeMojo.execute();
    }

}
