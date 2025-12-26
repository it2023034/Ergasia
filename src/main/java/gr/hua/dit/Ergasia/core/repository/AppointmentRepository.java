package gr.hua.dit.Ergasia.core.repository;

import gr.hua.dit.Ergasia.core.model.Appointment;
import gr.hua.dit.Ergasia.core.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByRequest(Request req);
}
