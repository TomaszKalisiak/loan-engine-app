package com.ngworks.loanengine.domain.service;

import com.ngworks.loanengine.domain.properties.LoanEngineProperties;
import com.ngworks.loanengine.domain.exceptions.DecisionEngineException;
import com.ngworks.loanengine.domain.model.CreditApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class DecisionEngine {

    private final LoanEngineProperties loanEngineConfiguration;

    public void decide(CreditApplication creditApplication) {
        verifyTimeCutoff(creditApplication.getApplicationRequestTime());
        verifyRequestedAmount(creditApplication.getAmount());
        verifyTerm(creditApplication.getTerm());
    }

    private void verifyRequestedAmount(BigDecimal requestedAmount) {
        if ((requestedAmount.compareTo(loanEngineConfiguration.getAmountMax()) > 0)
                || (requestedAmount.compareTo(loanEngineConfiguration.getAmountMin()) < 0)) {
            throw new DecisionEngineException("Invalid requested amount, out of allowed, configured range:" + requestedAmount);
        }
    }

    private void verifyTerm(int term) {
        if (term > loanEngineConfiguration.getTermMax()
                || term < loanEngineConfiguration.getTermMin()) {
            throw new DecisionEngineException("Invalid term, out of allowed, configured range:" + term);
        }
    }

    private void verifyTimeCutoff(LocalTime localTime) {
        boolean notLateNightHours =
                localTime.isAfter(LocalTime.parse(loanEngineConfiguration.getLowerCutoffTime()))
                        && localTime.isBefore(LocalTime.parse(loanEngineConfiguration.getUpperCutoffTime()));
        if (notLateNightHours) {
            throw new DecisionEngineException("Credit Application request time in acceptable hourly range.");
        }

    }
}
