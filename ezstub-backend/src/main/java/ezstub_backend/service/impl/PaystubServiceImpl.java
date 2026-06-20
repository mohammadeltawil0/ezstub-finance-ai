package ezstub_backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import ezstub_backend.client.PaystubClient;
import ezstub_backend.dto.PaystubDTO;
import ezstub_backend.dto.ocr.PaystubOCRResponseDTO;
import ezstub_backend.mapper.PaystubMapper;
import ezstub_backend.model.Paystub;
import ezstub_backend.model.User;
import ezstub_backend.repository.PaystubRepository;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.service.PaystubService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaystubServiceImpl implements PaystubService {

    private final PaystubClient paystubClient;
    private final PaystubRepository paystubRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public PaystubServiceImpl(
            PaystubClient paystubClient, PaystubRepository paystubRepository,
            UserRepository userRepository, ObjectMapper objectMapper
    ) {
        this.paystubClient = paystubClient;
        this.paystubRepository = paystubRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public PaystubOCRResponseDTO uploadPaystub(MultipartFile file, Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));

        File tempFile = File.createTempFile("paystub", ".pdf");

        file.transferTo(tempFile);

        String response = paystubClient.uploadPaystub(tempFile);

        PaystubOCRResponseDTO dto =
                objectMapper.readValue(
                        response,
                        PaystubOCRResponseDTO.class
                );

        Paystub paystub =
                PaystubMapper.fromOCRResponse(
                        dto,
                        user,
                        response
                );

        paystubRepository.save(paystub);

        tempFile.delete();

        return dto;
    }







//    @Override
//    public PaystubDTO createPaystub(PaystubDTO dto) {
//
//        User user = userRepository.findById(dto.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Paystub paystub =
//                PaystubMapper.toEntity(dto, user);
//
//        Paystub saved = paystubRepository.save(paystub);
//
//        return PaystubMapper.toDTO(saved);
//    }
//
    @Override
    public List<PaystubDTO> getByUserId(Long userId) {

        return paystubRepository.findByUserId(userId)
                .stream()
                .map(PaystubMapper::toDTO)
                .toList();
    }

    @Override
    public PaystubDTO getById(Long id) {

        Paystub paystub = paystubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paystub not found"));

        return PaystubMapper.toDTO(paystub);
    }
//
//    @Override
//    public PaystubDTO updatePaystub(Long id, PaystubDTO dto) {
//
////        Paystub paystub = paystubRepository.findById(id)
////                .orElseThrow(() -> new RuntimeException("Paystub not found"));
////
////        paystub.setEmployer(dto.getEmployer());
////        paystub.setGrossPay(dto.getGrossPay());
////        paystub.setTaxes(dto.getTaxes());
////        paystub.setNetPay(dto.getNetPay());
////        paystub.setPayPeriodStart(dto.getPayPeriodStart());
////        paystub.setPayPeriodEnd(dto.getPayPeriodEnd());
////        paystub.setHoursWorked(dto.getHoursWorked());
////        paystub.setHourlyRate(dto.getHourlyRate());
////
////        Paystub updated = paystubRepository.save(paystub);
////
////        return PaystubMapper.toDTO(updated);
//        return null;
//    }
//
//    @Override
//    public void deletePaystub(Long id) {
//
//        paystubRepository.deleteById(id);
//    }
}