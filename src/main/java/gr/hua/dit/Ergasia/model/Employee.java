package gr.hua.dit.Ergasia.model;

import jakarta.persistence.*;

/**
 * Employee entity.
 */
@Entity
@Table(name = "employee", uniqueConstraints = {}, indexes = {})
public class Employee extends User{

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private DepartmentService departmentService;

    public Employee(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public Employee() {}

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
}

