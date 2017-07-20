package com.hiddenite.service;

import com.google.gson.Gson;
import com.hiddenite.model.Threshold;
import com.hiddenite.repository.TransactionsRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FeeServiceTest {
  private FeeService feeService;
  private HashMap<String, Integer> thresholdMap;
  private HashMap<String, Integer> thresholdMap2;
  private List<HashMap<String, Integer>> thresholds;
  private Threshold threshold;
  private Gson gson;
  private ExchangeRateService mockExchangeRateService;
  private TransactionsRepository mockTransactionsRepository;

  @Rule
  public final EnvironmentVariables environmentVariables
          = new EnvironmentVariables();

  @Before
  public void setup() {
    thresholdMap = new HashMap<>();
    thresholdMap2 = new HashMap<>();
    thresholds = new ArrayList<>();
    thresholds.add(thresholdMap);
    thresholds.add(thresholdMap2);
    threshold = new Threshold(thresholds);
    gson = new Gson();
    mockExchangeRateService = Mockito.mock(ExchangeRateService.class);
    mockTransactionsRepository = Mockito.mock(TransactionsRepository.class);
    feeService = new FeeService(gson, mockTransactionsRepository, mockExchangeRateService);
  }

  @Test
  public void getTresholdValueWithEnviromentVariableTest() throws Exception {
    thresholdMap.put("percent", 6);
    thresholdMap.put("min-amount", 1000);
    thresholdMap2.put("percent", 2);
    thresholdMap2.put("max-amount", 3000);
    environmentVariables.set("FEE_THRESHOLD", gson.toJson(threshold));
    assertEquals(0.06, feeService.getThresholdValue(1200.0), 0.00001);
  }

  @Test
  public void getTresholdValueWithoutEnviromentVariableTest() throws Exception {
    environmentVariables.set("FEE_THRESHOLD", null);
    assertEquals(0.05, feeService.getThresholdValue(1200.0), 0.00001);
  }

//  @Test(expected = NotValidCurrencyException.class)
//  public void testNotValidCurrencyException() {
//    Timestamp tsStart = Timestamp.valueOf(LocalDate.now().withDayOfMonth(1).atStartOfDay());
//    Transaction transaction = new Transaction();
//    List<Transaction> transactions = new ArrayList<>();
//    transactions.add(transaction);
//    Mockito.when(mockTransactionsRepository.findAllByHotelIDAndCreatedAtAfter(1L, tsStart)).thenReturn(transactions);
//    feeService.getMonthlyFee("Not existing currency", 1L);
//  }
}

