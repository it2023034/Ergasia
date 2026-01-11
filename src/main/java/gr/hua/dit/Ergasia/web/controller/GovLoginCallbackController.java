package gr.hua.dit.Ergasia.web.controller;

import gr.hua.dit.Ergasia.core.model.User;
import gr.hua.dit.Ergasia.core.repository.UserRepository;
import gr.hua.dit.Ergasia.core.service.NocClientService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class GovLoginCallbackController {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final NocClientService nocClientService;

    public GovLoginCallbackController(UserRepository userRepository,
                                      UserDetailsService userDetailsService,
                                      NocClientService nocClientService) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.nocClientService = nocClientService;
    }

    @GetMapping("/login/gov/callback")
    public String handleGovCallback(@RequestParam String afm, @RequestParam String phone) {
        // Ταυτοποίηση στοιχείων στη δική μας βάση
        User user = userRepository.findByAfmAndPhone(afm, phone).orElse(null);

        if (user != null) {
            // Αυτόματη σύνδεση (Manual Authentication)
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Αποστολή SMS ειδοποίησης μέσω NOC
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));
            nocClientService.sendSms(phone, "Επιτυχής σύνδεση στο Gov.gr την " + time);

            return "redirect:/profile";
        } else {
            return "redirect:/login?error=Invalid Gov.gr credentials. User not found.";
        }
    }
}