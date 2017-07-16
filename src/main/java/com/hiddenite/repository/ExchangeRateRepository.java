package com.hiddenite.repository;

import com.hiddenite.model.exchangerates.ExchangeRate;
import com.hiddenite.model.exchangerates.ExchangeRateKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, ExchangeRateKey> {
  ExchangeRate findExchangeRateByExchangeRateKey_DateAndExchangeRateKey_ForeignCurrency(String date, String currency);
  ExchangeRate findExchangeRateByExchangeRateKey_Date(String date);
}
