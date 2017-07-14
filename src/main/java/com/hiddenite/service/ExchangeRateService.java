package com.hiddenite.service;

import com.hiddenite.model.Transaction;
import com.hiddenite.model.exchangerates.ExchangeRate;
import com.hiddenite.model.exchangerates.ExchangeRateDTO;
import com.hiddenite.model.exchangerates.ExchangeRateKey;
import com.hiddenite.repository.ExchangeRateDTORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ExchangeRateService {
  private final ExchangeRateDTORepository exchangeRateDTORepository;

  @Autowired
  public ExchangeRateService(ExchangeRateDTORepository exchangeRateDTORepository) {
    this.exchangeRateDTORepository = exchangeRateDTORepository;
  }

  private ExchangeRate getExchangeratesForGivenDates() {
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForObject("http://api.fixer.io/latest",  ExchangeRate
            .class);
  }

  public void saveExChangeDTOsFromExchangeRates(Transaction transaction) {
    ExchangeRateKey exchangeRateKey = new ExchangeRateKey();
    exchangeRateKey.setDate(getPreviousDayWithGivenFormat(transaction));
    exchangeRateKey.setBase("EUR");
    exchangeRateKey.setForeignCurrency(transaction.getCurrency());
    if (!exchangeRateDTORepository.exists(exchangeRateKey)) {
      ExchangeRate exchangeRate = getExchangeratesForGivenDates();
      List<String> listOfCurrencies = new ArrayList<>(exchangeRate.getRates().keySet());
      if (listOfCurrencies.size() != 0) {
        for (String currency : listOfCurrencies) {
          ExchangeRateKey newExchangeRateKey = new ExchangeRateKey();
          newExchangeRateKey.setDate(exchangeRate.getDate());
          newExchangeRateKey.setBase(exchangeRate.getBase());
          newExchangeRateKey.setForeignCurrency(currency);
          ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO(newExchangeRateKey);
          exchangeRateDTO.setRate(exchangeRate.getRates().get(currency));
          exchangeRateDTORepository.save(exchangeRateDTO);
        }
        ExchangeRateKey newExchangeRateKey = new ExchangeRateKey();
        newExchangeRateKey.setDate(exchangeRate.getDate());
        newExchangeRateKey.setBase("EUR");
        newExchangeRateKey.setForeignCurrency("EUR");
        ExchangeRateDTO exchangeRateDTOEUR = new ExchangeRateDTO(newExchangeRateKey);
        exchangeRateDTOEUR.setRate(1);
        exchangeRateDTORepository.save(exchangeRateDTOEUR);
      }
    }
  }

  private Date getPreviousDayWithGivenFormat(Transaction transaction) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date dateOfTransaction = new Date(transaction.getCreatedAt().getTime());
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(dateOfTransaction);
    calendar.add(Calendar.DAY_OF_YEAR, -1);
    Date dateOfRateToUse = calendar.getTime();
    sdf.format(dateOfRateToUse);
    return dateOfRateToUse;
  }
}
