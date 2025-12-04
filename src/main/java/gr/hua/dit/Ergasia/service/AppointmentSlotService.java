package gr.hua.dit.adminbt.adminbt.service;

import gr.hua.dit.adminbt.adminbt.model.AppointmentSlot;
import gr.hua.dit.adminbt.adminbt.model.Department;
import gr.hua.dit.adminbt.adminbt.repository.AppointmentSlotRepository;
import gr.hua.dit.adminbt.adminbt.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentSlotService {

    private final AppointmentSlotRepository slotRepository;
    private final DepartmentRepository departmentRepository;

    public AppointmentSlotService(AppointmentSlotRepository slotRepository,
                                  DepartmentRepository departmentRepository) {
        this.slotRepository = slotRepository;
        this.departmentRepository = departmentRepository;
    }

    public AppointmentSlot createSlot(Long departmentId, String dayOfWeek,
                                      LocalTime startTime, LocalTime endTime) {

        Department dept = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        AppointmentSlot slot = new AppointmentSlot(dayOfWeek, startTime, endTime, dept);
        return slotRepository.save(slot);
    }

    public List<AppointmentSlot> getSlotsForDepartment(Long departmentId) {
        return slotRepository.findByDepartmentId(departmentId);
    }
}
