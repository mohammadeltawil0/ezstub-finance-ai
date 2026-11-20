package ezstub_backend.service.impl;

import ezstub_backend.model.CompanyIncome;
import ezstub_backend.model.Paystub;
import ezstub_backend.payload.modelDTOs.CompanyIncomeDTO;
import ezstub_backend.repository.PaystubRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompanyIncomeService implements ezstub_backend.service.CompanyIncomeService {

    @Autowired
    private PaystubRepository paystubRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Transactional
    public List<CompanyIncome> getCompanyIncome(Long userId) {

        List<Paystub> paystubs = paystubRepository.findByUser_UserId(userId);
        Map<String, List<Paystub>> groupedPaystubs = paystubs.stream()
                .collect(Collectors.groupingBy(Paystub::getEmployerName));
        List<CompanyIncome> results = new ArrayList<>();
        long idCounter = 1L;

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

            CompanyIncome ci = new CompanyIncome(
                    idCounter++,
                    company,
                    totalEarned,
                    totalDeductions,
                    totalHours,
                    startDate,
                    endDate,
                    stubs.size()
            );
            results.add(ci);
        }
        return results;
    }

    @Override
    @Transactional
    public CompanyIncomeDTO getCompanyIncome(Long userId, String companyName) {
        List<CompanyIncome> list = getCompanyIncome(userId);
        CompanyIncome match = list.stream()
                .filter(ci -> ci.getCompanyName().equalsIgnoreCase(companyName))
                .findFirst()
                .orElseGet(() -> new CompanyIncome(null, companyName, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, null, null, 0));
        return modelMapper.map(match, CompanyIncomeDTO.class);
    }
}
