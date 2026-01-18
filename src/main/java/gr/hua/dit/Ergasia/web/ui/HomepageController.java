package gr.hua.dit.Ergasia.web.ui;

import gr.hua.dit.Ergasia.core.security.AuthUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

    @GetMapping("/homepage")
    public String showHomepage(final Authentication authentication) {
        if (AuthUtils.isAuthenticated(authentication)) {
            return "redirect:/profile";
        }
        return "homepage";
    }
}
