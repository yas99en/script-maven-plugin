package io.github.yas99en.mojo.script;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * evaluate maven variable
 */
@Mojo(name = "mvneval")
public class MvnEvalMojo extends AbstractMojo {

    @Parameter(property="scriptmvn.expression")
    private String expression;

    @Parameter(property="scriptmvn.expressions")
    private String[] expressions;

    @Parameter(property="scriptmvn.output", defaultValue="sys")
    private String output;

    @Override
    public void execute() throws MojoExecutionException {
        if(expression != null) {
            print(expression);
        }

        if(expressions != null) {
            for(String exp: expressions) {
                print(exp);
            }
        }
    }

    private void print(String str) {
        if(output.equals("log")) {
            getLog().info(str);
        } else if(output.equals("err")) {
            System.err.println(str);
        } else {
            System.out.println(str);
        }
    }
}
