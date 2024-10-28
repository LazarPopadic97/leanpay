package org.leanpay.project.entity;

import java.util.List;

public record TotalPayment(List<MonthlyPayment> monthlyPayments) {

}
