package com.hiddenite.controller;

import com.hiddenite.model.ErrorMessage;
import com.hiddenite.model.HotelBalance;
import com.hiddenite.model.Transaction;
import com.hiddenite.repository.TransactionsRepository;
import com.hiddenite.service.HotelBalanceService;
import com.hiddenite.service.TransactionService;


import java.util.List;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
  public HotelBalance getHotelBalance(@PathVariable(name = "id") Long hotelID) {
    return hotelBalanceService.getHotelBalanceByCurrency(hotelID);
  }

}
