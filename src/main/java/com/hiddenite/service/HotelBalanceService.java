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

  @Autowired
  public HotelBalanceService (TransactionsRepository transactionsRepository) {
    this.transactionsRepository = transactionsRepository;
  }


  public HotelBalance getHotelBalanceByCurrency(Long hotelID, Timestamp startDate, Timestamp endDate) {
    HotelBalanceData returnBalanceData = new HotelBalanceData();
    HotelBalance returnBalance = new HotelBalance();
    returnBalanceData.getAttributes().put("eur", getBalanceByCurrency("eur", hotelID, startDate, endDate));
    returnBalanceData.getAttributes().put("huf", getBalanceByCurrency("huf", hotelID, startDate, endDate));
    returnBalanceData.getAttributes().put("usd", getBalanceByCurrency("usd", hotelID, startDate, endDate));
    returnBalance.setData(returnBalanceData);
    returnBalance.setLinks("https://your-hostname.com/api/hotels/" + hotelID + "/balances");
    return returnBalance;
  }

  public Integer getBalanceByCurrency(String currency, Long hotelID, Timestamp startDate, Timestamp endDate) {
    List<Transaction> listByCurrency = transactionsRepository
        .findAllByCurrencyAndHotelIDAndCreatedAtBetween(currency, hotelID, startDate, endDate);
    Integer balance = 0;
    for (Transaction transaction : listByCurrency) {
      balance = balance + transaction.getAmount();
    }
    return balance;
  }
}
