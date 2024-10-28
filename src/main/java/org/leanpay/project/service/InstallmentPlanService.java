package org.leanpay.project.service;

import lombok.RequiredArgsConstructor;
import org.leanpay.project.entity.InstallmentPlan;
import org.leanpay.project.entity.MonthlyPayment;
import org.leanpay.project.entity.TotalPayment;
import org.leanpay.project.model.LoanRequest;
import org.leanpay.project.repository.InstallmentPlanRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstallmentPlanService {

    private final InstallmentPlanRepository installmentPlanRepository;


    public TotalPayment saveInstallationPlan(LoanRequest loanRequest) {
        var totalPayment = new TotalPayment(calculateMonthlyPayments(loanRequest));
        installmentPlanRepository.save(createInstallmentPlan(loanRequest, totalPayment));

        return totalPayment;
    }


    private List<MonthlyPayment> calculateMonthlyPayments(LoanRequest loanRequest) {
        List<MonthlyPayment> monthlyPayments = new ArrayList<>();

        BigDecimal monthlyInterestRate = loanRequest.annualInterestPercent()
            .divide(BigDecimal.valueOf(100), MathContext.DECIMAL128)
            .divide(BigDecimal.valueOf(12), MathContext.DECIMAL128);

        BigDecimal onePlusR = BigDecimal.ONE.add(monthlyInterestRate);

        var monthlyPayment = (onePlusR.pow(loanRequest.numberOfMonths(), MathContext.DECIMAL128)
                                  .subtract(BigDecimal.ONE)
                                  .compareTo(BigDecimal.ZERO) != 0)
            ? loanRequest.amount()
            .multiply(monthlyInterestRate
                .multiply(onePlusR.pow(loanRequest.numberOfMonths(), MathContext.DECIMAL128)))
            .divide(onePlusR.pow(loanRequest.numberOfMonths(), MathContext.DECIMAL128)
                .subtract(BigDecimal.ONE), MathContext.DECIMAL128)
            .setScale(2, RoundingMode.HALF_UP)
            : loanRequest.amount()
            .divide(BigDecimal.valueOf(loanRequest.numberOfMonths()), MathContext.DECIMAL128);

        for (int month = 1; month <= loanRequest.numberOfMonths(); month++) {
            monthlyPayments.add(new MonthlyPayment(month, monthlyPayment));
        }

        return monthlyPayments;

    }

    private InstallmentPlan createInstallmentPlan(LoanRequest loanRequest, TotalPayment totalPayment) {
        return new InstallmentPlan(null, loanRequest.amount(), loanRequest.annualInterestPercent(), loanRequest.numberOfMonths(),
            totalPayment);
    }

}
