package ezstub_backend.mapper;

import ezstub_backend.dto.*;
import ezstub_backend.dto.ocr.PaystubOCRResponseDTO;
import ezstub_backend.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.stream.Collectors;

public class PaystubMapper {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    // =========================
    // OCR -> ENTITY
    // =========================
    public static Paystub fromOCRResponse(
            PaystubOCRResponseDTO dto,
            User user,
            String rawText
    ) {

        Paystub paystub = Paystub.builder()
                .employerName(dto.getEmployerName())
                .employerLocation(dto.getEmployerLocation())
                .payBeginDate(LocalDate.parse(dto.getPayBeginDate(), FORMATTER))
                .payEndDate(LocalDate.parse(dto.getPayEndDate(), FORMATTER))
                .checkDate(LocalDate.parse(dto.getCheckDate(), FORMATTER))
                .baseHourlyRate(dto.getBaseHourlyRate())
                .totalHoursWorked(dto.getTotalHoursWorked())
                .currentGross(dto.getCurrentGross())
                .ytdGross(dto.getYtdGross())
                .currentNet(dto.getCurrentNet())
                .ytdNet(dto.getYtdNet())
                .verified(true)
//                .rawOCRText(rawText)
                .uploadedAt(LocalDateTime.now())
                .user(user)
                .build();

        // Deductions
        if (dto.getDeductions() != null) {
            dto.getDeductions().forEach(d -> {
                PaystubDeduction deduction = PaystubDeduction.builder()
                        .description(d.getDescription())
                        .currentAmount(d.getCurrentAmount())
                        .ytdAmount(d.getYtdAmount())
                        .paystub(paystub)
                        .build();

                paystub.getDeductions().add(deduction);
            });
        }

        // Earnings (rateVariations → earnings)
        if (dto.getRateVariations() != null) {
            dto.getRateVariations().forEach(e -> {
                PaystubEarning earning = PaystubEarning.builder()
                        .description(e.getDescription())
                        .appliedRate(e.getAppliedRate())
                        .hours(e.getHours())
                        .earnings(e.getEarnings())
                        .paystub(paystub)
                        .build();

                paystub.getEarnings().add(earning);
            });
        }

        return paystub;
    }

    // =========================
    // ENTITY -> DTO
    // =========================
    public static PaystubDTO toDTO(Paystub paystub) {

        if (paystub == null) return null;

        return PaystubDTO.builder()
                .id(paystub.getId())
                .employerName(paystub.getEmployerName())
                .employerLocation(paystub.getEmployerLocation())
                .payBeginDate(paystub.getPayBeginDate())
                .payEndDate(paystub.getPayEndDate())
                .checkDate(paystub.getCheckDate())
                .baseHourlyRate(paystub.getBaseHourlyRate())
                .totalHoursWorked(paystub.getTotalHoursWorked())
                .currentGross(paystub.getCurrentGross())
                .ytdGross(paystub.getYtdGross())
                .currentNet(paystub.getCurrentNet())
                .ytdNet(paystub.getYtdNet())
                .deductions(
                        paystub.getDeductions() == null
                                ? Collections.emptyList()
                                : paystub.getDeductions().stream()
                                .map(d -> PaystubDeductionDTO.builder()
                                        .description(d.getDescription())
                                        .currentAmount(d.getCurrentAmount())
                                        .ytdAmount(d.getYtdAmount())
                                        .build())
                                .collect(Collectors.toList())
                )
                .rateVariations(
                        paystub.getEarnings() == null
                                ? Collections.emptyList()
                                : paystub.getEarnings().stream()
                                .map(e -> PaystubEarningDTO.builder()
                                        .description(e.getDescription())
                                        .appliedRate(e.getAppliedRate())
                                        .hours(e.getHours())
                                        .earnings(e.getEarnings())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }
}