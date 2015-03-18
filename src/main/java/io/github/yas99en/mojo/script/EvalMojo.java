package io.github.yas99en.mojo.script;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * evaluate maven variable
 */
@Mojo(name = "eval")
public class EvalMojo extends AbstractMojo {

    @Parameter(property="scriptmvn.eval.expression")
    private String evalExpression;

    @Parameter(property="scriptmvn.eval.expressions")
    private String[] evalExpressions;

    @Parameter(property="scriptmvn.eval.output", defaultValue="out")
    private String evalOutput;

    @Override
    public void execute() throws MojoExecutionException {
        if(evalExpression != null) {
            print(evalExpression);
        }

        if(evalExpressions != null) {
            for(String exp: evalExpressions) {
                print(exp);
            }
        }
    }

    private void print(String str) {
        if(evalOutput.equals("log")) {
            getLog().info(str);
        } else if(evalOutput.equals("err")) {
            System.err.println(str);
        } else {
            System.out.println(str);
        }
    }
}
