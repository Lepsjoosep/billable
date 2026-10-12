# 03 · Beans & Dependency Injection

After this page you can split code into controller, service and repository, and let Spring connect them.

## In one minute

- A bean is an object that Spring makes for you. You never write `new` for it.
- Ask for a bean in your constructor. Spring hands it over.
- Keep the layers apart: controller, then service, then repository.

## The big idea

Think of a school. The principal does not build every desk herself. She says "bring me 30 desks" and they arrive.

Spring works the same way. You do not write `new GreetingService()`. You say "I need a GreetingService" and Spring hands you one. Objects that Spring makes and looks after are called **beans**. Handing them to you is called **dependency injection**.

## The layers

```
Visitor
   ↓
Controller    takes the order, checks the input
   ↓
Service       does the thinking (the rules)
   ↓
Repository    talks to the database (lesson 06)
```

Each layer only talks to the layer below it.

## The pieces

| Piece | In kid words |
| --- | --- |
| `@Component` | "Spring, make one of me." |
| `@Service` | The same, and it says "I hold the rules." |
| `@Repository` | The same, and it says "I talk to the database." |
| `@Configuration` | "This class makes beans on purpose." |
| `@Bean` | "Spring, use what this method returns as a bean." |
| Constructor injection | "I list what I need in my constructor. Spring brings it." |
| `@Primary` | "If two beans fit, pick me." |
| `@Qualifier("name")` | "Of the two, I want the one called name." |

## Build it

**Step 1.** Make the service.

```java
@Service
public class GreetingService {

    public String greet(String name) {
        return "Hi, " + name;
    }
}
```

**Step 2.** Ask for it in a controller's constructor.

```java
@RestController
public class GreetingController {

    private final GreetingService service;

    public GreetingController(GreetingService service) {
        this.service = service;
    }

    @GetMapping("/greet/{name}")
    public String greet(@PathVariable String name) {
        return service.greet(name);
    }
}
```

With one constructor you do not need `@Autowired`. The word `final` means the field can never be forgotten.

**Step 3.** Make a bean on purpose.

```java
@Configuration
public class AppConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
```

Now any class can ask for a `Clock` in its constructor. In a test you can swap in a fake clock.

## Solve a task

The task says "create a service" or "move the logic out of the controller". Do this:

1. Make a class named `ThingService` and put `@Service` on it.
2. Move the thinking (ifs, loops, math) into its methods.
3. The controller keeps three jobs only: take input, call the service, return the answer.
4. Ask for the service in the controller's constructor.
5. Run it. The same URL must give the same answer as before. Then you did it right.

## Stuck? Look here

- **No qualifying bean of type X.** You forgot `@Service` or `@Component` on X, or X is outside the main package.
- **Expected single matching bean but found 2.** Put `@Primary` on one, or use `@Qualifier` where you ask for it.
- **The dependencies form a cycle.** A needs B and B needs A. Move the shared part into a third class.
- **A field is null.** You wrote `new` yourself. Never `new` a bean. Ask for it in the constructor.

## Practice Task

Move the shouting into a service:

- [ ] A class `ShoutService` with `@Service` has a method `shout(String text)`.
- [ ] `HelloController` asks for `ShoutService` in its constructor. It never writes `new`.
- [ ] `/api/hello/mia/shout` still returns `MIA`.
- [ ] The controller has no `toUpperCase()` left in it.

## Help pages

- [Spring Boot: Spring Beans and Dependency Injection](https://docs.spring.io/spring-boot/reference/using/spring-beans-and-dependency-injection.html): short and clear.
- [Spring Framework: The IoC container](https://docs.spring.io/spring-framework/reference/core/beans.html): the full story.
- [Baeldung: Spring Boot](https://www.baeldung.com/spring-boot): search the site for "dependency injection".

---

[← Previous: 02](02-controllers-and-routes.md) · [Back to start](../README.md) · [Cheat sheet](../cheat-sheet.md) · [Next: 04 Settings & Profiles →](04-settings-and-profiles.md)
