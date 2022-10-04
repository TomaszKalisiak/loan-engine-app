package com.ngworks.loanengine.api.exposure;

import com.ngworks.loanengine.api.dto.ContractDto;
import com.ngworks.loanengine.api.dto.CreditApplicationDto;
import com.ngworks.loanengine.domain.service.LoanEngineService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanEngineController {

    private final LoanEngineService loanService;

    @PostMapping(path = "/")
    public ContractDto applyForLoan(@RequestBody CreditApplicationDto creditApplicationDto) {
        log.info("Creating new loan application:  " + creditApplicationDto);
        return loanService.applyForLoan(creditApplicationDto);
    }

    @PatchMapping(path = "/{id}")
    public ContractDto extendContract(@PathVariable @NotNull Long id) {
        log.info("Due date extension for loan with id " + id);
        return loanService.extendLoan(id);
    }

    @GetMapping(path = "/{id}")
    public ContractDto fetchContract(@PathVariable @NotNull Long id) {
        log.info("Fetching loan with id:  " + id);
        return loanService.findContract(id);
    }
}
