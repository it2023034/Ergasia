package gr.hua.dit.Ergasia.web.controller;

import gr.hua.dit.Ergasia.core.model.AppointmentSlot;
import gr.hua.dit.Ergasia.core.model.Request;
import gr.hua.dit.Ergasia.core.model.RequestType;
import gr.hua.dit.Ergasia.core.service.CitizenService;
import gr.hua.dit.Ergasia.core.service.RequestTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/citizen")
public class CitizenController {

    private final CitizenService citizenService;
    private final RequestTypeService requestTypeService;

    public CitizenController(CitizenService citizenService, RequestTypeService requestTypeService) {
        this.citizenService = citizenService;
        this.requestTypeService = requestTypeService;
    }

    // 1. Submit Request with File
    @PostMapping(value = "/requests", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createRequest(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("typeId") Long typeId,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Authentication authentication) {

        // Find Type
        RequestType type = requestTypeService.findById(typeId);

        Request created = citizenService.submitRequest(title, description, type, file, authentication.getName());
        return ResponseEntity.ok("Request submitted with ID: " + created.getId());
    }

    // 2. View My Requests
    @GetMapping("/requests")
    public ResponseEntity<List<Request>> getMyRequests(Authentication authentication) {
        return ResponseEntity.ok(citizenService.getMyRequests(authentication.getName()));
    }

    // 3. View Available Appointments for a Service
    @GetMapping("/appointments/slots/{serviceId}")
    public ResponseEntity<List<AppointmentSlot>> getSlots(@PathVariable Long serviceId) {
        // You would need to fetch the DepartmentService entity first
        // keeping it simple for example:
        // return ResponseEntity.ok(citizenService.getAvailableSlots(service));
        return ResponseEntity.ok(List.of());
    }

    // 4. Book Appointment
    @PostMapping("/appointments/book")
    public ResponseEntity<?> bookAppointment(@RequestParam Long slotId, @RequestParam Long requestId) {
        citizenService.bookAppointment(slotId, requestId);
        return ResponseEntity.ok("Appointment booked successfully pending confirmation.");
    }
}