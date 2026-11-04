package ezstub_backend.controller;

import ezstub_backend.model.User;
import ezstub_backend.payload.modelDTOs.PaystubDTO;
import ezstub_backend.payload.ocr.PaystubOCRResponseDTO;
import ezstub_backend.service.PaystubService;
import ezstub_backend.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/paystubs")
public class PaystubController {

    @Autowired
    private PaystubService paystubService;
    @Autowired
    private AuthUtil authUtil;

    @PostMapping(value = "/upload",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PaystubOCRResponseDTO> uploadPaystub(@RequestParam("file") MultipartFile file) throws Exception {
        User user = authUtil.loggedInUser();
        PaystubOCRResponseDTO response = paystubService.uploadPaystub(file, user.getUserId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PaystubDTO>> getMyPaystubs() {
        Long userId = authUtil.loggedInUserId();
        List<PaystubDTO> response = paystubService.getByUserId(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PaystubDTO> getPayStubById(@PathVariable Long id) {
        PaystubDTO response = paystubService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}