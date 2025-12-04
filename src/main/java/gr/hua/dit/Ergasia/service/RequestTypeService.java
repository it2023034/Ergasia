package gr.hua.dit.Ergasia.service;

import gr.hua.dit.adminbt.adminbt.model.Department;
import gr.hua.dit.adminbt.adminbt.model.RequestType;
import gr.hua.dit.adminbt.adminbt.repository.DepartmentRepository;
import gr.hua.dit.adminbt.adminbt.repository.RequestTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestTypeService {

    private final RequestTypeRepository requestTypeRepository;
    private final DepartmentRepository departmentRepository;

    public RequestTypeService(RequestTypeRepository requestTypeRepository,
                              DepartmentRepository departmentRepository) {
        this.requestTypeRepository = requestTypeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<RequestType> getAll() {
        return requestTypeRepository.findAll();
    }

    public RequestType create(String name) {
        RequestType type = new RequestType(name);
        return requestTypeRepository.save(type);
    }

    public RequestType setActive(Long id, boolean active) {
        RequestType type = requestTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request type not found"));
        type.setActive(active);
        return requestTypeRepository.save(type);
    }

    public RequestType assignDepartment(Long requestTypeId, Long departmentId) {
        RequestType rt = requestTypeRepository.findById(requestTypeId)
                .orElseThrow(() -> new RuntimeException("Request type not found"));

        Department dept = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        rt.setDepartment(dept);
        return requestTypeRepository.save(rt);
    }
}