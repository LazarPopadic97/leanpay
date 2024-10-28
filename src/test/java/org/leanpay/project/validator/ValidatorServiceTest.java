package org.leanpay.project.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.leanpay.project.model.LoanRequest;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class ValidatorServiceTest {

    @InjectMocks
    private ValidatorService validatorService;


    @Test
    void testValidateLoanRequest_AmountLessThanZero_ThrowsException() {
        var loanRequest = new LoanRequest(BigDecimal.valueOf(-1000), BigDecimal.ZERO, 12);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            validatorService.validateLoanRequest(loanRequest);
        });

        assertEquals("Loan amount must be greater than zero.", thrown.getMessage());
    }


    @Test
    void testValidateLoanRequest_InterestRateNegative_ThrowsException() {
        var loanRequest = new LoanRequest(BigDecimal.valueOf(1000), BigDecimal.valueOf(-5), 12);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            validatorService.validateLoanRequest(loanRequest);
        });

        assertEquals("Interest rate must be zero or positive.", thrown.getMessage());
    }


    @Test
    void testValidateLoanRequest_NumberOfMonthsLessThanOrEqualToZero_ThrowsException() {
        var loanRequest = new LoanRequest(BigDecimal.valueOf(1000), BigDecimal.ZERO, 0);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            validatorService.validateLoanRequest(loanRequest);
        });

        assertEquals("Number of months must be greater than zero.", thrown.getMessage());
    }


    @Test
    void testValidateLoanRequest_ValidLoanRequest_DoesNotThrowException() {
        var loanRequest = new LoanRequest(BigDecimal.valueOf(1000), BigDecimal.ZERO, 12);

        validatorService.validateLoanRequest(loanRequest);
    }

}
