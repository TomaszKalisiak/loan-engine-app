package com.ngworks.loanengine.domain.service;

import com.ngworks.loanengine.persistence.CreditApplicationRepository;
import com.ngworks.loanengine.persistence.model.Loan;
import com.ngworks.loanengine.domain.exceptions.LoanNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final CreditApplicationRepository creditApplicationRepository;
    private final DueDateHelper dueDateHelper;

    @Transactional
    public Loan createNewLoan(BigDecimal principal, int term) {
        Instant calculatedDueDate = dueDateHelper.calculateDueDate(term);
        BigDecimal calculatedPrincipal = PrincipalCalculationService.calculatePrincipal(principal);
        return creditApplicationRepository.save(new Loan(calculatedPrincipal, calculatedDueDate));
    }

    @Transactional(readOnly = true)
    public Loan findLoan(long loanId) {
        return creditApplicationRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found for id: " + loanId));
    }

    @Transactional
    public Loan extendLoan(long loanId) {
       Loan loan = findLoan(loanId);
       loan.setDueDate(dueDateHelper.extendDueDate(loan.getDueDate()));
       return creditApplicationRepository.save(loan);
    }
}
