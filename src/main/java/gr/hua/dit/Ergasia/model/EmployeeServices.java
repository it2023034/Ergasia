package gr.hua.dit.Ergasia.model;

import gr.hua.dit.Ergasia.repository.AppointmentRepository;
import gr.hua.dit.Ergasia.repository.RequestRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServices {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServices.class);

    private final RequestRepository requestRepository;
    private final AppointmentRepository appointmentRepository;

    public EmployeeServices(RequestRepository requestRepository, AppointmentRepository appointmentRepository) {
        this.requestRepository = requestRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<Request> showListOfRequests(Employee employee) {
        DepartmentService departmentService = employee.getDepartmentService();
        if (departmentService == null) {
            logger.warn("Employee {} has no assigned service", employee.getLastName() + " " + employee.getFirstName());
            return List.of();
        }

        List<Request> requests = requestRepository.findByDepartmentService(departmentService);

        for (Request r : requests) {
            String nameOfEmployee = r.getEmployee() != null ? r.getEmployee().getLastName() + r.getEmployee().getFirstName() : "None";
            logger.info("Request: {} | Status: {} | Assigned to: {}", r.getTitle(), r.getStatus(), nameOfEmployee);
        }

        return requests;
    }

    public void assignRequest(Request request, Employee employee) {
        request.setEmployee(employee);
        request.setStatus(ApplicationStatus.RECEIVED);
        requestRepository.save(request);
        logger.info("Request '{}' assigned to {}", request.getTitle(), employee.getLastName() + " " + employee.getFirstName());
    }

    public void selfAssignRequest(Request request, Employee employee) {
        if (request.getEmployee() != null) {
            logger.warn("Request '{}' is not available for self-assignment", request.getTitle());
            return;
        }
        request.setEmployee(employee);
        request.setStatus(ApplicationStatus.RECEIVED);
        requestRepository.save(request);
        logger.info("Request '{}' self-assigned to {}", request.getTitle(), employee.getLastName() + " " + employee.getFirstName());
    }

    public void addComment(Request request, String comment) {
        request.getComment().add(comment);
        requestRepository.save(request);
        logger.info("Comment added to request '{}': {}", request.getTitle(), comment);
    }

    public void changeStatus(Request request, ApplicationStatus status) {
        request.setStatus(status);
        requestRepository.save(request);
        logger.info("Request '{}' status changed to {}", request.getTitle(), status);
    }

    public void approveRequest(Request request, String documentation) {
        request.setStatus(ApplicationStatus.COMPLETED);
        requestRepository.save(request);
        logger.info("Request '{}' approved. Reason: {}", request.getTitle(), documentation);
    }

    public void rejectRequest(Request request, String documentation) {
        request.setStatus(ApplicationStatus.REJECTED);
        requestRepository.save(request);
        logger.info("Request '{}' rejected. Reason: {}", request.getTitle(), documentation);
    }

    public void confirmAppointment(Appointment appointment) {
        if (appointment == null) {
            logger.warn("No available appointments for confirmation");
            return;
        }
        appointment.setConfirmed(true);
        appointmentRepository.save(appointment);
        logger.info("Appointment for request '{}' confirmed", appointment.getRequest().getTitle());
    }

    public void rescheduleAppointment(Appointment appointment, LocalDateTime date) {
        if (appointment == null) {
            logger.warn("No available appointments for rescheduling");
            return;
        }
        appointment.setDate(date);
        appointment.setConfirmed(false);
        appointmentRepository.save(appointment);
        logger.info("Appointment for request '{}' rescheduled to {}", appointment.getRequest().getTitle(), date);
    }

    public void cancelAppointment(Appointment appointment) {
        if (appointment == null) {
            logger.warn("No available appointments for cancellation");
            return;
        }
        appointment.setDate(null);
        appointment.setConfirmed(false);
        appointmentRepository.save(appointment);
        logger.info("Appointment for request '{}' canceled", appointment.getRequest().getTitle());
    }
}
