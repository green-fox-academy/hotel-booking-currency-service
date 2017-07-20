package com.hiddenite.controller;

import com.hiddenite.repository.TransactionsRepository;
import com.hiddenite.service.HotelBalanceService;
import com.hiddenite.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(SpringRunner.class)
@WebAppConfiguration
@EnableWebMvc
public class TransactionControllerTest {
  private MockMvc mockMvc;
  private TransactionService transactionService;
  private TransactionsRepository transactionsRepositoryMock;
  private HotelBalanceService hotelBalanceServiceMock;
  private String from20160622;
  private String to20170719;
  private Date fromDate;
  private Date endDate;
  private Timestamp tsFromDate;
  private Timestamp tsToDate;

  @Before
  public void setup() throws Exception {
    transactionsRepositoryMock = Mockito.mock(TransactionsRepository.class);
    transactionService = new TransactionService(transactionsRepositoryMock);
    hotelBalanceServiceMock = Mockito.mock(HotelBalanceService.class);
    mockMvc = standaloneSetup(new TransactionController(transactionService, transactionsRepositoryMock, hotelBalanceServiceMock)).build();

    from20160622 = "2016-06-22";
    to20170719 = "2017-07-19";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    fromDate = formatter.parse(from20160622);
    endDate = formatter.parse(to20170719);
    tsFromDate = new Timestamp(fromDate.getTime());
    tsToDate = new Timestamp(endDate.getTime());
  }

  @Test
  public void transactionControllerShouldReturnBalance() throws Exception {
    mockMvc.perform(get("/api/hotels/1/balances")
            .param("from", from20160622)
            .param("to", to20170719))
            .andExpect(status().is2xxSuccessful());
    verify(hotelBalanceServiceMock).getHotelBalanceByCurrency(1L, tsFromDate, tsToDate);
  }
}