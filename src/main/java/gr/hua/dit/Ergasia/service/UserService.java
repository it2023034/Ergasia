package gr.hua.dit.Ergasia.service;

import gr.hua.dit.Ergasia.dto.RegisterRequest;
import gr.hua.dit.Ergasia.model.Role;
import gr.hua.dit.Ergasia.model.User;
import gr.hua.dit.Ergasia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerCitizen(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) throw new RuntimeException("Username exists");
        if (userRepository.existsByEmail(req.getEmail())) throw new RuntimeException("Email exists");
        if (userRepository.existsByAfm(req.getAfm())) throw new RuntimeException("AFM exists");
        if (userRepository.existsByIdCardNumber(req.getIdCardNumber())) throw new RuntimeException("ID Card exists");

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
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

        String generatedId = generateUniqueId("15");
        user.setId(generatedId);

        return userRepository.save(user);
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
}
