# 01 · Filters: Measure Every Request (Task 4)

Small steps. Short words. You can do this.

## In one minute

- A **filter** is a guard at the gate. It sees every request before and after your controller.
- The recipe: click START, call `chain.doFilter(...)`, click STOP, write the time in the log.
- Extend `OncePerRequestFilter` and add `@Component`. Spring signs the filter up for you.

## The 5 Magic Steps

Use these 5 steps for every task at the end of a lesson.

1. **Read it.** Read the task two times. Say it out loud in your own words.
2. **Make a checklist.** Find the words "Acceptance criteria". Each line is one box to tick.
3. **Find the spot.** Ask: where does my code go in the app?
4. **Build one tiny piece.** Do not build it all at once. Make one small thing. Then the next one.
5. **Test it.** Run the app. Look at what happens. Tick the boxes.

Stuck? Go back one step. That is okay.

## Our Example: Task 4, "The Stopwatch"

We will use the 5 magic steps on a real task. The task is: **measure how long every request takes** (Spring MVC).

### The big idea

Think of a school gate.

- A kid walks up to the gate. That is a **request**.
- A guard stands at the gate. The guard is a **filter**.
- The kid goes in and gets an answer. That is a **response**.

In our task, the guard has a **stopwatch**.

- The guard clicks START when the kid comes.
- The guard clicks STOP when the kid is done.
- The guard writes the time in a notebook. The notebook is the **log**.

That is the whole task. Really!

### Step 1: Read the task

Say it in your own words:

> "Time every visit. Write the time in the log."

The log line must look like this:

`GET / -> 200 in 12 ms`

- `GET` is the kind of visit.
- `/` is the page.
- `200` means "all good".
- `12 ms` is how long it took. (ms means milliseconds. That is a tiny bit of time. 1000 ms is 1 second.)

### Step 2: Make your checklist

Copy these into your notes:

- [ ] The log has one line for every request.
- [ ] The filter is signed up the normal way. (No controller calls it.)
- [ ] I can point to the spot on the lifecycle picture.
- [ ] I wrote 2 sentences about why there is no header.

### Step 3: Find the spot

Here is the picture. The guard is in the middle of the road.

```
Kid (request)
   ↓
🚪 FILTER   ← click START
   ↓
Controller does the work
   ↓
🚪 FILTER   ← click STOP and write in the log
   ↓
Answer goes back (response)
```

The filter is before the controller on the way in. It is after the controller on the way out. When someone asks "where does your code run?", point here.

### Step 4: Make the file

Make a new Java class. Name it `RequestTimingFilter`.

Put it here:

`src/main/java/com/example/yourapp/web/`

Use your own package name instead of `com.example.yourapp`. Keep it inside your main package. Spring only looks there.

### Step 5: Build it in tiny pieces

**Piece A: make the class a guard.**

```java
public class RequestTimingFilter extends OncePerRequestFilter {
}
```

`extends OncePerRequestFilter` means "I am a guard." Spring gives you guard powers.

See a red line? Press `Alt+Enter`. Pick **Implement methods**. Your IDE writes the `doFilterInternal` method for you.

**Piece B: add two stickers above the class.**

```java
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
```

- `@Component` says: "Spring, please find me and sign me up."
- `@Order(...)` says: "Put me first at the gate."

**Piece C: get a notebook.** Put this inside the class, above the method:

```java
private static final Logger log =
        LoggerFactory.getLogger(RequestTimingFilter.class);
```

**Piece D: fill in the method.** Put this inside `doFilterInternal`:

```java
long start = System.nanoTime();          // click START
try {
    chain.doFilter(request, response);   // let the kid in
} finally {
    long ms = (System.nanoTime() - start) / 1_000_000;   // click STOP
    log.info("{} {} -> {} in {} ms",
            request.getMethod(), request.getRequestURI(),
            response.getStatus(), ms);   // write in the notebook
}
```

- `finally` means "do this even if something breaks."
- Each `{}` is a hole. The values fall into the holes, in order.

**Red words?** Press `Alt+Enter` on them and pick the import. Choose the ones that start with `org.slf4j`, `jakarta.servlet` and `org.springframework`.

### Step 6: See it all together

Your finished file should look like this:

```java
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestTimingFilter extends OncePerRequestFilter {

    private static final Logger log =
            LoggerFactory.getLogger(RequestTimingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        long start = System.nanoTime();
        try {
            chain.doFilter(request, response);
        } finally {
            long ms = (System.nanoTime() - start) / 1_000_000;
            log.info("{} {} -> {} in {} ms",
                    request.getMethod(), request.getRequestURI(),
                    response.getStatus(), ms);
        }
    }
}
```

### Step 7: Run it and test it

1. Start your app.
2. Open a terminal. Type: `curl -i http://localhost:8080/`
3. Look at your app's log window.
4. You should see a line like: `GET / -> 200 in 12 ms`

On Windows PowerShell, type `curl.exe -i http://localhost:8080/` (with the `.exe`), or just open the address in a browser. The log line appears either way.

See it? Tick box 1.

Tick box 2 too. You used `@Component`, and no controller calls the filter.

### Step 8: Point at the picture

Say this out loud:

> "My code runs in the filter chain. It starts before the DispatcherServlet and it finishes after it."

Tick box 3.

### Step 9: Write the 2 sentences

The task says to explain why there is no header. Make a file called `docs/request-lifecycle.md`.

**The kid version (so you understand it):**

> The answer is already on its way to the user before my stopwatch stops. So it is too late to stick a time on it, and I write the time in the log instead.

**The grown-up version (paste this into the file):**

> Our filter only gets control back after `chain.doFilter()` returns, and by then the response may already be committed, so a late `setHeader("X-Request-Time", ...)` is silently ignored. Setting the header before the chain runs does not work either, because the duration is not known yet, so we log it instead.

Tick box 4.

### Step 10: Save your work

Type these in the terminal:

```
git add .
git commit -m "Add RequestTimingFilter to log per-request duration"
```

All four boxes ticked? **You did Task 4!**

## Stuck? Look Here

- **Nothing in the log.** You forgot `@Component`, or your class is outside the main package.
- **Red words everywhere.** You need imports. Press `Alt+Enter` on each red word.
- **`jakarta` is red.** Older Spring Boot uses `javax.servlet` instead of `jakarta.servlet`.
- **Status says 200 when the page crashed.** That is normal for this task. It is okay.
- **The app will not start.** Read the first red error line in the log. It often tells you what is wrong.

## Tiny Word Book

- **Request:** a visitor knocking on your app's door.
- **Response:** the answer the app sends back.
- **Filter:** a guard that sees every visitor.
- **Controller:** the helper that does the real work.
- **Log:** the notebook where the app writes things down.
- **ms:** milliseconds. A tiny bit of time.
- **Annotation:** a sticker that starts with `@`. It tells Spring what to do.

## Next Task? Do It Again

Use the same 5 magic steps every time:

1. Read it.
2. Make a checklist.
3. Find the spot.
4. Build one tiny piece.
5. Test it.

You are getting better with every task. Keep going!

## Practice Task

Make the filter shout when a request is slow. Add a `GET /slow` endpoint that waits 300 ms with `Thread.sleep(300)` (the method needs `throws InterruptedException`). Then change the filter:

- [ ] A request that takes 200 ms or more is logged with `log.warn`.
- [ ] A fast request is still logged with `log.info`.
- [ ] You tested both: `/` (fast) and `/slow` (slow).
- [ ] The filter is still signed up with `@Component`, and no controller calls it.

## Help Pages

- [HTTP overview (MDN)](https://developer.mozilla.org/en-US/docs/Web/HTTP): how requests and responses work.
- [Filters (Spring MVC)](https://docs.spring.io/spring-framework/reference/web/webmvc/filters.html): the official page about filters.
- [Servlets, Filters, and Listeners (Spring Boot)](https://docs.spring.io/spring-boot/reference/web/servlet.html): how a filter bean gets signed up.
- [OncePerRequestFilter (Javadoc)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/filter/OncePerRequestFilter.html): the method you must write.
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/): a first guide to run beside this page.

Using Spring Boot 4? Its web starter is called `spring-boot-starter-webmvc`. The filter code on this page stays the same.

---

[← Back to start](../README.md) · [Cheat sheet](../cheat-sheet.md) · [Next: 02 Controllers & Routes →](02-controllers-and-routes.md)
