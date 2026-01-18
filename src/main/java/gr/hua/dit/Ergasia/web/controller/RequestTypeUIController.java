package gr.hua.dit.Ergasia.web.controller;

import gr.hua.dit.Ergasia.core.service.RequestTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/request-type")
public class RequestTypeUIController {

    private final RequestTypeService requestTypeService;

    public RequestTypeUIController(RequestTypeService requestTypeService) {
        this.requestTypeService = requestTypeService;
    }

    @GetMapping
    public String listRequestTypes(Model model) {
        model.addAttribute("requestTypes", requestTypeService.getAll());
        return "request-types";
    }

    @PostMapping
    public String addRequestType(@RequestParam String name) {
        requestTypeService.create(name);
        return "redirect:/request-type";
    }

    @PostMapping("/{id}/active")
    public String toggleActive(
            @PathVariable Long id,
            @RequestParam boolean active
    ) {
        requestTypeService.setActive(id, active);
        return "redirect:/request-type";
    }
}

