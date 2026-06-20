package ezstub_backend.mapper;

import ezstub_backend.dto.WorkScheduleDTO;
import ezstub_backend.model.User;
import ezstub_backend.model.WorkSchedule;

public class WorkScheduleMapper {

    public static WorkScheduleDTO toDTO(WorkSchedule workSchedule) {

        return WorkScheduleDTO.builder()
                .id(workSchedule.getId())
                .workDate(workSchedule.getWorkDate())
                .startTime(workSchedule.getStartTime())
                .endTime(workSchedule.getEndTime())
                .unpaidBreakMinutes(workSchedule.getUnpaidBreakMinutes())
                .hourlyRate(workSchedule.getHourlyRate())
                .expectedGrossPay(workSchedule.getExpectedGrossPay())
                .type(workSchedule.getType())
                .notes(workSchedule.getNotes())
                .employer(workSchedule.getEmployer())
                .userId(
                        workSchedule.getUser() != null
                                ? workSchedule.getUser().getId()
                                : null
                )
                .build();
    }

    public static WorkSchedule toEntity(
            WorkScheduleDTO dto,
            User user
    ) {

        return WorkSchedule.builder()
                .id(dto.getId())
                .workDate(dto.getWorkDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .unpaidBreakMinutes(dto.getUnpaidBreakMinutes())
                .hourlyRate(dto.getHourlyRate())
                .expectedGrossPay(dto.getExpectedGrossPay())
                .type(dto.getType())
                .notes(dto.getNotes())
                .employer(dto.getEmployer())
                .user(user)
                .build();
    }
}