# 05 · Validation & Errors

After this page you can reject bad input and send clear error messages.

## In one minute

- Put rules on the data (`@NotBlank`), then add `@Valid`. Bad data gets a 400.
- Throw your own exception. Catch it once in a `@RestControllerAdvice`.
- Always test three visits: a good one, a bad one, and a missing one.

## The big idea

Think of a bouncer at a party door. The bouncer checks every ticket. A good ticket goes in. A bad ticket gets a polite message: "Your ticket has no name on it."

In Spring, **validation** is the ticket check. An **exception handler** is the polite message. Without them, a bad visitor gets a scary 500 error.

## Set up

Add the **Validation** dependency in your `pom.xml` (the artifact is `spring-boot-starter-validation`). You can also tick "Validation" in start.spring.io.

## The pieces

| Piece | In kid words |
| --- | --- |
| `@NotBlank` | "Text must have real letters in it." |
| `@NotNull` | "This must be there." |
| `@Size(min = 1, max = 200)` | "Text length must be in this range." |
| `@Min(1)` and `@Max(100)` | "A number must be in this range." |
| `@Email` | "This must look like an email." |
| `@Valid` | "Check the tickets on this object now." |
| `@RestControllerAdvice` | "A helper class that catches errors from all controllers." |
| `@ExceptionHandler` | "This method handles this kind of error." |
| `ProblemDetail` | A standard shape for an error answer. |

## Build it

**Step 1.** Open the NewNote record from lesson 02 and put rules on it.

```java
public record NewNote(
        @NotBlank String title,
        @Size(max = 200) String text) {}
```

**Step 2.** In the HelloController from lesson 02, turn the check on with `@Valid`.

```java
@PostMapping
public ResponseEntity<NewNote> create(@Valid @RequestBody NewNote note) {
    return ResponseEntity.status(HttpStatus.CREATED).body(note);
}
```

Send a note with an empty title. Spring answers **400 Bad Request** by itself.

**Step 3.** Make your own error. Create a class `NotFoundException`.

```java
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
```

**Step 4.** Catch it in one place.

```java
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail notFound(NotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }
}
```

Now any controller or service can write `throw new NotFoundException("No note with id 7")` and the visitor gets a clean 404.

## Solve a task

The task says "reject invalid input" or "return 404 when missing". Do this:

1. Write the rules in plain words. "Title cannot be empty." "Text is at most 200 letters."
2. Turn each rule into a sticker on the record.
3. Add `@Valid` before `@RequestBody`.
4. For "not found", throw your own exception. Handle it once, in the advice class.
5. Test three visits: a good one, a bad one, a missing one. Look at the status codes.

## Stuck? Look here

- **Bad input is accepted.** You forgot `@Valid`, or the validation dependency is missing.
- **My handler never runs.** The class is outside the main package, or the exception type does not match.
- **I get 500 instead of 404.** The exception escaped. Check that the handler's exception class is the same one you throw.
- **The error message is a wall of text.** Read the first line that says `Field error` or `Caused by`.

## Practice Task

Add a `POST /api/people` endpoint. The body is `{"name": "Mia", "age": 9}`. Use a record `NewPerson(String name, int age)`.

- [ ] `name` must not be blank, and must be 2 to 20 letters long (`@NotBlank`, `@Size`).
- [ ] `age` must be 0 to 120 (`@Min`, `@Max`).
- [ ] A good body returns 201.
- [ ] A blank name returns 400. An age of 200 returns 400.
- [ ] `GET /api/people/{id}` always throws `NotFoundException` for now (there is no database yet), and the visitor sees a 404 with a message.

## Help pages

- [Bean Validation in Spring](https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html)
- [@ExceptionHandler methods](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-exceptionhandler.html)
- [Validating Form Input (guide)](https://spring.io/guides/gs/validating-form-input)
- [Hibernate Validator docs](https://hibernate.org/validator/documentation/): the list of every validation sticker.

---

[← Previous: 04](04-settings-and-profiles.md) · [Back to start](../README.md) · [Cheat sheet](../cheat-sheet.md) · [Next: 06 Data & Databases →](06-data-and-databases.md)
