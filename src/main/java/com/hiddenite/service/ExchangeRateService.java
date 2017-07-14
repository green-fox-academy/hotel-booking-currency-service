package com.hiddenite.service;

import com.hiddenite.model.Transaction;
import com.hiddenite.model.exchangerates.ExchangeRate;
import com.hiddenite.model.exchangerates.ExchangeRateDTO;
import com.hiddenite.model.exchangerates.ExchangeRateKey;
import com.hiddenite.repository.ExchangeRateDTORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeRateService {
  @Autowired
  ExchangeRateDTORepository exchangeRateDTORepository;

  public ExchangeRate getExchangeratesForGivenDates() {
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForObject("http://api.fixer.io/latest?symbols=USD,HUF",  ExchangeRate
            .class);
  }

  public ExchangeRate saveExChangeDTOsFromExchangeRates(Transaction transaction, String currency) {
    ExchangeRateKey exchangeRateKey = new ExchangeRateKey();
    exchangeRateKey.setDate();
    exchangeRateKey.setBase("EUR");
    exchangeRateKey.setForeignCurrency(transaction.getCurrency());
    ExchangeRate exchangeRate = getExchangeratesForGivenDates();
    List<String> listOfCurrencies = new ArrayList<>(exchangeRate.getRates().keySet());
    if(listOfCurrencies.size() != 0) {
      for (int i = 0; i < listOfCurrencies.size(); i++) {
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        ExchangeRateKey exchangeRateKey = new ExchangeRateKey();
        exchangeRateKey.setDate(exchangeRate.getDate());
        exchangeRateKey.setBase(exchangeRate.getBase());
        exchangeRateKey.setForeignCurrency(listOfCurrencies.get(i));
        exchangeRateDTO.setRate(exchangeRate.getRates().get(listOfCurrencies.get(i)));
        if (!exchangeRateDTORepository.exists(exchangeRateKey)) {
          exchangeRateDTORepository.save(exchangeRateDTO);
        }
      }
    }
    return exchangeRate;
  }
}
