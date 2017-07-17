package com.hiddenite.repository;

import com.hiddenite.model.exchangerates.ExchangeRatesFromFixer;
import org.springframework.data.repository.CrudRepository;

public interface ExchangeRatesFromFixerRepository extends CrudRepository<ExchangeRatesFromFixer, String> {
}
