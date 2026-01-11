package gr.hua.dit.Ergasia.core.service;

import gr.hua.dit.Ergasia.core.model.*;
import gr.hua.dit.Ergasia.core.repository.AppointmentRepository;
import gr.hua.dit.Ergasia.core.repository.AppointmentSlotRepository;
import gr.hua.dit.Ergasia.core.repository.RequestRepository;
import gr.hua.dit.Ergasia.core.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final UserRepository userRepository;

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
    public Request submitRequest(String title, String description, RequestType type,
                                 MultipartFile file, String username) {

        // Βρίσκουμε τον χρήστη που υποβάλλει την αίτηση
        User citizen = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Request request = new Request();
        request.setTitle(title);
        request.setDescription(description);      // Σωστή αποθήκευση περιγραφής
        request.setRequestType(type);            // Σωστή αποθήκευση τύπου αιτήματος
        request.setStatus(ApplicationStatus.RECEIVED);
        request.setDate(LocalDateTime.now());    // Ημερομηνία υποβολής
        request.setCitizen(citizen);             // Συσχέτιση με χρήστη

        // Αν υπάρχει αρχείο, το αποθηκεύουμε και συνδέουμε το request με το filename
        if (file != null && !file.isEmpty()) {
            String fileName = fileStorageService.save(file);
            request.setFileName(fileName);
        }

        // Αποθήκευση στη βάση
        return requestRepository.save(request);
    }

    public List<Request> getMyRequests(String username) {

        // Βρίσκουμε τον χρήστη
        User citizen = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // Επιστρέφουμε μόνο τα requests που ανήκουν στον συγκεκριμένο χρήστη
        return requestRepository.findByCitizen(citizen);
    }

    public List<AppointmentSlot> getAvailableSlots(DepartmentService service) {
        return appointmentSlotRepository.findByDepartmentServiceAndIsBookedFalse(service);
    }

    @Transactional
    public Appointment bookAppointment(Long slotId, Long requestId) {
        AppointmentSlot slot = appointmentSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.isBooked()) {
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
