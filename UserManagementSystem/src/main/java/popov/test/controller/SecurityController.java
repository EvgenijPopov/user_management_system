package popov.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
// Security controller that redirect on login page or access denied page
    @GetMapping("/login")
    public String login() {
        return "login-page";
    }

    @GetMapping("/accessRestrict")
    public String restricted() {
        return "access-restrict-page";
    }
}
