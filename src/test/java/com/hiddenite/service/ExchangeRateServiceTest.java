package com.hiddenite.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import com.hiddenite.model.ExchangeRates;
import com.hiddenite.model.Transaction;
import com.hiddenite.repository.ExchangeRatesFromFixerRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

public class ExchangeRateServiceTest {

  ExchangeRateService exchangeRateService;
  ExchangeRatesFromFixerRepository exchangeRatesFromFixerRepository;
  RestTemplate mockRestTemplate;
  String today;
  ExchangeRates testExchangeRate;


  @Before
  public void runBeforeEachTest() {
    testExchangeRate = new ExchangeRates();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    today = format.format(LocalDate.now());
    mockRestTemplate = Mockito.mock(RestTemplate.class);
    exchangeRatesFromFixerRepository = Mockito.mock(ExchangeRatesFromFixerRepository.class);
    exchangeRateService = new ExchangeRateService(exchangeRatesFromFixerRepository, mockRestTemplate);
  }

  @Test
  public void testExchangeServiceWithExistingRate() throws Exception {
    Mockito.when(exchangeRatesFromFixerRepository.exists(today)).thenReturn(true);
    Mockito.when(exchangeRatesFromFixerRepository.findOne(today)).thenReturn(testExchangeRate);
    assertEquals(testExchangeRate, exchangeRateService.getExchangeratesForGivenDates());
  }

  @Test
  public void testExchangeServiceWithNotExistingRate() throws Exception{
    Mockito.when(exchangeRatesFromFixerRepository.exists(today)).thenReturn(false);
    Mockito.when(mockRestTemplate.getForObject("http://api.fixer.io/latest", ExchangeRates.class)).thenReturn(testExchangeRate);
    assertEquals(testExchangeRate, exchangeRateService.getExchangeratesForGivenDates());
  }

}