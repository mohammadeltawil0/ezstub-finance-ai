package ezstub_backend.controller;

import ezstub_backend.payload.PayrollAnalysisDTO;
import ezstub_backend.service.PayrollAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payroll-analysis")
public class PayrollAnalysisController {

    @Autowired
    private PayrollAnalysisService payrollAnalysisService;

    @GetMapping("/{payPeriodId}")
    public ResponseEntity<PayrollAnalysisDTO> analyze(@PathVariable Long payPeriodId) {
        PayrollAnalysisDTO response = payrollAnalysisService.analyzePayroll(payPeriodId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
