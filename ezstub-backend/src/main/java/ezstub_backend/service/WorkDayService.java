package ezstub_backend.service;

import ezstub_backend.payload.WorkDayDTO;

import java.math.BigDecimal;
import java.util.List;

public interface WorkDayService {

    WorkDayDTO createWorkSchedule(WorkDayDTO dto);

    List<WorkDayDTO> getByUserId(Long userId);

    WorkDayDTO getById(Long id);

    WorkDayDTO updateWorkSchedule(Long id, WorkDayDTO dto);

    void deleteWorkSchedule(Long id);

    BigDecimal calculateExpectedGross(WorkDayDTO dto);



}