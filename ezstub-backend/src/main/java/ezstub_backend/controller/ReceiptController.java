package ezstub_backend.controller;

import ezstub_backend.payload.modelDTOs.ReceiptDTO;
import ezstub_backend.service.ReceiptService;
import ezstub_backend.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private AuthUtil authUtil;





    @PostMapping()
    public ResponseEntity<ReceiptDTO> uploadReceipt(@Valid @RequestBody ReceiptDTO receiptDTO) {
        Long userId = authUtil.loggedInUserId();
        ReceiptDTO response = receiptService.uploadReceipt(receiptDTO, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<ReceiptDTO>> getReceipts() {
        Long userId = authUtil.loggedInUserId();
        List<ReceiptDTO> response = receiptService.getReceiptByUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ReceiptDTO getReceipt(@PathVariable Long id) {
        return receiptService.getReceiptById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteReceipt(@PathVariable Long id) {
        receiptService.deleteReceipt(id);
    }
}
