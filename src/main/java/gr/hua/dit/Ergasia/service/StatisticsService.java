package gr.hua.dit.Ergasia.service;

import gr.hua.dit.Ergasia.model.Department;
import gr.hua.dit.Ergasia.model.StatisticsDTO;
import gr.hua.dit.Ergasia.repository.AppointmentSlotRepository;
import gr.hua.dit.Ergasia.repository.DepartmentRepository;
import gr.hua.dit.Ergasia.repository.RequestTypeRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsService {

    private final RequestTypeRepository requestTypeRepository;
    private final DepartmentRepository departmentRepository;
    private final AppointmentSlotRepository appointmentSlotRepository;

    public StatisticsService(RequestTypeRepository requestTypeRepository,
                             DepartmentRepository departmentRepository,
                             AppointmentSlotRepository appointmentSlotRepository) {

        this.requestTypeRepository = requestTypeRepository;
        this.departmentRepository = departmentRepository;
        this.appointmentSlotRepository = appointmentSlotRepository;
    }

    public StatisticsDTO getStatistics() {

        long totalRequestTypes = requestTypeRepository.count();
        long activeRequestTypes = requestTypeRepository.findAll().stream().filter(t -> t.isActive()).count();
        long inactiveRequestTypes = totalRequestTypes - activeRequestTypes;

        long totalDepartments = departmentRepository.count();
        long totalAppointmentSlots = appointmentSlotRepository.count();

        // Count request types per department
        Map<String, Long> requestTypesPerDepartment = new HashMap<>();
        for (Department d : departmentRepository.findAll()) {
            long count = d.getId() == null ? 0 :
                    requestTypeRepository.findAll().stream()
                            .filter(t -> t.getDepartment() != null && t.getDepartment().getId().equals(d.getId()))
                            .count();

            requestTypesPerDepartment.put(d.getName(), count);
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
