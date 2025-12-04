package gr.hua.dit.Ergasia;

import gr.hua.dit.Ergasia.model.*;
import gr.hua.dit.Ergasia.repository.*;

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
            System.out.println("Ksekinaei to test gia tis employee services.");

            System.out.println("Anazitisi employee sti vasi...");
            Employee employee = userRepository.findByUsernameOrEmail("emp_nikolaou", "n.nikolaou@gov.gr")
                    .map(u -> (Employee) u)
                    .orElseThrow(() -> new RuntimeException("Employee den vrethike"));

            System.out.println("Employee vrethike: " + employee.getFirstName() + " " + employee.getLastName());

            System.out.println("Fere lista aitiseon tis ypiresias tou:");
            List<Request> requests = employeeServices.showListOfRequests(employee);

            if (requests.isEmpty()) {
                System.out.println("Den vrethikan aitiseis gia ton employee.");
            } else {
                Request request = requests.get(0);
                System.out.println("Proti aitisi gia testing: " + request.getTitle());

                System.out.println("Kanoume assign request...");
                employeeServices.assignRequest(request, employee);
                System.out.println("Assign oloklirothike");

                System.out.println("Prosthiki comment...");
                employeeServices.addComment(request, "Auto einai ena test comment");
                System.out.println("Comment prostethike");

                System.out.println("Allagi status se IN_PROGRESS...");
                employeeServices.changeStatus(request, ApplicationStatus.IN_PROGRESS);
                System.out.println("Status allagmeno");

                System.out.println("Epitagri/Apodixi request...");
                employeeServices.approveRequest(request, "Ola ta dikaiologitika einai swsta");
                employeeServices.rejectRequest(request, "Aporripsi gia test");
                System.out.println("Epitagri kai aporripsi oloklirothikan");

                Appointment appointment = appointmentRepository.findAll().stream().findFirst().orElse(null);
                if (appointment != null) {
                    System.out.println("Epitagri, anatheorisi kai akyrwsi appointment...");
                    employeeServices.confirmAppointment(appointment);
                    employeeServices.rescheduleAppointment(appointment, LocalDateTime.now().plusDays(2));
                    employeeServices.cancelAppointment(appointment);
                    System.out.println("Appointment diakriseis oloklirothikan");
                } else {
                    System.out.println("Den vrethikan appointments sti vasi");
                }
            }

            System.out.println("Telos test employee services.");
        };
    }
}
