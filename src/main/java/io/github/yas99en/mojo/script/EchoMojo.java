package io.github.yas99en.mojo.script;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Echo message.
 * This goal does not use any script engine.
 * The maven expression can be used in the message.
 */
@Mojo(name="echo")
public final class EchoMojo extends ScriptMojo {

    private String echoOutput = "out";

    @Override
    public void execute() throws MojoExecutionException {
        if(System.getProperty("scriptmvn.echo.output") != null) {
            echoOutput = System.getProperty("scriptmvn.echo.output");
        }

        String message = System.getProperty("scriptmvn.echo.message");
        if(message != null) {
            echo(evaluate(message));
        }
    }

    private void echo(String str) {
        if(echoOutput.equals("log")) {
            getLog().info(str);
        } else if(echoOutput.equals("err")) {
            System.err.println(str);
        } else {
            System.out.println(str);
        }
    }
}
