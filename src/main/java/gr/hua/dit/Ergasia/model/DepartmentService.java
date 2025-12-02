package gr.hua.dit.Ergasia.model;

import jakarta.persistence.*;

@Entity
@Table(name = "service")
public class DepartmentService {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "service_name", nullable = false, length = 30)
    private String serviceName;

    public DepartmentService() {}

    public DepartmentService(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
