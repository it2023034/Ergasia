package gr.hua.dit.Ergasia.config;

import gr.hua.dit.Ergasia.core.model.Role;
import gr.hua.dit.Ergasia.core.model.User;
import gr.hua.dit.Ergasia.core.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CitizenInitializer {

    @Bean
    @Order(2) // τρέχει μετά τον UserInitializer
    CommandLineRunner initCitizens(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            createCitizen(repo, encoder, "jkaralis", "2500000001", "ioannis.karalis@gmail.com", "test1234");
            createCitizen(repo, encoder, "maria.l", "2500000002", "mar.lamprou@gmail.com", "pass9988");
            createCitizen(repo, encoder, "d_pap", "2500000003", "dpap@gmail.com", "hello123");
            createCitizen(repo, encoder, "vicky89", "2500000004", "v.ralli@gmail.com", "mypassword");
            createCitizen(repo, encoder, "tsakmakis", "2500000005", "nikos.tsakmakis@gmail.com", "nikos321");
            createCitizen(repo, encoder, "anna_geo", "2500000006", "anna.geo@gmail.com", "anna2024");
            createCitizen(repo, encoder, "mixalis_k", "2500000007", "mix.katsaros@gmail.com", "katsaros1");
            createCitizen(repo, encoder, "sofial", "2500000008", "sofia.liakou@gmail.com", "sofia123");
        };
    }

    private void createCitizen(UserRepository repo, PasswordEncoder encoder,
                               String username, String id, String email, String password) {
        if (repo.existsByUsername(username)) return;

        User u = new User();
        u.setId(id);
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(encoder.encode(password));
        u.setRole(Role.CITIZEN);

        repo.save(u);
    }
}
