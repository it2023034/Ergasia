package gr.hua.dit.Ergasia.controller;

import gr.hua.dit.adminbt.adminbt.model.AppointmentSlot;
import gr.hua.dit.adminbt.adminbt.service.AppointmentSlotService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentSlotController {

    private final AppointmentSlotService slotService;

    public AppointmentSlotController(AppointmentSlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping("/create")
    public AppointmentSlot createAppointmentSlot(
            @RequestParam Long departmentId,
            @RequestParam String dayOfWeek,
            @RequestParam String start,
            @RequestParam String end
    ) {
        return slotService.createSlot(
                departmentId,
                dayOfWeek.toUpperCase(),
                LocalTime.parse(start),
                LocalTime.parse(end)
        );
    }

    @GetMapping("/{departmentId}")
    public List<AppointmentSlot> getSlots(@PathVariable Long departmentId) {
        return slotService.getSlotsForDepartment(departmentId);
    }
}
