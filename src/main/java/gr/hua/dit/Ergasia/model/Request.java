package gr.hua.dit.Ergasia.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @ManyToOne
    @JoinColumn(name = "service_id",  nullable = false)
    private DepartmentService departmentService;

    @ElementCollection
    private List<String> comment;

    public Request(String title, DepartmentService departmentService, List<String> comment) {
        this.title = title;
        this.departmentService = departmentService;
        this.status = RequestStatus.SUBMITTED;
        this.comment = comment;
    }

    public Request() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public DepartmentService getService() {
        return departmentService;
    }

    public void setService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public List<String> getComment() {
        return comment;
    }

    public void setComment(List<String> comment) {
        this.comment = comment;
    }

}

