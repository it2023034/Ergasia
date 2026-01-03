package gr.hua.dit.Ergasia.config;

import gr.hua.dit.Ergasia.core.model.Appointment;
import gr.hua.dit.Ergasia.core.model.Request;
import gr.hua.dit.Ergasia.core.repository.AppointmentRepository;
import gr.hua.dit.Ergasia.core.repository.RequestRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class AppointmentInitializer {

    @Bean
    CommandLineRunner initAppointments(AppointmentRepository appointmentRepo,
                                       RequestRepository requestRepo) {
        return args -> {

            List<Request> allRequests = requestRepo.findAll();

            for (int i = 0; i < allRequests.size() && i < 7; i++) {
                Request req = allRequests.get(i);

                if (appointmentRepo.existsByRequest(req)) continue;

                Appointment app = new Appointment();
                app.setRequest(req);

                LocalDateTime dateTime = LocalDateTime.now().plusDays(i + 1)
                        .withHour(10 + i % 5)
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0);

                app.setDate(dateTime);
                app.setConfirmed(i % 2 == 0);

                appointmentRepo.save(app);
            }
        };
    }
}