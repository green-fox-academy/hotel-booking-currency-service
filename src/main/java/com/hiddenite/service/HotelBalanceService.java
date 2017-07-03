package com.hiddenite.service;

import com.hiddenite.model.HotelBalance;
import com.hiddenite.model.HotelBalanceData;
import com.hiddenite.model.Transaction;
import com.hiddenite.repository.TransactionsRepository;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelBalanceService {

  @Autowired
  TransactionsRepository transactionsRepository;


  public HotelBalance getHotelBalanceByCurrency(Long hotelID) {
    HotelBalanceData returnBalanceData = new HotelBalanceData();
    HotelBalance returnBalance = new HotelBalance();
    returnBalanceData.getAttributes().put("eur", getBalanceByCurrency("eur", hotelID));
   // returnBalanceData.getAttributes().put("huf", getBalanceByCurrency("huf", hotelID));
    returnBalanceData.getAttributes().put("usd", getBalanceByCurrency("usd", hotelID));
    returnBalance.setData(returnBalanceData);
    returnBalance.setLinks("https://your-hostname.com/api/hotels/" + hotelID + "/balances");
    return returnBalance;
  }

  public Integer getBalanceByCurrency(String currency, Long hotelID) {
    HashMap<String, Integer> returnHM = new HashMap<>();
    List<Transaction> listByCurrency = transactionsRepository
        .findAllByCurrencyAndHotelID(currency, hotelID);
    Integer balance = 0;
    for (Transaction transaction : listByCurrency) {
      balance = balance + transaction.getAmount();
    }
    return balance;
  }
}
