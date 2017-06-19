package com.hiddenite.service;

import static org.junit.Assert.*;

import com.hiddenite.model.Status;
import com.hiddenite.repository.HeartbeatRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class StatusServiceTest {

  private StatusService statusService;

  private HeartbeatRepository mockHeartBeatRepo;

  private MQService mqService;

  @Before
  public void setup() throws Exception {
    mockHeartBeatRepo = Mockito.mock(HeartbeatRepository.class);
    mqService = Mockito.mock(MQService.class);
    statusService = new StatusService(mockHeartBeatRepo, mqService);
  }

  @Test
  public void testHeartBeatEmpty() throws Exception {
    Mockito.when(mockHeartBeatRepo.count()).thenReturn(0L);
    Mockito.when(mqService.getQueueMessageCount("heartbeat")).thenReturn(1);
    Mockito.when(mqService.isConnected()).thenReturn(true);
    Status status = statusService.checkStatus();
    assertEquals("error", status.getDatabase());
  }

  @Test
  public void testHeartBeatIsNotEmpty() throws Exception {
    Mockito.when(mqService.getQueueMessageCount("heartbeat")).thenReturn(1);
    Mockito.when(mqService.isConnected()).thenReturn(true);
    Mockito.when(mockHeartBeatRepo.count()).thenReturn(1L);
    Status status = statusService.checkStatus();
    assertEquals("ok", status.getDatabase());
  }

}