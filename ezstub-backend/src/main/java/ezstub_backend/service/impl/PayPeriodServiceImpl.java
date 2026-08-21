package ezstub_backend.service.impl;

import ezstub_backend.exception.ResourceNotFoundException;
import ezstub_backend.model.PayPeriod;
import ezstub_backend.model.User;
import ezstub_backend.payload.PayPeriodDTO;
import ezstub_backend.repository.PayPeriodRepository;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.service.PayPeriodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayPeriodServiceImpl implements PayPeriodService {

    @Autowired
    private PayPeriodRepository payPeriodRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PayPeriodDTO createPayPeriod(PayPeriodDTO dto, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        PayPeriod payPeriod = modelMapper.map(dto, PayPeriod.class);

        payPeriod.setUser(user);
        PayPeriod saved = payPeriodRepository.save(payPeriod);
        PayPeriodDTO response = modelMapper.map(saved, PayPeriodDTO.class);

        response.setUserId(userId);

        return response;
    }

    @Override
    public List<PayPeriodDTO> getByUserId(Long userId) {
        return payPeriodRepository
                .findByUser_UserId(userId)
                .stream()
                .map(payPeriod -> {
                    PayPeriodDTO dto = modelMapper.map(payPeriod, PayPeriodDTO.class);
                    dto.setUserId(userId);
                    return dto;
                }).toList();
    }

    @Override
    public PayPeriodDTO getById(Long id) {
        PayPeriod payPeriod = payPeriodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PayPeriod", "payPeriodId", id));
        PayPeriodDTO dto = modelMapper.map(payPeriod, PayPeriodDTO.class);
        if (payPeriod.getUser() != null) {
            dto.setUserId(payPeriod.getUser().getUserId());
        }
        return dto;
    }

    @Override
    public PayPeriodDTO updatePayPeriod(Long id, PayPeriodDTO dto) {
        return null;
    }

    @Override
    public void deletePayPeriod(Long id) {

    }
}
