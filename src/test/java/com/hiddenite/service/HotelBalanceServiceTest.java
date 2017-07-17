package com.hiddenite.service;


import static org.junit.Assert.assertEquals;

import com.hiddenite.model.Transaction;
import com.hiddenite.repository.TransactionsRepository;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


public class HotelBalanceServiceTest {

  private HotelBalanceService hotelBalanceService;

  private TransactionsRepository mockTransactionsRepository;
  private ExchangeRateService mockExchangeRepository;
  private Timestamp timestamp;


  @Before
  public void runBeforeEachTests() throws Exception {
    mockTransactionsRepository = Mockito.mock(TransactionsRepository.class);
    hotelBalanceService = new HotelBalanceService(mockTransactionsRepository,
        mockExchangeRepository);
    LocalDateTime now = LocalDateTime.now();
    timestamp = Timestamp.valueOf(now);
  }

  @Test
  public void testGetBalanceByCurrencyWithHUF() throws Exception {
    List<Transaction> testList = new ArrayList<>();
    testList.add(new Transaction(1l, "HUF", 5000));
    testList.add(new Transaction(2l, "HUF", 5000));
    testList.add(new Transaction(3l, "HUF", 5000));
    Mockito.when(
        mockTransactionsRepository
            .findAllByCurrencyAndHotelIDAndCreatedAtBetween("HUF", 1l, timestamp, timestamp))
        .thenReturn(testList);
    assertEquals(hotelBalanceService.getBalanceByCurrencies("HUF",1l,timestamp,timestamp), new Double(15000));
  }


}