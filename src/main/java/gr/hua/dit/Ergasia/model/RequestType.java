package gr.hua.dit.adminbt.adminbt.model;

import jakarta.persistence.*;

@Entity
public class RequestType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // Constructors
    public RequestType() {}

    public RequestType(String name) {
        this.name = name;
        this.active = true;
    }

    // Getters & setters
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

    // Department getters/setters
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}