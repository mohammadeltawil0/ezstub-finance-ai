package ezstub_backend.service;

import ezstub_backend.payload.modelDTOs.WorkDayDTO;

import java.math.BigDecimal;
import java.util.List;

public interface WorkDayService {

    WorkDayDTO createWorkDay(WorkDayDTO dto, Long userId);

    List<WorkDayDTO> getByUserId(Long userId);

    WorkDayDTO getById(Long id);

    WorkDayDTO updateWorkDay(Long id, WorkDayDTO dto);

    void deleteWorkDay(Long id);

    BigDecimal calculateExpectedGross(WorkDayDTO dto);



}