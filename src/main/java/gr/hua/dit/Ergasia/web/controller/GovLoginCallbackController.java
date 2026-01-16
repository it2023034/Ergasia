package gr.hua.dit.Ergasia.web.controller;

import gr.hua.dit.Ergasia.core.model.User;
import gr.hua.dit.Ergasia.core.repository.UserRepository;
import gr.hua.dit.Ergasia.core.service.NocClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
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
    public String handleGovCallback(@RequestParam String afm,
                                    @RequestParam String phone,
                                    HttpServletRequest request) {

        // normalize phone
        String normalizedPhone = phone;
        if (normalizedPhone != null && !normalizedPhone.startsWith("+30")) {
            normalizedPhone = "+30" + normalizedPhone;
        }

        // find user
        User user = userRepository.findByAfmAndPhone(afm, normalizedPhone).orElse(null);

        if (user != null) {

            // --- LOGIN USER ---
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            // store auth in session so /profile works
            request.getSession(true).setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    context
            );

            // --- SEND SMS ---
            String time = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));

            nocClientService.sendSms(
                    user.getPhone(),
                    "Επιτυχής σύνδεση στο Gov.gr την " + time
            );

            // --- REDIRECT TO PROFILE ---
            return "redirect:/profile";

        } else {
            // user not found
            return "redirect:/login?error=Invalid Gov.gr credentials. User not found.";
        }
    }
}
