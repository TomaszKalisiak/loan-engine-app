package com.ngworks.loanengine.domain.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConfigurationProperties(prefix = "loan")
@Getter
@Setter
public class LoanEngineProperties {
    private BigDecimal amountMin;
    private BigDecimal amountMax;
    private int termMin;
    private int termMax;
    private int termExtension;
    private String lowerCutoffTime;
    private String upperCutoffTime;
}
