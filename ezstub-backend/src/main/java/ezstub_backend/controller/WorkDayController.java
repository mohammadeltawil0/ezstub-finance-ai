package ezstub_backend.controller;

import ezstub_backend.model.WorkDay;
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
    public ResponseEntity<List<WorkDayDTO>> getMyWorkDays() {
        Long userId = authUtil.loggedInUserId();
        List<WorkDayDTO> response = workDayService.getByUserId(userId);
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