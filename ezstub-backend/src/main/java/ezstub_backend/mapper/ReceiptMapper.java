package ezstub_backend.mapper;

import ezstub_backend.dto.ReceiptDTO;
import ezstub_backend.model.Receipt;
import ezstub_backend.model.User;

public class ReceiptMapper {

    public static Receipt toEntity(ReceiptDTO dto, User user) {

        return Receipt.builder()
                .id(dto.getId())
                .imageUrl(dto.getImageUrl())
                .merchant(dto.getMerchant())
                .totalAmount(dto.getTotalAmount())
                .parsedJson(dto.getParsedJson())
                .processed(dto.getProcessed())
                .uploadedAt(dto.getUploadedAt())
                .user(user)
                .build();
    }

    public static ReceiptDTO toDTO(Receipt receipt) {

        return ReceiptDTO.builder()
                .id(receipt.getId())
                .imageUrl(receipt.getImageUrl())
                .merchant(receipt.getMerchant())
                .totalAmount(receipt.getTotalAmount())
                .parsedJson(receipt.getParsedJson())
                .processed(receipt.getProcessed())
                .uploadedAt(receipt.getUploadedAt())
                .userId(receipt.getUser().getId())
                .build();
    }

}
