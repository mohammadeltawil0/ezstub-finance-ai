package ezstub_backend.service;

import ezstub_backend.model.CompanyIncome;
import ezstub_backend.payload.modelDTOs.CompanyIncomeDTO;

import java.util.List;

public interface CompanyIncomeService {

    List<CompanyIncome> getCompanyIncome(Long userId);
    CompanyIncomeDTO getCompanyIncome(Long userId, String companyName);
}
