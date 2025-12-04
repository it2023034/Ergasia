//package gr.hua.dit.Ergasia.controller;
//
//import gr.hua.dit.Ergasia.model.Department;
//import gr.hua.dit.Ergasia.service.DepartmentService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/departments")
//public class DepartmentController {
//
//    private final DepartmentService departmentService;
//
//    public DepartmentController(DepartmentService departmentService) {
//        this.departmentService = departmentService;
//    }
//
//    @GetMapping
//    public List<Department> getAll() {
//        return departmentService.getAll();
//    }
//
//    @PostMapping
//    public Department create(@RequestParam String name) {
//        return departmentService.create(name);
//    }
//
//    @PutMapping("/{id}/active")
//    public Department setActive(
//            @PathVariable Long id,
//            @RequestParam boolean active
//    ) {
//        return departmentService.setActive(id, active);
//    }
//}
