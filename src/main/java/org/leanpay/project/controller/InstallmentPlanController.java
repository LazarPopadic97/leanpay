package org.leanpay.project.controller;


import lombok.RequiredArgsConstructor;
import org.leanpay.project.V1Api;
import org.leanpay.project.mapper.LoanRequestMapper;
import org.leanpay.project.mapper.TotalPaymentMapper;
import org.leanpay.project.model.LoanRequestDto;
import org.leanpay.project.model.TotalPaymentDto;
import org.leanpay.project.service.InstallmentPlanService;
import org.leanpay.project.validator.ValidatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InstallmentPlanController implements V1Api {


    private final InstallmentPlanService installmentPlanService;

    private final TotalPaymentMapper totalPaymentMapper;

    private final LoanRequestMapper loanRequestMapper;

    private final ValidatorService validatorService;


    @Override
    public ResponseEntity<TotalPaymentDto> calculateInstallmentPlan(LoanRequestDto loanRequestDto) {
        var loanRequest = loanRequestMapper.toLoanRequest(loanRequestDto);
        validatorService.validateLoanRequest(loanRequest);
        var totalPayment = installmentPlanService.saveInstallationPlan(loanRequest);

        return ResponseEntity.ok(totalPaymentMapper.toTotalPaymentDto(totalPayment));
    }

}
