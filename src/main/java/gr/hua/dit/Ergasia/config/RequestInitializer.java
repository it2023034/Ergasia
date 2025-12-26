package gr.hua.dit.Ergasia.config;

import gr.hua.dit.Ergasia.core.model.Request;
import gr.hua.dit.Ergasia.core.model.Employee;
import gr.hua.dit.Ergasia.core.model.DepartmentService;
import gr.hua.dit.Ergasia.core.repository.RequestRepository;
import gr.hua.dit.Ergasia.core.repository.EmployeeRepository;
import gr.hua.dit.Ergasia.core.repository.DepartmentServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RequestInitializer {

    @Bean
    CommandLineRunner initRequests(RequestRepository requestRepo,
                                   EmployeeRepository employeeRepo,
                                   DepartmentServiceRepository serviceRepo) {
        return args -> {

            Employee nikolaou = employeeRepo.findByUsername("empl_nikolaou").orElseThrow();
            Employee papageorgiou = employeeRepo.findByUsername("empl_papageorgiou").orElseThrow();
            Employee karapiperis = employeeRepo.findByUsername("empl_karapiperis").orElseThrow();

            DepartmentService kepDimosion = serviceRepo.findByServiceName("KEP Tmima Dimosion").orElseThrow();
            DepartmentService kepPolitismou = serviceRepo.findByServiceName("KEP Tmima Politismou").orElseThrow();
            DepartmentService kepEkpaidefsis = serviceRepo.findByServiceName("KEP Tmima Ekpaidefsis").orElseThrow();
            DepartmentService kepYgeias = serviceRepo.findByServiceName("KEP Tmima Ygeias").orElseThrow();
            DepartmentService kepOikonomikon = serviceRepo.findByServiceName("KEP Tmima Oikonomikon").orElseThrow();

            create(requestRepo, "Aithma gia dimosio eggrafo", nikolaou, kepDimosion,
                    List.of("Paraklisi gia eggrafa", "Xreiazetai epemvasi"));

            create(requestRepo, "Aithma gia politikous dokimous", papageorgiou, kepPolitismou,
                    List.of("Prothiki neou arxeiou", "Prothiki se sistima"));

            create(requestRepo, "Aithma gia ekpaideftika ylika", karapiperis, kepEkpaidefsis,
                    List.of("Apodoxi aithmatos", "Epistrofi apofasis"));

            create(requestRepo, "Aithma gia ygeionomiko episkepsi", nikolaou, kepYgeias,
                    List.of("Paraklisi gia eggrafa", "Xreiazetai epemvasi"));

            create(requestRepo, "Aithma gia oikonomika dedomena", papageorgiou, kepOikonomikon,
                    List.of("Prothiki neou arxeiou", "Prothiki se sistima"));

            create(requestRepo, "Aithma gia nea epikoinonia", karapiperis, kepDimosion,
                    List.of("Apodoxi aithmatos", "Epistrofi apofasis"));

            create(requestRepo, "Aithma gia anakoinoseis politismou", nikolaou, kepPolitismou,
                    List.of("Paraklisi gia eggrafa", "Xreiazetai epemvasi"));

        };
    }

    private void create(RequestRepository repo, String title,
                        Employee employee, DepartmentService service,
                        List<String> comments) {

        boolean exists = repo.existsByTitleAndEmployee(title, employee);
        if (exists) return;

        Request r = new Request(title, service, comments);
        r.setEmployee(employee);
        repo.save(r);
    }
}
