package gr.hua.dit.Ergasia.service;


import gr.hua.dit.adminbt.adminbt.model.Department;
import gr.hua.dit.adminbt.adminbt.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public Department create(String name) {
        Department d = new Department(name);
        return departmentRepository.save(d);
    }

    public Department setActive(Long id, boolean active) {
        Department d = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        d.setActive(active);
        return departmentRepository.save(d);
    }
}
