package org.leanpay.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class InstallmentPlan {

    @org.springframework.data.annotation.Id
    private Long id;

    private BigDecimal amount;

    private BigDecimal annualInterestRate;

    private Integer numberOfMonths;

    private TotalPayment totalPayment;

}
