package com.hiddenite.service;

import com.hiddenite.repository.CheckOutRepository;
import com.hiddenite.repository.CheckoutDataRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CheckoutDataServiceTest {
  private CheckoutDataRepository mockCheckoutDataRepository;
  private CheckOutRepository mockCheckOutRepository;
  private CheckoutDataService checkoutDataService;

  @Before
  public void setup() throws Exception {
    mockCheckoutDataRepository = Mockito.mock(CheckoutDataRepository.class);
    mockCheckOutRepository = Mockito.mock(CheckOutRepository.class);
    checkoutDataService = new CheckoutDataService(mockCheckoutDataRepository, mockCheckOutRepository);
  }

  @Test
  public void setCheckoutFiltering() throws Exception {
  }

}