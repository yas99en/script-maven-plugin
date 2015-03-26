package io.github.yas99en.mojo.script;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.PluginParameterExpressionEvaluator;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;

abstract class ScriptMojo extends AbstractMojo {

    @Parameter(defaultValue = "${session}", readonly = true)
    MavenSession session;
    @Parameter(defaultValue = "${mojoExecution}", readonly = true)
    MojoExecution execution;

    protected final String evaluate(String str) throws MojoExecutionException {
        if(str == null) {
            return null;
        }
        ExpressionEvaluator evaluator = new PluginParameterExpressionEvaluator(session, execution);
        try {
            return String.valueOf(evaluator.evaluate(str));
        } catch (ExpressionEvaluationException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    final void setSession(MavenSession session) {
        this.session = session;
    }

    final void setExecution(MojoExecution execution) {
        this.execution = execution;
    }
}