package gr.hua.dit.Ergasia.core.model;

import jakarta.persistence.*;

@Entity
public class RequestType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private boolean active = true;

    @ManyToOne
    private DepartmentService departmentService;

    public RequestType() {}

    public RequestType(String name) {
        this.name = name;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    public void setDepartment(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
}