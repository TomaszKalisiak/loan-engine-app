package com.ngworks.loanengine.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
public class ContractDto {

    private long loanId;
    private Instant dueDate;
    private BigDecimal calculatedPrincipal;
}
