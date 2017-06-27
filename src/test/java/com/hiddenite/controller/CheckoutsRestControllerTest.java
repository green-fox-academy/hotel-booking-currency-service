package com.hiddenite.controller;

import com.hiddenite.CurrencyApplication;
import com.hiddenite.model.ChargeRequest;
import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.model.checkout.CheckoutAttribute;
import com.hiddenite.model.checkout.CheckoutData;
import com.hiddenite.model.checkout.CheckoutLinks;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    EURcheckout = new Checkout();
    EURcheckout.setCheckoutData(new CheckoutData());
    EURcheckout.setLinks(new CheckoutLinks());
    EURcheckout.getLinks().setSelf(1L);
    EURcheckout.getCheckoutData().setAttributes(new CheckoutAttribute());
    EURcheckout.getCheckoutData().getAttributes().setCurrency(ChargeRequest.Currency.EUR);
    EURcheckout.getCheckoutData().getAttributes().setAmount(5000);
    EURcheckout.getCheckoutData().getAttributes().setBookingId(1L);
    EURcheckout.getCheckoutData().getAttributes().setUserId(1L);
    EURcheckout.getCheckoutData().getAttributes().setStatus("pending");

    USDcheckout = new Checkout();
    USDcheckout.setCheckoutData(new CheckoutData());
    USDcheckout.setLinks(new CheckoutLinks());
    USDcheckout.getLinks().setSelf(1L);
    USDcheckout.getCheckoutData().setAttributes(new CheckoutAttribute());
    USDcheckout.getCheckoutData().getAttributes().setCurrency(ChargeRequest.Currency.USD);
    USDcheckout.getCheckoutData().getAttributes().setAmount(5000);
    USDcheckout.getCheckoutData().getAttributes().setBookingId(1L);
    USDcheckout.getCheckoutData().getAttributes().setUserId(1L);
    USDcheckout.getCheckoutData().getAttributes().setStatus("pending");
  }

  @Test
  public void filterCheckouts() throws Exception {
//    BDDMockito.given(EURcheckout.getCheckoutData().getAttributes().getCurrency()).willReturn(ChargeRequest.Currency
//            .EUR);
//    BDDMockito.given(USDcheckout.getCheckoutData().getAttributes().getCurrency()).willReturn(ChargeRequest.Currency
//            .USD);
    checkOutRepository.deleteAll();
    checkOutRepository.save(EURcheckout);
    checkOutRepository.save(USDcheckout);
    mockMvc.perform(post("/api/checkouts")
            .param("currency", "EUR"))
            .andExpect(status().isOk());
  }
}