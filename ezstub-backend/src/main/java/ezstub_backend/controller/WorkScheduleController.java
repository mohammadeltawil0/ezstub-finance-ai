package ezstub_backend.controller;

import ezstub_backend.dto.WorkScheduleDTO;
import ezstub_backend.service.WorkScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-schedules")
public class WorkScheduleController {

    private final WorkScheduleService workScheduleService;

    public WorkScheduleController(WorkScheduleService workScheduleService) {
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