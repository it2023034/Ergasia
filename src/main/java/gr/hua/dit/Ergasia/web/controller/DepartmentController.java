package gr.hua.dit.Ergasia.web.controller;

import gr.hua.dit.Ergasia.core.model.DepartmentService;
import gr.hua.dit.Ergasia.core.service.DepartmentServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentServices departmentService;

    public DepartmentController(DepartmentServices departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<DepartmentService> getAll() {
        return departmentService.getAll();
    }

    @PostMapping
    public DepartmentService create(@RequestParam String name) {
        return departmentService.create(name);
    }

    @PutMapping("/{id}/active")
    public DepartmentService setActive(
            @PathVariable Long id,
            @RequestParam boolean active
    ) {
        return departmentService.setActive(id, active);
    }
}
