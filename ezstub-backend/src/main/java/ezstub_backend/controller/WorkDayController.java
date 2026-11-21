package ezstub_backend.controller;

import ezstub_backend.config.AppConstants;
import ezstub_backend.model.WorkDay;
import ezstub_backend.payload.apiResponses.PageResponse;
import ezstub_backend.payload.modelDTOs.WorkDayDTO;
import ezstub_backend.service.WorkDayService;
import ezstub_backend.util.AuthUtil;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-days")
public class WorkDayController {

    @Autowired
    private WorkDayService workDayService;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping
    public ResponseEntity<WorkDayDTO> createWorkDay(@Valid @RequestBody WorkDayDTO workDayDTO) {
        Long userId = authUtil.loggedInUserId();
        WorkDayDTO response = workDayService.createWorkDay(workDayDTO, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageResponse<WorkDayDTO>> getMyWorkDays(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_WORKDAYS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortOrder
    ) {
        Long userId = authUtil.loggedInUserId();
        PageResponse<WorkDayDTO> response = workDayService.getByUserId(userId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkDayDTO> getWorkDayById(@PathVariable Long id) {
        WorkDayDTO response = workDayService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkDayDTO> updateWorkDay(@PathVariable Long id, @Valid @RequestBody WorkDayDTO workDayDTO) {
        WorkDayDTO response = workDayService.updateWorkDay(id, workDayDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkDay(@PathVariable Long id) {
        workDayService.deleteWorkDay(id);
        return ResponseEntity.noContent().build();
    }


}