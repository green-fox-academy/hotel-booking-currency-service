package com.hiddenite.service;

import com.hiddenite.model.Transaction;
import com.hiddenite.repository.TransactionsRepository;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  @Autowired
  TransactionsRepository transactionsRepository;

  public List<Transaction> filterTransaction(Long id, HttpServletRequest request) {
    if (request.getParameterNames().hasMoreElements()) {
      String filterName = request.getParameterNames().nextElement();
      if (filterName.equals("currency")) {
        return transactionsRepository.findAllByCurrencyAndHotelID(filterName, id);
      }
    }
    return transactionsRepository.findAllByHotelID(id);
  }
}
