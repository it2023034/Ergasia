package gr.hua.dit.Ergasia.core.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
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
    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "service_id",  nullable = false)
    private DepartmentService departmentService;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User citizen;

    @ManyToOne
    @JoinColumn(name = "request_type_id")
    private RequestType requestType;

    @Column(length = 1000)
    private String description;

    private LocalDateTime date;

    private String fileName;

    public Request(String title, DepartmentService departmentService, List<String> comment) {
        this.title = title;
        this.departmentService = departmentService;
        this.status = ApplicationStatus.SUBMITTED;
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

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
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

    public String getId() {
        return id.toString();
    }

    public void setRequestType(RequestType type) { this.requestType = type; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public void setCitizen(User citizen) { this.citizen = citizen; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public User getCitizen() { return citizen; }
    public RequestType getRequestType() { return requestType; }
    public String getDescription() { return description; }
    public LocalDateTime getDate() { return date; }
    public String getFileName() { return fileName; }
}