package ezstub_backend.service;

import ezstub_backend.payload.PayrollAnalysisDTO;

public interface PayrollAnalysisService {

    PayrollAnalysisDTO analyzePayroll(Long payPeriodId);

}
