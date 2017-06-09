package com.hiddenite.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hiddenite.CurrencyApplication;
import com.hiddenite.model.Hearthbeat;
import com.hiddenite.model.Status;
import com.hiddenite.repository.HearthbeatRepository;
import com.hiddenite.service.StatusService;

import java.nio.charset.Charset;
import java.util.ArrayList;

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

import static org.junit.Assert.*;

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
  private HearthbeatRepository hearthbeatRepository;


  @Autowired
  private WebApplicationContext webApplicationContext;
  private HearthbeatRepository mockHearthBeatRepo;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
    mockHearthBeatRepo = Mockito.mock(HearthbeatRepository.class);

  }

  @Test
  public void testHearthBeatStatus() throws Exception {
    mockMvc.perform(get("/hearthbeat")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(statusIsOk));
  }

  @Test
  public void testHearthBeatDataBaseIsNotEmpty() throws Exception {
    BDDMockito.given(hearthbeatRepository.count()).willReturn(1L);
    mockMvc.perform(get("/hearthbeat")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(content().json("{\"database\": \"okassa\"}"));
  }

  @Test
  public void testHearthBeatWithEmptyDataBase() throws Exception {
    Mockito.when(hearthbeatRepository.count()).thenReturn(0L);
    mockMvc.perform(get("/hearthbeat")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(content().json("{\"database\": \"error\"}"));
  }


  @Test
  public void testHeartBeatEmpty() throws Exception {
    Mockito.when(mockHearthBeatRepo.count()).thenReturn(0L);
    StatusService statusService = new StatusService(mockHearthBeatRepo);
    Status status = statusService.checkDatabaseIsEmpty();
    assertEquals("error", status.getDatabase());
  }

  @Test
  public void testHeartBeartIsNotEmpty() throws Exception {
    Mockito.when(mockHearthBeatRepo.count()).thenReturn(1L);
    StatusService statusService = new StatusService(mockHearthBeatRepo);
    Status status = statusService.checkDatabaseIsEmpty();
    assertEquals("ok", status.getDatabase());
  }

}