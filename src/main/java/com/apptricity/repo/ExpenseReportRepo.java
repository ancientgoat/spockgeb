package com.apptricity.repo;

import com.apptricity.entity.ExpenseReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Simple Spring Repository for an ExpenseReport.
 * 'exported = false' is used to NOT export the default REST calls to the outside world.
 * However, they can still be used internally by other classes, like a Service.
 * <p/>
 * Also, 'QueryDslPredicateExecutor' is used to allow the dynamic querying of an ExpenseReport
 * based upon parameters passed from the URL
 */
@RepositoryRestResource(collectionResourceRel = "expensereports", path = "expensereport", exported = false)
public interface ExpenseReportRepo
    //extends JpaRepository<ExpenseReport, String>, QueryDslPredicateExecutor<ExpenseReport> {
    extends JpaRepository<ExpenseReport, String> {
}
