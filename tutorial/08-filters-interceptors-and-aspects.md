# 08 · Filters, Interceptors & Aspects

After this page you can pick the right door for code that must run on many requests.

## In one minute

- Filter is the front gate. Interceptor is the hallway, and it knows the controller. Aspect follows a method.
- Sign each one up the normal way. Never call it from a controller.
- Ask: do I need the raw request, the controller, or a method? The answer picks the tool.

## The big idea

A school has guards at several doors. One guard stands at the front gate. One stands in the hallway. One follows the class around. They all watch, but each one sees different things.

In lesson 01 you built the gate guard (a **filter**). Here you meet the other two: the hallway guard (an **interceptor**) and the follower (an **aspect**).

## The order of the doors

```
Visitor
   ↓
Filter                        the front gate: sees the raw request
   ↓
DispatcherServlet             Spring MVC's front desk
   ↓
Interceptor.preHandle         the hallway: knows which controller is next
   ↓
Controller → Service          an Aspect can wrap these methods
   ↓
Interceptor.afterCompletion
   ↓
Filter, on the way out
```

## Which tool should I use?

| Tool | Where it runs | What it sees | Use it to |
| --- | --- | --- | --- |
| Filter | Around the whole DispatcherServlet | Raw request and response | Time every request (Task 4), log, set early headers |
| Interceptor | Inside DispatcherServlet, around the controller | The request, and which controller will run | Audit, or check something for some controllers only |
| `@RestControllerAdvice` | When something throws | The exception | One place for all error answers (lesson 05) |
| Aspect (`@Aspect`) | Around any bean's method | The method and its arguments | Time or log service methods |

## Build it

**Step 1.** An interceptor.

```java
@Component
public class AuditInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuditInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        log.info("About to run {} {}", request.getMethod(), request.getRequestURI());
        return true;   // true means: keep going
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) {
        log.info("Finished with status {}", response.getStatus());
    }
}
```

**Step 2.** An interceptor does nothing until you sign it up. Do that in a config class.

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuditInterceptor audit;

    public WebConfig(AuditInterceptor audit) {
        this.audit = audit;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(audit).addPathPatterns("/api/**");
    }
}
```

**Step 3.** An aspect. It is the same stopwatch idea as lesson 01, aimed at service methods.

```java
@Aspect
@Component
public class TimingAspect {

    private static final Logger log = LoggerFactory.getLogger(TimingAspect.class);

    @Around("execution(* com.example.demo.service..*(..))")
    public Object time(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        try {
            return pjp.proceed();
        } finally {
            long ms = (System.nanoTime() - start) / 1_000_000;
            log.info("{} took {} ms", pjp.getSignature().toShortString(), ms);
        }
    }
}
```

An aspect needs an AOP library. Change com.example.demo.service in the code above to your own service package. In Boot 3 the starter is `spring-boot-starter-aop`. In Boot 4 look for `spring-boot-starter-aspectj` in the migration guide's list of starters.

## Solve a task

The task says "run this code for every request" or "for every service call". Pick the door:

1. Must it see every request, even a 404 or a static file? Use a **filter**.
2. Must it know which controller will answer? Use an **interceptor**.
3. Must it wrap service or repository methods? Use an **aspect**.
4. Must it turn errors into answers? Use **`@RestControllerAdvice`**.
5. Write it. Sign it up the normal way. Never call it from a controller.
6. Run it and read the log.

## Stuck? Look here

- **My interceptor never runs.** You forgot `addInterceptors`, or the path pattern does not match.
- **My aspect never runs.** The AOP starter is missing, the target is not a bean, or one method calls another method in the same class (that skips Spring's wrapper).
- **My filter runs twice.** Extend `OncePerRequestFilter`.
- **I cannot add a header at the end.** By then the response may be sent. This is the same problem as in lesson 01.

## Practice Task

Write an interceptor that logs which controller method runs:

- [ ] It implements `HandlerInterceptor` and is a `@Component`.
- [ ] It is signed up in `addInterceptors`, only for `/api/**`.
- [ ] The log says something like `Running getAll`.
- [ ] The page `/notes` does not trigger it.

Hint: first check `handler instanceof HandlerMethod`. Then use `((HandlerMethod) handler).getMethod().getName()`.

## Help pages

- [Filters (Spring MVC)](https://docs.spring.io/spring-framework/reference/web/webmvc/filters.html)
- [Interception (HandlerInterceptor)](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet/handlermapping-interceptor.html)
- [Aspect Oriented Programming with Spring](https://docs.spring.io/spring-framework/reference/core/aop.html)
- [Spring Boot 4.0 Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide): the list of starters.

---

[← Previous: 07](07-web-pages-with-thymeleaf.md) · [Back to start](../README.md) · [Cheat sheet](../cheat-sheet.md) · [Next: 09 Security →](09-security.md)
