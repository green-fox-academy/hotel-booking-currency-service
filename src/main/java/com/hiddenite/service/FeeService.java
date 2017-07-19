package com.hiddenite.service;

import com.google.gson.Gson;
import com.hiddenite.model.Transaction;
import com.hiddenite.model.Treshold;
import com.hiddenite.model.error.NotValidCurrencyException;
import com.hiddenite.model.monthlyFee.MonthlyFee;
import com.hiddenite.model.monthlyFee.MonthlyFeeData;
import com.hiddenite.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeeService {
  private Gson gson;
  private TransactionsRepository transactionsRepository;
  private TransactionService transactionService;


  @Autowired
  public FeeService(Gson gson, TransactionsRepository transactionsRepository, TransactionService transactionService) {
    this.gson = gson;
    this.transactionsRepository = transactionsRepository;
    this.transactionService = transactionService;
  }

  public MonthlyFee getMonthlyFee(String currency, Long hotelID) {
    Timestamp tsStart = Timestamp.valueOf(LocalDate.now().withDayOfMonth(1).atStartOfDay());
    Timestamp tsEnd = Timestamp.valueOf(LocalDateTime.now());
    MonthlyFee monthlyFee = new MonthlyFee();
    MonthlyFeeData monthlyFeeData = new MonthlyFeeData();
    monthlyFeeData.getAttributes().put(currency, getFeeOfTransactions(currency, hotelID, tsStart, tsEnd));
    monthlyFee.setData(monthlyFeeData);
    return monthlyFee;
  }

  private double getFeeOfTransactions(String currencyToCalculate, Long hotelID, Timestamp startDate, Timestamp
          endDate) {
    List<Transaction> transactionList = transactionsRepository
            .findAllByHotelIDAndCreatedAtBetween(hotelID, startDate, endDate);
    if (!transactionList.get(0).getExchangeRates().getRates().containsKey(currencyToCalculate)) {
      throw new NotValidCurrencyException();
    }
    double sumOfTransactionsFee = 0;
    for (Transaction transaction : transactionList) {
      double amountInEUR = transactionService.changeAmountToEUR(transaction);
      double tresholdValue = getTresholdValue(amountInEUR);
      if (currencyToCalculate.equalsIgnoreCase("EUR")) {
        sumOfTransactionsFee += amountInEUR * tresholdValue;
      } else {
        sumOfTransactionsFee += transactionService.changeFromEUR(transaction, amountInEUR, currencyToCalculate) * tresholdValue;
      }
    }
    return sumOfTransactionsFee;
  }

  private Double getTresholdValue(Double amount) {
    Treshold treshold = gson.fromJson(System.getenv("FEE_TRESHOLD"), Treshold.class);
    Double feePercentage = 0.05;
    if (treshold != null) {
      if (amount >= treshold.getTresholds().get(0).get("min-amount") && amount < treshold.getTresholds().get(1).get("max-amount")) {
        feePercentage = Double.valueOf(treshold.getTresholds().get(0).get("min-amount")) / 100;
      }
      if (amount >= treshold.getTresholds().get(1).get("max-amount")) {
        feePercentage = Double.valueOf(treshold.getTresholds().get(1).get("max-amount")) / 100;
      }
    }
    return feePercentage;
  }

}
