package gr.hua.dit.Ergasia.core.service;

import gr.hua.dit.Ergasia.core.model.*;
import gr.hua.dit.Ergasia.core.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitizenService {



    private final RequestRepository requestRepository;
    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentSlotRepository appointmentSlotRepository;
    private final UserRepository userRepository; // Added to fetch user directly if needed

    public CitizenService(RequestRepository requestRepository,
                          UserService userService,
                          FileStorageService fileStorageService,
                          AppointmentRepository appointmentRepository,
                          AppointmentSlotRepository appointmentSlotRepository,
                          UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.appointmentRepository = appointmentRepository;
        this.appointmentSlotRepository = appointmentSlotRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Request submitRequest(String title, String description, RequestType type, MultipartFile file, String username) {

        User citizen = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Request request = new Request();
        request.setTitle(title);
        request.setDescription(description);
        request.setRequestType(type);
        request.setStatus(ApplicationStatus.RECEIVED);
        request.setDate(LocalDateTime.now());

        if (file != null && !file.isEmpty()) {
            String fileName = fileStorageService.save(file);
        }

        return requestRepository.save(request);
    }

    public List<Request> getMyRequests(String username) {
        return requestRepository.findAll();
    }


    public List<AppointmentSlot> getAvailableSlots(DepartmentService service) {

        return appointmentSlotRepository.findByDepartmentServiceAndIsBookedFalse(service);
    }

    @Transactional
    public Appointment bookAppointment(Long slotId, Long requestId) {
        AppointmentSlot slot = appointmentSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.isBooked()) { // Ensure AppointmentSlot.java has isBooked() or getBooked()
            throw new RuntimeException("Slot already booked");
        }

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));


        Appointment appointment = new Appointment();

        LocalDateTime appointmentDateTime = LocalDateTime.of(LocalDate.now(), slot.getStartTime());

        appointment.setDate(appointmentDateTime);
        appointment.setRequest(request);
        appointment.setConfirmed(false);

        slot.setBooked(true);
        appointmentSlotRepository.save(slot);

        return appointmentRepository.save(appointment);
    }
}