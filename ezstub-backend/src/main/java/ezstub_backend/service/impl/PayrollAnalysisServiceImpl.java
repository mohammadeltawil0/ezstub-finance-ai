package ezstub_backend.service.impl;

import ezstub_backend.exception.ResourceNotFoundException;
import ezstub_backend.model.PayPeriod;
import ezstub_backend.model.Paystub;
import ezstub_backend.model.WorkDay;
import ezstub_backend.payload.PayPeriodDTO;
import ezstub_backend.payload.PayrollAnalysisDTO;
import ezstub_backend.repository.PayPeriodRepository;
import ezstub_backend.repository.PaystubRepository;
import ezstub_backend.repository.WorkDayRepository;
import ezstub_backend.service.PayrollAnalysisService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PayrollAnalysisServiceImpl implements PayrollAnalysisService {

    @Autowired
    private WorkDayRepository workDayRepository;
    @Autowired
    private PayPeriodRepository payPeriodRepository;
    @Autowired
    private PaystubRepository paystubRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PayrollAnalysisDTO analyzePayroll(Long payPeriodId) {
        PayPeriod payPeriod = payPeriodRepository.findById(payPeriodId)
                .orElseThrow(() -> new ResourceNotFoundException("PayPeriod", "payPeriodId", payPeriodId));
        BigDecimal expectedGross = payPeriod.getWorkDays()
                .stream()
                .map(WorkDay::getExpectedGrossPay)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Paystub paystub = payPeriod.getPaystub();

        if (paystub == null) {
            return PayrollAnalysisDTO.builder()
                    .expectedGross(expectedGross)
                    .actualGross(BigDecimal.ZERO)
                    .difference(expectedGross)
                    .underpaid(true)
                    .issues(List.of("No paystub is associated with this payperiod"))
                    .build();
        }
        BigDecimal actualGross = paystub.getCurrentGross() == null ? BigDecimal.ZERO : paystub.getCurrentGross();

        BigDecimal difference = expectedGross.subtract(actualGross);

        List<String> issues = new ArrayList<>();

        if (difference.compareTo(BigDecimal.ZERO) > 0) {
            issues.add("Possible underpayment detected!");
        }

        if (difference.compareTo(BigDecimal.ZERO) < 0) {
            issues.add("Paystub gross exceeds scheduled expected gross!");
        }

        return PayrollAnalysisDTO.builder()
                .expectedGross(expectedGross)
                .actualGross(actualGross)
                .difference(difference)
                .underpaid(difference.compareTo(BigDecimal.ZERO) > 0)
                .issues(issues)
                .build();
    }
}
