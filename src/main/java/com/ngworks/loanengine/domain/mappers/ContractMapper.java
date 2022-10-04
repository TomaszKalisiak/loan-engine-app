package com.ngworks.loanengine.domain.mappers;

import com.ngworks.loanengine.persistence.model.Loan;
import com.ngworks.loanengine.api.dto.ContractDto;

public class ContractMapper {

    public static ContractDto toDto(Loan loan) {
        return ContractDto.builder()
                .loanId(loan.getId())
                .dueDate(loan.getDueDate())
                .calculatedPrincipal(loan.getPrincipal())
                .build();
    }
}
