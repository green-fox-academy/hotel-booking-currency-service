package com.hiddenite.service;

import com.hiddenite.model.ExchangeRates;
import com.hiddenite.model.Transaction;
import com.hiddenite.repository.ExchangeRatesFromFixerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class ExchangeRateServiceTest {

  ExchangeRateService exchangeRateService;
  ExchangeRatesFromFixerRepository exchangeRatesFromFixerRepository;
  RestTemplate mockRestTemplate;
  String today;
  ExchangeRates testExchangeRate;
  Transaction mockTransaction;
  ExchangeRates mockExchangeRates;
  Map<String, Double> ratesHelper;


  @Before
  public void runBeforeEachTest() {
    testExchangeRate = new ExchangeRates();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    today = format.format(LocalDate.now());
    mockRestTemplate = Mockito.mock(RestTemplate.class);
    exchangeRatesFromFixerRepository = Mockito.mock(ExchangeRatesFromFixerRepository.class);
    exchangeRateService = new ExchangeRateService(exchangeRatesFromFixerRepository, mockRestTemplate);
    mockTransaction = Mockito.mock(Transaction.class);
    Mockito.when(mockTransaction.getAmount()).thenReturn(1000);
    mockExchangeRates = Mockito.mock(ExchangeRates.class);
    Mockito.when(mockTransaction.getExchangeRates()).thenReturn(mockExchangeRates);
    ratesHelper = new HashMap<>();
  }

  @Test
  public void testExchangeServiceWithExistingRate() throws Exception {
    Mockito.when(exchangeRatesFromFixerRepository.exists(today)).thenReturn(true);
    Mockito.when(exchangeRatesFromFixerRepository.findOne(today)).thenReturn(testExchangeRate);
    assertEquals(testExchangeRate, exchangeRateService.getLatestExchangerates());
  }

  @Test
  public void testExchangeServiceWithNotExistingRate() throws Exception {
    Mockito.when(exchangeRatesFromFixerRepository.exists(today)).thenReturn(false);
    exchangeRateService.getLatestExchangerates();
    verify(mockRestTemplate).getForObject("http://api.fixer.io/latest", ExchangeRates.class);
  }

  @Test
  public void testChangeToEURFromEUR() throws Exception {
    Mockito.when(mockTransaction.getCurrency()).thenReturn("EUR");
    assertEquals(1000, exchangeRateService.changeAmountToEUR(mockTransaction), 0.0001);
  }

  @Test
  public void testChangeToEURFromUSD() throws Exception {
    ratesHelper.put("USD", 0.8);
    Mockito.when(mockTransaction.getCurrency()).thenReturn("USD");
    Mockito.when(mockExchangeRates.getRates()).thenReturn(ratesHelper);
    assertEquals(1250, exchangeRateService.changeAmountToEUR(mockTransaction), 0.0001);
  }

  @Test
  public void testChangeFromEURToUSD() throws Exception {
    ratesHelper.put("USD", 0.8);
    Mockito.when(mockTransaction.getCurrency()).thenReturn("EUR");
    Mockito.when(mockExchangeRates.getRates()).thenReturn(ratesHelper);
    assertEquals(800, exchangeRateService.changeFromEUR(mockTransaction, exchangeRateService.changeAmountToEUR
                    (mockTransaction), "USD"),
            0.0001);
  }
}