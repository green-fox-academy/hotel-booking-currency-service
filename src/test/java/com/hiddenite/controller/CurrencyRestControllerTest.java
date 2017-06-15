package com.hiddenite.controller;

import com.hiddenite.CurrencyApplication;
import com.hiddenite.repository.HeartbeatRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import static org.mockito.Mockito.when;
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
  private HeartbeatRepository heartbeatRepositoryMock;
  @Autowired
  private WebApplicationContext webApplicationContext;
  @MockBean
  private HeartbeatRepository heartbeatRepository;

  @Before
  public void setup() throws Exception {
    mockMvc = webAppContextSetup(webApplicationContext).build();
    heartbeatRepositoryMock = Mockito.mock(HeartbeatRepository.class);
  }

  @Test
  public void basicTestCase() throws Exception {
    when(heartbeatRepositoryMock.count()).thenReturn(0L);
    mockMvc.perform(get("/heartbeat"))
            .andExpect(status().isOk());
  }

  @Test
  public void testHearthBeatDataBaseIsNotEmpty() throws Exception {
    when(heartbeatRepository.count()).thenReturn(1L);
    mockMvc.perform(get("/heartbeat")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(content().json("{\"database\": \"ok\"}"));
  }

  @Test
  public void testHearthBeatWithEmptyDataBase() throws Exception {
    when(heartbeatRepository.count()).thenReturn(0L);
    mockMvc.perform(get("/heartbeat")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(content().json("{\"database\": \"error\"}"));
  }

}