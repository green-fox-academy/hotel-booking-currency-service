package com.hiddenite.service;

import static org.junit.Assert.*;

import com.hiddenite.model.Status;
import com.hiddenite.repository.HeartbeatRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class StatusServiceTest {

  private HeartbeatRepository mockHeartBeatRepo;
  private StatusService statusService;

  @Before
  public void setup() throws Exception {
    mockHeartBeatRepo = Mockito.mock(HeartbeatRepository.class);
    statusService = new StatusService(mockHeartBeatRepo);
  }

  @Test
  public void testHeartBeatEmpty() throws Exception {
    Mockito.when(mockHeartBeatRepo.count()).thenReturn(0L);
    Status status = statusService.checkStatusCondition(2, true);
    assertEquals("error", status.getDatabase());
    assertEquals("error", status.getQueue());
  }

  @Test
  public void testHeartBeatIsNotEmpty() throws Exception {
    Mockito.when(mockHeartBeatRepo.count()).thenReturn(1L);
    Status status = statusService.checkStatusCondition(0, true);
    assertEquals("ok", status.getDatabase());
    assertEquals("ok", status.getQueue());
  }

}