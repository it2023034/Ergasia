package gr.hua.dit.Ergasia.web.ui;

import gr.hua.dit.Ergasia.web.dto.ApplicationRequest;
import gr.hua.dit.Ergasia.core.model.Application;
import gr.hua.dit.Ergasia.core.model.DepartmentService;
import gr.hua.dit.Ergasia.core.service.ApplicationService;
import gr.hua.dit.Ergasia.core.repository.DepartmentServiceRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationUIController {

    private final ApplicationService applicationService;
    private final DepartmentServiceRepository departmentServiceRepository;

    public ApplicationUIController(ApplicationService applicationService,
                                   DepartmentServiceRepository departmentServiceRepository) {
        this.applicationService = applicationService;
        this.departmentServiceRepository = departmentServiceRepository;
    }

    @GetMapping("/new")
    public String showNewApplicationForm(Model model) {
        model.addAttribute("applicationRequest", new ApplicationRequest());
        model.addAttribute("types", applicationService.getAvailableServices());

        List<DepartmentService> services = departmentServiceRepository.findAll();
        model.addAttribute("services", services);

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
            model.addAttribute("error", "Error uploading the file.");
            return "new";
        }
    }

    @GetMapping("/list")
    public String listApplications(Model model) {
        model.addAttribute("applications", applicationService.getMyApplications());
        return "list";
    }

    @GetMapping("/{id}")
    public String viewApplication(@PathVariable Long id, Model model) {
        Application app = applicationService.getMyApplications()
                .stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Application not found"));
        model.addAttribute("app", app);
        return "detail";
    }
}
