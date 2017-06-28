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

import static com.hiddenite.model.ChargeRequest.Currency.EUR;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
  String updateCheckout;

  @Before
  public void setup() throws Exception {
    mockMvc = webAppContextSetup(webApplicationContext).build();
    CheckoutAttribute checkoutAttribute = new CheckoutAttribute(1L, 1L, 5000,
        EUR, "pending");
    CheckoutData checkoutData = new CheckoutData("checkout", checkoutAttribute);
    EURcheckout = new Checkout(checkoutData);
    CheckoutAttribute checkoutAttribute2 = new CheckoutAttribute(2L, 2L, 8000,
        ChargeRequest.Currency.USD, "not so pending");
    CheckoutData checkoutData2 = new CheckoutData("checkout", checkoutAttribute2);
    USDcheckout = new Checkout(checkoutData2);
    updateCheckout = "   {\n" +
            "     \"data\": {\n" +
            "       \"type\": \"checkouts\",\n" +
            "       \"id\": \"1\",\n" +
            "       \"attributes\": {\n" +
            "         \"currency\": \"USD\",\n" +
            "         \"status\": \"success\"\n" +
            "       }\n" +
            "     }\n" +
            "   }";
  }

  @Test
  public void testFilterCheckouts() throws Exception {
    checkOutRepository.deleteAll();
    checkOutRepository.save(EURcheckout);
    checkOutRepository.save(USDcheckout);
    mockMvc.perform(get("/checkouts")
        .param("currency", "EUR"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()").value(1))
        .andExpect(jsonPath("$.data[:1].attributes.currency").value("EUR"));
  }

  @Test
  public void testDeleteCheckouts() throws Exception {
    checkOutRepository.deleteAll();
    checkOutRepository.save(EURcheckout);
    checkOutRepository.save(USDcheckout);
    assertEquals(checkOutRepository.count(), 2);
    mockMvc.perform(delete("/api/checkouts/2"))
        .andExpect(status().isOk())
    ;
    assertEquals(checkOutRepository.count(), 1);

  }

  @Test
  public void responseToNoIndexGetCheckout() throws Exception {
    checkOutRepository.deleteAll();
    mockMvc.perform(get("/api/checkouts/888")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError())
        .andExpect(content().contentType(contentType));
  }

  @Test
  public void testUpdateCheckout() throws Exception {
    checkOutRepository.deleteAll();
    checkOutRepository.save(EURcheckout);
    checkOutRepository.save(USDcheckout);
    mockMvc.perform(patch("/api/checkouts/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateCheckout))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.data.attributes.booking_id").value("1"))
            .andExpect(jsonPath("$.data.attributes.user_id").value("1"))
            .andExpect(jsonPath("$.data.attributes.amount").value("5000"))
            .andExpect(jsonPath("$.data.attributes.currency").value("USD"))
            .andExpect(jsonPath("$.data.attributes.status").value("success"));
  }


}