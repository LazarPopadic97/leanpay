package org.leanpay.project.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.leanpay.project.entity.InstallmentPlan;
import org.leanpay.project.entity.MonthlyPayment;
import org.leanpay.project.entity.TotalPayment;
import org.leanpay.project.model.LoanRequest;
import org.leanpay.project.repository.InstallmentPlanRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InstallmentPlanServiceTest {


    @InjectMocks
    private InstallmentPlanService installmentPlanService;

    @Mock
    private InstallmentPlanRepository installmentPlanRepository;



    @Test
    void saveInstallationPlanTest() {

        var loanRequest = new LoanRequest(BigDecimal.valueOf(1000), BigDecimal.valueOf(5), 5);
        var expected = new TotalPayment(List.of(new MonthlyPayment(1,BigDecimal.valueOf(202.51)),
            new MonthlyPayment(2,BigDecimal.valueOf(202.51)),
            new MonthlyPayment(3,BigDecimal.valueOf(202.51)),
            new MonthlyPayment(4,BigDecimal.valueOf(202.51)),
            new MonthlyPayment(5,BigDecimal.valueOf(202.51))));

        var installmentPlan = new InstallmentPlan(1L,BigDecimal.valueOf(1000), BigDecimal.valueOf(5), 5, expected);

        when(installmentPlanRepository.save(any(InstallmentPlan.class))).thenReturn(installmentPlan);

        var actual = installmentPlanService.saveInstallationPlan(loanRequest);

        assertEquals(expected, actual);

    }

}
