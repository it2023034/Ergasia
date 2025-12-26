package gr.hua.dit.Ergasia.core.service;


import gr.hua.dit.Ergasia.core.model.DepartmentService;
import gr.hua.dit.Ergasia.core.repository.DepartmentServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServices {

    private final DepartmentServiceRepository departmentRepository;

    public DepartmentServices(DepartmentServiceRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentService> getAll() {
        return departmentRepository.findAll();
    }

    public DepartmentService create(String name) {
        DepartmentService d = new DepartmentService(name);
        return departmentRepository.save(d);
    }

    public DepartmentService setActive(Long id, boolean active) {
        DepartmentService d = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        d.setActive(active);
        return departmentRepository.save(d);
    }
}
