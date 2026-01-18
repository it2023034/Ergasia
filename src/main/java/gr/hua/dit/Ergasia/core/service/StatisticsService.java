package gr.hua.dit.Ergasia.core.service;

import gr.hua.dit.Ergasia.core.model.DepartmentService;
import gr.hua.dit.Ergasia.web.dto.StatisticsDTO;
import gr.hua.dit.Ergasia.core.repository.AppointmentSlotRepository;
import gr.hua.dit.Ergasia.core.repository.DepartmentServiceRepository;
import gr.hua.dit.Ergasia.core.repository.RequestTypeRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final RequestTypeRepository requestTypeRepository;
    private final DepartmentServiceRepository departmentRepository;
    private final AppointmentSlotRepository appointmentSlotRepository;

    public StatisticsService(RequestTypeRepository requestTypeRepository,
                             DepartmentServiceRepository departmentRepository,
                             AppointmentSlotRepository appointmentSlotRepository) {

        this.requestTypeRepository = requestTypeRepository;
        this.departmentRepository = departmentRepository;
        this.appointmentSlotRepository = appointmentSlotRepository;
    }

    public StatisticsDTO getStatistics() {

        long totalRequestTypes = requestTypeRepository.count();

        long activeRequestTypes = requestTypeRepository.findAll().stream()
                .filter(t -> t.isActive())
                .count();
        long inactiveRequestTypes = totalRequestTypes - activeRequestTypes;

        long totalDepartments = departmentRepository.count();
        long totalAppointmentSlots = appointmentSlotRepository.count();

        Map<Long, Long> countPerDept = requestTypeRepository.findAll().stream()
                .filter(t -> t.getDepartmentService() != null)
                .collect(Collectors.groupingBy(t -> t.getDepartmentService().getId(), Collectors.counting()));

        Map<String, Long> requestTypesPerDepartment = new HashMap<>();
        for (DepartmentService d : departmentRepository.findAll()) {
            long count = countPerDept.getOrDefault(d.getId(), 0L);
            requestTypesPerDepartment.put(d.getServiceName(), count);
        }

        return new StatisticsDTO(
                totalRequestTypes,
                activeRequestTypes,
                inactiveRequestTypes,
                totalDepartments,
                totalAppointmentSlots,
                requestTypesPerDepartment
        );
    }
}
