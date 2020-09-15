package popov.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {
// default controller for redirection all request from root to users URL
    @GetMapping("/")
    public String redirect() {
        return "redirect:/ums/users/1";
    }
}
