package com.hiddenite.service;



import static org.junit.Assert.assertEquals;

import com.hiddenite.model.Transaction;
import com.hiddenite.repository.TransactionsRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class HotelBalanceServiceTest {

  private HotelBalanceService hotelBalanceService;

  private TransactionsRepository mockTransactionsRepository;

  @Before
  public void runBeforeEachTests() throws Exception{
    mockTransactionsRepository = Mockito.mock(TransactionsRepository.class);
    hotelBalanceService = new HotelBalanceService(mockTransactionsRepository);
  }

  @Test
  public void testServiceWithSelectedCurrency() throws Exception {
    List<Transaction> testList = new ArrayList<>();
    Integer testInteger = 6000;
    testList.add(new Transaction(1l,"EUR",1000));
    testList.add(new Transaction(2l,"EUR",2000));
    testList.add(new Transaction(3l,"EUR",3000));
    Mockito.when(mockTransactionsRepository.findAllByCurrencyAndHotelID("EUR", 1L)).thenReturn(testList);
    assertEquals(testInteger, hotelBalanceService.getBalanceByCurrency("EUR",1L));
  }

  @Test
  public void testServiceWithNotExistingCurrency() throws Exception{
    List<Transaction> testList = new ArrayList<>();
    Mockito.when(mockTransactionsRepository.findAllByCurrencyAndHotelID("EUR", 1L)).thenReturn(testList);
    assertEquals(new Integer(0), hotelBalanceService.getBalanceByCurrency("EUR",1L));
  }


}