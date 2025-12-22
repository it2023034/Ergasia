package gr.hua.dit.Ergasia.controller;

import gr.hua.dit.Ergasia.dto.LoginRequest;
import gr.hua.dit.Ergasia.dto.RegisterRequest;
import gr.hua.dit.Ergasia.model.User;
import gr.hua.dit.Ergasia.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

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
        return ResponseEntity.ok(userService.login(request));
    }
}

