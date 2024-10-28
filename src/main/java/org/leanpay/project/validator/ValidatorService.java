package org.leanpay.project.validator;

import org.leanpay.project.model.LoanRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ValidatorService {

    public void validateLoanRequest(LoanRequest loanRequest) {
        if (loanRequest.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Loan amount must be greater than zero.");
        }
        if (loanRequest.annualInterestPercent().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Interest rate must be zero or positive.");
        }
        if (loanRequest.numberOfMonths() <= 0) {
            throw new IllegalArgumentException("Number of months must be greater than zero.");
        }
    }
}
