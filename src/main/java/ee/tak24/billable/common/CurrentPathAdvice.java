// src/main/java/ee/ta25/billable/common/CurrentPathAdvice.java
package ee.tak24.billable.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/** Adds the current request path to every model, so the layout can highlight the active link. */
@ControllerAdvice
public class CurrentPathAdvice {

  @ModelAttribute("currentPath")
  public String currentPath(HttpServletRequest request) {
    return request.getRequestURI();
  }
}
