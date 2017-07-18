package com.hiddenite.repository;

import com.hiddenite.model.ExchangeRates;
import org.springframework.data.repository.CrudRepository;

public interface ExchangeRatesFromFixerRepository extends CrudRepository<ExchangeRates, String> {
}
