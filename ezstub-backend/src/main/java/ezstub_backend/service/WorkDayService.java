package ezstub_backend.service;

import ezstub_backend.payload.apiResponses.PageResponse;
import ezstub_backend.payload.modelDTOs.WorkDayDTO;

import java.math.BigDecimal;
import java.util.List;

public interface WorkDayService {

    WorkDayDTO createWorkDay(WorkDayDTO dto, Long userId);

    PageResponse<WorkDayDTO> getByUserId(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    WorkDayDTO getById(Long id);

    WorkDayDTO updateWorkDay(Long id, WorkDayDTO dto);

    void deleteWorkDay(Long id);

    BigDecimal calculateExpectedGross(WorkDayDTO dto);



}