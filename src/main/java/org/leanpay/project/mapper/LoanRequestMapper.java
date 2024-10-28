package org.leanpay.project.mapper;

import org.leanpay.project.model.LoanRequest;
import org.leanpay.project.model.LoanRequestDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface LoanRequestMapper {

    LoanRequest toLoanRequest(LoanRequestDto loanRequestDto);

}
