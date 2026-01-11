package gr.hua.dit.Ergasia.web.dto;

import java.util.Map;

public class StatisticsDTO {

    private long totalRequestTypes;
    private long activeRequestTypes;
    private long inactiveRequestTypes;

    private long totalDepartments;
    private long totalAppointmentSlots;

    // requestTypesPerDepartment: {"ΚΕΠ": 4, "Τεχνική Υπηρεσία": 2}
    private Map<String, Long> requestTypesPerDepartment;

    public StatisticsDTO(long totalRequestTypes,
                         long activeRequestTypes,
                         long inactiveRequestTypes,
                         long totalDepartments,
                         long totalAppointmentSlots,
                         Map<String, Long> requestTypesPerDepartment) {

        this.totalRequestTypes = totalRequestTypes;
        this.activeRequestTypes = activeRequestTypes;
        this.inactiveRequestTypes = inactiveRequestTypes;
        this.totalDepartments = totalDepartments;
        this.totalAppointmentSlots = totalAppointmentSlots;
        this.requestTypesPerDepartment = requestTypesPerDepartment;
    }

    public long getTotalRequestTypes() {
        return totalRequestTypes;
    }

    public long getActiveRequestTypes() {
        return activeRequestTypes;
    }

    public long getInactiveRequestTypes() {
        return inactiveRequestTypes;
    }

    public long getTotalDepartments() {
        return totalDepartments;
    }

    public long getTotalAppointmentSlots() {
        return totalAppointmentSlots;
    }

    public Map<String, Long> getRequestTypesPerDepartment() {
        return requestTypesPerDepartment;
    }
}