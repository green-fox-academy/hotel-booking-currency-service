package com.hiddenite.controller;

import com.hiddenite.CurrencyApplication;
import com.hiddenite.model.ChargeRequest;
import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.model.checkout.CheckoutAttribute;
import com.hiddenite.model.checkout.CheckoutData;
import com.hiddenite.repository.CheckOutRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurrencyApplication.class)
@WebAppConfiguration
@EnableWebMvc
public class CheckoutsRestControllerTest {
  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
          MediaType.APPLICATION_JSON.getSubtype(),
          Charset.forName("utf8"));
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private CheckOutRepository checkOutRepository;

  Checkout EURcheckout;
  Checkout USDcheckout;

  @Before
  public void setup() throws Exception {
    mockMvc = webAppContextSetup(webApplicationContext).build();
    CheckoutAttribute checkoutAttribute = new CheckoutAttribute(1L, 1L, 5000, ChargeRequest.Currency.EUR, "pending");
    CheckoutData checkoutData = new CheckoutData("checkout", checkoutAttribute);
    EURcheckout = new Checkout(checkoutData);
  }

  @Test
  public void testFilterCheckouts() throws Exception {
    checkOutRepository.deleteAll();
    checkOutRepository.save(EURcheckout);
//    checkOutRepository.save(USDcheckout);
    mockMvc.perform(get("/checkouts")
            .param("currency", "EUR"))
            .andExpect(status().isOk());
  }
}