# script-maven-plugin

Executes scripts in maven build.
This plugin is based on 'JSR 223: Scripting for the Java Platform'.


## Goals

* `script:execute`: executes scripts

## Configuration Options

| Option | Default Value                 | Explanation  |
| --------------- | :---------------------------: | ------------ |
| engine |javascript|script engine name. e.g. rhino, nashorn, ruby|
| script ||script|
| scriptFile ||script file name to be executed|
| prefix |mvn|By default, you can access the maven environment via global variabl mvn|
| global | true | use global variables, project, settings, session, log|

# Global variables

* By default, you can use `project, settings, session, log, and mvn`.
* project
 - org.apache.maven.project.MavenProject
 - http://maven.apache.org/ref/3.2.5/maven-core/apidocs/org/apache/maven/project/MavenProject.html
* session
 - org.apache.maven.execution
 - http://maven.apache.org/ref/3.2.5/maven-core/apidocs/org/apache/maven/execution/MavenSession.html
* settings
 - org.apache.maven.settings.Settings
 - http://maven.apache.org/ref/3.2.5/maven-settings/apidocs/org/apache/maven/settings/Settings.html
* log
 - wrapper for org.apache.maven.plugin.logging.Log
 - http://maven.apache.org/ref/3.2.5/maven-plugin-api/apidocs/org/apache/maven/plugin/logging/Log.html
* mvn
 - It provides project, session, settings, log.
 - It provides two fail() methods.
   + public void fail(String msg) throws MojoExecutionException: fails build with message.
   + public void fail() throws MojoExecutionException: fails build with default message.


## Example Configurations

javascript

```xml:javascript
      <plugin>
        <groupId>io.github.yas99en</groupId>
        <artifactId>script-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <executions>
          <execution>
            <phase>prepare-package</phase>
            <goals>
                <goal>execute</goal>
            </goals>
          </execution>
        </executions>

        <configuration>
          <engine>javascript</engine>
          <script>
          log.info("hello from pom");
          log.info(project.name);
          log.info(project.version);
          log.info(project.basedir);
          log.info(project.build.finalName);
          log.info(project.build.directory);
          log.info(project.build.outputDirectory);
          log.info(settings.localRepository);
          log.info(session.executionRootDirectory);
          </script>
        </configuration>
      </plugin>
```

javascript from file

```xml:javascript
      <plugin>
        <groupId>io.github.yas99en</groupId>
        <artifactId>script-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <executions>
          <execution>
            <phase>prepare-package</phase>
            <goals>
                <goal>execute</goal>
            </goals>
          </execution>
        </executions>

        <configuration>
          <engine>javascript</engine>
          <scriptFile>src/main/javascript/hello.js</scriptFile>
        </configuration>
      </plugin>
```

rhino for jdk8 or later

```xml:javascript
      <plugin>
        <groupId>io.github.yas99en</groupId>
        <artifactId>script-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <executions>
          <execution>
            <phase>prepare-package</phase>
            <goals>
                <goal>execute</goal>
            </goals>
          </execution>
        </executions>

        <configuration>
          <engine>javascript</engine>
          <scriptFile>src/main/javascript/hello.js</scriptFile>
        </configuration>
        <dependencies>
          <dependency>
            <groupId>de.christophkraemer</groupId>
            <artifactId>rhino-script-engine</artifactId>
            <version>1.0.1</version>
          </dependency>
        </dependencies>
      </plugin>
```


ruby

```xml
      <plugin>
        <groupId>io.github.yas99en</groupId>
        <artifactId>script-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <executions>
          <execution>
            <phase>prepare-package</phase>
            <goals>
                <goal>execute</goal>
            </goals>
          </execution>
        </executions>

        <configuration>
          <engine>ruby</engine>
          <script>
          puts("hello ruby from pom " + $project.basedir.toString);
          </script>
        </configuration>
        <dependencies>
          <dependency>
            <groupId>org.jruby</groupId>
            <artifactId>jruby-complete</artifactId>
            <version>1.7.19</version>
          </dependency>
        </dependencies>
       </plugin>
```

groovy

```xml
      <plugin>
        <groupId>io.github.yas99en</groupId>
        <artifactId>script-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <executions>
          <execution>
            <phase>prepare-package</phase>
            <goals>
                <goal>execute</goal>
            </goals>
          </execution>
        </executions>

        <configuration>
          <engine>groovy</engine>
          <script>
          log.info("hello groovy " + project.basedir.toString());
          </script>
        </configuration>
        <dependencies>
          <dependency>
            <groupId>org.codehaus.groovy</groupId>
            <artifactId>groovy-all</artifactId>
            <version>2.4.0</version>
          </dependency>
        </dependencies>
       </plugin>
```
 