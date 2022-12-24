package com.example.taskrabitmq;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RabbitSyncPublisherImpTest {

  @Mock
  RabbitSyncPublisherImp rabbitMQService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @ParameterizedTest
  @ValueSource(ints = {200, 311, 312, 313, 320, 402, 403, 404, 405, 406, 501})
  void testRabbitMqErrorCode(int errorCode) throws IOException, TimeoutException {
    //arrange
    when(rabbitMQService.publishMessageAndGetReplyCode(any(byte[].class), anyMap())).thenReturn(
        errorCode);
    //act
    int replyCode = rabbitMQService.publishMessageAndGetReplyCode(new byte[]{}, new HashMap<>());
    //assert
    assertEquals(errorCode, replyCode);
  }
}