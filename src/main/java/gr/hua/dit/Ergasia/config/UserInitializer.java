package gr.hua.dit.Ergasia.config;

import gr.hua.dit.Ergasia.model.Role;
import gr.hua.dit.Ergasia.model.User;
import gr.hua.dit.Ergasia.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository repo, PasswordEncoder encoder) {
        return args -> {

            if (!repo.existsByUsername("admin")) {
                    User admin = new User();
                    admin.setId("7500000001");
                    admin.setUsername("admin");
                    admin.setPassword(encoder.encode("admin123"));
                    admin.setEmail("admin@gov.gr");
                    admin.setRole(Role.ADMIN);
                    repo.save(admin);
                }

                if (!repo.existsByUsername("employee")) {
                    User emp = new User();
                    emp.setId("4500000001");
                    emp.setUsername("employee");
                    emp.setPassword(encoder.encode("emp123"));
                    emp.setEmail("employee@gov.gr");
                    emp.setRole(Role.EMPLOYEE);
                    repo.save(emp);
                }

            if (!repo.existsByUsername("d_papadopoulos")) {
                User u = new User();
                u.setId("7500000002");
                u.setUsername("d_papadopoulos");
                u.setPassword(encoder.encode("Admin@2025"));
                u.setEmail("dim.papadopoulos@gov.gr");
                u.setRole(Role.ADMIN);
                repo.save(u);
            }

            create(repo, encoder, "empl_konstantinou", "4500000002",
                    "m.konstantinou@gov.gr", "employee123", Role.EMPLOYEE);

            create(repo, encoder, "empl_papadakis", "4500000003",
                    "g.papadakis@gov.gr", "emp12345", Role.EMPLOYEE);

            create(repo, encoder, "empl_athanasiou", "4500000004",
                    "e.athanasiou@gov.gr", "securepass1", Role.EMPLOYEE);

            create(repo, encoder, "jkaralis", "2500000001",
                    "ioannis.karalis@gmail.com", "test1234", Role.CITIZEN);

            create(repo, encoder, "maria.l", "2500000002",
                    "mar.lamprou@gmail.com", "pass9988", Role.CITIZEN);

            create(repo, encoder, "d_pap", "2500000003",
                    "dpap@gmail.com", "hello123", Role.CITIZEN);

            create(repo, encoder, "vicky89", "2500000004",
                    "v.ralli@gmail.com", "mypassword", Role.CITIZEN);

            create(repo, encoder, "tsakmakis", "2500000005",
                    "nikos.tsakmakis@gmail.com", "nikos321", Role.CITIZEN);

            create(repo, encoder, "anna_geo", "2500000006",
                    "anna.geo@gmail.com", "anna2024", Role.CITIZEN);

            create(repo, encoder, "mixalis_k", "2500000007",
                    "mix.katsaros@gmail.com", "katsaros1", Role.CITIZEN);

            create(repo, encoder, "sofial", "2500000008",
                    "sofia.liakou@gmail.com", "sofia123", Role.CITIZEN);
        };
    }

    private void create(UserRepository repo, PasswordEncoder encoder,
                        String username, String id,
                        String email, String password, Role role) {

        if (repo.existsByUsername(username)) return;

        User u = new User();
        u.setId(id);

        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(encoder.encode(password));
        u.setRole(role);

        repo.save(u);
    }
}
