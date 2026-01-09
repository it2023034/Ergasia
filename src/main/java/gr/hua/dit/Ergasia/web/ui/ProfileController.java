package gr.hua.dit.Ergasia.web.ui;

import gr.hua.dit.Ergasia.core.model.User;
import gr.hua.dit.Ergasia.core.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {

        User user = userService.getUserProfile(authentication.getName());
        model.addAttribute("user", user);

        return "profile";
    }
}

