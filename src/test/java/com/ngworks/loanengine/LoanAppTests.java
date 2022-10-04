package com.ngworks.loanengine;

import com.ngworks.loanengine.domain.properties.LoanEngineProperties;
import com.ngworks.loanengine.api.dto.ContractDto;
import com.ngworks.loanengine.api.dto.CreditApplicationDto;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Example of a full web server test starting up whole spring context for the application including H2 database
 */
@SpringBootTest(classes = LoanEngineApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoanAppTests {

    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    LoanEngineProperties loanEngineConfiguration;

    Condition<HttpStatus> ok = new Condition<>(
            status -> HttpStatus.OK == status, "ok");
    Condition<HttpStatus> badRequest = new Condition<>(
            status -> HttpStatus.BAD_REQUEST == status, "Bad Request");

    @Test
    public void givenProperPayload_shouldCreateLoanFetchTheLoanAndExtendDueDate () {

        //given
        CreditApplicationDto creditApplicationDto = new CreditApplicationDto(new BigDecimal(500), 356);

        //when
        ResponseEntity<ContractDto> responsePostEntity =
                restTemplate.postForEntity("/loan/", creditApplicationDto, ContractDto.class);
        ResponseEntity<ContractDto> responseGetEntity =
                restTemplate.getForEntity("/loan/" + responsePostEntity.getBody().getLoanId(), ContractDto.class);

        HttpEntity<Void> request = new HttpEntity<>(null);
        ResponseEntity<ContractDto> responsePatchEntity =
                restTemplate.exchange("/loan/" + responsePostEntity.getBody().getLoanId(), HttpMethod.PATCH, request,
                        ContractDto.class);

        //then
        Assertions.assertThat(responsePostEntity.getStatusCode()).is(ok);
        Assertions.assertThat(responseGetEntity.getStatusCode()).is(ok);
        Assertions.assertThat(responsePatchEntity.getStatusCode()).is(ok);

        Assertions.assertThat(responsePostEntity.getBody().getCalculatedPrincipal())
                .isEqualTo(creditApplicationDto.getAmount()
                        .multiply(new BigDecimal(1.1)).setScale(2, RoundingMode.HALF_DOWN));
        Assertions.assertThat(responsePostEntity.getBody().getLoanId())
                .isEqualTo(responseGetEntity.getBody().getLoanId());
        Assertions.assertThat(responsePatchEntity.getBody().getLoanId())
                .isEqualTo(responsePostEntity.getBody().getLoanId());

    }

    @Test
    public void givenRequestedAmount_shouldReturnBadRequestBasedOnDecisionEngine() {

        //given
        int invalidTerm = 2;
        CreditApplicationDto creditApplicationDto = new CreditApplicationDto(new BigDecimal(500), invalidTerm);

        //when
        ResponseEntity<String> responsePostEntity = restTemplate.postForEntity("/loan/", creditApplicationDto, String.class);

        //then
        Assertions.assertThat(responsePostEntity.getStatusCode()).is(badRequest);
    }
}
