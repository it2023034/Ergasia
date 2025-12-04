package gr.hua.dit.Ergasia.model;

import java.time.LocalTime;

public class AppointmentSlot {
    private Long id;

    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Department department;

    public AppointmentSlot() {}

    public AppointmentSlot(String dayOfWeek, LocalTime startTime, LocalTime endTime, Department department) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.department = department;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
