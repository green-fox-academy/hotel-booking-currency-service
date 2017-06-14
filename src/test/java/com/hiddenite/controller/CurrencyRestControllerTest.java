package com.hiddenite.controller;


import com.hiddenite.CurrencyApplication;
import com.hiddenite.model.Status;
import com.hiddenite.repository.HeartbeatRepository;
import com.hiddenite.service.StatusService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurrencyApplication.class)
@WebAppConfiguration
@EnableWebMvc
public class CurrencyRestControllerTest {

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
          MediaType.APPLICATION_JSON.getSubtype(),
          Charset.forName("utf8"));

  private MockMvc mockMvc;
  private String statusIsOk = "{\"status\": \"ok\"}";
  @MockBean
  private HeartbeatRepository heartbeatRepository;

  @Autowired
  private WebApplicationContext webApplicationContext;
  private HeartbeatRepository mockHeartBeatRepo;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
    mockHeartBeatRepo = Mockito.mock(HeartbeatRepository.class);
  }

  @Test
  public void testHearthBeatStatus() throws Exception {
    mockMvc.perform(get("/heartbeat")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(statusIsOk));
  }

  @Test
  public void testHearthBeatDataBaseIsNotEmpty() throws Exception {
    BDDMockito.given(heartbeatRepository.count()).willReturn(1L);
    mockMvc.perform(get("/heartbeat")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(content().json("{\"database\": \"ok\"}"));
  }

  @Test
  public void testHearthBeatWithEmptyDataBase() throws Exception {
    Mockito.when(heartbeatRepository.count()).thenReturn(0L);
    mockMvc.perform(get("/heartbeat")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(content().json("{\"database\": \"error\"}"));
  }

  @Test
  public void testHeartBeatEmpty() throws Exception {
    Mockito.when(mockHeartBeatRepo.count()).thenReturn(0L);
    StatusService statusService = new StatusService(mockHeartBeatRepo);
    Status status = statusService.checkStatusCondition(0);
    assertEquals("error", status.getDatabase());
  }

  @Test
  public void testHeartBeartIsNotEmpty() throws Exception {
    Mockito.when(mockHeartBeatRepo.count()).thenReturn(1L);
    StatusService statusService = new StatusService(mockHeartBeatRepo);
    Status status = statusService.checkStatusCondition(0);
    assertEquals("ok", status.getDatabase());
  }

}