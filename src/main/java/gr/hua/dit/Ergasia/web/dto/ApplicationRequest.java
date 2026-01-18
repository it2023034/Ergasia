package gr.hua.dit.Ergasia.web.dto;

import gr.hua.dit.Ergasia.core.model.ApplicationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class ApplicationRequest {

    @NotNull
    private ApplicationType type;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotNull()
    private LocalDateTime appointmentDate;

    @NotNull
    private Long serviceId;

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }


    public ApplicationType getType() { return type; }
    public void setType(ApplicationType type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }
}
