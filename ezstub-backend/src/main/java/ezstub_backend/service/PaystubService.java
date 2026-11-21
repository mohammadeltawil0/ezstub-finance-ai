package ezstub_backend.service;

import ezstub_backend.payload.apiResponses.PageResponse;
import ezstub_backend.payload.modelDTOs.PaystubDTO;
import ezstub_backend.payload.ocr.PaystubOCRResponseDTO;
import org.springframework.web.multipart.MultipartFile;


public interface PaystubService {


    PaystubOCRResponseDTO uploadPaystub(MultipartFile file, Long userId) throws Exception;
    PageResponse<PaystubDTO> getByUserId(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    PaystubDTO getById(Long id);
    PaystubDTO updatePaystub(Long id, PaystubDTO dto);
    void deletePaystub(Long id);
}