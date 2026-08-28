package ezstub_backend.controller;


import ezstub_backend.payload.PayPeriodDTO;
import ezstub_backend.service.PayPeriodService;
import ezstub_backend.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pay-periods")
public class PayPeriodController {

    @Autowired
    private PayPeriodService payPeriodService;
    @Autowired
    private AuthUtil authUtil;

    @PostMapping
    public ResponseEntity<PayPeriodDTO> create(@Valid @RequestBody PayPeriodDTO payPeriodDTO) {
        Long userId = authUtil.loggedInUserId();
        PayPeriodDTO response = payPeriodService.createPayPeriod(payPeriodDTO, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PayPeriodDTO>> getAllPayPeriods() {
        Long userId = authUtil.loggedInUserId();
        List<PayPeriodDTO> response = payPeriodService.getByUserId(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayPeriodDTO> getPayPeriodById(@PathVariable Long id) {
        PayPeriodDTO response = payPeriodService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayPeriodDTO> updatePayPeriod(@PathVariable Long id, @Valid @RequestBody PayPeriodDTO dto) {
        PayPeriodDTO response = payPeriodService.updatePayPeriod(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        payPeriodService.deletePayPeriod(id);
        return ResponseEntity.noContent().build();
    }
}
