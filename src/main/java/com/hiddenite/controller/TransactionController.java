package com.hiddenite.controller;

import com.hiddenite.model.HotelBalance;
import com.hiddenite.model.Transaction;
import com.hiddenite.model.error.ErrorMessage;
import com.hiddenite.repository.TransactionsRepository;
import com.hiddenite.service.HotelBalanceService;
import com.hiddenite.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
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


  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public ErrorMessage noSuchTransaction(Exception e) {
    return new ErrorMessage(404, "NOT FOUND", "no transaction found by the filters");
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
          defaultValue = "1970-01-01")@DateTimeFormat (pattern = "yyyy-MM-dd") Date startDate) {
    Timestamp ts = new Timestamp(startDate.getTime());
    return hotelBalanceService.getHotelBalanceByCurrency(hotelID, ts);
  }

//  public static String createDateFormat(LocalDate date) {
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
//    return date.format(formatter);
//  }
//
//
//  @InitBinder
//  public void initBinder(WebDataBinder binder) throws Exception {
//    final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//    final CustomDateEditor dateEditor = new CustomDateEditor(df, true) {
//      @Override
//      public void setAsText(String text) throws IllegalArgumentException {
//        if ("today".equals(text)) {
//          setValue(LocalDate.now());
//        } else {
//          super.setAsText(text);
//        }
//      }
//    };
//    binder.registerCustomEditor(Date.class, dateEditor);
//  }
}
