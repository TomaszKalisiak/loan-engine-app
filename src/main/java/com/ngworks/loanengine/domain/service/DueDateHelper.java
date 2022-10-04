package com.ngworks.loanengine.domain.service;

import com.ngworks.loanengine.domain.properties.LoanEngineProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class DueDateHelper {

    private final LoanEngineProperties loanEngineConfiguration;

    public Instant calculateDueDate(long term) {
        return Instant.now().plus(term, ChronoUnit.DAYS);
    }

    public Instant extendDueDate(Instant instant) {
        return instant.plus(loanEngineConfiguration.getTermExtension(), ChronoUnit.DAYS);
    }
}
