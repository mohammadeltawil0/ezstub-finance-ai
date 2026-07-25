package ezstub_backend.service;

import ezstub_backend.payload.ReceiptDTO;

import java.util.List;

public interface ReceiptService {

//    ReceiptDTO uploadReceipt(Long userId, MultipartFile multipartFile);

    List<ReceiptDTO> getReceiptByUser(Long userId);

    ReceiptDTO getReceiptById(Long id);

    void deleteReceipt(Long id);
}
