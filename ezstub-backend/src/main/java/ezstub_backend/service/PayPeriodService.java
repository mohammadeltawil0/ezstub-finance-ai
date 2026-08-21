package ezstub_backend.service;

import ezstub_backend.payload.PayPeriodDTO;

import java.util.List;

public interface PayPeriodService {

    PayPeriodDTO createPayPeriod(PayPeriodDTO dto, Long userId);
    List<PayPeriodDTO> getByUserId(Long userId);
    PayPeriodDTO getById(Long id);
    PayPeriodDTO updatePayPeriod(Long id, PayPeriodDTO dto);
    void deletePayPeriod(Long id);

}
