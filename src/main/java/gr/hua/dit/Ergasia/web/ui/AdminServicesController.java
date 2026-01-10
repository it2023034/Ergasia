package gr.hua.dit.Ergasia.web.ui;

import gr.hua.dit.Ergasia.core.model.AppointmentSlot;
import gr.hua.dit.Ergasia.core.model.DepartmentService;
import gr.hua.dit.Ergasia.core.model.RequestType;
import gr.hua.dit.Ergasia.core.repository.AppointmentSlotRepository;
import gr.hua.dit.Ergasia.core.repository.DepartmentServiceRepository;
import gr.hua.dit.Ergasia.core.repository.RequestTypeRepository;
import gr.hua.dit.Ergasia.core.service.StatisticsService;
import gr.hua.dit.Ergasia.web.dto.StatisticsDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminServicesController {

    private final DepartmentServiceRepository departmentRepository;
    private final RequestTypeRepository requestTypeRepository;
    private final AppointmentSlotRepository appointmentRepository;
    private final StatisticsService statisticsService;

    public AdminServicesController(DepartmentServiceRepository departmentRepository,
                               RequestTypeRepository requestTypeRepository,
                               AppointmentSlotRepository appointmentRepository,
                               StatisticsService statisticsService) {
        this.departmentRepository = departmentRepository;
        this.requestTypeRepository = requestTypeRepository;
        this.appointmentRepository = appointmentRepository;
        this.statisticsService = statisticsService;
    }

    // ------------------ Departments ------------------
    @GetMapping("/departments")
    public String departments(Model model) {
        List<DepartmentService> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        return "departments"; // departments.html
    }

    // ------------------ Request Types ------------------
    @GetMapping("/request-types")
    public String requestTypes(Model model) {
        List<RequestType> requestTypes = requestTypeRepository.findAll();
        model.addAttribute("requestTypes", requestTypes);
        return "request-types"; // request-types.html
    }

    // ------------------ Appointments ------------------
    @GetMapping("/appointments")
    public String appointments(Model model) {
        List<AppointmentSlot> appointments = appointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        return "appointments"; // appointments.html
    }

    // ------------------ Statistics ------------------
    @GetMapping("/statistics")
    public String statistics(Model model) {
        StatisticsDTO stats = statisticsService.getStatistics();
        model.addAttribute("stats", stats);
        return "statistics"; // statistics.html
    }
}
