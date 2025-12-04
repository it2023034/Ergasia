package gr.hua.dit.Ergasia;

import gr.hua.dit.Ergasia.model.Appointment;
import gr.hua.dit.Ergasia.model.ApplicationStatus;
import gr.hua.dit.Ergasia.model.Employee;
import gr.hua.dit.Ergasia.model.Request;
import gr.hua.dit.Ergasia.model.EmployeeServices;
import gr.hua.dit.Ergasia.repository.AppointmentRepository;
import gr.hua.dit.Ergasia.repository.RequestRepository;
import gr.hua.dit.Ergasia.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ErgasiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErgasiaApplication.class, args);
    }

    @Bean
    public CommandLineRunner testEmployeeServices(
            EmployeeServices employeeServices,
            RequestRepository requestRepository,
            AppointmentRepository appointmentRepository,
            UserRepository userRepository
    ) {
        return args -> {

            System.out.println("===== TESTING EMPLOYEE SERVICES =====");

            // 1️⃣ Βρες έναν employee από τη βάση (αλλάζεις username ανάλογα με τα δεδομένα σου)
            Employee employee = userRepository.findByUsernameOrEmail("employee1", "employee1@test.com")
                    .map(u -> (Employee) u)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            System.out.println("Employee: " + employee.getFirstName() + " " + employee.getLastName());


            // 2️⃣ Φέρε όλα τα requests της υπηρεσίας του
            System.out.println("\n--- showListOfRequests ---");
            List<Request> requests = employeeServices.showListOfRequests(employee);

            if (requests.isEmpty()) {
                System.out.println("No requests found for employee.");
                return;
            }

            // Διάλεξε ένα request για τεστ
            Request request = requests.getFirst();


            // 3️⃣ assignRequest
            System.out.println("\n--- assignRequest ---");
            employeeServices.assignRequest(request, employee);


            // 4️⃣ addComment
            System.out.println("\n--- addComment ---");
            employeeServices.addComment(request, "Αυτό είναι ένα τεστ comment");


            // 5️⃣ changeStatus
            System.out.println("\n--- changeStatus ---");
            employeeServices.changeStatus(request, ApplicationStatus.IN_PROGRESS);


            // 6️⃣ approveRequest
            System.out.println("\n--- approveRequest ---");
            employeeServices.approveRequest(request, "Όλα τα δικαιολογητικά είναι σωστά.");


            // 7️⃣ rejectRequest (δοκιμαστικά – απλά δείχνει τη λειτουργία)
            System.out.println("\n--- rejectRequest ---");
            employeeServices.rejectRequest(request, "Απόρριψη για τεστ.");


            // 8️⃣ Appointment tests
            System.out.println("\n--- Appointment Tests ---");
            Appointment appointment = appointmentRepository.findAll().stream().findFirst().orElse(null);

            if (appointment != null) {

                // confirm
                System.out.println("\n--- confirmAppointment ---");
                employeeServices.confirmAppointment(appointment);

                // reschedule
                System.out.println("\n--- rescheduleAppointment ---");
                employeeServices.rescheduleAppointment(appointment, LocalDateTime.now().plusDays(2));

                // cancel
                System.out.println("\n--- cancelAppointment ---");
                employeeServices.cancelAppointment(appointment);
            } else {
                System.out.println("No appointments found in DB.");
            }

            System.out.println("\n===== END OF TEST =====");
        };
    }
}

