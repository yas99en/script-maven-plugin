package io.github.yas99en.script;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;

public final class Mvn {
    public final MavenSession session;
    public final MavenProject project;
    public final Settings settings;
    public final LogWrapper log;

    Mvn(MavenSession session, Log log) {
        this.session = session;
        this.project = session.getCurrentProject();
        this.settings = session.getSettings();
        this.log = new LogWrapper(log);
    }

    public void fail() throws MojoExecutionException {
        this.fail("failed");
    }

    public void fail(String msg) throws MojoExecutionException {
        throw new MojoExecutionException(msg);
    }
}
