package io.github.yas99en.mojo.script;

import java.util.List;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Executes one line script.
 */
@Mojo(name = "oneline")
public class OneLineMojo extends AbstractMojo {
    private final ExecuteMojo executeMojo = new ExecuteMojo();

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Parameter(property="scriptmvn.engine1", defaultValue = "javascript")
    private String engine1;

    @Parameter(property="scriptmvn.arguments1")
    private String[] arguments1;

    @Parameter(property="scriptmvn.scriptFiles1")
    private List<String> scriptFiles1;

    @Parameter(property="scriptmvn.scriptFile1")
    private String scriptFile1;

    @Parameter(property="scriptmvn.script1")
    private String script1;

    @Override
    public void execute() throws MojoExecutionException {
        executeMojo.setSession(session);
        executeMojo.setEngine(engine1);
        executeMojo.setArguments(arguments1);
        executeMojo.setScriptFiles(scriptFiles1);
        executeMojo.setScriptFile(scriptFile1);
        executeMojo.setScript(script1);
        executeMojo.setGlobalLog(true);
        executeMojo.setGlobalProject(true);
        executeMojo.setGlobalSettings(true);
        executeMojo.setPrefix("mvn");
        executeMojo.setLog(getLog());
        executeMojo.execute();
    }

}
