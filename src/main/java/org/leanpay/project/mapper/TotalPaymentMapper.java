package org.leanpay.project.mapper;

import org.leanpay.project.entity.TotalPayment;
import org.leanpay.project.model.TotalPaymentDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface TotalPaymentMapper {

    TotalPaymentDto toTotalPaymentDto(TotalPayment totalPayment);
}
