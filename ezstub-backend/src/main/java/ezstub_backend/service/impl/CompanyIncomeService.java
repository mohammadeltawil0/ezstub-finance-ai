package ezstub_backend.service.impl;

import ezstub_backend.model.CompanyIncome;
import ezstub_backend.model.Paystub;
import ezstub_backend.payload.apiResponses.PageResponse;
import ezstub_backend.payload.modelDTOs.CompanyIncomeDTO;
import ezstub_backend.repository.PaystubRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyIncomeService implements ezstub_backend.service.CompanyIncomeService {

    @Autowired
    private PaystubRepository paystubRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Transactional
    public PageResponse<CompanyIncomeDTO> getCompanyIncome(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        List<CompanyIncomeDTO> allItems = buildCompanyIncomeList(userId);

        Comparator<CompanyIncomeDTO> comparator = switch (sortBy) {
            case "totalEarnedFromCompany" -> Comparator.comparing(CompanyIncomeDTO::getTotalEarnedFromCompany, Comparator.nullsLast(Comparator.naturalOrder()));
            case "hoursWorkedAtCompany" -> Comparator.comparing(CompanyIncomeDTO::getHoursWorkedAtCompany, Comparator.nullsLast(Comparator.naturalOrder()));
            case "paystubCount" -> Comparator.comparing(CompanyIncomeDTO::getPaystubCount, Comparator.nullsLast(Comparator.naturalOrder()));
            default -> Comparator.comparing(CompanyIncomeDTO::getCompanyName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
        };
        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }
        allItems.sort(comparator);

        int totalElements = allItems.size();
        int safeSize = Math.max(1, pageSize);
        int totalPages = (int) Math.ceil((double) totalElements / safeSize);
        int fromIndex = Math.min(pageNumber + safeSize, totalElements);
        int toIndex = Math.min(fromIndex + safeSize, totalElements);
        List<CompanyIncomeDTO> pageContent = allItems.subList(fromIndex, toIndex);

        return PageResponse.<CompanyIncomeDTO>builder()
                .content(pageContent)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements((long) totalElements)
                .totalPages(totalPages)
                .lastPage(pageNumber >= totalPages - 1 || totalElements == 0)
                .build();
    }


    @Override
    @Transactional
    public CompanyIncomeDTO getCompanyIncome(Long userId, String companyName) {
        return buildCompanyIncomeList(userId).stream()
                .filter(ci -> ci.getCompanyName() != null && ci.getCompanyName().equalsIgnoreCase(companyName))
                .findFirst()
                .orElseGet(() -> CompanyIncomeDTO.builder()
                        .companyName(companyName)
                        .totalEarnedFromCompany(BigDecimal.ZERO)
                        .totalDeductionsFromCompany(BigDecimal.ZERO)
                        .hoursWorkedAtCompany(BigDecimal.ZERO)
                        .dateStarted(null)
                        .dateEnded(null)
                        .paystubCount(0)
                        .build());
    }

    private List<CompanyIncomeDTO> buildCompanyIncomeList(Long userId) {

        List<Paystub> paystubs = paystubRepository.findByUser_UserId(userId);
        Map<String, List<Paystub>> groupedPaystubs = paystubs.stream()
                .filter(p -> p.getEmployerName() != null)
                .collect(Collectors.groupingBy(Paystub::getEmployerName));

        List<CompanyIncomeDTO> allItems = new ArrayList<>();

        for (Map.Entry<String, List<Paystub>> entry : groupedPaystubs.entrySet()) {
            String company = entry.getKey();
            List<Paystub> stubs = entry.getValue();

            BigDecimal totalEarned = stubs.stream()
                    .map(paystub -> paystub.getCurrentGross() != null ? paystub.getCurrentGross() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalDeductions = stubs.stream()
                    .flatMap(paystub -> paystub.getDeductions().stream())
                    .map(deduction -> deduction.getCurrentAmount() != null ? deduction.getCurrentAmount() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalHours = stubs.stream()
                    .map(paystub -> paystub.getTotalHoursWorked() != null ? paystub.getTotalHoursWorked() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            LocalDate startDate = stubs.stream().map(Paystub::getPayBeginDate).filter(Objects::nonNull).min(LocalDate::compareTo).orElse(null);
            LocalDate endDate = stubs.stream().map(Paystub::getPayEndDate).filter(Objects::nonNull).max(LocalDate::compareTo).orElse(null);

            allItems.add(CompanyIncomeDTO.builder()
                    .companyName(company)
                    .totalEarnedFromCompany(totalEarned)
                    .totalDeductionsFromCompany(totalDeductions)
                    .hoursWorkedAtCompany(totalHours)
                    .dateStarted(startDate)
                    .dateEnded(endDate)
                    .paystubCount(stubs.size())
                    .build()
            );

        }
        return allItems;
    }
}
