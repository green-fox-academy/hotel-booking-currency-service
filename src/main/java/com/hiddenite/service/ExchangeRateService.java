package com.hiddenite.service;

import com.hiddenite.model.Transaction;
import com.hiddenite.model.exchangerates.ExchangeRate;
import com.hiddenite.model.exchangerates.ExchangeRateKey;
import com.hiddenite.model.exchangerates.ExchangeRatesFromFixer;
import com.hiddenite.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExchangeRateService {
  private final ExchangeRateRepository exchangeRateRepository;

  @Autowired
  public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
    this.exchangeRateRepository = exchangeRateRepository;
  }

  private ExchangeRatesFromFixer getExchangeratesForGivenDates(String queryParam) {
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForObject("http://api.fixer.io/" + queryParam,  ExchangeRatesFromFixer
            .class);
  }

  void saveSingleExchangeRatesFromFixer(Transaction transaction) {
    ExchangeRateKey exchangeRateKey = new ExchangeRateKey();
    exchangeRateKey.setDate(getDayInGivenFormat(transaction));
    exchangeRateKey.setBase("EUR");
    exchangeRateKey.setForeignCurrency(transaction.getCurrency());
    if (!exchangeRateRepository.exists(exchangeRateKey)) {
      ExchangeRatesFromFixer exchangeRatesFromFixer = getExchangeratesForGivenDates(getDayInGivenFormat(transaction));
      List<String> listOfCurrencies = new ArrayList<>(exchangeRatesFromFixer.getRates().keySet());
      if (listOfCurrencies.size() != 0) {
        for (String currency : listOfCurrencies) {
          ExchangeRate exchangeRate = generatingExchangeRates(exchangeRatesFromFixer.getDate(),
                  exchangeRatesFromFixer.getBase(), currency, exchangeRatesFromFixer.getRates().get(currency));
          exchangeRateRepository.save(exchangeRate);
        }
        ExchangeRate exchangeRateEUR = generatingExchangeRates(exchangeRatesFromFixer.getDate(), "EUR",
                "EUR", 1);
        exchangeRateRepository.save(exchangeRateEUR);
      }
    }
  }

  private String getDayInGivenFormat(Transaction transaction) {
    Date dateOfTransaction = new Date(transaction.getCreatedAt().getTime());
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return format.format(dateOfTransaction);
  }

  private ExchangeRate generatingExchangeRates(String date, String base, String foreignCurrency, double rate) {
    ExchangeRateKey newExchangeRateKey = new ExchangeRateKey();
    newExchangeRateKey.setDate(date);
    newExchangeRateKey.setBase(base);
    newExchangeRateKey.setForeignCurrency(foreignCurrency);
    ExchangeRate exchangeRate = new ExchangeRate(newExchangeRateKey);
    exchangeRate.setRate(rate);
    return exchangeRate;
  }

  double calculateTransactionToOtherCurrency(Transaction transaction, String otherCurrency) {
    String date = getDayInGivenFormat(transaction);
    String baseCurrency = transaction.getCurrency();
    double exchangeRate = 1;
    if (!baseCurrency.equals(otherCurrency)) {
      ExchangeRate EURtoOtherCurrencyRate = exchangeRateRepository
              .findExchangeRateByExchangeRateKey_DateAndExchangeRateKey_ForeignCurrency(date, otherCurrency);
      if (baseCurrency.equals("EUR")) {
        exchangeRate = EURtoOtherCurrencyRate.getRate();
      } else {
        ExchangeRate EURtoTransactionBase = exchangeRateRepository
                .findExchangeRateByExchangeRateKey_DateAndExchangeRateKey_ForeignCurrency(date, transaction
                        .getCurrency());
        double EURtoBaseRate = EURtoTransactionBase.getRate();
        double EURToOtherCurrencyRate = EURtoOtherCurrencyRate.getRate();
        exchangeRate = EURtoBaseRate * EURToOtherCurrencyRate;
      }
    }
    return exchangeRate;
  }
}
