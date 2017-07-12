package com.hiddenite.service;

import com.hiddenite.model.HotelBalance;
import com.hiddenite.model.HotelBalanceData;
import com.hiddenite.model.Transaction;
import com.hiddenite.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Service
public class HotelBalanceService {


  private TransactionsRepository transactionsRepository;

  @Autowired
  public HotelBalanceService (TransactionsRepository transactionsRepository) {
    this.transactionsRepository = transactionsRepository;
  }


  public HotelBalance getHotelBalanceByCurrency(Long hotelID, Timestamp startDate) {
    HotelBalanceData returnBalanceData = new HotelBalanceData();
    HotelBalance returnBalance = new HotelBalance();
    returnBalanceData.getAttributes().put("eur", getBalanceByCurrency("eur", hotelID, startDate));
    returnBalanceData.getAttributes().put("huf", getBalanceByCurrency("huf", hotelID, startDate));
    returnBalanceData.getAttributes().put("usd", getBalanceByCurrency("usd", hotelID, startDate));
    returnBalance.setData(returnBalanceData);
    returnBalance.setLinks("https://your-hostname.com/api/hotels/" + hotelID + "/balances");
    return returnBalance;
  }

  public Integer getBalanceByCurrency(String currency, Long hotelID, Timestamp startDate) {
    HashMap<String, Integer> returnHM = new HashMap<>();
    List<Transaction> listByCurrency = transactionsRepository
        .findAllByCurrencyAndHotelIDAndCreatedAtAfter(currency, hotelID, startDate);
    Integer balance = 0;
    for (Transaction transaction : listByCurrency) {
      balance = balance + transaction.getAmount();
    }
    return balance;
  }
}
