package com.example.taskrabitmq;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public interface RabbitSyncPublisher {

  int publishMessageAndGetReplyCode(byte[] body, Map<String,String> properties) throws
      TimeoutException, IOException;
}
