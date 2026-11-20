package ezstub_backend.controller;

import ezstub_backend.model.CompanyIncome;
import ezstub_backend.payload.modelDTOs.CompanyIncomeDTO;
import ezstub_backend.service.CompanyIncomeService;
import ezstub_backend.util.AuthUtil;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/company-income")
public class CompanyIncomeController {

    @Autowired
    private CompanyIncomeService companyIncomeService;
    @Autowired
    private AuthUtil authUtil;

    @GetMapping
    public ResponseEntity<List<CompanyIncome>> getCompanyIncomes(){
        Long userId = authUtil.loggedInUserId();
        List<CompanyIncome> response = companyIncomeService.getCompanyIncome(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{companyName}")
    public ResponseEntity<CompanyIncomeDTO> getCompanyIncomeByName(@PathVariable String companyName) {
        Long userId = authUtil.loggedInUserId();
        CompanyIncomeDTO response = companyIncomeService.getCompanyIncome(userId, companyName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
