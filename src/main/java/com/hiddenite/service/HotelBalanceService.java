package com.hiddenite.service;

import com.hiddenite.model.HotelBalance;
import com.hiddenite.model.HotelBalanceData;
import com.hiddenite.model.Transaction;
import com.hiddenite.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class HotelBalanceService {
  private TransactionsRepository transactionsRepository;
  private ExchangeRateService exchangeRateService;
  private HotelBalanceData returnBalanceData;
  private HotelBalance returnBalance;

  @Autowired
  public HotelBalanceService(TransactionsRepository transactionsRepository, ExchangeRateService exchangeRateService,
                             HotelBalanceData returnBalanceData, HotelBalance returnBalance) {
    this.transactionsRepository = transactionsRepository;
    this.exchangeRateService = exchangeRateService;
    this.returnBalanceData = returnBalanceData;
    this.returnBalance = returnBalance;
  }

  public HotelBalance getHotelBalanceByCurrency(Long hotelID, Timestamp startDate, Timestamp endDate) {
    returnBalanceData.getAttributes().put("eur", getBalanceByCurrencies("eur", hotelID, startDate, endDate));
    returnBalanceData.getAttributes().put("huf", getBalanceByCurrencies("huf", hotelID, startDate, endDate));
    returnBalanceData.getAttributes().put("usd", getBalanceByCurrencies("usd", hotelID, startDate, endDate));
    returnBalance.setData(returnBalanceData);
    returnBalance.setLinks("https://your-hostname.com/api/hotels/" + hotelID + "/balances");
    return returnBalance;
  }

  public HotelBalance getHotelBalanceInOneCurrency(String currency, Long hotelID, Timestamp startDate, Timestamp
          endDate) {
    returnBalanceData.getAttributes().put(currency, getBalanceInOneCurrency(currency, hotelID, startDate, endDate));
    returnBalance.setData(returnBalanceData);
    returnBalance.setLinks("https://your-hostname.com/api/hotels/" + hotelID + "/balances");
    return returnBalance;
  }

  public Double getBalanceByCurrencies(String currency, Long hotelID, Timestamp startDate, Timestamp endDate) {
    List<Transaction> listByCurrency = transactionsRepository
            .findAllByCurrencyAndHotelIDAndCreatedAtBetween(currency, hotelID, startDate, endDate);
    Double balance = 0.0;
    for (Transaction transaction : listByCurrency) {
      balance = balance + transaction.getAmount();
    }
    return balance;
  }

  public double getBalanceInOneCurrency(String currencyToCalculate, Long hotelID, Timestamp startDate, Timestamp
          endDate) {
    List<Transaction> transactionList = transactionsRepository
            .findAllByHotelIDAndCreatedAtBetween(hotelID, startDate, endDate);
    double balanceInOneCurrency = 0;
    for (Transaction transaction : transactionList) {
      double amountInEUR = exchangeRateService.changeAmountToEUR(transaction);
      if (currencyToCalculate.equals("EUR")) {
        balanceInOneCurrency += amountInEUR;
      } else {
        balanceInOneCurrency += exchangeRateService.changeFromEUR(transaction, amountInEUR, currencyToCalculate);
      }
    }
    return balanceInOneCurrency;
  }

}
