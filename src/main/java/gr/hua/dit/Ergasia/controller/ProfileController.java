package gr.hua.dit.Ergasia.controller;

import gr.hua.dit.Ergasia.model.User;
import gr.hua.dit.Ergasia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public String profilePage(Model model, Authentication authentication) {

        if (authentication == null) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);

        model.addAttribute("user", user);

        return "profile";
    }
}
