package com.apptricity.dto;

import com.apptricity.entity.ExpenseReport;
import com.apptricity.enums.ExpenseReportStatus;
import com.apptricity.util.Messages;

import java.math.BigDecimal;
import java.util.Date;

/**
 * A Data Transfer Object that is created by input JSON during the acceptance of a HTTP call in a Controller.
 */
public class ExpenseReportCreateDto {

  /**
   * ExpenseReport entity
   */
  private final ExpenseReport expenseReport;

  /**
   * Messages come in three type ERROR, WANN, INFO
   */
  private final Messages messages;

  /**
   * Set the basic information for a ExpenseReport from the input from a Controller.
   *
   * @param builder Follows the builder pattern.
   */
  private ExpenseReportCreateDto(final Builder builder) {
    this.messages = builder.messages;
    this.expenseReport =
        new ExpenseReport()
            .setAmount(builder.amount)
            .setExpenseDateTime(builder.expenseDateTime)
            .setStatus(builder.status)
    ;
  }

  /**
   * @return ExpenseReport Return the ExpenseReport
   */
  public ExpenseReport getExpenseReport() {
    return this.expenseReport;
  }

  /**
   * @return boolean Return true if there are any associated Messages.
   */
  public boolean hasMessage() {
    return this.messages.hasMessage();
  }

  /**
   * @return Messages
   */
  public Messages getMessages() {
    return this.messages;
  }

  /**
   * The builder pattern.  This class is built internally when a controller receives HTTP JSoN content
   *    in the forms of a ExpenseReport.
   */
  public static class Builder {

    private BigDecimal amount;
    private Date expenseDateTime;
    private String comment;
    private ExpenseReportStatus status = ExpenseReportStatus.NEW;
    private Messages messages;
    private Messages.Builder messagesBuilder = new Messages.Builder();

    public void setStatus(ExpenseReportStatus status) {
      this.status = status;
    }

    public Builder setComment(final String inComment) {
      this.comment = inComment;
      return this;
    }

    public Builder setAmount(BigDecimal amount) {
      this.amount = amount;
      return this;
    }

    public Builder setExpenseDateTime(final Date expenseDateTime) {
      this.expenseDateTime = expenseDateTime;
      return this;
    }

    /**
     * Validate input for an (Create) Insert into the database
     */
    private void validateForInsert() {
      if (null == this.amount) {
        this.messagesBuilder.addError("'amount' is required, please add and try again.");
      }
      if (null == this.expenseDateTime) {
        this.messagesBuilder.addError("'expenseDateTime' is required, please add and try again.");
      }
    }

    /**
     * No validation for an Update into the database, at this stage.
     */
    private void validateForUpdate() {
    }

    /**
     * @return ExpenseReportCreateDto Build the DTO after checking for rules for inserting.
     */
    public ExpenseReportCreateDto buildForUpdate() {
      validateForUpdate();
      return _build();
    }

    /**
     * @return ExpenseReportCreateDto Build the DTO after checking for rules for updating.
     */
    public ExpenseReportCreateDto buildForInsert() {
      validateForInsert();
      return _build();
    }

    /**
     * I have a saying, if you do it twice - do it once.
     *
     * @return ExpenseReportCreateDto
     */
    private ExpenseReportCreateDto _build() {
      this.messages = messagesBuilder.build();
      return new ExpenseReportCreateDto(this);
    }
  }
}
