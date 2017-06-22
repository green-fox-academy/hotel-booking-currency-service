package com.hiddenite.controller;

import com.google.gson.Gson;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurrencyApplication.class)
@WebAppConfiguration
@EnableWebMvc
public class CheckOutTrackRestControllerTest {

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
          MediaType.APPLICATION_JSON.getSubtype(),
          Charset.forName("utf8"));
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;
  @Autowired
  CheckOutRepository checkOutRepository;
  String checkout;
  String checkoutWithMissingField;

  @Before
  public void setup() throws Exception {
    mockMvc = webAppContextSetup(webApplicationContext).build();
    checkout = "   {\n" +
            "     \"data\": {\n" +
            "       \"type\": \"checkout\",\n" +
            "       \"attributes\": {\n" +
            "         \"user_id\": \"1\",\n" +
            "         \"booking_id\": \"1\",\n" +
            "         \"amount\": \"100\",\n" +
            "         \"currency\": \"EUR\"\n" +
            "       }\n" +
            "     }\n" +
            "   }";
    checkoutWithMissingField = "   {\n" +
            "     \"data\": {\n" +
            "       \"type\": \"checkout\",\n" +
            "       \"attributes\": {\n" +
            "         \"user_id\": \"1\",\n" +
            "         \"amount\": \"50\",\n" +
            "         \"currency\": \"EUR\"\n" +
            "       }\n" +
            "     }\n" +
            "   }";
  }

  @Test
  public void responseToCheckout() throws Exception {
    checkOutRepository.deleteAll();
    mockMvc.perform(post("/api/checkouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(checkout))
            .andExpect(status().is2xxSuccessful())
            .andExpect(status().isCreated())
            .andExpect(content().contentType(contentType));

  }

  @Test
  public void responseToCheckoutWithMissingFields() throws Exception {
    checkOutRepository.deleteAll();
    mockMvc.perform(post("/api/checkouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(checkoutWithMissingField))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(contentType));

  }


//  @Test
//  public void responseToCheckoutWithMissingFieldsShouldReturnErrorMessage() throws Exception {
//    checkOutRepository.deleteAll();
//    mockMvc.perform(post("/api/checkouts")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(checkoutWithMissingField))
//            .andExpect(content().contentType(contentType))
//            .andExpect(status().is4xxClientError())
//            .andExpect(content().json())
//
//
//  }
}