package gr.hua.dit.Ergasia.web.ui;

import gr.hua.dit.Ergasia.web.dto.ApplicationRequest;
import gr.hua.dit.Ergasia.core.model.Application;
import gr.hua.dit.Ergasia.core.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/applications")
public class ApplicationUIController {

    private final ApplicationService applicationService;

    public ApplicationUIController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/new")
    public String showNewApplicationForm(Model model) {
        model.addAttribute("applicationRequest", new ApplicationRequest());
        model.addAttribute("types", applicationService.getAvailableServices());
        return "new";
    }

    @PostMapping("/new")
    public String submitApplication(@ModelAttribute @Valid ApplicationRequest applicationRequest,
                                    @RequestParam(value = "file", required = false) MultipartFile file,
                                    Model model) {
        try {
            Application saved = applicationService.submitApplication(applicationRequest, file);
            return "redirect:/applications/" + saved.getId();
        } catch (IOException e) {
            model.addAttribute("error", "Sfalma sto anebasma arxeiou.");
            return "new";
        }
    }

    @GetMapping("/list")
    public String listApplications(Model model) {
        model.addAttribute("applications", applicationService.getMyApplications());
        return "list"; // templates/applications/list.html
    }

    @GetMapping("/{id}")
    public String viewApplication(@PathVariable Long id, Model model) {
        Application app = applicationService.getMyApplications()
                .stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Application not found"));
        model.addAttribute("application", app);
        return "detail"; // templates/applications/detail.html
    }
}
