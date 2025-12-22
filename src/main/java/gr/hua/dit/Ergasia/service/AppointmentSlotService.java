package gr.hua.dit.Ergasia.service;

import gr.hua.dit.Ergasia.model.AppointmentSlot;
import gr.hua.dit.Ergasia.model.DepartmentService;
import gr.hua.dit.Ergasia.repository.AppointmentSlotRepository;
import gr.hua.dit.Ergasia.repository.DepartmentServiceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentSlotService {

    private final AppointmentSlotRepository slotRepository;
    private final DepartmentServiceRepository departmentRepository;

    public AppointmentSlotService(AppointmentSlotRepository slotRepository,
                                  DepartmentServiceRepository departmentRepository) {
        this.slotRepository = slotRepository;
        this.departmentRepository = departmentRepository;
    }

    public AppointmentSlot createSlot(Long departmentId, String dayOfWeek,
                                      String start, String end) {

        LocalTime startTime = LocalTime.parse(start);
        LocalTime endTime = LocalTime.parse(end);

        DepartmentService dept = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        AppointmentSlot slot = new AppointmentSlot(dayOfWeek.toUpperCase(), startTime, endTime, dept);
        return slotRepository.save(slot);
    }


    public List<AppointmentSlot> getSlotsForDepartment(Long departmentId) {
        return slotRepository.findByDepartmentId(departmentId);
    }
}
