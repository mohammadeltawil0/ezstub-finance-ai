package ezstub_backend.controller;


import ezstub_backend.payload.PayPeriodDTO;
import ezstub_backend.service.PayPeriodService;
import ezstub_backend.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
