# 11 · Build, Run & Ship

After this page you can pack your app into one file, check that it is healthy, and hand it in cleanly.

## In one minute

- `./mvnw clean package` makes one JAR file. `java -jar` runs it.
- `/actuator/health` tells you if the app is alive.
- Before you hand in: tests green, a fresh clone runs, no secrets, README written.

## The big idea

Think of a lunchbox. You put everything you need in one box: food, fork, napkin. You can carry it anywhere and eat.

A Spring Boot app can be packed the same way. The box is one **JAR file**. It has your code, the libraries, and even the web server inside. Anyone with Java can run it with one command.

## The commands

Use `./mvnw` on Mac and Linux. On Windows use `mvnw.cmd`. The `mvnw` file is the Maven helper that came with your project, so you do not have to install Maven.

| Command | What it does |
| --- | --- |
| `./mvnw spring-boot:run` | Start the app. |
| `./mvnw test` | Run all tests. |
| `./mvnw clean package` | Build the JAR into the `target` folder. This also runs the tests. |
| `java -jar target/demo-0.0.1-SNAPSHOT.jar` | Run the JAR. Your file name will be different. |
| `./mvnw clean package -DskipTests` | Build without tests. Use only when you must. |

## Build it

**Step 1.** Add a health check. Add the **Actuator** dependency (`spring-boot-starter-actuator`) and one dial:

```
management.endpoints.web.exposure.include=health,info
```

Open `/actuator/health`. You should see `{"status":"UP"}`. Real servers ask this question all day to know your app is alive.

**Step 2.** Build and run the JAR.

```
./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar --server.port=9090
```

The `--server.port=9090` part is a dial from lesson 04. It works on a JAR too.

**Step 3 (optional).** Put it in a Docker box. Make a file named `Dockerfile` next to `pom.xml`:

```
FROM eclipse-temurin:21-jre
COPY target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

```
docker build -t my-app .
docker run -p 8080:8080 my-app
```

## Before you hand in a task

- [ ] `./mvnw clean test` is green.
- [ ] The app starts from a fresh clone of your repository.
- [ ] Every item on the task checklist works, and you tried each one.
- [ ] No passwords or secrets are in the code. They come from environment variables.
- [ ] `target/` is in `.gitignore` (start.spring.io adds it for you).
- [ ] There is a short `README.md`: what the app does, how to run it, how to test it.
- [ ] Your commit message says what you did and why, in one or two lines.

## Solve a task

The task says "package", "run" or "deploy". Do this:

1. Run `./mvnw clean package`. If it fails, read the first `ERROR` line.
2. Run the JAR with `java -jar`. Use the same Java version as the build.
3. Open a URL that proves it works, such as `/actuator/health`.
4. Write the run command in your README.

## Stuck? Look here

- **Port 8080 is already in use.** Another run is still going. Stop it, or use `--server.port=9090`.
- **no main manifest attribute.** The JAR was not built by the Spring Boot Maven plugin. Check that your `pom.xml` still has `spring-boot-maven-plugin`.
- **UnsupportedClassVersionError.** Your `java` is older than the one that built the JAR. Check `java -version`.
- **mvnw: Permission denied.** Run `chmod +x mvnw` once.
- **Build passes on my laptop, fails on the grader's.** A test depends on your machine. Make tests set up their own data.

## Practice Task

Hand in the book shelf like a pro:

- [ ] `./mvnw clean package` passes.
- [ ] `java -jar` runs it on port 9090.
- [ ] `/actuator/health` says `UP`.
- [ ] `README.md` says how to run it and how to test it.
- [ ] No password sits in the code.

## Help pages

- [Spring Boot Actuator (guide)](https://spring.io/guides/gs/actuator-service)
- [Spring Boot: Actuator reference](https://docs.spring.io/spring-boot/reference/actuator/index.html)
- [Spring Boot Docker (guide)](https://spring.io/guides/gs/spring-boot-docker)
- [Docker: Get started](https://docs.docker.com/get-started/)
- [Maven in 5 minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

---

[← Previous: 10](10-testing.md) · [Back to start](../README.md) · [Cheat sheet](../cheat-sheet.md) · [Sources & help pages →](../sources.md)
