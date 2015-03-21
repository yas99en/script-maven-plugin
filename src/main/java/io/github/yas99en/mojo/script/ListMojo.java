package io.github.yas99en.mojo.script;

import java.util.List;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Lists all script engine information.
 */
@Mojo(name="list")
public final class ListMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException {
        try {
            listScriptEngines();
        } catch (ScriptException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private static void listScriptEngines() throws ScriptException {
        ScriptEngineManager m = new ScriptEngineManager();

        List<ScriptEngineFactory> factories = m.getEngineFactories();
        for (ScriptEngineFactory factory: factories) {
            System.out.println("");
            dumpEngineFactory(factory);
        }
        System.out.println("");
    }

    private static void dumpEngineFactory(ScriptEngineFactory factory) {
        System.out.println("    LanguageName   : "+factory.getLanguageName());
        System.out.println("    LanguageVersion: "+factory.getLanguageVersion() );
        System.out.println("    EngineName     : "+factory.getEngineName());
        System.out.println("    EngineVersion  : "+factory.getEngineVersion());
        System.out.println("    Extensions     : "+factory.getExtensions());
        System.out.println("    MimeTypes      : "+factory.getMimeTypes());
        System.out.println("    Names          : "+factory.getNames());
    }

    public static void main(String[] args) throws ScriptException {
        listScriptEngines();
    }
}
