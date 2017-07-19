package com.hiddenite.service;

import com.hiddenite.model.ExchangeRates;
import com.hiddenite.model.Transaction;
import com.hiddenite.repository.ExchangeRatesFromFixerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ExchangeRateService {

  private ExchangeRatesFromFixerRepository exchangeRatesFromFixerRepository;
  private RestTemplate restTemplate;

  @Autowired
  public ExchangeRateService(ExchangeRatesFromFixerRepository exchangeRateRepository,
                             RestTemplate restTemplate) {
    this.exchangeRatesFromFixerRepository = exchangeRateRepository;
    this.restTemplate = restTemplate;
  }

  public ExchangeRates getLatestExchangerates() {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String today = format.format(LocalDate.now());
    if (exchangeRatesFromFixerRepository.exists(today)) {
      return exchangeRatesFromFixerRepository.findOne(today);
    } else {
      ExchangeRates exchangeRate = restTemplate
              .getForObject("http://api.fixer.io/latest", ExchangeRates.class);
      exchangeRatesFromFixerRepository.save(exchangeRate);
      return exchangeRate;
    }
  }

  public double changeAmountToEUR(Transaction transaction) {
    if (transaction.getCurrency().equalsIgnoreCase("EUR")) {
      return transaction.getAmount();
    } else {
      return transaction.getAmount() / transaction.getExchangeRates().getRates().get(transaction.getCurrency());
    }
  }

  public double changeFromEUR(Transaction transaction, double amountInEUR, String currencyToCalculate) {
    return amountInEUR * transaction.getExchangeRates().getRates().get(currencyToCalculate);
  }

}
