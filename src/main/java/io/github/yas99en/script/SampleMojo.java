package io.github.yas99en.script;

import java.io.File;
import java.util.Map;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;

@Mojo( name = "sample")
public class SampleMojo extends AbstractMojo {


    @Parameter( defaultValue = "${session}", readonly = true )
    private MavenSession session;

    @Parameter( defaultValue = "${project}", readonly = true )
    private MavenProject project;

    @Parameter( defaultValue = "${mojoExecution}", readonly = true )
    private MojoExecution mojo;

    @Parameter( defaultValue = "${plugin}", readonly = true ) // Maven 3 only
    private PluginDescriptor plugin;

    @Parameter( defaultValue = "${settings}", readonly = true )
    private Settings settings;

    @Parameter( defaultValue = "${project.basedir}", readonly = true )
    private File basedir;

    @Parameter( defaultValue = "${project.build.directory}", readonly = true )
    private File target;

    
    public void execute() throws MojoExecutionException {
        for(Object o : getPluginContext().entrySet()) {
            @SuppressWarnings("unchecked")
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>)o;
            System.out.println("---");
            System.out.println(entry.getKey() + ": " + entry.getValue().getClass());
        }
    }
}
