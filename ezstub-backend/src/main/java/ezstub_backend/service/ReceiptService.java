package ezstub_backend.service;

import ezstub_backend.model.Receipt;
import ezstub_backend.payload.apiResponses.PageResponse;
import ezstub_backend.payload.modelDTOs.ReceiptDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReceiptService {

    ReceiptDTO uploadReceipt(ReceiptDTO dto,Long userId);

    PageResponse<ReceiptDTO> getReceiptByUser(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ReceiptDTO getReceiptById(Long id);

    ReceiptDTO updateReceipt(Long id, ReceiptDTO dto);

    void deleteReceipt(Long id);
}
