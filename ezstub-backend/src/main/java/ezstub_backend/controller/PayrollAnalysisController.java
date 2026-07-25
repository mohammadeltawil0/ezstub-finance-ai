package ezstub_backend.controller;

import ezstub_backend.service.PayrollAnalysisService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payroll-analysis")
public class PayrollAnalysisController {

    private final PayrollAnalysisService payrollAnalysisService;

    public PayrollAnalysisController(PayrollAnalysisService payrollAnalysisService) {
        this.payrollAnalysisService = payrollAnalysisService;
    }

//    @GetMapping
//    public PayrollAnalysisDTO analyze(
//            @RequestParam Long scheduleId,
//            @RequestParam Long paystubId
//    ) {
//
//        return payrollAnalysisService.analyzePayroll(
//                scheduleId,
//                paystubId
//        );
//    }
}
