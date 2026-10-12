# 07 · Web Pages with Thymeleaf

After this page you can show data on a real web page and take input from a form.

## In one minute

- `@Controller` returns a page name. A Thymeleaf file in `templates/` fills the blanks.
- Put facts on the `Model`. Read them with `th:text` and `th:each`.
- After a form POST, `redirect:` back to a GET.

## The big idea

Think of a mail-merge letter. The letter says "Dear ____". A helper fills in the blank for each person.

In Spring MVC, the letter is a **template**: an HTML file with blanks. The helper is a **controller**. The box that carries the facts from the controller to the template is the **Model**. The tool that fills the blanks is **Thymeleaf**.

## Set up

Add the **Thymeleaf** dependency (`spring-boot-starter-thymeleaf`). HTML files go in `src/main/resources/templates`.

## The pieces

| Piece | In kid words |
| --- | --- |
| `@Controller` | A waiter that returns the **name of a page**, not raw text. |
| `Model` | The tray. Put facts on it with `addAttribute`. |
| Return `"notes"` | "Show `templates/notes.html`." |
| `th:text="${n.title}"` | "Put this value in the blank." |
| `th:each="n : ${notes}"` | "Repeat this tag for every note." |
| `th:if="..."` | "Show this only if it is true." |
| `th:action="@{/notes}"` | "Send the form to this address." |
| `th:object` and `th:field` | "Tie the form to a Java object." |
| `BindingResult` | "The list of problems with the form." |
| `redirect:/notes` | "Now go to this other page." |

## Build it

**Step 1.** A plain class for the form. Use getters and setters here, because Thymeleaf reads them.

```java
public class NoteForm {

    @NotBlank
    private String title;
    private String text;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}
```

**Step 2.** The page controller.

```java
@Controller
@RequestMapping("/notes")
public class NotePageController {

    private final NoteRepository repo;

    public NotePageController(NoteRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("notes", repo.findAll());
        model.addAttribute("noteForm", new NoteForm());
        return "notes";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("noteForm") NoteForm form,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("notes", repo.findAll());
            return "notes";
        }
        repo.save(new Note(form.getTitle(), form.getText()));
        return "redirect:/notes";
    }
}
```

**Step 3.** The template, saved as `src/main/resources/templates/notes.html`.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<body>
  <h1>My notes</h1>

  <ul>
    <li th:each="n : ${notes}" th:text="${n.title}">Title here</li>
  </ul>

  <form th:action="@{/notes}" th:object="${noteForm}" method="post">
    <input th:field="*{title}" placeholder="Title">
    <div th:if="${#fields.hasErrors('title')}" th:errors="*{title}">Title error</div>
    <input th:field="*{text}" placeholder="Text">
    <button type="submit">Save</button>
  </form>
</body>
</html>
```

Open http://localhost:8080/notes. Save a note. The page reloads with your note in the list.

Why `redirect:`? After a POST, the redirect sends a fresh GET. If you refresh, the browser will not send the form twice.

## Solve a task

The task says "make a page that shows X" or "add a form to create X". Do this:

1. Draw the page on paper. Which blanks does it have?
2. For each blank, decide the name you will put on the Model.
3. Write the `@GetMapping` that fills the Model and returns the page name.
4. Write the HTML file with the same page name. Use `th:text` and `th:each` for the blanks.
5. For a form: make a form class, a `@PostMapping`, and a `redirect:` at the end.
6. Run it. Click it like a user would.

## Stuck? Look here

- **The browser shows the word "notes" as plain text.** You used `@RestController`. Use `@Controller`.
- **Error resolving template.** The file name or folder is wrong. It must be `templates/notes.html`.
- **A blank stays empty.** The Model name and the name in `${...}` must match exactly.
- **Neither BindingResult nor plain target object.** On the GET, you forgot to put `noteForm` on the Model.
- **403 on submit after adding Spring Security.** Use `th:action` on the form. Thymeleaf then adds the CSRF token for you.

## Practice Task

Make a page at `/books`:

- [ ] It shows every book in a list (`th:each`).
- [ ] It has a form with title, author and year.
- [ ] An empty title shows an error message under the field.
- [ ] After a good save, the page reloads with the new book on it (`redirect:`).
- [ ] The controller is `@Controller`, not `@RestController`.

## Help pages

- [Serving Web Content with Spring MVC (guide)](https://spring.io/guides/gs/serving-web-content)
- [Handling Form Submission (guide)](https://spring.io/guides/gs/handling-form-submission)
- [Thymeleaf + Spring tutorial](https://www.thymeleaf.org/doc/tutorials/3.1/thymeleafspring.html)
- [Thymeleaf documentation](https://www.thymeleaf.org/documentation.html)

---

[← Previous: 06](06-data-and-databases.md) · [Back to start](../README.md) · [Cheat sheet](../cheat-sheet.md) · [Next: 08 Filters, Interceptors & Aspects →](08-filters-interceptors-and-aspects.md)
