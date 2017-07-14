package com.hiddenite.service;

import com.hiddenite.model.Transaction;
import com.hiddenite.model.exchangerates.ExchangeRate;
import com.hiddenite.repository.ExchangeRateRepository;
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
  private final ExchangeRateRepository exchangeRateRepository;
  private ExchangeRateService exchangeRateService;

  @Autowired
  public TransactionService(TransactionsRepository transactionsRepository, ExchangeRateRepository
          exchangeRateRepository, ExchangeRateService exchangeRateService) {
    this.transactionsRepository = transactionsRepository;
    this.exchangeRateRepository = exchangeRateRepository;
    this.exchangeRateService = exchangeRateService;
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

  public void setTransactionValuesOfDifferentCurrencies(Transaction transaction) {
    double EUR_USDrate = getGivenRateForGivenDate(transaction, "USD");
    double EUR_HUFrate = getGivenRateForGivenDate(transaction, "EUR");
    if (transaction.getCurrency().equals("EUR")) {
      transaction.setEURvalue(transaction.getAmount());
      transaction.setUSDvalue(transaction.getAmount() * EUR_USDrate);
      transaction.setHUFvalue(transaction.getAmount() * EUR_HUFrate);
    } else if (transaction.getCurrency().equals("USD")){
      transaction.setUSDvalue(transaction.getAmount());
      transaction.setHUFvalue(transaction.getAmount() / EUR_USDrate * EUR_HUFrate);
      transaction.setEURvalue(transaction.getAmount() / EUR_USDrate);
    } else if (transaction.getCurrency().equals("HUF")) {
      transaction.setHUFvalue(transaction.getAmount());
      transaction.setEURvalue(transaction.getAmount() / EUR_HUFrate);
      transaction.setUSDvalue(transaction.getAmount() / EUR_USDrate);
    }
  }

  public double getGivenRateForGivenDate(Transaction transaction, String currency) {
    String previousDateOfTransaction = exchangeRateService.getPreviousDayWithGivenFormat(transaction);
    ExchangeRate exRate = exchangeRateRepository.findExchangeRateByExchangeRateKey_DateAndExchangeRateKey_ForeignCurrency(previousDateOfTransaction,
                    currency);
    return exRate.getRate();
  }
}
