//package gr.hua.dit.Ergasia.model;
//
//import jakarta.persistence.*;
//import java.time.LocalTime;
//
//@Entity
//public class AppointmentSlot {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String dayOfWeek;   // e.g. "MONDAY"
//    private LocalTime startTime;
//    private LocalTime endTime;
//
//    @ManyToOne
//    @JoinColumn(name = "department_id")
//    private Department department;
//
//    public AppointmentSlot() {}
//
//    public AppointmentSlot(String dayOfWeek, LocalTime startTime, LocalTime endTime, Department department) {
//        this.dayOfWeek = dayOfWeek;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.department = department;
//    }
//
//    // Getters & setters
//    public Long getId() {
//        return id;
//    }
//
//    public String getDayOfWeek() {
//        return dayOfWeek;
//    }
//
//    public void setDayOfWeek(String dayOfWeek) {
//        this.dayOfWeek = dayOfWeek;
//    }
//
//    public LocalTime getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(LocalTime startTime) {
//        this.startTime = startTime;
//    }
//
//    public LocalTime getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(LocalTime endTime) {
//        this.endTime = endTime;
//    }
//
//    public Department getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(Department department) {
//        this.department = department;
//    }
//}
