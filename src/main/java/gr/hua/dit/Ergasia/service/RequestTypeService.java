package gr.hua.dit.Ergasia.service;

import gr.hua.dit.Ergasia.model.DepartmentService;
import gr.hua.dit.Ergasia.model.RequestType;
import gr.hua.dit.Ergasia.repository.DepartmentServiceRepository;
import gr.hua.dit.Ergasia.repository.RequestTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestTypeService {

    private final RequestTypeRepository requestTypeRepository;
    private final DepartmentServiceRepository departmentRepository;

    public RequestTypeService(RequestTypeRepository requestTypeRepository,
                              DepartmentServiceRepository departmentRepository) {
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

        DepartmentService dept = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        rt.setDepartment(dept);
        return requestTypeRepository.save(rt);
    }
}