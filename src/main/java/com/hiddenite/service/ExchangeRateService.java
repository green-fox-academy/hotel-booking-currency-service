package com.hiddenite.service;

import com.hiddenite.model.Transaction;
import com.hiddenite.model.exchangerates.ExchangeRate;
import com.hiddenite.model.exchangerates.ExchangeRateKey;
import com.hiddenite.model.exchangerates.ExchangeRatesFromFixer;
import com.hiddenite.repository.ExchangeRateRepository;
import com.hiddenite.repository.ExchangeRatesFromFixerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExchangeRateService {
  private final ExchangeRateRepository exchangeRateRepository;
  @Autowired
  private ExchangeRatesFromFixerRepository exchangeRatesFromFixerRepository;

  @Autowired
  public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
    this.exchangeRateRepository = exchangeRateRepository;
  }

  public ExchangeRatesFromFixer getExchangeratesForGivenDates() {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String today = format.format(LocalDate.now());
    System.out.println(today);
    if (exchangeRatesFromFixerRepository.exists(today)) {
      return exchangeRatesFromFixerRepository.findOne(today);
    } else {
      RestTemplate restTemplate = new RestTemplate();
      ExchangeRatesFromFixer exchangeRate = restTemplate.getForObject("http://api.fixer.io/latest", ExchangeRatesFromFixer.class);
      exchangeRatesFromFixerRepository.save(exchangeRate);
      return exchangeRate;
    }
  }

//  void saveSingleExchangeRatesFromFixer(Transaction transaction) {
//    ExchangeRateKey exchangeRateKey = new ExchangeRateKey();
//    exchangeRateKey.setDate(getDayInGivenFormat(transaction));
//    exchangeRateKey.setBase("EUR");
//    exchangeRateKey.setForeignCurrency(transaction.getCurrency());
//    if (!exchangeRateRepository.exists(exchangeRateKey)) {
//      ExchangeRatesFromFixer exchangeRatesFixer = getExchangeratesForGivenDates(getDayInGivenFormat(transaction));
//      List<String> listOfCurrencies = new ArrayList<>(exchangeRatesFixer.getRates().keySet());
//      if (listOfCurrencies.size() != 0) {
//        for (String currency : listOfCurrencies) {
//          ExchangeRate exchangeRate = generatingExchangeRates(exchangeRatesFixer.getDate(),
//                  exchangeRatesFixer.getBase(), currency, exchangeRatesFixer.getRates().get(currency));
//          exchangeRateRepository.save(exchangeRate);
//        }
//        ExchangeRate exchangeRateEUR = generatingExchangeRates(exchangeRatesFixer.getDate(), "EUR",
//                "EUR", 1);
//        exchangeRateRepository.save(exchangeRateEUR);
//      }
//    }
//  }

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
    ExchangeRate EURtoOtherCurrencyRate = exchangeRateRepository
            .findExchangeRateByExchangeRateKey_DateAndExchangeRateKey_ForeignCurrency(date, otherCurrency);
    if (!baseCurrency.equals(otherCurrency)) {
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
