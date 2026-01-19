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

    @GetMapping("/types")
    public ResponseEntity<Map<String, String>> getServiceTypes() {
        return ResponseEntity.ok(applicationService.getAvailableServices());
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> submitApplication(
            @Valid @ModelAttribute ApplicationRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        try {
            Application savedApp = applicationService.submitApplication(request, file);
            return ResponseEntity.ok("The application with ID " + savedApp.getId() + " was submitted successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error while uploading the file.");
        }
    }


    @GetMapping("/my")
    public ResponseEntity<List<Application>> getMyApplications() {
        return ResponseEntity.ok(applicationService.getMyApplications());
    }
}
