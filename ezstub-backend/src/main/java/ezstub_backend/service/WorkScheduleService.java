package ezstub_backend.service;

import ezstub_backend.dto.WorkScheduleDTO;

import java.util.List;

public interface WorkScheduleService {

    WorkScheduleDTO createWorkSchedule(WorkScheduleDTO dto);

    List<WorkScheduleDTO> getByUserId(Long userId);

    WorkScheduleDTO getById(Long id);

    WorkScheduleDTO updateWorkSchedule(Long id, WorkScheduleDTO dto);

    void deleteWorkSchedule(Long id);
}