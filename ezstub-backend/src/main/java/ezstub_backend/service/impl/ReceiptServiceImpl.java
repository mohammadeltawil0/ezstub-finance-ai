package ezstub_backend.service.impl;

//import ezstub_backend.client.OCRClient;
import ezstub_backend.exception.ResourceNotFoundException;
import ezstub_backend.model.Receipt;
import ezstub_backend.model.User;
import ezstub_backend.payload.modelDTOs.ReceiptDTO;
import ezstub_backend.repository.ReceiptRepository;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.service.ReceiptService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReceiptDTO uploadReceipt(ReceiptDTO dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        Receipt receipt = modelMapper.map(dto, Receipt.class);
        receipt.setUser(user);
        receipt.setProcessed(dto.getProcessed() != null ? dto.getProcessed() : true);
        Receipt saved = receiptRepository.save(receipt);
        ReceiptDTO response = modelMapper.map(receipt, ReceiptDTO.class);
        dto.setUserId(receipt.getUser().getUserId());
        return response;

    }

    @Override
    public List<ReceiptDTO> getReceiptByUser(Long userId) {
        return List.of();
    }

    @Override
    public ReceiptDTO getReceiptById(Long id) {
        return null;
    }

    @Override
    public ReceiptDTO updateReceipt(Long id, ReceiptDTO dto) {
        return null;
    }

    @Override
    public void deleteReceipt(Long id) {

    }

//    private final ReceiptRepository receiptRepository;
//    private final UserRepository userRepository;
////    private final OCRClient ocrClient;
//
//    public ReceiptServiceImpl(ReceiptRepository receiptRepository, UserRepository userRepository) {
//        this.receiptRepository = receiptRepository;
//        this.userRepository = userRepository;
////        this.ocrClient = ocrClient;
//    }
//
////    @Override
////    public ReceiptDTO uploadReceipt(Long userId, MultipartFile multipartFile) {
////
////        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
////
////        try {
////            String uploadDir = "uploads/";
////
////            File dir = new File(uploadDir);
////
////            if (!dir.exists()) {
////                dir.mkdirs();
////            }
////
////            String uniqueFileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
////
////            String filePath = uploadDir + uniqueFileName;
////            File savedFile = new File(filePath);
////
////            multipartFile.transferTo(savedFile);
////
////            // Call OCR service
////            OCRResponseDTO ocr = ocrClient.scanReceipt(savedFile);
////
////            Receipt receipt = Receipt.builder()
////                    .imageUrl(filePath)
////                    .merchant(ocr.getMerchant())
////                    .totalAmount(ocr.getAmount())
////                    .parsedJson(ocr.getRawText())
////                    .processed(true)
////                    .user(user)
////                    .build();
////
////            Transaction transaction = Transaction.builder()
////                    .merchant(ocr.getMerchant())
////                    .amount(ocr.getAmount())
////                    .transactionDate(LocalDate.now())
////                    .type(TransactionType.EXPENSE)
////                    .expenseCategory(ExpenseCategory.OTHER)
////                    .source(TransactionSource.RECEIPT)
////                    .description("Auto-generated from receipt upload")
////                    .user(user)
////                    .build();
////
////            transactionRepository.save(transaction);
////            Receipt saved = receiptRepository.save(receipt);
////            return ReceiptMapper.toDTO(saved);
////
////        } catch (IOException e) {
////            throw new RuntimeException("Failed to upload receipt!");
////        }
////    }

}
