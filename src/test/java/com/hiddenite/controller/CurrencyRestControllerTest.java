package com.hiddenite.controller;


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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
  private String  statusIsOk = "{\"status\": \"ok\"}";

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void testHearthBeatIsOk() throws Exception {
    mockMvc.perform(get("/hearthbeat")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(statusIsOk));
  }

  @Test
  public void testHeartBeatEmpty() throws Exception {
    HearthbeatRepository mockHearthBeatRepo = Mockito.mock(HearthbeatRepository.class);
    StatusService statusService = new StatusService(mockHearthBeatRepo);
    assertEquals(statusService.checkDatabaseIsEmpty().getDatabase(),"error");
  }

  @Test
  public void testHeartBeartIsNotEmpty() throws Exception {
    HearthbeatRepository mockHearthBeatRepo = Mockito.mock(HearthbeatRepository.class);
    mockHearthBeatRepo.save(new Hearthbeat());
    StatusService statusService = new StatusService(mockHearthBeatRepo);
    assertEquals("ok", statusService.checkDatabaseIsEmpty().getDatabase());
  }


}