package org.leanpay.project.repository;

import org.leanpay.project.entity.InstallmentPlan;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallmentPlanRepository extends ListCrudRepository<InstallmentPlan, Long> {

}
