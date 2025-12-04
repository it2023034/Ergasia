package gr.hua.dit.adminbt.adminbt.repository;

import gr.hua.dit.adminbt.adminbt.model.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {
    List<AppointmentSlot> findByDepartmentId(Long departmentId);
}
