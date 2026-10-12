# 02 · Controllers & Routes

After this page you can make a URL that answers with text or JSON.

## In one minute

- A controller is a waiter. A URL and a kind of visit go in. An answer comes out.
- Put `@RestController` on the class and `@GetMapping` or `@PostMapping` on each method.
- Inputs come from the URL (`@PathVariable`), after the `?` (`@RequestParam`), or from the body (`@RequestBody`).

## The big idea

Think of a restaurant. You sit down and order. That is the **request**. The waiter takes your order to the right kitchen window. The waiter is the **controller**, and each window is a **method**. The waiter brings food back. That is the **response**.

Every window has a sign. The sign is a URL plus a kind of visit, like GET or POST.

## The pieces

| Sticker | In kid words |
| --- | --- |
| `@RestController` | "This class is a waiter. Answers go straight back as text or JSON." |
| `@RequestMapping("/api/notes")` | "All my windows start with this address." |
| `@GetMapping` | "This method answers GET visits (reading)." |
| `@PostMapping` | "This method answers POST visits (making something new)." |
| `@PutMapping` | "This method answers PUT visits (replacing something)." |
| `@DeleteMapping` | "This method answers DELETE visits (removing something)." |
| `@PathVariable` | "Take a piece out of the URL. /notes/7 gives 7." |
| `@RequestParam` | "Take a piece after the question mark. /search?q=cat gives cat." |
| `@RequestBody` | "Read the JSON the visitor sent." |
| `ResponseEntity` | "Let me pick the status code myself." |

## Build it

**Step 1.** Make a class `HelloController` in your main package.

```java
@RestController
@RequestMapping("/api/hello")
public class HelloController {

    @GetMapping
    public String hello() {
        return "Hello!";
    }

    @GetMapping("/{name}")
    public String greet(@PathVariable String name) {
        return "Hi, " + name;
    }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "all") String q) {
        return "You looked for " + q;
    }
}
```

**Step 2.** Accept JSON. A `record` is a tiny class that only holds data. Put it in its own file `NewNote.java`.

```java
public record NewNote(String title, String text) {}
```

Then add this method to the controller:

```java
@PostMapping
public ResponseEntity<NewNote> create(@RequestBody NewNote note) {
    return ResponseEntity.status(HttpStatus.CREATED).body(note);
}
```

**Step 3.** Test it. In IntelliJ, make a file `requests.http` (VS Code: the REST Client extension reads the same format).

```
GET http://localhost:8080/api/hello/Mia

###
POST http://localhost:8080/api/hello
Content-Type: application/json

{"title": "A", "text": "B"}
```

You can also use `curl` in a terminal. On Windows, the `.http` file is easier because quotes are tricky there.

## Status codes you will use

| Code | Name | When |
| --- | --- | --- |
| 200 | OK | A GET worked |
| 201 | Created | A POST made something new |
| 204 | No Content | A DELETE worked, nothing to say |
| 400 | Bad Request | The visitor sent bad data |
| 404 | Not Found | No such thing |
| 500 | Server Error | My code broke |

## Solve a task

The task says "make an endpoint that...". Do this:

1. Write one line: METHOD plus URL. Example: `GET /api/notes/{id}`.
2. Pick the sticker for that method.
3. List the inputs. In the URL? Use `@PathVariable`. After the `?`? Use `@RequestParam`. In the body? Use `@RequestBody`.
4. Say what comes back, and with which status code.
5. Write the method. Run the app. Send a request.
6. Tick the box.

## Stuck? Look here

- **404.** A typo in the URL, or your class is outside the main package.
- **405 Method Not Allowed.** Right URL, wrong kind of visit. GET versus POST.
- **415 Unsupported Media Type.** Add the header `Content-Type: application/json`.
- **400 on a POST.** The JSON is broken, or the field names do not match the record.
- **You see plain text where you wanted a web page.** You used `@RestController` where you needed `@Controller`. See lesson 07.

## Practice Task

Add two endpoints to `HelloController`:

- [ ] `GET /api/hello/{name}/shout` returns the name in capital letters. `/api/hello/mia/shout` gives `MIA`.
- [ ] `GET /api/hello/add?a=2&b=3` returns `5`. Use `int` parameters.
- [ ] If `b` is missing, the visitor gets a 400.
- [ ] You tested all three with an `.http` file or `curl`.

Hint: `name.toUpperCase()` makes capital letters.

## Help pages

- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/): the official first guide.
- [Annotated controllers (Spring MVC)](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller.html): every sticker explained.
- [HTTP request methods (MDN)](https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods)
- [HTTP status codes (MDN)](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
- [Record classes (Oracle)](https://docs.oracle.com/en/java/javase/21/language/records.html)

---

[← Previous: 01](01-filters-measure-every-request.md) · [Back to start](../README.md) · [Cheat sheet](../cheat-sheet.md) · [Next: 03 Beans & Injection →](03-beans-and-dependency-injection.md)
