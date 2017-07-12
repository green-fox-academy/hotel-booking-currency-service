package com.hiddenite.controller;

import com.hiddenite.model.HotelBalance;
import com.hiddenite.model.Transaction;
import com.hiddenite.model.error.ErrorMessage;
import com.hiddenite.repository.TransactionsRepository;
import com.hiddenite.service.HotelBalanceService;
import com.hiddenite.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class TransactionController {

  @Autowired
  TransactionService transactionService;

  @Autowired
  TransactionsRepository transactionsRepository;

  @Autowired
  HotelBalanceService hotelBalanceService;

  public final static LocalDate today = LocalDate.now();

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public ErrorMessage noSuchTransaction(Exception e) {
    return new ErrorMessage(404, "NOT FOUND", "no transaction found by the filters");
  }


  @InitBinder
  public void initBinder(WebDataBinder binder) throws Exception {
    final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    final CustomDateEditor dateEditor = new CustomDateEditor(df, true) {
      @Override
      public void setAsText(String text) throws IllegalArgumentException {
        if ("today".equals(text)) {
          setValue(LocalDate.now());
        } else {
          super.setAsText(text);
        }
      }
    };
    binder.registerCustomEditor(Date.class, dateEditor);
  }

  @RequestMapping("/api/hotel/{id}/transactions")
  public List<Transaction> getTransactions(@PathVariable(name = "id") Long id,
      HttpServletRequest request) {
    if (transactionService.filterTransaction(id, request).size() != 0) {
      return transactionService.filterTransaction(id, request);
    } else {
      throw new NoSuchElementException();
    }
  }

  @GetMapping("/api/hotel/{hotelID}/transactions/{transactionID}")
  public Transaction getSingleTransaction(@PathVariable(name = "hotelID") Long hotelid,
      @PathVariable(name = "transactionID") Long trID) {
    if (transactionsRepository.findByTransactionIDAndHotelID(trID, hotelid) != null) {
      return transactionsRepository.findByTransactionIDAndHotelID(trID, hotelid);
    }
    throw new NoSuchElementException();
  }

  @GetMapping("/api/hotels/{id}/balances")
  public HotelBalance getHotelBalance(@PathVariable(name = "id") Long hotelID, @RequestParam (name = "from",
  defaultValue = "1970-01-01")@DateTimeFormat (pattern = "yyyy-MM-dd") Date startDate, @RequestParam (name = "to",
          required = false) @DateTimeFormat (pattern = "yyyy-MM-dd") Date endDate, HttpRequestHandlerServlet request) {
    Timestamp tsStart = new Timestamp(startDate.getTime());
    Timestamp tsEnd;
    System.out.println(endDate);
    if (endDate != null) {
      tsEnd = new Timestamp(endDate.getTime());
    } else {
      tsEnd = Timestamp.valueOf(LocalDateTime.now());
    }
    return hotelBalanceService.getHotelBalanceByCurrency(hotelID, tsStart, tsEnd);
  }

  public static String createCurrentDateFormat(LocalDate today) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return today.format(formatter);
  }
}
