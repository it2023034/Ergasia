package gr.hua.dit.Ergasia.core.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
public class AppointmentSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    @Column(name = "is_booked")
    private boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentService departmentService;

    public AppointmentSlot() {}

    public AppointmentSlot(String dayOfWeek, LocalTime startTime, LocalTime endTime, DepartmentService departmentService) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.departmentService = departmentService;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public Long getId() {
        return id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
}