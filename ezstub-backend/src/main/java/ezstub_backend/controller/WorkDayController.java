package ezstub_backend.controller;

import ezstub_backend.payload.modelDTOs.WorkDayDTO;
import ezstub_backend.service.WorkDayService;
import ezstub_backend.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}