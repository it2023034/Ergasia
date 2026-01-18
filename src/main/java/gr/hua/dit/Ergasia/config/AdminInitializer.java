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
public class AdminInitializer {

    @Bean
    @Order(1)
    CommandLineRunner initAdmins(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            createAdmin(repo, encoder, "athanasios.krios", "7500000001", "athanasios.krios@gov.gr", "Admin@2026", "123406789", "2101234567");
            createAdmin(repo, encoder, "eleftheria.nyssa", "7500000002", "eleftheria.nyssa@gov.gr", "SecurePass1", "907654321", "2107654321");
            createAdmin(repo, encoder, "zephyros.elios", "7500000003", "zephyros.elios@gov.gr", "UltraSafe2026", "456089123", "2101122334");
        };
    }

    private void createAdmin(UserRepository repo, PasswordEncoder encoder,
                             String username, String id, String email, String password,
                             String afm, String phone) {
        if (repo.existsByUsername(username)) return;

        User admin = new User();
        admin.setId(id);
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPassword(encoder.encode(password));
        admin.setRole(Role.ADMIN);
        admin.setAfm(afm);
        admin.setPhone(phone);

        repo.save(admin);
    }
}
