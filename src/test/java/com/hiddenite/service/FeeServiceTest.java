package com.hiddenite.service;

import com.google.gson.Gson;
import com.hiddenite.model.Treshold;
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
  private HashMap<String, Integer> tresholdMap;
  private HashMap<String, Integer> tresholdMap2;
  private List<HashMap<String, Integer>> tresholds;
  private Treshold treshold;
  private Gson gson;
  private ExchangeRateService mockExchangeRateService;
  private TransactionsRepository mockTransactionsRepository;

  @Rule
  public final EnvironmentVariables environmentVariables
          = new EnvironmentVariables();

  @Before
  public void setup() {
    tresholdMap = new HashMap<>();
    tresholdMap2 = new HashMap<>();
    tresholds = new ArrayList<>();
    tresholds.add(tresholdMap);
    tresholds.add(tresholdMap2);
    treshold = new Treshold(tresholds);
    gson = new Gson();
    mockExchangeRateService = Mockito.mock(ExchangeRateService.class);
    mockTransactionsRepository = Mockito.mock(TransactionsRepository.class);
    feeService = new FeeService(gson, mockTransactionsRepository, mockExchangeRateService);
  }

  @Test
  public void getTresholdValueWithEnviromentVariableTest() throws Exception {
    tresholdMap.put("percent", 6);
    tresholdMap.put("min-amount", 1000);
    tresholdMap2.put("percent", 2);
    tresholdMap2.put("max-amount", 3000);
    environmentVariables.set("FEE_TRESHOLD", gson.toJson(treshold));
    assertEquals(0.06, feeService.getTresholdValue(1200.0), 0.00001);
  }

  @Test
  public void getTresholdValueWithoutEnviromentVariableTest() throws Exception {
    environmentVariables.set("FEE_TRESHOLD", null);
    assertEquals(0.05, feeService.getTresholdValue(1200.0), 0.00001);
  }

//}