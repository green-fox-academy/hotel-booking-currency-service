package com.hiddenite.service;

import com.hiddenite.model.Transaction;
import com.hiddenite.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
  private final TransactionsRepository transactionsRepository;

  @Autowired
  public TransactionService(TransactionsRepository transactionsRepository) {
    this.transactionsRepository = transactionsRepository;
  }

  public List<Transaction> filterTransaction(Long id, HttpServletRequest request) {
    if (request.getParameterNames().hasMoreElements()) {
      String filterName = request.getParameterNames().nextElement();
      if (filterName.equals("currency")) {
        return transactionsRepository.findAllByCurrencyAndHotelID(filterName, id);
      }
    }
    return transactionsRepository.findAllByHotelID(id);
  }

  public Timestamp createTimeStampFromDate(Date date) {
    Timestamp tsEnd;
    if (date != null) {
      tsEnd = new Timestamp(date.getTime());
    } else {
      tsEnd = Timestamp.valueOf(LocalDateTime.now());
    }
    return tsEnd;
  }

  public double changeAmountToEUR(Transaction transaction) {
    if (transaction.getCurrency().equals("EUR")) {
      return transaction.getAmount();
    } else {
      return transaction.getAmount() * transaction.getExchangeRates().getRates().get(transaction.getCurrency());
    }
  }

  public double changeFromEUR(Transaction transaction, double amountInEUR, String currencyToCalculate) {
    return amountInEUR * transaction.getExchangeRates().getRates().get(currencyToCalculate);
  }
}
