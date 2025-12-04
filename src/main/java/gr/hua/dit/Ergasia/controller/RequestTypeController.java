package gr.hua.dit.Ergasia.controller;

import gr.hua.dit.adminbt.adminbt.model.RequestType;
import gr.hua.dit.adminbt.adminbt.service.RequestTypeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/request-types")
public class RequestTypeController {

    private final RequestTypeService requestTypeService;

    public RequestTypeController(RequestTypeService requestTypeService) {
        this.requestTypeService = requestTypeService;
    }

    @GetMapping
    public List<RequestType> getAllRequestTypes() {
        return requestTypeService.getAll();
    }

    @PostMapping
    public RequestType createRequestType(@RequestParam String name) {
        return requestTypeService.create(name);
    }

    @PutMapping("/{id}/active")
    public RequestType setActive(
            @PathVariable Long id,
            @RequestParam boolean active
    ) {
        return requestTypeService.setActive(id, active);
    }

    @PutMapping("/{id}/assign-department")
    public RequestType assignDepartment(
            @PathVariable Long id,
            @RequestParam Long departmentId
    ) {
        return requestTypeService.assignDepartment(id, departmentId);
    }
}
