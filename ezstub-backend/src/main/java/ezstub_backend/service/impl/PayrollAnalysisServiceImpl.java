package ezstub_backend.service.impl;

import ezstub_backend.dto.PayrollAnalysisDTO;
import ezstub_backend.model.Paystub;
import ezstub_backend.model.WorkSchedule;
import ezstub_backend.repository.PaystubRepository;
import ezstub_backend.repository.WorkScheduleRepository;
import ezstub_backend.service.PayrollAnalysisService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayrollAnalysisServiceImpl implements PayrollAnalysisService {

    private final WorkScheduleRepository workScheduleRepository;
    private final PaystubRepository paystubRepository;

    public PayrollAnalysisServiceImpl(WorkScheduleRepository workScheduleRepository, PaystubRepository paystubRepository) {
        this.workScheduleRepository = workScheduleRepository;
        this.paystubRepository = paystubRepository;
    }

//    @Override
//    public PayrollAnalysisDTO analyzePayroll(Long workScheduleId, Long paystubId) {
//
//        // Get work schedule
//        WorkSchedule schedule = workScheduleRepository.findById(workScheduleId).orElseThrow(() -> new RuntimeException("Schedule not found!"));
//        Paystub paystub = paystubRepository.findById(paystubId).orElseThrow(() -> new RuntimeException("Paystub not found!"));
//
//        BigDecimal expectedGross =
//                schedule.getExpectedGrossPay();
//
//        BigDecimal actualGross =
//                paystub.getGrossPay();
//
//        BigDecimal difference =
//                expectedGross.subtract(actualGross);
//
//        List<String> issues = new ArrayList<>();
//
//        boolean underpaid =
//                difference.compareTo(BigDecimal.ZERO) > 0;
//
//        if (underpaid) {
//            issues.add("Possible underpayment detected");
//        }
//
//        return PayrollAnalysisDTO.builder()
//                .expectedGross(expectedGross)
//                .actualGross(actualGross)
//                .difference(difference)
//                .underpaid(underpaid)
//                .issues(issues)
//                .build();
//
//    }
}
