package ezstub_backend.service.impl;

import ezstub_backend.client.PaystubClient;
import ezstub_backend.exception.ResourceNotFoundException;
import ezstub_backend.payload.PaystubDTO;
import ezstub_backend.payload.ocr.PaystubOCRResponseDTO;
import ezstub_backend.model.Paystub;
import ezstub_backend.model.User;
import ezstub_backend.repository.PayPeriodRepository;
import ezstub_backend.repository.PaystubRepository;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.service.PaystubService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.Reader;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaystubServiceImpl implements PaystubService {

    @Autowired
    private PaystubClient paystubClient;
    @Autowired
    private PaystubRepository paystubRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PayPeriodRepository payPeriodRepository;

    @Override
    public PaystubOCRResponseDTO uploadPaystub(MultipartFile file, Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        File tempFile = File.createTempFile("paystub-", ".pdf");

        try {
            file.transferTo(tempFile);
            String response = paystubClient.uploadPaystub(tempFile);

            ObjectMapper objectMapper = new ObjectMapper();
            PaystubOCRResponseDTO ocrResponseDTO = objectMapper.readValue(response, PaystubOCRResponseDTO.class);

            Paystub paystub = new Paystub();
            paystub.setEmployerName(ocrResponseDTO.getEmployerName());
            paystub.setEmployerLocation(ocrResponseDTO.getEmployerLocation());
            paystub.setPayBeginDate(java.time.LocalDate.parse(ocrResponseDTO.getPayBeginDate()));
            paystub.setPayEndDate(java.time.LocalDate.parse(ocrResponseDTO.getPayEndDate()));
            paystub.setCheckDate(java.time.LocalDate.parse(ocrResponseDTO.getCheckDate()));
            paystub.setBaseHourlyRate(ocrResponseDTO.getBaseHourlyRate());
            paystub.setTotalHoursWorked(ocrResponseDTO.getTotalHoursWorked());
            paystub.setCurrentGross(ocrResponseDTO.getCurrentGross());
            paystub.setYtdGross(ocrResponseDTO.getYtdGross());
            paystub.setCurrentNet(ocrResponseDTO.getCurrentNet());
            paystub.setYtdNet(ocrResponseDTO.getYtdNet());
            paystub.setVerified(false);
            paystub.setUploadedAt(java.time.LocalDateTime.now());
            paystub.setUser(user);

            // Bind to matching PayPeriod if it exists
            LocalDate beginDate = paystub.getPayBeginDate();
            payPeriodRepository.findByUser_UserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(userId, beginDate, beginDate)
                    .ifPresent(paystub::setPayPeriod);
            paystubRepository.save(paystub);

            return ocrResponseDTO;

        }
        finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    @Override
    public List<PaystubDTO> getByUserId(Long userId) {
        return List.of();
    }

    @Override
    public PaystubDTO getById(Long id) {
        return null;
    }

    @Override
    public PaystubDTO updatePaystub(Long id, PaystubDTO dto) {
        return null;
    }

    @Override
    public void deletePaystub(Long id) {

    }
}