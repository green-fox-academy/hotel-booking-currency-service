package com.hiddenite.service;

import com.hiddenite.model.HotelBalance;
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
    HotelBalance returnBalance = new HotelBalance();
    returnBalance.getAttributes().put("eur", getBalanceByCurrency("eur", hotelID));
   // returnBalance.getAttributes().put("huf", getBalanceByCurrency("huf", hotelID));
    returnBalance.getAttributes().put("usd", getBalanceByCurrency("usd", hotelID));
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
