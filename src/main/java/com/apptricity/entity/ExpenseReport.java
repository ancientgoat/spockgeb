package com.apptricity.entity;

import com.apptricity.enums.ExpenseReportStatus;
import com.apptricity.util.CustomDateSerializer;
import com.apptricity.util.UpdateHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Entity representation of an ExpenseReport.
 */
@Entity
@Table(name = "export_report")
public class ExpenseReport {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private String id;
  private BigDecimal amount;
  private Date expenseDateTime;
  private ExpenseReportStatus status = ExpenseReportStatus.NEW;

  /**
   * I added a CreatedDate for grins - I usually like to also add a Last_Modified_Date
   * which helps when knowing which information is the newest in any table/entity.
   */
  private Date createdDate = new Date();

  public String getId() {
    return id;
  }

  /**
   * I serialized the date, which happens during marshalling to JSON, I like Dates to
   * be human readable.
   *
   * @return Date
   */
  @JsonSerialize(using = CustomDateSerializer.class)
  public Date getExpenseDateTime() {
    return expenseDateTime;
  }

  /**
   * I serialized the date, which happens during marshalling to JSON, I like Dates to
   * be human readable.
   *
   * @return Date
   */
  @JsonSerialize(using = CustomDateSerializer.class)
  public Date getCreatedDate() {
    return createdDate;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public ExpenseReportStatus getStatus() {
    return status;
  }

  public ExpenseReport setExpenseDateTime(Date expenseDateTime) {
    this.expenseDateTime = expenseDateTime;
    return this;
  }

  public ExpenseReport setAmount(final BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  public ExpenseReport setStatus(final ExpenseReportStatus status) {
    this.status = status;
    return this;
  }

  /**
   * @param inExpenseDateTime
   * @return boolean True if there was a change, false if no change to the value.
   */
  public boolean updateExpenseDateTime(final Date inExpenseDateTime) {
    final UpdateHelper<Date> updateHelper = UpdateHelper.newInstance(this.expenseDateTime, inExpenseDateTime);
    this.expenseDateTime = updateHelper.getValue();
    return updateHelper.isChanged();
  }

  /**
   * @param inAmount
   * @return boolean True if there was a change, false if no change to the value.
   */
  public boolean updateAmount(final BigDecimal inAmount) {
    final UpdateHelper<BigDecimal> updateHelper = UpdateHelper.newInstance(this.amount, inAmount);
    this.amount = updateHelper.getValue();
    return updateHelper.isChanged();
  }

  /**
   * @param inStatus
   * @return boolean True if there was a change, false if no change to the value.
   */
  public boolean updateStatus(final ExpenseReportStatus inStatus) {
    final UpdateHelper<ExpenseReportStatus> updateHelper = UpdateHelper.newInstance(this.status, inStatus);
    this.status = updateHelper.getValue();
    return updateHelper.isChanged();
  }
}
