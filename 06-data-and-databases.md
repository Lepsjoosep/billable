# 06 · Data & Databases

After this page you can save things in a database and read them back with Spring Data JPA.

## In one minute

- An `@Entity` is a table. A `JpaRepository` is the clerk who saves and finds.
- You write only an interface. Spring writes the code behind it.
- H2 in memory forgets everything when the app stops.

## The big idea

Think of a filing cabinet. An **entity** is the shape of one folder: it has a title and a text. A **repository** is the clerk. You tell the clerk "file this" or "find all folders with cat in the title", and the clerk does it. You never open the drawers yourself.

The cabinet is a **database**. For learning, we use **H2**. It lives in memory and disappears when the app stops, so there is nothing to install.

## Set up

In start.spring.io add **Spring Data JPA** and **H2 Database**. Put these dials in `application.properties`:

```
spring.datasource.url=jdbc:h2:mem:notesdb
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

`ddl-auto=update` makes the tables for you. `show-sql=true` prints each SQL line, so you can watch the clerk work.

In Boot 4 the H2 web console needs an extra module. You can skip it. Look at your data through your own GET endpoints instead.

## The pieces

| Piece | In kid words |
| --- | --- |
| `@Entity` | "This class is a folder shape. Make a table for it." |
| `@Id` | "This field is the folder's number." |
| `@GeneratedValue` | "Make the number for me." |
| `JpaRepository<Note, Long>` | "A ready-made clerk for Note, whose number is a Long." |
| `findByTitleContainingIgnoreCase` | A method name that Spring turns into a search. |
| `@Transactional` | "Do all of this, or none of it." |
| `@OneToMany` and `@ManyToOne` | "One author has many notes." |

## Build it

**Step 1.** The folder shape.

```java
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String text;

    protected Note() {}   // JPA needs this empty one

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getText() { return text; }
}
```

**Step 2.** The clerk. It is only an interface. Spring writes the rest.

```java
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByTitleContainingIgnoreCase(String part);
}
```

**Step 3.** Use the clerk from a controller (or better, from a service, as in lesson 03).

```java
@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteRepository repo;

    public NoteController(NoteRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Note> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Note one(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("No note " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note create(@Valid @RequestBody NewNote body) {
        return repo.save(new Note(body.title(), body.text()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
```

This uses `NewNote` from lesson 05 and `NotFoundException` too. That is how the lessons stack up.

## Solve a task

The task says "store X in a database". Do this:

1. Write the fields of X on paper, with their types.
2. Make the `@Entity` class. Add `@Id`.
3. Make the repository interface.
4. Write the endpoints: list, get one, create, delete.
5. Run. Create two items. List them. Get one. Delete one. List again.
6. Need a search? Write a method name like `findBy...` and let Spring do the work.

## Stuck? Look here

- **No identifier specified for entity.** The `@Id` field is missing.
- **Table not found.** Check `ddl-auto=update`, and check that your entity is in the main package.
- **Data disappears on restart.** That is H2 in memory. It is normal.
- **LazyInitializationException.** You touched a lazy relation outside a transaction. Fetch what you need inside a `@Transactional` service method.
- **Infinite JSON.** Two entities point at each other. Return small records instead of entities.

## Practice Task

Build a small book shelf:

- [ ] An `@Entity` named `Book` with `title`, `author` and `year`.
- [ ] A `BookRepository` with the method `findByAuthorIgnoreCase(String author)`.
- [ ] `POST /api/books` saves a book and returns 201.
- [ ] `GET /api/books` lists all books. `GET /api/books?author=Tove` lists only that author.
- [ ] `GET /api/books/99` returns 404.

Hint: `@RequestParam(required = false)` lets one method handle both URLs.

## Help pages

- [Accessing Data with JPA (guide)](https://spring.io/guides/gs/accessing-data-jpa)
- [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/reference/): query methods are explained here.
- [Boot: SQL databases](https://docs.spring.io/spring-boot/reference/data/sql.html)
- [Hibernate ORM documentation](https://hibernate.org/orm/documentation/)
- [H2 Database](https://www.h2database.com/)

---

[← Previous: 05](05-validation-and-errors.md) · [Back to start](../README.md) · [Cheat sheet](../cheat-sheet.md) · [Next: 07 Web Pages →](07-web-pages-with-thymeleaf.md)
