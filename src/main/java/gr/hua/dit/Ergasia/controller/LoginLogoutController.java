package gr.hua.dit.Ergasia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginLogoutController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout-page")
    public String logoutPage() {
        return "logout";
    }
}

