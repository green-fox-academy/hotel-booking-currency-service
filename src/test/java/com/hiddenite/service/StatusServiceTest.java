package com.hiddenite.service;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.hiddenite.CurrencyApplication;
import com.hiddenite.model.Status;
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
public class StatusServiceTest {

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));

  private MockMvc mockMvc;
  private HeartbeatRepository mockHeartBeatRepo;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
    mockHeartBeatRepo = Mockito.mock(HeartbeatRepository.class);
  }

  @Test
  public void testHeartBeatEmpty() throws Exception {
    Mockito.when(mockHeartBeatRepo.count()).thenReturn(0L);
    StatusService statusService = new StatusService(mockHeartBeatRepo);
    Status status = statusService.checkStatusCondition(0, true);
    assertEquals("error", status.getDatabase());
  }

  @Test
  public void testHeartBeartIsNotEmpty() throws Exception {
    Mockito.when(mockHeartBeatRepo.count()).thenReturn(1L);
    StatusService statusService = new StatusService(mockHeartBeatRepo);
    Status status = statusService.checkStatusCondition(0, true);
    assertEquals("ok", status.getDatabase());
  }

}