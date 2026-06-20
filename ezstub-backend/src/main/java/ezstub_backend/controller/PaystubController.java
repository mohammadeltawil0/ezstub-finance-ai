package ezstub_backend.controller;

import ezstub_backend.dto.PaystubDTO;
import ezstub_backend.dto.ocr.PaystubOCRResponseDTO;
import ezstub_backend.service.PaystubService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/paystubs")
public class PaystubController {

    private final PaystubService paystubService;

    public PaystubController(PaystubService paystubService) {
        this.paystubService = paystubService;
    }


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PaystubOCRResponseDTO uploadPayStub(@RequestParam("file") MultipartFile file, @RequestParam Long userId) throws Exception {
        return paystubService.uploadPaystub(file, userId);
    }

//    @PostMapping
//    public PaystubDTO createPaystub(
//            @RequestBody PaystubDTO dto
//    ) {
//        return paystubService.createPaystub(dto);
//    }
//
    @GetMapping("/user/{userId}")
    public List<PaystubDTO> getByUserId(
            @PathVariable Long userId
    ) {
        return paystubService.getByUserId(userId);
    }

    @GetMapping("/{id}")
    public PaystubDTO getById(
            @PathVariable Long id
    ) {
        return paystubService.getById(id);
    }
//
//    @PutMapping("/{id}")
//    public PaystubDTO updatePaystub(
//            @PathVariable Long id,
//            @RequestBody PaystubDTO dto
//    ) {
//        return paystubService.updatePaystub(id, dto);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deletePaystub(
//            @PathVariable Long id
//    ) {
//        paystubService.deletePaystub(id);
//    }
}