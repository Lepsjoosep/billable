# 04 · Settings & Profiles

After this page you can change how your app behaves without touching the code.

## In one minute

- Settings live in `application.properties`, not in your code.
- `@Value` reads one dial. `@ConfigurationProperties` reads a group of dials.
- A profile such as `dev` is a saved set of dials. Switch it on with `spring.profiles.active`.

## The big idea

Think of a radio with dials: volume and station. It is the same radio at home and at school, but the dials are set differently.

Your app has dials too: the port, a greeting, the database. The dials live in a file, so you can turn them without opening the code. A **profile** is a saved set of dials, like "dev" (on my laptop) or "prod" (on the real server).

## The pieces

| Piece | In kid words |
| --- | --- |
| `application.properties` | The file with the dials. It lives in `src/main/resources`. |
| `application.yml` | The same dials, written with indentation instead of dots. Pick one style, not both. |
| `@Value("${app.greeting}")` | Read one dial into one field. |
| `@ConfigurationProperties(prefix = "app")` | Read a whole group of dials into one object. |
| Profile | A named set of dials. The file is named `application-dev.properties`. |
| `@Profile("dev")` | Only make this bean when the dev profile is on. |
| Environment variable | A dial set by the computer, like `SERVER_PORT=9090`. |
| Command line | A dial set at start time, like `--server.port=9090`. |

## Build it

**Step 1.** Put some dials in `application.properties`.

```
server.port=8081
app.greeting=Hello from the settings file
app.max-items=5
logging.level.com.example=DEBUG
```

Change `com.example` to your own package name.

**Step 2.** Read one dial.

```java
@RestController
public class SettingsController {

    @Value("${app.greeting}")
    private String greeting;

    @GetMapping("/settings/greeting")
    public String greeting() {
        return greeting;
    }
}
```

**Step 3.** Read a group of dials into a record.

```java
@ConfigurationProperties(prefix = "app")
public record AppProps(String greeting, int maxItems) {}
```

Put `@ConfigurationPropertiesScan` on your `DemoApplication` class. Spring matches `app.max-items` to `maxItems` for you. Then ask for `AppProps` in a constructor, like any other bean.

**Step 4.** Make a profile. Create `application-dev.properties`:

```
app.greeting=Hello from DEV
```

Turn it on with the program argument `--spring.profiles.active=dev`. In IntelliJ, open Run, then Edit Configurations, and type `dev` in the Active profiles box. You can also set the environment variable `SPRING_PROFILES_ACTIVE=dev`.

**Who wins when two dials disagree?** The strongest first: command line, then environment variables, then the profile file, then `application.properties`.

Never put passwords in a file you commit to Git. Pass them as environment variables.

## Solve a task

The task says "make X configurable". Do this:

1. Pick a name, like `app.something`.
2. Put a default value in `application.properties`.
3. Read it with `@Value` (one dial) or `@ConfigurationProperties` (a group).
4. Use it in your code instead of a number or text typed in by hand.
5. Change the value, restart, and see the app change. Tick the box.

## Stuck? Look here

- **Could not resolve placeholder.** The key is missing, or it has a typo. The names must match exactly.
- **Port 8080 was already in use.** Stop the old run, or set `server.port` to another number.
- **YAML does nothing.** Use spaces, never tabs. Two spaces for each level. A space after each colon.
- **The profile file is ignored.** The name must be exactly `application-dev.properties`, and the profile must be switched on.
- **You changed a value and nothing happened.** Restart the app.

## Practice Task

Make the shout end with a setting:

- [ ] `application.properties` has `app.shout-suffix=!`.
- [ ] `ShoutService` reads it with `@Value`. (Or try `@ConfigurationProperties`.)
- [ ] `application-dev.properties` sets it to `!!!`.
- [ ] Run once without the profile and once with `dev`. The answers differ: `MIA!` and `MIA!!!`.
- [ ] You changed the behaviour without changing any Java code.

## Help pages

- [Externalized Configuration](https://docs.spring.io/spring-boot/reference/features/external-config.html): where dials can come from, and who wins.
- [Profiles](https://docs.spring.io/spring-boot/reference/features/profiles.html)
- [Logging](https://docs.spring.io/spring-boot/reference/features/logging.html): log levels and log files.
- [Common application properties](https://docs.spring.io/spring-boot/appendix/application-properties/index.html): the list of every dial.

---

[← Previous: 03](03-beans-and-dependency-injection.md) · [Back to start](../README.md) · [Cheat sheet](../cheat-sheet.md) · [Next: 05 Validation & Errors →](05-validation-and-errors.md)
