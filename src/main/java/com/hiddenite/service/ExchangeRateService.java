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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ExchangeRateService {
  private final ExchangeRateRepository exchangeRateRepository;

  @Autowired
  public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
    this.exchangeRateRepository = exchangeRateRepository;
  }

  private ExchangeRatesFromFixer getExchangeratesForGivenDates() {
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForObject("http://api.fixer.io/latest",  ExchangeRatesFromFixer
            .class);
  }

  public void saveSingleExchangeRatesFromFixer(Transaction transaction) {
    ExchangeRateKey exchangeRateKey = new ExchangeRateKey();
    exchangeRateKey.setDate(getPreviousDayWithGivenFormat(transaction));
    exchangeRateKey.setBase("EUR");
    exchangeRateKey.setForeignCurrency(transaction.getCurrency());
    if (!exchangeRateRepository.exists(exchangeRateKey)) {
      ExchangeRatesFromFixer exchangeRatesFromFixer = getExchangeratesForGivenDates();
      List<String> listOfCurrencies = new ArrayList<>(exchangeRatesFromFixer.getRates().keySet());
      if (listOfCurrencies.size() != 0) {
        for (String currency : listOfCurrencies) {
          ExchangeRateKey newExchangeRateKey = new ExchangeRateKey();
          newExchangeRateKey.setDate(exchangeRatesFromFixer.getDate());
          newExchangeRateKey.setBase(exchangeRatesFromFixer.getBase());
          newExchangeRateKey.setForeignCurrency(currency);
          ExchangeRate exchangeRate = new ExchangeRate(newExchangeRateKey);
          exchangeRate.setRate(exchangeRatesFromFixer.getRates().get(currency));
          exchangeRateRepository.save(exchangeRate);
        }
        ExchangeRateKey newExchangeRateKey = new ExchangeRateKey();
        newExchangeRateKey.setDate(exchangeRatesFromFixer.getDate());
        newExchangeRateKey.setBase("EUR");
        newExchangeRateKey.setForeignCurrency("EUR");
        ExchangeRate exchangeRateDTOEUR = new ExchangeRate(newExchangeRateKey);
        exchangeRateDTOEUR.setRate(1);
        exchangeRateRepository.save(exchangeRateDTOEUR);
      }
    }
  }

  public String getPreviousDayWithGivenFormat(Transaction transaction) {
    Date dateOfTransaction = new Date(transaction.getCreatedAt().getTime());
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(dateOfTransaction);
    calendar.add(Calendar.DAY_OF_YEAR, -1);
    Date dateOfRateToUse = calendar.getTime();
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = format.format(dateOfRateToUse);
    return formattedDate;
  }
}
