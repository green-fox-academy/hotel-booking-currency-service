package com.hiddenite.controller;

import com.google.gson.Gson;
import com.hiddenite.CurrencyApplication;
import com.hiddenite.model.ChargeRequest;
import com.hiddenite.model.checkout.Checkout;
import com.hiddenite.model.checkout.CheckoutAttribute;
import com.hiddenite.model.checkout.CheckoutData;
import com.hiddenite.model.error.ErrorMessage;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

  private Checkout EURcheckout;
  private Checkout USDcheckout;
  private String updateCheckout;
  private Gson gson;
  private Checkout USDcheckout2;

  @Before
  public void setup() throws Exception {
    mockMvc = webAppContextSetup(webApplicationContext).build();
    CheckoutAttribute checkoutAttribute = new CheckoutAttribute(1L, 1L, 5000,
        EUR, "pending");
    CheckoutData checkoutData = new CheckoutData("checkout", checkoutAttribute);
    EURcheckout = new Checkout(checkoutData);
    CheckoutAttribute checkoutAttribute2 = new CheckoutAttribute(2L, 2L, 8000,
        ChargeRequest.Currency.USD, "success");
    CheckoutData checkoutData2 = new CheckoutData("checkout", checkoutAttribute2);
    USDcheckout = new Checkout(checkoutData2);
    CheckoutAttribute checkoutAttribute3 = new CheckoutAttribute(3L, 1L, 8000,
            ChargeRequest.Currency.USD, "pending");
    CheckoutData checkoutData3 = new CheckoutData("checkout", checkoutAttribute3);
    USDcheckout2 = new Checkout(checkoutData3);
    gson = new Gson();
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
  public void testMultipleFilterCheckouts() throws Exception {
    checkOutRepository.deleteAll();
    checkOutRepository.save(EURcheckout);
    checkOutRepository.save(USDcheckout);
    checkOutRepository.save(USDcheckout2);
    mockMvc.perform(get("/checkouts")
            .param("booking_id", "1")
            .param("status", "pending"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.length()").value(2))
            .andExpect(jsonPath("$.data[0].attributes.currency").value("EUR"))
            .andExpect(jsonPath("$.data[1].attributes.currency").value("USD"))
            .andExpect(jsonPath("$.data[1].attributes.user_id").value("3"));
  }

  @Test
  public void testDeleteCheckouts() throws Exception {
    checkOutRepository.deleteAll();
    checkOutRepository.save(EURcheckout);
    checkOutRepository.save(USDcheckout);
    assertEquals(checkOutRepository.count(), 2);
    mockMvc.perform(delete("/api/checkouts/" + EURcheckout.getId()))
        .andExpect(status().isOk());
    assertEquals(checkOutRepository.count(), 1);
  }

  @Test
  public void responseToNoIndexGetCheckout() throws Exception {
    checkOutRepository.deleteAll();
    ErrorMessage errorMessage = new ErrorMessage(404, "Not found", "No checkouts found by id: 666");
    mockMvc.perform(get("/api/checkouts/666"))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(contentType))
            .andExpect(content().json(gson.toJson(errorMessage)));
  }

  @Test
  public void testUpdateCheckout() throws Exception {
    checkOutRepository.deleteAll();
    checkOutRepository.save(EURcheckout);
    checkOutRepository.save(USDcheckout);
    String EURId = gson.toJson(EURcheckout.getId());
    updateCheckout = "   {\n" +
            "     \"data\": {\n" +
            "       \"type\": \"checkouts\",\n" +
            "       \"id\":" + EURId + ",\n" +
            "       \"attributes\": {\n" +
            "         \"currency\": \"USD\",\n" +
            "         \"status\": \"success\"\n" +
            "       }\n" +
            "     }\n" +
            "   }";
    mockMvc.perform(patch("/api/checkouts/" + EURId)
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