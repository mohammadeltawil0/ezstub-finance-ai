package ezstub_backend.controller;

import ezstub_backend.config.AppConstants;
import ezstub_backend.model.CompanyIncome;
import ezstub_backend.payload.apiResponses.PageResponse;
import ezstub_backend.payload.modelDTOs.CompanyIncomeDTO;
import ezstub_backend.service.CompanyIncomeService;
import ezstub_backend.util.AuthUtil;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company-income")
public class CompanyIncomeController {

    @Autowired
    private CompanyIncomeService companyIncomeService;
    @Autowired
    private AuthUtil authUtil;

    @GetMapping
    public ResponseEntity<PageResponse<CompanyIncomeDTO>> getCompanyIncomes(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_COMPANY_INCOME_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortOrder
    ){
        Long userId = authUtil.loggedInUserId();
        PageResponse<CompanyIncomeDTO> response = companyIncomeService.getCompanyIncome(userId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{companyName}")
    public ResponseEntity<CompanyIncomeDTO> getCompanyIncomeByName(@PathVariable String companyName) {
        Long userId = authUtil.loggedInUserId();
        CompanyIncomeDTO response = companyIncomeService.getCompanyIncome(userId, companyName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
