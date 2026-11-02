package ezstub_backend.service.impl;

import ezstub_backend.client.PaystubClient;
import ezstub_backend.exception.ResourceNotFoundException;
import ezstub_backend.model.Deduction;
import ezstub_backend.model.Earning;
import ezstub_backend.payload.modelDTOs.PaystubDTO;
import ezstub_backend.payload.ocr.PaystubOCRResponseDTO;
import ezstub_backend.model.Paystub;
import ezstub_backend.model.User;
import ezstub_backend.repository.PayPeriodRepository;
import ezstub_backend.repository.PaystubRepository;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.service.PaystubService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
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
            paystub.setPayBeginDate(ocrResponseDTO.getPayBeginDate());
            paystub.setPayEndDate(ocrResponseDTO.getPayEndDate());
            paystub.setCheckDate(ocrResponseDTO.getCheckDate());
            paystub.setBaseHourlyRate(ocrResponseDTO.getBaseHourlyRate());
            paystub.setTotalHoursWorked(ocrResponseDTO.getTotalHoursWorked());
            paystub.setCurrentGross(ocrResponseDTO.getCurrentGross());
            paystub.setYtdGross(ocrResponseDTO.getYtdGross());
            paystub.setCurrentNet(ocrResponseDTO.getCurrentNet());
            paystub.setYtdNet(ocrResponseDTO.getYtdNet());
            paystub.setVerified(false);
            paystub.setUploadedAt(java.time.LocalDateTime.now());
            paystub.setUser(user);

            // Extract deductions
            if (ocrResponseDTO.getDeductions() != null) {
                List<Deduction> deductions = ocrResponseDTO.getDeductions().stream()
                        .map(deductionDTO -> {
                            Deduction d = modelMapper.map(deductionDTO, Deduction.class);
                            d.setPaystub(paystub);
                            return d;
                        }).collect(Collectors.toList());
                paystub.setDeductions(deductions);
            }

            if (ocrResponseDTO.getRateVariations() != null) {
                List<Earning> earnings = ocrResponseDTO.getDeductions().stream()
                        .map(earningDTO -> {
                            Earning e = modelMapper.map(earningDTO, Earning.class);
                            e.setPaystub(paystub);
                            return e;
                        }).collect(Collectors.toList());
                paystub.setEarnings(earnings);
            }


            // Bind to matching PayPeriod if it exists
            LocalDate beginDate = paystub.getPayBeginDate();
            payPeriodRepository.findByUser_UserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(userId, beginDate, beginDate)
                    .ifPresent(paystub::setPayPeriod);
            paystubRepository.save(paystub);

//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//            if (ocrResponseDTO.getPayBeginDate() != null) {
//                paystub.setPayBeginDate(ocrResponseDTO.getPayBeginDate());
//            }

            return ocrResponseDTO;

        }
        finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    @Override
    @Transactional()
    public List<PaystubDTO> getByUserId(Long userId) {
        return paystubRepository.findByUser_UserId(userId).stream()
                .map(paystub -> {
                    PaystubDTO dto = modelMapper.map(paystub, PaystubDTO.class);
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional
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