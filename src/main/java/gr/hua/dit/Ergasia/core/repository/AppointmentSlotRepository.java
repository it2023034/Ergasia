package gr.hua.dit.Ergasia.core.repository;

import gr.hua.dit.Ergasia.core.model.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {
    List<AppointmentSlot> findByDepartmentServiceId(Long departmentId);
}
