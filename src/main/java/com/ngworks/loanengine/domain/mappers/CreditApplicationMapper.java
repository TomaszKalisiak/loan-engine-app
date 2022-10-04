package com.ngworks.loanengine.domain.mappers;

import com.ngworks.loanengine.api.dto.CreditApplicationDto;
import com.ngworks.loanengine.domain.model.CreditApplication;

public class CreditApplicationMapper {
    public static CreditApplication toValueObject(final CreditApplicationDto creditApplicationDto) {
        return CreditApplication.builder()
                .amount(creditApplicationDto.getAmount())
                .term(creditApplicationDto.getTerm())
                .build() ;
    }
}
