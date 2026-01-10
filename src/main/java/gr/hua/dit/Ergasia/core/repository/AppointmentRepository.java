package gr.hua.dit.Ergasia.core.repository;

import gr.hua.dit.Ergasia.core.model.Appointment;
import gr.hua.dit.Ergasia.core.model.Employee;
import gr.hua.dit.Ergasia.core.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByRequest(Request req);

    @Query("SELECT a FROM Appointment a WHERE a.request.employee = :employee")
    List<Appointment> findByEmployee(@Param("employee") Employee employee);
}
