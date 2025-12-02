package gr.hua.dit.Ergasia.controller;

import gr.hua.dit.Ergasia.dto.LoginRequest;
import gr.hua.dit.Ergasia.dto.RegisterRequest;
import gr.hua.dit.Ergasia.model.User;
import gr.hua.dit.Ergasia.repository.UserRepository;
import gr.hua.dit.Ergasia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User newUser = userService.registerCitizen(request);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Επιτυχής εγγραφή");
            response.put("id", newUser.getId());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsernameOrEmail(request.getIdentifier(), request.getIdentifier())
                .orElse(null);

        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("role", user.getRole());
            response.put("username", user.getUsername());
            response.put("message", "Επιτυχής σύνδεση");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Λάθος στοιχεία εισόδου");
        }
    }
}
