package com.hiddenite.repository;

import com.hiddenite.model.exchangerates.ExchangeRateDTO;
import com.hiddenite.model.exchangerates.ExchangeRateKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateDTORepository extends CrudRepository<ExchangeRateDTO, ExchangeRateKey> {

}
