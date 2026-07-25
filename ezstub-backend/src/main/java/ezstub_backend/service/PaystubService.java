package ezstub_backend.service;

import ezstub_backend.payload.PaystubDTO;
import ezstub_backend.payload.ocr.PaystubOCRResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PaystubService {


    PaystubOCRResponseDTO uploadPaystub(MultipartFile file, Long userId) throws Exception;

    List<PaystubDTO> getByUserId(Long userId);

    PaystubDTO getById(Long id);
//
//    PaystubDTO updatePaystub(Long id, PaystubDTO dto);
//
//    void deletePaystub(Long id);
}