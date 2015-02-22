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
| global | true | use global variable project, settings, session, log|

# Global variables

* By default you can use `project, settings, session, log, and mvn`.


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
          </script>
          <scriptFile>src/main/javascript/hello.js</scriptFile>
        </configuration>
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
 