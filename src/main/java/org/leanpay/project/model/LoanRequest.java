package org.leanpay.project.model;

import java.math.BigDecimal;

public record LoanRequest(BigDecimal amount,
                          BigDecimal annualInterestPercent,
                          Integer numberOfMonths) {
}
