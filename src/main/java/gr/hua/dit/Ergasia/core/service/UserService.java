package gr.hua.dit.Ergasia.core.service;

import gr.hua.dit.Ergasia.web.dto.LoginRequest;
import gr.hua.dit.Ergasia.web.dto.RegisterRequest;
import gr.hua.dit.Ergasia.core.model.Role;
import gr.hua.dit.Ergasia.core.model.User;
import gr.hua.dit.Ergasia.core.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Map<String, Object> login(LoginRequest request) {

        User user = userRepository.findByUsernameOrEmail(request.getIdentifier(), request.getIdentifier()).orElse(null);

        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("role", user.getRole());
            response.put("username", user.getUsername());
            response.put("message", "Επιτυχής σύνδεση");

            return response;
        } else {
            throw new RuntimeException("Λάθος στοιχεία εισόδου");
        }
    }

    public User registerCitizen(RegisterRequest req) {

        if (!userRepository.existsByUsername(req.getUsername()))
            throw new RuntimeException("Username already exists");

        if (!userRepository.existsByEmail(req.getEmail()))
            throw new RuntimeException("Email already exists");

        if (!userRepository.existsByAfm(req.getAfm()))
            throw new RuntimeException("AFM already exists");

        if (!userRepository.existsByIdCardNumber(req.getIdCardNumber()))
            throw new RuntimeException("ID card number already exists");

        User user = new User();
        user.setId(generateUniqueId("15"));
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(Role.CITIZEN);
        user.setEmail(req.getEmail());
        user.setAfm(req.getAfm());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setIdCardNumber(req.getIdCardNumber());
        user.setFatherName(req.getFatherName());
        user.setFatherSurname(req.getFatherSurname());
        user.setMotherName(req.getMotherName());
        user.setMotherSurname(req.getMotherSurname());
        user.setDob(req.getDob());
        user.setBirthPlace(req.getBirthPlace());
        user.setResidencePlace(req.getResidencePlace());
        user.setStreet(req.getStreet());
        user.setStreetNumber(req.getStreetNumber());
        user.setPhone(req.getPhone());

        User savedUser = userRepository.save(user);

        log.info(
                "New citizen registered | id={} | username={} | email={}",
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );

        return savedUser;
    }

    private String generateUniqueId(String prefix) {
        Random random = new Random();
        String id;
        boolean exists;
        do {
            int number = 10000000 + random.nextInt(90000000);
            id = prefix + number;
            exists = userRepository.existsById(id);
        } while (exists);
        return id;
    }

    public User getUserProfile(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
