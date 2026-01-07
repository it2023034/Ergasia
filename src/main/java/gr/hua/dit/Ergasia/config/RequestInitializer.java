package gr.hua.dit.Ergasia.config;

import gr.hua.dit.Ergasia.core.model.Request;
import gr.hua.dit.Ergasia.core.model.Employee;
import gr.hua.dit.Ergasia.core.model.DepartmentService;
import gr.hua.dit.Ergasia.core.model.User;
import gr.hua.dit.Ergasia.core.repository.RequestRepository;
import gr.hua.dit.Ergasia.core.repository.EmployeeRepository;
import gr.hua.dit.Ergasia.core.repository.DepartmentServiceRepository;
import gr.hua.dit.Ergasia.core.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class RequestInitializer {

    @Bean
    @Order(4)
    CommandLineRunner initRequests(RequestRepository requestRepo,
                                   EmployeeRepository employeeRepo,
                                   DepartmentServiceRepository serviceRepo,
                                   UserRepository userRepo) {
        return args -> {

            // Employees (υπεύθυνοι)
            Employee nikolaou = employeeRepo.findByUsername("empl_nikolaou").orElseThrow();
            Employee papageorgiou = employeeRepo.findByUsername("empl_papageorgiou").orElseThrow();
            Employee karapiperis = employeeRepo.findByUsername("empl_karapiperis").orElseThrow();

            // Department Services
            DepartmentService kepDimosion = serviceRepo.findByServiceName("KEP Tmima Dimosion").orElseThrow();
            DepartmentService kepPolitismou = serviceRepo.findByServiceName("KEP Tmima Politismou").orElseThrow();
            DepartmentService kepEkpaidefsis = serviceRepo.findByServiceName("KEP Tmima Ekpaidefsis").orElseThrow();
            DepartmentService kepYgeias = serviceRepo.findByServiceName("KEP Tmima Ygeias").orElseThrow();
            DepartmentService kepOikonomikon = serviceRepo.findByServiceName("KEP Tmima Oikonomikon").orElseThrow();

            // Citizens (χρήστες που κάνουν τα αιτήματα)
            User jkaralis = userRepo.findByUsername("jkaralis").orElseThrow();
            User mariaL = userRepo.findByUsername("maria.l").orElseThrow();
            User dpap = userRepo.findByUsername("d_pap").orElseThrow();
            User vicky = userRepo.findByUsername("vicky89").orElseThrow();
            User tsakmakis = userRepo.findByUsername("tsakmakis").orElseThrow();
            User annaGeo = userRepo.findByUsername("anna_geo").orElseThrow();
            User mixalisK = userRepo.findByUsername("mixalis_k").orElseThrow();
            User sofial = userRepo.findByUsername("sofial").orElseThrow();

            // Requests
            create(requestRepo, jkaralis, "Aithma gia dimosio eggrafo", nikolaou, kepDimosion,
                    List.of("Paraklisi gia eggrafa", "Xreiazetai epemvasi"));

            create(requestRepo, mariaL, "Aithma gia politikous dokimous", papageorgiou, kepPolitismou,
                    List.of("Prothiki neou arxeiou", "Prothiki se sistima"));

            create(requestRepo, dpap, "Aithma gia ekpaideftika ylika", karapiperis, kepEkpaidefsis,
                    List.of("Apodoxi aithmatos", "Epistrofi apofasis"));

            create(requestRepo, vicky, "Aithma gia ygeionomiko episkepsi", nikolaou, kepYgeias,
                    List.of("Paraklisi gia eggrafa", "Xreiazetai epemvasi"));

            create(requestRepo, tsakmakis, "Aithma gia oikonomika dedomena", papageorgiou, kepOikonomikon,
                    List.of("Prothiki neou arxeiou", "Prothiki se sistima"));

            create(requestRepo, annaGeo, "Aithma gia nea epikoinonia", karapiperis, kepDimosion,
                    List.of("Apodoxi aithmatos", "Epistrofi apofasis"));

            create(requestRepo, mixalisK, "Aithma gia anakoinoseis politismou", nikolaou, kepPolitismou,
                    List.of("Paraklisi gia eggrafa", "Xreiazetai epemvasi"));
        };
    }

    private void create(RequestRepository repo, User citizen, String title,
                        Employee employee, DepartmentService service,
                        List<String> comments) {

        boolean exists = repo.existsByTitleAndEmployee(title, employee);
        if (exists) return;

        Request r = new Request(title, service, comments);
        r.setEmployee(employee);  // Υπεύθυνος
        r.setCitizen(citizen);   // Χρήστης που υποβάλλει το αίτημα
        repo.save(r);
    }
}
