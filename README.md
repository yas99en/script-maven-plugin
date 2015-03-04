# script-maven-plugin

Executes scripts in maven build.
This plugin is based on 'JSR 223: Scripting for the Java Platform'.


## Goal: execute

* `script:execute`: executes scripts

## Configuration Options

| Option | Default Value | Explanation  |
| ------ |:------------: | ------------ |
| engine |javascript|script engine name. e.g. rhino, nashorn, ruby. It is passed to ScriptEngineManager.getEngineByName(). The goal `script:list` is available to confirm the engine name.|
| arguments | [] | command line arguments for the script.  **sytem property**: scriptmvn.arguments|
| script ||inline script|
| scriptFile ||script file name to be executed|
| scriptFiles ||list of script files to be executed|
| prefix |mvn| the global variable name to access the maven environment. If empty, no global variable for this purpose is created.|
| globalProject | true | use global variable `project`.|
| globalSettings | true | use global variable `settings`.|
| globalLog | true | use global variable `log`.|


## Global variables

* By default, `project, settings, log, and mvn` are available.
* project
 - [org.apache.maven.project.MavenProject](http://maven.apache.org/ref/3.2.5/maven-core/apidocs/org/apache/maven/project/MavenProject.html)
* settings
 - [org.apache.maven.settings.Settings](http://maven.apache.org/ref/3.2.5/maven-settings/apidocs/org/apache/maven/settings/Settings.html)
* log
 - wrapper for [org.apache.maven.plugin.logging.Log](http://maven.apache.org/ref/3.2.5/maven-plugin-api/apidocs/org/apache/maven/plugin/logging/Log.html)
* mvn

|member|description|
| ---- | ---- |
|project|same as above|
|settings|same as above|
|log|same as above|
|session|[org.apache.maven.execution.MavenSession](http://maven.apache.org/ref/3.2.5/maven-core/apidocs/org/apache/maven/execution/MavenSession.html)|
|arguments|String array provided by arguments property.|
|scriptFile|current executing script file|
|public void fail(String msg) throws MojoExecutionException| fails build with message.|
|public void fail() throws MojoExecutionException|fails build with default message.|


## Usage

### Basic

pom.xml

```xml
      <plugin>
        <groupId>io.github.yas99en</groupId>
        <artifactId>script-maven-plugin</artifactId>
        <version>0.0.2</version>

        <configuration>
          <engine>javascript</engine>
          <script>
          log.info("hello from pom");
          </script>
        </configuration>
      </plugin>
```

execution

```
  $ mvn script:execute
```

### Auto Execution

pom.xml

```xml
      <plugin>
        <groupId>io.github.yas99en</groupId>
        <artifactId>script-maven-plugin</artifactId>
        <version>0.0.2</version>
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
          </script>
        </configuration>
      </plugin>
```

The script is executed in prepare-pakcage phase.

```
  $ mvn install
```

### Arguments and Filename

* This plugin sets ScriptEngine.FILENAME and ScriptEngine.ARGV to script engines, but many engines ignore these properties.
  + jruby supports both properties.
* The filename and command line arguments can be accessed via the global variable `mvn`.


```xml
      <plugin>
        <groupId>io.github.yas99en</groupId>
        <artifactId>script-maven-plugin</artifactId>
        <version>0.0.2</version>

        <configuration>
          <engine>ruby</engine>
          <script>
          p $mvn.scriptFile
          p $0
          p $mvn.arguments
          p ARGV
          </script>
          <arguments>
            <argument>aaa</argument>
            <argument>xyz</argument>
          </arguments>
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

execution

```
  $ mvn script:execute
```

The arguments can be passed by command line.

```
  $ mvn -Dscript.arguments="123,zzz" script:execute
```

### Mixed Multiple files and inline script


```xml
      <plugin>
        <groupId>io.github.yas99en</groupId>
        <artifactId>script-maven-plugin</artifactId>
        <version>0.0.2</version>

        <configuration>
          <engine>javascript</engine>
          <scriptFiles>
            <scriptFile>src/main/javascript/libA.js</scriptFile>
            <scriptFile>src/main/javascript/libB.js</scriptFile>
          </scriptFiles>
          <scriptFile>src/main/javascript/hello.js</scriptFile>
          <script>
          log.info("hello from pom");
          </script>
          <arguments>
            <argument>aaa</argument>
            <argument>xyz</argument>
          </arguments>
        </configuration>
      </plugin>
```
The elements `script`, `scriptFile` and `scriptFiles` can be put any order.
But the execution order is alway s `scriptFiles`, `scriptFile`, `script`.


## Example Configurations

javascript

```xml
      <plugin>
        <groupId>io.github.yas99en</groupId>
        <artifactId>script-maven-plugin</artifactId>
        <version>0.0.2</version>

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
          log.info(mvn.session.executionRootDirectory);
          </script>
          <arguments>
            <argument>aaa</argument>
            <argument>xyz</argument>
          </arguments>
        </configuration>
      </plugin>
```

javascript from file

```xml
      <plugin>
        <groupId>io.github.yas99en</groupId>
        <artifactId>script-maven-plugin</artifactId>
        <version>0.0.2</version>

        <configuration>
          <engine>javascript</engine>
          <scriptFile>src/main/javascript/hello.js</scriptFile>
          <arguments>
            <argument>aaa</argument>
            <argument>xyz</argument>
          </arguments>
        </configuration>
      </plugin>
```

javascript from multile files

```xml
      <plugin>
        <groupId>io.github.yas99en</groupId>
        <artifactId>script-maven-plugin</artifactId>
        <version>0.0.2</version>

        <configuration>
          <engine>javascript</engine>
          <scriptFiles>
            <scriptFile>src/main/javascript/libA.js</scriptFile>
            <scriptFile>src/main/javascript/libB.js</scriptFile>
          </scriptFiles>
          <arguments>
            <argument>aaa</argument>
            <argument>xyz</argument>
          </arguments>
        </configuration>
      </plugin>
```

rhino for jdk8 or later

```xml
      <plugin>
        <groupId>io.github.yas99en</groupId>
        <artifactId>script-maven-plugin</artifactId>
        <version>0.0.2</version>

        <configuration>
          <engine>javascript</engine>
          <scriptFile>src/main/javascript/hello.js</scriptFile>
          <arguments>
            <argument>aaa</argument>
            <argument>xyz</argument>
          </arguments>
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
        <version>0.0.2</version>

        <configuration>
          <engine>ruby</engine>
          <script>
          puts("hello ruby from pom " + $project.basedir.toString);
          </script>
          <arguments>
            <argument>aaa</argument>
            <argument>xyz</argument>
          </arguments>
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
        <version>0.0.2</version>

        <configuration>
          <engine>groovy</engine>
          <script>
          log.info("hello groovy " + project.basedir.toString());
          </script>
          <arguments>
            <argument>aaa</argument>
            <argument>xyz</argument>
          </arguments>
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
python

```xml
      <plugin>
        <groupId>io.github.yas99en</groupId>
        <artifactId>script-maven-plugin</artifactId>
        <version>0.0.2</version>

        <configuration>
          <engine>python</engine>
          <script>
log.info("hello python " + project.basedir.toString());
          </script>
          <arguments>
            <argument>aaa</argument>
            <argument>xyz</argument>
          </arguments>
        </configuration>
        <dependencies>
          <dependency>
            <groupId>org.python</groupId>
            <artifactId>jython</artifactId>
            <version>2.5.3</version>
          </dependency>
        </dependencies>
       </plugin>
```
## AntBuilder

```
      <plugin>
        <groupId>io.github.yas99en</groupId>
        <artifactId>script-maven-plugin</artifactId>
        <version>0.0.2</version>
        
        <configuration>
          <engine>rhino</engine>
          <arguments>
            <argument>aa</argument>
            <argument>xx</argument>
          </arguments>
          <script>
            log.info("hello from pom");
            var ant = new Packages.groovy.util.AntBuilder();
            ant.invokeMethod("echo", "hello");
            ant.invokeMethod("copy", {file: "pom.xml", tofile: "copied"});
            ant.invokeMethod("delete", {file: "copied"});
          </script>
        </configuration>
        <dependencies>
          <dependency>
            <groupId>de.christophkraemer</groupId>
            <artifactId>rhino-script-engine</artifactId>
            <version>1.0.1</version>
          </dependency>
          <dependency>
            <groupId>org.codehaus.groovy</groupId>
            <artifactId>groovy-all</artifactId>
            <version>2.4.0</version>
          </dependency>
          <dependency>
            <groupId>org.apache.ant</groupId>
            <artifactId>ant</artifactId>
            <version>1.9.4</version>
          </dependency>
        </dependencies>
      </plugin>
```


## Goal: list

* `script:list`: list all script engine information. Any one of `Names` can be used as the option `engine`.

execution

```
  $ mvn script:list
```
