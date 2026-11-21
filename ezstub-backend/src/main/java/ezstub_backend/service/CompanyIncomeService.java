package ezstub_backend.service;

import ezstub_backend.model.CompanyIncome;
import ezstub_backend.payload.apiResponses.PageResponse;
import ezstub_backend.payload.modelDTOs.CompanyIncomeDTO;

import java.util.List;

public interface CompanyIncomeService {

    PageResponse<CompanyIncomeDTO> getCompanyIncome(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CompanyIncomeDTO getCompanyIncome(Long userId, String companyName);
}
