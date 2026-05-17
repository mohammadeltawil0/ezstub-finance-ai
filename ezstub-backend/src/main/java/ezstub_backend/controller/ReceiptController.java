package ezstub_backend.controller;

import ezstub_backend.dto.ReceiptDTO;
import ezstub_backend.service.ReceiptService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/upload/{userId}")
    public ReceiptDTO uploadReceipt(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        return receiptService.uploadReceipt(userId, file);
    }

    @GetMapping("/user/{userId}")
    public List<ReceiptDTO> getReceipts(@PathVariable Long userId) {
        return receiptService.getReceiptByUser(userId);
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
