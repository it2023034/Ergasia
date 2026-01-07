package gr.hua.dit.Ergasia.config;

import gr.hua.dit.Ergasia.core.model.Employee;
import gr.hua.dit.Ergasia.core.model.Role;
import gr.hua.dit.Ergasia.core.model.DepartmentService;
import gr.hua.dit.Ergasia.core.repository.EmployeeRepository;
import gr.hua.dit.Ergasia.core.repository.DepartmentServiceRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EmpServInitializer {

    @Bean
    @Order(3)
    CommandLineRunner initEmployees(EmployeeRepository employeeRepo,
                                    DepartmentServiceRepository serviceRepo,
                                    PasswordEncoder encoder) {

        return args -> {

            DepartmentService service1 = getOrCreateService(serviceRepo, "KEP Tmima Dimosion");
            DepartmentService service2 = getOrCreateService(serviceRepo, "KEP Tmima Politismou");
            DepartmentService service3 = getOrCreateService(serviceRepo, "KEP Tmima Ekpaidefsis");
            DepartmentService service4 = getOrCreateService(serviceRepo, "KEP Tmima Ygeias");
            DepartmentService service5 = getOrCreateService(serviceRepo, "KEP Tmima Oikonomikon");

            createEmployee(employeeRepo, encoder, "empl_nikolaou", "4500000011",
                    "n.nikolaou@gov.gr", "emp123", "Nikos", "Nikolaou", service1);

            createEmployee(employeeRepo, encoder, "empl_papageorgiou", "4500000012",
                    "g.papageorgiou@gov.gr", "emp1234", "Georgia", "Papageorgiou", service2);

            createEmployee(employeeRepo, encoder, "empl_karapiperis", "4500000013",
                    "a.karapiperis@gov.gr", "emp12345", "Alexandros", "Karapiperis", service3);

            createEmployee(employeeRepo, encoder, "empl_tsoklis", "4500000014",
                    "m.tsoklis@gov.gr", "emp567", "Maria", "Tsoklis", service4);

            createEmployee(employeeRepo, encoder, "empl_vlachos", "4500000015",
                    "d.vlachos@gov.gr", "emp890", "Dimitris", "Vlachos", service5);

            createEmployee(employeeRepo, encoder, "empl_makris", "4500000016",
                    "s.makris@gov.gr", "emp321", "Sofia", "Makris", service1);

            createEmployee(employeeRepo, encoder, "empl_papathanasiou", "4500000017",
                    "k.papathanasiou@gov.gr", "emp654", "Konstantinos", "Papathanasiou", service2);

            createEmployee(employeeRepo, encoder, "empl_georgiou", "4500000018",
                    "e.georgiou@gov.gr", "emp987", "Eleni", "Georgiou", service3);

            createEmployee(employeeRepo, encoder, "empl_kostopoulos", "4500000019",
                    "p.kostopoulos@gov.gr", "emp111", "Panagiotis", "Kostopoulos", service4);

            createEmployee(employeeRepo, encoder, "empl_roumeliotis", "4500000020",
                    "l.roumeliotis@gov.gr", "emp222", "Lefteris", "Roumeliotis", service5);
        };
    }

    private DepartmentService getOrCreateService(DepartmentServiceRepository repo, String name) {
        return repo.findByServiceName(name)
                .orElseGet(() -> {
                    DepartmentService s = new DepartmentService();
                    s.setServiceName(name);
                    return repo.save(s);
                });
    }

    private void createEmployee(EmployeeRepository repo,
                                PasswordEncoder encoder,
                                String username, String id,
                                String email, String password,
                                String firstName, String lastName,
                                DepartmentService departmentService) {

        if (repo.existsByUsername(username)) return;

        Employee emp = new Employee();
        emp.setId(id);
        emp.setUsername(username);
        emp.setEmail(email);
        emp.setPassword(encoder.encode(password));
        emp.setRole(Role.EMPLOYEE);
        emp.setFirstName(firstName);
        emp.setLastName(lastName);
        emp.setDepartmentService(departmentService);

        repo.save(emp);
    }
}