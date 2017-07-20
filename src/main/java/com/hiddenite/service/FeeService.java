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
import java.util.List;

@Service
public class FeeService {
  private Gson gson;
  private TransactionsRepository transactionsRepository;
  private ExchangeRateService exchangeRateService;
  private static final double PERCENTAGE_TO_DECIMAL = 100.0;
  private static final double BASE_RATE = 0.05;


  @Autowired
  public FeeService(Gson gson, TransactionsRepository transactionsRepository, ExchangeRateService exchangeRateService) {
    this.gson = gson;
    this.transactionsRepository = transactionsRepository;
    this.exchangeRateService = exchangeRateService;
  }

  public MonthlyFee getMonthlyFee(String currency, Long hotelID) {
    Timestamp tsStart = Timestamp.valueOf(LocalDate.now().withDayOfMonth(1).atStartOfDay());
    MonthlyFee monthlyFee = new MonthlyFee();
    MonthlyFeeData monthlyFeeData = new MonthlyFeeData();
    monthlyFeeData.getAttributes().put(currency, getTotalFeeOfTransactions(currency, hotelID, tsStart));
    monthlyFee.setData(monthlyFeeData);
    return monthlyFee;
  }

  public double getTotalFeeOfTransactions(String currencyToCalculate, Long hotelID, Timestamp startDate) {
    List<Transaction> transactionList = transactionsRepository
            .findAllByHotelIDAndCreatedAtAfter(hotelID, startDate);
    if (0 < transactionList.size() && !transactionList.get(0).getExchangeRates().getRates().containsKey(currencyToCalculate)) {
      throw new NotValidCurrencyException();
    }
    return sumTransactionFees(currencyToCalculate, transactionList);
  }

  private double sumTransactionFees(String currencyToCalculate, List<Transaction> transactionList) {
    double sumOfTransactionsFee = 0;
    for (Transaction transaction : transactionList) {
      double amountInEUR = exchangeRateService.changeAmountToEUR(transaction);
      double tresholdValue = getTresholdValue(amountInEUR);
      if (currencyToCalculate.equalsIgnoreCase("EUR")) {
        sumOfTransactionsFee += amountInEUR * tresholdValue;
      } else {
        sumOfTransactionsFee += exchangeRateService.changeFromEUR(transaction, amountInEUR, currencyToCalculate) * tresholdValue;
      }
    }
    return sumOfTransactionsFee;
  }

  public Double getTresholdValue(Double amount) {
    Treshold treshold = gson.fromJson(System.getenv("FEE_TRESHOLD"), Treshold.class);
    Double feePercentage = BASE_RATE;
    if (treshold != null) {
      feePercentage = setFeePercentageByTreshold(amount, treshold, feePercentage);
    }
    return feePercentage;
  }

  private Double setFeePercentageByTreshold(Double amount, Treshold treshold, Double feePercentage) {
    if (amount >= treshold.getTresholds().get(0).get("min-amount") && amount < treshold.getTresholds().get(1).get("max-amount")) {
      feePercentage = Double.valueOf(treshold.getTresholds().get(0).get("percent")) / PERCENTAGE_TO_DECIMAL;
    }
    if (amount >= treshold.getTresholds().get(1).get("max-amount")) {
      feePercentage = Double.valueOf(treshold.getTresholds().get(1).get("percent")) / PERCENTAGE_TO_DECIMAL;
    }
    return feePercentage;
  }

}
