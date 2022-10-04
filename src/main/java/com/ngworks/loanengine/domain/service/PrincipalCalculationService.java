package com.ngworks.loanengine.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PrincipalCalculationService {

    private static final BigDecimal OFFERED_PRINCIPAL_PERCENTAGE = BigDecimal.valueOf(1.1);

    public static BigDecimal calculatePrincipal(final BigDecimal bigDecimal) {
        return bigDecimal.multiply(OFFERED_PRINCIPAL_PERCENTAGE)
                .setScale(2, RoundingMode.HALF_DOWN);
    }
}
