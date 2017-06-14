package com.hiddenite.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import com.hiddenite.CurrencyApplication;
import com.hiddenite.repository.HeartbeatRepository;
import java.nio.charset.Charset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurrencyApplication.class)
@WebAppConfiguration
@EnableWebMvc
public class CurrencyRestControllerTest {

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));
  private MockMvc mockMvc;

  private HeartbeatRepository heartbeatRepositoryMock;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() throws Exception {
    mockMvc = webAppContextSetup(webApplicationContext).build();
    heartbeatRepositoryMock = Mockito.mock(HeartbeatRepository.class);
  }



  @Test
  public void basicTestCase() throws Exception {
    when(heartbeatRepositoryMock.count()).thenReturn(0L);
    mockMvc.perform(get("/test"))
        .andExpect(status().isOk());
  }

  @Test
  public void testTestWithEmptyDataBase() throws Exception {
    when(heartbeatRepositoryMock.count()).thenReturn(0L);
    mockMvc.perform(get("/test")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(content().json("{\"database\": \"error\"}"));
  }

  @Test
  public void testHearthBeatWithEmptyDataBase() throws Exception {
    when(heartbeatRepositoryMock.count()).thenReturn(0L);
    mockMvc.perform(get("/heartbeat")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(content().json("{\"database\": \"error\"}"));
  }

}