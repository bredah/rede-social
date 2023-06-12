# Debug

- [Enable to remote debug](#enable-to-remote-debug)
  - [Via CLI (JVM arg)](#via-cli-jvm-arg)
  - [Via POM](#via-pom)
- [Connect to Remote Debug](#connect-to-remote-debug)
  - [VS Code](#vs-code)
  - [IntelliJ](#intellij)

## Enable to remote debug

### Via CLI (JVM arg)

Add JVM args when execute via maven:

```sh
./mvnw -pl :social-network-web clean spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.jvmArguments=\"-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005\"
```

### Via POM

This method active the debug mode by `default`:

```xml
<project>
  ...
  <build>
    ...
    <plugins>
      ...
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <version>2.2.6.RELEASE</version>
        <configuration>
          <jvmArguments>
            -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005
          </jvmArguments>
        </configuration>
        ...
      </plugin>
      ...
    </plugins>
    ...
  </build>
  ...
</project>
```

> Remember to separete this config in an appropriate profile.

## Connect to Remote Debug

### VS Code

They are two ways:

- Attach to PID (Process ID):

```json
{
  "type": "java",
  "name": "Debug (Attach by Process ID)",
  "request": "attach",
  "processId": "${command:PickJavaProcess}"
}
```

- Attach to default settings:

```json
{
  "type": "java",
  "name": "Debug (Attach)",
  "request": "attach",
  "hostName": "localhost",
  "port": 5005
}
```

### IntelliJ

TBD
