<!-- docs/request-lifecycle.md -->
# Request lifecycle: GET /about

Before any request: `BillableApplication.main()` calls `SpringApplication.run()`. Spring scans
`ee.ta25.billable`, creates a bean for `HomeController`, registers its `@GetMapping` methods and starts
embedded Tomcat on port 8080. This happens once.

1. **Tomcat** receives `GET /about` on port 8080.
2. **Servlet filters** run (for example `CharacterEncodingFilter`), then the request reaches
   **`DispatcherServlet`**, Spring MVC's front controller.
3. **`RequestMappingHandlerMapping`** finds the handler: `HomeController#about()`.
4. **`HomeController.about()`** (`src/main/java/ee/ta25/billable/common/HomeController.java`) returns
   the view name `"about"`.
5. **`ThymeleafViewResolver`** resolves `"about"` to `src/main/resources/templates/about.html`.
6. **Thymeleaf** renders `about.html`; `th:replace` on `<html>` wraps its `<title>` and `<main>` in the
   `layout` fragment from `templates/layout.html` (head, nav, footer).
7. The HTML is written to the response with status 200 and goes back through the filters and Tomcat
   to the browser.

Evidence (DEBUG log):

    o.s.web.servlet.DispatcherServlet        : GET "/about", parameters={}
    s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to ee.ta25.billable.common.HomeController#about()
    o.s.web.servlet.DispatcherServlet        : Completed 200 OK

MVC in this request: `HomeController` is the controller and `about.html` is the view. There is no
model data because the page only shows fixed text.