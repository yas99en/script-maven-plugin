package io.github.yas99en.mojo.script;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.PluginParameterExpressionEvaluator;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;

/**
 * Echo message.
 * This goal does not use any script engine.
 * The maven expression can be used in the message.
 */
@Mojo(name="echo")
public final class EchoMojo extends AbstractMojo {

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Parameter(defaultValue = "${mojoExecution}", readonly = true)
    private MojoExecution execution;

    private String echoOutput = "out";

    @Override
    public void execute() throws MojoExecutionException {
        if(System.getProperty("scriptmvn.echo.output") != null) {
            echoOutput = System.getProperty("scriptmvn.echo.output");
        }

        String message = System.getProperty("scriptmvn.echo.message");
        if(message != null) {
            Object obj = evaluate(message);
            echo(String.valueOf(obj));
        }
    }

    private Object evaluate(String str) throws MojoExecutionException {
        ExpressionEvaluator evaluator = new PluginParameterExpressionEvaluator(session, execution);
        try {
            return evaluator.evaluate(str);
        } catch (ExpressionEvaluationException e) {
            throw new MojoExecutionException(e.getMessage(), e);
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
