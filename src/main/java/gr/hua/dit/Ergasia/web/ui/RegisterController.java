package gr.hua.dit.Ergasia.web.ui;

import gr.hua.dit.Ergasia.web.dto.RegisterRequest;
import gr.hua.dit.Ergasia.web.dto.noc.UserLookupResult; // Βεβαιωθείτε ότι έχετε δημιουργήσει αυτό το DTO
import gr.hua.dit.Ergasia.core.service.UserService;
import gr.hua.dit.Ergasia.core.service.NocClientService; // Η νέα υπηρεσία που καλεί το NOC
import gr.hua.dit.Ergasia.core.security.AuthUtils;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final UserService userService;
    private final NocClientService nocClientService;

    // Χρησιμοποιούμε Constructor Injection (προτείνεται αντί του @Autowired σε πεδία)
    public RegisterController(UserService userService, NocClientService nocClientService) {
        this.userService = userService;
        this.nocClientService = nocClientService;
    }

    @GetMapping("/register")
    public String registerPage(
            Authentication authentication,
            Model model
    ) {
        if (AuthUtils.isAuthenticated(authentication)) {
            return "redirect:/profile";
        }

        RegisterRequest request = new RegisterRequest();
        model.addAttribute("registerRequest", request);

        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registerRequest") RegisterRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        // 1. Έλεγχος για σφάλματα εγκυρότητας της φόρμας (π.χ. @NotBlank, @Size)
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            // 2. Κλήση της εξωτερικής υπηρεσίας NOC για έλεγχο αν ο χρήστης υπάρχει στο "Μητρώο"
            // Σημείωση: Το RegisterRequest είναι record, οπότε καλούμε request.username()
            UserLookupResult lookup = nocClientService.lookupUser(request.getUsername());

            // 3. Αν ο χρήστης δεν βρεθεί στην υπηρεσία NOC, εμφανίζουμε σφάλμα
            if (lookup == null || !lookup.exists()) {
                bindingResult.rejectValue("username", "error.user",
                        "Ο αριθμός ταυτότητας/Username δεν βρέθηκε στο κυβερνητικό μητρώο (NOC).");
                return "register";
            }

            // 4. Αν η ταυτοποίηση από το NOC πέτυχε, προχωράμε στην εγγραφή στη δική μας βάση
            userService.registerCitizen(request);
            return "redirect:/login";

        } catch (RuntimeException e) {
            // Διαχείριση σφαλμάτων από την πλευρά της δικής μας βάσης (π.χ. duplicate username)
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        } catch (Exception e) {
            // Διαχείριση περίπτωσης που η υπηρεσία NOC είναι εκτός λειτουργίας
            model.addAttribute("errorMessage", "Η υπηρεσία NOC δεν είναι διαθέσιμη αυτή τη στιγμή. Δοκιμάστε αργότερα.");
            return "register";
        }
    }
}