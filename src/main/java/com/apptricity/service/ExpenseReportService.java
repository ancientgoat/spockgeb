package com.apptricity.service;

import com.apptricity.dto.ExpenseReportCreateDto;
import com.apptricity.dto.ExpenseReportResponseDto;
import com.apptricity.entity.ExpenseReport;
import com.apptricity.enums.ExpenseReportStatus;
import com.apptricity.repo.ExpenseReportRepo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service/Business layer between the Controller and the Repositories.
 */
@Service
public class ExpenseReportService {

  @JsonIgnore
  public static final Logger log = LoggerFactory.getLogger(ExpenseReportService.class);

  private ExpenseReportRepo expenseReportRepo;

  @Autowired
  public ExpenseReportService(final ExpenseReportRepo inExpenseReportRepo) {
    this.expenseReportRepo = inExpenseReportRepo;
  }

  /**
   * @param pageable
   * @return
   */
  public Page<ExpenseReport> findAll(final Pageable pageable) {
    return expenseReportRepo.findAll(pageable);
  }

  public List<ExpenseReport> findAll() {
    List<ExpenseReport> all = expenseReportRepo.findAll();
    return all;
  }

  /**
   * @param predicate
   * @param pageable
   * @return
   */
  // public Page<ExpenseReport> findAll(final Predicate predicate, final Pageable pageable) {
  //   return expenseReportRepo.findAll(predicate, pageable);
  // }

  /**
   * @param inId
   * @return
   */
  public ExpenseReport findOne(final String inId) {
    //expenseReportRepo.findBy
    ExpenseReport expenseReport = expenseReportRepo.findOne(inId);
    return expenseReport;

  }

  /**
   * @param createDto
   * @return
   */
  public ExpenseReportResponseDto createFromDto(final ExpenseReportCreateDto createDto) {

    final ExpenseReportResponseDto.Builder responseDtoBuilder = new ExpenseReportResponseDto.Builder();

    try {
      if (!createDto.hasMessage()) {
        ExpenseReport expenseReport = createDto.getExpenseReport();
        responseDtoBuilder.setExpenseReport(expenseReportRepo.save(expenseReport));
      } else {
        // Has errors
        responseDtoBuilder.addMessages(createDto.getMessages());
      }
    } catch (Exception e) {
      responseDtoBuilder.addError(e.getMessage());
    }
    return responseDtoBuilder.build();
  }

  /**
   * @param inId
   * @param createDto
   * @return
   */
  public ExpenseReportResponseDto updateFromDto(final String inId, final ExpenseReportCreateDto createDto) {

    final ExpenseReportResponseDto.Builder responseDtoBuilder =
        new ExpenseReportResponseDto.Builder();

    try {
      final ExpenseReport expenseReport = this.expenseReportRepo.findOne(inId);

      if (null == expenseReport) {
        responseDtoBuilder.addError(String.format("No such ExpenseReport with id '%s'", inId));
      } else {
        if (ExpenseReportStatus.REIMBURSED == expenseReport.getStatus()) {
          // ExpenseReport is NOT NEW - update not allowed.
          responseDtoBuilder.addWarn("Can not update a REIMBURSED ExpenseReport.");
        } else {
          final ExpenseReport dtoExpenseRpt = createDto.getExpenseReport();

          // Update ExpenseReport
          boolean changed =
              expenseReport.updateAmount(dtoExpenseRpt.getAmount())
                  || expenseReport.updateExpenseDateTime(dtoExpenseRpt.getExpenseDateTime());

          final ExpenseReportStatus status = createDto.getExpenseReport().getStatus();
          if (null != status && status == ExpenseReportStatus.REIMBURSED) {
            changed = expenseReport.updateStatus(status) || changed;
          }

          // Save some time - if nothing changed.
          ExpenseReport savedExpenseReport = null;
          if (changed) {
            savedExpenseReport = this.expenseReportRepo.save(expenseReport);
          } else {
            responseDtoBuilder.addInfo(
                String.format("No changes were input for ExpenseReport id '%s', so nothing saved.", inId));
            savedExpenseReport = expenseReport;
          }
          responseDtoBuilder.setExpenseReport(savedExpenseReport);
        }
      }
    } catch (Exception e) {
      responseDtoBuilder.addWarn(String.format("Error with ExpenseReport : '%s' : %s", inId, e.toString()));
    }
    return responseDtoBuilder.build();
  }

  /**
   * @param inId
   * @return
   */
  public ExpenseReportResponseDto delete(final String inId) {

    final ExpenseReportResponseDto.Builder responseDtoBuilder =
        new ExpenseReportResponseDto.Builder();

    try {
      final ExpenseReport expenseReport = this.expenseReportRepo.findOne(inId);

      if (null == expenseReport) {
        responseDtoBuilder.addError(String.format("No such ExpenseReport with id '%s'", inId));
      } else {
        if (ExpenseReportStatus.REIMBURSED == expenseReport.getStatus()) {
          // ExpenseReport is NOT NEW - update not allowed.
          responseDtoBuilder.addWarn("Can not delete a REIMBURSED ExpenseReport.");
        } else {
          final String msg = String.format("Deleted ExpenseReport '%s'", inId);
          this.expenseReportRepo.delete(inId);
          if (log.isInfoEnabled()) {
            log.info(msg);
          }
          responseDtoBuilder.addInfo(msg);
        }
      }
    } catch (Exception e) {
      responseDtoBuilder.addWarn(String.format("Error with ExpenseReport : '%s' : %s", inId, e.toString()));
    }
    return responseDtoBuilder.build();
  }
}
