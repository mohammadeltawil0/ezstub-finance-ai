package ezstub_backend.service.impl;

//import ezstub_backend.client.OCRClient;
import ezstub_backend.payload.ReceiptDTO;
import ezstub_backend.model.Receipt;
import ezstub_backend.repository.ReceiptRepository;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.service.ReceiptService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptServiceImpl implements ReceiptService {

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
//
//    @Override
//    public List<ReceiptDTO> getReceiptByUser(Long userId) {
//
//        return receiptRepository.findByUserId(userId)
//                .stream()
//                .map(ReceiptMapper::toDTO)
//                .toList();
//    }
//
//    @Override
//    public ReceiptDTO getReceiptById(Long id) {
//
//        Receipt receipt = receiptRepository.findById(id).orElseThrow(() -> new RuntimeException("Receipt not found!"));
//        return ReceiptMapper.toDTO(receipt);
//    }
//
//    @Override
//    public void deleteReceipt(Long id) {
//        receiptRepository.deleteById(id);
//    }
}
