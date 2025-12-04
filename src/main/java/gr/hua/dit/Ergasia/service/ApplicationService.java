package gr.hua.dit.Ergasia.service;

import gr.hua.dit.Ergasia.dto.ApplicationRequest;
import gr.hua.dit.Ergasia.model.Application;
import gr.hua.dit.Ergasia.model.User;
import gr.hua.dit.Ergasia.model.ApplicationStatus;
import gr.hua.dit.Ergasia.model.ApplicationType;
import gr.hua.dit.Ergasia.repository.ApplicationRepository;
import gr.hua.dit.Ergasia.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public ApplicationService(ApplicationRepository applicationRepository, UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    // Προβολή διαθέσιμων υπηρεσιών
    public Map<String, String> getAvailableServices() {
        return Arrays.stream(ApplicationType.values())
                .collect(Collectors.toMap(Enum::name, ApplicationType::getDescription));
    }

    // Υποβολή αιτήματος
    public Application submitApplication(ApplicationRequest request, MultipartFile file) throws IOException {
        // Βρίσκουμε ποιος χρήστης είναι συνδεδεμένος
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Application app = new Application();
        app.setType(request.getType());
        app.setDescription(request.getDescription());
        app.setStatus(ApplicationStatus.SUBMITTED);
        app.setCreatedAt(LocalDateTime.now());
        app.setCitizen(currentUser);

        // Διαχείριση Αρχείου
        if (file != null && !file.isEmpty()) {
            app.setFileName(file.getOriginalFilename());
            app.setAttachedFile(file.getBytes());
        }

        return applicationRepository.save(app);
    }

    // Ιστορικό αιτήσεων του πολίτη
    public List<Application> getMyApplications() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return applicationRepository.findByCitizenId(currentUser.getId());
    }
}
