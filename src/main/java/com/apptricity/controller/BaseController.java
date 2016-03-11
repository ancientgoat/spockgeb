package com.apptricity.controller;

import com.apptricity.dto.ExpenseReportCreateDto;
import com.apptricity.dto.ExpenseReportResponseDto;
import com.apptricity.entity.ExpenseReport;
import com.apptricity.service.ExpenseReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * A super class containing all supported rest calls for the ExpenseManagement project.
 * Both Controllers, one with Basic Authorization Security required and one without.
 *
 * @see ExpenseReportController
 * @see ExpenseReportAuthController
 */
public class BaseController {

  private ExpenseReportService expenseReportService;

  private BaseController() {
  }

  public BaseController(final ExpenseReportService inExpenseReportService) {
    this.expenseReportService = inExpenseReportService;
  }

  /**
   * Find all Expense reports, allow a 'querydsl' filter based on URL parameters.
   *
   * @param model
   * @param predicate
   * @param pageable
   * @param parameters
   * @return ResponseEntity Returns a HttpStatus along with an Object that is displayed as JSON.
   */
//  public ResponseEntity findAllPageable(
//      Model model,
//      @QuerydslPredicate(root = ExpenseReport.class) Predicate predicate, Pageable pageable,
//      @RequestParam MultiValueMap<String, String> parameters
//  ) {
//    HttpStatus httpStatus = HttpStatus.OK;
//    Page<ExpenseReport> expenseReportPages = expenseReportService.findAll(predicate, pageable);
//    if (0 == expenseReportPages.getTotalElements()) {
//      httpStatus = HttpStatus.NOT_FOUND;
//    }
//    return new ResponseEntity(expenseReportPages, httpStatus);
//  }

  public ResponseEntity findAllPageable(Pageable pageable) {
    HttpStatus httpStatus = HttpStatus.OK;
    Page<ExpenseReport> expenseReportPages = expenseReportService.findAll(pageable);
    if (null == expenseReportPages || 0 == expenseReportPages.getTotalElements()) {
      httpStatus = HttpStatus.NOT_FOUND;
    }
    return new ResponseEntity(expenseReportPages, httpStatus);
  }


  /**
   * @param id Input ExportReport id - used to fetch a single record.
   * @return ResponseEntity Returns a HttpStatus along with an Object that is displayed as JSON.
   */
  public ResponseEntity findOne(@PathVariable("id") String id) {
    HttpStatus httpStatus = HttpStatus.OK;
    final ExpenseReport expenseReport = expenseReportService.findOne(id);
    if (null == expenseReport) {
      httpStatus = HttpStatus.NOT_FOUND;
    }
    return new ResponseEntity(expenseReport, httpStatus);
  }

  /**
   * @param dtoBuilder A DTO is created on the fly by the Http attached JSON content.
   * @return ResponseEntity Returns a HttpStatus along with an Object that is displayed as JSON.
   */
  public ResponseEntity createExpenseReport(@RequestBody ExpenseReportCreateDto.Builder dtoBuilder) {
    HttpStatus httpStatus = HttpStatus.OK;
    ExpenseReportCreateDto createDto = dtoBuilder.buildForInsert();
    ExpenseReportResponseDto responseDto = expenseReportService.createFromDto(createDto);
    if (null == responseDto) {
      httpStatus = HttpStatus.NOT_ACCEPTABLE;
      return new ResponseEntity(createDto.getMessages(), httpStatus);
    } else if (responseDto.hasMessage()) {
      httpStatus = HttpStatus.NOT_ACCEPTABLE;
      return new ResponseEntity(responseDto.getMessages(), httpStatus);
    }
    return new ResponseEntity(responseDto.getExpenseReport(), httpStatus);
  }

  /**
   * @param id         The id of the ExpenseReport to attempt to update.
   * @param dtoBuilder A DTO is created on the fly by the Http attached JSON content.
   * @return ResponseEntity Returns a HttpStatus along with an Object that is displayed as JSON.
   */
  public ResponseEntity updateExpenseReport(
      @PathVariable("id") String id, @RequestBody ExpenseReportCreateDto.Builder dtoBuilder) {

    HttpStatus httpStatus = HttpStatus.OK;
    ExpenseReportResponseDto responseDto =
        expenseReportService.updateFromDto(id, dtoBuilder.buildForInsert());

    return new ResponseEntity(responseDto, httpStatus);
  }

  /**
   * @param id The id of the ExpenseReport to attempt to delete.
   */
  public ResponseEntity deleteExpenseReport(@PathVariable("id") String id) {
    HttpStatus httpStatus = HttpStatus.OK;
    final ExpenseReportResponseDto responseDto = expenseReportService.delete(id);
    return new ResponseEntity(responseDto, httpStatus);
  }
}
