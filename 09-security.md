# 09 · Security

After this page you can add a login, protect some URLs, and give different people different powers.

## In one minute

- Authentication means "who are you?". Authorization means "what may you do?".
- Make one `SecurityFilterChain` bean. Put narrow rules first and `anyRequest()` last.
- Encode passwords with BCrypt. A 401 means "who are you?". A 403 means "I know you, but no".

## The big idea

Think of a school building. The front desk checks who you are (**authentication**: "are you Mia?"). Then each door has a rule about who may enter (**authorization**: "only teachers may enter the staff room").

Here is a nice surprise. Spring Security is a **chain of filters**. It is the same idea as the timing filter you built in lesson 01. Every request walks through the security guards before it reaches your controller.

## Set up

Add the **Spring Security** dependency (`spring-boot-starter-security`). Start the app. Now every URL asks for a login. The user name is `user`, and the password is printed in the console, on a line that says `Using generated security password`.

## The pieces

| Piece | In kid words |
| --- | --- |
| `SecurityFilterChain` | "My list of rules." You make it as a bean. |
| `authorizeHttpRequests` | "Who may open which URL." |
| `permitAll()` | "Everybody, even without a login." |
| `authenticated()` | "Anyone who is logged in." |
| `hasRole("ADMIN")` | "Only people with the ADMIN badge." |
| `formLogin` | "Show a login page." |
| `httpBasic` | "Ask for a user name and password in every request." Good for APIs. |
| `UserDetailsService` | "Where the users are kept." |
| `PasswordEncoder` | "Scramble passwords so nobody can read them." Use BCrypt. |
| `@PreAuthorize` | "Check the rule on this one method." |
| CSRF | A trick-protection for forms. Keep it on for web pages. |

## Build it

**Step 1.** One config class with the rules and two users kept in memory.

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain chain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/public/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated())
            .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService users(PasswordEncoder encoder) {
        UserDetails mia = User.withUsername("mia")
                .password(encoder.encode("secret1"))
                .roles("USER")
                .build();
        UserDetails max = User.withUsername("max")
                .password(encoder.encode("secret2"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(mia, max);
    }
}
```

The order of the rules matters. The first rule that matches wins. Put the narrow rules first and `anyRequest()` last.

**Step 2.** Try it.

1. Open `/public/hello`. No login needed.
2. Open `/api/notes`. You are sent to the login page. Log in as mia.
3. Open `/admin/anything` as mia. You get **403 Forbidden**. Log in as max. Now security lets you in. You may see a 404 if you have no admin page yet. That is fine.

**Step 3 (for an API).** Replace `formLogin` with Basic login. A tool like curl sends the user name and password each time.

```java
.httpBasic(Customizer.withDefaults())
```

For a learning API that has no web forms, you may turn CSRF off with `.csrf(csrf -> csrf.disable())`. Do this only because your teacher's task says so, and never for pages with forms.

## Solve a task

The task says "protect these URLs" or "only admins can...". Do this:

1. Make a table on paper: URL on the left, who may enter on the right.
2. Turn each row into a `requestMatchers(...)` line. Narrow rules first. `anyRequest()` last.
3. Make the users. Give each one a role. Encode every password.
4. Test each row of your table with a real person, a wrong person, and nobody.
5. Check the codes: **401** means "who are you?", **403** means "I know you, but no".

## Stuck? Look here

- **Everything redirects to /login.** That is the default. Add `permitAll()` for the URLs that should be open.
- **POST gives 403.** CSRF is on. For a form, use `th:action`. For a learning API, see step 3.
- **There is no PasswordEncoder mapped for the id null.** You stored a plain password. Encode it, or use a `PasswordEncoder` bean.
- **hasRole("ADMIN") does not work.** Give the user `.roles("ADMIN")`. Spring adds the `ROLE_` prefix for you.
- **Wrong rule wins.** Check the order of your `requestMatchers` lines.

## Practice Task

Protect the book shelf API:

- [ ] Anyone may `GET /api/books` with no login.
- [ ] Only the `ADMIN` role may `POST` and `DELETE` under `/api/books`.
- [ ] A `USER` who tries to POST gets 403.
- [ ] Passwords are stored with BCrypt.
- [ ] You tested four visits: nobody, wrong password, USER, ADMIN.

Hint: use `httpBasic` and turn CSRF off for this API (step 3). A rule for open reading looks like `requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()`.

## Help pages

- [Spring Security reference](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Security architecture](https://docs.spring.io/spring-security/reference/servlet/architecture.html): the filter chain, explained.
- [Securing a Web Application (guide)](https://spring.io/guides/gs/securing-web)
- [OWASP Password Storage Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html): why we scramble passwords.

---

[← Previous: 08](08-filters-interceptors-and-aspects.md) · [Back to start](../README.md) · [Cheat sheet](../cheat-sheet.md) · [Next: 10 Testing →](10-testing.md)
