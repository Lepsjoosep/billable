# Spring Boot, the Easy Way

This course has 11 short lessons that teach you Spring Boot, so you can solve the task at the end of each lesson. Every page uses small steps and short words. Every lesson ends with a practice task and a list of help pages.

The lesson numbers follow a common Spring Boot order. If your course uses another order, rename the files to match.

## Lesson map

1. [01 · Filters: Measure Every Request (Task 4)](01-filters-measure-every-request.md): how a request travels, and your first filter.
2. [02 · Controllers & Routes](tutorial/02-controllers-and-routes.md): URLs that answer with text or JSON.
3. [03 · Beans & Dependency Injection](tutorial/03-beans-and-dependency-injection.md): controller, service and repository, connected by Spring.
4. [04 · Settings & Profiles](tutorial/04-settings-and-profiles.md): change the app with dials, not code.
5. [05 · Validation & Errors](tutorial/05-validation-and-errors.md): reject bad input and send clear errors.
6. [06 · Data & Databases](tutorial/06-data-and-databases.md): save and find data with JPA.
7. [07 · Web Pages with Thymeleaf](tutorial/07-web-pages-with-thymeleaf.md): web pages and forms.
8. [08 · Filters, Interceptors & Aspects](tutorial/08-filters-interceptors-and-aspects.md): pick the right door for code that runs on many requests.
9. [09 · Security](tutorial/09-security.md): logins, roles and protected URLs.
10. [10 · Testing](tutorial/10-testing.md): tests that turn your checklist green.
11. [11 · Build, Run & Ship](tutorial/11-build-run-and-ship.md): build the JAR, check health, hand in cleanly.

Quick lookup: [Cheat Sheet](cheat-sheet.md). Help links: [Sources & Help Pages](sources.md).

## How to use each lesson page

1. Read **In one minute**. It is the short version.
2. Read **The big idea**. It is a short story.
3. Look at **The pieces**. These are the words and stickers you will use.
4. Do **Build it**. Type the code yourself. Do not paste it. Your fingers learn too.
5. Open **Solve a task**. It says what to do when a task looks like yours. Then do the Practice Task near the bottom.
6. Stuck? Open **Stuck? Look here**, then the **Help pages** at the bottom.

## The 5 magic steps

Use them for every task. Lesson 01 shows all five on a real task.

1. Read the task. Say it in your own words.
2. Make a checklist from the "Acceptance criteria".
3. Find the spot where your code goes.
4. Build one tiny piece at a time.
5. Test it. Tick the boxes.

## Set up once

- [ ] Install JDK 17 or newer (21 is a good pick). Check it with `java -version`.
- [ ] Install IntelliJ IDEA (the free Community edition is fine) or VS Code with the Java extension pack.
- [ ] Install Git. Check it with `git --version`.
- [ ] Open [start.spring.io](https://start.spring.io). Pick **Maven**, **Java**, the newest stable Spring Boot (not SNAPSHOT, not M1), and add the **Spring Web** dependency.
- [ ] Click Generate, unzip the file, and open the folder in your IDE.
- [ ] Run the class whose name ends in `Application`.
- [ ] Open http://localhost:8080. A "Whitelabel Error Page" means your app is alive.

## What is inside a project

```
pom.xml                               the shopping list of libraries
src/main/java/com/example/demo/
    DemoApplication.java              the start button
src/main/resources/
    application.properties            the settings dials
    templates/ and static/            web pages (later lessons)
src/test/java/com/example/demo/       the tests
```

Put every class in the same package as `DemoApplication`, or in a package below it. Spring only looks there.

## Which Spring Boot version do I have?

Open `pom.xml` and find the `<parent>` block. The version inside it is your Boot version. Follow what your course uses. Boot 4 renamed some things:

| Thing | Boot 3.x | Boot 4.x |
| --- | --- | --- |
| Web starter | spring-boot-starter-web | spring-boot-starter-webmvc |
| Test starters | spring-boot-starter-test | spring-boot-starter-test, plus one test starter per feature, such as spring-boot-starter-webmvc-test |
| Package of the web test annotation | org.springframework.boot.test.autoconfigure.web.servlet | org.springframework.boot.webmvc.test.autoconfigure |

The `jakarta.servlet` imports are the same in both. If your IDE offers two imports for one name, pick the one that matches your Boot version.

## Read errors without fear

1. A red squiggle in the editor is a typing or import problem. Press `Alt+Enter`.
2. A wall of red text in the console? Scroll to the last line that starts with `Caused by:`. That is the real reason.
3. Copy that one line into a search engine. Add the words "Spring Boot".

## Help pages

- [Spring Initializr](https://start.spring.io): makes a new project for you.
- [Spring Boot reference](https://docs.spring.io/spring-boot/reference/index.html): the official manual.
- [Spring guides](https://spring.io/guides): short hands-on tutorials, 15 to 30 minutes each.
- [Spring Academy](https://spring.academy/): free video courses from the Spring team.
- [Spring Boot 4.0 Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide): every renamed starter in one list.
- [Modularizing Spring Boot](https://spring.io/blog/2025/10/28/modularizing-spring-boot): why Boot 4 has more starters.
