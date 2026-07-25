package ezstub_backend.controller;

import ezstub_backend.payload.WorkScheduleDTO;
import ezstub_backend.service.WorkDayService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-schedules")
public class WorkDayController {

    private final WorkDayService workScheduleService;

    public WorkDayController(WorkDayService workScheduleService) {
        this.workScheduleService = workScheduleService;
    }

    @PostMapping
    public WorkScheduleDTO createWorkSchedule(
            @RequestBody WorkScheduleDTO dto
    ) {
        return workScheduleService.createWorkSchedule(dto);
    }

    @GetMapping("/user/{userId}")
    public List<WorkScheduleDTO> getByUserId(
            @PathVariable Long userId
    ) {
        return workScheduleService.getByUserId(userId);
    }

    @GetMapping("/{id}")
    public WorkScheduleDTO getById(
            @PathVariable Long id
    ) {
        return workScheduleService.getById(id);
    }

    @PutMapping("/{id}")
    public WorkScheduleDTO updateWorkSchedule(
            @PathVariable Long id,
            @RequestBody WorkScheduleDTO dto
    ) {
        return workScheduleService.updateWorkSchedule(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkSchedule(
            @PathVariable Long id
    ) {
        workScheduleService.deleteWorkSchedule(id);
    }
}