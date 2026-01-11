package gr.hua.dit.Ergasia.web.controller;

import gr.hua.dit.Ergasia.web.dto.ApplicationRequest;
import gr.hua.dit.Ergasia.core.model.Application;
import gr.hua.dit.Ergasia.core.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // Endpoint 1: Λίστα Διαθέσιμων Υπηρεσιών
    @GetMapping("/types")
    public ResponseEntity<Map<String, String>> getServiceTypes() {
        return ResponseEntity.ok(applicationService.getAvailableServices());
    }

    // Endpoint 2: Υποβολή Αίτησης (Με Αρχείο)
    // ΣΗΜΕΙΩΣΗ: Στο Postman/React πρέπει να στείλεις 'Content-Type: multipart/form-data'
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> submitApplication(
            @Valid @ModelAttribute ApplicationRequest request, // <--- Εδώ
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        try {
            Application savedApp = applicationService.submitApplication(request, file);
            return ResponseEntity.ok("Η αίτηση με ID " + savedApp.getId() + " υποβλήθηκε επιτυχώς.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Σφάλμα κατά το ανέβασμα του αρχείου.");
        }
    }


    // Endpoint 3: Παρακολούθηση (Οι αιτήσεις μου)
    @GetMapping("/my")
    public ResponseEntity<List<Application>> getMyApplications() {
        return ResponseEntity.ok(applicationService.getMyApplications());
    }
}
