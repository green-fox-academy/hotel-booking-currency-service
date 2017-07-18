package com.hiddenite.service;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.hiddenite.model.HotelBalance;
import com.hiddenite.model.HotelBalanceData;
import com.hiddenite.model.Transaction;
import com.hiddenite.repository.TransactionsRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class HotelBalanceServiceTest {

  private HotelBalanceService hotelBalanceService;

  private TransactionsRepository mockTransactionsRepository;
  private ExchangeRateService mockExchangeRepository;
  private Timestamp timestamp;
  private HotelBalanceData returnBalanceData;
  private HotelBalance returnBalance;


  @Before
  public void runBeforeEachTests() throws Exception {
    returnBalanceData = Mockito.mock(HotelBalanceData.class);
    returnBalance = Mockito.mock(HotelBalance.class);
    mockTransactionsRepository = Mockito.mock(TransactionsRepository.class);
    hotelBalanceService = new HotelBalanceService(mockTransactionsRepository,
        mockExchangeRepository, returnBalanceData, returnBalance);
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
    assertEquals(hotelBalanceService.getBalanceByCurrencies("HUF", 1l, timestamp, timestamp),
        new Double(15000));
  }

  @Test
  public void testGetBalanceInOneCurrency() throws Exception {
    hotelBalanceService.getBalanceInOneCurrency("EUR", 1l, timestamp, timestamp);
    verify(hotelBalanceService).getBalanceInOneCurrency("EUR", 1l, timestamp, timestamp);
  }

 /* public HotelBalance getHotelBalanceInOneCurrency(String currency, Long hotelID, Timestamp startDate, Timestamp
      endDate) {
    HotelBalanceData returnBalanceData = new HotelBalanceData();
    HotelBalance returnBalance = new HotelBalance();
    returnBalanceData.getAttributes().put(currency, getBalanceInOneCurrency(currency, hotelID, startDate, endDate));
    returnBalance.setData(returnBalanceData);
    returnBalance.setLinks("https://your-hostname.com/api/hotels/" + hotelID + "/balances");
    return returnBalance;
  }*/


}