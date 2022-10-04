package com.ngworks.loanengine.domain.service;

import com.ngworks.loanengine.domain.validation.LoanEngineValidator;
import com.ngworks.loanengine.api.dto.ContractDto;
import com.ngworks.loanengine.api.dto.CreditApplicationDto;
import com.ngworks.loanengine.domain.mappers.ContractMapper;
import com.ngworks.loanengine.domain.mappers.CreditApplicationMapper;
import com.ngworks.loanengine.domain.model.CreditApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;


@Service
@RequiredArgsConstructor
public class LoanEngineService {

    private final LoanService loanService;
    private final DecisionEngine decisionEngine;
    private final LoanEngineValidator validator;

    public ContractDto applyForLoan(final CreditApplicationDto creditApplicationDto) {
        validator.validate(creditApplicationDto);

        CreditApplication creditApplication = CreditApplicationMapper.toValueObject(creditApplicationDto);
        creditApplication.setApplicationRequestTime(LocalTime.now());

        decisionEngine.decide(creditApplication);

        return ContractMapper.toDto(loanService.createNewLoan(creditApplication.getAmount(), creditApplication.getTerm()));
    }

    public ContractDto findContract(long loanId) {
        return ContractMapper.toDto(loanService.findLoan(loanId));
    }

    public ContractDto extendLoan(long loanId) {
        return ContractMapper.toDto(loanService.extendLoan(loanId));
    }
}
