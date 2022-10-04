package com.ngworks.loanengine.domain.validation;

import com.ngworks.loanengine.api.dto.CreditApplicationDto;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LoanEngineValidator {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public void validate(CreditApplicationDto creditApplicationDto) {
        Optional.ofNullable(creditApplicationDto)
                .orElseThrow(() -> new ValidationException("CreditApplicationDto cannot be null"));
        Set<ConstraintViolation<CreditApplicationDto>> violations = validator.validate(creditApplicationDto);
        if (!violations.isEmpty()) {
            throw new ValidationException("Validation exception. Constraints violated: " +
                    violations.stream().map(v -> v.getPropertyPath().toString() + " " +
                            v.getMessage()).collect(Collectors.joining(", \n")));
        }
    }
}
