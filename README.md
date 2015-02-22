# script-maven-plugin

Executes scripts in maven build

## Goals

* `script:execute`: runs scripts

## Configuration Options
| Option | Default Value                 | Explanation  |
| --------------- | :---------------------------: | ------------ |
| engine |javascript|script engine name. e.g. rhino, nashorn, jruby|
| script ||script|
| scriptFile ||script file name to be executed|
| prefix |mvn|By default, you can access the maven environment via global variabl mvn|
| global | true | use global variable project, settings, session, log|


## Example Configurations

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
          print("hello from pom");
          print(print);
          </script>
          <scriptFile>src/main/javascript/hello.js</scriptFile>
        </configuration>
      </plugin>
```
