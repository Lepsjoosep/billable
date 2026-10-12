# 10 · Testing

After this page you can write small robots that check your code, and run them before you hand in a task.

## In one minute

- A test is a robot that asks your code a question and checks the answer.
- Use a plain test for a service, `@WebMvcTest` for a controller, and `@DataJpaTest` for a repository.
- Write one test for each tick box on your checklist.

## The big idea

Think of a practice quiz before the real exam. You ask your own code a question and you write down the answer you expect. A **test** is a robot that asks the question for you, every time, in one second. If the answer is wrong, the robot turns red.

A good trick: write **one test for each tick box** on your task checklist. When every test is green, every box is ticked.

## Set up

Start.spring.io adds `spring-boot-starter-test` for you. It brings JUnit 5, Mockito and MockMvc. In Boot 4, each main starter also has its own test starter. If a test annotation will not import, check that you have the matching one, for example `spring-boot-starter-webmvc-test`. Tests live in `src/test/java`. Run them with `./mvnw test`, or press the green arrow in your IDE.

## The pieces

| Piece | In kid words |
| --- | --- |
| `@Test` | "This method is a test." |
| `assertEquals(expected, actual)` | "These two must be the same." |
| `@Mock` and `when(...).thenReturn(...)` | "A pretend helper that gives the answer I choose." |
| `@SpringBootTest` | "Start the whole app." Slow, so use it rarely. |
| `@WebMvcTest` | "Start only the web layer." Fast. |
| `MockMvc` | "A pretend browser inside the test." |
| `@MockitoBean` | "Put a pretend bean into Spring for this test." |
| `@DataJpaTest` | "Start only the database layer, with a fresh H2." |

## Build it

**Step 1.** A plain test. No Spring at all, so it is very fast.

```java
class GreetingServiceTest {

    @Test
    void greetsByName() {
        var service = new GreetingService();
        assertEquals("Hi, Mia", service.greet("Mia"));
    }
}
```

**Step 2.** A web test. Spring starts only the controller you name. The service is a pretend one.

```java
@WebMvcTest(GreetingController.class)
class GreetingControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    GreetingService service;

    @Test
    void returnsTheGreeting() throws Exception {
        when(service.greet("Mia")).thenReturn("Hi, Mia");

        mvc.perform(get("/greet/Mia"))
           .andExpect(status().isOk())
           .andExpect(content().string("Hi, Mia"));
    }
}
```

**Step 3.** A database test.

```java
@DataJpaTest
class NoteRepositoryTest {

    @Autowired
    NoteRepository repo;

    @Test
    void findsByTitle() {
        repo.save(new Note("Cat facts", "Cats purr"));
        assertEquals(1, repo.findByTitleContainingIgnoreCase("cat").size());
    }
}
```

Let the IDE add the imports with `Alt+Enter`. The static ones (`get`, `status`, `content`, `when`) come from `MockMvcRequestBuilders`, `MockMvcResultMatchers` and `Mockito`.

**Which import do I pick?** It depends on your Boot version.

| Annotation | Boot 3.x package | Boot 4.x package |
| --- | --- | --- |
| `@WebMvcTest` | `org.springframework.boot.test.autoconfigure.web.servlet` | `org.springframework.boot.webmvc.test.autoconfigure` |
| `@DataJpaTest` | `org.springframework.boot.test.autoconfigure.orm.jpa` | `org.springframework.boot.data.jpa.test.autoconfigure` |

`@MockitoBean` needs Boot 3.4 or newer. Older Boot uses `@MockBean`.

## Solve a task

The task has a checklist. Do this:

1. Write each tick box as a sentence that starts with "when I ask ..., I get ...".
2. Turn each sentence into a test method with a name that says it.
3. Use the **Given, When, Then** steps inside the test. Given: set things up. When: do the thing. Then: check the result.
4. Run the tests. They are all red at first. That is fine.
5. Write the code until they turn green.
6. Run all tests one last time before you commit.

To test that a filter wrote a log line, look up `OutputCaptureExtension` in the Spring Boot testing docs.

## Stuck? Look here

- **No qualifying bean in a `@WebMvcTest`.** The controller needs a service that the slice did not load. Add `@MockitoBean` for it.
- **MockMvc gives 404.** Check the URL. Check that you named the right controller.
- **MockMvc gives 401 or 403.** Spring Security is on. Look up `spring-security-test` and `@WithMockUser`.
- **Tests are slow.** You used `@SpringBootTest` everywhere. Use a slice like `@WebMvcTest` when you can.
- **A test passes alone but fails with the others.** Tests share data. Make each test set up its own data.

## Practice Task

Write three tests for the book shelf:

- [ ] A plain test for a small method, for example the shout service.
- [ ] A `@WebMvcTest` that checks a good POST gives 201 and a bad POST gives 400.
- [ ] A `@DataJpaTest` for `findByAuthorIgnoreCase`.
- [ ] `./mvnw test` is green.
- [ ] Each test is named after a tick box from your task checklist.

## Help pages

- [Spring Boot: Testing](https://docs.spring.io/spring-boot/reference/testing/index.html)
- [Testing the Web Layer (guide)](https://spring.io/guides/gs/testing-web)
- [Spring MVC Test (MockMvc)](https://docs.spring.io/spring-framework/reference/testing/mockmvc.html)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito](https://site.mockito.org/)

---

[← Previous: 09](09-security.md) · [Back to start](../README.md) · [Cheat sheet](../cheat-sheet.md) · [Next: 11 Build, Run & Ship →](11-build-run-and-ship.md)
