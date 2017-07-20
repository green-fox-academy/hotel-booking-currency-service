package com.hiddenite.controller;

import com.hiddenite.service.FeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(SpringRunner.class)
@WebAppConfiguration
@EnableWebMvc
public class MonthlyFeeControllerTest {
  private MockMvc mockMvc;
  private FeeService mockFeeService;

  @Before
  public void setup() throws Exception {
    mockFeeService = Mockito.mock(FeeService.class);
    mockMvc = standaloneSetup(new MonthlyFeeController(mockFeeService)).build();
  }

  @Test
  public void basicTest() throws Exception {
    mockMvc.perform(get("/api/hotels/1/fee")
        .param("currency", "EUR"))
        .andExpect(status().is2xxSuccessful());
    verify(mockFeeService).getMonthlyFee("EUR", 1l);
  }
}