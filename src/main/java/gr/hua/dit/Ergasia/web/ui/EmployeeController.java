package gr.hua.dit.Ergasia.web.ui;

import gr.hua.dit.Ergasia.core.model.Appointment;
import gr.hua.dit.Ergasia.core.model.Employee;
import gr.hua.dit.Ergasia.core.model.Request;
import gr.hua.dit.Ergasia.core.model.ApplicationStatus;
import gr.hua.dit.Ergasia.core.repository.AppointmentRepository;
import gr.hua.dit.Ergasia.core.repository.EmployeeRepository;
import gr.hua.dit.Ergasia.core.repository.RequestRepository;
import gr.hua.dit.Ergasia.core.service.EmployeeServices;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeServices employeeServices;
    private final EmployeeRepository employeeRepository;
    private final RequestRepository requestRepository;
    private final AppointmentRepository appointmentRepository;

    public EmployeeController(EmployeeServices employeeServices,
                              EmployeeRepository employeeRepository,
                              RequestRepository requestRepository,
                              AppointmentRepository appointmentRepository) {
        this.employeeServices = employeeServices;
        this.employeeRepository = employeeRepository;
        this.requestRepository = requestRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/requests")
    public String listRequests(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Employee employee = getEmployeeFromUser(userDetails);
        if (employee == null) {
            return "redirect:/login";
        }

        List<Request> requests = employeeServices.showListOfRequests(employee);
        model.addAttribute("requests", requests);
        return "requests";
    }

    @PostMapping("/requests/{id}/self-assign")
    public String selfAssignRequest(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        Request request = getRequestById(id);
        Employee employee = getEmployeeFromUser(userDetails);
        if (request != null && employee != null) {
            employeeServices.selfAssignRequest(request, employee);
        }
        return "redirect:/employee/requests";
    }

    @PostMapping("/requests/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestParam String comment) {
        Request request = getRequestById(id);
        if (request != null) {
            employeeServices.addComment(request, comment);
        }
        return "redirect:/employee/requests";
    }

    @PostMapping("/requests/{id}/status")
    public String changeStatus(@PathVariable Long id,
                               @RequestParam ApplicationStatus status) {
        Request request = getRequestById(id);
        if (request != null) {
            employeeServices.changeStatus(request, status);
        }
        return "redirect:/employee/requests";
    }

    @PostMapping("/empappointments/{id}/confirm")
    public String confirmAppointment(@PathVariable Long id) {
        Appointment appointment = getAppointmentById(id);
        if (appointment != null) {
            employeeServices.confirmAppointment(appointment);
        }
        return "redirect:/employee/empappointments";
    }

    @PostMapping("/empappointments/{id}/reschedule")
    public String rescheduleAppointment(@PathVariable Long id,
                                        @RequestParam String date) {
        Appointment appointment = getAppointmentById(id);
        if (appointment != null) {
            LocalDateTime newDate = LocalDateTime.parse(date);
            employeeServices.rescheduleAppointment(appointment, newDate);
        }
        return "redirect:/employee/empappointments";
    }

    @PostMapping("/empappointments/{id}/cancel")
    public String cancelAppointment(@PathVariable Long id) {
        Appointment appointment = getAppointmentById(id);
        if (appointment != null) {
            employeeServices.cancelAppointment(appointment);
        }
        return "redirect:/employee/empappointments";
    }

    @GetMapping("/empappointments")
    public String listAppointments(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Employee employee = getEmployeeFromUser(userDetails);
        if (employee == null) return "redirect:/login";

        List<Appointment> appointments = appointmentRepository.findByEmployee(employee);

        model.addAttribute("appointments", appointments);
        return "empappointments";
    }


    private Employee getEmployeeFromUser(UserDetails userDetails) {
        if (userDetails == null) return null;
        Optional<Employee> employee = employeeRepository.findByUsername(userDetails.getUsername());
        return employee.orElse(null);
    }

    private Request getRequestById(Long id) {
        if (id == null) return null;
        return requestRepository.findById(id).orElse(null);
    }

    private Appointment getAppointmentById(Long id) {
        if (id == null) return null;
        return appointmentRepository.findById(id).orElse(null);
    }
}
