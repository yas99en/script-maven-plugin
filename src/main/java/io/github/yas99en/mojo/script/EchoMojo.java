package io.github.yas99en.mojo.script;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Echo message.
 * This goal does not use any script engine.
 * The maven variables can be used in the message.
 */
@Mojo(name="echo")
public final class EchoMojo extends AbstractMojo {

    @Parameter(property="scriptmvn.echo.message")
    private String echoMessage;

    @Parameter(property="scriptmvn.echo.messages")
    private String[] echoMessages;

    private String echoOutput = "out";

    @Override
    public void execute() throws MojoExecutionException {
        if(System.getProperty("scriptmvn.echo.output") != null) {
            echoOutput = System.getProperty("scriptmvn.echo.output");
        }

        if(echoMessage != null) {
            echo(echoMessage);
        }

        if(echoMessages != null) {
            for(String exp: echoMessages) {
                echo(exp);
            }
        }
    }

    private void echo(String str) {
        if(echoOutput.equals("log")) {
            getLog().info(str);
        } else if(echoOutput.equals("error")) {
            getLog().error(str);
        } else if(echoOutput.equals("warn")) {
            getLog().warn(str);
        } else if(echoOutput.equals("info")) {
            getLog().info(str);
        } else if(echoOutput.equals("debug")) {
            getLog().debug(str);
        } else if(echoOutput.equals("err")) {
            System.err.println(str);
        } else {
            System.out.println(str);
        }
    }
}
