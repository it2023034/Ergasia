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

    /**
     * Επιστρέφει στατιστικά στοιχεία για το σύστημα.
     * Περιλαμβάνει συνολικό πλήθος τύπων αιτήσεων, ενεργών/ανενεργών,
     * συνολικό πλήθος τμημάτων, slots, και πλήθος αιτήσεων ανά τμήμα.
     */
    public StatisticsDTO getStatistics() {

        // 1️⃣ Συνολικό πλήθος request types
        long totalRequestTypes = requestTypeRepository.count();

        // 2️⃣ Συνολικό πλήθος ενεργών και ανενεργών request types
        long activeRequestTypes = requestTypeRepository.findAll().stream()
                .filter(t -> t.isActive())
                .count();
        long inactiveRequestTypes = totalRequestTypes - activeRequestTypes;

        // 3️⃣ Συνολικό πλήθος τμημάτων και appointment slots
        long totalDepartments = departmentRepository.count();
        long totalAppointmentSlots = appointmentSlotRepository.count();

        // 4️⃣ Βελτιωμένος υπολογισμός request types ανά τμήμα
        // Προηγούμενη έκδοση έκανε findAll() για κάθε τμήμα → βαρύ
        Map<Long, Long> countPerDept = requestTypeRepository.findAll().stream()
                .filter(t -> t.getDepartmentService() != null) // αγνοούμε null
                .collect(Collectors.groupingBy(t -> t.getDepartmentService().getId(), Collectors.counting()));

        // 5️⃣ Χτίζουμε τελικό Map με όνομα τμήματος -> πλήθος request types
        Map<String, Long> requestTypesPerDepartment = new HashMap<>();
        for (DepartmentService d : departmentRepository.findAll()) {
            long count = countPerDept.getOrDefault(d.getId(), 0L);
            requestTypesPerDepartment.put(d.getServiceName(), count);
        }

        // 6️⃣ Επιστρέφουμε DTO
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
