# Cheat Sheet

Every sticker, command and common error from lessons 01 to 11, on one page. [Back to start](README.md).

## Stickers (annotations)

| Sticker | What it means | Lesson |
| --- | --- | --- |
| `@SpringBootApplication` | The start button of the app | Start |
| `@Component` | Spring makes one of this class | 03 |
| `@Order` | Position in a chain (first, second...) | 01 |
| `@RestController` | Methods return text or JSON | 02 |
| `@Controller` | Methods return page names | 07 |
| `@RequestMapping` | Shared address start for a class | 02 |
| `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` | This method answers that kind of visit | 02 |
| `@PathVariable` | A piece of the URL | 02 |
| `@RequestParam` | A piece after the `?` | 02 |
| `@RequestBody` | The JSON the visitor sent | 02 |
| `@ResponseStatus` | A fixed status code for this method | 05, 06 |
| `@Service` | A component that holds the rules | 03 |
| `@Repository` | A component that talks to the database | 03 |
| `@Configuration` and `@Bean` | Make beans on purpose | 03 |
| `@Primary` and `@Qualifier` | Pick one of several beans | 03 |
| `@Value` | Read one setting | 04 |
| `@ConfigurationProperties` | Read a group of settings | 04 |
| `@Profile` | Only when this profile is on | 04 |
| `@Valid` | Check the rules on this object now | 05 |
| `@NotBlank`, `@NotNull`, `@Size`, `@Min`, `@Max`, `@Email` | Validation rules | 05 |
| `@RestControllerAdvice` and `@ExceptionHandler` | Catch errors in one place | 05 |
| `@Entity`, `@Id`, `@GeneratedValue` | A database folder shape | 06 |
| `@Transactional` | Do all of it, or none of it | 06 |
| `@ModelAttribute` | Tie a form to an object | 07 |
| `@Aspect` and `@Around` | Wrap method calls | 08 |
| `@EnableWebSecurity` | Turn on the security rules | 09 |
| `@PreAuthorize` | A rule on one method | 09 |
| `@Test` | This method is a test | 10 |
| `@WebMvcTest`, `@DataJpaTest`, `@SpringBootTest` | Small or big test setups | 10 |
| `@MockitoBean` | A pretend bean for a test | 10 |

## Commands

| Command | What it does |
| --- | --- |
| `java -version` | Show your Java version |
| `./mvnw spring-boot:run` | Start the app |
| `./mvnw test` | Run all tests |
| `./mvnw clean package` | Build the JAR in `target` |
| `java -jar target/your-app.jar` | Run the JAR |
| `curl -i http://localhost:8080/` | Visit a URL and show the headers (Windows PowerShell: `curl.exe`) |
| `curl -X POST -H "Content-Type: application/json" -d '{"title":"A"}' http://localhost:8080/api/notes` | Send JSON (Mac and Linux) |
| `git add .` then `git commit -m "message"` | Save your work in Git |

## Error text: what to check first

| You see | Check this first | Lesson |
| --- | --- | --- |
| 404 | The URL, and whether the class is inside the main package | 02 |
| 405 | Wrong kind of visit (GET or POST) | 02 |
| 415 | The `Content-Type: application/json` header | 02 |
| No qualifying bean of type | Missing `@Service` or `@Component`, or wrong package | 03 |
| Could not resolve placeholder | A setting is missing or misspelled | 04 |
| Bad input is accepted | Missing `@Valid` or the validation dependency | 05 |
| No identifier specified for entity | Missing `@Id` | 06 |
| Error resolving template | The file name or folder under `templates` | 07 |
| Interceptor or aspect never runs | Not signed up, or starter missing | 08 |
| 401 or 403 | Login and role rules, and CSRF | 09 |
| No qualifying bean in a web test | Add `@MockitoBean` for the missing helper | 10 |
| Port 8080 was already in use | Stop the old run, or set `server.port` | 04, 11 |
