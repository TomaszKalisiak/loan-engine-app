package com.ngworks.loanengine.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;

@Builder
@Getter
@Setter
public class CreditApplication {
    private BigDecimal amount;
    private Integer term;
    private LocalTime applicationRequestTime;
}
