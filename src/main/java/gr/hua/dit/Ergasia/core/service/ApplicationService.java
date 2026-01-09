package gr.hua.dit.Ergasia.core.service;

import gr.hua.dit.Ergasia.web.dto.ApplicationRequest;
import gr.hua.dit.Ergasia.core.model.Application;
import gr.hua.dit.Ergasia.core.model.ApplicationStatus;
import gr.hua.dit.Ergasia.core.model.User;
import gr.hua.dit.Ergasia.core.model.ApplicationType;
import gr.hua.dit.Ergasia.core.repository.ApplicationRepository;
import gr.hua.dit.Ergasia.core.repository.UserRepository;
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

    // Utility method για ασφαλή ανάκτηση του username
    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return principal.toString(); // Αν είναι απλό String
    }

    public Map<String, String> getAvailableServices() {
        return Arrays.stream(ApplicationType.values())
                .collect(Collectors.toMap(Enum::name, ApplicationType::getDescription));
    }

    public Application submitApplication(ApplicationRequest request, MultipartFile file) throws IOException {
        String username = getCurrentUsername();
        User currentUser = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Application app = new Application();
        app.setType(request.getType());
        app.setDescription(request.getDescription());
        app.setStatus(ApplicationStatus.SUBMITTED);
        app.setCreatedAt(LocalDateTime.now());
        app.setCitizen(currentUser);

        if (file != null && !file.isEmpty()) {
            app.setFileName(file.getOriginalFilename());
            app.setAttachedFile(file.getBytes());
        }

        return applicationRepository.save(app);
    }

    public List<Application> getMyApplications() {
        String username = getCurrentUsername();
        User currentUser = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return applicationRepository.findByCitizenId(currentUser.getId());
    }
}
